package org.am21.game;

import org.am21.controller.PlayerController;
import org.am21.model.Match;
import org.am21.model.enumer.GamePhase;
import org.am21.model.enumer.GameState;
import org.am21.networkRMI.ClientInputHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VirtualViewTest {
    Match m;
    PlayerController c1;
    PlayerController c2;

    /**
     * We create a match and 2 players.
     * Before startMatch, virtualView attributes are null.
     */
    @BeforeEach
    void setUp(){
        m=new Match(2);
        try {
            c1 = new PlayerController("A",new ClientInputHandler());
            c2 = new PlayerController("B",new ClientInputHandler());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }

    @AfterEach
    void tearDown(){
        m=null;
        c1=null;
        c2=null;

    }

    /**
     * Test VirtualView attributes values
     * after the match began (after startFirstRound())
     * - VirtualViewHelper.buildVirtualView(m)
     * - updatePlayersVirtualView()
     * are called
     */
    @Test
    void testAfterStart(){

        m.addPlayer(c1.getPlayer());
        m.addPlayer(c1.getPlayer());
        //Match Started

        assertNotNull(m.virtualView.board);
        assertNotNull(m.virtualView.matchID);
        assertNotNull(m.virtualView.commonGoals);
        assertNotNull(m.virtualView.commonGoalScores);
        assertNotNull(m.virtualView.players);
        assertNotNull(m.virtualView.currentPlayer);
        assertNotNull(m.virtualView.currentPlayerHand);
        assertNotNull(m.virtualView.shelves);
        assertNotNull(m.virtualView.personalGoals);
        assertNotNull(m.virtualView.gamePhase);
        assertNotNull(m.virtualView.gameState);
        assertNotNull(m.virtualView.endGameToken);

        assertEquals(GameState.GameGoing.toString(),m.virtualView.gameState);
        assertEquals(GamePhase.Selection.toString(),m.virtualView.gamePhase);
        assertEquals(m.chairman.getNickname(),m.virtualView.currentPlayer);


    }

    @Test
    void testVirtualViewDuringGameplay(){



    }
}
