package org.am21.model.items;

import org.am21.model.Player;
import org.am21.model.items.Card.ScoringTokenCard;
import org.am21.utilities.CardUtil;

import java.util.List;

public abstract class CommonGoal extends Goal {
    private int id;
    private String name;
    public List<ScoringTokenCard> tokenStack;
    public List<Player> achievedPlayers;
    public int index;

    /*
    public CommonGoal(int id, List<Player> achievedPlayers) {
        super();
        this.id = id;
        this.achievedPlayers = achievedPlayers;
        this.tokenStack = new ArrayList<>();
    }*/

    public CommonGoal(String name, int numPlayer){
        super(name);
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

    public int getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public void setAchievedPlayers(Player player) {
        this.achievedPlayers.add(player);
        player.playerScore += this.tokenStack.get(index).getScoreValue();
        this.tokenStack.remove(index);

        index++;
    }
}
