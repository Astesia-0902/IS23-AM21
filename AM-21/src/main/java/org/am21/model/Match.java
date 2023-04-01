package org.am21.model;

import org.am21.controller.GameController;
import org.am21.model.items.Bag;
import org.am21.model.items.Card.ScoringTokenCard;
import org.am21.model.items.CommonGoal;
import org.am21.model.items.LivingRoomBoard;
import org.am21.util.CardUtil;


import java.util.ArrayList;
import java.util.List;

public class Match {
    public int matchID;
    public List<CommonGoal> commonGoals;
    public boolean endGameToken;
    public PlayerManager playerManager;
    public GameController gameController;
    public LivingRoomBoard livingRoomBoard;
    public Bag bag;
    public GamePhases gamePhase;
    public TurnPhases turnPhase;
    public Player currentPlayer;
    public List<Player> playerList;
    public int maxSeats;

    public Match(int maxSeats) {
        this.maxSeats = maxSeats;
        playerManager = new PlayerManager(this);
        bag = new Bag(this);
        livingRoomBoard = new LivingRoomBoard(9, 9, maxSeats, this);
        playerList = new ArrayList<Player>(maxSeats);
        gamePhase = GamePhases.StartGame;
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

        //TODO:distribute cards to all players
        //CardUtil.buildItemTileCard();
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
