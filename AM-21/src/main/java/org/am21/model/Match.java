package org.am21.model;

import org.am21.model.Cards.CommonGoal;
import org.am21.model.Cards.PersonalGoalCard;
import org.am21.model.enumer.GamePhase;
import org.am21.model.enumer.GameState;
import org.am21.model.enumer.ServerMessage;
import org.am21.model.enumer.UserStatus;
import org.am21.model.items.Board;
import org.am21.model.items.Shelf;
import org.am21.utilities.CardUtil;
import org.am21.utilities.CommonGoalUtil;
import org.am21.utilities.GameGear;
import org.am21.utilities.MyTimer;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.ArrayList;
import java.util.List;

import static org.am21.model.enumer.ServerMessage.*;

public class Match {
    public int matchID;
    public List<CommonGoal> commonGoals;
    private boolean endGameToken = true;
    public Board board;
    public GameState gameState;
    public GamePhase gamePhase;
    public Player currentPlayer;
    public List<Player> playerList;
    public int maxSeats;
    private Player firstToComplete;
    //TODO:initialize the virtual view
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
        virtualView=new VirtualView();
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
     * @param phase new GamePhase
     */
    public void setGamePhase(GamePhase phase) {
        gamePhase = phase;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Add player to this match.
     * And, eventually, when there are enough players,
     * it will call the method to initialize the match {@link #initializeMatch()}
     * @param player player that need to be added to the match
     * @return true if the addition is successful, otherwise false
     */
    public boolean addPlayer(Player player) {
        synchronized (playerList) {
            if (playerList.size() < maxSeats) {
                playerList.add(player);
                player.setStatus(UserStatus.GameMember);
                player.setMatch(this);
                player.setPlayerScore(0);
                player.setShelf(new Shelf(player));
                synchronized (GameManager.playerMatchMap) {
                    GameManager.playerMatchMap.put(player.getNickname(), matchID);
                }
                //System.out.println("Game > " + player.getNickname() + " added to the match N." + this.matchID);
                //Update virtual view
                //VirtualViewHelper.setPlayers(this);
                //notifyVirtualView();
                checkRoom();
                //If, after checkRoom(), the match did not start, send Client to Waiting Phase
                if(gameState==GameState.WaitingPlayers){
                    try {
                        player.getController().clientInput.callBack.notifyToWait();
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
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
                checkRoom();
                //TODO: update VV PLayersList and Player Score, Shelf List...
                return true;
            }
            return false;
        }
    }

    /**
     * This method is called at the end of each player's turn
     */
    private void callEndTurnRoutine() {
        //Check if last round is completed
        if (gameState == GameState.LastRound &&
                playerList.get((playerList.indexOf(currentPlayer) + 1) % maxSeats) == firstToComplete) {
            //Calculate Personal Goal Points for each player
            checkPersonalGoals();
            checkShelfPoints();
            endMatch();
        } else {
            if (board.checkBoard()) {
                System.out.println("Match > Board need refill");
                GameGear.printThisBoard(board);
                //refill
                if (board.bag.refillBoard()) {
                    GameGear.printThisBoard(board);

                }
            }

            if (currentPlayer.getShelf().getTotSlotAvail() == 0 && gameState != GameState.LastRound) {
                //System.out.println("Match > Congratulations! " + currentPlayer.getNickname() + " has completed the shelves first");
                this.setEndGameToken(false);
                //System.out.println("Match > EndGame Token assigned");
                firstToComplete = currentPlayer;
                firstToComplete.setPlayerScore(firstToComplete.getPlayerScore() + 1);
                gameState = GameState.LastRound;
                //TODO: update VV Gamestate,endgametoken, player score
            }
            this.nextTurn();
            //TODO: Add VV update BOard, GamePhase, CurrentPlayer.
            //      notify all at end
        }
    }

    /**
     * This method check if the player has completed any Goal
     * @param player player that need to check
     */
    public void checkCommonGoals(Player player) {
        if (gamePhase == GamePhase.GoalChecking) {
            for (CommonGoal goal : commonGoals) {
                if (goal.checkGoal(player.getShelf())) {
                    // Give player points/scoreToken
                    //Server Message: announce how many points the player's got
                    if(player.getController().clientInput.callBack!=null) {
                        try {
                            player.getController().clientInput.callBack.sendMessageToClient("Match > " + player.getNickname() + " acquired " + goal.tokenStack.get(0) + " points");
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    goal.commonGoalAchieved(player);
                }
            }
            setGamePhase(GamePhase.EndTurn);
            this.callEndTurnRoutine();
        }
    }

    /**
     * Sequence for Personal Goal Check
     * Called after the last round (Current Setup)
     * Otherwise(Alternative setup):
     * We need to change the Personal Goal Card setup.
     * - Add new Integer that store old Points
     * So each time a checkGoal is called, the points will be removed from player's score
     * and new points will be added thank to calculatePoints().
     * This way, the personal goal can be called individually at the end of each turn.
     */
    public void checkPersonalGoals() {
        for (Player p : playerList) {
            p.getController().addScore(p.getMyPersonalGoal().calculatePoints());
        }
    }

    public void checkShelfPoints() {
        for (Player p : playerList) {
            p.getController().addScore(p.getShelf().getGroupPoints());
        }
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
        decideWinner();
        //Print the Final Stats of the Match
        //GameGear.viewFinalStats(this);

        //Removing players from the match
        for (Player player : playerList) {
            player.setStatus(UserStatus.Online);
            player.setMatch(null);
            player.setShelf(null);
            synchronized (GameManager.playerMatchMap) {
                GameManager.playerMatchMap.remove(player.getNickname());
            }
        }
        //Maybe not necessary, at end match instance will be deleted
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
        sendMessageToAll(BB);

        //Initialization of the board
        board = new Board(this);
        if(board.firstSetup())
            sendMessageToAll(BB_Ok);
        else
            sendMessageToAll(BB_No);

        setGameState(GameState.Ready);
    }

    /**
     * Command to start the first round.
     * State: {@link GameState#GameGoing}
     */
    private void startFirstRound() {
        gameState = GameState.GameGoing;
        //System.out.println("Game > The match of ID: " + matchID + " is starting!");
        currentPlayer = chairman;
        //System.out.println("Match > Player Turn: " + currentPlayer.getNickname());
        setGamePhase(GamePhase.Selection);
        //TODO: test it
        VirtualViewHelper.buildVirtualView(this);
        for(Player p: playerList){
            try {
                p.getController().clientInput.callBack.notifyStart();
            } catch (RemoteException | ServerNotActiveException e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * This method set up the next turn
     */
    public void nextTurn() {
        currentPlayer = playerList.get((playerList.indexOf(currentPlayer) + 1) % maxSeats);
        setGamePhase(GamePhase.Selection);
    }

    /**
     * Get the JSON of the virtual view
     * @return the JSON of the virtual view
     */

    public String getVirtualView() {
        return VirtualViewHelper.getVirtualViewJSON(virtualView);
    }

    /**
     * notify all the players of the virtual view
     */
    public void notifyVirtualView() {

        for (Player p : playerList) {
            //TODO: Watch out for test
            if(p.getController().clientInput.callBack!=null){
                try {
                    p.getController().clientInput.callBack.sendVirtualView(getVirtualView());
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * This method will decide the absolute winner of the match
     * If multiple players have the same highest score, then there is no winner.
     */
    public void decideWinner(){
        Player tiePlayer=null;
        Player topPlayer=null;
        int maxScore=0;
        for(Player p: playerList){
            if(p.getPlayerScore()>maxScore){
                topPlayer = p;
                maxScore = p.getPlayerScore();
            }else if(p.getPlayerScore()==maxScore){
                tiePlayer=p;
            }
        }
        if(topPlayer!=null&&tiePlayer!=null&&tiePlayer.getPlayerScore()==topPlayer.getPlayerScore()){
            winner=null;
        }else{
            winner=topPlayer;
        }

    }
    public void sendMessageToAll(ServerMessage message){

        for(Player p:playerList){
            if(p.getController().clientInput.callBack!=null) {
                try {
                    p.getController().clientInput.callBack.sendMessageToClient(message.value());
                } catch (RemoteException e) {
                    throw new RuntimeException();
                }
            }

        }

    }

}