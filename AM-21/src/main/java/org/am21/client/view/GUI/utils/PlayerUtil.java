package org.am21.client.view.GUI.utils;

import javax.swing.*;
import java.util.HashMap;

public class PlayerUtil {
    private static final HashMap<String, JLabel> chatList = new HashMap<>();


    static {
        // Just for test...
        chatList.put("#All", new JLabel("#All"));
//
//        onlineList.put("player1", new JLabel("player1"));
//        onlineList.put("player2", new JLabel("player2"));
//        onlineList.put("player3", new JLabel("player3"));

    }

    public static void addChatList(String username) {
        chatList.put(username,new JLabel(username));
    }
    public static HashMap<String, JLabel> getChatList() {
        return chatList;
    }
}
