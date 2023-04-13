package org.am21.game;

import org.am21.controller.PlayerController;
import org.am21.model.Match;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Ken Chen
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
     * What if a player leave the match while the match is starting?
     */
    @Test
    void testInitializeMatch(){

    }















}
