package org.am21.controller;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public class ClientTestMain1 {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException, ServerNotActiveException {

        // Start the server
        IClientHandler clientInputHandler = (IClientHandler) Naming.lookup("rmi://localhost:8808/ClientInputHandler");
        //IClientHandler clientChatHandler = (IClientHandler) Naming.lookup("rmi://localhost:8808/ClientChatHandler");
        //Client do something
        System.out.println("Client is ready");
        System.out.println(clientInputHandler);
        //System.out.println(clientChatHandler);
        clientInputHandler.logIn("ade");
        clientInputHandler.joinGame(0);

    }
}
