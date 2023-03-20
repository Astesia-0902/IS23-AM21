package org.am21.model.items;

import org.am21.model.items.Card.ItemTileCard;

public class LivingRoomBoard extends Grid{
    private int size;               // The number of cards required varies according to the number of players

    public LivingRoomBoard(int rowNum, int colNum) {
        super(rowNum, colNum);
        this.size = 0;
    }

    /**
     * assigned size of cell according the number of player
    **/

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    /**ask bag to fill the cell**/
}

