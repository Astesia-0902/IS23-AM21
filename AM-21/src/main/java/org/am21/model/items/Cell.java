package org.am21.model.items;

import org.am21.model.items.Card.ItemTileCard;

public class Cell {
    private boolean state = false;
    private boolean dark = false;
    private ItemTileCard itemTileCard = null;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
    public boolean isDark() {
        return dark;
    }
    public void setDark(boolean dark) {
        this.dark = dark;
    }
    public ItemTileCard getItemTileCard() {
        return itemTileCard;
    }

    public void setItemTileCard(ItemTileCard itemTileCard) {
        this.itemTileCard = itemTileCard;
    }
}
