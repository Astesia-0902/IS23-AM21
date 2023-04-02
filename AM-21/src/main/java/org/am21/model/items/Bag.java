package org.am21.model.items;

import org.am21.model.Match;
import org.am21.model.items.Card.ItemTileCard;
import org.am21.utilities.CardUtil;
import org.am21.utilities.BoardUtil;

import java.util.HashMap;
import java.util.List;

public class Bag {
    public Match match;
//    private int itemNum; //initial is 132 item
    private List<ItemTileCard> itemCollection;
    public int bagIndex;

    /**
     * 'request' may not be needed
     */

    private final static HashMap<Integer, Integer> request = new HashMap<>();
    static {
        request.put(2,29);
        request.put(3,37);
        request.put(4,45);
    }

    /**
     * Bag constructor:
     * When initialized, Bag will create the ItemCollection list, which will store all the items available
     * The cards are already shuffled.
     */
    public Bag(Match match){
        this.match = match;
        //this.itemNum = 132;
        this.itemCollection = CardUtil.buildItemTileCard();
        /** fill the item initialy **/
    }

    /**
     * This method return itemCollection reference
     * @return
     */
    public List<ItemTileCard> getItemCollection() {
        return itemCollection;
    }

    public void setItemCollection(int playerNum) {
        for (int i = 0; i < request.get(playerNum); i++) {
            itemCollection.remove(i);
        }
    }

    /**
     * Match call refillRequest when LivingRoomBoard.isSingle() is true.
     * This method will verify if there is any item in the Bag.
     * If so, then it will call the GridUtil.refillBoard() method.
     * @return true: if there are item added to the board.
     */
    public boolean refillRequest(){
        if(itemCollection.size()==0){
            return false;
        }else {
            BoardUtil.refillBoard(match.livingRoomBoard, this);
            return true;
        }
    }



}

