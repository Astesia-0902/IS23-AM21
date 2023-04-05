package org.am21.utilities;

import org.am21.model.items.Bag;
import org.am21.model.Cards.ItemTileCard;
import org.am21.model.items.Cell;
import org.am21.model.items.Board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BoardUtil {

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
     */

    public static boolean boardBuilder(Board board, List<ItemTileCard> itemTileCards){
        int index = 0;
        for(int i = 0; i<board.gRow; i++){
            for(int j = 0; j<board.gColumn; j++){
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

    public static List<Coordinates> boardBounder(int maxSeats){
        List<Coordinates> boundaries = new ArrayList<>();

        switch (maxSeats){
            case 2: Collections.addAll(boundaries,
                        new Coordinates(-1,-1),
                        new Coordinates(3,4),
                        new Coordinates(3,5),
                        new Coordinates(2,7),
                        new Coordinates(1,7),
                        new Coordinates(1,6),
                        new Coordinates(3,5),
                        new Coordinates(4,5),
                        new Coordinates(-1,-1));
                break;
            case 3: Collections.addAll(boundaries,
                    new Coordinates(3,3),
                    new Coordinates(3,4),
                    new Coordinates(2,6),
                    new Coordinates(2,8),
                    new Coordinates(1,7),
                    new Coordinates(0,6),
                    new Coordinates(2,6),
                    new Coordinates(4,5),
                    new Coordinates(5,5));
                break;
            case 4: Collections.addAll(boundaries,
                    new Coordinates(3,4),
                    new Coordinates(3,5),
                    new Coordinates(2,6),
                    new Coordinates(1,8),
                    new Coordinates(0,8),
                    new Coordinates(0,7),
                    new Coordinates(2,6),
                    new Coordinates(3,5),
                    new Coordinates(4,5));
                break;
        }
        return boundaries;
    }

    public static boolean boardBuilder2(Board b, List<ItemTileCard> itemList){
        int index=0;
        List<Coordinates> x = b.boundaries;
        boolean state = true;

        for(int i=0;i<b.gRow;i++){
            for(int j=x.get(i).x;j<x.get(i).y;j++){
                Cell cell = new Cell();
                cell.setItem(itemList.get(index++));
                if(cell.getItem()==null){
                    state=false;
                }
                b.getCellGrid()[i][j] = cell;
            }

        }
        return state;
    }







    /**
     * This method will be called by the Bag when the cards needed to refill the board are enough
     * The pre-condition is LivingBoard.isSingle() is true (every card in the board is isolated)
     * The method-chain is initialized by Match
     *
     *
     *
     *
     *
     * @param board
     * @param bag
     */
    public static void refillBoard(Board board, Bag bag) {
        Cell[][] tmp = board.getCellGrid();

        for (int i = 0; i < board.gRow; i++) {
            for (int j = 0; j < board.gColumn; j++) {
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
