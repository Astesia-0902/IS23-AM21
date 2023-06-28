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
    private ArrayList<CardPointer> selectedItems;

    public Hand(Player player){
        this.player = player;
        this.selectedItems = new ArrayList<>();
    }

    public ArrayList<CardPointer> getSelectedItems() {
        return selectedItems;
    }

    /**
     * Create a temporary Coordinates object for data setting.
     * Then add it to the selectedItems list.
     * @param item ItemCard
     * @param r row
     * @param c column
     */
    public void memCard(ItemCard item, int r, int c){

        CardPointer tmp = new CardPointer(r,c);
        tmp.item = item;
        this.selectedItems.add(tmp);
    }

    /**
     * Clearing all items in selectedItems list when the player trigger 'Deselection'
     */
    public void clearHand(){
        this.selectedItems.clear();
    }


    /**
     * function for order changing between 2 element in selectedItems
     * Used during Insertion Phase
     * @param i is position 1
     * @param j is position 2
     */
    public boolean changeOrder(int i, int j){
        int limit = getSelectedItems().size();
        if(limit<=1){

            GameManager.sendReply(player.getController(),ServerMessage.Sort_No.value());
            return false;
        }
        if(i>=0 && i<limit && j>=0 && j<limit && limit>1){
            Collections.swap(selectedItems, i, j);
            return true;
        }
        GameManager.sendReply(player.getController(),ServerMessage.Sort_Index_NO.value());

        return false;
    }


}
