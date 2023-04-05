package org.am21.model.Cards;

import org.am21.model.Player;
import org.am21.model.items.Shelf;
import org.am21.utilities.CardUtil;

import java.util.List;

public abstract class CommonGoal extends Goal {
    public List<Integer> tokenStack;
    public List<Player> achievedPlayers;
    public int index;

    public CommonGoal(String nameCard,int numPlayer){
        super(nameCard);
        this.tokenStack = CardUtil.buildScoringTokenCards(numPlayer);
        this.index = 0;
    }

    public boolean checkGoal(Shelf shelf) {
        /**
         * Durante la fase del turno CheckingGoal, viene controllato la Shelf del Player
         * per verificare se l'obiettivo è stato completato
         *
         * @param shelf
         * @return
         */

        return false;
    }

    public void setAchievedPlayers(Player player) {
        this.achievedPlayers.add(player);
        player.playerScore += this.tokenStack.get(index);
        this.tokenStack.remove(index);

        index++;
    }
}



