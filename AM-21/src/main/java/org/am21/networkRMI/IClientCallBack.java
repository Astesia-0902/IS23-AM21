package org.am21.networkRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

/**
 * This interface is the client call back
 */
public interface IClientCallBack extends Remote {
    /**
     * This method send a message to client from server
     */
    void sendMessageToClient(String message) throws RemoteException;

    /**
     * This method call client method to send a chat message
     */
    void sendChatNotification(String message) throws RemoteException;

    /**
     * This method call client method to send a virtual view
     */
    void sendVirtualView(String virtualView, int pIndex) throws RemoteException;

    /**
     * This method notify the client that the match is started
     */
    void notifyStart(int id) throws RemoteException, ServerNotActiveException;

    /**
     * This method notify the client to wait
     */
    void notifyToWait(String info) throws RemoteException;

    /**
     * This method show the client the menu
     */
    void notifyGoToMenu() throws RemoteException;

    /**
     * This method notify the client that the match is ended
     */
    void notifyEndMatch() throws RemoteException;

    /**
     * This method send the client the hand of the player
     */
    void sendVirtualHand(String JSONHand) throws RemoteException;

    /**
     * This method send the client the virtual public chat
     */
    void sendVirtualPublicChat(String virtualPublicChat) throws RemoteException;

    /**
     * This method send the client the virtual view of the server
     */
    void sendServerVirtualView(String serverVV) throws RemoteException;

    /**
     * This method notify the update of the virtual view
     */
    void notifyUpdate(int milliseconds) throws RemoteException;

    /**
     * This method is used to ping the client
     */
    void ping() throws RemoteException;
}
