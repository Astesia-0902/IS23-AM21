package org.am21.model.items.Card.CommonGoals;

import org.am21.model.items.CommonGoal;
import org.am21.model.items.Shelf;

import java.util.HashMap;
import java.util.Map;

public class CommonGoal8Tiles extends CommonGoal {
    private static final int rowNumShelf = 6;
    private static final int colNumShelf = 5;
    private static final int numSame = 8;

//    public CommonGoal8Tiles(List<Player> achievedPlayers) {
//        super(9, achievedPlayers);
//    }


    public CommonGoal8Tiles(int numPlayer) {
        super(numPlayer);
        nameCard = "CommonGoal8Tiles";
    }

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
        for (int row = 0; row < rowNumShelf; row++) {
            for (int col = 0; col < colNumShelf; col++) {
                String tile = shelf.getItemName(row, col);

                // If the counter for tileType already exists in countTiles, the current value
                // of the counter is incremented by 1, otherwise its value is set to 1
                countTiles.put(tile, countTiles.getOrDefault(tile, 0) + 1);

                // If one of the Tiles has 8 of the same type, then it returns true
                if (countTiles.get(tile) >= numSame ){
                    return true;
                }
            }
        }
        return false;
    }
}
