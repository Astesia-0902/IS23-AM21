package org.am21;

import org.am21.model.Match;
import org.am21.model.items.LivingRoomBoard;
import org.junit.jupiter.api.Test;

class MatchTest {

    @Test
    void run(){
        Match match = new Match(4);
        System.out.println("Number of players:" + match.maxSeats);
        System.out.println("Board's size:" + match.livingRoomBoard.getSize());
    }
    @Test
    static void printBoard(){
        Match match = new Match(2);
        LivingRoomBoard board = match.livingRoomBoard;
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(board.getCells()[i][j]==null){
                    System.out.print("[_________._]");
                }else if(board.getCells()[i][j]!=null){
                    System.out.print("["+ board.getCells()[i][j].getItemTileCard().getNameCard() +"]");
                }

            }
            System.out.println("");
        }


    }

    @Test
    static void printThisBoard(LivingRoomBoard board){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(board.getCells()[i][j].getItemTileCard()==null){
                    System.out.print("[_________._]");
                }else if(board.getCells()[i][j].getItemTileCard()!=null){
                    System.out.print("["+ board.getCells()[i][j].getItemTileCard().getNameCard() +"]");
                }

            }
            System.out.println("");
        }
    }


}
