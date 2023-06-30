package org.am21.game;

import org.am21.controller.PlayerController;
import org.am21.model.Cards.CommonGoals.CommonGoalDiagonal;
import org.am21.model.Cards.CommonGoals.CommonGoalXShape;
import org.am21.model.Cards.ItemCard;
import org.am21.model.Cards.ItemType;
import org.am21.model.Cards.PersonalGoalCard;
import org.am21.model.GameManager;
import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.model.enumer.GamePhase;
import org.am21.model.enumer.GameState;
import org.am21.model.enumer.UserStatus;
import org.am21.model.items.Shelf;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;

class MatchTest {
    Match m;

    /**
     * SetUp: an empty match with 2 players
     */
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
    void testAddPlayer() {
        PlayerController c1 = new PlayerController("A");
        PlayerController c2 = new PlayerController("B");
        PlayerController c3 = new PlayerController("C");
        m.addPlayer(c1.getPlayer());
        // Control if the gameState is WaitingPlayers
        assertEquals(GameState.WaitingPlayers, m.gameState);

        m.addPlayer(c2.getPlayer());

        assertFalse(m.addPlayer(c3.getPlayer()));
        assertNotEquals(c3.getPlayer().getStatus(), UserStatus.GameMember);

        assertEquals(2, m.playerList.size());
    }

    /**
     * Test if the method initializeMatch() works correctly
     * When called verify that all the attributes are correct.
     */
    @Test
    void testInitializeMatch() {
        PlayerController c1 = new PlayerController("A");
        PlayerController c2 = new PlayerController("B");
        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());
        //startMatch() is called automatically
        //startFirstRound() is called after that
        //Check gameState
        assertEquals(m.gameState, GameState.GameGoing);
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

        //Check if board is completely filled
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
     * Test if removePlayer() works and the match is not ended.
     */
    @Test
    void testRemovePlayer() {
        m = new Match(4);
        PlayerController c1 = new PlayerController("A");
        PlayerController c2 = new PlayerController("B");
        PlayerController c3 = new PlayerController("C");

        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());
        m.addPlayer(c3.getPlayer());
        //Match not started yet
        m.removePlayer(c1.getPlayer());

