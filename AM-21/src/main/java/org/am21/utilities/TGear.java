package org.am21.utilities;

import org.am21.model.Match;
import org.am21.model.Player;
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

    public static void spacer(){
        System.out.println("-----------");
    }
    public static void viewStats(Match m, int nRound){
        spacer();
        System.out.println("Match[!][!] > Game Stats");
        System.out.println("Num round: ["+(nRound+1)+"]");
        System.out.println("Winner is: "+m.getFirstToComplete().getName());
        System.out.println("Bag items: ["+(m.bag.getDeck().size()-m.bag.bagIndex)+"]");
        System.out.println("Bag index: ["+m.bag.bagIndex+"]");
        System.out.println("Player/Shelf/Hand situation:" );
        for(Player x: m.playerList){

            System.out.print(x.getName()+"["+x.shelf.getTotSlotAvail() +"]--");
            System.out.println("Number of clicks:["+x.tmp_clicks+"]");
            System.out.print("Limit:["+x.shelf.insertLimit +"]\n");

            System.out.println("Personal goals completed: " + x.getMyPersonalGoal().checkGoal());
            System.out.println("Score obtained: " + x.getMyPersonalGoal().calculatePoints());

            printThisShelf(x.shelf);
        }
        System.out.println();
        printThisBoard(m.livingRoomBoard);
        spacer();






    }
}
