package org.am21.utilities;

import org.am21.model.items.Bag;
import org.am21.model.items.Card.ItemTileCard;
import org.am21.model.items.Cell;
import org.am21.model.items.LivingRoomBoard;

import java.util.List;

public class BoardUtil {

    public static boolean buildLivingRoom(LivingRoomBoard board, List<ItemTileCard> itemTileCards){
        int index = 0;
        for(int i=0;i<board.rowNum;i++){
            for(int j=0;j<board.colNum;j++){
                Cell newcell = new Cell();
                if(board.getSize()==29){
                    if (i == 0 || i == 8 || j == 0 || j == 8 || (i == 1) &&
                            ((j == 1) || (j == 2) || (j == 3) || (j == 6) || (j == 7)) ||
                            ((i == 2) || (i == 6)) && ((j == 1) || (j == 2) || (j == 6) || (j == 7))
                            || (i == 3) && (j == 7) || (i == 5) && (j == 1) ||
                            (i == 7) && ((j == 1) || (j == 2) || (j == 5) || (j == 6) || (j == 7)))
                    {
                        newcell.setDark(true);
                    }
                }else if(board.getSize()==37){
                    if ((i == 0) && (j != 5) ||
                            (i == 1) && ((j == 0) || (j == 1) || (j == 2) || (j == 3) || (j == 6) || (j == 7) || (j == 8)) ||
                            (i == 2) && ((j == 0) || (j == 1) || (j == 7) || (j == 8)) ||
                            (i == 3) && ((j == 7) || (j == 8)) ||
                            (i == 4) && ((j == 0) || (j == 8)) ||
                            (i == 5) && ((j == 0) || (j == 1)) ||
                            (i == 6) && ((j == 0) || (j == 1) || (j == 7) || (j == 8)) ||
                            (i == 7) && ((j == 0) || (j == 1) || (j == 2) || (j == 5) || (j == 6) || (j == 7) || (j == 8)) ||
                            (i == 8) && (j != 3))
                    {
                        newcell.setDark(true);
                    }
                }else if(board.getSize() == 45){
                    if ((i == 0) && ((j == 0) || (j == 1) || (j == 2) || (j == 3) || (j == 6) || (j == 7) || (j == 8)) ||
                            (i == 1) && ((j == 0) || (j == 1) || (j == 2) || (j == 6) || (j == 7) || (j == 8)) ||
                            (i == 2) && ((j == 0) || (j == 1) || (j == 7) || (j == 8)) ||
                            (i == 3) && (j == 8) ||
                            (i == 5) && (j == 0) ||
                            (i == 6) && ((j == 0) || (j == 1) || (j == 7) || (j == 8)) ||
                            (i == 7) && ((j == 0) || (j == 1) || (j == 2) || (j == 6) || (j == 7) || (j == 8)) ||
                            (i == 8) && ((j == 0) || (j == 1) || (j == 2) || (j == 5) || (j == 6) || (j == 7) || (j == 8)))
                    {
                        newcell.setDark(true);
                    }
                }else{
                    return false;
                }
                if(!newcell.isDark() && newcell.getItem() == null) {
                    newcell.setItem(itemTileCards.get(index++));
                }
                board.getCellGrid()[i][j] = newcell;

            }
        }
        board.match.bag.bagIndex=index;
        return true;
    }


