package org.am21.controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientChatHandler extends UnicastRemoteObject implements IClientHandler {
    public GameController gameController;
    /**
     * Creates and exports a new UnicastRemoteObject object using an
     * anonymous port.
     *
     * @throws RemoteException if failed to export object
     * @since JDK1.1
     */
    protected ClientChatHandler() throws RemoteException {
    }

    public String sendChatMessage(String message) throws RemoteException {
        return null;
    }
}
