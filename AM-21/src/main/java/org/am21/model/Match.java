package org.am21.model;

import org.am21.controller.CommunicationController;
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
            Thread td = new Thread() {
                @Override
                public void run() {
                    super.run();
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
                if (gameState == GameState.WaitingPlayers && (player.getController().clientInput != null || player.getController().clientHandlerSocket != null)) {

                    //TODO: NEW Protocol
                    //New Communication Protocol
                    CommunicationController.instance.notifyToWait(VirtualViewHelper.convertMatchInfoToJSON(this), player.getController());
                    //OLD RMI only
                    //player.getController().clientInput.callBack.notifyToWait(VirtualViewHelper.convertMatchInfoToJSON(this));
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
                playerList.remove(player);
                player.setPlayerScore(0);
                player.setStatus(UserStatus.Online);
                player.setMatch(null);
                player.setShelf(null);
                synchronized (GameManager.playerMatchMap) {
                    GameManager.playerMatchMap.remove(player.getNickname());
                }
                sendTextToAll(SC.YELLOW_BB + "\nServer > " + player.getNickname() + " left the match" + SC.RST, true, true);
                checkRoom();
                //TODO: update VV PLayersList and Player Score, Shelf List...
                //TODO: watch out what if shelf is null
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
                sendMessageToAll(LastRound, true);
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
                    //TODO: New protocl
                    CommunicationController.instance.sendMessageToClient(
                            "Server > " + player.getNickname() + " acquired " + goal.tokenStack.get(0) + " points", true, player.getController());
                    //OLD RMI only
                    //player.getController().clientInput.callBack.sendMessageToClient(
                    //"Server > " + player.getNickname() + " acquired " + goal.tokenStack.get(0) + " points");
                }
                goal.commonGoalAchieved(player);
                sendTextToAll(SC.YELLOW_BB + "Server > " + player.getNickname() + " achieved a Common Goal!"
                        + " Press 'Enter'\n" + SC.RST, true, true);
            }
        }
    }

    /**
     * TODO: rewrite better
     *
     * @return
     */
    public List<String> checkGamePoints() {
        Player p;
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
            if (p.equals(firstToComplete)) {
                res.set(i, res.get(i) + "+ 1 Endgame Token\n");
            }
            res.set(i, res.get(i) + "Total: " + total + "\n");

            p.getController().addScore(personal + group);
        }
        return res;
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
        List<String> gameRes = checkGamePoints();
        VirtualViewHelper.updateVirtualScores(this);
        decideWinner();
        if (winner != null) {
            gameRes.add("\n The winner is " + winner.getNickname() + "!!\n");
        } else {
            gameRes.add("\n No winner for this match!\n");
        }
        VirtualViewHelper.virtualizeGameResults(this, gameRes);
        updatePlayersView();

        //Print the Final Stats of the Match
        //Removing players from the match
        for (Player p : playerList) {

            if (p.getController().clientInput != null || p.getController().clientHandlerSocket != null) {
                //TODO: new protocol
                CommunicationController.instance.notifyEndMatch(p.getController());
                //OLD RMI
                //p.getController().clientInput.callBack.notifyEndMatch();
            }
            p.setStatus(UserStatus.Online);
            p.setMatch(null);
            p.setShelf(null);
            synchronized (GameManager.playerMatchMap) {
                GameManager.playerMatchMap.remove(p.getNickname());
            }
        }
        //TODO: Maybe not necessary, at the end, match instance will be deleted
        playerList.clear();
        //System.out.println("Game > Room closed. See ya!");
        //temp
        gameState = GameState.Closed;
        //TODO: add VV deletion
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
        sendMessageToAll(BB, false);

        //Initialization of the board
        board = new Board(this);
        if (board.firstSetup()) {
            sendMessageToAll(BB_Ok, false);
        } else {
            sendMessageToAll(BB_No, false);
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
        //System.out.println("Match > Player Turn: " + currentPlayer.getNickname());
        setGamePhase(GamePhase.Selection);
        //TODO: test it
        VirtualViewHelper.buildVirtualView(this);
        updatePlayersView();
        for (Player p : playerList) {

            if (p.getController().clientInput != null || p.getController().clientHandlerSocket != null) {
                //TODO: new protocol
                CommunicationController.instance.notifyStart(matchID, p.getController());
                //OLD RMI
                //p.getController().clientInput.callBack.notifyStart(matchID);
            }
        }

    }

    /**
     * This method set up the next turn
     */
    public void nextTurn() {
        sendTextToAll(SC.YELLOW_BB + "\nServer > " + currentPlayer.getNickname() + " ended his turn" + SC.RST, false, false);
        currentPlayer = playerList.get((playerList.indexOf(currentPlayer) + 1) % maxSeats);
        setGamePhase(GamePhase.Selection);
        if (currentPlayer.getController().clientInput != null || currentPlayer.getController().clientHandlerSocket != null) {
            String message = SC.RED_B + "Server[!] > " + currentPlayer.getNickname() + "! It's your turn." + SC.RST;
            //TODO: new Protocol
            CommunicationController.instance.sendMessageToClient(message, false, currentPlayer.getController());

            //OLD RMI
            //currentPlayer.getController().clientInput.callBack.sendMessageToClient(message);
        }

    }


    /**
     * Update each player with the new version of the virtual view
     * When? When the match begins and when the turn of a player ends
     */
    public void updatePlayersView() {

        for (Player p : playerList) {
            //TODO: Watch out for test
            //if (p.getController().clientInput != null || p.getController().clientHandlerSocket != null) {
                //TODO: new Protocol
                CommunicationController.instance.sendVirtualView(getJSONVirtualView(), playerList.indexOf(p), p.getController());
                //OLD RMI
                //p.getController().clientInput.callBack.sendVirtualView(getJSONVirtualView(), playerList.indexOf(p));
            //}
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
     * Send a pre-defined Server Message to each player of this match
     *
     * @param message
     */
    public void sendMessageToAll(ServerMessage message, boolean refresh) {
        for (Player p : playerList) {
            if (p.getController().clientInput != null || p.getController().clientHandlerSocket != null) {
                GameManager.sendReply(p.getController(), message, refresh);
            }
        }
    }

    /**
     * Send a text message to each player of this match
     *
     * @param message
     * @param includeCurrentPlayer if false the message is not sent to the currentPlayer
     */
    public void sendTextToAll(String message, boolean includeCurrentPlayer, boolean refresh) {
        for (Player p : playerList) {
            if (!includeCurrentPlayer && p.equals(currentPlayer)) {
                continue;
            }
            if (p.getController().clientInput != null || p.getController().clientHandlerSocket != null) {
                GameManager.sendTextReply(p.getController(), message, refresh);
            }
        }
    }

    /**
     * Send Chat notification to all player in the match except the sender
     * @param message
     * @param sender
     * @param refresh
     */
    public void sendChatNotification(String message,String sender, boolean refresh) {
        for (Player p : playerList) {
            if (p.getNickname().equals(sender)) {
                continue;
            }
            if (p.getController().clientInput != null || p.getController().clientHandlerSocket != null) {
                GameManager.sendChatNotification(p.getController(), message, refresh);
            }
        }
    }

    /**
     * Update each player view with the new JSONHand
     */
    public void updateVirtualHand() {
        for (Player p : playerList) {
            //TODO: Watch out for test
//            if (p.getController().clientInput.callBack != null) {
                //TODO: new Protocol
                CommunicationController.instance.sendVirtualHand(getJSONHand(), p.getController());

                //OLD RMI
                //p.getController().clientInput.callBack.sendVirtualHand(getJSONHand());}
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

    public String getJSONHand() {
        return VirtualViewHelper.convertVirtualHandToJSON(virtualView);
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




    public void updatePlayersChats(){
        for (Player p : playerList) {
            CommunicationController.instance.sendVirtualPublicChat(VirtualViewHelper.convertPublicChatToJSON(virtualView),p.getController());
            //CommunicationController.instance.sendVirtualPrivateChat(VirtualViewHelper.convertPrivateChats(virtualView),p.getController());
            //CommunicationController.instance.sendVirtualChatMap(VirtualViewHelper.convertVirtualChatMapToJSON(virtualView),p.getController());
        }
    }

}