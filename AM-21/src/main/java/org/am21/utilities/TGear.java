package org.am21.utilities;

import org.am21.model.items.LivingRoomBoard;
import org.am21.model.items.Shelf;

/**
 * Temporary Class. Needed for Testing
 */
public class TGear {
    public static void printThisBoard(LivingRoomBoard board){
        System.out.println("Match > Board:");
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(board.getCellGrid()[i][j].isDark()==true){
                    System.out.print("[XXXXXXXXXX.]");
                }else if(board.getCellGrid()[i][j].getItem()==null){
                    System.out.print("[__________.]");
                }else if(board.getCellGrid()[i][j].getItem()!=null){
                    System.out.print("["+ board.getCellGrid()[i][j].getItem().getNameCard() +"]");
                }

            }
            System.out.println("");
        }
    }

    public static void printThisShelf(Shelf shelf){
        System.out.println("Match > "+shelf.player.getName()+"'s Shelf ["+shelf.insertLimit +"]:");
        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                if(shelf.getCellGrid()[i][j].getItem()==null){
                    System.out.print("[_________._]");
                }else if(shelf.getCellGrid()[i][j].getItem()!=null){
                    System.out.print("["+ shelf.getCellGrid()[i][j].getItem().getNameCard() +"]");
                }

            }
            System.out.println("");
        }

    }
}
