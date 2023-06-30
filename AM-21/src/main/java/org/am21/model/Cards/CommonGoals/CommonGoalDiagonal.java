package org.am21.model.Cards.CommonGoals;

import org.am21.model.Cards.CommonGoal;
import org.am21.model.items.Shelf;

/**
 * Five tiles of the same type forming a diagonal.
 */
public class CommonGoalDiagonal extends CommonGoal {
    private static final int nDiag = 2;

    /**
     * Constructor
     *
     * @param numPlayer the number of players
     */
    public CommonGoalDiagonal(int numPlayer) {

        super("CommonGoalDiagonal", numPlayer);
    }


    /**
     * Scan the shelves to find five tiles of the same type forming a diagonal.
     * (0,0), (1,1), (2,2), (3,3), (4,4)
     * (1,0), (2,1), (3,2), (4,3), (5,4)
     * (0,4), (1,3), (2,2), (3,1), (4,0)
     * (1,4), (2,3), (3,2), (4,1), (5,0)
     *
     * @param s the shelf to be scanned
     * @return true if the goal is achieved, false otherwise
     */
    @Override
    public boolean checkGoal(Shelf s) {

        for (int r = 0; r < nDiag; r++) {

            if (s.isOccupied(r, 0)) {
                String type = s.getItemType(r, 0);
                // Scan diagonal \
                if (s.isOccupied(r, 0) && type.equals(s.getItemType(r, 0)) &&
                    s.isOccupied(r + 1, 1) && type.equals(s.getItemType(r + 1, 1)) &&
                    s.isOccupied(r + 2, 2) && type.equals(s.getItemType(r + 2, 2)) &&
                    s.isOccupied(r + 3, 3) && type.equals(s.getItemType(r + 3, 3)) &&
                    s.isOccupied(r + 4, 4) && type.equals(s.getItemType(r + 4, 4))) {
                    return true;
                }
            }

            if (s.isOccupied(r, 4)) {
                String type = s.getItemType(r, 4);
                // Scan diagonal /
                if (s.isOccupied(r, 4) && type.equals(s.getItemType(r, 4)) &&
                    s.isOccupied(r + 1, 3) && type.equals(s.getItemType(r + 1, 3)) &&
                    s.isOccupied(r + 2, 2) && type.equals(s.getItemType(r + 2, 2)) &&
                    s.isOccupied(r + 3, 1) && type.equals(s.getItemType(r + 3, 1)) &&
                    s.isOccupied(r + 4, 0) && type.equals(s.getItemType(r + 4, 0))) {
                    return true;
                }
            }
        }
        return false;
    }
}