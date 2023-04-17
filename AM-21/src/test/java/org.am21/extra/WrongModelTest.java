package org.am21.extra;

import org.am21.model.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.am21.extra.Gear.buildGame;

public class WrongModelTest {
    @BeforeEach
    void reset(){
        //GameGear.numberOfRefill=0;
    }
    @Test
    @DisplayName("Game 2 players")
    void runner2(){
        Match m1 = buildGame(2,20);

    }
    @Test
    @DisplayName("Game 3 players")

    void runner3(){
        Match m1 = buildGame(3,20);

    }
    @RepeatedTest(5)
    @DisplayName("Game 4 players")
    void runner4(){
        Match m1 = buildGame(4,20);

    }
}
