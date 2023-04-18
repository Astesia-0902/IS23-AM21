package org.am21.game;

import org.am21.networkRMI.ClientInputHandler;
import org.am21.networkRMI.IClientInput;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class GameAppTest {
    public static void main(String[] args) throws RemoteException {
        try {
            LocateRegistry.createRegistry(1234);
            IClientInput IClientInputHandler = new ClientInputHandler();
            Naming.bind("rmi://localhost:1234/ClientInputHandler", IClientInputHandler);
            System.out.println("Server > Server online");
            while (true){
                Thread.sleep(1000);
            }


        } catch (Exception ignored) {

        }
    }
}
