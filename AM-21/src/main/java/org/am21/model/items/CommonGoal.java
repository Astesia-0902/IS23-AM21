package org.am21.model.items;

import org.am21.model.Player;
import org.am21.model.items.Card.ScoringTokenCard;

import java.util.ArrayList;
import java.util.List;

public abstract class CommonGoal extends Goal {
    private int id;
    public List<ScoringTokenCard> tokenStack;
    public List<Player> achievedPlayers;

    public CommonGoal(int id, List<Player> achievedPlayers) {
        this.id = id;
        this.achievedPlayers = achievedPlayers;
        this.tokenStack = new ArrayList<>();
    }

    public CommonGoal(){}

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
}
