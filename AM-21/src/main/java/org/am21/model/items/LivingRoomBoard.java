package org.am21.model.items;

import org.am21.model.Hand;
import org.am21.model.Match;
import org.am21.model.items.Card.ItemTileCard;
import org.am21.util.BoardUtil;
import org.am21.util.Coordinates;

import java.util.List;

public class LivingRoomBoard extends Grid{

    /** The number of required cards depends on the number of players */
    private int numPlayer;

    public Match match;

    /**
     * Construction of the LivingRoom:
     * - initialize cells of the grid
     * - set number of Players
     * - building the Board with all the item
     * @param rowNum
     * @param colNum
     * @param numPlayer
     * @param match
     */
    public LivingRoomBoard(int rowNum, int colNum, int numPlayer,Match match) {
        super(rowNum, colNum);
        this.numPlayer = numPlayer;
        this.match = match;
        BoardUtil.buildLivingRoom(this,match.bag.getItemCollection());
    }

    /**
     * Setting the size of the grid according to the number of player
    **/
    public int getSize() {
        if(numPlayer == 2)
            return 29;
        else if(numPlayer == 3)
            return 37;
        else
            return 45;
    }

    /**
     * Verify cell occupancy
     * @param r
     * @param c
     * @return
     */
    public boolean isTaken(int r, int c){
        if(getCells()[r][c].getItemTileCard()!=null)
            return true;
        else
            return false;
    }

    /**
     * Verify if the Item(r,c) is available to the player to pick.
     * To be available, it needs to have at least one side free and also,
     * if the player pick more items, they need to form a straight line
     * Index (0-rowNum-1,0-colNum-1)
     * Checking four side of the item(r,c) if they are free
     *
     * Could be improved, maybe need to be more efficient
     * @param r
     * @param c
     * @return
     */
    public boolean isSelectable(int r,int c){

        if(r+1<rowNum && !isTaken(r+1,c)){
            return true;
        }else if(r-1>=0 && !isTaken(r-1,c)) {
            return true;
        }else if(c+1<colNum && !isTaken(r,c+1)){
            return true;
        }else if(c-1>=0 && !isTaken(r,c-1)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * The selected tiles need to be on a Straight Line
     * According to distance
     * @param r
     * @param c
     * @param pHand
     * @param distance
     * @return
     */
    public boolean isOrthogonal(int r, int c, Hand pHand, int distance){

        List<Coordinates> tmp = pHand.getSlot();

        if(r - tmp.get(distance).x == -distance && c - tmp.get(distance).y == 0){
            /**then the card is founded in up(north)*/
            return true;

        }else if(r- tmp.get(distance).x == 0 && c- tmp.get(distance).y == distance){
            //Allora la carta si trova a destra(east)
            return true;

        }else if(r-tmp.get(distance).x == distance && c-tmp.get(distance).y ==0){
            //Allora la carta si trova sotto(south)
            return true;
        }else if(r-tmp.get(distance).x == 0 && c-tmp.get(distance).y== -distance){
            //Allora la carta si trova a sinistra(west)
            return true;
        }else{
            return false;
        }
    }

    /**
     * Obtain Item's Reference
     * @param r
     * @param c
     * @return
     */
    public ItemTileCard getItem(int r,int c){
        return getCells()[r][c].getItemTileCard();
    }

    /**
     * Checking if all the item in grid is isolated.
     * @return if true, the Board needs to be refilled
     */
    public boolean isSingle(){
        /** register all adjacent state of single cell
         * (if all center up, center down, center left, center right is null or dark then return true)
         * */
        for(int row=0; row<9; row++)
        {
            for(int col=0;col<9;col++)
            {
               if(this.getItemName(row, col)!=null && this.getCells()[row][col].isDark()==false)
               {
                   String left = this.getItemName(row, col-1);
                   String right = this.getItemName(row, col+1);
                   String up = this.getItemName(row+1, col);
                   String down = this.getItemName(row-1, col);
                   if(left!=null|| right!=null|| up!=null|| down!=null)
                       return false;
               }
            }
        }
        return true;
    }

    /**ask bag to fill the cell ?? **/

    public Bag requestBag(){

        return null;
    }
}

