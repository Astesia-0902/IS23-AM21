package org.am21;

import org.am21.model.Match;
import org.am21.model.items.LivingRoomBoard;
import org.testng.annotations.Test;

class MatchTest {
    @Test
    void run(){
        Match match = createMatch(3);
        printThisBoard(match.livingRoomBoard);

    }

    @Test
    static Match createMatch(int numSeats){
        System.out.println("Creating new match...");
        Match match = new Match(numSeats);
        System.out.println("Number of seats:" + match.maxSeats);
        System.out.println("Match created.");
        return match;
    }
    @Test
    static void printBoard(){
        Match match = createMatch(2);
        LivingRoomBoard board = match.livingRoomBoard;
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(board.getCellGrid()[i][j].getItem()==null){
                    System.out.print("[_________._]");
                }else if(board.getCellGrid()[i][j]!=null){
                    System.out.print("["+ board.getCellGrid()[i][j].getItem().getNameCard() +"]");
                }

            }
            System.out.println("");
        }


    }


    @Test
    static void printThisBoard(LivingRoomBoard board){
        System.out.println("Board:");
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(board.getCellGrid()[i][j].getItem()==null){
                    System.out.print("[_________._]");
                }else if(board.getCellGrid()[i][j].getItem()!=null){
                    System.out.print("["+ board.getCellGrid()[i][j].getItem().getNameCard() +"]");
                }

            }
            System.out.println("");
        }
    }


}
