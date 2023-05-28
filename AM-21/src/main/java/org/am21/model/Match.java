package org.am21.model;

import org.am21.controller.CommunicationController;
import org.am21.controller.GameController;
import org.am21.model.Cards.CommonGoal;
import org.am21.model.Cards.PersonalGoalCard;
import org.am21.model.chat.ChatManager;
import org.am21.model.enumer.*;
import org.am21.model.items.Board;
import org.am21.model.items.Shelf;
import org.am21.model.virtualview.VirtualView;
import org.am21.utilities.CardUtil;
import org.am21.utilities.CommonGoalUtil;
import org.am21.utilities.MyTimer;
import org.am21.utilities.VirtualViewHelper;

import java.util.ArrayList;
import java.util.List;

import static org.am21.model.enumer.ServerMessage.*;

public class Match {
    public int matchID;
    public Player admin;
    public List<CommonGoal> commonGoals;
    private boolean endGameToken = true;
    public Board board;
    public GameState gameState;
    public GamePhase gamePhase;
    public Player currentPlayer;
    public List<Player> playerList;
    public int maxSeats;
    private Player firstToComplete;
    public VirtualView virtualView;
    public Player chairman;
    public ChatManager chatManager;
    public MyTimer timer;
    public Player winner;

    public Match(int maxSeats) {
        this.maxSeats = maxSeats;
        playerList = new ArrayList<>(maxSeats);
        gameState = GameState.WaitingPlayers;
        commonGoals = new ArrayList<>(2);
        chatManager = new ChatManager(this);
        virtualView = new VirtualView();
        virtualView.setMaxSeats(maxSeats);

    }

    /**
     * @param endGameToken true if the match is ended, otherwise false
     */
    public void setEndGameToken(boolean endGameToken) {
        this.endGameToken = endGameToken;
    }

    public boolean isEndGameToken() {
        return endGameToken;
    }

    public Player getFirstToComplete() {

        return firstToComplete;
    }

