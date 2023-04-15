package org.am21.player;

import org.am21.controller.PlayerController;
import org.am21.model.Cards.ItemCard;
import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.model.enumer.GamePhase;
import org.am21.model.items.Board;
import org.am21.model.items.Shelf;
import org.am21.utilities.CardPointer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @version 1.0
 */
public class PlayerTest {
    private PlayerController c1,c2;
    private Player p1,p2;
    private Match m;
    private Board b;
    private Shelf s;

    @BeforeEach
    void setUp(){
        m=new Match(2);
        c1 = new PlayerController("Rorschach");
        p1 = c1.getPlayer();
        c2 = new PlayerController("Rorschach");
        p2 = c2.getPlayer();
        m.addPlayer(p1);
        m.addPlayer(p2);

        b=m.board;
        p1.setShelf(new Shelf(p1));
        s= p1.getShelf();

        m.currentPlayer = p1;
    }

    @AfterEach
    void tearDown(){
        c1=null;
        p1=null;
        c2=null;
        p2=null;
        b=null;
        m=null;
        s=null;

    }

    /**
     * Setup: Board completely filled
     * 1: Select (5,5) -> Not selectable
     * 2: Select (1,4) -> Selectable
     * 3: TurnPhase wrong
     * 4: Not CurrentPlayer
     * 5: InsertionLimit
     *
     */
    @Test
    void testSelectCell(){


        assertFalse(c1.selectCell(5,5));
        assertTrue(c1.selectCell(1,4));

        m.setGamePhase(GamePhase.GoalChecking);
        assertFalse(c1.selectCell(1,4));

        assertFalse(c2.selectCell(1,4));

        s.insertLimit=0;
        assertFalse(c1.selectCell(1,4));

    }

    /**
     * Selecting 2 adjacent cards
     * And another one, which will not pass the orthogonality check
     */
    @Test
    void testSelectCellWithHand(){


        c1.selectCell(1,4);
        assertTrue(c1.selectCell(1,3));
        assertFalse(c1.selectCell(2,3));

    }

    /**
     * Hand is cleared
     */
    @Test
    void testUnselectCards(){
        c1.selectCell(1,4);
        c1.selectCell(1,3);
        assertTrue(c1.unselectCards());

        assertEquals(0, c1.getHand().getSlot().size());

    }

    /**
     * Moving on to the Insertion Phase
     * Removing definitely the cards from the board
     * 1: Wrong Turn phase
     */
    @Test
    void testMoveAllToHand(){
        c1.selectCell(1,4);
        c1.selectCell(1,3);

        assertFalse(c1.moveAllToHand());

        m.setGamePhase(GamePhase.Insertion);
        assertTrue(c1.moveAllToHand());
        assertFalse(b.isOccupied(1,4));
        assertFalse(b.isOccupied(1,3));

    }

    /**
     * CallEndInsertion should call a sequence of method.
     * Where at the end,it is going to be the next player turn and Selection phase
     */
    @Test
    void testCallingPhases(){
        c1.callEndSelection();
        assertTrue(m.gamePhase.equals(GamePhase.Insertion));
        c1.callEndInsertion();
        assertTrue(m.gamePhase.equals(GamePhase.Selection));
        assertTrue(m.currentPlayer.equals(p2));

    }

    @Test
    void testChangeHandOrder(){
        c1.selectCell(1,4);
        c1.selectCell(1,3);
        CardPointer t= c1.getHand().getSlot().get(0);
        CardPointer f= c1.getHand().getSlot().get(1);
        assertFalse(c1.changeHandOrder(1,2));
        c1.changeHandOrder(0,1);

        assertTrue(c1.getHand().getSlot().get(1).equals(t));
        assertTrue(c1.getHand().getSlot().get(0).equals(f));
    }

    /**
     * Setup:
     * 1: Wrong phase
     * 2: Trying to insert in a column where there is not slot available
     */
    @Test
    void testTryToInsert(){
        c1.selectCell(1,4);
        c1.selectCell(1,3);

        assertFalse(c1.tryToInsert(0));
        c1.callEndSelection();
        assertTrue(c1.tryToInsert(0));

        assertTrue(s.isOccupied(5,0));

        c1.getPlayer().getShelf().insertInColumn(new ItemCard("Generic"),0);
        c1.getPlayer().getShelf().insertInColumn(new ItemCard("Generic"),0);
        c1.getPlayer().getShelf().insertInColumn(new ItemCard("Generic"),0);
        c1.getPlayer().getShelf().insertInColumn(new ItemCard("Generic"),0);
        assertFalse(c1.tryToInsert(0));

    }




}
