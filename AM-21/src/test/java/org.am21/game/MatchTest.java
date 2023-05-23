package org.am21.game;

import org.am21.controller.PlayerController;
import org.am21.model.Cards.ItemCard;
import org.am21.model.Cards.ItemType;
import org.am21.model.GameManager;
import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.model.enumer.GamePhase;
import org.am21.model.enumer.GameState;
import org.am21.model.items.Shelf;
import org.am21.networkRMI.ClientCallBack;
import org.am21.networkRMI.ClientInputHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @version 1.0
 */
class MatchTest {
    Match m;

    @BeforeEach
    void setUp() {

        m = new Match(2);
    }

    @AfterEach
    void tearDown() {
        m = null;
    }

    /**
     * Test if the method works correctly
     * Setup : the match has already reached his player number limit(2)
     * Test case: What happens when this method is called one more time?
     * Result expected: There should be 2 players in the match
     */
    @Test
    void testAddPlayer() throws RemoteException {
        PlayerController c1 = new PlayerController("A");
        c1.clientInput.callBack = new ClientCallBack();
        PlayerController c2 = new PlayerController("B");
        c2.clientInput.callBack = new ClientCallBack();
        PlayerController c3 = new PlayerController("C");
        c3.clientInput.callBack = new ClientCallBack();
        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());
        m.addPlayer(c3.getPlayer());

