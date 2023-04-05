package org.am21.model.items;

import org.am21.model.Cards.ItemTileCard;
import org.am21.model.Match;
import org.am21.utilities.BoardUtil;
import org.am21.utilities.CardUtil;

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
        match.bag = this;
        this.itemCollection = CardUtil.buildItemTileCard();
        /** fill the item initialy **/
//        System.out.println("Match > Bag completely filled with "+ itemCollection.size());
    }

    /**
     * This method return itemCollection reference
     * @return
     */
    public List<ItemTileCard> getDeck() {
        return itemCollection;
    }

    /**
     * Match call refillRequest when LivingRoomBoard.isSingle() is true.
     * This method will verify if there is any item in the Bag.
     * If so, then it will call the GridUtil.refillBoard() method.
     * @return true: if there are item added to the board.
     */
    public boolean refillRequest(){
        if((this.itemCollection.size()-bagIndex)==0){
//            System.out.println("Bag > Bag empty. No more refill");
            return false;
        }else {
//            System.out.println("Bag > Accessing Bag...");
            BoardUtil.refillBoard(match.board, this);
//            System.out.println("Bag > Refill completed");
//            System.out.println("Bag > Items remaining: "+ (this.itemCollection.size()-bagIndex));
            return true;
        }
    }



}

