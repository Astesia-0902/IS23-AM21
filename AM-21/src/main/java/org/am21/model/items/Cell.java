package org.am21.model.items;

import org.am21.model.items.Card.ItemTileCard;

public class Cell {
    private boolean state = false;
    private ItemTileCard itemTileCard;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public ItemTileCard getItemTileCard() {
        return itemTileCard;
    }

    public void setItemTileCard(ItemTileCard itemTileCard) {
        this.itemTileCard = itemTileCard;
    }
}
