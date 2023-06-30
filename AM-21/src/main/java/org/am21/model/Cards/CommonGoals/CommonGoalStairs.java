package org.am21.model.Cards.CommonGoals;

import org.am21.model.Cards.CommonGoal;
import org.am21.model.items.Shelf;

/**
 * Five columns of increasing or decreasing height.
 * Starting from the first column on the left or on the right,
 * each next column must be made of exactly one more tile.
 * Tiles can be of any type.
 */
public class CommonGoalStairs extends CommonGoal {

    /**
     * Constructor
     *
     * @param numPlayer the number of players
     */
    public CommonGoalStairs(int numPlayer) {

        super("CommonGoalStairs", numPlayer);
    }

    /**
     * public CommonGoalStairs(){
     * <p>
     * <p>
     * check in 2 mode:
     * 1. since the first column: record the number of item (5 o 6),
     * then followed the rest column ordinary:
     * second column (4 o 5), third (3 o 4), forth (2 o 3), last (1 o 2)
     * the check fall when one of the request column is superior the number max.
     * 2. the same mode but with reverse ordinary
     * <p>
     * the check past when all of 5 column is matched: (necessary one of the sequence mode)
     * first mode:  6,5,4,3,2
     * second mode: 5,4,3,2,1
     * third mode:  1,2,3,4,5
     * forth mode:  2,3,4,5,6
     * }
     *
     * @param shelf the shelf to be scanned
     * @return true if the goal is achieved, false otherwise
     */
    public boolean checkGoal(Shelf shelf) {
        int[] vectorRow = {0, 0, 0, 0, 0};
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 6; y++) {
                if (shelf.getItemName(y, x) != null)
                    vectorRow[x]++;
            }
        }
        int fRow = vectorRow[0]; /*  count the number of the row which has the item */

        if (fRow == 6 || fRow == 5) /* control descending*/ {
            for (int i = 0; i < 5; i++) {
                if (vectorRow[i] == fRow) {
                    fRow--;
                    if (i == 4)
                        return true;
                } else
                    return false;
            }
        } else if (fRow == 1 || fRow == 2) /* control ascending*/ {
            for (int i = 0; i < 5; i++) {
                if (vectorRow[i] == fRow) {
                    fRow++;
                    if (i == 4)
                        return true;
                } else
                    return false;
            }
        }
        return false;
    }
}
