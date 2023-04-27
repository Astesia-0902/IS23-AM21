package org.am21.networkRMI;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Lobby extends Remote {


    String connect() throws RemoteException, MalformedURLException, AlreadyBoundException;
}
