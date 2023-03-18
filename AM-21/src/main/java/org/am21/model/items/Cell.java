package org.am21.model.items;

import org.am21.model.items.Card.ItemTileCard;

public class Cell {
    private Integer state = 0;          // 0: without cards, 1: with cards
    private ItemTileCard itemTileCard;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public ItemTileCard getItemTileCard() {
        return itemTileCard;
    }

    public void setItemTileCard(ItemTileCard itemTileCard) {
        this.itemTileCard = itemTileCard;
    }
}
