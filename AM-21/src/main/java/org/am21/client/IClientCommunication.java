package org.am21.client;

import org.am21.networkRMI.IClientCallBack;

public interface IClientCommunication {boolean joinGame(int matchID);

    boolean checkPlayerActionPhase();

    boolean logIn(String username, IClientCallBack clientCallBack);

    boolean createMatch(int playerNum);


    boolean selectCell(int row, int col);

    boolean confirmSelection() ;

    boolean insertInColumn(int colNum);

    boolean deselectCards();

    boolean sortHand(int pos1, int pos2);

    boolean leaveMatch();

    boolean exitGame();

    String getVirtualView();

    void registerCallBack(IClientCallBack callBack);

    boolean sendChatMessage(String message);

    boolean sendPlayerMessage(String message,String receiver);

    void printOnlinePlayers();

    void printMatchList();

    boolean endTurn();

    //TODO:
    void openChat();

    boolean changeMatchSeats(int newMaxSeats);

    boolean changeInsertLimit(int newLimit);
}
