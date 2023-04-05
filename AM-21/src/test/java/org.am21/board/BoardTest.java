package org.am21.board;

import org.am21.model.Match;
import org.am21.model.items.Board;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTest {
    @BeforeEach
    void setUp() {
        int seats=2;
        Board b = new Board(new Match(seats));


    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void testSize(){

        //assertTrue()

    }


}
