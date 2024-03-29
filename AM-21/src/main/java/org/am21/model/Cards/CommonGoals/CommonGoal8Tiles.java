package org.am21.model.Cards.CommonGoals;

import org.am21.model.Cards.CommonGoal;
import org.am21.model.items.Shelf;

import java.util.HashMap;
import java.util.Map;

/**
 * Eight tiles of the same type.
 * There’s no restriction about the position of these tiles.
 */
public class CommonGoal8Tiles extends CommonGoal {
    private static final int rowNumShelf = 6;
    private static final int colNumShelf = 5;
    private static final int numSame = 8;

    /**
     * Constructor
     *
     * @param numPlayer the number of players
     */
    public CommonGoal8Tiles(int numPlayer) {

        super("CommonGoal8Tiles", numPlayer);
    }

    /**
     * Scan the shelves to find eight tiles of the same type. There’s no
     * restriction about the position of these tiles.
     *
     * @param shelf the shelf to be scanned
     * @return true if there are eight tiles of the same type
     */
    public boolean checkGoal(Shelf shelf) {
        // Create a counter
        Map<String, Integer> countTiles = new HashMap<>();

        for (int row = 0; row < rowNumShelf; row++) {
            for (int col = 0; col < colNumShelf; col++) {
                if (shelf.isOccupied(row, col)) {
                    String tile = shelf.getItemName(row, col).substring(0, shelf.getItemName(row, col).length() - 3);

                    // If the counter for tileType already exists in countTiles, the current value
                    // of the counter is incremented by 1, otherwise its value is set to 1
                    countTiles.put(tile, countTiles.getOrDefault(tile, 0) + 1);

                    if (countTiles.get(tile) >= numSame) {
                        return true;
                    }
                    // If one of the Tiles has 8 of the same type, then it returns true


                }
            }
        }
        return false;
    }
}
