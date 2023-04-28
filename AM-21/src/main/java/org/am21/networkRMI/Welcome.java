package org.am21.networkRMI;

import org.am21.controller.ServerPrototype;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Welcome extends UnicastRemoteObject implements Lobby {
    public Welcome() throws RemoteException {
    }
    @Override
    public String connect() throws RemoteException, MalformedURLException, AlreadyBoundException {
            ServerPrototype.done();
            return String.valueOf(ServerPrototype.number);


    }
}
