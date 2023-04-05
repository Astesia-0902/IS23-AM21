package org.am21;

import org.am21.controller.PlayerController;
import org.am21.extra.Printer;
import org.am21.model.enumer.GameState;
import org.am21.model.Match;
import org.am21.model.Player;
import org.junit.jupiter.api.Test;

import static org.am21.extra.Gear.*;
import static org.am21.extra.Printer.*;
import static org.am21.extra.Printer.printPersonalGoals;
import static org.junit.jupiter.api.Assertions.*;

public class WrongModelTest {
    static int numMatch=0;
    public static int t=0;
    public static int maxRound=10;


    public static Match buildGame(){
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
        printfThisBag(m.bag);
        printCommGoals(m.commonGoals);
        printPersonalGoals(m.playerList);
        spacer();

        for(t=0; t<maxRound; t++){      //Number of round
            System.out.println("Match > {[ Round number: "+ (t+1)+" ]}");

            for(Player p : m.playerList) {
                if(m.currentPlayer==p){
                    robotMoves(p.controller,p);
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
        Player p1 = pC1.player;
        PlayerController pC2 = createPlayerController("Omar");
        Player p2 = pC2.player;
        PlayerController pC3 = createPlayerController("Silvestro");
        Player p3 = pC3.player;
        PlayerController pC4 = createPlayerController("Jane");
        Player p4 = pC4.player;

        m.addPlayer(p1);
        m.addPlayer(p2);
        //m.addPlayer(p3);
        //m.addPlayer(p4);

        while(m.gamePhase== GameState.WaitingPlayers);

        //Lista player contine effetivamente quel player?
        assertTrue(m.playerList.contains(p3));
        //Il numero di player è entro i limiti?
        assertEquals(2,m.playerList.size());
        //Se non ci sono abbastanza player sono in Waiting Phase?
        assertTrue(m.gamePhase.equals(GameState.WaitingPlayers));

    }

    @Test
    void testPlayerCtrl(){
        Match m = createMatch(2);
        PlayerController c1 = createPlayerController("Uno");
        PlayerController c2 = createPlayerController("Due");
        m.addPlayer(c1.player);
        m.addPlayer(c1.player);



        //quando la partita viene inizializzata,startGame dura solo fino alla fine della chiamata
        assertTrue(m.gamePhase.equals(GameState.GameGoing));

    }

    @Test
    void runner(){
        Match m1 = buildGame();

        assertFalse(m1.playerList.size()==m1.maxSeats);




    }
}
