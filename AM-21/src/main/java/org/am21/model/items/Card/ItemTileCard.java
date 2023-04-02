package org.am21.model.items.Card;

public class ItemTileCard extends Card {
    private boolean isNonSelectable;


    public ItemTileCard(String nameCard) {
        super(nameCard);
        this.isNonSelectable = false;
    }
    @Override
    public String getNameCard() {
        return super.getNameCard();
    }

    public boolean getNonSelectable() {
        return isNonSelectable;
    }

    public void setNonSelectable(boolean nonSelectable) {
        isNonSelectable = nonSelectable;
    }
}
