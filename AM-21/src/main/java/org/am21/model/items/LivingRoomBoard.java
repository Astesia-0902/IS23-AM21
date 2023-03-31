package org.am21.model.items;

import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.model.items.Card.ItemTileCard;
import org.am21.util.Coordinates;

import java.util.List;

public class LivingRoomBoard extends Grid{
    private int numPlayer;
    /** The number of cards required varies according to the number of players matrix 9*9*/
    public Grid gameBoard;

    public Match match;


    public LivingRoomBoard(int rowNum, int colNum, int numPlayer,Match match) {
        super(rowNum, colNum);
        this.numPlayer = numPlayer;
        this.match = match;
        this.gameBoard = new Grid(9,9){};
        //default grid 9*9 all null
    }

    /**
     * assigned size of cell according the number of player
    **/

    public int getSize() {
        if(numPlayer == 2)
            return 29;
        else if(numPlayer == 3)
            return 37;
        else
            return 45;
    }

    public void setSize() {

        if (getSize() == 29) {
            /**
             *8: [][][][][][][][][]
             *7: [][][][*][*][][][][]
             *6: [][][][*][*][*][][][]
             *5: [][][*][*][*][*][*][*][]
             *4: [][*][*][*][*][*][*][*][]
             *3: [][*][*][*][*][*][*][][]
             *2: [][][][*][*][*][][][]
             *1: [][][][][*][*][][][]
             *0: [][][][][][][][][]
             * */
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (i == 0 || i == 8 || j == 0 || j == 8 ||
                            (i == 1) && ((j == 1) || (j == 2) || (j == 3) || (j == 6) || (j == 7)) ||
                            ((i == 2) || (i == 6)) && ((j == 1) || (j == 2) || (j == 6) || (j == 7)) ||
                            (i == 3) && (j == 7) ||
                            (i == 5) && (j == 1) ||
                            (i == 7) && ((j == 1) || (j == 2) || (j == 5) || (j == 6) || (j == 7)))
                        gameBoard.setCells(i, j, null);
                    //else random card fill
                }

            }
        } else if (getSize() == 37) {
            /**
             *8: [][][][+][][][][][]
             *7: [][][][*][*][][][][]
             *6: [][][+][*][*][*][+][][]
             *5: [][][*][*][*][*][*][*][+]
             *4: [][*][*][*][*][*][*][*][]
             *3: [+][*][*][*][*][*][*][][]
             *2: [][][+][*][*][*][+][][]
             *1: [][][][][*][*][][][]
             *0: [][][][][][+][][][]
             * */
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if ((i == 0) && (j != 5) ||
                            (i == 1) && ((j == 0) || (j == 1) || (j == 2) || (j == 3) || (j == 6) || (j == 7) || (j == 8)) ||
                            (i == 2) && ((j == 0) || (j == 1) || (j == 7) || (j == 8)) ||
                            (i == 3) && ((j == 7) || (j == 8)) ||
                            (i == 4) && ((j == 0) || (j == 8)) ||
                            (i == 5) && ((j == 0) || (j == 1)) ||
                            (i == 6) && ((j == 0) || (j == 1) || (j == 7) || (j == 8)) ||
                            (i == 7) && ((j == 0) || (j == 1) || (j == 2) || (j == 5) || (j == 6) || (j == 7) || (j == 8)) ||
                            (i == 8) && (j != 3))
                        gameBoard.setCells(i, j, null);
                    //else random card fill
                }

            }
        }


        if (getSize() == 45) {
            /**
             *8: [][][][*][+][][][][]
             *7: [][][][*][*][+][][][]
             *6: [][][*][*][*][*][*][][]
             *5: [][+][*][*][*][*][*][*][*]
             *4: [+][*][*][*][*][*][*][*][+]
             *3: [*][*][*][*][*][*][*][+][]
             *2: [][][*][*][*][*][*][][]
             *1: [][][][+][*][*][][][]
             *0: [][][][][+][*][][][]
             * */
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if ((i == 0) && ((j == 0) || (j == 1) || (j == 2) || (j == 3) || (j == 6) || (j == 7) || (j == 8)) ||
                            (i == 1) && ((j == 0)||(j == 1)||(j == 2)||(j == 6)||(j == 7)||(j == 8)) ||
                            (i == 2) && ((j == 0) || (j == 1) || (j == 7) || (j == 8)) ||
                            (i == 3) && (j == 8)||
                            (i == 5) && (j == 0)||
                            (i == 6) && ((j == 0) || (j == 1) || (j == 7) || (j == 8)) ||
                            (i == 7) && ((j == 0)||(j == 1)||(j == 2) || (j == 6) || (j == 7) || (j == 8)) ||
                            (i == 8) && ((j == 0) || (j == 1) || (j == 2) || (j == 5) || (j == 6) || (j == 7) || (j == 8)))
                        gameBoard.setCells(i, j, null);
                    //else random card fill
                }

            }
        }
    }



    /**ask bag to fill the cell**/








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
     * @param r
     * @param c
     * @param player
     * @return
     */
    public boolean isOrthogonal(int r, int c, Player player){

        List<Coordinates> tmp = player.hand.getSlot();



        return false;
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


}

