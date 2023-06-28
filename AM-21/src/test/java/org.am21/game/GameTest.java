package org.am21.game;

import org.am21.controller.GameController;
import org.am21.controller.PlayerController;
import org.am21.model.Cards.ItemCard;
import org.am21.model.Cards.ItemType;
import org.am21.model.GameManager;
import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.model.enumer.GameState;
import org.am21.model.items.Shelf;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    PlayerController client1;
    PlayerController client2;

    PlayerController c1;
    PlayerController c2;
    Match m;

    /**
     * Build test setup
     */
    @BeforeEach
    void setUp() {
        GameManager.serverComm=false;
        client1 = new PlayerController("A");
        client2 = new PlayerController("B");
        GameController.login("A",client1);
        GameController.login("B",client2);


    }

    /**
     * Tear down test setup
     */
    @AfterEach
    void tearDown() {
        GameManager.matchIndex=0;
        GameManager.matchMap.clear();
        GameManager.playerMatchMap.clear();
        GameManager.players.clear();
    }

    /**
     * Test the creation of a game with GameController
     */
    @Test
    void testCreateAndJoinMatch() {
        GameManager.serverComm=false;
        GameController.createMatch(client1.getPlayer().getNickname(), 0, 2, client1);
        GameController.joinGame(0, client2.getPlayer().getNickname(), client2);
        assertNotNull(GameManager.matchMap.get(0));
        assertNotNull(GameManager.playerMatchMap.get("A"));
        Match m = GameManager.matchMap.get(0);
        assertSame(m.gameState, GameState.GameGoing);

    }

    /**
     * Test a match  with 2 players with model methods
     */
    @Test
    void testGameSimulation2Players() {
        assertTrue(GameController.createMatch(client1.getPlayer().getNickname(), 0, 2, client1));
        Match m = client1.getPlayer().getMatch();
        assertTrue(GameController.joinGame(0, client2.getPlayer().getNickname(), client2));

        //Select x2
        assertTrue(m.currentPlayer.getController().selectCell(1, 4));
        assertTrue(m.currentPlayer.getController().selectCell(1, 3));
        //Deselect
        assertTrue(m.currentPlayer.getController().clearSelectedCards());
        //Select other cards
        assertTrue(m.currentPlayer.getController().selectCell(4, 1));
        assertTrue(m.currentPlayer.getController().selectCell(5, 1));
        //insert
        assertTrue(m.currentPlayer.getController().callEndSelection());
        assertTrue(m.currentPlayer.getController().tryToInsert(0));
        m.currentPlayer.getController().callEndInsertion();
        //next turn

        //Check insertion was successful
        Player prevPlayer = m.playerList.get((m.playerList.indexOf(m.currentPlayer) + 1) % m.maxSeats);
        assertTrue(prevPlayer.getShelf().isOccupied(5, 0));
        assertTrue(prevPlayer.getShelf().isOccupied(4, 0));

        assertTrue(m.currentPlayer.getController().selectCell(3, 2));
        assertTrue(m.currentPlayer.getController().selectCell(4, 2));
        assertTrue(m.currentPlayer.getController().selectCell(5, 2));
        m.currentPlayer.getController().callEndSelection();
        assertTrue(m.currentPlayer.getController().tryToInsert(0));


    }

    /**
     * Set up a real match with 2 players using GameController
     */
    @Before
    public void setupNormalGame(){
        GameManager.serverComm=false;
        c1 = new PlayerController("A");
        c2 = new PlayerController("B");
        GameController.login("A",c1);
        GameController.login("B",c2);
        GameController.createMatch("A",0,2,c1);
        m= c1.getPlayer().getMatch();
        GameController.joinGame(0,"B",c2);
    }

    /**
     * Test a game with GameController methods
     */
    @Test
    void testFullGame(){
        tearDown();
        setupNormalGame();
        Shelf s1 = c1.getPlayer().getShelf();
        Shelf s2 = c2.getPlayer().getShelf();
        m.currentPlayer = c1.getPlayer();
        assertEquals(c1.getPlayer(),m.currentPlayer);
        assertTrue(GameController.selectCell(1,3,c1));
        assertTrue(GameController.deselectCards(c1));
        assertEquals(0,c1.getHand().getSelectedItems().size());
        assertTrue(GameController.selectCell(1,3,c1));
        assertTrue(GameController.selectCell(1,4,c1));

        assertTrue(GameController.confirmSelection(c1));
        assertTrue(GameController.sortHand(0,1,c1));
        assertTrue(GameController.insertInColumn(0,c1));
        assertTrue(GameController.endTurn(c1));

        assertEquals(c2.getPlayer(),m.currentPlayer);
        assertTrue(GameController.selectCell(2,3,c2));
        assertTrue(GameController.selectCell(2,4,c2));
        assertTrue(GameController.selectCell(2,5,c2));

        assertTrue(GameController.confirmSelection(c2));
        assertTrue(GameController.insertInColumn(0,c2));
        assertTrue(GameController.endTurn(c2));

        assertEquals(c1.getPlayer(),m.currentPlayer);


        String[][] wish1 = {
                {"", ItemType._Games__+"1.1", ItemType.Trophies+"1.1", ItemType.Trophies+"1.1", ItemType._Frames_+"1.1"},
                {ItemType.Trophies+"1.1", ItemType.Trophies+"1.1", ItemType.Trophies+"1.1", ItemType._Frames_+"1.1", ItemType._Frames_+"1.1"},
                {ItemType.Trophies+"1.1", ItemType._Plants_ + "1.1", ItemType._Plants_ + "1.1", ItemType._Frames_+"1.1", ItemType._Frames_+"1.1"},
                {ItemType.__Cats__ + "1.1", ItemType._Plants_ + "1.1", ItemType.__Cats__ + "1.1", ItemType._Frames_+"1.1", ItemType._Games__+"1.1"},
                {ItemType._Plants_ + "1.1", ItemType.__Cats__ + "1.1", ItemType._Plants_ + "1.1", ItemType._Frames_+"1.1", ItemType._Games__+"1.1"},
                {ItemType.__Cats__ + "1.1", ItemType._Plants_ + "1.1", ItemType.__Cats__ + "1.1", ItemType._Games__+"1.1", ItemType._Games__+"1.1"}
        };

        String[][] wish2 = {
                {ItemType._Games__+"1.1", ItemType._Games__+"1.1", ItemType.Trophies+"1.1", ItemType.Trophies+"1.1", ""},
                {ItemType.Trophies+"1.1", ItemType.Trophies+"1.1", ItemType.Trophies+"1.1", ItemType._Frames_+"1.1", ItemType._Frames_+"1.1"},
                {ItemType.Trophies+"1.1", ItemType._Plants_ + "1.1", ItemType._Plants_ + "1.1", ItemType._Frames_+"1.1", ItemType._Frames_+"1.1"},
                {ItemType.__Cats__ + "1.1", ItemType._Books__ + "1.1", ItemType.__Cats__ + "1.1", ItemType._Frames_+"1.1", ItemType._Games__+"1.1"},
                {ItemType._Plants_ + "1.1", ItemType.__Cats__ + "1.1", ItemType._Plants_ + "1.1", ItemType._Frames_+"1.1", ItemType._Games__+"1.1"},
                {ItemType._Plants_ + "1.1", ItemType._Plants_ + "1.1", ItemType.__Cats__ + "1.1", ItemType._Games__+"1.1", ItemType._Games__+"1.1"}
        };

        fillTheShelfAsIWish(wish1,s1);
        updateSlot(s1);

        assertTrue(GameController.selectCell(3,2,c1));
        assertTrue(GameController.confirmSelection(c1));
        assertTrue(GameController.insertInColumn(0,c1));
        assertTrue(GameController.endTurn(c1));
        assertEquals(GameState.LastRound,m.gameState);

        fillTheShelfAsIWish(wish2,s2);
        updateSlot(s2);

        assertTrue(GameController.selectCell(3,3,c2));
        assertTrue(GameController.confirmSelection(c2));
        assertTrue(GameController.insertInColumn(4,c2));
        assertTrue(GameController.endTurn(c2));

        assertEquals(GameState.Closed,m.gameState);

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
     * Print shelf
     * @param shelf player shelf
     */
    private void printShelf(Shelf shelf){
        for(int i=0; i<Shelf.SHELF_ROW;i++){
            for(int j=0; j<Shelf.SHELF_COLUMN;j++){
                if(shelf.getMatrix()[i][j]!=null){
                    System.out.print(shelf.getMatrix()[i][j].getNameCard());
                }else{
                    System.out.print("[         ]");
                }


            }
            System.out.println();
        }
    }

    /**
     * Update Shelf column slot parameters
     * @param shelf player's shelf
     */
    private void updateSlot(Shelf shelf){

        for(int c=0; c<Shelf.SHELF_COLUMN;c++){
            scanColumn(c,shelf);
        }
    }

    /**
     * Scan a shelf column to individuate how much slot are still available
     * @param column column number (0-5)
     * @param shelf player's shelf
     */
    private void scanColumn(int column,Shelf shelf){
        for(int i=0; i<Shelf.SHELF_ROW;i++){
            if(shelf.getMatrix()[i][column]!=null){
                shelf.slotCol.set(column,i);
                break;
            }
        }
    }

}
