package org.am21.game;

import org.am21.controller.PlayerController;
import org.am21.model.Cards.CommonGoals.CommonGoalXShape;
import org.am21.model.Cards.ItemCard;
import org.am21.model.Cards.ItemType;
import org.am21.model.Cards.PersonalGoalCard;
import org.am21.model.GameManager;
import org.am21.model.Match;
import org.am21.model.enumer.GameState;
import org.am21.model.enumer.UserStatus;
import org.am21.model.items.Shelf;
import org.am21.model.virtualview.ServerVirtualView;
import org.am21.utilities.VirtualViewHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VirtualViewTest {
    Match m;
    PlayerController c1;
    PlayerController c2;


    /**
     * We create a match and 2 players.
     * Before startMatch, virtualView attributes are null.
     */
    @BeforeEach
    void setUp() {
        c1 = new PlayerController("A");
        c2 = new PlayerController("B");
        GameManager.createMatch(2, c1);
        m = c1.getPlayer().getMatch();
        m.addPlayer(c2.getPlayer());
    }

    @AfterEach
    void tearDown() {
        m = null;
        c1 = null;
        c2 = null;

    }

    /**
     * Test VirtualView attributes values
     * after the match began (after startFirstRound())
     * - VirtualViewHelper.buildVirtualView(m)
     * - updatePlayersView()
     * are called
     */
    @Test
    void testAfterStart() {

        //Match Started
        assertEquals(c1.getPlayer().getNickname(), m.virtualView.admin);
        assertEquals(2, m.virtualView.maxSeats);
        assertNotNull(m.virtualView.board);
        String[][] wish = {
                {"o", "o", "o", "o", "o", "o", "o", "o", "o"},
                {"o", "o", "o", "x", "x", "o", "o", "o", "o"},
                {"o", "o", "o", "x", "x", "x", "o", "o", "o"},
                {"o", "o", "x", "x", "x", "x", "x", "x", "o"},
                {"o", "x", "x", "x", "x", "x", "x", "x", "o"},
                {"o", "x", "x", "x", "x", "x", "x", "o", "o"},
                {"o", "o", "o", "x", "x", "x", "o", "o", "o"},
                {"o", "o", "o", "o", "x", "x", "o", "o", "o"},
                {"o", "o", "o", "o", "o", "o", "o", "o", "o"}
        };
        checkTheBoardAsIWish(wish, m.virtualView.board);
        assertNotNull(m.virtualView.commonGoals);
        assertNotNull(m.virtualView.commonGoals.get(0));
        assertNotNull(m.virtualView.commonGoals.get(1));
        assertNotNull(m.virtualView.commonGoalScores);
        assertEquals(8, m.virtualView.commonGoalScores.get(0));
        assertEquals(8, m.virtualView.commonGoalScores.get(1));
        assertNotNull(m.virtualView.players);
        assertEquals(c1.getPlayer().getNickname(), m.virtualView.players.get(0));
        assertEquals(c2.getPlayer().getNickname(), m.virtualView.players.get(1));
        assertNotNull(m.virtualView.currentPlayer);
        assertNotNull(m.virtualView.currentPlayerHand);
        assertEquals(0, m.virtualView.currentPlayerHand.size());
        assertNotNull(m.virtualView.shelves);
        assertNotNull(m.virtualView.shelves.get(0));
        for (int i = 0; i < m.virtualView.shelves.get(0).length; i++) {
            for (int j = 0; j < m.virtualView.shelves.get(0)[i].length; j++) {
                assertNull(m.virtualView.shelves.get(0)[i][j]);

            }

        }
        assertNotNull(m.virtualView.shelves.get(1));
        assertNotNull(m.virtualView.personalGoals);
        assertNotNull(m.virtualView.personalGoals.get(0));
        assertNotNull(m.virtualView.personalGoals.get(1));
        assertEquals(m.chairman.getNickname(), m.virtualView.currentPlayer);

        VirtualViewHelper.printJSON(m);


    }

    private void checkTheBoardAsIWish(String[][] wish, String[][] board) {
        if (wish != null && wish.length > 0 && wish.length == board.length && wish[0].length == board[0].length) {

            for (int i = 0; i < wish.length; i++) {
                for (int j = 0; j < wish[i].length; j++) {
                    if (wish[i][j] != null && wish[i][j].equals("x")) {
                        assertNotNull(m.virtualView.board[i][j]);
                    }
                }
            }
        }
    }

    /**
     * Test: update virtual view after selection
     * CurrentPlayerHand should contain 2 items name
     * On the board the selected cells should have a ">" mark
     */
    @Test
    void testViewSelection() {
        assertEquals(GameState.GameGoing, m.gameState);
        m.currentPlayer = c1.getPlayer();

        assertTrue(c1.selectCell(1, 3));
        assertTrue(c1.selectCell(1, 4));
        assertEquals(c1.getHand().getSelectedItems().get(0).item.getNameCard(), m.virtualView.currentPlayerHand.get(0));
        assertEquals(c1.getHand().getSelectedItems().get(1).item.getNameCard(), m.virtualView.currentPlayerHand.get(1));
        assertEquals(">" + m.board.getCell(1, 3).getNameCard(), m.virtualView.board[1][3]);
        assertEquals(">" + m.board.getCell(1, 4).getNameCard(), m.virtualView.board[1][4]);

    }

    /**
     * Test: update virtual view after clear selection
     * CurrentPlayerHand should be empty
     * On the board the selected cells should not have a ">" mark
     */
    @Test
    void testViewClearSelection() {
        assertEquals(GameState.GameGoing, m.gameState);
        m.currentPlayer = c1.getPlayer();

        assertTrue(c1.selectCell(1, 3));
        assertTrue(c1.selectCell(1, 4));

        c1.clearSelectedCards();
        assertEquals(0, m.virtualView.currentPlayerHand.size());
        assertEquals(m.board.getCell(1, 3).getNameCard(), m.virtualView.board[1][3]);
        assertEquals(m.board.getCell(1, 4).getNameCard(), m.virtualView.board[1][4]);

    }

    /**
     * Test: update virtual view after deselection
     * CurrentPlayerHand should lose an item
     * On the board the deselected cell should not have a ">" mark
     */
    @Test
    void testViewDeselection() {
        assertEquals(GameState.GameGoing, m.gameState);
        m.currentPlayer = c1.getPlayer();

        assertTrue(c1.selectCell(1, 3));
        assertTrue(c1.selectCell(1, 4));
        assertEquals(">" + m.board.getCell(1, 3).getNameCard(), m.virtualView.board[1][3]);

        c1.selectCell(1, 3);
        assertEquals(c1.getHand().getSelectedItems().get(0).item.getNameCard(), m.virtualView.currentPlayerHand.get(0));
        assertEquals(m.board.getCell(1, 3).getNameCard(), m.virtualView.board[1][3]);
        assertEquals(">" + m.board.getCell(1, 4).getNameCard(), m.virtualView.board[1][4]);

    }

    /**
     * Test end selection
     * Virtual board should not have the confirmed selected items
     * Virtual Hand should be the same
     */
    @Test
    void testViewEndSelection() {
        assertEquals(GameState.GameGoing, m.gameState);
        m.currentPlayer = c1.getPlayer();

        assertTrue(c1.selectCell(1, 3));
        assertTrue(c1.selectCell(1, 4));

        assertTrue(c1.callEndSelection());
        assertNull(m.virtualView.board[1][3]);
        assertNull(m.virtualView.board[1][4]);
        assertEquals(c1.getHand().getSelectedItems().get(0).item.getNameCard(), m.virtualView.currentPlayerHand.get(0));
        assertEquals(c1.getHand().getSelectedItems().get(1).item.getNameCard(), m.virtualView.currentPlayerHand.get(1));

    }

    /**
     * Test Insertion
     * Virtual shelf should have the new items
     * And the hand should be empty
     */
    @Test
    void testViewInsertion() {
        m.currentPlayer = c1.getPlayer();

        assertTrue(c1.selectCell(1, 3));
        assertTrue(c1.selectCell(1, 4));
        c1.callEndSelection();
        assertTrue(c1.tryToInsert(1));
        assertEquals(0, m.virtualView.currentPlayerHand.size());
        assertEquals(c1.getPlayer().getShelf().getCell(5, 1).getNameCard(), m.virtualView.shelves.get(0)[5][1]);
        assertEquals(c1.getPlayer().getShelf().getCell(4, 1).getNameCard(), m.virtualView.shelves.get(0)[4][1]);

    }

    /**
     * Test DropHand
     * When player is suspended, the selected cards should be back on the board
     */
    @Test
    void testViewDropHand(){
        m.currentPlayer = c1.getPlayer();
        c1.selectCell(1,3);
        c1.selectCell(1,4);

        c1.callEndSelection();
        c1.getPlayer().setStatus(UserStatus.Suspended);
        c1.dropHand();

        assertEquals(0,m.virtualView.currentPlayerHand.size());
        assertEquals(m.board.getCell(1,3).getNameCard(),m.virtualView.board[1][3]);
        assertEquals(m.board.getCell(1,3).getNameCard(),m.virtualView.board[1][4]);

    }

    /**
     * Test change hand order
     */
    @Test
    void testViewChangeOrder(){
        m.currentPlayer = c1.getPlayer();
        c1.selectCell(1,3);
        c1.selectCell(1,4);
        c1.callEndSelection();
        assertTrue(c1.changeHandOrder(0,1));
        assertEquals(c1.getHand().getSelectedItems().get(0).item.getNameCard(), m.virtualView.currentPlayerHand.get(0));
        assertEquals(c1.getHand().getSelectedItems().get(1).item.getNameCard(), m.virtualView.currentPlayerHand.get(1));
    }

    /**
     * Test End
     */
    @Test
    void testViewEndTurn(){
        m.currentPlayer = c1.getPlayer();
        Shelf currShelf = m.currentPlayer.getShelf();
        m.currentPlayer.setMyPersonalGoal(new PersonalGoalCard("PERSONAL_GOAL03"));
        m.currentPlayer.getMyPersonalGoal().player=m.currentPlayer;
        m.commonGoals.set(0, new CommonGoalXShape(2));
        String[][] wish = {
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", ItemType._Plants_ + "1.1", ItemType._Plants_ + "1.1", "", ""},
                {ItemType.__Cats__ + "1.1", ItemType._Plants_ + "1.1", ItemType.__Cats__ + "1.1", "", ""},
                {ItemType._Plants_ + "1.1", ItemType.__Cats__ + "1.1", ItemType._Plants_ + "1.1", "", ""},
                {ItemType.__Cats__ + "1.1", ItemType._Plants_ + "1.1", ItemType.__Cats__ + "1.1", "", ""}
        };

        fillTheShelfAsIWish(wish, currShelf);
        m.checkCommonGoals(m.currentPlayer);
        m.currentPlayer.setHiddenPoints(m.currentPlayer.getMyPersonalGoal().calculatePoints());
        m.callEndTurnRoutine();

        assertEquals(4,m.virtualView.commonGoalScores.get(0));
        assertEquals(8,m.virtualView.scores.get(0));
        assertEquals(1,m.virtualView.hiddenPoints.get(0));

        assertEquals(c2.getPlayer().getNickname(),m.virtualView.currentPlayer);
    }

    /**
     * This method is used to custom fill a shelf
     * @param wish shelf markup for filling
     * @param shelf player shelf
     */
    private void fillTheShelfAsIWish(String[][] wish, Shelf shelf) {
        if (wish != null && wish.length > 0 && wish.length == shelf.gRow && wish[0].length == shelf.gColumn) {

            for (int i = 0; i < wish.length; i++) {
                for (int j = 0; j < wish[i].length; j++) {
                    if (wish[i][j] != null && !wish[i][j].equals("")) {
                        shelf.setCell(i, j, new ItemCard(wish[i][j]));
                    }
                }
            }
        }
    }

    /**
     * Test Server Virtual View Virtual Match list
     */
    @Test
    void testViewStartRound(){
        assertEquals(GameState.GameGoing.toString(),ServerVirtualView.instance.virtualMatchList[0][1]);
    }

    @Test
    void testViewLastRound(){
        m.currentPlayer = c1.getPlayer();
        Shelf currShelf = m.currentPlayer.getShelf();
        String[][] wish = {
                {ItemType.__Cats__ + "1.1", ItemType._Plants_ + "1.1", ItemType.__Cats__ + "1.1", ItemType.__Cats__ + "1.1", ItemType.__Cats__ + "1.1"},
                {ItemType.__Cats__ + "1.1", ItemType._Plants_ + "1.1", ItemType.__Cats__ + "1.1", ItemType.__Cats__ + "1.1", ItemType.__Cats__ + "1.1"},
                {ItemType.__Cats__ + "1.1", ItemType._Plants_ + "1.1", ItemType.__Cats__ + "1.1", ItemType.__Cats__ + "1.1", ItemType.__Cats__ + "1.1"},
                {ItemType.__Cats__ + "1.1", ItemType._Plants_ + "1.1", ItemType.__Cats__ + "1.1", ItemType.__Cats__ + "1.1", ItemType.__Cats__ + "1.1"},
                {ItemType.__Cats__ + "1.1", ItemType._Plants_ + "1.1", ItemType.__Cats__ + "1.1", ItemType.__Cats__ + "1.1", ItemType.__Cats__ + "1.1"},
                {ItemType.__Cats__ + "1.1", ItemType._Plants_ + "1.1", ItemType.__Cats__ + "1.1", ItemType.__Cats__ + "1.1", ItemType.__Cats__ + "1.1"}
        };

        fillTheShelfAsIWish(wish, currShelf);

        c1.getPlayer().getShelf().slotCol.set(0,0);
        c1.getPlayer().getShelf().slotCol.set(1,0);
        c1.getPlayer().getShelf().slotCol.set(2,0);
        c1.getPlayer().getShelf().slotCol.set(3,0);
        c1.getPlayer().getShelf().slotCol.set(4,0);
        m.callEndTurnRoutine();
        assertEquals(GameState.LastRound,m.gameState);

    }
}
