package org.am21;

import org.am21.controller.PlayerController;
import org.am21.extra.Printer;
import org.am21.model.enumer.GameState;
import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.model.enumer.TurnPhases;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.am21.extra.Gear.*;
import static org.am21.extra.Printer.*;


class PlayerModel {
    static int numMatch=0;
    public static int t=0;
    public static int maxRound=20;

    @DisplayName("Test1")
    @Test
     void selectionSimulation(){

        Match match = createMatch(3);
        PlayerController pCtrl1 = createPlayerController("Ambrogio");
        Player player1 = pCtrl1.player;
        match.addPlayer(player1);
        Printer.printThisShelf(player1.shelf);
        PlayerController pCtrl2 = createPlayerController("Ambra");
        Player player2 = pCtrl2.player;
        match.addPlayer(player2);
        Printer.printThisShelf(player2.shelf);
        PlayerController pCtrl3 = createPlayerController("Zazà");
        Player player3 = pCtrl3.player;
        match.addPlayer(player3);
        Printer.printThisShelf(player3.shelf);

        System.out.println("----------------------");
        System.out.println("Match > PlayerTurn: "+ match.currentPlayer.getName());

        printThisBoard(match.board);

        pCtrl1.selectCell(0,5);
        pCtrl1.selectCell(1,5);
        pCtrl1.selectCell(2,5);

        //pCtrl1.unselectCard();
        pCtrl2.selectCell(7,3);
        pCtrl2.selectCell(8,3);
        pCtrl2.selectCell(2,3);
        pCtrl2.selectCell(5,1);

        pCtrl3.selectCell(3,0);
        pCtrl3.selectCell(4,1);


        Printer.showHand(pCtrl1.hand);
        Printer.showHand(pCtrl2.hand);
        Printer.showHand(pCtrl3.hand);

        pCtrl1.moveAllToHand();
        pCtrl2.moveAllToHand();
        pCtrl3.moveAllToHand();

        printThisBoard(match.board);

    }




    @DisplayName("Test2")
    @Test
    void randomSimulation(){
        Match match = createMatch(4);
        PlayerController pCtrl1 = createPlayerController("Ambrogio");
        Player player1 = pCtrl1.player;
        match.addPlayer(player1);
        PlayerController pCtrl2 = createPlayerController("Ambra");
        Player player2 = pCtrl2.player;
        match.addPlayer(player2);
        PlayerController pCtrl3 = createPlayerController("Zazà");
        Player player3 = pCtrl3.player;
        match.addPlayer(player3);
        PlayerController pCtrl4 = createPlayerController("Lupin");
        Player player4 = pCtrl4.player;
        match.addPlayer(player4);

        System.out.println("----------------------");
        Printer.printThisShelf(player1.shelf);
        Printer.printThisShelf(player2.shelf);
        Printer.printThisShelf(player3.shelf);
        Printer.printThisShelf(player4.shelf);
        System.out.println("Match > PlayerTurn: "+ match.currentPlayer.getName());

        printThisBoard(match.board);
        randomChoice(pCtrl1);
        randomChoice(pCtrl2);
        randomChoice(pCtrl3);
        randomChoice(pCtrl4);

        Printer.showHand(pCtrl1.hand);
        pCtrl1.changeHandOrder(0,1);
        Printer.showHand(pCtrl1.hand);
        Printer.showHand(pCtrl2.hand);
        Printer.showHand(pCtrl3.hand);
        Printer.showHand(pCtrl4.hand);

        pCtrl1.moveAllToHand();
        pCtrl2.moveAllToHand();
        pCtrl3.moveAllToHand();

        printThisBoard(match.board);

        match.turnPhase = TurnPhases.Insertion;
        pCtrl1.tryToInsert(1);
        pCtrl2.tryToInsert(2);
        pCtrl3.tryToInsert(3);
        pCtrl4.tryToInsert(4);
        Printer.printThisShelf(player1.shelf);
        Printer.printThisShelf(player2.shelf);
        Printer.printThisShelf(player3.shelf);
        Printer.printThisShelf(player4.shelf);
        Printer.showHand(pCtrl1.hand);
        Printer.showHand(pCtrl2.hand);
        Printer.showHand(pCtrl3.hand);
        Printer.showHand(pCtrl4.hand);
    }