    /**
     *  2Players:
     *              *8: [ ][ ][ ][ ][ ][ ][ ][ ][ ]
     *              *7: [ ][ ][ ][*][*][ ][ ][ ][ ]
     *              *6: [ ][ ][ ][*][*][*][ ][ ][ ]
     *              *5: [ ][ ][*][*][*][*][*][*][ ]
     *              *4: [ ][*][*][*][*][*][*][*][ ]
     *              *3: [ ][*][*][*][*][*][*][ ][ ]
     *              *2: [ ][ ][ ][*][*][*][ ][ ][ ]
     *              *1: [ ][ ][ ][ ][*][*][ ][ ][ ]
     *              *0: [ ][ ][ ][ ][ ][ ][ ][ ][ ]
     *              *
     *  3Players:
     *              *8: [ ][ ][ ][+][ ][ ][ ][ ][ ]
     *              *7: [ ][ ][ ][*][*][ ][ ][ ][ ]
     *              *6: [ ][ ][+][*][*][*][+][ ][ ]
     *              *5: [ ][ ][*][*][*][*][*][*][+]
     *              *4: [ ][*][*][*][*][*][*][*][ ]
     *              *3: [+][*][*][*][*][*][*][ ][ ]
     *              *2: [ ][ ][+][*][*][*][+][ ][ ]
     *              *1: [ ][ ][ ][ ][*][*][ ][ ][ ]
     *              *0: [ ][ ][ ][ ][ ][+][ ][ ][ ]
     *              *
     *  4Players:
     *              *8: [ ][ ][ ][*][+][ ][ ][ ][ ]
     *              *7: [ ][ ][ ][*][*][+][ ][ ][ ]
     *              *6: [ ][ ][*][*][*][*][*][ ][ ]
     *              *5: [ ][+][*][*][*][*][*][*][*]
     *              *4: [+][*][*][*][*][*][*][*][+]
     *              *3: [*][*][*][*][*][*][*][+][ ]
     *              *2: [ ][ ][*][*][*][*][*][ ][ ]
     *              *1: [ ][ ][ ][+][*][*][ ][ ][ ]
     *              *0: [ ][ ][ ][ ][+][*][ ][ ][ ]
     *              *
     * LivingRoom builder is going to fill the board's Cells for the first time.
     *
     * @param board
     * @param itemTileCards
     * @return
     */
    public static boolean xbuildLivingRoom(LivingRoomBoard board, List<ItemTileCard> itemTileCards) {
        Cell[][] cells = board.getCellGrid();
        int index = 0;

        if (board.getSize() == 29) {

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
                        cells[i][j] = cell;
                    } else {
                        if (cell.getItem() == null) {
                            ItemTileCard itemTileCard = itemTileCards.get(index++);
                            cell.setItem(itemTileCard);
                            cells[i][j] = cell;
                        }
                    }

                }
            }
        } else if (board.getSize() == 37) {

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
                            (i == 8) && (j != 3)) {
                        /** default prohibit during all time of the game (dark cell)*/
                        cell.setDark(true);
                        cells[i][j] = cell;
                    } else {
                        if (cell.getItem() == null) {
                            ItemTileCard itemTileCard = itemTileCards.get(index++);
                            cell.setItem(itemTileCard);
                            cells[i][j] = cell;
                        }
                    }
                }
            }
        } else if (board.getSize() == 45) {

            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    Cell cell = new Cell();
                    if ((i == 0) && ((j == 0) || (j == 1) || (j == 2) || (j == 3) || (j == 6) || (j == 7) || (j == 8)) ||
                            (i == 1) && ((j == 0) || (j == 1) || (j == 2) || (j == 6) || (j == 7) || (j == 8)) ||
                            (i == 2) && ((j == 0) || (j == 1) || (j == 7) || (j == 8)) ||
                            (i == 3) && (j == 8) ||
                            (i == 5) && (j == 0) ||
                            (i == 6) && ((j == 0) || (j == 1) || (j == 7) || (j == 8)) ||
                            (i == 7) && ((j == 0) || (j == 1) || (j == 2) || (j == 6) || (j == 7) || (j == 8)) ||
                            (i == 8) && ((j == 0) || (j == 1) || (j == 2) || (j == 5) || (j == 6) || (j == 7) || (j == 8))) {
                        /** default prohibit during all time of the game (dark cell)*/
                        cell.setDark(true);
                        cells[i][j] = cell;
                    } else {
                        if (cell.getItem() == null) {
                            ItemTileCard itemTileCard = itemTileCards.get(index++);
                            cell.setItem(itemTileCard);
                            cells[i][j] = cell;
                        }
                    }
                }
            }
        } else {
            return false;
        }

        return true;
    }

    /**
     * This method will be called by the Bag when the cards needed to refill the board are enough
     * The pre-condition is LivingBoard.isSingle() is true (every card in the board is isolated)
     * The method-chain is initiliazed by Match
     *
     *
     *
     * !!!Maybe remake order of refill: Do that from the center (5,5)
     *
     * @param board
     * @param bag
     */
    public static void refillBoard(LivingRoomBoard board, Bag bag) {
        Cell[][] tmp = board.getCellGrid();

        for (int i = 0; i < board.rowNum; i++) {
            for (int j = 0; j < board.colNum; j++) {
                Cell cell = new Cell();
                if (tmp[i][j].isDark() == false && !board.isOccupied(i, j)) {
                    if ((bag.getDeck().size()- bag.bagIndex )>0) {

                        board.insertInCell(i, j, bag.getDeck().get(bag.bagIndex));
                        bag.bagIndex++;

                    }
                }
            }
        }

    }
}