    /**
     * Change GamePhase
     *
     * @param phase new GamePhase
     */
    public void setGamePhase(GamePhase phase) {
        gamePhase = phase;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public boolean changeSeats(Player p, int maxSeats) {
        if (gameState.equals(GameState.WaitingPlayers) && p.equals(admin) && maxSeats > 1 && maxSeats < 5) {
            this.maxSeats = maxSeats;
            this.virtualView.setMaxSeats(maxSeats);

            Thread td = new Thread() {
                @Override
                public void run() {
                    super.run();
                    updatePlayersView();
                    checkRoom();
                }
            };
            td.start();
            return true;
        }
        return false;
    }


    /**
     * Add player to this match.
     * And, eventually, when there are enough players,
     * it will call the method to initialize the match {@link #initializeMatch()}
     *
     * @param player player that need to be added to the match
     * @return true if the addition is successful, otherwise false
     */
    public boolean addPlayer(Player player) {
        synchronized (playerList) {
            if (playerList.size() < maxSeats) {
                sendTextToAll(SC.YELLOW_BB + "\nServer > " + player.getNickname() + " joined the match." + SC.RST, true, true);
                playerList.add(player);
                player.setStatus(UserStatus.GameMember);
                player.setMatch(this);
                player.setPlayerScore(0);
                player.setShelf(new Shelf(player));
                synchronized (GameManager.playerMatchMap) {
                    GameManager.playerMatchMap.put(player.getNickname(), matchID);
                }
                checkRoom();
                //If, after checkRoom(), the match did not start, send Client to Waiting Phase
                if (gameState == GameState.WaitingPlayers) {
                    VirtualViewHelper.virtualizeMatchList();
                    CommunicationController.instance.notifyToWait(VirtualViewHelper.convertMatchInfoToJSON(this), player.getController());
                    if (chatManager.publicChatMessages.size() > 0) {
                        CommunicationController.instance.sendVirtualPublicChat(VirtualViewHelper.convertPublicChatToJSON(this.virtualView), player.getController());
                    }
                }

                return true;
            }
            return false;
        }
    }


    /**
     * This method allows to safely remove the player from the match.
     * Operations:
     * - Update PlayerList<br>
     * - Update Player's game items<br>
     * - Update PlayerMatchMap<br>
     *
     * @param player player who left the match
     * @return true if the operation is successful, otherwise false
     */
    public boolean removePlayer(Player player) {

        synchronized (playerList) {
            if (playerList.contains(player)) {
                if (gameState == GameState.WaitingPlayers && player.equals(admin) && playerList.size() > 1) {
                    int nextAdmin = (playerList.indexOf(player) + 1) % maxSeats;
                    admin = playerList.get(nextAdmin);
                    virtualView.admin = admin.getNickname();
                    for (Player p : playerList) {
                        if (p.equals(player)) continue;
                        CommunicationController.instance.notifyToWait(VirtualViewHelper.convertMatchInfoToJSON(this), p.getController());
                    }
                }
                playerList.remove(player);
                player.setPlayerScore(0);
                player.setStatus(UserStatus.Online);
                player.setMatch(null);
                player.setShelf(null);
                synchronized (GameManager.playerMatchMap) {
                    GameManager.playerMatchMap.remove(player.getNickname());
                }
                VirtualViewHelper.virtualizeMatchList();
                updatePlayersView();
                sendTextToAll(SC.YELLOW_BB + "\nServer > " + player.getNickname() + " left the match" + SC.RST, true, true);
                checkRoom();

                return true;
            }
            return false;
        }
    }

    /**
     * This method is called at the end of each player's turn
     */
    public void callEndTurnRoutine() {
        //Check if currentPlayer has achieved any Common goal
        checkCommonGoals(currentPlayer);
        //Update Virtual View --> Private points, Players Scores, Common Goal Tokens
        VirtualViewHelper.updateHiddenPoints(this);
        VirtualViewHelper.updateCommonGoalScore(this);

        //Check if last round is completed
        if (gameState == GameState.LastRound &&
                playerList.get((playerList.indexOf(currentPlayer) + 1) % maxSeats) == firstToComplete) {
            //GAME OVER(almost)

            endMatch();
        } else {
            //Not GAME OVER
            // Check if the board need refill
            if (board.checkBoard()) {
                if (board.bag.refillBoard()) {
                    //Board refilled
                    //Update Virtual View --> Board
                    VirtualViewHelper.virtualizeBoard(this);

                }
            }

            // Check if the CurrentPlayer is the first to complete his shelf
            if (currentPlayer.getShelf().getTotSlotAvail() == 0 && gameState != GameState.LastRound) {
                //TODO: Server message
                //System.out.println("Match > Congratulations! " + currentPlayer.getNickname() + " has completed the shelves first");
                this.setEndGameToken(false);
                //System.out.println("Match > virtualizeEndGame Token assigned");
                firstToComplete = currentPlayer;
                firstToComplete.setPlayerScore(firstToComplete.getPlayerScore() + 1);
                gameState = GameState.LastRound;
                VirtualViewHelper.virtualizeEndGame(this);
                sendTextToAll(LastRound.value(), true, true);
            }
            this.nextTurn();
            endTurnUpdate();
        }
    }

    /**
     * This method check if the player has completed any Goal
     *
     * @param player player that need to check
     */
    public void checkCommonGoals(Player player) {
        for (CommonGoal goal : commonGoals) {
            if (goal.checkGoal(player.getShelf()) && !goal.achievedPlayers.contains(player)) {
                // Give player points/scoreToken
                //Server Message: announce how many points the player's got
                if (player.getController().clientInput != null || player.getController().clientHandlerSocket != null) {
                    String mex = "Server > " + player.getNickname() + " acquired " + goal.tokenStack.get(0) + " points";
                    CommunicationController.instance.sendMessageToClient(mex, player.getController());
                    CommunicationController.instance.notifyUpdate(player.getController(), 1000);
                }
                goal.commonGoalAchieved(player);
                sendTextToAll(SC.YELLOW_BB + "Server > " + player.getNickname() + " achieved a Common Goal!"
                        + " Press 'Enter'\n" + SC.RST, true, true);
            }
        }
    }

    /**
     * This method return a matrix, which contains game results data
     * 0 - Player name
     * 1 - Common Goal points
     * 2 - Personal Goal Points
     * 3 - Shelf Group Points
     * 4 - Eventual Endgame token
     * 5 - Total Score;
     * @return Game Results Matrix
     */
    public String[][] checkGamePoints() {
        Player p;
        String[][] resultsMatrix;
        synchronized (playerList) {
            resultsMatrix = new String[playerList.size()+1][6];
            List<String> res = new ArrayList<>(playerList.size());
            for (int i = 0; i < playerList.size(); i++) {
                p = playerList.get(i);
                int common = p.getPlayerScore();
                int personal = p.getHiddenPoints();
                int group = p.getShelf().getGroupPoints();
                int total = common + personal + group;
                res.add("* " + p.getNickname() + " Score:\n" +
                        "+ " + common + " Common points\n" +
                        "+ " + personal + " Personal points\n" +
                        "+ " + group + " Group points\n"
                );
                resultsMatrix[i][0] = p.getNickname();  // set player name
                resultsMatrix[i][1] = String.valueOf(common); // set common points
                resultsMatrix[i][2] = String.valueOf(personal); // set personal points
                resultsMatrix[i][3] = String.valueOf(group); // set shelf points
                resultsMatrix[i][4] = null; // set default value for endgame token
                resultsMatrix[i][5] = String.valueOf(total); // set total score


                if (p.equals(firstToComplete)) {
                    res.set(i, res.get(i) + "+ 1 Endgame Token\n");
                    resultsMatrix[i][4] = "1";
                }
                res.set(i, res.get(i) + "Total: " + total + "\n");
                // Adding score
                p.getController().addScore(personal + group);
            }
        }
        return resultsMatrix;
    }


    /**
     * This method is called when the match ends.
     * Operations:
     * - Declare the absolute winner<br>
     * - Show the final game stats.<br>
     * - Reset players score<br>
     * - Remove all the players from the match
     */
    private boolean endMatch() {
        String[][] gameResults = checkGamePoints();
        decideWinner();
        if (winner != null) {
            //gameRes.add("\n The winner is " + winner.getNickname() + "!!\n");
            gameResults[playerList.size()][0] = winner.getNickname();
        } else {
            //gameRes.add("\n No winner for this match!\n");
        }
        VirtualViewHelper.updateVirtualScores(this);
        VirtualViewHelper.virtualizeGameResults(this, gameResults);
        updatePlayersView();

        //Print the Final Stats of the Match
        //Removing players from the match
        for (Player p : playerList) {
            CommunicationController.instance.notifyEndMatch(p.getController());
            p.setStatus(UserStatus.Online);
            p.setMatch(null);
            p.setShelf(null);
            synchronized (GameManager.playerMatchMap) {
                GameManager.playerMatchMap.remove(p.getNickname());
            }
        }
        playerList.clear();
        gameState = GameState.Closed;

        return true;
    }

    /**
     * This method is called whenever there is a risk that the players number changes.
     * If there isn't enough players to continue the game it calls {@link #endMatch()}.
     * Otherwise:
     * If the state is {@link GameState#WaitingPlayers}: it calls {@link #initializeMatch()}
     * If it is {@link GameState#Ready}: it calls {@link #startFirstRound()}
     */
    public void checkRoom() {
        if (playerList.size() == 0) {
            gameState = GameState.Closed;
            return;
        }
        if (playerList.size() < maxSeats) {
            if (!gameState.equals(GameState.WaitingPlayers)) {
                endMatch();
            }
        } else {
            if (gameState.equals(GameState.WaitingPlayers)) {
                initializeMatch();
                //System.out.println("Match > InitMatch Complete");
                checkRoom();
            }
            if (gameState.equals(GameState.Ready)) {
                startFirstRound();
                //System.out.println("Match > Start first round!");
            }
        }
    }

    /**
     * This should be just the initialization of the Match:
     * Building board, Bag, cards, choosing chairman,set {@link GameState#GameGoing }
     * <p>
     * At the end,it will call another method to start the first Round:
     * Declare who is player turn
     * And setting Turn Phase
     */
    public void initializeMatch() {
        //Determine the chairman player
        chairman = playerList.get((int) (Math.random() * maxSeats));

        //Distribution of Personal Goals
        List<PersonalGoalCard> personalGoalCards = CardUtil.buildPersonalGoalCard(maxSeats);
        for (int i = 0; i < maxSeats; i++) {
            //Give player's reference to the card
            personalGoalCards.get(i).player = playerList.get(i);
            //Give players their Personal Goal
            playerList.get(i).setMyPersonalGoal(personalGoalCards.get(i));
        }

        //Draw 2 randomly Common Goals
        commonGoals = CommonGoalUtil.getCommonGoals(maxSeats);

        //Register the players in the playerMatchMap
        for (Player player : playerList) {
            GameManager.playerMatchMap.put(player.getNickname(), matchID);
        }
        sendTextToAll(BB.value(), true, false);

        //Initialization of the board
        board = new Board(this);
        if (board.firstSetup()) {
            sendTextToAll(BB_Ok.value(), true, false);
        } else {
            sendTextToAll(BB_No.value(), true, false);
        }
        setGameState(GameState.Ready);
    }

    /**
     * Command to start the first round.
     * State: {@link GameState#GameGoing}
     */
    private void startFirstRound() {
        gameState = GameState.GameGoing;
        currentPlayer = chairman;
        setGamePhase(GamePhase.Selection);
        //TODO: test it
        VirtualViewHelper.virtualizeMatchList();
        VirtualViewHelper.buildVirtualView(this);
        updatePlayersView();
        GameController.updatePlayersGlobalView();
        for (Player p : playerList) {
            CommunicationController.instance.notifyStart(matchID, p.getController());
        }

    }

    /**
     * This method set up the next turn
     */
    public void nextTurn() {
        sendTextToAll(SC.YELLOW_BB + "\nServer > " + currentPlayer.getNickname() + " ended his turn" + SC.RST, false, false);
        do {
            currentPlayer = playerList.get((playerList.indexOf(currentPlayer) + 1) % maxSeats);
             if (currentPlayer.getStatus().equals(UserStatus.Suspended)) {
                sendTextToAll(SC.YELLOW_BB + "\nServer > " + currentPlayer.getNickname() + " his turn is skipped" + SC.RST, false, true);
            }
        } while (currentPlayer.getStatus().equals(UserStatus.Suspended));
        setGamePhase(GamePhase.Selection);
        if (currentPlayer.getController().clientInput != null || currentPlayer.getController().clientHandlerSocket != null) {
            String message = SC.RED_B + "Server[!] > " + currentPlayer.getNickname() + "! It's your turn." + SC.RST;
            CommunicationController.instance.sendMessageToClient(message, currentPlayer.getController());
            //GameManager.notifyUpdate(currentPlayer.getController(),100);
        }
    }


    /**
     * Update each player with the new version of the virtual view
     * When? When the match begins and when the turn of a player ends
     */
    public void updatePlayersView() {
        for (Player p : playerList) {
            if(p.getStatus() == UserStatus.GameMember)
                CommunicationController.instance.sendVirtualView(getJSONVirtualView(), playerList.indexOf(p), p.getController());
        }
    }


    /**
     * This method will decide the absolute winner of the match
     * If multiple players have the same highest score, then there is no winner.
     */
    public void decideWinner() {
        Player tiePlayer = null;
        Player topPlayer = null;
        int maxScore = 0;
        for (Player p : playerList) {
            if (p.getPlayerScore() > maxScore) {
                topPlayer = p;
                maxScore = p.getPlayerScore();
            } else if (p.getPlayerScore() == maxScore) {
                tiePlayer = p;
            }
        }
        if (topPlayer != null && tiePlayer != null && tiePlayer.getPlayerScore() == topPlayer.getPlayerScore()) {
            winner = null;
        } else {
            winner = topPlayer;
        }

    }

    /**
     * Send a text message to each player of this match
     *
     * @param message              message from the server
     * @param includeCurrentPlayer if false the message is not sent to the currentPlayer
     */
    public void sendTextToAll(String message, boolean includeCurrentPlayer, boolean update) {
        for (Player p : playerList) {
            if (!includeCurrentPlayer && p.equals(currentPlayer)) {
                continue;
            }
            GameManager.sendReply(p.getController(), message);
            if (update) GameManager.notifyUpdate(p.getController(), 1000);
        }
    }

    /**
     * Send Chat notification to all player in the match except the sender
     *
     * @param message message to send
     * @param sender  player name of the sender
     */
    public void sendPublicChatNotification(String message, String sender) {
        for (Player p : playerList) {
            if (p.getNickname().equals(sender)) {
                continue;
            }
            GameManager.sendChatNotification(p.getController(), message);
        }
    }

    /**
     * Update each player view with the new JSONHand
     */
    public void updateVirtualHand() {
        for (Player p : playerList) {
            CommunicationController.instance.sendVirtualHand(VirtualViewHelper.convertVirtualHandToJSON(virtualView), p.getController());
        }
    }

    /**
     * Get the JSON of the virtual view
     *
     * @return the JSON of the virtual view
     */

    public String getJSONVirtualView() {
        return VirtualViewHelper.convertVirtualViewToJSON(virtualView);
    }


    /**
     * This method is called at the end of a SelectCell, DeselectCell or ClearSelectedCards
     */
    public void selectionUpdate() {
        VirtualViewHelper.virtualizeCurrentPlayerHand(this);
        VirtualViewHelper.virtualizeBoard(this);
        updatePlayersView();
    }

    /**
     * Maybe not necessary, can be merged with end turn update
     */
    public void insertionUpdate() {
        VirtualViewHelper.virtualizeBoard(this);
        VirtualViewHelper.virtualizeCurrentPlayerHand(this);
        VirtualViewHelper.updateVirtualShelves(this);
        updatePlayersView();
    }

    public void sortUpdate() {
        VirtualViewHelper.virtualizeCurrentPlayerHand(this);
        updateVirtualHand();
    }

    public void endTurnUpdate() {
        VirtualViewHelper.updateVirtualScores(this);
        VirtualViewHelper.virtualizeCurrentPlayer(this);
        updatePlayersView();
    }

    public void updatePlayersPublicChats() {
        for (Player p : playerList) {
            CommunicationController.instance.sendVirtualPublicChat(VirtualViewHelper.convertPublicChatToJSON(virtualView), p.getController());
        }
    }

}