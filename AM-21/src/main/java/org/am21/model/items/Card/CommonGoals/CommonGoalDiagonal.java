package org.am21.model.items.Card.CommonGoals;

import org.am21.model.items.CommonGoal;
import org.am21.model.items.Shelf;

public class CommonGoalDiagonal extends CommonGoal {
    private static final int rowNumShelf = 2;

    public CommonGoalDiagonal(int numPlayer) {
        super("CommonGoalDiagonal",numPlayer);
    }

    @Override
    public boolean checkGoal(Shelf shelf) {
        /**
         * Scan the shelf to find five tiles of the same type forming a diagonal.
         * @param shelf
         * @return
         */

        // The game can only be composed of the following lists::
        //  (0,0), (1,1), (2,2), (3,3), (4,4)
        //  (1,0), (2,1), (3,2), (4,3), (5,4)
        for (int row = 0; row < rowNumShelf; row++) {
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
