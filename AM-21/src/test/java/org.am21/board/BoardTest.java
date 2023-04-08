package org.am21.board;

import org.am21.controller.PlayerController;
import org.am21.model.Cards.ItemCard;
import org.am21.model.Match;
import org.am21.model.items.Bag;
import org.am21.model.items.Board;
import org.am21.model.items.Hand;
import org.am21.utilities.CardPointer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class BoardTest {
    private Match m;
    private Board board;
    private Bag bag;
    private static int seats=2;


    @BeforeEach
    void setUp() {

        m= new Match(seats);
        bag = new Bag(m);
        board = new Board(m);
        board.setupBoard();
        clearBoard(board);

    }


    @AfterEach
    void tearDown() {
        m=null;
        board =null;
        bag = null;
    }

    /**
     * Auxiliary Method for board reset
     * @param b
     */
    public void clearBoard(Board b){
        for(int i=0;i<b.gRow;i++) {
            for(int j=0;j<b.gColumn;j++){
                b.setCell(i,j,null);

            }
        }

    }


    /**
     * Test if the board size is 45.
     *
     * Expect NotEquals, in fact the maxSeats is 2 ,
     * so it should be 29.
     *
     */
    @Test
    void testSize(){

        assertNotEquals(45, board.getSize());

    }

    /**
     * Test if item in a cell:
     * -If it doesn't exist, it means the setup of the board has failed
     * -If the test is successful it, means the setup was done right
     *
     * In fact, during boardSetup all the cells in the grid are initialized
     */
    @Test
    void testCell(){

        assertNull(board.getCell(0,0));

    }


    /**
     * Test if, after an insertion, the cell is occupied
     * 1: Playable cell     -> isOccupied=> true if there is an item
     * 2: Not playable cell -> isOccupied=>false
     */
    @Test
    void testOccupation(){
        board.setCell(5,5,new ItemCard("Leo"));
        assertTrue(board.isOccupied(5,5));

        assertFalse(board.isOccupied(0,0));
    }

    /**
     * Setup: Board is empty
     * After inserting 2 adjacent item,
     * it tests if the board need refill
     * Expected : False
     * Game Rule: Board need refill when every item has no adjacent
     */
    @Test
    void testCheckBoardFalse(){

        board.setCell(5,5,new ItemCard("Leo"));
        board.setCell(5,4,new ItemCard("MiniLeo"));
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
    void testCheckBoardTrue(){

        board.setCell(5,5,new ItemCard("Leo"));
        board.setCell(4,4,new ItemCard("MiniLeo"));
        assertTrue(board.checkBoard());

    }

    /**
     * Test if a cell is alone
     */
    @Test
    void testIsAlone(){

        board.setCell(5,5,new ItemCard("Koko"));
        assertTrue(board.isAlone(5,5));

        board.setCell(4,5,new ItemCard("kiki"));
        assertFalse(board.isAlone(5,5));

    }

    /**
     * Setup: Hand with some cards
     * Test if the card respect the selection condition when other cards have been already selected
     */
    @Test
    void testIsOrthogonal(){

        Hand h = new Hand(new PlayerController("").player);

        h.getSlot().add(new CardPointer(3,3));
        h.getSlot().get(0).item= new ItemCard("none");

        assertTrue(board.isOrthogonal(3,4,h));

        h.getSlot().add((new CardPointer(3,4)));
        h.getSlot().get(0).item = new ItemCard("none");

        assertFalse(board.isOrthogonal(4,4,h));

    }

    /**
     * Setup: 3 card, 2 adjacent and 1 don't
     * (4,5) should be selectable
     * (3,4) should be selectable
     * (0,0) should be not selectable
     *
     *The condition of existence of the item inside
     * the cell is already verified in SelectCell() from PlayerController
     */
    @Test
    void testHasFreeSide(){

        board.setCell(5,5,new ItemCard("Ken"));
        board.setCell(4,5,new ItemCard("BigKen"));
        board.setCell(3,4,new ItemCard("LilKen"));


        assertTrue(board.hasFreeSide(4,5));
        assertFalse(board.hasFreeSide(0,0));
        assertTrue(board.hasFreeSide(3,4));

    }

    /**
     * Test when a card is surrounded in every direction.
     * (3,4) Should not be selectable
     *
     */
    @Test
    void testHasFreeSideFalse(){
        board.setCell(2,4,new ItemCard("Ken"));
        board.setCell(3,3,new ItemCard("BigKen"));
        board.setCell(3,4,new ItemCard("LilKen"));
        board.setCell(3,5,new ItemCard("Ken"));
        board.setCell(4,4,new ItemCard("BigKen"));

        assertFalse(board.hasFreeSide(3,4));
    }

}
