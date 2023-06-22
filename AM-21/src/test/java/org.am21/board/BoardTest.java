package org.am21.board;

import org.am21.controller.PlayerController;
import org.am21.model.Cards.ItemCard;
import org.am21.model.Cards.ItemType;
import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.model.items.Bag;
import org.am21.model.items.Board;
import org.am21.model.items.Hand;
import org.am21.utilities.CardPointer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @version 1.0
 */
public class BoardTest {
    private Match m;
    private Board board;
    private Bag bag;
    private final static int seats = 2;


    @BeforeEach
    void setUp() {

        m = new Match(seats);
        m.currentPlayer=new Player("A",new PlayerController("A"));
        board = new Board(m);
        bag = board.bag;

    }


    @AfterEach
    void tearDown() {
        m = null;
        board = null;
    }

    /**
     * Auxiliary Method for board reset
     *
     * @param b is board
     */
    public void clearBoard(Board b) {
        for (int i = 0; i < Board.BOARD_ROW; i++) {
            for (int j = 0; j < Board.BOARD_COLUMN; j++) {
                b.setCell(i, j, null);

            }
        }

    }


    /**
     * Test if the board size is 45.
     * Expect NotEquals, in fact the maxSeats is 2 ,
     * so it should be 29.
     */
    @Test
    void testSize() {

        assertNotEquals(45, board.getSize());

    }

    /**
     * Test if the new generated board is empty in a generic cell
     * And then test it after firstSetup() is called.
     */
    @Test
    void testCell() {

        assertNull(board.getCell(5, 5));
        board.firstSetup();
        assertNotNull(board.getCell(5, 5));

    }


    /**
     * Test if, after an insertion, the cell is occupied
     */
    @Test
    void testOccupation() {
        board.setCell(5, 5, new ItemCard("L"));
        assertTrue(board.isOccupied(5, 5));

        assertFalse(board.isOccupied(0, 0));
    }

    /**
     * Setup: Board is empty
     * After inserting 2 adjacent item,
     * it tests if the board need refill
     * Expected : False
     * Game Rule: Board need refill when every item has no adjacent
     */
    @Test
    void testCheckBoardFalse() {

        board.setCell(5, 5, new ItemCard("L"));
        board.setCell(5, 4, new ItemCard("M"));
        assertFalse(board.checkBoard());

    }

    /**
     * Setup: Board is empty
     * After inserting 2 adjacent item,
     * it tests if the board need refill
     * Expected : True
     * Game Rule: Board need refill when every item has no adjacent
     */
    @Test
    void testCheckBoardTrue() {

        board.setCell(5, 5, new ItemCard("L"));
        board.setCell(4, 4, new ItemCard("M"));
        assertTrue(board.checkBoard());

    }

    /**
     * Test if an item in a cell is isolated
     */
    @Test
    void testIsAlone() {

        board.setCell(5, 5, new ItemCard("K"));
        assertTrue(board.isAlone(5, 5));

        board.setCell(4, 5, new ItemCard("B"));
        assertFalse(board.isAlone(5, 5));

    }

    /**
     * Setup: Hand with some cards
     * Test if the card respect the selection condition when other cards have been already selected
     */
    @Test
    void testIsOrthogonal() {

        Hand h = new Hand(new PlayerController("").getPlayer());

        h.getSelectedItems().add(new CardPointer(3, 3));
        h.getSelectedItems().get(0).item = new ItemCard("none");

        assertTrue(board.isOrthogonal(3, 4, h.getSelectedItems()));

        h.getSelectedItems().add((new CardPointer(3, 4)));
        h.getSelectedItems().get(0).item = new ItemCard("none");

        assertFalse(board.isOrthogonal(4, 4, h.getSelectedItems()));

    }

    /**
     * Setup: 3 card, 2 adjacent and 1 don't
     * (4,5) should be selectable
     * (3,4) should be selectable
     * (0,0) should be not selectable
     * The condition of existence of the item inside
     * the cell is already verified in SelectCell() from PlayerController
     */
    @Test
    void testHasFreeSide() {

        board.setCell(5, 5, new ItemCard("A"));
        board.setCell(4, 5, new ItemCard("B"));
        board.setCell(3, 4, new ItemCard("L"));


        assertTrue(board.hasFreeSide(4, 5));
        assertFalse(board.hasFreeSide(0, 0));
        assertTrue(board.hasFreeSide(3, 4));

    }

