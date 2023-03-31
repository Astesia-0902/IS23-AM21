package org.am21.model.items;

import org.am21.model.items.Card.ItemTileCard;

public class LivingRoomBoard extends Grid{
    private int numPlayer;
    /** The number of cards required varies according to the number of players matrix 9*9*/
    public Grid gameBoard;


    public LivingRoomBoard(int rowNum, int colNum, int numPlayer) {
        super(rowNum, colNum);
        this.numPlayer = numPlayer;
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
}

