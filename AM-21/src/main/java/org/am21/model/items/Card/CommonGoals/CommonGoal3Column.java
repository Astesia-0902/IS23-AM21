package org.am21.model.items.Card.CommonGoals;

import org.am21.model.Player;
import org.am21.model.items.Card.ScoringTokenCard;
import org.am21.model.items.CommonGoal;
import org.am21.model.items.Shelf;

import java.util.List;

public class CommonGoal3Column extends CommonGoal {
    private int id;
    public List<ScoringTokenCard> tokenStack;

    public List<Player> achievedPlayers;



    public CommonGoal3Column(){};

    /**
     * Durante la fase del turno CheckingGoal, viene controllato la Shelf del Player
     * per verificare se l'obiettivo è stato completato
     *
     *
     *Uso un int per contare il numero di colonne che soddisfano la condizione
     * Itero ogni colonna. Per ogni colonna controlliamo tutte le celle e creiamo una variabile int
     * che conterà ItemType diversi che incontriamo nella colonna.
     * Quando int supera 3 allora non soddisfa la condizione e passiamo alla prossima colonna
     *
     * Infine se la variabile che conta le colonne che soddisfano la condizione è 3 allora goal è completato
     *
     *
     *
     *
     *
     * @param shelf
     * @return
     */
    public boolean checkGoal(Shelf shelf) {
        return false;
    }
}
