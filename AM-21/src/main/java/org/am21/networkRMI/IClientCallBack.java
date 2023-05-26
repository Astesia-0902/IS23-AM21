package org.am21.networkRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public interface IClientCallBack extends Remote {
    void sendMessageToClient(String message) throws RemoteException;
    void sendChatNotification(String message) throws RemoteException;
    void sendVirtualView(String virtualView, int pIndex) throws RemoteException;
    void notifyStart(int id) throws RemoteException, ServerNotActiveException;
    void notifyToWait(String info) throws RemoteException;
    void notifyGoToMenu() throws RemoteException;
    void notifyEndMatch() throws RemoteException;
    void sendVirtualHand(String JSONHand) throws RemoteException;
    void sendVirtualPublicChat(String virtualPublicChat) throws RemoteException;
    void sendServerVirtualView(String serverVV) throws RemoteException;
    void notifyUpdate(int milliseconds) throws RemoteException;

    void ping() throws RemoteException;
}
