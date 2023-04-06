package org.am21.extra;

import org.am21.model.items.Hand;
import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.model.items.Bag;
import org.am21.model.Cards.CommonGoal;
import org.am21.model.items.Board;
import org.am21.model.items.Shelf;
import org.am21.utilities.CardPointer;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.am21.extra.Gear.spacer;

public class Printer{

    public static void printThisBoard(Board board){
        System.out.println("Match > Board:");
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(board.getMatrix()[i][j]==null){
                    System.out.print("[XXXXXXXXXX.]");
                }else if(board.getMatrix()[i][j]==null){
                    System.out.print("[__________.]");
                }else if(board.getMatrix()[i][j]!=null){
                    System.out.print("["+ board.getMatrix()[i][j].getNameCard() +"]");
                }

            }
            System.out.println("");
        }
    }

    public static void printfThisBag(Bag bag){
        int count=0;
        System.out.println("Match > Bag:");
        for(int i = bag.bagIndex; i<bag.getDeck().size(); i++){
            count++;
            System.out.print("["+bag.getDeck().get(i).getNameCard()+"]\t");
            if(count%7==0){
                System.out.println();
            }
        }
        System.out.println("\nMatch > Cards in bag: "+count);

    }

    public static void printCommGoals(List<CommonGoal> commonGoalList){
        System.out.println("Match > CommGoal:");
        for(CommonGoal x: commonGoalList){
            System.out.println(x.getNameCard());
        }
    }

    public static void printPersonalGoals(List<Player> pList){
        System.out.println("Match[Hide] > Personal:");
        for(Player x: pList){
            System.out.println("Match[Hide] > "+x.getName()+"'s goal is:"+x.getMyPersonalGoal().getNameCard());
            printGoalShelf(x.getMyPersonalGoal().getGoalShelf());
        }
    }

    @DisplayName("Printf GoalShelf")
    public static void printGoalShelf(Shelf shelf){
        System.out.println("Match[Hide] > "+shelf.player.getName()+"'s PersonalGoal:");
        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                if(shelf.getMatrix()[i][j]==null){
                    System.out.print("[______._]");
                }else if(shelf.getMatrix()[i][j]!=null){
                    System.out.print("["+ shelf.getMatrix()[i][j].getNameCard() +"]");
                }

            }
            System.out.println("");
        }
    }

    public static void printScoringTokens(List<CommonGoal> commonGoalList){
        System.out.println("Match[Hide] > ScoringToken:");
        for(CommonGoal x: commonGoalList){
            System.out.println(x.getNameCard() + "ScoringTokens List: ");
            for (int i = 0; i < x.tokenStack.size(); i++) {
                System.out.println(x.tokenStack.get(i));
            }
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

    public static void showHand(Hand hand){
        System.out.println("--------");
        System.out.println("Match  > "+hand.player.getName() + "'s Hand ["+ hand.getSlot().size()+"]:");
        for(CardPointer x : hand.getSlot()) {
            if(x.item.getNameCard()!=null){
                System.out.print("["+x.item.getNameCard()+"]");
            }
        }
        System.out.println("");

    }

    public static void viewStats(Match m,int nRound){
        spacer();
        System.out.println("Match[!][!] > Game Stats");
        System.out.println("Num round: ["+(nRound+1)+"]");
        System.out.println("AI usage: ["+ Gear.counter_AI +"]");
        System.out.println("Bag items: ["+(m.board.bag.getDeck().size()-m.board.bag.bagIndex)+"]");
        System.out.println("Bag index: ["+m.board.bag.bagIndex+"]");
        System.out.println("Player/Shelf/Hand situation:" );
        for(Player x: m.playerList){

            System.out.print(x.getName()+"["+x.shelf.getTotSlotAvail() +"]--");
            System.out.print("Limit:["+x.shelf.insertLimit +"]\n");
            printThisShelf(x.shelf);
        }
        System.out.println();
        printThisBoard(m.board);
        spacer();

    }

}