    /**
     * Test failed during 4th test: do{}while(); loop in PersonalGoalBuild(CarUtil line 63)
     */
    @DisplayName("Test3")
   // @RepeatedTest(5)
    @Test
    void randomRobotMovesSimulation(){
        Match match = createMatch(4);
        PlayerController pCtrl1 = createPlayerController("Ambrogio");
        Player player1 = pCtrl1.player;
        match.addPlayer(player1);
        PlayerController pCtrl2 = createPlayerController("Ambra");
        Player player2 = pCtrl2.player;
        match.addPlayer(player2);
        PlayerController pCtrl3 = createPlayerController("Zazà");
        Player player3 = pCtrl3.player;
        match.addPlayer(player3);
        PlayerController pCtrl4 = createPlayerController("Lupin");
        Player player4 = pCtrl4.player;
        match.addPlayer(player4);


        System.out.println("----------------------");
        Printer.printThisShelf(player1.shelf);
        Printer.printThisShelf(player2.shelf);
        Printer.printThisShelf(player3.shelf);
        Printer.printThisShelf(player4.shelf);
        System.out.println("Match > PlayerTurn: "+ match.currentPlayer.getName());

        printThisBoard(match.board);

        randomChoice(pCtrl1);

        randomChoice(pCtrl2);

        randomChoice(pCtrl3);

        randomChoice(pCtrl4);

        Printer.showHand(pCtrl1.hand);
        pCtrl1.changeHandOrder(0,1);
        Printer.showHand(pCtrl1.hand);
        Printer.showHand(pCtrl2.hand);
        Printer.showHand(pCtrl3.hand);
        Printer.showHand(pCtrl4.hand);
        pCtrl1.moveAllToHand();
        pCtrl2.moveAllToHand();
        pCtrl3.moveAllToHand();

        printThisBoard(match.board);

        match.turnPhase = TurnPhases.Insertion;
        pCtrl1.tryToInsert(1);
        pCtrl2.tryToInsert(2);
        pCtrl3.tryToInsert(3);
        pCtrl4.tryToInsert(4);

        Printer.printThisShelf(player1.shelf);
        Printer.printThisShelf(player2.shelf);
        Printer.printThisShelf(player3.shelf);
        Printer.printThisShelf(player4.shelf);

        Printer.showHand(pCtrl1.hand);
        Printer.showHand(pCtrl2.hand);
        Printer.showHand(pCtrl3.hand);
        Printer.showHand(pCtrl4.hand);
    }


    @DisplayName("Test4")
    //@Test
    @RepeatedTest(5)
    void unfinishedGameSimulation(){
        Match m = createMatch(4);
        numMatch++;
        System.out.println("Game > [[Match n. "+ numMatch+"]]");
        PlayerController pC1 = createPlayerController("Kratos");
        Player p1 = pC1.player;
        PlayerController pC2 = createPlayerController("Omar");
        Player p2 = pC2.player;
        PlayerController pC3 = createPlayerController("Silvestro");
        Player p3 = pC3.player;
        PlayerController pC4 = createPlayerController("Jane");
        Player p4 = pC4.player;
        spacer();
        while(m.gamePhase== GameState.WaitingPlayers) {
            m.addPlayer(p1);
            m.addPlayer(p2);
            m.addPlayer(p3);
            m.addPlayer(p4);
        }


        spacer();
        printThisBoard(m.board);
        printfThisBag(m.board.bag);
        printCommGoals(m.commonGoals);
        printPersonalGoals(m.playerList);
        spacer();

        for(t=0; t<maxRound; t++){      //Number of round
            System.out.println("Match > {[ Round number: "+ (t+1)+" ]}");

            for(Player p : m.playerList) {
                if(m.currentPlayer==p){
                    robotMoves(p.getController(),p);
                }
            }

            Printer.viewStats(m,t);

        }

        if(t==maxRound){
            System.out.println("Match > Game ended without a winner");
        }

    }






}
