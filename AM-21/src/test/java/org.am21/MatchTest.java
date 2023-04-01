package org.am21;

import org.am21.controller.PlayerController;
import org.am21.model.Match;
import org.junit.jupiter.api.Test;

class MatchTest {

    @Test
    void run(){
        Match match = new Match(4);
        System.out.println("Number of players:" + match.maxSeats);
        System.out.println("Board's size:" + match.livingRoomBoard.getSize());
    }

}