    /**
     * Test when a card is surrounded in every direction.
     * (3,4) Should not be selectable
     */
    @Test
    void testHasFreeSideFalse() {
        board.setCell(2, 4, new ItemCard("A"));
        board.setCell(3, 3, new ItemCard("B"));
        board.setCell(3, 4, new ItemCard("L"));
        board.setCell(3, 5, new ItemCard("A"));
        board.setCell(4, 4, new ItemCard("B"));

        assertFalse(board.hasFreeSide(3, 4));
    }

    @Test
    void testIsPlayable() {

        assertTrue(board.isPlayable(5, 5));
        assertFalse(board.isPlayable(0, 0));
    }

    /**
     * Setup: there are 3 cards on the board in different positions
     * L shape and I shape
     * Test if I can pick all three of them, also in different order
     */
    @Test
    void testIsOrthogonal2() throws RemoteException {
        Match m1 = new Match(2);
        PlayerController c = new PlayerController("A");
        PlayerController d = new PlayerController("B");
        m1.addPlayer(c.getPlayer());
        m1.addPlayer(d.getPlayer());

        m1.currentPlayer = (c.getPlayer());

        clearBoard(m1.board);

        m1.board.setCell(4, 4, new ItemCard(ItemType.__Cats__ + "1.1"));
        m1.board.setCell(3, 4, new ItemCard(ItemType.__Cats__ + "1.1"));
        m1.board.setCell(5, 4, new ItemCard(ItemType.__Cats__ + "1.1"));

        assertTrue(c.selectCell(4, 4));
        assertTrue(c.selectCell(5, 4));
        assertTrue(c.selectCell(3, 4));

        c.clearSelectedCards();
        //Change order
        assertTrue(c.selectCell(3, 4));
        assertFalse(c.selectCell(5, 4));
        assertTrue(c.selectCell(4, 4));


    }

    /**
     * Setup: there are 3 cards on the board in different positions
     * L shape
     * Test if I can pick all three of them, also in different order
     */
    @Test
    void testIsOrthogonal3()  {
        Match m1 = new Match(2);
        PlayerController c = new PlayerController("A");
        PlayerController d = new PlayerController("B");
        m1.addPlayer(c.getPlayer());
        m1.addPlayer(d.getPlayer());

        m1.currentPlayer = (c.getPlayer());
        clearBoard(m1.board);
        m1.board.setCell(4, 4, new ItemCard(ItemType.__Cats__ + "1.1"));
        m1.board.setCell(3, 4, new ItemCard(ItemType.__Cats__ + "1.1"));
        m1.board.setCell(4, 5, new ItemCard(ItemType.__Cats__ + "1.1"));

        assertTrue(c.selectCell(4, 4));
        assertTrue(c.selectCell(3, 4));
        assertFalse(c.selectCell(4, 5));

        c.clearSelectedCards();
        //Change Order
        assertTrue(c.selectCell(3, 4));
        assertFalse(c.selectCell(4, 5));
        assertTrue(c.selectCell(4, 4));

    }

    /**
     * Test boundaries for 2 players.
     * Boundaries must have 9 elements, but the first and the last one must be null.
     */
    @Test
    void test2Seats(){
        Match m1 = new Match(2);
        PlayerController c = new PlayerController("A");
        PlayerController d = new PlayerController("B");
        m1.addPlayer(c.getPlayer());
        m1.addPlayer(d.getPlayer());

        assertEquals(9,m1.board.boundaries.size());
        assertNull(m1.board.boundaries.get(0));
        assertNull(m1.board.boundaries.get(8));

    }
    /**
     * Test boundaries for 3 player.
     * Boundaries must have 9 elements.
     */
    @Test
    void test3Seats(){
        Match m1 = new Match(3);
        PlayerController c = new PlayerController("A");
        PlayerController d = new PlayerController("B");
        PlayerController e = new PlayerController("C");
        m1.addPlayer(c.getPlayer());
        m1.addPlayer(d.getPlayer());
        m1.addPlayer(e.getPlayer());

        assertEquals(9,m1.board.boundaries.size());
    }
    /**
     * Test boundaries for 4 player.
     * Boundaries must have 9 elements.
     */
    @Test
    void test4Seats(){
        Match m1 = new Match(3);
        PlayerController c = new PlayerController("A");
        PlayerController d = new PlayerController("B");
        PlayerController e = new PlayerController("C");
        PlayerController f = new PlayerController("F");
        m1.addPlayer(c.getPlayer());
        m1.addPlayer(d.getPlayer());
        m1.addPlayer(e.getPlayer());
        m1.addPlayer(f.getPlayer());

        assertEquals(9,m1.board.boundaries.size());
    }
}
