package org.am21.model.items.Card.CommonGoals;

import org.am21.model.items.CommonGoal;
import org.am21.model.items.Shelf;

import java.util.HashMap;

// Check if there are two columns with 6 different types of tiles
public class CommonGoal2Columns extends CommonGoal {
    @Override
    public boolean checkGoal(Shelf shelf) {
        /**
         * Scan the shelf to find two columns each formed by 6 different types of tiles.
         * @param shelf
         * @return
         */

        // Count the number of occurrences of each type of tile in each column
        int countGroup = 0;

        for (int col = 0; col < 5; col++) {
            // Create a counter for each column
            HashMap<String, Integer> countTiles = new HashMap<>();

            for (int row = 0; row < 6; row++) {
                String tileType = shelf.getItemName(row,col);

                // If the counter for tileType already exists in countTiles, the current value
                // of the counter is incremented by 1, otherwise its value is set to 1
                countTiles.put(tileType, countTiles.getOrDefault(tileType, 0) + 1);
            }

            // Check if there is a column with 6 different types of tiles
            // If so, increment countGroup
            if (countTiles.keySet().size() == 6){
                countGroup++;
                if (countGroup >=2){
                    return true;
                }
            }
        }

        //
        return false;
    }
}
