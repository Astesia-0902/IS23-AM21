package org.am21.game;

import org.am21.controller.PlayerController;
import org.am21.model.GameManager;
import org.am21.model.Match;
import org.am21.utilities.VirtualViewHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        c1 = new PlayerController("A");
        c2 = new PlayerController("B");
        GameManager.createMatch(2,c1);
        m= c1.getPlayer().getMatch();
        m.addPlayer(c2.getPlayer());
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

        //Match Started
        assertEquals(c1.getPlayer().getNickname(),m.virtualView.admin);
        assertEquals(2,m.virtualView.maxSeats);
        assertNotNull(m.virtualView.board);
        String[][] wish = {
                {"o","o","o","o","o","o","o","o","o"},
                {"o","o","o","x","x","o","o","o","o"},
                {"o","o","o","x","x","x","o","o","o"},
                {"o","o","x","x","x","x","x","x","o"},
                {"o","x","x","x","x","x","x","x","o"},
                {"o","x","x","x","x","x","x","o","o"},
                {"o","o","o","x","x","x","o","o","o"},
                {"o","o","o","o","x","x","o","o","o"},
                {"o","o","o","o","o","o","o","o","o"}
        };
        checkTheBoardAsIWish(wish,m.virtualView.board);
        assertNotNull(m.virtualView.commonGoals);
        assertNotNull(m.virtualView.commonGoals.get(0));
        assertNotNull(m.virtualView.commonGoals.get(1));
        assertNotNull(m.virtualView.commonGoalScores);
        assertEquals(8,m.virtualView.commonGoalScores.get(0));
        assertEquals(8,m.virtualView.commonGoalScores.get(1));
        assertNotNull(m.virtualView.players);
        assertEquals(c1.getPlayer().getNickname(),m.virtualView.players.get(0));
        assertEquals(c2.getPlayer().getNickname(),m.virtualView.players.get(1));
        assertNotNull(m.virtualView.currentPlayer);
        assertNotNull(m.virtualView.currentPlayerHand);
        assertEquals(0,m.virtualView.currentPlayerHand.size());
        assertNotNull(m.virtualView.shelves);
        assertNotNull(m.virtualView.shelves.get(0));
        assertNotNull(m.virtualView.shelves.get(1));
        assertNotNull(m.virtualView.personalGoals);
        assertNotNull(m.virtualView.personalGoals.get(0));
        assertNotNull(m.virtualView.personalGoals.get(1));
        assertEquals(m.chairman.getNickname(),m.virtualView.currentPlayer);

        VirtualViewHelper.printJSON(m);


    }

    private void checkTheBoardAsIWish(String[][] wish, String[][] board) {
        if (wish != null && wish.length > 0 && wish.length == board.length && wish[0].length == board[0].length) {

            for (int i = 0; i < wish.length; i++) {
                for (int j = 0; j < wish[i].length; j++) {
                    if(wish[i][j]!=null && wish[i][j].equals("x")) {
                        assertNotNull(m.virtualView.board[i][j]);
                    }
                }
            }
        }
    }

    @Test
    void testVirtualViewDuringGameplay(){



    }
}
