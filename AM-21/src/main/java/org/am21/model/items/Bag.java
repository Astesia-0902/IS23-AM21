package org.am21.model.items;

import org.am21.model.items.Card.ItemTileCard;

public class Bag {
    private int itemNum; //initial is 132 item
    //private Collection<ItemTileCard> itemCollection;

    public Bag(){
        this.itemNum = 132;
        //this.itemCollection = new ItemTileCard();
    }

    public void addItem(){
        //once a game (132 item)
        this.itemNum=132;
    }

    public void drawItem(int requestNum){
        this.itemNum -=requestNum;
        //pick the the different card by ItemTileCard
    }
}
