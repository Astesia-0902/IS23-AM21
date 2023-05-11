package org.am21.model;

public class ServerVirtualView {
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
    public static String[][] virtualMatchList;

    /**
     * List:
     * - Player Data
     * Players Data:
     * - Nickname
     * - UserStatus
     */
    public static String[][] virtualOnlinePlayers;

    public static String[][] virtualPrivateChats;

    public static String[][] virtualChatMap;

    public static String[][] getVirtualPrivateChats() {
        return virtualPrivateChats;
    }

    public static void setVirtualPrivateChats(String[][] virtualPrivateChats) {
        ServerVirtualView.virtualPrivateChats = virtualPrivateChats;
    }

    public static String[][] getVirtualChatMap() {
        return virtualChatMap;
    }

    public static void setVirtualChatMap(String[][] virtualChatMap) {
        ServerVirtualView.virtualChatMap = virtualChatMap;
    }

    public static String[][] getVirtualMatchList() {
        return virtualMatchList;
    }

    public static void setVirtualMatchList(String[][] matchListVV) {
        virtualMatchList = matchListVV;
    }

    public static String[][] getVirtualOnlinePlayers() {
        return virtualOnlinePlayers;
    }

    public static void setVirtualOnlinePlayers(String[][] onlinePlayersVV) {
        virtualOnlinePlayers = onlinePlayersVV;
    }
}
