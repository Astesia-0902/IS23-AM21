package org.am21.extra;

import org.am21.networkRMI.IClientInput;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.Scanner;

public class ClientMainTest2 {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException, ServerNotActiveException {

        // Start the client n°2
        IClientInput IClientInputHandler = (IClientInput) Naming.lookup("rmi://localhost:8808/ClientInputHandler");
        //IClientHandler clientChatHandler = (IClientHandler) Naming.lookup("rmi://localhost:8808/ClientChatHandler");
        //Client do something
        //ClientGameController.IClientInputHandler = IClientInputHandler;
        System.out.println("Client 2 is ready");
        while (true) {
            System.out.println("Please enter a command:");
            System.out.println("Enter \"help\" for a list of available commands");
            System.out.println("====================================");
            Scanner in = new Scanner(System.in);

            String input = in.nextLine();

            //CliInputHandler.handleInput(input);
        }
        /*System.out.println(clientInputHandler);
        System.out.println(clientChatHandler);
        clientInputHandler.logIn("ade");
        clientInputHandler.joinGame(2);*/




    }
}
