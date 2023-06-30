package org.am21.networkRMI;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

/**
 * This interface is the lobby
 */
public interface Lobby extends Remote {
    /**
     * This method is used to connect to the server
     *
     * @return a HashMap containing the information of the server
     * @throws RemoteException       if the remote object is not available
     * @throws MalformedURLException if the URL is not valid
     * @throws AlreadyBoundException if the server is already bound
     */
    HashMap<String, String> connect() throws RemoteException, MalformedURLException, AlreadyBoundException;
}
