package org.am21.model.Cards.CommonGoals;

import org.am21.model.Cards.CommonGoal;
import org.am21.model.items.Shelf;

/**
 * Two groups each containing 4 tiles of the same type in a 2x2 square.
 * The tiles of one square can be different from those of the other square.
 */
public class CommonGoalSquare extends CommonGoal {

    private boolean[][] visited;

    /**
     * Constructor
     *
     * @param numPlayer the number of players
     */
    public CommonGoalSquare(int numPlayer) {

        super("CommonGoalSquare", numPlayer);
    }

    /**
     * Scan the shelves to find 2 groups of 4 tiles of the same color
     *
     * @param s is the shelves
     * @return true if the goal is reached, false otherwise
     */
    @Override
    public boolean checkGoal(Shelf s) {
        visited = new boolean[Shelf.SHELF_ROW][Shelf.SHELF_COLUMN];

        String itemRef = null;


        //Scan full matrix(not row=5, it's useless to check)
        for (int i = 0; i < (Shelf.SHELF_ROW - 1); i++) {
            for (int j = 0; j < Shelf.SHELF_COLUMN - 1; j++) {

                //(i,j) is the square root
                if (s.isOccupied(i, j) && !visited[i][j]) {
                    if (itemRef == null) {
                        itemRef = s.getItemType(i, j);
                    }

                    // Cell get visited
                    visited[i][j] = true;
                    // Check if there is a Square with same type
                    if (controlStatus(i + 1, j, s, itemRef)
                        && controlStatus(i + 1, j + 1, s, itemRef)
                        && controlStatus(i, j + 1, s, itemRef)) {
                        //Yes, We got a square!

                        if (find2ndSquare(i, j, itemRef, s)) {
                            //End Check goal, we got 2 squares with same type
                            return true;
                        } else {
                            //reset itemRef
                            itemRef = null;
                        }
                    }


                }
                // when we end the check of the square(win or lost)
                // we go back to (i,j+1) in the for
            }
        }
        return false;
    }

    /**
     * Check if the cell is occupied and if it contains the same type of item
     *
     * @param x       is the row of the first square
     * @param y       is the column of the first square
     * @param itemRef is the type of the first square
     * @param s       is the shelves
     * @return true if the cell is occupied and if it contains the same type of item, false otherwise
     */
    private boolean find2ndSquare(int x, int y, String itemRef, Shelf s) {

        for (int i = x; i < Shelf.SHELF_ROW - 1; i++) {
            for (int j = y; j < Shelf.SHELF_COLUMN - 1; j++) {
                //(i,j) is the square root
                if (controlStatus(i, j, s, itemRef)) {
                    if (controlStatus(i + 1, j, s, itemRef)
                        && controlStatus(i + 1, j + 1, s, itemRef)
                        && controlStatus(i, j + 1, s, itemRef)) {
                        //2nd Square found
                        return true;


                    }


                }

            }
        }

        return false;

    }

    /**
     * Check if the cell is occupied and if it contains the same type of item
     *
     * @param r       is the row of the cell
     * @param c       is the column of the cell
     * @param s       is the shelves
     * @param itemRef is the type of the first square
     * @return true if the cell is occupied and if it contains the same type of item, false otherwise
     */
    private boolean controlStatus(int r, int c, Shelf s, String itemRef) {
        if (s.isOccupied(r, c) && s.getItemType(r, c).equals(itemRef) && !visited[r][c]) {
            visited[r][c] = true;
            return true;
        }
        return false;
    }
}
