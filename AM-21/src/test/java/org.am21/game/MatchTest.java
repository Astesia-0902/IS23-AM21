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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @version 1.0
 */
class MatchTest {
    Match m;
    @BeforeEach
    void setUp(){
        m=new Match(2);


    }

    @AfterEach
    void tearDown(){
        m=null;

    }

    /**
     * Test if the method works correctly
     * Setup : the match has already reached his player number limit(2)
     * Test case: What happens when this method is called one more time?
     * Result expected: There should be 2 players in the match
     */
    @Test
    void testAddPlayer(){
        PlayerController c1 = new PlayerController("A");
        PlayerController c2 = new PlayerController("B");
        PlayerController c3 = new PlayerController("C");
        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());
        m.addPlayer(c3.getPlayer());

        assertEquals(2,m.playerList.size());
    }

    /**
     *Test if the method works correctly
     * When called verify if all the attributes are correct.
     */
    @Test
    void testInitializeMatch(){
        PlayerController c1 = new PlayerController("A");
        PlayerController c2 = new PlayerController("B");
        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());
        //startMatch() is called automatically
        //startFirstRound() is called after that
        //Check gamestate
        assertTrue(m.gameState.equals(GameState.GameGoing));
        //Check chairman
        assertTrue(m.playerList.contains(m.chairman));
        //Check if all players personal goal are different
        for(Player x: m.playerList){
            //Check also if each player is mapped in playerMatchMap
            assertTrue(GameManager.playerMatchMap.containsKey(x.getNickname()));
            for(Player y:m.playerList){
                if(!y.equals(x))
                    assertTrue(!x.getMyPersonalGoal().equals(y.getMyPersonalGoal()));
            }
        }
        //Check if common goals are different
        assertFalse(m.commonGoals.get(0).equals(m.commonGoals.get(1)));

        //Check if board is completely refilled
        int k=0;
        if(m.maxSeats==2) k=1;
        for(int i=0+k;i<m.board.boundaries.size()-k;i++){
            for(int j=m.board.boundaries.get(i).x;j<m.board.boundaries.get(i).y;j++){
                assertTrue(m.board.isOccupied(i,j));
            }
        }
    }

    /**
     * Setup: MaxSeats: 4
     * We have 3 players waiting to begin. One gets removed.
     * Test if removePlayer() works.
     */
    @Test
    void testRemovePlayer(){
        m=new Match(4);
        PlayerController c1 = new PlayerController("A");
        PlayerController c2 = new PlayerController("B");
        PlayerController c3 = new PlayerController("C");

        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());
        m.addPlayer(c3.getPlayer());
        //Match not started yet

        m.removePlayer(c1.getPlayer());

        assertTrue(m.playerList.get(0).equals(c2.getPlayer()));
        assertEquals(2,m.playerList.size());

    }


    /**
     * Test if the method works correctly
     * Setup: match initialized
     * - GameState==GameGoing
     * - GamePhase==Selection
     * - CurrentPlayer contained in playerList
     */
    @Test
    void testStartFirstRound(){
        m=new Match(2);
        PlayerController c1 = new PlayerController("A");
        PlayerController c2 = new PlayerController("B");
        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());
        //Match initialized, then it should call startFirstRound() automatically

        assertTrue(m.gameState==GameState.GameGoing);
        assertTrue(m.gamePhase== GamePhase.Selection);
        assertTrue(m.playerList.contains(m.currentPlayer));
        assertNotNull(m.timer);

    }

    /**
     * What if a player leave the match while the match is starting?
     * The match should end, thanks to checkRoom() that calls endMatch()
     * Test:
     * During a game, if endMatch is called what will happen?
     * Expectation: GameState=Closed, playerList cleared, board cleared,
     *      playerMatchMap doesn't contain players of the match
     */
    @Test
    void testCheckRoomAndEndMatch(){
        m=new Match(2);
        PlayerController c1 = new PlayerController("A");
        PlayerController c2 = new PlayerController("B");
        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());
        //Match started
        assertTrue(m.gameState==GameState.GameGoing);
        m.removePlayer(c1.getPlayer());
        assertTrue(m.gameState==GameState.Closed);
        assertEquals(0,m.playerList.size());
        assertFalse(GameManager.playerMatchMap.containsKey(c1.getPlayer()));
        assertFalse(GameManager.playerMatchMap.containsKey(c2.getPlayer()));

    }

    /**
     * Test if the current player after calling nextTurn() is the next in list
     */
    @Test
    void testNextTurn(){
        m=new Match(4);
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
        assertTrue(m.currentPlayer.equals(m.playerList.get((m.playerList.indexOf(prev) + 1) % m.maxSeats)));


    }

    /**
     * Setup:
     * Player with a full shelf.
     * If callEndRouting is called by playerController.callEndInsertion:
     * - GamePhase changes to LastRound
     * - EndGameToke should be false
     * - firstToComplete should be set
     * After the first player completed the shelf, the second player can play one last turn
     * and the endMatch will be called.
     */
    @Test
    void testCallEndTurnRoutine(){
        m=new Match(2);
        PlayerController c1 = new PlayerController("A");
        PlayerController c2 = new PlayerController("B");
        m.addPlayer(c1.getPlayer());
        m.addPlayer(c2.getPlayer());
        m.currentPlayer=c1.getPlayer();
        Shelf s=c1.getPlayer().getShelf();
        c1.callEndSelection();
        //Simulate full insertion of the shelf
        for(int i = 0; i< Shelf.SHELF_COLUMN; i++){
            s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),i);
            s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),i);
            s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),i);
            s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),i);
            s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),i);
            s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),i);
        }
        assertEquals(0,s.getTotSlotAvail());
        c1.callEndInsertion();
        assertTrue(m.gameState==GameState.LastRound);
        assertFalse(m.isEndGameToken());
        assertEquals(m.getFirstToComplete(),c1.getPlayer());
        c2.callEndSelection();
        c2.callEndInsertion();
        assertTrue(m.gameState==GameState.Closed);
    }















}
