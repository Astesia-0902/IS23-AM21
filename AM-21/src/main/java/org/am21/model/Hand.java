package org.am21.model;
import org.am21.model.items.Card.ItemTileCard;
import org.am21.util.Coordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * PlayerHand class contains an array with 3 elements.
 * It is used to memorize temporarily the selected cards(1-3).
 */
public class Hand {
    public Player player;

    /**
     * Numero di carte in mano (SelectionPhase)
     */
    private int numCards;

    private List<Coordinates> slot;


    public Hand(Player player){
        this.player = player;
        this.numCards=0;
        this.slot = new ArrayList<>(3);
    }

    public ItemTileCard getNSlot(int n) {
        return slot.get(n).item;
    }


    public void setSlot(ItemTileCard item,int n) {
        this.slot.get(n).item = item;
    }


    public int getNumCards() {
        return numCards;
    }

    public void setNumCards(int numCards) {
        if(numCards>=0 && numCards<=3) {
            this.numCards = numCards;
        }
    }

    public List<Coordinates> getSlot() {
        return slot;
    }
}
