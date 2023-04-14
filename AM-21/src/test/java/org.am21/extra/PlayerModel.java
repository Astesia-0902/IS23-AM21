package org.am21.extra;

import org.am21.controller.PlayerController;
import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.model.enumer.GamePhase;
import org.am21.model.enumer.GameState;
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
        Player player1 = pCtrl1.getPlayer();
        match.addPlayer(player1);
        printThisShelf(player1.getShelf());
        PlayerController pCtrl2 = createPlayerController("Ambra");
        Player player2 = pCtrl2.getPlayer();
        match.addPlayer(player2);
        printThisShelf(player2.getShelf());
        PlayerController pCtrl3 = createPlayerController("Zazà");
        Player player3 = pCtrl3.getPlayer();
        match.addPlayer(player3);
        printThisShelf(player3.getShelf());

        System.out.println("----------------------");
        System.out.println("Match > PlayerTurn: "+ match.currentPlayer.getNickname());

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


        showHand(pCtrl1.getHand());
        showHand(pCtrl2.getHand());
        showHand(pCtrl3.getHand());

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
        Player player1 = pCtrl1.getPlayer();
        match.addPlayer(player1);
        PlayerController pCtrl2 = createPlayerController("Ambra");
        Player player2 = pCtrl2.getPlayer();
        match.addPlayer(player2);
        PlayerController pCtrl3 = createPlayerController("Zazà");
        Player player3 = pCtrl3.getPlayer();
        match.addPlayer(player3);
        PlayerController pCtrl4 = createPlayerController("Lupin");
        Player player4 = pCtrl4.getPlayer();
        match.addPlayer(player4);

        System.out.println("----------------------");
        printThisShelf(player1.getShelf());
        printThisShelf(player2.getShelf());
        printThisShelf(player3.getShelf());
        printThisShelf(player4.getShelf());
        System.out.println("Match > PlayerTurn: "+ match.currentPlayer.getNickname());

        printThisBoard(match.board);
        randomChoice(pCtrl1);
        randomChoice(pCtrl2);
        randomChoice(pCtrl3);
        randomChoice(pCtrl4);

        showHand(pCtrl1.getHand());
        pCtrl1.changeHandOrder(0,1);
        showHand(pCtrl1.getHand());
        showHand(pCtrl2.getHand());
        showHand(pCtrl3.getHand());
        showHand(pCtrl4.getHand());

        pCtrl1.moveAllToHand();
        pCtrl2.moveAllToHand();
        pCtrl3.moveAllToHand();

        printThisBoard(match.board);

        match.gamePhase = GamePhase.Insertion;
        pCtrl1.tryToInsert(1);
        pCtrl2.tryToInsert(2);
        pCtrl3.tryToInsert(3);
        pCtrl4.tryToInsert(4);
        printThisShelf(player1.getShelf());
        printThisShelf(player2.getShelf());
        printThisShelf(player3.getShelf());
        printThisShelf(player4.getShelf());
        showHand(pCtrl1.getHand());
        showHand(pCtrl2.getHand());
        showHand(pCtrl3.getHand());
        showHand(pCtrl4.getHand());
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
        Player player1 = pCtrl1.getPlayer();
        match.addPlayer(player1);
        PlayerController pCtrl2 = createPlayerController("Ambra");
        Player player2 = pCtrl2.getPlayer();
        match.addPlayer(player2);
        PlayerController pCtrl3 = createPlayerController("Zazà");
        Player player3 = pCtrl3.getPlayer();
        match.addPlayer(player3);
        PlayerController pCtrl4 = createPlayerController("Lupin");
        Player player4 = pCtrl4.getPlayer();
        match.addPlayer(player4);


        System.out.println("----------------------");
        printThisShelf(player1.getShelf());
        printThisShelf(player2.getShelf());
        printThisShelf(player3.getShelf());
        printThisShelf(player4.getShelf());
        System.out.println("Match > PlayerTurn: "+ match.currentPlayer.getNickname());

        printThisBoard(match.board);

        randomChoice(pCtrl1);

        randomChoice(pCtrl2);

        randomChoice(pCtrl3);

        randomChoice(pCtrl4);

        showHand(pCtrl1.getHand());
        pCtrl1.changeHandOrder(0,1);
        showHand(pCtrl1.getHand());
        showHand(pCtrl2.getHand());
        showHand(pCtrl3.getHand());
        showHand(pCtrl4.getHand());
        pCtrl1.moveAllToHand();
        pCtrl2.moveAllToHand();
        pCtrl3.moveAllToHand();

        printThisBoard(match.board);

        match.gamePhase = GamePhase.Insertion;
        pCtrl1.tryToInsert(1);
        pCtrl2.tryToInsert(2);
        pCtrl3.tryToInsert(3);
        pCtrl4.tryToInsert(4);

        printThisShelf(player1.getShelf());
        printThisShelf(player2.getShelf());
        printThisShelf(player3.getShelf());
        printThisShelf(player4.getShelf());

        showHand(pCtrl1.getHand());
        showHand(pCtrl2.getHand());
        showHand(pCtrl3.getHand());
        showHand(pCtrl4.getHand());
    }


    @DisplayName("Test4")
    //@Test
    @RepeatedTest(5)
    void unfinishedGameSimulation(){
        Match m = createMatch(4);
        numMatch++;
        System.out.println("Game > [[Match n. "+ numMatch+"]]");
        PlayerController pC1 = createPlayerController("Kratos");
        Player p1 = pC1.getPlayer();
        PlayerController pC2 = createPlayerController("Omar");
        Player p2 = pC2.getPlayer();
        PlayerController pC3 = createPlayerController("Silvestro");
        Player p3 = pC3.getPlayer();
        PlayerController pC4 = createPlayerController("Jane");
        Player p4 = pC4.getPlayer();
        spacer();
        while(m.gameState == GameState.WaitingPlayers) {
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
