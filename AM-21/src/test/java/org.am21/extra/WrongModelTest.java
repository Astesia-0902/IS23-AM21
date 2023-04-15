package org.am21.extra;

import org.am21.model.Match;
import org.junit.jupiter.api.RepeatedTest;

import static org.am21.extra.Gear.buildGame;

public class WrongModelTest {

    @RepeatedTest(5)
    void runner(){
        Match m1 = buildGame(4,20);

    }
}
