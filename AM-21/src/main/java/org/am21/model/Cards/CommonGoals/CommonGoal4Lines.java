package org.am21.model.Cards.CommonGoals;

import org.am21.model.Cards.CommonGoal;
import org.am21.model.items.Shelf;

import java.util.HashSet;
import java.util.Set;

/**
 * Four lines each formed by 5 tiles of maximum three different types.
 * One line can show the same or a different combination of another line.
 */
public class CommonGoal4Lines extends CommonGoal {
    /**
     * Constructor
     * @param numPlayer the number of players
     */
    public CommonGoal4Lines(int numPlayer) {

        super("CommonGoal4Lines",numPlayer);
    }

    /**
     * Scan the shelves to find four rows each formed by 5 different types of tiles.
     * @param shelf the shelf to be scanned
     * @return true if the goal is achieved, false otherwise
     */
    public boolean checkGoal(Shelf shelf) {
        Set<String> reg;
        //at first check if number of full column in the Shelf is full
        int numRowMatch=0;
        for(int i=0;i<6;i++) {
            reg = new HashSet<>();
            int j;
            for(j=0;j<5&&shelf.getItemName(i, j) != null;j++)
            {
                reg.add(shelf.getItemName(i, j).substring(0, shelf.getItemName(i, j).length() - 3));
            }
            if(reg.size()<=3&&j==5)
                numRowMatch++;
            reg.clear();
        }

        if(numRowMatch<4)
            return false;
        else
            return true;

    }
}
