package org.am21.model;

import org.am21.model.Card.ItemTileCard;
import org.am21.utilities.CardPointer;

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
     * (became useless)
     */
    private int numCards;
    private ArrayList<CardPointer> slot;

    public static ItemTileCard neutral_card = new ItemTileCard("none");

    public Hand(Player player){
        this.player = player;
        this.numCards=0;
        this.slot = new ArrayList<>();
    }

    public ArrayList<CardPointer> getSlot() {
        return slot;
    }

    /**
     * Create a temporary Coordinates object for data setting.
     * Then add it to the slot list.
     * @param item
     * @param r
     * @param c
     */
    public void memCard(ItemTileCard item,int r, int c){
        CardPointer tmp = new CardPointer(r,c);
        tmp.item = item;
        this.slot.add(tmp);
    }

    /**
     * Clearing all items in slot list when the player trigger 'Deselection'
     */
    public void clearHand(){
        this.slot.clear();
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

    /**
     * Reset slot when player unselect a card
     * @param n
     */
    public void resetSlot(int n){
        this.slot.get(n).item = neutral_card;
        this.slot.get(n).x=-1;
        this.slot.get(n).y=-1;
    }

    public int getNumCards() {
        return numCards;
    }

    public void setNumCards(int numCards) {
        if (numCards >= 0 && numCards <= 3) {
            this.numCards = numCards;
        } else {
            System.out.println("Try again");
        }
    }

    /**
     * function for order changing between 2 element in slot
     * Used during Insertion Phase
     * @param pos1
     * @param pos2
     */
    public void changeOrder(int pos1,int pos2){

        Collections.swap(slot,pos1,pos2);

    }


}
