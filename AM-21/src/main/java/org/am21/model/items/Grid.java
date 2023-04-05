package org.am21.model.items;

import org.am21.model.Cards.ItemTileCard;

public abstract class Grid {
    public int gRow;
    public int gColumn;
    public int capacity;
    private Cell[][] cellGrid;

    public Grid(int gRow, int gColumn) {
        this.gRow = gRow;
        this.gColumn = gColumn;
        this.capacity = this.gRow * this.gColumn;
        this.cellGrid = new Cell[this.gRow][this.gColumn];
    }

    public Cell[][] getCellGrid() {
        return cellGrid;
    }

    public void setCell(int rowNum, int colNum, Cell value){
        this.cellGrid[rowNum][colNum] = value;
    }

    public ItemTileCard getCellItem(int r,int c){
        return cellGrid[r][c].getItem();
    }

    public String getItemName(int rowNum,int colNum){
        /**
         * Obtain Item's Reference
         * @param rowNum
         * @param colNum
         * @return ItemName
         */
        if (cellGrid[rowNum][colNum].getItem()!= null) {
            return cellGrid[rowNum][colNum].getItem().getNameCard();
        }
        return null;
    }

    public boolean insertInCell(int r, int c, ItemTileCard item){
        if(cellGrid[r][c]!=null) {
            if (!cellGrid[r][c].isDark()){
                cellGrid[r][c].setItem(item);
                return true;
            }
        }
        return false;
    }
}
