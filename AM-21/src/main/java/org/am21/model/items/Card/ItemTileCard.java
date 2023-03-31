package org.am21.model.items.Card;
import java.util.*;

public class ItemTileCard extends Card {
    private boolean isNonSelectable;


    public ItemTileCard(String nameCard) {
        super(nameCard);
        this.isNonSelectable = false;
    }

    public boolean getNonSelectable() {
        return isNonSelectable;
    }

    public void setNonSelectable(boolean nonSelectable) {
        isNonSelectable = nonSelectable;
    }
}
