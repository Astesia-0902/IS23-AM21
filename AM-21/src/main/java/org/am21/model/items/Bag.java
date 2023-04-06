package org.am21.model.items;

import org.am21.model.Cards.ItemTileCard;
import org.am21.model.Match;
import org.am21.utilities.BoardUtil;
import org.am21.utilities.CardUtil;
import java.util.List;

public class Bag {
    public Match match;
    private List<ItemTileCard> itemCollection;
    public int bagIndex;

    /**
     * Bag constructor:
     * When initialized, Bag will create the ItemCollection list, which will store all the items available
     * The cards are already shuffled.
     */
    public Bag(Match match){
        this.match = match;
        this.bagIndex=0;
        this.itemCollection = CardUtil.buildItemTileCard();
        /** fill the item initially **/
//        System.out.println("Match > Bag completely filled with "+ itemCollection.size());
    }


    public List<ItemTileCard> getDeck() {
        /**
         * This method return itemCollection reference
         * @return
         */
        return itemCollection;
    }

    public boolean refillRequest(){
        /**
         * Match call refillRequest when LivingRoomBoard.isSingle() is true.
         * This method will verify if there is any item in the Bag.
         * If so, then it will call the GridUtil.refillBoard() method.
         * @return true: if there are item added to the board.
         */
        if((this.itemCollection.size()-bagIndex)==0){
//            System.out.println("Bag > Bag empty. No more refill");
            return false;
        }else {
//            System.out.println("Bag > Accessing Bag...");
            BoardUtil.refillBoard(match.board);

//            System.out.println("Bag > Refill completed");
//            System.out.println("Bag > Items remaining: "+ (this.itemCollection.size()-bagIndex));
            return true;
        }
    }
}