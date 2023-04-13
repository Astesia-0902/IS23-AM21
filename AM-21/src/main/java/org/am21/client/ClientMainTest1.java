package org.am21.client;

import org.am21.client.controller.CliInputHandler;
import org.am21.controller.ClientInput;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public class ClientMainTest1 {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException, ServerNotActiveException {

        // Start the server
        ClientInput clientInputHandler = (ClientInput) Naming.lookup("rmi://localhost:8808/ClientInputHandler");

        //IClientHandler clientChatHandler = (IClientHandler) Naming.lookup("rmi://localhost:8808/ClientChatHandler");
        //Client do something
        System.out.println("Client is ready");

        while (true) {
            System.out.println("Please enter a command:");
            System.out.println("Enter \"help\" for a list of available commands");
            System.out.println("====================================");
            String input = System.console().readLine();
            CliInputHandler.handleInput(input);
        }
    }
}
