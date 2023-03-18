package org.am21.model.items;

import org.am21.model.items.Card.ItemTileCard;

public class LivingRoomBoard {
    private Integer rowNum = 11;
    private Integer colNum = 12;
    private Integer capacity;

    private Integer size;               // The number of cards required varies according to the number of players

    private Cell[][] cells;

    public LivingRoomBoard() {
        this.capacity = this.rowNum * this.colNum;            // 132 item tiles
        this.cells = new Cell[this.rowNum][this.colNum];
        this.size = 0;
    }


    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }
}

