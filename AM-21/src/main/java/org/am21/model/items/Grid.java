package org.am21.model.items;

import org.am21.model.items.Card.ItemTileCard;

public abstract class Grid {
    public int rowNum;
    public int colNum;
    public int capacity;
    private Cell[][] cellGrid;

    public Grid(int rowNum, int colNum) {
        this.rowNum = rowNum;
        this.colNum = colNum;
        this.capacity = this.rowNum * this.colNum;
        this.cellGrid = new Cell[this.rowNum][this.colNum];
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

        return cellGrid[rowNum][colNum].getItem().getNameCard();
    }

    public int getCapacity() {
        return capacity;
    }

    public void insertInCell(int r, int c, ItemTileCard item){
        if(!cellGrid[r][c].isDark())
            cellGrid[r][c].setItem(item);

    }
}
