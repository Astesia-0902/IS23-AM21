package org.am21.controller;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

/**
 * Interface for RMI communication, any class that will be used by the client must implement this interface
 * @author JiaLiang Ding
 * @version 1.0
 */
public interface IClientInput extends Remote {
    boolean joinGame(int matchID) throws RemoteException, ServerNotActiveException;


    boolean checkPlayerActionPhase() throws RemoteException,ServerNotActiveException;

    boolean logIn(String username) throws RemoteException, ServerNotActiveException;


    void createMatch(int playerNum) throws RemoteException, ServerNotActiveException;


    boolean selectCell(int row, int col) throws RemoteException,ServerNotActiveException;

    boolean insertInColumn(int colNum) throws RemoteException,ServerNotActiveException;

    boolean unselectCards() throws RemoteException,ServerNotActiveException;

    boolean sortHand(int pos1, int pos2) throws RemoteException,ServerNotActiveException;

    boolean exitMatch() throws RemoteException;

    String getVirtualView() throws RemoteException;
}

