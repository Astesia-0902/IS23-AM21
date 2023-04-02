package org.am21.model.items;

import org.am21.model.items.Card.ItemTileCard;

public class Cell {
    /**
     * Dark means the cell cannot be utilized during the match
     */
    private boolean dark = false;
    private ItemTileCard item = null;



    public boolean isDark() {

        return dark;
    }
    public void setDark(boolean dark) {

        this.dark = dark;
    }
    public ItemTileCard getItem() {

        return this.item;
    }

    public void setItem(ItemTileCard item) {

        this.item = item;
    }
}
