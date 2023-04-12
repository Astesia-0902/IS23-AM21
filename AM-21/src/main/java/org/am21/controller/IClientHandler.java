package org.am21.controller;

import org.am21.model.PlayerManager;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

/**
 * Interface for RMI communication, any class that will be used by the client must implement this interface
 */
public interface IClientHandler extends Remote {
    public default void joinGame(int matchID) throws RemoteException, ServerNotActiveException {
    }

    public default void logIn(String username) throws RemoteException, ServerNotActiveException {
    }

    public default void createMatch(int playerNum) throws RemoteException, ServerNotActiveException {
    }


}

