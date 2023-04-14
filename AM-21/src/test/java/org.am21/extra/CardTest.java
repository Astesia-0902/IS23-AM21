package org.am21.extra;

import org.am21.controller.PlayerController;
import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.model.enumer.GamePhase;
import org.am21.model.items.Shelf;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.am21.extra.Gear.createMatch;
import static org.am21.extra.Gear.createPlayerController;
import static org.am21.extra.Printer.printPersonalGoals;
import static org.am21.extra.Printer.printThisBoard;

class CardTest {
    @Test
    void testCard(){
        Match match = createMatch(2);

        PlayerController pCtrl1 = createPlayerController("Ambrogio");
        Player player1 = pCtrl1.getPlayer();
        match.addPlayer(player1);
        Printer.printThisShelf(player1.getShelf());

        PlayerController pCtrl2 = createPlayerController("Ambra");
        Player player2 = pCtrl2.getPlayer();
        match.addPlayer(player2);
        Printer.printThisShelf(player2.getShelf());
        System.out.println("\n----------------------\n");
        printPersonalGoals(match.playerList);
        System.out.println("\n----------------------\n");
        printPlayerPersonalGoal(player1.getMyPersonalGoal().setupGoalShelf(player1));
        printPlayerPersonalGoal(player2.getMyPersonalGoal().setupGoalShelf(player2));
        System.out.println("\n----------------------\n");
        printThisBoard(match.board);
        System.out.println("\n----------------------\n");
        System.out.println("Match > PlayerTurn: "+ match.currentPlayer.getNickname());
        System.out.println("\n----------------------\n");
        Printer.showHand(pCtrl1.getHand());
        pCtrl1.selectCell(1,4);
        pCtrl1.selectCell(1,5);
        Printer.showHand(pCtrl1.getHand());
        pCtrl1.moveAllToHand();

        match.gamePhase = GamePhase.Insertion;
        pCtrl1.tryToInsert(1);
        Printer.printThisShelf(player1.getShelf());
        System.out.println("\n----------------------\n");
        printThisBoard(match.board);
        System.out.println("\n----------------------\n");
        System.out.println("Personal goals completed: " + player1.getMyPersonalGoal().checkGoal());
        System.out.println("Score obtained: " + player1.getMyPersonalGoal().calculatePoints());
    }

    @DisplayName("Printf Shelf")
    static void printPlayerPersonalGoal(Shelf shelf){
        System.out.println("Match > "+shelf.player.getNickname()+"'s PersonalGoal:");
        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                if(shelf.getMatrix()[i][j]==null){
                    System.out.print("[______._]");
                }else if(shelf.getMatrix()[i][j]!=null){
                    System.out.print("["+ shelf.getMatrix()[i][j].getNameCard() +"]");
                }

            }
            System.out.println();
        }
    }
}
