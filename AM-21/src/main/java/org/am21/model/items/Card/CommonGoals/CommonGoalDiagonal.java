package org.am21.model.items.Card.CommonGoals;

import org.am21.model.Player;
import org.am21.model.items.Card.ScoringTokenCard;
import org.am21.model.items.CommonGoal;
import org.am21.model.items.Shelf;

import java.util.List;

public class CommonGoalDiagonal extends CommonGoal {
    private int id;
    public List<ScoringTokenCard> tokenStack;

    public List<Player> achievedPlayers;



    public CommonGoalDiagonal(){};

    /**
     * Durante la fase del turno CheckingGoal, viene controllato la Shelf del Player
     * per verificare se l'obiettivo è stato completato
     *
     * Ci sono 4 pseudo-diagonali da controllare.
     * Per accedere alla diagonale usiamo le coordinate (R,C)
     * 1--> (1,1)
     * 2--> (2,1)
     * 3--> (1,5)
     * 4--> (2,5)
     * Partiamo dalla prima cella e l'iterazione avviene aggiungendo entrambe le coordinate +1
     *
     * Dalla prima cella di ogni diagonale(resettabile) viene salvato il ItemType e per le successive caselle
     * viene controllato se gli item hanno il Type uguale.
     * Quando incontriamo un type diverso passiamo alla prossima diagonale.
     *
     *
     * @param shelf
     * @return
     */
    public boolean checkGoal(Shelf shelf) {
        return false;
    }
}
