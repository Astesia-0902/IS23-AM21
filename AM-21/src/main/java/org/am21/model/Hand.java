package org.am21.model;

import org.am21.model.items.Card.ItemTileCard;
import org.am21.utilities.Coordinates;

import java.util.ArrayList;
import java.util.Collections;

/**
 * PlayerHand class contains an array with 3 elements.
 * It is used to memorize temporarily the selected cards(1-3).
 */
public class Hand {
    public Player player;

    /**
     * Number of cards in the hand (SelectionPhase)
     * It is useful because the array has always 3 elements
     */
    private int numCards;
    private ArrayList<Coordinates> slot;

    public Hand(Player player){
        this.player = player;
        this.numCards=0;
        this.slot = new ArrayList<>(3);
        slot.add(new Coordinates(0,0));
        slot.add(new Coordinates(0,0));
        slot.add(new Coordinates(0,0));

    }

    public ArrayList<Coordinates> getSlot() {
        return slot;
    }

    /**
     * Insert the item in the slot-n.
     * @param item
     * @param n
     */
    public void setSlot(ItemTileCard item,int n,int r,int c) {
        this.slot.get(n).item = item;
        this.slot.get(n).x = r;
        this.slot.get(n).y = c;
    }

    public int getNumCards() {
        return numCards;
    }

    public void setNumCards(int numCards) {
        if(numCards>=0 && numCards<=3) {
            this.numCards = numCards;
        }else{
            System.out.println("Try again");
        }
    }

    public void changeOrder(int pos1,int pos2){
        Collections.swap(slot,pos1,pos2);
    }


}
