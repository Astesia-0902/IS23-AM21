package org.am21.model.items;

import org.am21.model.items.Card.ItemTileCard;

public class LivingRoomBoard extends Grid{
    private Integer size;               // The number of cards required varies according to the number of players

    public LivingRoomBoard(int rowNum, int colNum) {
        super(rowNum, colNum);
        this.size = 0;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}

