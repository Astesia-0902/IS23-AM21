package org.am21.model.items;

import org.am21.model.Cards.ItemCard;
import org.am21.model.GameManager;
import org.am21.model.Player;
import org.am21.model.enumer.GamePhase;
import org.am21.model.enumer.ServerMessage;
import org.am21.utilities.CardPointer;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Hand class contains an array with 3 elements.
 * It's used to store temporarily the selected cards,during {@link GamePhase#Selection}.
 * Min: 1 card. Max: 3 card.
 *
 * @author Ken Chen
 * @version 1.0
 */
public class Hand {
    public Player player;
    private ArrayList<CardPointer> slot;

    /**
     *
     * @param player
     */
    public Hand(Player player){
        this.player = player;
        this.slot = new ArrayList<>();
    }

    /**
     *
     * @return
     */
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
    public void memCard(ItemCard item, int r, int c){

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
     * function for order changing between 2 element in slot
     * Used during Insertion Phase
     * @param i is position 1
     * @param j is position 2
     */
    public boolean changeOrder(int i, int j){
        int limit =getSlot().size();
        if(limit<=1){

            GameManager.sendCommunication(player.getController(),ServerMessage.Sort_No);
            return false;
        }
        if(i>=0 && i<limit && j>=0 && j<limit && limit>1){
            Collections.swap(slot, i, j);
            //System.out.println("Hand > Order Changed");
            return true;
        }
        GameManager.sendCommunication(player.getController(),ServerMessage.Sort_Index_NO);

        return false;
    }


}
