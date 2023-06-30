package org.am21.model.items;

import org.am21.model.Cards.ItemCard;

/**
 * This class is the abstract class of the grid
 */
public abstract class Grid {
    public int gRow;
    public int gColumn;
    public int capacity;
    private final ItemCard[][] matrix;

    public Grid(int gRow, int gColumn) {
        this.gRow = gRow;
        this.gColumn = gColumn;
        this.capacity = this.gRow * this.gColumn;
        this.matrix = new ItemCard[this.gRow][this.gColumn];
    }

    /**
     * This method return the matrix reference
     *
     * @return matrix
     */
    public ItemCard[][] getMatrix() {

        return matrix;
    }

    /**
     * get the item in the cell
     *
     * @param r row
     * @param c column
     * @return ItemCard
     */
    public ItemCard getCell(int r, int c) {

        return matrix[r][c];
    }


    /**
     * Setting a new item in the cell
     *
     * @param rowNum row
     * @param colNum column
     * @param value  ItemCard
     */
    public void setCell(int rowNum, int colNum, ItemCard value) {

        this.matrix[rowNum][colNum] = value;
    }


    /**
     * Obtain Item's Reference
     *
     * @param rowNum row
     * @param colNum column
     * @return ItemName
     */
    public String getItemName(int rowNum, int colNum) {

        if (matrix[rowNum][colNum] != null) {
            return matrix[rowNum][colNum].getNameCard();
        }
        return null;
    }

    /**
     * Check if the cell is occupied by an item
     *
     * @param r row
     * @param c column
     * @return true if the cell is occupied, otherwise false
     */
    public boolean isOccupied(int r, int c) {

        /*Cell (r,c) occupied*/
        return getCell(r, c) != null;
    }

    /**
     * Extract Item Type from the name of the item in the cell
     *
     * @return item type name
     */
    public String getItemType(int r, int c) {
        if (matrix[r][c] != null) {
            return matrix[r][c].getNameCard().substring(0, matrix[r][c].getNameCard().length() - 3);
        }
        return null;
    }


}
