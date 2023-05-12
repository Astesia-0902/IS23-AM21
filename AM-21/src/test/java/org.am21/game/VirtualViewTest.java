package org.am21.game;

import org.am21.controller.PlayerController;
import org.am21.model.Match;
import org.am21.networkRMI.ClientCallBack;
import org.am21.networkRMI.ClientInputHandler;
import org.am21.utilities.VirtualViewHelper;
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
            c1.clientInput.callBack= new ClientCallBack();
            c2 = new PlayerController("B",new ClientInputHandler());
            c2.clientInput.callBack = new ClientCallBack();
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
     * - updatePlayersView()
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

        assertNotNull(m.virtualView.endGameToken);

        assertEquals(m.chairman.getNickname(),m.virtualView.currentPlayer);

        VirtualViewHelper.printJSON(m);


    }

    @Test
    void testVirtualViewDuringGameplay(){



    }
}
