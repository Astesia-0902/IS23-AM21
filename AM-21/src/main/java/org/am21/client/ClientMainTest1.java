package org.am21.client;

import org.am21.networkRMI.ClientCallBack;
import org.am21.networkRMI.IClientInput;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.Scanner;
public class ClientMainTest1 {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException, ServerNotActiveException {

        // Start the client n°1
        IClientInput clientInput = (IClientInput) Naming.lookup("rmi://localhost:8807/ClientInputHandler");
        ClientGameController.IClientInputHandler = clientInput;
        clientInput.registerCallBack(new ClientCallBack());
        System.out.println("Client 1 is ready");
        //TODO:Use callback to get the message from server
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
