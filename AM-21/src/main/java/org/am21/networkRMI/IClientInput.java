package org.am21.networkRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

/**
 * Interface for RMI communication, any class that will be used by the client must implement this interface
 * @version 1.0
 */
public interface IClientInput extends Remote {
    boolean joinGame(int matchID) throws RemoteException, ServerNotActiveException;


    boolean checkPlayerActionPhase() throws RemoteException,ServerNotActiveException;

    boolean logIn(String username,IClientCallBack clientCallBack) throws RemoteException, ServerNotActiveException;

    boolean createMatch(int playerNum) throws RemoteException, ServerNotActiveException;


    boolean selectCell(int row, int col) throws RemoteException,ServerNotActiveException;

    boolean confirmSelection() throws RemoteException, ServerNotActiveException;

    boolean insertInColumn(int colNum) throws RemoteException,ServerNotActiveException;

    boolean deselectCards() throws RemoteException,ServerNotActiveException;

    boolean sortHand(int pos1, int pos2) throws RemoteException,ServerNotActiveException;

    boolean leaveMatch() throws RemoteException;

    boolean exitGame() throws RemoteException;

    String getVirtualView() throws RemoteException;

    void registerCallBack(IClientCallBack callBack) throws RemoteException;

    boolean sendChatMessage(String message) throws RemoteException;

    boolean sendPlayerMessage(String message,String receiver) throws RemoteException;

    void printOnlinePlayers() throws RemoteException;

    void printMatchList() throws RemoteException;

    boolean endTurn() throws RemoteException, ServerNotActiveException;

    //TODO:
    void openChat()throws RemoteException;
}

