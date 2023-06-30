package org.am21.model.Cards.CommonGoals;

import org.am21.model.Cards.CommonGoal;
import org.am21.model.items.Shelf;

import java.util.HashSet;

/**
 * Three columns each formed by 6 tiles of maximum three different types.
 * One column can show the same or a different combination of another column.
 */
public class CommonGoal3Column extends CommonGoal {

    /**
     * Constructor
     *
     * @param numPlayer the number of players
     */
    public CommonGoal3Column(int numPlayer) {

        super("CommonGoal3Column", numPlayer);
    }

    /**
     * @param shelf the shelf to be scanned
     * @return true if the goal is achieved, false otherwise
     */
    @Override
    public boolean checkGoal(Shelf shelf) {
        //at first check if number of full column in the Shelf is full
        int numColMatch = 0;
        HashSet<String> reg;
        for (int j = 0; j < 5; j++) {
            reg = new HashSet<>();
            if (shelf.slotCol.get(j) == 0) {
                //column full
                for (int i = 0; i < 6; i++) {
                    //scan column
                    if (shelf.isOccupied(i, j)) {
                        reg.add(shelf.getItemName(i, j).substring(0, shelf.getItemName(i, j).length() - 3));
                    }
                }
                if (reg.size() <= 3)
                    numColMatch++;
            }
            reg.clear();


        }

        return numColMatch >= 3;

    }
}