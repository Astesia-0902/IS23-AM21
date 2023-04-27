package org.am21.networkRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public interface IClientCallBack extends Remote {
    void sendMessageToClient(String message) throws RemoteException;
    void sendVirtualView(String virtualView) throws RemoteException;
    void sendChatMessage(String message) throws RemoteException;
    void notifyStart(int id) throws RemoteException, ServerNotActiveException;
    void notifyToWait(int id) throws RemoteException;

    void notifyGoToMenu() throws RemoteException;

    void sendVirtualHand(String JSONHand) throws RemoteException;
}
