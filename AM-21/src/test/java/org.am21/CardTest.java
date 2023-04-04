package org.am21;

import org.am21.controller.PlayerController;
import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.model.TurnPhases;
import org.am21.model.items.Shelf;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CardTest {
    @Test
    void testCard(){
        Match match = MatchTest.createMatch(2);

        PlayerController pCtrl1 = PlayerControllerTest.createPlayerController("Ambrogio");
        Player player1 = pCtrl1.player;
        match.addPlayer(player1);
        PlayerControllerTest.printThisShelf(player1.myShelf);

        PlayerController pCtrl2 = PlayerControllerTest.createPlayerController("Ambra");
        Player player2 = pCtrl2.player;
        match.addPlayer(player2);
        PlayerControllerTest.printThisShelf(player2.myShelf);
        System.out.println("\n----------------------\n");
        MatchTest.printPersonalGoals(match.playerList);
        System.out.println("\n----------------------\n");
        printPlayerPersonalGoal(player1.getMyPersonalGoal().getMyPersonalGoalShelf());
        printPlayerPersonalGoal(player2.getMyPersonalGoal().getMyPersonalGoalShelf());
        System.out.println("\n----------------------\n");
        MatchTest.printThisBoard(match.livingRoomBoard);
        System.out.println("\n----------------------\n");
        System.out.println("Match > PlayerTurn: "+ match.currentPlayer.getName());
        System.out.println("\n----------------------\n");
        PlayerControllerTest.showHand(pCtrl1.hand);
        pCtrl1.selectCell(1,4);
        pCtrl1.selectCell(1,5);
        PlayerControllerTest.showHand(pCtrl1.hand);
        pCtrl1.moveAllToHand();

        match.turnPhase = TurnPhases.Insertion;
        pCtrl1.tryToInsert(1);
        PlayerControllerTest.printThisShelf(player1.myShelf);
        System.out.println("\n----------------------\n");
        MatchTest.printThisBoard(match.livingRoomBoard);
        System.out.println("\n----------------------\n");
        System.out.println("Personal goals completed: " + player1.getMyPersonalGoal().checkGoal());
        System.out.println("Score obtained: " + player1.getMyPersonalGoal().calculatePoints());
    }

    @DisplayName("Printf Shelf")
    static void printPlayerPersonalGoal(Shelf shelf){
        System.out.println("Match > "+shelf.player.getName()+"'s PersonalGoal:");
        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                if(shelf.getCellGrid()[i][j].getItem()==null){
                    System.out.print("[______._]");
                }else if(shelf.getCellGrid()[i][j].getItem()!=null){
                    System.out.print("["+ shelf.getCellGrid()[i][j].getItem().getNameCard() +"]");
                }

            }
            System.out.println("");
        }
    }
}
