package org.am21.controller;

import org.am21.networkRMI.ClientInputHandler;
import org.am21.networkRMI.IClientInput;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.List;

/**
 * Server class
 *
 * Requirements:
 * - Uniqueness of the nickname is granted by the server in phase of acceptance of the player
 */
public class ServerMain {
    public static IClientInput new_IH=null;
    public static List<IClientInput> inputList = new ArrayList<>();

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
            new_IH = new ClientInputHandler();
            //IClientHandler clientChatHandler = new ClientChatHandler();

            // Bind the remote object's stub in the registry
            //Naming.bind("rmi://localhost:8807/ClientInputHandler", IClientInputHandler);
            Naming.bind("rmi://localhost:8807/ClientInputHandler", new_IH);
            //Naming.bind("rmi://localhost:8888/ClientChatHandler", clientChatHandler);
            System.out.println("Server is ready");
            while (true){
                Thread.sleep(1000);
            }


        } catch (Exception ignored) {

        }
    }





    public static void welcomeNewClient() throws RemoteException {
        inputList.add(new_IH);
        new_IH = new ClientInputHandler();
        try {
            Naming.bind("rmi://localhost:8807/ClientInputHandler", new_IH);
        } catch (AlreadyBoundException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("New stub generated");
    }

}

