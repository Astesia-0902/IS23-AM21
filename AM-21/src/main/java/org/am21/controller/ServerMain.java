package org.am21.controller;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Main server class
 */
public class ServerMain {
    public static void main(String[] args) throws RemoteException {
        GameController gameController = new GameController();
        try {
            // Start the server
            LocateRegistry.createRegistry(8888);

            // This object will be used by the client
            IClientHandler clientInputHandler = gameController.clientInputHandler;
            IClientHandler clientChatHandler = gameController.clientChatHandler;

            // Bind the remote object's stub in the registry
            Naming.bind("rmi://localhost:8888/ClientInputHandler", clientInputHandler);
            Naming.bind("rmi://localhost:8888/ClientChatHandler", clientChatHandler);
            System.out.println("Server is ready");
        } catch (Exception ignored) {

        }
    }
}
