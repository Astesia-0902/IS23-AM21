package org.am21.model.items;

import org.am21.model.Cards.ItemCard;
import org.am21.model.GameManager;
import org.am21.model.enumer.ServerMessage;
import org.am21.utilities.CardUtil;
import org.am21.utilities.Coordinates;

import java.util.List;


public class Bag {
    public Board board;
    private List<ItemCard> itemCollection;
    public int bagIndex;

    /**
     * Bag constructor:
     * When initialized, Bag will create the ItemCollection list, which will store all the items available
     * The cards are already shuffled.
     * @param board match's board
     */
    public Bag(Board board){
        this.bagIndex=0;
        this.board=board;
        //Filling the bag completely
        this.itemCollection = CardUtil.buildItemTileCard();

    }

    /**
     * This method return itemCollection reference
     * @return itemCollection
     */
    public List<ItemCard> getDeck() {

        return itemCollection;
    }


    /**
     * This method will be called by the Bag when the cards needed to refill the board are valid
     */
    public boolean refillBoard() {
        if((itemCollection.size()-bagIndex)==0){
            board.match.sendTextToAll(ServerMessage.BagEmpty.value(), true);
            board.match.sendNotificationToAll(true);
            return false;
        }
        List<Coordinates> borders = board.boundaries;
        int k=0;//parameter to shrink the border array at the extremities
        if(board.maxSeats == 2){
            k=1;
        }

        for(int i = k; i<Board.BOARD_ROW -k; i++){
            for(int j = borders.get(i).x; j<= borders.get(i).y; j++){

                if (!board.isOccupied(i, j)) {
                    if ((itemCollection.size()-bagIndex)>0) {
                        board.setCell(i, j, itemCollection.get(bagIndex));
                        bagIndex++;

                    }
                }
            }
        }
        return true;
    }
}