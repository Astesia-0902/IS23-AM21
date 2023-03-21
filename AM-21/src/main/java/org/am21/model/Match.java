package org.am21.model;

import org.am21.controller.GameController;
import org.am21.model.items.Bag;
import org.am21.model.items.Card.ScoringTokenCard;
import org.am21.model.items.CommonGoal;
import org.am21.model.items.LivingRoomBoard;

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


    public Match(int maxSeats){
        playerManager = new PlayerManager(this);
        livingRoomBoard = new LivingRoomBoard(5,6);
        bag = new Bag();
    }

    public void initializeMatch(int playerNum){}

    public void drawCard(){
    }

    public void nextTurn(){}

    public void giveToken(Player player, ScoringTokenCard scoringToken){}

    public boolean isEndGame(){
        return false;
    }

    public void checkLastRound(){}


    
}
