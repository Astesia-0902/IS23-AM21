package org.am21.model.items;

import org.am21.model.Cards.ItemCard;
import org.am21.model.Player;
import org.am21.utilities.CardPointer;

import java.util.ArrayList;
import java.util.Collections;

/**
 * PlayerHand class contains an array with 3 elements.
 * It is used to memorize temporarily the selected cards(1-3).
 */
public class Hand {
    public Player player;
    private ArrayList<CardPointer> slot;

    public Hand(Player player){
        this.player = player;
        this.slot = new ArrayList<>();
    }

    public ArrayList<CardPointer> getSlot() {
        return slot;
    }


    public void memCard(ItemCard item, int r, int c){
        /**
         * Create a temporary Coordinates object for data setting.
         * Then add it to the slot list.
         * @param item
         * @param r
         * @param c
         */
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



    public void changeOrder(int pos1,int pos2){
        /**
         * function for order changing between 2 element in slot
         * Used during Insertion Phase
         * @param pos1
         * @param pos2
         */
        Collections.swap(slot,pos1,pos2);

    }


}
