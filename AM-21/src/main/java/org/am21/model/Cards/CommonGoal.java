package org.am21.model.Cards;

import org.am21.model.Player;
import org.am21.model.items.Shelf;
import org.am21.utilities.CardUtil;

import java.util.ArrayList;
import java.util.List;


public abstract class CommonGoal extends Goal {
    public List<Integer> tokenStack;
    public List<Player> achievedPlayers;
    public int index;

    public CommonGoal(String nameCard,int numPlayer){
        super(nameCard);
        this.tokenStack = CardUtil.buildScoringTokenCards(numPlayer);
        this.index = 0;
        achievedPlayers = new ArrayList<>();
    }

    /**
     * Durante la fase del turno CheckingGoal, viene controllato la Shelf del Player
     * per verificare se l'obiettivo è stato completato
     *
     * @param shelf
     * @return
     */
    public abstract boolean checkGoal(Shelf shelf);



    /**
     * When a CommonGoal is achieved,
     * the player is going to be added to the AchievedPlayers List
     * A player can achieve the goal just one time, so if, for any reasons, it's achieved again, nothing happens
     * @param player player who has achieved Common Goal
     */
    public void commonGoalAchieved(Player player) {
        if(!achievedPlayers.contains(player)&&tokenStack.size()>0) {
            achievedPlayers.add(player);
            player.getController().addScore(tokenStack.get(0));
            tokenStack.remove(0);
        }
    }

}



