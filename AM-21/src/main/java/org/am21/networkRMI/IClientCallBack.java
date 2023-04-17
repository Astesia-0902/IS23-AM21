package org.am21.networkRMI;

import java.rmi.Remote;

public interface IClientCallBack extends Remote {
    void messageFromServer(String message) throws java.rmi.RemoteException;
    void sendVirtualView(String virtualView) throws java.rmi.RemoteException;
    void sendChatMessage(String message) throws java.rmi.RemoteException;
}
