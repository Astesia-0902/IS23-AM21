package org.am21.controller;

public interface ICommunication {

    void sendMessageToClient(String message, PlayerController myPlayer);

    void sendVirtualView(String virtualView, int pIndex, PlayerController myPlayer);

    void notifyStart(int id, PlayerController myPlayer);

    void notifyToWait(String info, PlayerController myPlayer);

    void notifyGoToMenu(PlayerController myPlayer);

    void notifyEndMatch(PlayerController myPlayer) ;

    void sendVirtualHand(String JSONHand, PlayerController myPlayer);
    void returnBool(String method, boolean value, PlayerController pCtrl);

    void sendChatNotification(String message, PlayerController pc);

    void sendServerVirtualView(String serverVirtualView, PlayerController pc);

    void notifyUpdate(PlayerController ctrl, int milliseconds);

    void ping(PlayerController ctrl);
}
