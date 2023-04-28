package org.am21.controller;

import org.am21.networkRMI.ClientInputHandler;
import org.am21.networkRMI.IClientInput;

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
            LocateRegistry.createRegistry(8807);
            // This object will be used by the client
            IClientInput IClientInputHandler = new ClientInputHandler();
            //IClientHandler clientChatHandler = new ClientChatHandler();

            // Bind the remote object's stub in the registry
            Naming.bind("rmi://localhost:8807/ClientInputHandler", IClientInputHandler);
            //Naming.bind("rmi://localhost:8888/ClientChatHandler", clientChatHandler);
            System.out.println("Server is ready");
            while (true){
                Thread.sleep(1000);
            }


        } catch (Exception ignored) {

        }
    }


}

