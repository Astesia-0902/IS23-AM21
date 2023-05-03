package org.am21.controller;

public interface ICommunication {

    void sendMessageToClient(String message, PlayerController myPlayer);

    void sendVirtualView(String virtualView, int pIndex, PlayerController myPlayer);

    void sendChatMessage(String message, PlayerController myPlayer);

    void notifyStart(int id, PlayerController myPlayer);

    void notifyToWait(String info, PlayerController myPlayer);

    void notifyGoToMenu(PlayerController myPlayer);

    void notifyEndMatch(PlayerController myPlayer) ;

    void sendVirtualHand(String JSONHand, PlayerController myPlayer);
}
