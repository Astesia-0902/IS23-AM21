package org.am21.client;

import org.am21.controller.ClientInput;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.Scanner;
public class ClientMainTest1 {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException, ServerNotActiveException {

        // Start the client n°1
        ClientInput clientInputHandler = (ClientInput) Naming.lookup("rmi://localhost:8807/ClientInputHandler");
        ClientGameController.clientInputHandler = clientInputHandler;
        //IClientHandler clientChatHandler = (IClientHandler) Naming.lookup("rmi://localhost:8808/ClientChatHandler");
        //Client do something
        System.out.println("Client 1 is ready");

        while (true) {
            System.out.println("Please enter a command:");
            System.out.println("Enter \"help\" for a list of available commands");
            System.out.println("====================================");
            Scanner in = new Scanner(System.in);

            String input = in.nextLine();
            
            CliInputHandler.handleInput(input);
        }
    }
}
