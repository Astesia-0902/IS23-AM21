package org.am21.networkRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClientCallBack extends Remote {
    void sendMessageToClient(String message) throws RemoteException;
    void sendVirtualView(String virtualView) throws RemoteException;
    void sendChatMessage(String message) throws RemoteException;
    void notifyStart() throws RemoteException;
}
