package org.am21.utilities;

import org.am21.model.Cards.ItemCard;

public class CardPointer extends Coordinates{

    public ItemCard item;

    public CardPointer(int r, int c){
        super(r,c);
        this.item= new ItemCard("none");
    }
}
