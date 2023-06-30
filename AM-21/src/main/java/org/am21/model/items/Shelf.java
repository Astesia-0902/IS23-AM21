package org.am21.model.items;

import org.am21.model.Cards.ItemCard;
import org.am21.model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class is used to manage the shelves of the player
 */
public class Shelf extends Grid {

    public Player player;
    /**
     * Each element show how many slots are still available in correspondent column
     */
    public List<Integer> slotCol;
    /**
     * Numbers of items insertable in the shelves each turn. Starting limit: 3. It's diminishable.
     */
    public int insertLimit = STD_LIMIT;
    public static int STD_LIMIT = 3;
    public final static int SHELF_ROW = 6;
    public final static int SHELF_COLUMN = 5;

    private final static HashMap<Integer, Integer> pointsMap = new HashMap<>();

    static {
        pointsMap.put(0, 0);
        pointsMap.put(3, 2);
        pointsMap.put(4, 3);
        pointsMap.put(5, 5);
        pointsMap.put(6, 8);
    }


    /**
     * Construction of an empty shelves:
     * Initialize the grid with superclass
     * Create array, each elem count slot available for each column
     * Row-Index-0 is on the top of the shelves
     *
     * @param player player instance
     */
    public Shelf(Player player) {
        super(SHELF_ROW, SHELF_COLUMN);
        this.player = player;
        this.slotCol = new ArrayList<>();
        for (int i = 0; i < SHELF_COLUMN; i++) {
            slotCol.add(SHELF_ROW);
        }

    }

    /**
     * Calculate the min Limit for hand Capacity
     * Example: If there are only column with 2 slots available,
     * then 'handLimit' = 2
     */
    public void checkLimit() {
        int max = 0;

        for (int j = 0; j < SHELF_COLUMN; j++) {
            if (this.slotCol.get(j) > max) {
                max = this.slotCol.get(j);
            }
        }
        if (max >= STD_LIMIT) {
            this.insertLimit = STD_LIMIT;
        } else {
            this.insertLimit = max;
        }
    }

    /**
     * number of slot available in total
     *
     * @return Number of Total space available in this shelves
     */
    public int getTotSlotAvail() {
        int sum = 0;
        for (int x : this.slotCol)
            sum = sum + x;
        return sum;
    }


    /**
     * Insert an itemCard in the column, then decrease the count
     * in column (col)
     * Check limit after insertion
     *
     * @param item ItemCard reference
     * @param col  column for insertion
     * @return true if insertion has been successful
     */
    public boolean insertInColumn(ItemCard item, int col) {

        if (slotCol.get(col) > 0 && this.getMatrix()[slotCol.get(col) - 1][col] == null) {
            this.getMatrix()[slotCol.get(col) - 1][col] = item;
            this.slotCol.set(col, slotCol.get(col) - 1);
            return true;
        }

        return false;
    }


    /**
     * This method is called at the end of the Game
     * It will calculate the points according to a table.
     * Having multiple item of the same type adjacent
     * give different amount of points.
     */
    public int getGroupPoints() {
        boolean[][] visited = new boolean[SHELF_ROW][SHELF_COLUMN];
        int points = 0;

        for (int r = 0; r < SHELF_ROW; r++) {
            for (int c = 0; c < SHELF_COLUMN; c++) {
                if (!isOccupied(r, c) || visited[r][c]) {
                    continue;
                }
                points += pointsTable(colorCounter(r, c, visited, 1, getItemType(r, c)));
            }
        }
        return points;
    }

    /**
     * Calculate the total points from shelf group points
     *
     * @param r       root row of the backtracking
     * @param c       root column of the backtracking
     * @param visited matrix of visited cells
     * @param depth   depth of the backtracking
     * @param type    type of item
     * @return number of item of the same type adjacent to each other
     */
    public int colorCounter(int r, int c, boolean[][] visited, int depth, String type) {
        int newDepth = depth;
        visited[r][c] = true;

        if (r > 0 && !visited[r - 1][c] && isOccupied(r - 1, c) && getItemType(r - 1, c).equals(type)) {
            newDepth = colorCounter(r - 1, c, visited, newDepth + 1, type);
        }
        if (r + 1 < SHELF_ROW && !visited[r + 1][c] && isOccupied(r + 1, c) && getItemType(r + 1, c).equals(type)) {
            newDepth = colorCounter(r + 1, c, visited, newDepth + 1, type);
        }
        if (c > 0 && !visited[r][c - 1] && isOccupied(r, c - 1) && getItemType(r, c - 1).equals(type)) {
            newDepth = colorCounter(r, c - 1, visited, newDepth + 1, type);
        }
        if (c + 1 < SHELF_COLUMN && !visited[r][c + 1] && isOccupied(r, c + 1) && getItemType(r, c + 1).equals(type)) {
            newDepth = colorCounter(r, c + 1, visited, newDepth + 1, type);
        }
        return newDepth;
    }

    /**
     * This method return the number of points gained due to a number of item adjacent to each other
     *
     * @param nItem number of item
     * @return points
     */
    public int pointsTable(int nItem) {
        if (nItem < 3) {
            nItem = 0;
        } else if (nItem > 6) {
            nItem = 6;
        }

        return pointsMap.get(nItem);
    }
}


