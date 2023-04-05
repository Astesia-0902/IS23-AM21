package org.am21.controller;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Server class
 *
 * Requirements:
 * - Uniqueness of the nickname is granted by the server in phase of acceptance of the player
 */
public class Server {
    public static void main(String[] args) throws RemoteException {
        try {
            // Start the server
            LocateRegistry.createRegistry(8888);

            // This object will be used by the client
            IClientHandler clientInputHandler = new ClientInputHandler();
            //IClientHandler clientChatHandler = new ClientChatHandler();

            // Bind the remote object's stub in the registry
            Naming.bind("rmi://localhost:8888/ClientInputHandler", clientInputHandler);
            //Naming.bind("rmi://localhost:8888/ClientChatHandler", clientChatHandler);
            System.out.println("Server is ready");
        } catch (Exception ignored) {

        }
    }
}
