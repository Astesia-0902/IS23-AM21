package org.am21.model.virtualview;

import java.io.Serializable;

public class ServerVirtualView implements Serializable {
    public static final ServerVirtualView instance = new ServerVirtualView();

    /**
     * List:
     * - Match Data
     * Match data:
     * - ID
     * - GameState
     * - number of players in the match
     * - maxSeats
     */
    private String[][] virtualMatchList;

    /**
     * List:
     * - Player Data
     * Players Data:
     * - Nickname
     * - UserStatus
     */
    private String[][] virtualOnlinePlayers;

    private String[][] virtualPrivateChats;

    private String[][] virtualChatMap;

    public String[][] getVirtualPrivateChats() {
        return virtualPrivateChats;
    }

    public void setVirtualPrivateChats(String[][] privateChats) {
        virtualPrivateChats = privateChats;
    }

    public String[][] getVirtualChatMap() {
        return virtualChatMap;
    }

    public void setVirtualChatMap(String[][] chatMap) {
        virtualChatMap = chatMap;
    }

    public String[][] getVirtualMatchList() {
        return virtualMatchList;
    }

    public void setVirtualMatchList(String[][] matchListVV) {
        virtualMatchList = matchListVV;
    }

    public String[][] getVirtualOnlinePlayers() {
        return virtualOnlinePlayers;
    }

    public void setVirtualOnlinePlayers(String[][] onlinePlayersVV) {
        virtualOnlinePlayers = onlinePlayersVV;
    }
}
