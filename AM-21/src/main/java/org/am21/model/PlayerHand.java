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



    


}
