package org.am21.model;

import org.am21.controller.GameController;
import org.am21.model.Cards.CommonGoal;
import org.am21.model.Cards.PersonalGoalCard;
import org.am21.model.enumer.GameState;
import org.am21.model.enumer.TurnPhases;
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
    public GameController gameController;
    public Board board;
    public GameState gamePhase;
    public TurnPhases turnPhase;
    public Player currentPlayer;
    public List<Player> playerList;
    public int maxSeats;
    private Player firstToComplete;

    public Player chairman;
    public MyTimer timer;

    public Match(int maxSeats) {
        this.maxSeats = maxSeats;
        playerList = new ArrayList<Player>(maxSeats);
        gamePhase = GameState.WaitingPlayers;
        commonGoals = new ArrayList<CommonGoal>(2);
    }

    /**
     * Add player to this match
     *
     * @param player
     * @return
     */
    public boolean addPlayer(Player player) {
        if (playerList.size() < maxSeats) {
            playerList.add(player);
            player.status = UserStatus.GameMember;
//            System.out.println("Game > " + player.getName() + " added to the match");
            player.match = this;
            player.createHand();
            player.shelf = new Shelf(player);

            synchronized (GameManager.playerMatchMap) {
                GameManager.playerMatchMap.put(player.getName(), matchID);
            }

            if (playerList.size() == maxSeats) {
                matchStart();
            }

            return true;
        }
        return false;
    }

    /**
     * This should be just the initialization of the Match:
     * Building board, Bag, cards, choosing chairman
     * -> StartGame phase
     * <p>
     * At the end,it will call another method to start the first Round:
     * Declare who is player turn
     * And setting Turn Phase
     */
    public void matchStart() {
        if (playerList.size() < maxSeats) {
//            System.out.println("Game > Not enough players to begin. Keep waiting...");
            return;
        }
//        System.out.println("-------------------------");
//        System.out.println("Game > The match is starting!");
//        System.out.println("Match[!] > Let's play!");
        //Determine the first player
        chairman = playerList.get((int) (Math.random() * maxSeats));
//        System.out.println("Match > " + chairman.getName() + " get the Chair!");
        currentPlayer = chairman;

        //Distribution of personal goals
        List<PersonalGoalCard> personalGoalCards = CardUtil.buildPersonalGoalCard(maxSeats);
        for (int i = 0; i < maxSeats; i++) {
            playerList.get(i).setOwnGoal(personalGoalCards.get(i));
            personalGoalCards.get(i).setPlayer(playerList.get(i));
        }

        //Determine the common goals
        commonGoals = CommonGoalUtil.getCommonGoals(maxSeats);
        for (Player player : playerList) {
            GameManager.playerMatchMap.put(player.getName(), matchID);
        }

        //Initialization of the board
        //bag = new Bag(this);
        //bag.setItemCollection(maxSeats);
        board = new Board(this);
        board.setupBoard();

        startFirstRound();
    }

    /**
     *
     */
    private void startFirstRound() {

        //Initialize the game phase
        gamePhase = GameState.GameGoing;
//        System.out.println("Match > Player Turn: " + currentPlayer.getName());
        changeTurnPhase(TurnPhases.Selection);

        //Start the timer
        timer = new MyTimer();
        timer.startTimer(5, this);
    }

    /**
     *
     */
    public void nextTurn() {
        currentPlayer = playerList.get((playerList.indexOf(currentPlayer) + 1) % maxSeats);
//        System.out.println("Match > Player Turn: " + currentPlayer.getName());

        timer = new MyTimer();
        timer.startTimer(2, this);

        changeTurnPhase(TurnPhases.Selection);
    }



    /**
     *
     * @param endGameToken
     */
    public void setEndGameToken(boolean endGameToken) {
        this.endGameToken = endGameToken;
    }

    /**
     *
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
     *
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
    public void changeTurnPhase(TurnPhases phase) {
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

        for(CommonGoal goal : commonGoals){
            if(goal.checkGoal(player.shelf)){
                // Give player points/scoreToken
                player.playerScore += goal.extractToken();
                goal.setAchievedPlayers(player);

            }
        }

        changeTurnPhase(TurnPhases.EndTurn);
        this.callEndTurnRoutine();
    }


    /**
     *
     */
    private void callEndTurnRoutine() {
        if (board.checkBoard()) {
//            System.out.println("Match > Board need refill");
            TGear.printThisBoard(board);

            //refill
            if (!board.bag.refillRequest()) {
//                System.out.println("Match > Board not refilled");
            } else {
                TGear.printThisBoard(board);
            }
        }
        if (checkLastRound()) {
            endMatch();
        }
        if (currentPlayer.shelf.getTotSlotAvail() == 0 && gamePhase != GameState.LastRound) {
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
     *
     * @param player
     * @return
     */
    public boolean removePlayer(Player player) {
        return false;
    }
}