package org.am21.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BoardUtil {

    /*
     *  2Players:
     *              *0: [ ][ ][ ][ ][ ][ ][ ][ ][ ]
     *              *1: [ ][ ][ ][*][*][ ][ ][ ][ ]
     *              *2: [ ][ ][ ][*][*][*][ ][ ][ ]
     *              *3: [ ][ ][*][*][*][*][*][*][ ]
     *              *4: [ ][*][*][*][*][*][*][*][ ]
     *              *5: [ ][*][*][*][*][*][*][ ][ ]
     *              *6: [ ][ ][ ][*][*][*][ ][ ][ ]
     *              *7: [ ][ ][ ][ ][*][*][ ][ ][ ]
     *              *8: [ ][ ][ ][ ][ ][ ][ ][ ][ ]
     *              *
     *  3Players:
     *              *0: [ ][ ][ ][+][ ][ ][ ][ ][ ]
     *              *1: [ ][ ][ ][*][*][ ][ ][ ][ ]
     *              *2: [ ][ ][+][*][*][*][+][ ][ ]
     *              *3: [ ][ ][*][*][*][*][*][*][+]
     *              *4: [ ][*][*][*][*][*][*][*][ ]
     *              *5: [+][*][*][*][*][*][*][ ][ ]
     *              *6: [ ][ ][+][*][*][*][+][ ][ ]
     *              *7: [ ][ ][ ][ ][*][*][ ][ ][ ]
     *              *8: [ ][ ][ ][ ][ ][+][ ][ ][ ]
     *              *
     *  4Players:
     *              *0: [ ][ ][ ][*][+][ ][ ][ ][ ]
     *              *1: [ ][ ][ ][*][*][+][ ][ ][ ]
     *              *2: [ ][ ][*][*][*][*][*][ ][ ]
     *              *3: [ ][+][*][*][*][*][*][*][*]
     *              *4: [+][*][*][*][*][*][*][*][+]
     *              *5: [*][*][*][*][*][*][*][+][ ]
     *              *6: [ ][ ][*][*][*][*][*][ ][ ]
     *              *7: [ ][ ][ ][+][*][*][ ][ ][ ]
     *              *8: [ ][ ][ ][ ][+][*][ ][ ][ ]
     */



    public static List<Coordinates> boardBounder(int maxSeats){
        List<Coordinates> boundaries = new ArrayList<>();

        switch (maxSeats){
            case 2: Collections.addAll(boundaries,
                        null,  //!!!!
                        new Coordinates(3,4),
                        new Coordinates(3,5),
                        new Coordinates(2,7),
                        new Coordinates(1,7),
                        new Coordinates(1,6),
                        new Coordinates(3,5),
                        new Coordinates(4,5),
                        null);
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



}












/*public static boolean boardBuilder(Board board, List<ItemTileCard> itemTileCards){
        int index = 0;
        for(int i = 0; i<board.gRow; i++){
            for(int j = 0; j<board.gColumn; j++){
                ItemTileCard cell;
                if(board.getSize()==29){
                    if (i == 0 || i == 8 || j == 0 || j == 8 || (i == 1) &&
                            ((j == 1) || (j == 2) || (j == 3) || (j == 6) || (j == 7)) ||
                            ((i == 2) || (i == 6)) && ((j == 1) || (j == 2) || (j == 6) || (j == 7))
                            || (i == 3) && (j == 7) || (i == 5) && (j == 1) ||
                            (i == 7) && ((j == 1) || (j == 2) || (j == 5) || (j == 6) || (j == 7))){

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
                            (i == 8) && (j != 3)){

                    }
                }else if(board.getSize() == 45){
                    if ((i == 0) && ((j == 0) || (j == 1) || (j == 2) || (j == 3) || (j == 6) || (j == 7) || (j == 8)) ||
                            (i == 1) && ((j == 0) || (j == 1) || (j == 2) || (j == 6) || (j == 7) || (j == 8)) ||
                            (i == 2) && ((j == 0) || (j == 1) || (j == 7) || (j == 8)) ||
                            (i == 3) && (j == 8) ||
                            (i == 5) && (j == 0) ||
                            (i == 6) && ((j == 0) || (j == 1) || (j == 7) || (j == 8)) ||
                            (i == 7) && ((j == 0) || (j == 1) || (j == 2) || (j == 6) || (j == 7) || (j == 8)) ||
                            (i == 8) && ((j == 0) || (j == 1) || (j == 2) || (j == 5) || (j == 6) || (j == 7) || (j == 8))){

                    }
                }else{
                    return false;
                    }
                cell = new ItemTileCard(itemTileCards.get(index++).getNameCard());
                board.getMatrix()[i][j] = cell;

            }
        }
        board.bag.bagIndex=index;
        return true;
    }*/