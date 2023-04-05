package org.am21.utilities;

import org.am21.model.Cards.ItemTileCard;

public class CardPointer extends Coordinates{

    public ItemTileCard item;

    public CardPointer(int r, int c){
        super(r,c);
        this.item= new ItemTileCard("none");
    }
}
