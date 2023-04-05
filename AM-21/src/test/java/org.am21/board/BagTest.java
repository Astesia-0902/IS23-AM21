package org.am21.board;

import org.am21.model.Match;
import org.am21.model.items.Bag;
import org.am21.model.items.Board;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class BagTest {
    private Match m;
    private Board board;
    private Bag b;
    private static int seats=2;

    @BeforeEach
    void setUp(){
        m = new Match(seats);
        b = new Bag(m);
    }

    @AfterEach
    void tearDown(){
        m=null;
        b=null;

    }

}
