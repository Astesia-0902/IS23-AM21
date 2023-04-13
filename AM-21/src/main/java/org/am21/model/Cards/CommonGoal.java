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
    public boolean checkGoal(Shelf shelf) {


        return false;
    }

    /**
     * When a CommonGoal is achieved,
     * the player is going to be added to the AchievedPlayers List
     * A player can achieve the goal just one time.
     *
     * @param player
     */
    public void setAchievedPlayers(Player player) {
        this.achievedPlayers.add(player);
        player.setPlayerScore(player.getPlayerScore()+this.tokenStack.get(index));
        this.tokenStack.remove(index);
        index++;
    }

//    public int extractToken(){
//        return tokenStack.get(index++);
//
//    }
}



