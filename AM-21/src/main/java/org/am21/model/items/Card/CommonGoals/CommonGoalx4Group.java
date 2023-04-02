package org.am21.model.items.Card.CommonGoals;

import org.am21.model.items.CommonGoal;
import org.am21.model.items.Shelf;

public class CommonGoalx4Group extends CommonGoal {
    /**
     * Scan the shelf to find 3 groups of 4 tiles of the same color
     * @param shelf
     * @return
     */
    @Override
    public boolean checkGoal(Shelf shelf) {
        boolean[][] visited = new boolean[6][5];
        int count = 0;
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 5; j++) {
                //Once we find a tile != 0 and not visited, we check if it's the upper left tile of a 1x4 group
                if(shelf.getCells()[i][j] != null && !visited[i][j]) {
                    boolean result = true;
                    for(int k = 0; k < 4; k++) {
                        result = result && process(shelf,i+k,j,visited);
                    }
                    if(result) {
                        count++;
                        //If we find 3 groups of 4 tiles, we return true
                        if(count >= 3) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean process(Shelf shelf, int i, int j, boolean[][] visited) {
        //Check if the tile is in the shelf and not visited
        if(i < 0 || i >= 6 || j < 0 || j >= 5 || visited[i][j] || shelf.getCells()[i][j] == null)
            return false;

        //mark the tile as visited
        visited[i][j] = true;
        return true;
    }
}
