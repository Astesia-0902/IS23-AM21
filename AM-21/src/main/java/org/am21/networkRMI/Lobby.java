package org.am21.networkRMI;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface Lobby extends Remote {


    HashMap<String, String> connect() throws RemoteException, MalformedURLException, AlreadyBoundException;
}
