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
public class ServerMain {
    /**
     *
     * @param args
     * @throws RemoteException
     */
    public static void main(String[] args) throws RemoteException {
        try {
            // Start the server
            LocateRegistry.createRegistry(8808);

            // This object will be used by the client
            ClientInput clientInputHandler = new ClientInputHandler();
            //IClientHandler clientChatHandler = new ClientChatHandler();

            // Bind the remote object's stub in the registry
            Naming.bind("rmi://localhost:8808/ClientInputHandler", clientInputHandler);
            //Naming.bind("rmi://localhost:8888/ClientChatHandler", clientChatHandler);
            System.out.println("Server is ready");
            while (true){
                Thread.sleep(1000);
            }


        } catch (Exception ignored) {

        }
    }
}
