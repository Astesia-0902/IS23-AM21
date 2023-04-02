package org.am21;

import org.am21.controller.PlayerController;
import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.model.items.Bag;
import org.am21.model.items.CommonGoal;
import org.am21.model.items.LivingRoomBoard;
import org.junit.jupiter.api.Test;

import java.util.List;

class MatchTest {
    @Test
    void run(){
        Match match = createMatch(2);
        PlayerController pCtrl1 = PlayerControllerTest.createPlayerController("Ambrogio");
        Player player1 = pCtrl1.player;
        match.addPlayer(player1);
        PlayerController pCtrl2 = PlayerControllerTest.createPlayerController("Ambra");
        Player player2 = pCtrl2.player;
        match.addPlayer(player2);
        match.matchStart();
        printfThisBag(match.bag);
        printCommGoals(match.commonGoals);
        printPersonalGoals(match.playerList);
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

    static void printfThisBag(Bag bag){
        int count=0;
        System.out.println("Bag:");
        for(int i= bag.bagIndex; i<bag.getItemCollection().size();i++){
            count++;
            System.out.println("["+bag.getItemCollection().get(i).getNameCard()+"]");
        }
        System.out.println(count);

    }

    static void printCommGoals(List<CommonGoal> commonGoalListst){
        System.out.println("CommGoal:");
        for(CommonGoal x: commonGoalListst){
            System.out.println(x.getNameCard());
        }
    }

    static void printPersonalGoals(List<Player> pList){
        System.out.println("Personal:");
        for(Player x: pList){
            System.out.println(x.getName()+"'s goal is:"+x.getMyPersonalGoal().getNameCard());
        }
    }


}
