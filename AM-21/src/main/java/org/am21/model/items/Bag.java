package org.am21.model.items;

import org.am21.model.Cards.ItemCard;
import org.am21.model.Match;
import org.am21.utilities.BoardUtil;
import org.am21.utilities.CardUtil;
import java.util.List;

public class Bag {
    public Match match;
    private List<ItemCard> itemCollection;
    public int bagIndex;

    /**
     * Bag constructor:
     * When initialized, Bag will create the ItemCollection list, which will store all the items available
     * The cards are already shuffled.
     * @param match
     */
    public Bag(Match match){
        this.match = match;
        this.bagIndex=0;
        //Filling the bag completely
        this.itemCollection = CardUtil.buildItemTileCard();

//        System.out.println("Match > Bag completely filled with "+ itemCollection.size());
    }

    /**
     * This method return itemCollection reference
     * @return
     */
    public List<ItemCard> getDeck() {

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
            BoardUtil.refillBoard(match.board);

//            System.out.println("Bag > Refill completed");
//            System.out.println("Bag > Items remaining: "+ (this.itemCollection.size()-bagIndex));
            return true;
        }
    }
}