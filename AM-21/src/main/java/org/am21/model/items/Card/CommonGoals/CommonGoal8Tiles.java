package org.am21.model.items.Card.CommonGoals;

import org.am21.model.Player;
import org.am21.model.items.Card.ScoringTokenCard;
import org.am21.model.items.CommonGoal;
import org.am21.model.items.Shelf;

import java.util.List;

public class CommonGoal8Tiles extends CommonGoal {
    private int id;
    public List<ScoringTokenCard> tokenStack;

    public List<Player> achievedPlayers;



    public CommonGoal8Tiles(){};

    /**
     * Durante la fase del turno CheckingGoal, viene controllato la Shelf del Player
     * per verificare se l'obiettivo è stato completato
     *
     * Iteriamo la shelf per ogni tipo di Item.
     * Abbiamo Variabile int per contare il numero di tessere uguali, che verrà resettata ogni volta
     * che si passa al prossimo ItemType.
     * Scansioniamo la totalità delle celle e ogni volta che incontriamo un Item del tipo corrispondente
     * la variabile contatore viene incrementato.
     * Se la var raggiunge 8 alemno una volta allora il goal è completato.
     *
     * @param shelf
     * @return
     */
    public boolean checkGoal(Shelf shelf) {
        return false;
    }
}
