package org.am21.model.Cards.CommonGoals;

import org.am21.model.Cards.CommonGoal;
import org.am21.model.items.Shelf;

/**
 * Four tiles of the same type in the four corners of the bookshelf.
 */
public class CommonGoalCorner extends CommonGoal {
    /**
     * Constructor
     *
     * @param numPlayer the number of players
     */
    public CommonGoalCorner(int numPlayer) {

        super("CommonGoalCorner", numPlayer);
    }

    /*
        public CommonGoalCorner(){
           /**
            * check 2 or more the sequence condition:
            * 1. remember the first row and first column cell;
            * 2. remember the last row and first column cell;
            * 3. remember the first row and last column cell ;
            * 4. remember the last row and last column cell;
            * if fall the 2 or more condition, then return false for this player
            * if success the all 4 condition, then return true and distribuite the actually points
            *
        }

     */

    /**
     * Scan the shelves to find four tiles of the same type in the four corners of the bookshelf.
     *
     * @param shelf the shelf to be scanned
     * @return
     */
    @Override
    public boolean checkGoal(Shelf shelf) {

        if (shelf.isOccupied(0, 0) &&
            shelf.isOccupied(0, 4) &&
            shelf.isOccupied(5, 0) &&
            shelf.isOccupied(5, 4)) {

            String corn1 = shelf.getItemType(0, 0);
            String corn2 = shelf.getItemType(0, 4);
            String corn3 = shelf.getItemType(5, 0);
            String corn4 = shelf.getItemType(5, 4);
            /* if all corner are filled  */

            /*  if they are the same then return true*/
            /*
             *  else return false (so this player is missed the points)
             */
            return corn1.equals(corn2) && corn2.equals(corn3) && corn3.equals(corn4);

        }
        /* return to continuing  checking
         * ( the player has still chance for accumulating the points )*/
        return false;
    }

}
