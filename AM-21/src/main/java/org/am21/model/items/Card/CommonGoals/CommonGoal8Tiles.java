package org.am21.model.items.Card.CommonGoals;

import org.am21.model.items.CommonGoal;
import org.am21.model.items.Shelf;

import java.util.HashMap;
import java.util.Map;

public class CommonGoal8Tiles extends CommonGoal {
    @Override
    public boolean checkGoal(Shelf shelf) {
        /**
         * Scan the shelf to find eight tiles of the same type. There’s no
         * restriction about the position of these tiles.
         * @param shelf
         * @return
         */

        // Create a counter
        Map<String, Integer> countTiles = new HashMap<>();
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 5; col++) {
                String tile = shelf.getItemName(row, col);

                // If the counter for tileType already exists in countTiles, the current value
                // of the counter is incremented by 1, otherwise its value is set to 1
                countTiles.put(tile, countTiles.getOrDefault(tile, 0) + 1);

                // If one of the Tiles has 8 of the same type, then it returns true
                if (countTiles.get(tile) >= 8 ){
                    return true;
                }
            }
        }
        return false;
    }
}
