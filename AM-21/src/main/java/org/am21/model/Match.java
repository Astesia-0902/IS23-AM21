package org.am21.model;

import org.am21.controller.GameController;
import org.am21.model.items.Bag;
import org.am21.model.items.CommonGoal;
import org.am21.model.items.LivingRoomBoard;

import java.util.List;

public class Match {
    public int matchID;
    public List<CommonGoal> commonGoals;
    public PlayerManager playerManager;
    public GameController gameController;
    public LivingRoomBoard livingRoomBoard;
    public Bag bag;

    public Match(){
        playerManager = new PlayerManager(this);    //edit(ken)

        livingRoomBoard = new LivingRoomBoard();
        bag = new Bag();
    }

    public void initializeMatch(int playerCount){

    }
}
