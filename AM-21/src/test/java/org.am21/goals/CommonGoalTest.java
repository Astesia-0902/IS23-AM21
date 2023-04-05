package org.am21.goals;

import org.am21.model.Match;
import org.am21.model.items.Shelf;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class CommonGoalTest {
    private Match m;
    private Shelf s;
    private static int seats=2;

    @BeforeEach
    void setUp(){
        m = new Match(2);



    }

    @AfterEach
    void tearDown(){

    }
}
