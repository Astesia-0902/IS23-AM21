package org.am21.model;

import org.am21.model.Cards.CommonGoal;
import org.am21.model.Cards.PersonalGoalCard;
import org.am21.model.enumer.GamePhases;
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
    public GameState gamePhase;
    public GamePhases turnPhase;
    public Player currentPlayer;
    public List<Player> playerList;
    public int maxSeats;
    private Player firstToComplete;

    public Player chairman;
    public MyTimer timer;

    public Match(int maxSeats) {
        this.maxSeats = maxSeats;
        playerList = new ArrayList<>(maxSeats);
        gamePhase = GameState.WaitingPlayers;
        commonGoals = new ArrayList<>(2);
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
        if (playerList.size() < maxSeats) {
            playerList.add(player);
            player.setStatus(UserStatus.GameMember);
            player.setMatch(this);
            player.setShelf(new Shelf(player));

            synchronized (GameManager.playerMatchMap) {
                GameManager.playerMatchMap.put(player.getNickname(), matchID);
            }

            if (playerList.size() == maxSeats) {
                initializeMatch();
            }

            //System.out.println("Game > " + player.getNickname() + " added to the match N." + this.matchID);

            return true;
        }
        return false;
    }

    /**
     * This should be just the initialization of the Match:
     * Building board, Bag, cards, choosing chairman -> {@link GameState#GameGoing }
     * <p>
     * At the end,it will call another method to start the first Round:
     * Declare who is player turn
     * And setting Turn Phase
     */
    public void initializeMatch() {
        if (playerList.size() < maxSeats) {
            //System.out.println("Game > Not enough players to begin. Keep waiting...");
            return;
        }
        //Determine the first player
        chairman = playerList.get((int) (Math.random() * maxSeats));
        currentPlayer = chairman;

        //Distribution of Personal Goals
        List<PersonalGoalCard> personalGoalCards = CardUtil.buildPersonalGoalCard(maxSeats);
        for (int i = 0; i < maxSeats; i++) {
            //Give player's reference to the card
            personalGoalCards.get(i).player=playerList.get(i);
            //Give players their Personal Goal
            playerList.get(i).setMyPersonalGoal(personalGoalCards.get(i));
        }

        //Determine the common goals
        commonGoals = CommonGoalUtil.getCommonGoals(maxSeats);

        //Register the players in the playerMatchMap
        for (Player player : playerList) {
            GameManager.playerMatchMap.put(player.getNickname(), matchID);
        }

        //Initialization of the board
        board = new Board(this);
        board.firstSetup();

        //System.out.println("Game > The match of ID: " + matchID + " is starting!");
        startFirstRound();
    }

    /**
     *
     */
    private void startFirstRound() {
        //Initialize the game phase
        gamePhase = GameState.GameGoing;
//        System.out.println("Match > Player Turn: " + currentPlayer.getName());
        changeTurnPhase(GamePhases.Selection);

        //Start the timer
        timer = new MyTimer();
        timer.startTimer(30, this);
    }

    /**
     *
     */
    public void nextTurn() {
        currentPlayer = playerList.get((playerList.indexOf(currentPlayer) + 1) % maxSeats);
//        System.out.println("Match > Player Turn: " + currentPlayer.getName());

        timer = new MyTimer();
        timer.startTimer(30, this);

        changeTurnPhase(GamePhases.Selection);
    }


    /**
     * @param endGameToken
     */
    public void setEndGameToken(boolean endGameToken) {

        this.endGameToken = endGameToken;
    }

    /**
     * @return
     */
    public boolean checkLastRound() {
        if (gamePhase == GameState.LastRound) {
            if (playerList.get((playerList.indexOf(currentPlayer) + 1) % maxSeats) == firstToComplete) {
//                System.out.println("Match > GAME OVER");
                return true;
            }
        }
        return false;
    }

    /**
     * @return
     */
    public Player getFirstToComplete() {
        return firstToComplete;
    }


    /**
     * Change TurnPhase
     *
     * @param phase
     */
    public void changeTurnPhase(GamePhases phase) {
        turnPhase = phase;
//        System.out.println("Match [!] > { " + turnPhase + " Phase }");
    }

    /**
     * This method check if the player has completed any Goal
     *
     * @param player
     */
    public void checkingGoals(Player player) {
        //Serie di comandi per controllare se il player ha completato dei goal
        player.getMyPersonalGoal().calculatePoints();

        for (CommonGoal goal : commonGoals) {
            if (goal.checkGoal(player.getShelf())) {
                // Give player points/scoreToken
                //player.playerScore += goal.extractToken();
                goal.setAchievedPlayers(player);

            }
        }

        changeTurnPhase(GamePhases.EndTurn);
        this.callEndTurnRoutine();
    }


    /**
     *
     */
    private void callEndTurnRoutine() {
        //Check if last round is completed
        if (checkLastRound()) {
            endMatch();
        }
        if (board.checkBoard()) {
//            System.out.println("Match > Board need refill");
            TGear.printThisBoard(board);

            //refill
            if (!board.bag.refillBoard()) {
//                System.out.println("Match > Board not refilled");
            } else {
                TGear.printThisBoard(board);
            }
        }

        if (currentPlayer.getShelf().getTotSlotAvail() == 0 && gamePhase != GameState.LastRound) {
//            System.out.println("Match > Congratulations! " + currentPlayer.getName() + " has completed the shelf first");
            this.setEndGameToken(false);
//            System.out.println("Match > EndGame Token assigned");
            firstToComplete = currentPlayer;
            gamePhase = GameState.LastRound;
        }

        this.nextTurn();


    }

    /**
     *
     */
    private void endMatch() {
//        System.out.println("Game > Room closed. See ya!");

        //temp
        TGear.viewStats(this, -2);
        System.exit(100);
    }

    /**
     * @param player
     * @return
     */
    public boolean removePlayer(Player player) {
        return false;
    }
}