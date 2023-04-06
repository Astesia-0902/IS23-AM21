package org.am21.model.items;

import org.am21.model.Cards.ItemTileCard;

public abstract class Grid {
    public int gRow;
    public int gColumn;
    public int capacity;
    private ItemTileCard[][] matrix;

    public Grid(int gRow, int gColumn) {
        this.gRow = gRow;
        this.gColumn = gColumn;
        this.capacity = this.gRow * this.gColumn;
        this.matrix = new ItemTileCard[this.gRow][this.gColumn];
    }

    public ItemTileCard[][] getMatrix() {
        return matrix;
    }

    public ItemTileCard getCell(int r, int c){
        return matrix[r][c];
    }

    public void setCell(int rowNum, int colNum, ItemTileCard value){
        this.matrix[rowNum][colNum] = value;
    }

    public ItemTileCard getCellItem(int r,int c){
        if(matrix[r][c]!=null){
            return matrix[r][c];
        }
        return null;

    }

    public String getItemName(int rowNum,int colNum){
        /**
         * Obtain Item's Reference
         * @param rowNum
         * @param colNum
         * @return ItemName
         */
        if (matrix[rowNum][colNum]!= null) {
            return matrix[rowNum][colNum].getNameCard();
        }
        return null;
    }


}
