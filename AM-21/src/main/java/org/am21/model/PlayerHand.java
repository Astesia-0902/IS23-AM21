package org.am21.model;
import org.am21.model.items.Card.ItemTileCard;
import org.am21.model.Player;


public class PlayerHand {
    public Player player;
    private ItemTileCard slot1;
    private ItemTileCard slot2;
    private ItemTileCard slot3;
    private int numCards;


    public PlayerHand(Player player){
        this.player = player;
        this.numCards=0;
        this.slot1=null;
        this.slot2=null;
        this.slot3=null;
    }

    public ItemTileCard getSlot1() {
        return slot1;
    }

    public void setSlot1(ItemTileCard slot1) {
        this.slot1 = slot1;
    }

    public ItemTileCard getSlot2() {
        return slot2;
    }

    public void setSlot2(ItemTileCard slot2) {
        this.slot2 = slot2;
    }

    public ItemTileCard getSlot3() {
        return slot3;
    }

    public void setSlot3(ItemTileCard slot3) {
        this.slot3 = slot3;
    }

    public int getNumCards() {
        return numCards;
    }

    public void setNumCards(int numCards) {
        this.numCards = numCards;
    }
}
