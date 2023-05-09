package org.am21.networkRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public interface IClientCallBack extends Remote {
    void sendMessageToClient(String message,boolean refresh) throws RemoteException;
    void sendVirtualView(String virtualView, int pIndex) throws RemoteException;
    void sendChatMessage(String message) throws RemoteException;
    void notifyStart(int id) throws RemoteException, ServerNotActiveException;
    void notifyToWait(String info) throws RemoteException;
    void notifyGoToMenu() throws RemoteException;
    void notifyEndMatch() throws RemoteException;
    void sendVirtualHand(String JSONHand) throws RemoteException;

    void sendVirtualPublicChat(String virtualPublicChat) throws RemoteException;

    void sendVirtualPrivateChats(String virtualPrivateChats) throws RemoteException;
    //TODO: testClientConnection
}
