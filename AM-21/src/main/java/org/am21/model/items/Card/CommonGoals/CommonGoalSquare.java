package org.am21.model.items.Card.CommonGoals;

import org.am21.model.items.Card.CommonGoalCard;
import org.am21.model.items.Shelf;

public class CommonGoalSquare extends CommonGoalCard {

    /**
     * Scan the shelf to find 2 groups of 4 tiles of the same color
     * @param shelf to scan
     * @return true if the goal is reached, false otherwise
     */
    public boolean checkGoal(Shelf shelf) {
        boolean[][] visited = new boolean[6][5];
        int count = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                //Once we find a tile != 0 and not visited, we check if it's the upper left tile of a 2x2 group
                if (shelf.bookshelfGrid.get(i).get(j) != null && !visited[i][j]) {
                    boolean result = process(shelf,i,j,visited)
                            && process(shelf,i+1,j,visited)
                            && process(shelf,i,j+1,visited)
                            && process(shelf,i+1,j+1,visited);
                    if (result) {
                        count++;
                        //If we find 2 groups of 4 tiles, we return true
                        if (count >= 2) {
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
        if (i < 0 || i >= 6 || j < 0 || j >= 5 || visited[i][j] || shelf.bookshelfGrid.get(i).get(j) == null) {
            return false;
        }

        //mark the tile as visited
        visited[i][j] = true;
        return true;
    }


}
