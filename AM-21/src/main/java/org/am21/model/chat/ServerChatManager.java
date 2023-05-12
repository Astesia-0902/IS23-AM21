package org.am21.model.chat;

import org.am21.model.GameManager;
import org.am21.model.Player;
import org.am21.model.enumer.UserStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServerChatManager {

    private static List<List<String>> privateChats = new ArrayList<>();
    private static HashMap<String, Integer> chatMap = new HashMap<>();

    public static List<List<String>> getPrivateChats() {
        return privateChats;
    }

    public static void setPrivateChats(List<List<String>> updateChats) {
        privateChats = updateChats;
    }

    public static HashMap<String, Integer> getChatMap() {
        return chatMap;
    }

    public void setChatMap(HashMap<String, Integer> map) {
        chatMap = map;
    }

    public static boolean handlePrivateChatMessage(Player sender, String receiver, String message) {
        //Check sender and receiver
        Player p_receiver = isOnline(receiver);
        //Check if private chat exists
        String key = getChatKey(sender.getNickname(),receiver);
        String messageLine = sender.getNickname() + " > " + message;

        if(!chatMap.containsKey(key) && !createNewPrivateChat(sender,p_receiver)){
            return  false;
        }
        int chatNum = chatMap.get(key);
        privateChats.get(chatNum).add(messageLine);

        return true;
    }

    public static Player isOnline(String receiverName){
        synchronized (GameManager.players){
            for(Player p:GameManager.players){
                if(p.getNickname().equals(receiverName) && !p.getStatus().equals(UserStatus.Offline))
                    return p;
            }
        }
        return null;
    }


    public static String getChatKey(String name1,String name2){
        return  (name1.compareTo(name2)) < 0 ? (name1 + "@" + name2) : (name2 + "@" + name1);

    }

    public static boolean createNewPrivateChat(Player p1, Player p2) {
        //If the player are not in the same match or have the same name
        if (p1.getNickname().equals(p2.getNickname())) {
            return false;
        }
        String name1 = p1.getNickname();
        String name2 = p2.getNickname();
        String key = (name1.compareTo(name2)) < 0 ? (name1 + "@" + name2) : (name2 + "@" + name1);
        if (chatMap.containsKey(key)) {
            return false;
        }
        privateChats.add(new ArrayList<>());
        chatMap.put(key, privateChats.size() - 1);

        return true;
    }



}
