package org.am21.model.Cards.CommonGoals;

import org.am21.model.Cards.CommonGoal;
import org.am21.model.items.Shelf;

/**
 * This class includes the common goal of 4 groups and common goal of 6 groups
 * User can define the number of groups and the number of tiles in each group by using the constructor
 */
public class CommonGoalGroup extends CommonGoal {
    public int groupNum;
    public int groupSize;


    /**
     * Constructor
     *
     * @param numPlayer the number of players
     * @param groupNum  the number of groups
     * @param groupSize the number of tiles in each group
     */
    public CommonGoalGroup(int numPlayer, int groupNum, int groupSize) {
        super("CommonGoal" + groupNum + "Group", numPlayer);
        this.groupNum = groupNum;
        this.groupSize = groupSize;
    }

    /**
     * Scan the shelves to find the number of groups of the same type
     *
     * @param shelf the shelf to be scanned
     * @return true if the goal is achieved, false otherwise
     */
    @Override
    public boolean checkGoal(Shelf shelf) {
        boolean[][] visited = new boolean[Shelf.SHELF_ROW][Shelf.SHELF_COLUMN];
        int count = 0;
        for (int i = 0; i < Shelf.SHELF_ROW; i++) {
            for (int j = 0; j < Shelf.SHELF_COLUMN; j++) {
                if (shelf.getCell(i, j) == null || visited[i][j]) {
                    continue;
                }
                String itemType = shelf.getItemType(i, j);
                if (process(shelf, i, j, visited, 1, itemType)) {
                    count++;

                    if (count >= groupNum) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * This method recursively checks if there is a group of tiles of the same type
     * Use this version if a continuous set of tiles is regarded as a group no matter how many tiles are in the group
     *
     * @param shelf    the shelves to check
     * @param i        row index
     * @param j        column index
     * @param visited  a boolean matrix to keep track of visited tiles
     * @param depth    the depth of the recursion
     * @param itemType the type of the item we are looking for
     * @return true if there is a group of tiles of the same type
     */
    private boolean process(Shelf shelf, int i, int j, boolean[][] visited, int depth, String itemType) {
        boolean flag = depth >= groupSize;

        visited[i][j] = true;

        //Check all 4 directions
        //If we find a tile of the same type, we recursively call process
        //check the conditions before calling process, building calls on stack is expensive
        if (i > 0 && !visited[i - 1][j] && shelf.getCell(i - 1, j) != null && shelf.getItemType(i - 1, j).equals(itemType)) {
            if (process(shelf, i - 1, j, visited, depth + 1, itemType)) {
                return true;
            }
        }

        if (i < Shelf.SHELF_ROW - 1 && !visited[i + 1][j] && shelf.getCell(i + 1, j) != null && shelf.getItemType(i + 1, j).equals(itemType)) {
            if (process(shelf, i + 1, j, visited, depth + 1, itemType)) {
                return true;
            }
        }

        if (j > 0 && !visited[i][j - 1] && shelf.getCell(i, j - 1) != null && shelf.getItemType(i, j - 1).equals(itemType)) {
            if (process(shelf, i, j - 1, visited, depth + 1, itemType)) {
                return true;
            }
        }

        if (j < Shelf.SHELF_COLUMN - 1 && !visited[i][j + 1] && shelf.getCell(i, j + 1) != null && shelf.getItemType(i, j + 1).equals(itemType)) {
            if (process(shelf, i, j + 1, visited, depth + 1, itemType)) {
                return true;
            }
        }

        //reset visited if we don't find a group
        if (!flag) visited[i][j] = false;

        return flag;
    }
}



