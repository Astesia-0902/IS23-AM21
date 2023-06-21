package org.am21.game;

import org.am21.controller.GameController;
import org.am21.controller.PlayerController;
import org.am21.model.GameManager;
import org.am21.model.virtualview.ServerVirtualView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServerVirtualViewTest {
    PlayerController c1;
    PlayerController c2;
    PlayerController c3;

    @BeforeEach
    void setUp() {
        c1 = new PlayerController("A");
        c2 = new PlayerController("B");
        c3 = new PlayerController("C");
    }
    @AfterEach
    void tearDown(){
        GameManager.matchMap.clear();
        GameManager.playerMatchMap.clear();
        GameManager.players.clear();
    }

    /**
     * Test server virtual view's match map
     * The value should correspond
     */
    @Test
    void testViewMatchList() {
        assertTrue(GameController.createMatch("A",0,2,c1));
        assertTrue(GameController.joinGame(c1.getPlayer().getMatch().matchID,"B",c2));
        assertTrue(GameController.createMatch("C", 0, 2, c3));

        assertEquals(2, GameManager.matchMap.size());
        assertEquals(String.valueOf(GameManager.matchMap.get(0).matchID), ServerVirtualView.instance.getVirtualMatchList()[0][0]);
        assertEquals(String.valueOf(GameManager.matchMap.get(0).gameState),ServerVirtualView.instance.getVirtualMatchList()[0][1]);
        assertEquals(String.valueOf(GameManager.matchMap.get(0).playerList.size()),ServerVirtualView.instance.getVirtualMatchList()[0][2]);
        assertEquals(String.valueOf(GameManager.matchMap.get(0).maxSeats),ServerVirtualView.instance.getVirtualMatchList()[0][3]);

        assertEquals(String.valueOf(GameManager.matchMap.get(1).matchID), ServerVirtualView.instance.getVirtualMatchList()[1][0]);
        assertEquals(String.valueOf(GameManager.matchMap.get(1).gameState),ServerVirtualView.instance.getVirtualMatchList()[1][1]);
        assertEquals(String.valueOf(GameManager.matchMap.get(1).playerList.size()),ServerVirtualView.instance.getVirtualMatchList()[1][2]);
        assertEquals(String.valueOf(GameManager.matchMap.get(1).maxSeats),ServerVirtualView.instance.getVirtualMatchList()[1][3]);

    }

    @Test
    void testViewPlayersList(){
        assertTrue(GameController.login("A",c1));
        assertTrue(GameController.login("B",c2));
        assertTrue(GameController.login("C",c3));

        assertEquals(3,GameManager.players.size());
        assertEquals(GameManager.players.get(0).getNickname(),ServerVirtualView.instance.getVirtualOnlinePlayers()[0][0]);
        assertEquals(GameManager.players.get(0).getStatus().toString(),ServerVirtualView.instance.getVirtualOnlinePlayers()[0][1]);
        assertEquals(GameManager.players.get(1).getNickname(),ServerVirtualView.instance.getVirtualOnlinePlayers()[1][0]);
        assertEquals(GameManager.players.get(1).getStatus().toString(),ServerVirtualView.instance.getVirtualOnlinePlayers()[1][1]);
        assertEquals(GameManager.players.get(2).getNickname(),ServerVirtualView.instance.getVirtualOnlinePlayers()[2][0]);
        assertEquals(GameManager.players.get(2).getStatus().toString(),ServerVirtualView.instance.getVirtualOnlinePlayers()[2][1]);

    }


}
