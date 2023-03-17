package org.am21.controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class InputManager extends UnicastRemoteObject implements IInputManager{
    public GameController gameController;
    /**
     * Creates and exports a new UnicastRemoteObject object using an
     * anonymous port.
     * @throws RemoteException if failed to export object
     * @since JDK1.1
     */
    protected InputManager() throws RemoteException {
    }

    @Override
    public void logIn(String username, String password) throws RemoteException {

    }

    @Override
    public String sendChatMessage(String message) throws RemoteException {
        return null;
    }

    /**
     * Draw cards from the deck.
     * This method will return the virtual view of game state to the client in the future.
     * @throws RemoteException
     */
    @Override
    public void drawCards() throws RemoteException {

    }

    /**
     * Fill the shelf with cards from the deck.
     * This method will return the virtual view of game state to the client in the future.
     * @throws RemoteException
     */
    @Override
    public void fillShelf() throws RemoteException {

    }
}
