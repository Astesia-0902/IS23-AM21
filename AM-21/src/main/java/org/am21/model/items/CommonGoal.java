package org.am21.model.items;

import org.am21.model.Player;
import org.am21.model.items.Card.ScoringTokenCard;

import javax.swing.text.PlainDocument;
import java.util.List;

public abstract class CommonGoal extends Goal {
    private int id;
    public List<ScoringTokenCard> tokenStack;

    public List<Player> achievedPlayers;



    public CommonGoal(){};

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
}
