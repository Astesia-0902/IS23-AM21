package org.am21.controller;

import java.rmi.Remote;

/**
 * Interface for RMI communication
 */
public interface IInputManager extends Remote {
    public void logIn(String username, String password) throws java.rmi.RemoteException;
    public String sendChatMessage(String message) throws java.rmi.RemoteException;
    public void drawCards() throws java.rmi.RemoteException;
    public void fillShelf() throws java.rmi.RemoteException;
}
