package org.am21.utilities;

import org.am21.model.Cards.CommonGoal;
import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.model.items.Bag;
import org.am21.model.items.Board;
import org.am21.model.items.Hand;
import org.am21.model.items.Shelf;

import java.util.List;

/**
 * Temporary Class. Needed for Testing
 */
public class GameGear {
    public static void printThisBoard(Board board){
        System.out.println("Match > Board:");
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(!board.isPlayable(i,j)){
                    System.out.print("[XXXXXXXXXX.]");

                }else {
                    if (board.getMatrix()[i][j] == null) {
                        System.out.print("[__________.]");
                    } else if (board.getMatrix()[i][j] != null) {
                        System.out.print("[" + board.getMatrix()[i][j].getNameCard() + "]");
                    }
                }

            }
            System.out.println("");
        }
    }

    public static void printThisShelf(Shelf shelf){
        System.out.println("Match > "+shelf.player.getNickname()+"'s Shelf:");
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
        System.out.println("Match  > "+hand.player.getNickname() + "'s HandLimit ["+ hand.getSlot().size()+"]:");
        for(CardPointer x : hand.getSlot()) {
            if(x.item.getNameCard()!=null){
                System.out.print("["+x.item.getNameCard()+"]");
            }
        }
        System.out.println("");
    }

    public static void printGoalShelf(Shelf shelf){
        System.out.println("Match[Hide] > "+shelf.player.getNickname()+"'s PersonalGoal:");
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

    public static void printPersonalGoals(List<Player> pList){
        System.out.println("Match[Hide] > Personal Goals:");
        for(Player x: pList){
            System.out.println("Match[Hide] > "+x.getNickname()+"'s goal is:"+x.getMyPersonalGoal().getNameCard());
            GameGear.printGoalShelf(x.getMyPersonalGoal().setupGoalShelf(x));
        }
    }
    public static void printCommGoals(List<CommonGoal> commonGoalList){
        System.out.println("Match > Common Goals:");
        for(CommonGoal x: commonGoalList){
            System.out.println(x.getNameCard());
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



    public static void spacer(){
        System.out.println("-----------------");
    }

    public static void viewStats(Match m,int nRound){
        spacer();
        System.out.println("Match[!][!] > Game Stats");
        System.out.println("Num round: ["+(nRound+1)+"]");
        System.out.println("Bag items: ["+(m.board.bag.getDeck().size()-m.board.bag.bagIndex)+"]");
        System.out.println("Bag index: ["+m.board.bag.bagIndex+"]");
        System.out.println("Player/Score/Shelf/Hand situation:" );
        for(Player x: m.playerList){
            spacer();
            System.out.print(x.getNickname()+"["+ x.getShelf().getTotSlotAvail() +"]--");
            System.out.print("Limit:["+ x.getShelf().insertLimit +"]\n");
            GameGear.printGoalShelf(x.getMyPersonalGoal().getPersonalGoalShelf());
            System.out.println("PersonalGoal Points: " + x.getMyPersonalGoal().calculatePoints());
            System.out.println("Score: "+x.getPlayerScore());
            GameGear.printThisShelf(x.getShelf());
        }
        System.out.println();
        GameGear.printThisBoard(m.board);
        spacer();

    }
    public static void viewFinalStats(Match m){
        spacer();
        System.out.println("Match[!][!] > Final Game Stats");
        System.out.println("Winner is: "+m.getFirstToComplete().getNickname());
        System.out.println("Bag items: ["+(m.board.bag.getDeck().size()-m.board.bag.bagIndex)+"]");
        System.out.println("Bag index: ["+m.board.bag.bagIndex+"]");
        m.commonGoals.stream().forEach(x->{
            System.out.print(x.getNameCard()+": ");
            x.achievedPlayers.stream().forEach(p->System.out.print(p.getNickname()+" - "));
            System.out.println();
        });


        System.out.println("Player/Shelf/Hand situation:" );
        for(Player x: m.playerList){
            spacer();
            System.out.print(x.getNickname()+"["+ x.getShelf().getTotSlotAvail() +"]--");
            System.out.print("Limit:["+ x.getShelf().insertLimit +"]\n");
            GameGear.printGoalShelf(x.getMyPersonalGoal().getPersonalGoalShelf());
            System.out.println("Shelf Points: "+x.getShelf().getGroupPoints());
            System.out.println("PersonalGoal Points: " + x.getMyPersonalGoal().calculatePoints());
            System.out.println("Score: "+x.getPlayerScore());
            printThisShelf(x.getShelf());
        }
        System.out.println();
        printThisBoard(m.board);
        spacer();






    }
}
