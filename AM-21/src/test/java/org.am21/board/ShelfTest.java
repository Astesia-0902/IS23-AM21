package org.am21.board;

import org.am21.controller.PlayerController;
import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.model.items.Bag;
import org.am21.model.items.Board;
import org.am21.model.items.Shelf;
import org.junit.jupiter.api.BeforeEach;

public class ShelfTest {
    private Match m;
    private Board board;
    private Bag bag;
    private static int seats=2;
    private Shelf s;
    private Player p;
    private PlayerController c;

    @BeforeEach
    void setUp(){
        m=new Match(seats);
        bag = new Bag(m);
        c=new PlayerController("Ub");

        s=new Shelf(p);

    }


}