        assertEquals(2, m.playerList.size());
    }

    /**
     * Test if the method works correctly
     * When called verify if all the attributes are correct.
     */
    @Test
    void testInitializeMatch() throws RemoteException {
        PlayerController c1 = new PlayerController("A");
        c1.clientInput.callBack = new ClientCallBack();

        PlayerController c2 = new PlayerController("B");
        c2.clientInput.callBack = new ClientCallBack();
        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());
        //startMatch() is called automatically
        //startFirstRound() is called after that
        //Check gamestate
        assertTrue(m.gameState.equals(GameState.GameGoing));
        //Check chairman
        assertTrue(m.playerList.contains(m.chairman));
        //Check if all players personal goal are different
        for (Player x : m.playerList) {
            //Check also if each player is mapped in playerMatchMap
            assertTrue(GameManager.playerMatchMap.containsKey(x.getNickname()));
            for (Player y : m.playerList) {
                if (!y.equals(x))
                    assertTrue(!x.getMyPersonalGoal().equals(y.getMyPersonalGoal()));
            }
        }
        //Check if common goals are different
        assertFalse(m.commonGoals.get(0).equals(m.commonGoals.get(1)));

        //Check if board is completely refilled
        int k = 0;
        if (m.maxSeats == 2) k = 1;
        for (int i = 0 + k; i < m.board.boundaries.size() - k; i++) {
            for (int j = m.board.boundaries.get(i).x; j < m.board.boundaries.get(i).y; j++) {
                assertTrue(m.board.isOccupied(i, j));
            }
        }
    }

    /**
     * Setup: MaxSeats: 4
     * We have 3 players waiting to begin. One gets removed.
     * Test if removePlayer() works.
     */
    @Test
    void testRemovePlayer() throws RemoteException {
        m = new Match(4);
        PlayerController c1 = new PlayerController("A", new ClientInputHandler());
        c1.clientInput.callBack = new ClientCallBack();
        PlayerController c2 = new PlayerController("B", new ClientInputHandler());
        c2.clientInput.callBack = new ClientCallBack();
        PlayerController c3 = new PlayerController("C", new ClientInputHandler());
        c3.clientInput.callBack = new ClientCallBack();

        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());
        m.addPlayer(c3.getPlayer());
        //Match not started yet

        m.removePlayer(c1.getPlayer());

        assertTrue(m.playerList.get(0).equals(c2.getPlayer()));
        assertEquals(2, m.playerList.size());

    }


    /**
     * Test if the method works correctly
     * Setup: match initialized
     * - GameState==GameGoing
     * - GamePhase==Selection
     * - CurrentPlayer contained in playerList
     */
    @Test
    void testStartFirstRound() throws RemoteException {
        m = new Match(2);
        PlayerController c1 = new PlayerController("A", new ClientInputHandler());
        c1.clientInput.callBack = new ClientCallBack();
        PlayerController c2 = new PlayerController("B", new ClientInputHandler());
        c2.clientInput.callBack = new ClientCallBack();
        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());
        //Match initialized, then it should call startFirstRound() automatically

        assertTrue(m.gameState == GameState.GameGoing);
        assertTrue(m.gamePhase == GamePhase.Selection);
        assertTrue(m.playerList.contains(m.currentPlayer));
        //TODO: assertNotNull(m.timer);

    }

    /**
     * What if a player leave the match while the match is starting?
     * The match should end, thanks to checkRoom() that calls endMatch()
     * Test:
     * During a game, if endMatch is called what will happen?
     * Expectation: GameState=Closed, playerList cleared, board cleared,
     * playerMatchMap doesn't contain players of the match
     */
    @Test
    void testCheckRoomAndEndMatch() throws RemoteException {
        m = new Match(2);
        PlayerController c1 = new PlayerController("A", new ClientInputHandler());
        c1.clientInput.callBack = new ClientCallBack();
        PlayerController c2 = new PlayerController("B", new ClientInputHandler());
        c2.clientInput.callBack = new ClientCallBack();
        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());
        //Match started
        assertTrue(m.gameState == GameState.GameGoing);
        m.removePlayer(c1.getPlayer());
        assertTrue(m.gameState == GameState.Closed);
        assertEquals(0, m.playerList.size());
        assertFalse(GameManager.playerMatchMap.containsKey(c1.getPlayer()));
        assertFalse(GameManager.playerMatchMap.containsKey(c2.getPlayer()));

    }

    /**
     * Test if the current player after calling nextTurn() is the next in list
     */
    @Test
    void testNextTurn() throws RemoteException {
        m = new Match(4);
        PlayerController c1 = new PlayerController("A", new ClientInputHandler());
        c1.clientInput.callBack = new ClientCallBack();
        PlayerController c2 = new PlayerController("B", new ClientInputHandler());
        c2.clientInput.callBack = new ClientCallBack();
        PlayerController c3 = new PlayerController("C", new ClientInputHandler());
        c3.clientInput.callBack = new ClientCallBack();
        PlayerController c4 = new PlayerController("D", new ClientInputHandler());
        c4.clientInput.callBack = new ClientCallBack();
        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());
        m.addPlayer(c3.getPlayer());
        m.addPlayer(c4.getPlayer());
        //Start

        Player prev = m.currentPlayer;
        m.nextTurn();
        assertTrue(m.currentPlayer.equals(m.playerList.get((m.playerList.indexOf(prev) + 1) % m.maxSeats)));


    }

    /**
     * Setup:
     * Player with a full shelves.
     * If callEndRouting is called by playerController.callEndInsertion:
     * - GamePhase changes to LastRound
     * - EndGameToke should be false
     * - firstToComplete should be set
     * After the first player completed the shelves, the second player can play one last turn
     * and the endMatch will be called.
     */
    @Test
    void testCallEndTurnRoutine() throws RemoteException {
        m = new Match(2);
        PlayerController c1 = new PlayerController("A", new ClientInputHandler());
        c1.clientInput.callBack = new ClientCallBack();
        PlayerController c2 = new PlayerController("B", new ClientInputHandler());
        c2.clientInput.callBack = new ClientCallBack();
        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());
        m.currentPlayer = c1.getPlayer();
        c1.selectCell(4, 1); //Need a card in my hand to call end selection
        Shelf s = c1.getPlayer().getShelf();
        c1.callEndSelection();
        //Simulate full insertion of the shelves
        for (int i = 0; i < Shelf.SHELF_COLUMN; i++) {
            s.insertInColumn(new ItemCard(ItemType.__Cats__ + "1.1"), i);
            s.insertInColumn(new ItemCard(ItemType.__Cats__ + "1.1"), i);
            s.insertInColumn(new ItemCard(ItemType.__Cats__ + "1.1"), i);
            s.insertInColumn(new ItemCard(ItemType.__Cats__ + "1.1"), i);
            s.insertInColumn(new ItemCard(ItemType.__Cats__ + "1.1"), i);
            s.insertInColumn(new ItemCard(ItemType.__Cats__ + "1.1"), i);
        }
        assertEquals(0, s.getTotSlotAvail());
        c1.callEndInsertion();
        assertTrue(m.gameState == GameState.LastRound);
        assertFalse(m.isEndGameToken());
        assertEquals(m.getFirstToComplete(), c1.getPlayer());
        c2.selectCell(5, 1);
        c2.callEndSelection();
        c2.callEndInsertion();
        assertTrue(m.gameState == GameState.Closed);
    }

    /**
     * Setup:
     * 1: 4 player, 4 different score
     * Expect 1 winner
     * 2: 2 player with same highest score
     * Expect no winner
     * 4: if one of the two players that has the same best score leaves the game
     * Expect 1 winner
     */
    @Test
    void testDeclareWinner() throws RemoteException {
        m = new Match(4);
        PlayerController c1 = new PlayerController("A", new ClientInputHandler());
        c1.clientInput.callBack = new ClientCallBack();
        PlayerController c2 = new PlayerController("B", new ClientInputHandler());
        c2.clientInput.callBack = new ClientCallBack();
        PlayerController c3 = new PlayerController("C", new ClientInputHandler());
        c3.clientInput.callBack = new ClientCallBack();
        PlayerController c4 = new PlayerController("D", new ClientInputHandler());
        c4.clientInput.callBack = new ClientCallBack();
        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());
        m.addPlayer(c3.getPlayer());
        m.addPlayer(c4.getPlayer());

        c1.addScore(20);    //20
        c2.addScore(18);    //18
        c3.addScore(30);    //30
        c4.addScore(10);    //10

        m.decideWinner();
        assertEquals(c3.getPlayer(), m.winner);

        c1.addScore(10);    //30 same as c3
        m.decideWinner();
        assertNull(m.winner);
        m.removePlayer(c3.getPlayer());
        assertEquals(GameState.Closed, m.gameState);
        assertEquals(c1.getPlayer(), m.winner);


    }

    /**
     * What if every player has 0 score?
     * I expect no winner
     * What if someone leaves the match?
     * I expect no winner
     */
    @Test
    void testDeclareWinner2() throws RemoteException {
        m = new Match(4);
        PlayerController c1 = new PlayerController("A", new ClientInputHandler());
        c1.clientInput.callBack = new ClientCallBack();
        PlayerController c2 = new PlayerController("B", new ClientInputHandler());
        c2.clientInput.callBack = new ClientCallBack();
        PlayerController c3 = new PlayerController("C", new ClientInputHandler());
        c3.clientInput.callBack = new ClientCallBack();
        PlayerController c4 = new PlayerController("D", new ClientInputHandler());
        c4.clientInput.callBack = new ClientCallBack();
        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());
        m.addPlayer(c3.getPlayer());
        m.addPlayer(c4.getPlayer());
        assertEquals(0, c1.getPlayer().getPlayerScore());
        m.decideWinner();
        assertNull(m.winner);

        m.removePlayer(c2.getPlayer());
        assertEquals(GameState.Closed, m.gameState);
        assertNull(m.winner);


    }


}
