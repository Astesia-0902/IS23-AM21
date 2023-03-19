package org.am21.model.items;

import org.am21.model.items.Card.ItemTileCard;

public class Bag {
    private int itemNum; //initial is 132 item
    //private Collection<ItemTileCard> itemCollection;
    public int requestNum;
    public Bag(){
        this.itemNum = 132;
        //this.itemCollection = new ItemTileCard();
    }

    public int getRequestNum(){
        return requestNum;
    }

    public void drawItem(){
        this.itemNum -= requestNum;
        //pick the different card by ItemTileCard
    }
}

