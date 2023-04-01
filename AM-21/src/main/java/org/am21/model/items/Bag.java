package org.am21.model.items;

import org.am21.model.items.Card.ItemTileCard;
import org.am21.util.CardUtil;

import java.util.HashMap;
import java.util.List;

public class Bag {
    private int itemNum; //initial is 132 item
    private final List<ItemTileCard> itemCollection;
    public int requestNum;
    private final static HashMap<Integer, Integer> request = new HashMap<>();
    static {
        request.put(2,29);
        request.put(3,37);
        request.put(4,45);
    }

    public Bag(){
        this.itemNum = 132;
        this.itemCollection = CardUtil.buildItemTileCard();
        /** fill the item initialy **/
    }

    public List<ItemTileCard> getItemCollection() {
        return itemCollection;
    }

    public void setItemCollection(int playerNum) {
        for (int i = 0; i < request.get(playerNum); i++) {
            itemCollection.remove(i);
        }
    }

    public void addItem(int requestNum){
        this.itemNum = requestNum;
        //pick the Item sequencialy by the another class
        //this.itemCollection

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
            return false;

    }
}

