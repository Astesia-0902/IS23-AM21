package org.am21.utilities;

import org.am21.model.Cards.ItemCard;

/**
 * @author Ken Chen
 * @version 1.0
 */
public class CardPointer extends Coordinates{

    public ItemCard item;

    public CardPointer(int r, int c){
        super(r,c);
        this.item= new ItemCard("none");
    }
}
