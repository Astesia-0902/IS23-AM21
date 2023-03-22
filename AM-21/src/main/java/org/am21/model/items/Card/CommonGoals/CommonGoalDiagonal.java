package org.am21.model.items.Card.CommonGoals;

import org.am21.model.items.CommonGoal;
import org.am21.model.items.Shelf;

public class CommonGoalDiagonal extends CommonGoal {
    @Override
    public boolean checkGoal(Shelf shelf) {
        /**
         * Scan the shelf to find five tiles of the same type forming a diagonal.
         * @param shelf
         * @return
         */

        for (int row = 0; row < 2; row++) {
            String tile = shelf.getItemName(row, 0);
            if (tile != null && tile.equals(shelf.getItemName(row+1, 1))
                    && tile.equals(shelf.getItemName(row+2,2))
                    && tile.equals(shelf.getItemName(row+3,3))
                    && tile.equals(shelf.getItemName(row+4,4))){
                return true;
            }
        }
        return false;
    }
}