package org.am21.util;

import org.am21.model.Match;
import org.am21.model.items.Card.ItemTileCard;
import org.am21.model.items.Cell;
import org.am21.model.items.LivingRoomBoard;

import java.util.List;

public class GridUtil {
    public static LivingRoomBoard buildLivingRoomBoard(LivingRoomBoard livingRoomBoard, List<ItemTileCard> itemTileCards){
        Cell[][] cells = livingRoomBoard.getCells();
        int index = 0;

        if (livingRoomBoard.getSize() == 29) {
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
        } else if (livingRoomBoard.getSize() == 37) {
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
        } else if (livingRoomBoard.getSize() == 45) {
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

        return livingRoomBoard;
    }
}
