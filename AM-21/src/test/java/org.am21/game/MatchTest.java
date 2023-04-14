package org.am21.game;

import org.am21.controller.PlayerController;
import org.am21.model.GameManager;
import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.model.enumer.GameState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @version 1.0
 */
class MatchTest {
    Match m;
    @BeforeEach
    void setUp(){
        m=new Match(2);


    }

    @AfterEach
    void tearDown(){
        m=null;

    }

    /**
     * Test if the method works correctly
     * Setup : the match has already reached his player number limit(2)
     * Test case: What happens when this method is called one more time?
     * Result expected: There should be 2 players in the match
     */
    @Test
    void testAddPlayer(){
        PlayerController c1 = new PlayerController("A");
        PlayerController c2 = new PlayerController("B");
        PlayerController c3 = new PlayerController("C");
        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());
        m.addPlayer(c3.getPlayer());

        assertEquals(2,m.playerList.size());
    }

    /**
     *Test if the method works correctly
     * When called verify if all the attributes are correct.
     */
    @Test
    void testInitializeMatch(){
        PlayerController c1 = new PlayerController("A");
        PlayerController c2 = new PlayerController("B");
        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());
        //startMatch() is called automatically
        //startFirstRound() is called after that
        //Check gamestate
        assertTrue(m.gameState.equals(GameState.GameGoing));
        //Check chairman
        assertTrue(m.playerList.contains(m.chairman));
        //Check if all players personal goal are different
        for(Player x: m.playerList){
            //Check also if each player is mapped in playerMatchMap
            assertTrue(GameManager.playerMatchMap.containsKey(x.getNickname()));
            for(Player y:m.playerList){
                if(!y.equals(x))
                    assertTrue(!x.getMyPersonalGoal().equals(y.getMyPersonalGoal()));
            }
        }
        //Check if common goals are different
        assertFalse(m.commonGoals.get(0).equals(m.commonGoals.get(1)));

        //Check if board is completely refilled
        int k=0;
        if(m.maxSeats==2) k=1;
        for(int i=0+k;i<m.board.boundaries.size()-k;i++){
            for(int j=m.board.boundaries.get(i).x;j<m.board.boundaries.get(i).y;j++){
                assertTrue(m.board.isOccupied(i,j));
            }
        }
    }

    /**
     * Setup: MaxSeats: 4
     * We have 3 players waiting to begin. One gets removed.
     * Test if removePlayer() works.
     */
    @Test
    void testRemovePlayer(){
        m=new Match(4);
        PlayerController c1 = new PlayerController("A");
        PlayerController c2 = new PlayerController("B");
        PlayerController c3 = new PlayerController("C");

        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());
        m.addPlayer(c3.getPlayer());
        //Match not started yet

        m.removePlayer(c1.getPlayer());

        assertTrue(m.playerList.get(0).equals(c2.getPlayer()));
        assertEquals(2,m.playerList.size());

    }



    @Test
    void testStartFirstRound(){

    }

    /**
     * What if a player leave the match while the match is starting?
     */
    @Test
    void testCheckRoom(){

    }

    @Test
    void testEndMatch(){

    }

    @Test
    void testNextTurn(){

    }

    @Test
    void testCheckingGoals(){

    }

    @Test
    void testCallEndTurnRoutine(){

    }














}
