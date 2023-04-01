package org.am21.util;

import org.am21.model.items.Bag;
import org.am21.model.items.Card.ItemTileCard;
import org.am21.model.items.Cell;
import org.am21.model.items.LivingRoomBoard;

import java.util.List;

public class BoardUtil {
    /**
     * LivingRoom builder is going to fill the board's Cells for the first time.
     * @param board
     * @param itemTileCards
     *
     * @return
     */
    public static LivingRoomBoard buildLivingRoom(LivingRoomBoard board, List<ItemTileCard> itemTileCards){
        Cell[][] cells = board.getCells();
        int index = 0;

        if (board.getSize() == 29) {
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
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    Cell cell = new Cell();
                    if (i == 0 || i == 8 || j == 0 || j == 8 ||
                            (i == 1) && ((j == 1) || (j == 2) || (j == 3) || (j == 6) || (j == 7)) ||
                            ((i == 2) || (i == 6)) && ((j == 1) || (j == 2) || (j == 6) || (j == 7)) ||
                            (i == 3) && (j == 7) ||
                            (i == 5) && (j == 1) ||
                            (i == 7) && ((j == 1) || (j == 2) || (j == 5) || (j == 6) || (j == 7))) {
                       /** default prohibit during all time of the game (dark cell)*/
                        cell.setDark(true);
                    }
                    else{
                        if (cell.getItemTileCard() == null) {
                            ItemTileCard itemTileCard = itemTileCards.get(index++);
                            cell.setItemTileCard(itemTileCard);
                            cells[i][j] = cell;
                        }
                    }

                }
            }
        } else if (board.getSize() == 37) {
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
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    Cell cell = new Cell();
                    if ((i == 0) && (j != 5) ||
                            (i == 1) && ((j == 0) || (j == 1) || (j == 2) || (j == 3) || (j == 6) || (j == 7) || (j == 8)) ||
                            (i == 2) && ((j == 0) || (j == 1) || (j == 7) || (j == 8)) ||
                            (i == 3) && ((j == 7) || (j == 8)) ||
                            (i == 4) && ((j == 0) || (j == 8)) ||
                            (i == 5) && ((j == 0) || (j == 1)) ||
                            (i == 6) && ((j == 0) || (j == 1) || (j == 7) || (j == 8)) ||
                            (i == 7) && ((j == 0) || (j == 1) || (j == 2) || (j == 5) || (j == 6) || (j == 7) || (j == 8)) ||
                            (i == 8) && (j != 3)){
                        /** default prohibit during all time of the game (dark cell)*/
                        cell.setDark(true);
                    }
                    else {
                        if (cell.getItemTileCard() == null) {
                            ItemTileCard itemTileCard = itemTileCards.get(index++);
                            cell.setItemTileCard(itemTileCard);
                            cells[i][j] = cell;
                        }
                    }
                }
            }
        } else if (board.getSize() == 45) {
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
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    Cell cell = new Cell();
                    if ((i == 0) && ((j == 0) || (j == 1) || (j == 2) || (j == 3) || (j == 6) || (j == 7) || (j == 8)) ||
                            (i == 1) && ((j == 0)||(j == 1)||(j == 2)||(j == 6)||(j == 7)||(j == 8)) ||
                            (i == 2) && ((j == 0) || (j == 1) || (j == 7) || (j == 8)) ||
                            (i == 3) && (j == 8)||
                            (i == 5) && (j == 0)||
                            (i == 6) && ((j == 0) || (j == 1) || (j == 7) || (j == 8)) ||
                            (i == 7) && ((j == 0)||(j == 1)||(j == 2) || (j == 6) || (j == 7) || (j == 8)) ||
                            (i == 8) && ((j == 0) || (j == 1) || (j == 2) || (j == 5) || (j == 6) || (j == 7) || (j == 8))){
                        /** default prohibit during all time of the game (dark cell)*/
                        cell.setDark(true);
                    }
                    else{
                        if (cell.getItemTileCard() == null) {
                            ItemTileCard itemTileCard = itemTileCards.get(index++);
                            cell.setItemTileCard(itemTileCard);
                            cells[i][j] = cell;
                        }
                    }
                }
            }
        }

        return board;
    }

    /**
     * This method will be called by the Bag when the cards needed to refill the board are enough
     * The pre-condition is LivingBoard.isSingle() is true (every card in the board is isolated)
     * The method-chain is initiliazed by Match
     * @param board
     * @param bag
     */
    public static void refillBoard(LivingRoomBoard board, Bag bag){
        Cell[][] cells = board.getCells();

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                Cell cell = new Cell();
                if (cells[i][j].isDark()==false && !board.isTaken(i,j)) {
                    if(bag.getItemCollection().size()>0) {
                        //Cell can be filled
                        bag.getItemCollection().get(0);
                        bag.getItemCollection().remove(0);
                    }
                }
            }
        }

    }
}
