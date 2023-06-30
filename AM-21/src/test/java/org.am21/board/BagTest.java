package org.am21.board;

import org.am21.model.Match;
import org.am21.model.items.Bag;
import org.am21.model.items.Board;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class BagTest {
    private Match m;
    private Board board;
    private Bag b;
    private static int seats=2;

    /**
     * Create a match with an empty board
     */
    @BeforeEach
    void setUp(){
        m = new Match(seats);
        board = new Board(m);
        b=board.bag;
    }

    @AfterEach
    void tearDown(){
        m=null;
        board=null;
        b=null;

    }

    /**
     * Test is refillBoard is successful.
     *
     */
    @Test
    void bagRefillTest(){
        assertEquals(0,b.bagIndex);
        assertEquals(132,b.getDeck().size());
        assertTrue(b.refillBoard());
    }

}
