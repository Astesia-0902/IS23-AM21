package org.am21.model.Card.CommonGoals;

import org.am21.model.Card.ItemType;
import org.am21.model.Cell;
import org.am21.model.Card.CommonGoal;
import org.am21.model.Shelf;

import java.util.HashSet;
import java.util.Set;

public class CommonGoal3Column extends CommonGoal {


    public CommonGoal3Column(int numPlayer) {
        super("CommonGoal3Column",numPlayer);
    }

    /**
     * Durante la fase del turno CheckingGoal, viene controllato la Shelf del Player
     * per verificare se l'obiettivo è stato completato
     * <p>
     * <p>
     * Uso un int per contare il numero di colonne che soddisfano la condizione
     * Itero ogni colonna. Per ogni colonna controlliamo tutte le celle e creiamo una variabile int
     * che conterà ItemType diversi che incontriamo nella colonna.
     * Quando int supera 3 allora non soddisfa la condizione e passiamo alla prossima colonna
     * <p>
     * Infine se la variabile che conta le colonne che soddisfano la condizione è 3 allora goal è completato
     *
     * @param pShelf
     * @return
     */
    @Override
    public boolean checkGoal(Shelf pShelf) {
        int win_col = 0;
        Cell[][] tmpCell = pShelf.getCellGrid();
        Set<ItemType> reg = new HashSet<>();
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 6; i++) {
                //Scorrimento in verticale
                if (reg.contains(tmpCell[i][j].getItem().getNameCard())) {

                }


            }
        }


        return false;
    }
}
