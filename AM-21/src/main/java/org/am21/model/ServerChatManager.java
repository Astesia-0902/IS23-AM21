package org.am21.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServerChatManager {

    private List<List<String>> privateChats = new ArrayList<>();
    private HashMap<String, Integer> chatMap = new HashMap<>();

    public List<List<String>> getPrivateChats() {
        return privateChats;
    }

    public void setPrivateChats(List<List<String>> privateChats) {
        this.privateChats = privateChats;
    }

    public HashMap<String, Integer> getChatMap() {
        return chatMap;
    }

    public void setChatMap(HashMap<String, Integer> chatMap) {
        this.chatMap = chatMap;
    }



}
