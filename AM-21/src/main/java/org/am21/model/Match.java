package org.am21.model;

import org.am21.controller.CommunicationController;
import org.am21.controller.GameController;
import org.am21.model.Cards.CommonGoal;
import org.am21.model.Cards.PersonalGoalCard;
import org.am21.model.chat.ChatManager;
import org.am21.model.enumer.GamePhase;
import org.am21.model.enumer.GameState;
import org.am21.model.enumer.SC;
import org.am21.model.enumer.UserStatus;
import org.am21.model.items.Board;
import org.am21.model.items.Shelf;
import org.am21.model.virtualview.VirtualView;
import org.am21.utilities.CardUtil;
import org.am21.utilities.CommonGoalUtil;
import org.am21.utilities.VirtualViewHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

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
    public Timer pauseTimer;
    public Player winner;

    public Match(int maxSeats) {
        this.maxSeats = maxSeats;
        playerList = new ArrayList<>(maxSeats);
        gameState = GameState.WaitingPlayers;
        commonGoals = new ArrayList<>(2);
        chatManager = new ChatManager();
        virtualView = new VirtualView();

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

    /**
     * set the game state of the match
     * @param gameState new game state
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * change the max player of the match
     * @param p player that want to change the max player
     * @param maxSeats new max player
     * @return true if the change is successful, otherwise false
     */
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
     * Add player to this match and in the GameManager.matchMap. Reset Player data regarding the match
     * Also the method checkRoom() will be called to check if the game can starts
     * If the match is not started yet, the Client will receive a notification and sent to the Waiting Room
     *
     * @param player player that need to be added to the match
     * @return true if the addition is successful, otherwise false
     */
    public boolean addPlayer(Player player) {
        synchronized (playerList) {
            if (playerList.size() < maxSeats) {
                sendTextToAll(SC.YELLOW_BB + player.getNickname() + " joined the match." + SC.RST, true);
                playerList.add(player);
                player.setStatus(UserStatus.GameMember);
                player.setMatch(this);
                player.setPlayerScore(0);
                player.setHiddenPoints(0);
                player.setShelf(new Shelf(player));
                synchronized (GameManager.playerMatchMap) {
                    GameManager.playerMatchMap.put(player.getNickname(), matchID);
                }
                checkRoom();
                //If, after checkRoom(), the match did not start, send Client to Waiting Phase
                if (gameState == GameState.WaitingPlayers) {
                    VirtualViewHelper.virtualizeMatchMap();
                    CommunicationController.instance.notifyToWait(VirtualViewHelper.convertMatchInfoToJSON(this), player.getController());
                    if (chatManager.getPublicChatMessages().size() > 0) {
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
     * If the player was the admin, the role will be passed on the next player in list if possible.
     * Operations:
     * - Update PlayerList<br>
     * - Update Player's game items<br>
     * - Update PlayerMatchMap<br>
     * - Update VirtualView (Server and Match)
     * - Call CheckRoom() to check if it needs to end the match.
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
                    virtualView.setAdmin(admin.getNickname());
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
                VirtualViewHelper.virtualizeMatchMap();
                VirtualViewHelper.virtualizeOnlinePlayers();
                updatePlayersView();
                sendTextToAll(SC.YELLOW_BB + player.getNickname() + " left the match" + SC.RST, true);
                sendNotificationToAll(true);
                checkRoom();

                return true;
            }
            return false;
        }
    }

    /**
     * set game state to pause
     */
    public void pauseMatch() {
        setGameState(GameState.Pause);
    }

    /**
     * This method is called at the end of each player's turn
     * It will call method to check if the current player has reached any goal, or completed the shelf, so it can initiate last round.
     * It checks if the board need to be refilled.
     * It checks if the conditions to end the match are respected.
     * And obviously call the nextTurn() method and update Virtual views.
     */
    public void callEndTurnRoutine() {
        //Check if currentPlayer has achieved any Common goal
        checkCommonGoals(currentPlayer);
        //Update Virtual View --> Private points, Players Scores, Common Goal Tokens
        VirtualViewHelper.updateHiddenPoints(this);
        VirtualViewHelper.updateCommonGoalScore(this);

        //Check if last round is completed
        if (gameState.equals(GameState.LastRound)  &&
                playerList.get((playerList.indexOf(currentPlayer) + 1) % maxSeats).equals(firstToComplete)) {

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
                String message = SC.BLUE_BOLD + currentPlayer.getNickname() + " obtained the endgame token by completing the shelf first." + SC.RST;
                sendTextToAll(message, true);
                this.setEndGameToken(false);
                firstToComplete = currentPlayer;
                firstToComplete.setPlayerScore(firstToComplete.getPlayerScore() + 1);
                gameState = GameState.LastRound;
                VirtualViewHelper.virtualizeEndGame(this);
                sendTextToAll(LastRound.value(), true);
            }
            this.nextTurn();
            endTurnUpdate();
            sendNotificationToAll(true);
        }
    }

    /**
     * This method check if the player has completed any CommonGoal
     *
     * @param player player that need to check the goal
     */
    public void checkCommonGoals(Player player) {
        for (CommonGoal goal : commonGoals) {
            if (!goal.achievedPlayers.contains(player) && goal.checkGoal(player.getShelf())) {
                // Give player points/scoreToken
                //Server Message: announce how many points the player's got
                String mex = player.getNickname() + " acquired " + goal.tokenStack.get(0) + " points";
                CommunicationController.instance.sendMessageToClient(mex, player.getController());
                CommunicationController.instance.notifyUpdate(player.getController(), 1000);
                goal.commonGoalAchieved(player);
                sendTextToAll(SC.YELLOW_BB + player.getNickname() + " achieved a Common Goal!"+ SC.RST, true);
                sendNotificationToAll(true);
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
     *
     * @return Game Results Matrix
     */
    public String[][] checkGamePoints() {
        Player p;
        String[][] resultsMatrix;
        synchronized (playerList) {
            resultsMatrix = new String[playerList.size() + 1][6];
            for (int i = 0; i < playerList.size(); i++) {
                p = playerList.get(i);

                int common = p.getPlayerScore();
                int personal = p.getHiddenPoints();
                int group = p.getShelf().getGroupPoints();
                int total = common + personal + group;

                resultsMatrix[i][0] = p.getNickname();  // set player name
                resultsMatrix[i][1] = String.valueOf(common); // set common points
                resultsMatrix[i][2] = String.valueOf(personal); // set personal points
                resultsMatrix[i][3] = String.valueOf(group); // set shelf points
                resultsMatrix[i][4] = null; // set default value for endgame token
                resultsMatrix[i][5] = String.valueOf(total); // set total score

                if (p.equals(firstToComplete) && p.getStatus().equals(UserStatus.GameMember)) {
                    resultsMatrix[i][4] = "1";
                    resultsMatrix[i][1] = ""+(Integer.parseInt(resultsMatrix[i][1])-1);
                }
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
     * - Calculate the game results<br>
     * - Reset players score<br>
     * - Remove all the players from the match
     */
    public boolean endMatch() {
        String[][] gameResults = checkGamePoints();
        decideWinner();
        if (winner != null) {
            gameResults[playerList.size()][0] = winner.getNickname();
        }
        //Update virtual view
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
        VirtualViewHelper.virtualizeMatchMap();
        GameController.updatePlayersGlobalView();
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
                checkRoom();
            }
            if (gameState.equals(GameState.Ready)) {
                startFirstRound();
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
    private void initializeMatch() {
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
        sendTextToAll(BB.value(), true);

        //Initialization of the board
        board = new Board(this);
        if (board.firstSetup()) {
            sendTextToAll(BB_Ok.value(), true);
        } else {
            sendTextToAll(BB_No.value(), true);
        }
        setGameState(GameState.Ready);
    }

    /**
     * Command which starts the first round.
     */
    private void startFirstRound() {
        gameState = GameState.GameGoing;
        currentPlayer = chairman;
        setGamePhase(GamePhase.Selection);
        // Virtual view update
        VirtualViewHelper.virtualizeMatchMap();
        VirtualViewHelper.buildVirtualView(this);
        updatePlayersView();
        GameController.updatePlayersGlobalView();
        //Notify Game members to start match
        synchronized (playerList) {
            for (Player p : playerList) {
                CommunicationController.instance.notifyStart(matchID, p.getController());
            }
        }

    }

    /**
     * This method set up the next turn
     */
    public void nextTurn() {
        if (gameState.equals(GameState.Closed) || gameState.equals(GameState.Pause)) {
            currentPlayer = null;
            sendTextToAll(CloseOrPause.value(), true);
            return;
        }

        sendTextToAll(SC.YELLOW_BB + currentPlayer.getNickname() + " ended his turn" + SC.RST, false);
        do {
            currentPlayer = playerList.get((playerList.indexOf(currentPlayer) + 1) % maxSeats);
            if (currentPlayer.getStatus().equals(UserStatus.Suspended)) {
                sendTextToAll(SC.YELLOW_BB + currentPlayer.getNickname() + " his turn is skipped" + SC.RST, false);
                sendNotificationToAll(false);
            }
        } while (currentPlayer.getStatus().equals(UserStatus.Suspended));
        setGamePhase(GamePhase.Selection);

        String message = SC.RED_B+ currentPlayer.getNickname() + "! It's your turn." + SC.RST;
        CommunicationController.instance.sendMessageToClient(message, currentPlayer.getController());
        GameManager.notifyUpdate(currentPlayer.getController(), 0);
    }


    /**
     * Update match's players with the new version of the virtual view.
     */
    public void updatePlayersView() {
        synchronized (playerList) {
            for (Player p : playerList) {
                if (p.getStatus() == UserStatus.GameMember)
                    CommunicationController.instance.sendVirtualView(getJSONVirtualView(), playerList.indexOf(p), p.getController());
            }
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
        synchronized (playerList) {
            for (Player p : playerList) {
                if (p.getStatus().equals(UserStatus.Suspended)) {
                    continue;
                }

                if (p.getPlayerScore() > maxScore) {
                    topPlayer = p;
                    maxScore = p.getPlayerScore();
                } else if (p.getPlayerScore() == maxScore) {
                    tiePlayer = p;
                }
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
    public void sendTextToAll(String message, boolean includeCurrentPlayer) {
        synchronized (playerList) {
            for (Player p : playerList) {
                if (!includeCurrentPlayer && p.equals(currentPlayer)) {
                    continue;
                }
                GameManager.sendReply(p.getController(), message);
            }
        }
    }


    /**
     * Send Chat notification to all player in the match except the sender
     *
     * @param message message to send
     * @param sender  player name of the sender
     */
    public void sendPublicChatNotification(String message, String sender) {
        synchronized (playerList) {
            for (Player p : playerList) {
                if (p.getNickname().equals(sender)) {
                    continue;
                }
                GameManager.sendChatNotification(p.getController(), message);
            }
        }
    }

    /**
     * Update each player view with the new JSONHand
     */
    public void updateVirtualHand() {
        synchronized (playerList) {
            for (Player p : playerList) {
                CommunicationController.instance.sendVirtualHand(VirtualViewHelper.convertVirtualHandToJSON(virtualView), p.getController());
            }
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
     * Method to update virtual view after an insertion
     */
    public void insertionUpdate() {
        VirtualViewHelper.virtualizeBoard(this);
        VirtualViewHelper.virtualizeCurrentPlayerHand(this);
        VirtualViewHelper.updateVirtualShelves(this);
        updatePlayersView();
    }

    /**
     * Method to update virtual view after turn end
     */
    public void endTurnUpdate() {
        VirtualViewHelper.updateVirtualScores(this);
        VirtualViewHelper.virtualizeCurrentPlayer(this);
        updatePlayersView();
    }

    /**
     * Send to all game members group chat virtual view
     */
    public void updatePlayersPublicChats() {
        synchronized (playerList) {
            for (Player p : playerList) {
                CommunicationController.instance.sendVirtualPublicChat(VirtualViewHelper.convertPublicChatToJSON(virtualView), p.getController());
            }
        }
    }

    /**
     * Send an interface update notification to all game members
     * @param includeCurrentPlayer true if the current player should receive the notification
     */
    public void sendNotificationToAll(boolean includeCurrentPlayer) {
        synchronized (playerList) {
            for (Player p : playerList) {
                if (!includeCurrentPlayer && p.equals(currentPlayer)) {
                    continue;
                }
                GameManager.notifyUpdate(p.getController(), 500);
            }
        }

    }

}