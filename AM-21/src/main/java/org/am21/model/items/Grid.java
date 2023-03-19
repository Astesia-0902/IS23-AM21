package org.am21.model.items;

public abstract class Grid {
    private int rowNum;
    private int colNum;
    private int capacity;
    private Cell[][] cells;

    public Grid(int rowNum, int colNum) {
        this.rowNum = rowNum;
        this.colNum = colNum;
        this.capacity = this.rowNum * this.colNum;
        this.cells = new Cell[this.rowNum][this.colNum];
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getColNum() {
        return colNum;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(int rowNum, int colNum, Cell value) {
        this.cells[rowNum][colNum] = value;
    }

    public String getItemName(int rowNum,int colNum){
        return cells[rowNum][colNum].getItemTileCard().getNameCard();
    }
}
