package org.am21.game;

import org.am21.controller.GameController;
import org.am21.controller.PlayerController;
import org.am21.model.GameManager;
import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.model.enumer.GameState;
import org.am21.networkRMI.ClientCallBack;
import org.am21.networkRMI.ClientInputHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameTest {
    PlayerController client1;
    PlayerController client2;

    GameController game_ctrl;
    GameManager game;

    @BeforeEach
    void setUp() {
        try {
            ClientInputHandler client1_handler = new ClientInputHandler();
            ClientInputHandler client2_handler = new ClientInputHandler();
            client1_handler.callBack = new ClientCallBack();
            client2_handler.callBack = new ClientCallBack();
            client1 = new PlayerController("A");
            client2 = new PlayerController("B");

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }

    @AfterEach
    void tearDown() {
        GameManager.matchList.clear();
        GameManager.playerMatchMap.clear();

    }

    /**
     * Test the creation of a game with GameController
     */
    @Test
    void testCreateAndJoinMatch() {
        GameController.createMatch(client1.getPlayer().getNickname(), 0, 2, client1);
        GameController.joinGame(0, client2.getPlayer().getNickname(), client2);
        assertNotNull(GameManager.matchList.get(0));
        assertNotNull(GameManager.playerMatchMap.get("A"));
        Match m = GameManager.matchList.get(0);
        assertTrue(m.gameState == GameState.GameGoing);

    }

    @Test
    void testGameSimulation2Players() {
        GameController.createMatch(client1.getPlayer().getNickname(), 0, 2, client1);
        GameController.joinGame(0, client2.getPlayer().getNickname(), client2);
        Match m = GameManager.matchList.get(0);
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


    //TODO: CIH(RMI) is not needed for testing
    //TODO: Check Match.checkCommonGoals()
    //TODO: Check Match.checkPersonalGOal()
    //TODO: Check Match.checkShelfPoints()
    //TODO: Full simulation of a game with GameController, Match , GameManager
    //TODO: Verify the declaration of the winner
}
