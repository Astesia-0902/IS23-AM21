package org.am21.model;

import org.am21.controller.GameController;
import org.am21.model.items.Bag;
import org.am21.model.items.Card.PersonalGoalCard;
import org.am21.model.items.Card.ScoringTokenCard;
import org.am21.model.items.CommonGoal;
import org.am21.model.items.LivingRoomBoard;
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
    public boolean endGameToken;
    public GameController gameController;
    public LivingRoomBoard livingRoomBoard;
    public Bag bag;
    //public int bagIndex;
    public GamePhases gamePhase;
    public TurnPhases turnPhase;
    public Player currentPlayer;
    public List<Player> playerList;
    public int maxSeats;
    private Player firstToComplete;
    private int numPlayers;
    public Player chairman;
    public MyTimer timer;

    public Match(int maxSeats) {
        this.maxSeats = maxSeats;
        playerList = new ArrayList<Player>(maxSeats);
        gamePhase = GamePhases.WaitingPlayers;
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
            System.out.println("Game > "+player.getName()+" added to the match");
            player.match = this;
            player.createHand();
            player.shelf = new Shelf(player);

            GameManager.playerMatchMap.put(player.getName(), matchID);

            if (playerList.size() == maxSeats) {
                matchStart();
            }

            return true;
        }
        return false;
    }

    public void matchStart() {
        if(playerList.size()<maxSeats) {
            System.out.println("Game > Not enough players to begin. Keep waiting...");
            return;
        }
        this.gamePhase=GamePhases.StartGame;
        System.out.println("-------------------------");
        System.out.println("Game > The match is starting!");
        System.out.println("Match[!] > Let's play!");
            //Determine the first player
            chairman = playerList.get((int) (Math.random() * maxSeats));
            System.out.println("Match > "+chairman.getName() + " get the Chair!");
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
            bag = new Bag(this);
            //bag.setItemCollection(maxSeats);
            livingRoomBoard = new LivingRoomBoard(9, 9, maxSeats, this);

            //Start the timer
            timer = new MyTimer();
            timer.startTimer(5, this);

            //Initialize the game phase
            gamePhase = GamePhases.GameOnGoing;
            changeTurnPhase(TurnPhases.Selection);

    }

//    private void fillBoard() {
//        List<ItemTileCard> itemTileCards = bag.getItemCollection();
//        for (Cell[] cells : livingRoomBoard.getCellGrid()) {
//            for (Cell cell : cells) {
//                if (cell.isDark() || cell.getItem() != null) {
//                    continue;
//                }
//                cell.setItem(itemTileCards.get(bagIndex));
//                bagIndex++;
//            }
//        }
//    }

//    public void drawCard() {
//    }

    public void nextTurn() {
        currentPlayer = playerList.get((playerList.indexOf(currentPlayer) + 1) % maxSeats);
        System.out.println("Match > Player Turn: " + currentPlayer.getName());

        timer = new MyTimer();
        timer.startTimer(2,this);

        changeTurnPhase(TurnPhases.Selection);
    }

    public void giveToken(Player player, ScoringTokenCard scoringToken) {
    }

    public boolean isEndGame() {
        return false;
    }

    public void checkLastRound() {
    }



    /**
     * @return which TurnPhase is
     */
    public TurnPhases whichTurnPhase() {
        return turnPhase;
    }

    /**
     * Change TurnPhase
     *
     * @param phase
     */
    public void changeTurnPhase(TurnPhases phase) {
        turnPhase = phase;
        System.out.println("Match [!] > { " + turnPhase + " Phase }");
    }


    public void checkingGoals(Player player) {
        //Serie di comandi per controllare se il player ha completato dei goal


        changeTurnPhase(TurnPhases.EndTurn);
        this.callEndTurnRoutine();
    }

    private void callEndTurnRoutine() {
        if(livingRoomBoard.checkBoard()){
           System.out.println("Match > Board need refill");
           TGear.printThisBoard(livingRoomBoard);
           //refill
            if(!this.bag.refillRequest()){
                System.out.println("Match > Board not refilled");
            }else{
                TGear.printThisBoard(livingRoomBoard);
            }
        }
        if(currentPlayer.shelf.getTotSlotAvail()==0 && gamePhase!=GamePhases.LastRound){
            System.out.println("Match > Congratulations! "+currentPlayer.getName()+" has completed the shelf first");
            System.out.println("Match > EndGame Token assigned");
            gamePhase = GamePhases.LastRound;
        }

        this.nextTurn();


    }
}
