package org.am21.controller;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class ClientMainTest {
    public static void main(String[] args) {
        try {
            // Start the server
            LocateRegistry.createRegistry(8888);
            ClientInputHandler clientInputHandler = (ClientInputHandler) Naming.lookup("rmi://localhost:8888/ClientInputHandler");
            ClientChatHandler clientChatHandler = (ClientChatHandler) Naming.lookup("rmi://localhost:8888/ClientChatHandler");
            //Client do something
            System.out.println("Client is ready");

        } catch (Exception ignored) {

        }
    }
}
