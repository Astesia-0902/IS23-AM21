package org.am21.model.virtualview;

import java.io.Serializable;

/**
 * This class is used to manage the virtual view of the server
 * It contains the virtual match list and the virtual online players
 */
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

    /**
     * This method is used to get the instance of the virtual private chats
     */
    public String[][] getVirtualPrivateChats() {
        return virtualPrivateChats;
    }

    /**
     * This method is used to set the instance of the virtual private chats
     */
    public void setVirtualPrivateChats(String[][] privateChats) {
        virtualPrivateChats = privateChats;
    }

    /**
     * This method is used to set the instance of the virtual chat map
     */
    public void setVirtualChatMap(String[][] chatMap) {
        virtualChatMap = chatMap;
    }

    /**
     * This method is used to get the instance of the virtual chat map
     */
    public String[][] getVirtualMatchList() {
        return virtualMatchList;
    }

    /**
     * This method is used to set the instance of the virtual match list
     */
    public void setVirtualMatchList(String[][] matchListVV) {
        virtualMatchList = matchListVV;
    }

    /**
     * This method is used to get the instance of  the virtual online players
     */
    public String[][] getVirtualOnlinePlayers() {
        return virtualOnlinePlayers;
    }

    /**
     * This method is used to set the instance of the virtual online players
     */
    public void setVirtualOnlinePlayers(String[][] onlinePlayersVV) {
        virtualOnlinePlayers = onlinePlayersVV;
    }
}
