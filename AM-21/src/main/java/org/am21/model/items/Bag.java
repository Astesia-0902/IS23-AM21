package org.am21.model.items;

import org.am21.model.items.Card.ItemTileCard;

import java.util.ArrayList;

public class Bag {
    private int itemNum; //initial is 132 item

    private ArrayList<ItemTileCard> itemCollection;
    public int requestNum;
    public Bag(){
        this.itemNum = 132;
        this.itemCollection = new ArrayList<ItemTileCard> ();
        /** fill the item initialy **/
    }

    public void addItem() {
        /**once a game (132 item)**/
        this.itemNum = 132;

    }
    public void setRequestNum(int requestNum){
        this.requestNum = requestNum;
    }

    public boolean drawItem(){
        /**pick the different card by ItemTileCard**/
        if(itemNum>=requestNum) {
            this.itemNum -= requestNum;

            return true;

        }
        else
        {
            return false;
        }

    }
}

