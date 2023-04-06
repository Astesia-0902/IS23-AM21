package org.am21.utilities;

import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.model.items.Board;
import org.am21.model.items.Shelf;

/**
 * Temporary Class. Needed for Testing
 */
public class TGear {
    public static void printThisBoard(Board board){
        System.out.println("Match > Board:");
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(board.getMatrix()[i][j]==null){
                    System.out.print("[_________.]");
                } else if(board.getMatrix()[i][j]!=null){
                    System.out.print("["+ board.getMatrix()[i][j].getNameCard() +"]");
                }

            }
            System.out.println("");
        }
    }

    public static void printThisShelf(Shelf shelf){
        System.out.println("Match > "+shelf.player.getName()+"'s Shelf ["+shelf.insertLimit +"]:");
        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                if(shelf.getMatrix()[i][j]==null){
                    System.out.print("[_________._]");
                }else if(shelf.getMatrix()[i][j]!=null){
                    System.out.print("["+ shelf.getMatrix()[i][j].getNameCard() +"]");
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
        System.out.println("Bag items: ["+(m.board.bag.getDeck().size()-m.board.bag.bagIndex)+"]");
        System.out.println("Bag index: ["+m.board.bag.bagIndex+"]");
        System.out.println("Player/Shelf/Hand situation:" );
        for(Player x: m.playerList){

            System.out.print(x.getName()+"["+x.shelf.getTotSlotAvail() +"]--");
            System.out.print("Limit:["+x.shelf.insertLimit +"]\n");

            System.out.println("Score obtained from OwnGoal: " + x.getMyPersonalGoal().calculatePoints());

            printThisShelf(x.shelf);
        }
        System.out.println();
        printThisBoard(m.board);
        spacer();






    }
}
