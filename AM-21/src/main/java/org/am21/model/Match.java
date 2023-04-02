package org.am21.model;

import org.am21.controller.GameController;
import org.am21.model.items.Bag;
import org.am21.model.items.Card.ItemTileCard;
import org.am21.model.items.Card.ScoringTokenCard;
import org.am21.model.items.Cell;
import org.am21.model.items.CommonGoal;
import org.am21.model.items.LivingRoomBoard;


import java.util.ArrayList;
import java.util.List;

public class Match {
    public int matchID;
    public List<CommonGoal> commonGoals;
    public boolean endGameToken;
    public GameController gameController;
    public LivingRoomBoard livingRoomBoard;
    public Bag bag;
    public int bagIndex;
    public GamePhases gamePhase;
    public TurnPhases turnPhase;
    public Player currentPlayer;
    public List<Player> playerList;
    public int maxSeats;
    private Player firstToComplete;
    private int numPlayers;
    public Player chairman;

    public Match(int maxSeats) {
        this.maxSeats = maxSeats;
        livingRoomBoard = new LivingRoomBoard(9, 9, maxSeats, this);
        playerList = new ArrayList<Player>(maxSeats);
        gamePhase = GamePhases.StartGame;
        commonGoals = new ArrayList<CommonGoal>(2);
    }

    public boolean addPlayer(Player player) {
        if (playerList.size() < maxSeats) {
            playerList.add(player);
            player.match = this;
            GameManager.playerMatchMap.put(player.getName(), matchID);

            if(playerList.size() == maxSeats) {
                matchStart();
            }

            return true;
        }
        return false;
    }

    public void matchStart() {
        gamePhase = GamePhases.GameOnGoing;
        turnPhase = TurnPhases.Selection;
        for (Player player : playerList) {
            GameManager.playerMatchMap.put(player.getName(), matchID);
        }
        currentPlayer = playerList.get((int) (Math.random() * maxSeats) - 1);
        bag = new Bag(this);
        bag.setItemCollection(maxSeats);
        fillBoard();
        //TODO:distribute cards to all players
        //CardUtil.buildItemTileCard();
    }

    private void fillBoard() {
        List<ItemTileCard> itemTileCards = bag.getItemCollection();
        for (Cell[] cells : livingRoomBoard.getCells()) {
            for (Cell cell : cells) {
                if (cell.isDark() || cell.getItemTileCard() != null) {
                    continue;
                }
                cell.setItemTileCard(itemTileCards.get(bagIndex));
                bagIndex++;
            }
        }
    }

    public void drawCard() {
    }

    public void nextTurn() {
    }

    public void giveToken(Player player, ScoringTokenCard scoringToken) {
    }

    public boolean isEndGame() {
        return false;
    }

    public void checkLastRound() {
    }

    /**
     * This method is called at end of a player's turn
     *
     * It needs to check if the board has all the item isolated
     * @return
     */
    public void endTurnActions(){

        //TODO: I dunno, we will figure it out.



        if(livingRoomBoard.isSingle()){
            //Refill board
            bag.refillRequest();
        }


    }


}