        assertEquals(m.gameState, GameState.WaitingPlayers);
        assertTrue(m.playerList.get(0).equals(c2.getPlayer()));
        assertEquals(2, m.playerList.size());
        assertFalse(GameManager.playerMatchMap.containsKey(c1.getPlayer().getNickname()));

    }

    /**
     * Testing the removal of a player from the match, when it has already begun.
     * Results: Match should be Closed
     */
    @Test
    void testRemovePlayerDuringMatch() {
        m = new Match(3);
        PlayerController c1 = new PlayerController("A");
        PlayerController c2 = new PlayerController("B");
        PlayerController c3 = new PlayerController("C");

        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());
        m.addPlayer(c3.getPlayer());
        //Match not started yet
        m.removePlayer(c1.getPlayer());
        assertEquals(GameState.Closed, m.gameState);
    }

    /**
     * Test what happens when the admin is removed.
     * The admin should change if there are other players.
     * If the admin was the only one in the room, then the match get Closed.
     * Setup: 2 player in a room with maximum 3 seats
     * Test: remove admin, and then again.
     */
    @Test
    void testRemoveAdmin() {
        PlayerController c1 = new PlayerController("A");
        PlayerController c2 = new PlayerController("B");
        GameManager.createMatch(3, c1);
        m = c1.getPlayer().getMatch();

        m.addPlayer(c2.getPlayer());

        assertEquals(m.admin, c1.getPlayer());

        m.removePlayer(c1.getPlayer());

        assertEquals(m.admin, c2.getPlayer());

        m.removePlayer(c2.getPlayer());
        assertEquals(GameState.Closed, m.gameState);
        assertFalse(GameManager.playerMatchMap.containsKey(c1.getPlayer().getNickname()));
        assertFalse(GameManager.playerMatchMap.containsKey(c2.getPlayer().getNickname()));

    }


    /**
     * Test if the method works correctly
     * Setup: match initialized
     * - GameState==GameGoing
     * - GamePhase==Selection
     * - CurrentPlayer contained in playerList
     */
    @Test
    void testStartFirstRound() {
        m = new Match(2);
        PlayerController c1 = new PlayerController("A");
        PlayerController c2 = new PlayerController("B");
        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());
        //Match initialized, then it should call startFirstRound() automatically

        assertEquals(m.gameState, GameState.GameGoing);
        assertEquals(m.gamePhase, GamePhase.Selection);
        assertTrue(m.playerList.contains(m.currentPlayer));

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
        PlayerController c1 = new PlayerController("A");
        PlayerController c2 = new PlayerController("B");
        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());
        //Match started
        assertEquals(m.gameState, GameState.GameGoing);
        m.removePlayer(c1.getPlayer());
        assertEquals(m.gameState, GameState.Closed);
        assertEquals(0, m.playerList.size());
        assertFalse(GameManager.playerMatchMap.containsKey(c1.getPlayer().getNickname()));
        assertFalse(GameManager.playerMatchMap.containsKey(c2.getPlayer().getNickname()));

    }

    /**
     * Test if the current player after calling nextTurn() is the next in list
     */
    @Test
    void testNextTurn() {
        m = new Match(4);
        PlayerController c1 = new PlayerController("A");
        PlayerController c2 = new PlayerController("B");
        PlayerController c3 = new PlayerController("C");
        PlayerController c4 = new PlayerController("D");
        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());
        m.addPlayer(c3.getPlayer());
        m.addPlayer(c4.getPlayer());
        //Start

        Player prev = m.currentPlayer;
        m.nextTurn();
        assertEquals(m.currentPlayer, m.playerList.get((m.playerList.indexOf(prev) + 1) % m.maxSeats));
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
        PlayerController c1 = new PlayerController("A");
        PlayerController c2 = new PlayerController("B");
        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());
        m.currentPlayer = c1.getPlayer();   //set c1.getPlayer() as current player
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
        assertEquals(m.gameState, GameState.LastRound);
        assertFalse(m.isEndGameToken());
        assertEquals(m.getFirstToComplete(), c1.getPlayer());
        c2.selectCell(5, 1);
        c2.callEndSelection();
        c2.callEndInsertion();
        assertEquals(m.gameState, GameState.Closed);
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
        PlayerController c1 = new PlayerController("A");
        PlayerController c2 = new PlayerController("B");
        PlayerController c3 = new PlayerController("C");
        PlayerController c4 = new PlayerController("D");
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
     * I expect a winner if the remaining players has some points: in this case, the player who got 5 points
     */
    @Test
    void testDeclareWinner2() {
        m = new Match(4);
        PlayerController c1 = new PlayerController("A");
        PlayerController c2 = new PlayerController("B");
        PlayerController c3 = new PlayerController("C");
        PlayerController c4 = new PlayerController("D");
        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());
        m.addPlayer(c3.getPlayer());
        m.addPlayer(c4.getPlayer());
        assertEquals(0, c1.getPlayer().getPlayerScore());
        m.decideWinner();
        assertNull(m.winner);
        c4.addScore(5); //player 4 got 5 points
        m.removePlayer(c2.getPlayer());
        assertEquals(GameState.Closed, m.gameState);
        assertEquals(c4.getPlayer(), m.winner);
    }

    /**
     * Test the resizing of the room from 3 to 2.
     * In the room there are originally 2 players, so the match is still in waitingPlayers phase.
     * After changing the size, the match should start.
     */
    @Test
    void testChangeSeats() throws InterruptedException {
        PlayerController c1 = new PlayerController("A");
        PlayerController c2 = new PlayerController("B");
        GameManager.createMatch(3, c1);

        m = c1.getPlayer().getMatch();
        assertEquals(m.maxSeats, 3);
        assertEquals(c1.getPlayer(), m.admin);
        m.addPlayer(c2.getPlayer());
        assertTrue(m.changeSeats(c1.getPlayer(), 2));
        assertEquals(m.maxSeats, 2);
        assertEquals(2, m.playerList.size());
        Thread.sleep(200);
        assertEquals(GameState.GameGoing, m.gameState);

    }

    /**
     * Test if a player who has not the role of admin can change the setting of the seats
     * Answer is no.
     */
    @Test
    void testNotAdmin() {
        PlayerController c1 = new PlayerController("A");
        PlayerController c2 = new PlayerController("B");
        GameManager.createMatch(3, c1);

        m = c1.getPlayer().getMatch();
        m.addPlayer(c2.getPlayer());
        assertFalse(m.changeSeats(c2.getPlayer(), 2));
    }

    /**
     * Setup: 2 player with some points
     * Test: When endMatch is called, there should be a winner(c2) and the players removed from player list and playerMatchMap.
     */
    @Test
    void testEndMatch() {
        PlayerController c1 = new PlayerController("A");
        PlayerController c2 = new PlayerController("B");
        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());
        c1.addScore(5);
        c2.addScore(10);
        m.endMatch();
        assertEquals(m.winner, c2.getPlayer());
        assertEquals(UserStatus.Online, c1.getPlayer().getStatus());
        assertEquals(UserStatus.Online, c2.getPlayer().getStatus());
        assertFalse(GameManager.playerMatchMap.containsKey(c1.getPlayer().getNickname()));
        assertFalse(GameManager.playerMatchMap.containsKey(c2.getPlayer().getNickname()));
        assertEquals(m.gameState, GameState.Closed);
        assertEquals(m.playerList.size(), 0);

    }

    /**
     * Setup: The Game is paused
     * Test nextTurn()
     * There should not be a current player
     */
    @Test
    void testGamePauseNextTurn() {
        PlayerController c1 = new PlayerController("A");
        PlayerController c2 = new PlayerController("B");
        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());

        assertEquals(m.gameState, GameState.GameGoing);
        m.pauseMatch();
        m.nextTurn();

        assertNull(m.currentPlayer);
    }

    /**
     * Setup: A player is suspended from the match due to connection loss
     * Test if his turn is skipped after a call of nextTurn() method
     */
    @Test
    void testSuspendedPlayerNextTurn() {
        PlayerController c1 = new PlayerController("A");
        PlayerController c2 = new PlayerController("B");
        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());

        assertEquals(m.gameState, GameState.GameGoing);
        if (!c1.getPlayer().equals(m.currentPlayer)) {
            c1.getPlayer().setStatus(UserStatus.Suspended);
            m.nextTurn();
            m.nextTurn();
            assertEquals(c2.getPlayer(), m.currentPlayer);
        } else {
            c2.getPlayer().setStatus(UserStatus.Suspended);
            m.nextTurn();
            m.nextTurn();
            assertEquals(c1.getPlayer(), m.currentPlayer);
        }

    }

    /**
     * Setup:
     * Player who has achieved a common Goal
     * Test1: the model of the goal and the player score
     */
    @Test
    void testCheckCommonGoals1() {
        PlayerController c1 = new PlayerController("A");
        PlayerController c2 = new PlayerController("B");
        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());

        // Match started
        // Forced setting: Common Goal
        Shelf currShelf = m.currentPlayer.getShelf();
        m.commonGoals.set(0, new CommonGoalXShape(2));
        m.commonGoals.set(1, new CommonGoalDiagonal(2));
        String[][] wish = {
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {ItemType.__Cats__ + "1.1", ItemType._Plants_ + "1.1", ItemType.__Cats__ + "1.1", "", ""},
                {ItemType._Plants_ + "1.1", ItemType.__Cats__ + "1.1", ItemType._Plants_ + "1.1", "", ""},
                {ItemType.__Cats__ + "1.1", ItemType._Plants_ + "1.1", ItemType.__Cats__ + "1.1", "", ""}
        };

        fillTheShelfAsIWish(wish, currShelf);
        m.callEndTurnRoutine();
        // Token Stack top is 4
        assertEquals(4, m.commonGoals.get(0).tokenStack.get(0));

        //Achieved players contains one player
        assertEquals(1, m.commonGoals.get(0).achievedPlayers.size());

        if (m.currentPlayer.equals(c1.getPlayer())) {
            assertEquals(8, c2.getPlayer().getPlayerScore());
        } else {
            assertEquals(8, c1.getPlayer().getPlayerScore());
        }


    }

    /**
     * Setup:
     * Player who has already achieved a common Goal
     * Test2: Achieve for the second time the same goal -> not possible
     */
    @Test
    void testCheckCommonGoals2() {
        PlayerController c1 = new PlayerController("A");
        PlayerController c2 = new PlayerController("B");
        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());

        // Match started
        // Forced setting: Common Goal
        m.currentPlayer=c1.getPlayer();
        Shelf currShelf = m.currentPlayer.getShelf();
        m.commonGoals.set(0, new CommonGoalXShape(2));
        String[][] wish = {
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {ItemType.__Cats__ + "1.1", ItemType._Plants_ + "1.1", ItemType.__Cats__ + "1.1", "", ""},
                {ItemType._Plants_ + "1.1", ItemType.__Cats__ + "1.1", ItemType._Plants_ + "1.1", "", ""},
                {ItemType.__Cats__ + "1.1", ItemType._Plants_ + "1.1", ItemType.__Cats__ + "1.1", "", ""}
        };

        fillTheShelfAsIWish(wish, currShelf);
        m.callEndTurnRoutine();
        m.callEndTurnRoutine();

        String[][] wish2 = {
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {ItemType._Plants_ + "1.1", "", ItemType._Plants_ + "1.1", "", ""},
                {ItemType.__Cats__ + "1.1", ItemType._Plants_ + "1.1", ItemType.__Cats__ + "1.1", "", ""},
                {ItemType._Plants_ + "1.1", ItemType.__Cats__ + "1.1", ItemType._Plants_ + "1.1", "", ""},
                {ItemType.__Cats__ + "1.1", ItemType._Plants_ + "1.1", ItemType.__Cats__ + "1.1", "", ""}
        };
        currShelf = m.currentPlayer.getShelf();
        fillTheShelfAsIWish(wish2, currShelf);
        m.callEndTurnRoutine();
        // Token Stack top is 4
        assertEquals(4, m.commonGoals.get(0).tokenStack.get(0));

        //Achieved players contains one player
        assertEquals(1, m.commonGoals.get(0).achievedPlayers.size());

        assertEquals(8, c1.getPlayer().getPlayerScore());


    }

    /**
     * Setup:
     * Player shelf with some points for Personal Goal, Common Goal, Shelf Points
     * Test: if result is correct
     */
    @Test
    void testCheckGamePoints() {
        PlayerController c1 = new PlayerController("A");
        PlayerController c2 = new PlayerController("B");
        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());

        // Match started
        // Forced setting: Common Goal
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
        String[][] points = m.checkGamePoints();
        assertEquals("8",points[m.playerList.indexOf(m.currentPlayer)][1]);
        assertEquals("1",points[m.playerList.indexOf(m.currentPlayer)][2]);
        assertEquals("2",points[m.playerList.indexOf(m.currentPlayer)][3]);
        assertNull(points[m.playerList.indexOf(m.currentPlayer)][4]);
        assertEquals("11",points[m.playerList.indexOf(m.currentPlayer)][5]);


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


}
