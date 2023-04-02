package org.am21.model.items;

import org.am21.model.items.Card.ItemTileCard;

public abstract class Grid {
    public int rowNum;
    public int colNum;
    public int capacity;
    private Cell[][] cells;

    public Grid(int rowNum, int colNum) {
        this.rowNum = rowNum;
        this.colNum = colNum;
        this.capacity = this.rowNum * this.colNum;
        this.cells = new Cell[this.rowNum][this.colNum];
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCell(int rowNum, int colNum, Cell value){
        this.cells[rowNum][colNum] = value;
    }

    public String getItemName(int rowNum,int colNum){
        return cells[rowNum][colNum].getItemTileCard().getNameCard();
    }

    public int getCapacity() {
        return capacity;
    }

    public void insertInCell(int r, int c, ItemTileCard item){
        cells[r][c].setItemTileCard(item);

    }
}
