package org.am21.player;

import org.am21.controller.PlayerController;
import org.am21.model.Cards.ItemCard;
import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.model.enumer.GamePhase;
import org.am21.model.enumer.UserStatus;
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

    /**
     * Setup: Creating a Match with 2 Seats, adding 2 players in the match.
     * The match has started.
     * Getting Board and Player p1's Shelf and setting p1 to be current player
     */
    @BeforeEach
    void setUp(){
        m =new Match(2);
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
     * Setup: Board completely filled from SetUp()
     * 1: Select (5,5) -> Not selectable
     * 2: Select (1,4) -> Selectable
     * 3: Ending selection phase: Player should not be able to use selectCell()
     * 4: Not CurrentPlayer: Player p2 try to select but fails-
     */
    @Test
    void testSelectCell(){
        // Player cannot select cell due to violation of selection rules (item need to have a free side)
        assertFalse(c1.selectCell(5,5));
        // Selection successful
        assertTrue(c1.selectCell(1,4));
        // Confirm selection
        c1.callEndSelection();
        // Selection confirmed, cannot change the cards anymore
        assertFalse(c1.selectCell(1,4));
        // The not current Player tries to select, it fails due to not having authorization
        assertFalse(c2.selectCell(1,4));

    }

    /**
     * Try to select when shelf's insertion limit has changed
     * Setup: Fill the player shelf with only one free row and try to select 2 items
     * Result: False, insertion limit is 1 and the player cannot select 2 items in this case.
     */
    @Test
    void testInsertionLimit(){
        // Filling the shelf, leaving one row free
        for(int i=0;i<Shelf.SHELF_COLUMN;i++) {
            p1.getShelf().insertInColumn(new ItemCard("Generic"), i);
            p1.getShelf().insertInColumn(new ItemCard("Generic"), i);
            p1.getShelf().insertInColumn(new ItemCard("Generic"), i);
            p1.getShelf().insertInColumn(new ItemCard("Generic"), i);
            p1.getShelf().insertInColumn(new ItemCard("Generic"), i);
        }
        // Check the amount of slot available in the shelf
        p1.getShelf().checkLimit();
        // First selection possible
        assertTrue(c1.selectCell(1,3));
        // Second selection fails due to lack of space for insertion
        assertFalse(c1.selectCell(1,4));

    }

    /**
     * Selecting 2 adjacent cards.
     * And another one, which will not pass the orthogonality check
     */
    @Test
    void testSelectCellWithHand(){

        c1.selectCell(1,4);
        assertTrue(c1.selectCell(1,3));

        // The item cannot be selected due to violation of the game rule
        assertFalse(c1.selectCell(2,3));

    }

    /**
     * What happens when a player select an already selected item on board?
     * That one item will be deselected.
     * Test cases:
     * 1. select one item and then select it again
     * 2. select 2 items, select again the first one, the hand should not contain the item anymore
     */
    @Test
    void testDeselectCell(){
        c1.selectCell(1,4);
        //Select the same cell, it should trigger the deselection of the cell
        c1.selectCell(1,4);
        // The item is removed from the selected item list
        assertTrue(c1.isHandEmpty());
        c1.selectCell(1,4);
        c1.selectCell(1,3);
        c1.selectCell(1,4);
        // The position of the items has changed in the selected items list
        assertNotEquals(c1.getHand().getSelectedItems().get(0).item, b.getMatrix()[1][4]);
    }

    /**
     * The player has selected 3 items, what happens if the middle card is selected again?
     * All items will be deselected, because the property of the selected cards would be broken
     */
    @Test
    void testCenterDeselection(){
        //Clearing the second row, allowing 3 items selection
        b.setCell(1,3,null);
        b.setCell(1,4,null);

        c1.selectCell(2,3);
        c1.selectCell(2,4);
        assertTrue(c1.selectCell(2,5));
        // Select the center item
        c1.selectCell(2,4);
        // All the selected items cleared
        assertTrue(c1.isHandEmpty());
    }

    /**
     * Setup: 2 cards in Hand.getSelectedItems
     * After calling clearSelectedCards(), "Hand" should be cleared
     */
    @Test
    void testClearSelectCards(){
        c1.selectCell(1,4);
        c1.selectCell(1,3);
        assertEquals(2,c1.getHand().getSelectedItems().size());
        // Call method to clear selected items
        assertTrue(c1.clearSelectedCards());
        assertTrue(c1.isHandEmpty());
        assertEquals(0, c1.getHand().getSelectedItems().size());

    }

    /**
     * Test : MoveAllToHand
     * Moving on to the Insertion Phase by calling callEndSelection.
     * It will call moveAllToHand which will remove the cards from the board
     * When successful, the selected items should not be on the board anymore
     */
    @Test
    void testMoveAllToHand(){
        c1.selectCell(1,4);
        c1.selectCell(1,3);
        // Confirm selection
        c1.callEndSelection();
        // The items are removed from the board
        assertFalse(b.isOccupied(1,4));
        assertFalse(b.isOccupied(1,3));

    }

    /**
     * Test Passage of phases
     * CallEndInsertion should call a sequence of method.
     * Where at the end,it is going to be the next player turn and Selection phase
     */
    @Test
    void testCallingPhases(){
        c1.selectCell(4,1);
        // Confirm selection
        c1.callEndSelection();
        //Verify if the GamePhase has changed to Insertion
        assertEquals(m.gamePhase, GamePhase.Insertion);
        // Confirm insertion and pass turn
        c1.callEndInsertion();
        //Verify if the GamePhase has changed to Selection and the current player has changed
        assertEquals(m.gamePhase, GamePhase.Selection);
        assertEquals(m.currentPlayer, p2);

    }

    /**
     * Selected 2 cards and changing their order.
     * changeHandOrder is allowed only after callEndSelection().
     * Check also if the index are correct
     * Control if the references were changed
     */
    @Test
    void testChangeHandOrder(){
        c1.selectCell(1,4);
        c1.selectCell(1,3);
        CardPointer t= c1.getHand().getSelectedItems().get(0);
        CardPointer f= c1.getHand().getSelectedItems().get(1);
        c1.callEndSelection();
        // Fails for selecting the wrong indexes
        assertFalse(c1.changeHandOrder(1,2));
        c1.changeHandOrder(0,1);
        // Check if the position are changed
        assertEquals(c1.getHand().getSelectedItems().get(1), t);
        assertEquals(c1.getHand().getSelectedItems().get(0), f);
    }

    /**
     * Setup: 2 items selected
     * Test tryToInsert.
     * Wrong phase, tryToInsert is allowed during Insertion Phase
     */
    @Test
    void testTryToInsert(){
        c1.selectCell(1,4);
        c1.selectCell(1,3);
        // Wrong phase cannot insert
        assertFalse(c1.tryToInsert(0));
        // Confirm selection and end selection phase
        c1.callEndSelection();
        // Insert
        assertTrue(c1.tryToInsert(0));
        //Control if the slot is occupied
        assertTrue(s.isOccupied(5,0));

    }



    /**
     * Verification game design:
     * - Cannot insert without selection confirm
     * - Insertion of cards in just one column
     */
    @Test
    void testTryToInsert2(){
        c1.selectCell(1,4);
        c1.selectCell(1,3);
        // Insertion fails
        assertFalse(c1.tryToInsert(0));
        c1.callEndSelection();
        assertTrue(c1.tryToInsert(0));
        assertTrue(s.isOccupied(5,0));
        assertTrue(s.isOccupied(4,0));
        // Other column is not changed
        assertFalse(s.isOccupied(5,1));
    }

    /**
     * Setup: Column 0 contains 5 items, select 2 cards
     * Trying to insert in a column without slots available (column 0), but with
     */
    @Test
    void testTryToInsert3(){
        //Fill column
        c1.getPlayer().getShelf().insertInColumn(new ItemCard("Generic"),0);
        c1.getPlayer().getShelf().insertInColumn(new ItemCard("Generic"),0);
        c1.getPlayer().getShelf().insertInColumn(new ItemCard("Generic"),0);
        c1.getPlayer().getShelf().insertInColumn(new ItemCard("Generic"),0);
        c1.getPlayer().getShelf().insertInColumn(new ItemCard("Generic"),0);
        // Check shelf slot available
        p1.getShelf().checkLimit();
        c1.selectCell(1,3);
        assertTrue(c1.selectCell(1,4));
        // The column has not enough space for insertion
        assertFalse(c1.tryToInsert(0));
    }

    /**
     * Test: callEndInsertion
     * Try to Confirm Selection without selecting any cards
     */
    @Test
    void testCallEndSelection(){
        assertFalse(c1.callEndSelection());
    }

    /**
     * Try to insert in the shelf without selecting: fails
     */
    @Test
    void testTryToInsert4(){
        assertFalse(c1.tryToInsert(0));
    }

    /**
     * Test: dropHand()
     * Player got suspended from the match while he already selected some cards.
     */
    @Test
    void testDropHand(){
        c1.selectCell(1,4);
        c1.selectCell(1,3);
        // Simulate suspension
        c1.getPlayer().setStatus(UserStatus.Suspended);
        // Simulate the call of dropHand()
        c1.dropHand();
        assertEquals(0,c1.getHand().getSelectedItems().size());

    }

    /**
     * Player got suspended from the match while he already picked some cards.
     * The cards should get back on the board.
     * Expect the items to go back on the board
     */
    @Test
    void testDropHand2(){
        c1.selectCell(1,4);
        c1.selectCell(1,3);
        c1.callEndSelection();
        assertFalse(m.board.isOccupied(1,4));
        assertFalse(m.board.isOccupied(1,3));
        c1.getPlayer().setStatus(UserStatus.Suspended);
        c1.dropHand();
        // Return the items on the board
        assertTrue(m.board.isOccupied(1,4));
        assertTrue(m.board.isOccupied(1,3));
        assertEquals(0,c1.getHand().getSelectedItems().size());

    }


}
