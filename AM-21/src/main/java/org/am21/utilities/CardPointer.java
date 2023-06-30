package org.am21.utilities;

import org.am21.model.Cards.ItemCard;

/**
 * This class is used to check if a card is selected
 *
 * @version 1.0
 */
public class CardPointer extends Coordinates {

    public ItemCard item;

    /**
     * Card Pointer Constructor: save row,column and ItemCard
     *
     * @param r row
     * @param c column
     */
    public CardPointer(int r, int c) {
        super(r, c);
        this.item = new ItemCard("none");
    }
}
