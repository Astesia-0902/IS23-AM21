package org.am21.utilities;

import org.am21.model.Cards.ItemCard;

/**
 * @version 1.0
 */
public class CardPointer extends Coordinates{

    public ItemCard item;

    /**
     * Constructor
     * @param r
     * @param c
     */
    public CardPointer(int r, int c){
        super(r,c);
        this.item= new ItemCard("none");
    }
}
