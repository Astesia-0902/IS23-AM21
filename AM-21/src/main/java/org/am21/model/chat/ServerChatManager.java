package org.am21.model.chat;

import org.am21.model.GameManager;
import org.am21.model.Player;
import org.am21.model.enumer.UserStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class is used to manage the chat messages
 * Every match has a chat manager
 */
public class ServerChatManager {
    private static final List<List<String>> privateChats = new ArrayList<>();
    private static final HashMap<String, Integer> chatMap = new HashMap<>();

    public static List<List<String>> getPrivateChats() {
        return privateChats;
    }

    public static HashMap<String, Integer> getChatMap() {
        return chatMap;
    }

    /**
     * Manage the private message
     *
     * @param sender   sender's Player instance
     * @param receiver receiver name
     * @param message  message
     * @return false if the operation fails, otherwise true
     */
    public static boolean handlePrivateChatMessage(Player sender, String receiver, String message) {
        //Check sender and receiver
        Player p_receiver = isOnline(receiver);
        //Check if private chat exists
        String key = getChatKey(sender.getNickname(), receiver);
        String messageLine = sender.getNickname() + " > " + message;

        if (p_receiver == null || (!chatMap.containsKey(key) && !createNewPrivateChat(sender, p_receiver))) {
            return false;
        }
        int chatNum;
        synchronized (chatMap) {
            chatNum = chatMap.get(key);
        }
        synchronized (privateChats) {
            privateChats.get(chatNum).add(messageLine);
        }
        return true;
    }

    /**
     * Find the player receiver by name
     *
     * @param receiverName receiver name (String)
     * @return receiver's Player instance if exist and is online, otherwise null
     */
    public static Player isOnline(String receiverName) {
        synchronized (GameManager.players) {
            for (Player p : GameManager.players) {
                if (p.getNickname().equals(receiverName) && !p.getStatus().equals(UserStatus.Offline))
                    return p;
            }
        }
        return null;
    }

    /**
     * Find the Key to use in ChatMap by two player name
     *
     * @param name1 player 1 name
     * @param name2 player 2 name
     * @return (String) key
     */
    public static String getChatKey(String name1, String name2) {
        return (name1.compareTo(name2)) < 0 ? (name1 + "@" + name2) : (name2 + "@" + name1);

    }

    /**
     * Create a new private for 2 players
     *
     * @param p1 player 1
     * @param p2 player 2
     * @return false if the nicknames are the same or if the chat already exists, otherwise true
     */
    public static boolean createNewPrivateChat(Player p1, Player p2) {
        //If the player are not in the same match or have the same name
        if (p1.getNickname().equals(p2.getNickname())) {
            return false;
        }
        String name1 = p1.getNickname();
        String name2 = p2.getNickname();
        String key = (name1.compareTo(name2)) < 0 ? (name1 + "@" + name2) : (name2 + "@" + name1);
        synchronized (chatMap) {
            if (chatMap.containsKey(key)) {
                return false;
            }
        }
        synchronized (privateChats) {
            privateChats.add(new ArrayList<>());
        }
        synchronized (chatMap) {
            chatMap.put(key, privateChats.size() - 1);
        }
        return true;
    }
}
