package org.am21.player;

import org.am21.controller.GameController;
import org.am21.controller.PlayerController;
import org.am21.model.GameManager;
import org.am21.model.Match;
import org.am21.model.chat.ServerChatManager;
import org.am21.model.enumer.UserStatus;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChatTest {
    PlayerController c1;
    PlayerController c2;
    Match m;

    /**
     * Setup:2 Player
     */
    @Before
    public void setUpMatchChat() {
        GameManager.serverComm=false;
        c1 = new PlayerController("A");
        c2 = new PlayerController("B");

    }

    @AfterEach
    void tearDown() {
        c1=null;
        c2=null;
        m=null;
        GameManager.players.clear();
        GameManager.playerMatchMap.clear();
        GameManager.matchMap.clear();
    }

    /**
     * Test Match Chat:
     * Player send a message in the group chat
     * Verifies the message is saved in chatManager.publicChatMessage
     */
    @Test
    void testPublicChat() {
        setUpMatchChat();
        GameController.createMatch("A", 0, 2, c1);
        GameController.joinGame(0, "B", c2);
        m= c1.getPlayer().getMatch();
        String message = "Test";
        // Player send message in the group chat
        assertTrue(GameController.forwardPublicMessage(message,c1));
        assertEquals(c1.getPlayer().getNickname()+": "+message,m.chatManager.getPublicChatMessages().get(0));

    }

    /**
     * If a player is not in a match should not be able to send a public message
     * ForwardPublicMessage return false
     */
    @Test
    void testFailPublicChat(){
        setUpMatchChat();
        GameController.createMatch("A", 0, 2, c1);
        m= c1.getPlayer().getMatch();
        String message = "Test";
        assertFalse(GameController.forwardPublicMessage(message,c2));


    }

    /**
     * 2 Players in game, not in a match
     */
    @Before
    public void setUpDefault() {
        GameManager.serverComm=false;

        c1 = new PlayerController("A");
        c2 = new PlayerController("B");
        GameController.login("A",c1);
        GameController.login("B",c2);


    }
    /**
     * Test Private chat:
     * 2 player chatting
     * Expect ServerChatMessage.getPrivateChats() to contains
     */
    @Test
    void testPrivateChat(){
        setUpDefault();
        String message = "Hello B";
        // A sends message to B
        assertTrue(GameController.forwardPrivateMessage(message,"B",c1));
        String key = (ServerChatManager.getChatKey("A","B"));
        int chatIndex = ServerChatManager.getChatMap().get(key);
        assertEquals("A > Hello B", ServerChatManager.getPrivateChats().get(chatIndex).get(0));
    }

    /**
     * A player tries to send a text a player which does not exist
     * Expect forwardPrivateMessage to return false
     */
    @Test
    void testFailPrivateChat(){
        setUpDefault();
        String message = "Hello C";
        assertFalse(GameController.forwardPrivateMessage(message,"C",c1));

    }

    /**
     * A player tries to send a text a player which is Offline
     * Expect forwardPrivate Message to return false
     */
    @Test
    void testSuspendedPrivateChat(){
        setUpDefault();
        c2.getPlayer().setStatus(UserStatus.Offline);
        String message = "Hello B";
        assertFalse(GameController.forwardPrivateMessage(message,"B",c1));

    }

}
