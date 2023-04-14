package org.am21.extra;

import org.am21.controller.PlayerController;
import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.model.enumer.GameState;
import org.junit.jupiter.api.Test;

import static org.am21.extra.Gear.*;
import static org.am21.extra.Printer.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WrongModelTest {
    static int numMatch=0;
    public static int t=0;
    public static int maxRound=10;


    public static Match buildGame(int seats){
        Match m = createMatch(seats);
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

        return m;
    }

    /**
     * Test playerList number correct
     * Test player added to the is within match Seat limit.
     */
    @Test
    void testPlayerList(){

        Match m = createMatch(2);
        System.out.println("Game > [[Match n. "+ numMatch+"]]");
        PlayerController pC1 = createPlayerController("Kratos");
        Player p1 = pC1.getPlayer();
        PlayerController pC2 = createPlayerController("Omar");
        Player p2 = pC2.getPlayer();
        PlayerController pC3 = createPlayerController("Silvestro");
        Player p3 = pC3.getPlayer();
        PlayerController pC4 = createPlayerController("Jane");
        Player p4 = pC4.getPlayer();

        m.addPlayer(p1);
        m.addPlayer(p2);
        //m.addPlayer(p3);
        //m.addPlayer(p4);

        while(m.gameState == GameState.WaitingPlayers);

        //Lista player contine effetivamente quel player?
        assertTrue(m.playerList.contains(p3));
        //Il numero di player è entro i limiti?
        assertEquals(2,m.playerList.size());
        //Se non ci sono abbastanza player sono in Waiting Phase?
        assertTrue(m.gameState.equals(GameState.WaitingPlayers));

    }

    @Test
    void testPlayerCtrl(){
        Match m = createMatch(2);
        PlayerController c1 = createPlayerController("Uno");
        PlayerController c2 = createPlayerController("Due");
        m.addPlayer(c1.getPlayer());
        m.addPlayer(c1.getPlayer());



        //quando la partita viene inizializzata,startGame dura solo fino alla fine della chiamata
        assertTrue(m.gameState.equals(GameState.GameGoing));

    }

    @Test
    void runner(){
        Match m1 = buildGame(2);

    }
}
