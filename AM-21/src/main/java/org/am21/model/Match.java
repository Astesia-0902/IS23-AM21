package org.am21.model;

import org.am21.model.Cards.CommonGoal;
import org.am21.model.Cards.PersonalGoalCard;
import org.am21.model.enumer.GamePhase;
import org.am21.model.enumer.GameState;
import org.am21.model.enumer.UserStatus;
import org.am21.model.items.Board;
import org.am21.model.items.Shelf;
import org.am21.utilities.CardUtil;
import org.am21.utilities.CommonGoalUtil;
import org.am21.utilities.MyTimer;
import org.am21.utilities.TGear;

import java.util.ArrayList;
import java.util.List;

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

    public Player chairman;
    public MyTimer timer;

    public Match(int maxSeats) {
        this.maxSeats = maxSeats;
        playerList = new ArrayList<>(maxSeats);
        gameState = GameState.WaitingPlayers;
        commonGoals = new ArrayList<>(2);
    }

    /**
     * @param endGameToken
     */
    public void setEndGameToken(boolean endGameToken) {

        this.endGameToken = endGameToken;
    }
    public Player getFirstToComplete() {
        return firstToComplete;
    }
    /**
     * Change GamePhase
     * @param phase
     */
    public void setGamePhase(GamePhase phase) {
        gamePhase = phase;
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
                playerList.add(player);
                player.setStatus(UserStatus.GameMember);
                player.setMatch(this);
                player.setPlayerScore(0);
                player.setShelf(new Shelf(player));

                synchronized (GameManager.playerMatchMap) {
                    GameManager.playerMatchMap.put(player.getNickname(), matchID);
                }
                //System.out.println("Game > " + player.getNickname() + " added to the match N." + this.matchID);
                checkRoom();
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
            if(playerList.contains(player)){
                playerList.remove(player);
                player.setPlayerScore(0);
                player.setStatus(UserStatus.Online);
                player.setMatch(null);
                player.setShelf(null);
                synchronized (GameManager.playerMatchMap) {
                    GameManager.playerMatchMap.remove(player.getNickname());
                }
                checkRoom();
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
                playerList.get((playerList.indexOf(currentPlayer) + 1) % maxSeats) == firstToComplete)
        {
            endMatch();
        }else {
            if (board.checkBoard()) {
//            System.out.println("Match > Board need refill");
                TGear.printThisBoard(board);
                //refill
                if (board.bag.refillBoard()) {
                    TGear.printThisBoard(board);
                }
            }

            if (currentPlayer.getShelf().getTotSlotAvail() == 0 && gameState != GameState.LastRound) {
//            System.out.println("Match > Congratulations! " + currentPlayer.getName() + " has completed the shelf first");
                this.setEndGameToken(false);
//            System.out.println("Match > EndGame Token assigned");
                firstToComplete = currentPlayer;
                gameState = GameState.LastRound;
            }
            this.nextTurn();
        }

    }
    /**
     * This method check if the player has completed any Goal
     * @param player player that need to check
     */
    public void checkingCommonGoals(Player player) {
        //Serie di comandi per controllare se il player ha completato dei goal
        player.getMyPersonalGoal().calculatePoints();

        for (CommonGoal goal : commonGoals) {
            if (goal.checkGoal(player.getShelf())) {
                // Give player points/scoreToken
                //player.playerScore += goal.extractToken();
                goal.setAchievedPlayers(player);

            }
        }
        setGamePhase(GamePhase.EndTurn);
        this.callEndTurnRoutine();
    }

    /**
     *This method is called when the match ends.
     * Operations:
     * - Show the final game stats.<br>
     * - Clear the board and the items.<br>
     * - Reset players score<br>
     * - Remove all the players from the match<br>
     */
    private boolean endMatch() {
        //TODO: final game stats
        //TODO: clear board
        //Removing players from the match
        for(Player player : playerList){
            player.setStatus(UserStatus.Online);
            player.setMatch(null);
            player.setShelf(null);
            synchronized (GameManager.playerMatchMap){
                GameManager.playerMatchMap.remove(player.getNickname());
            }
        }
        //Maybe not necessary, at end match instance will deleted
        playerList.clear();

        //System.out.println("Game > Room closed. See ya!");
        //temp
        //TGear.viewStats(this, -2);

        gameState=GameState.Closed;
        return true;
    }

    /**
     * This method is called whenever there is a risk that the players number changes.
     * If there isn't enough players to continue the game it calls {@link #endMatch()}.
     * Otherwise:
     * If the state is {@link GameState#WaitingPlayers}: it calls {@link #initializeMatch()}
     * If it is {@link GameState#Ready}: it calls {@link #startFirstRound()}
     */
    public void checkRoom(){
        if(playerList.size()<maxSeats){
            if(!gameState.equals(GameState.WaitingPlayers)){
                endMatch();
            }
        }else {
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
    public void initializeMatch() {
        //Change GameState to GameGoing, from now on if a player leaves, then the match ends
        gameState = GameState.GameGoing;

        //Determine the chairman player
        chairman = playerList.get((int) (Math.random() * maxSeats));

        //Distribution of Personal Goals
        List<PersonalGoalCard> personalGoalCards = CardUtil.buildPersonalGoalCard(maxSeats);
        for (int i = 0; i < maxSeats; i++) {
            //Give player's reference to the card
            personalGoalCards.get(i).player=playerList.get(i);
            //Give players their Personal Goal
            playerList.get(i).setMyPersonalGoal(personalGoalCards.get(i));
        }

        //Draw 2 randomly Common Goals
        commonGoals = CommonGoalUtil.getCommonGoals(maxSeats);

        //Register the players in the playerMatchMap
        for (Player player : playerList) {
            GameManager.playerMatchMap.put(player.getNickname(), matchID);
        }

        //Initialization of the board
        board = new Board(this);
        board.firstSetup();

    }

    /**
     * Command to start the first round.
     * State: {@link GameState#GameGoing}
     */
    private boolean startFirstRound() {
        gameState=GameState.GameGoing;
        //System.out.println("Game > The match of ID: " + matchID + " is starting!");
        currentPlayer = chairman;
        //System.out.println("Match > Player Turn: " + currentPlayer.getNickname());
        setGamePhase(GamePhase.Selection);
        //Start the timer
        timer = new MyTimer();
        timer.startTimer(30, this);
        return true;
    }

    /**
     * This method set up the next turn
     */
    public void nextTurn() {
        currentPlayer = playerList.get((playerList.indexOf(currentPlayer) + 1) % maxSeats);
        //System.out.println("Match > Player Turn: " + currentPlayer.getNickname());

        timer = new MyTimer();
        timer.startTimer(30, this);

        setGamePhase(GamePhase.Selection);

    }
}