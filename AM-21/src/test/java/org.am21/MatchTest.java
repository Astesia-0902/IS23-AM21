package org.am21;

import org.am21.controller.PlayerController;
import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.model.items.Bag;
import org.am21.model.items.CommonGoal;
import org.am21.model.items.LivingRoomBoard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class MatchTest {
    @Test
    void run(){
        Match match = createMatch(4);
        PlayerController pCtrl1 = PlayerControllerTest.createPlayerController("Ambrogio");
        Player player1 = pCtrl1.player;
        match.addPlayer(player1);
        PlayerController pCtrl2 = PlayerControllerTest.createPlayerController("Ambra");
        Player player2 = pCtrl2.player;
        match.addPlayer(player2);
        PlayerController pCtrl3 = PlayerControllerTest.createPlayerController("Anna");
        Player player3 = pCtrl3.player;
        match.addPlayer(player3);
        PlayerController pCtrl4 = PlayerControllerTest.createPlayerController("Andrea");
        Player player4 = pCtrl4.player;
        match.addPlayer(player4);

        printfThisBag(match.bag);
        printCommGoals(match.commonGoals);
        printPersonalGoals(match.playerList);

//        while(true){
//
//        }
        printScoringTokens(match.commonGoals);
    }

    @DisplayName("Match creation")
    static Match createMatch(int numSeats){
        System.out.println("Game > Creating new match...");
        Match match = new Match(numSeats);
        System.out.println("Match > Number of seats:" + match.maxSeats);
        System.out.println("Game > Match created.");
        return match;
    }
    @DisplayName("Print Board")
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


    @DisplayName("Print Board")
    static void printThisBoard(LivingRoomBoard board){
        System.out.println("Match > Board:");
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

    @DisplayName("Print Bag")
    static void printfThisBag(Bag bag){
        int count=0;
        System.out.println("Match > Bag:");
        for(int i= bag.bagIndex; i<bag.getItemCollection().size();i++){
            count++;
            System.out.print("["+bag.getItemCollection().get(i).getNameCard()+"]\t");
        }
        System.out.println(count);

    }

    @DisplayName("Print CommGoals")
    static void printCommGoals(List<CommonGoal> commonGoalList){
        System.out.println("Match > CommGoal:");
        for(CommonGoal x: commonGoalList){
            System.out.println(x.getNameCard());
        }
    }

    @DisplayName("Print PersonalGoal")
    static void printPersonalGoals(List<Player> pList){
        System.out.println("Match[Hide] > Personal:");
        for(Player x: pList){
            System.out.println("Match[Hide] > "+x.getName()+"'s goal is:"+x.getMyPersonalGoal().getNameCard());
        }
    }

    @DisplayName("Print ScoringTokens")
    static void printScoringTokens(List<CommonGoal> commonGoalList){
        System.out.println("Match[Hide] > ScoringToken:");
        for(CommonGoal x: commonGoalList){
            System.out.println(x.getNameCard() + "ScoringTokens List: ");
            for (int i = 0; i < x.tokenStack.size(); i++) {
                System.out.println(x.tokenStack.get(i).getNameCard());
            }
        }
    }


}
