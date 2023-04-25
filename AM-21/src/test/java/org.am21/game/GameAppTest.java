package org.am21.game;

import org.am21.networkRMI.ClientInputHandler;
import org.am21.networkRMI.IClientInput;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class GameAppTest {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(8807);
            IClientInput IClientInputHandler = new ClientInputHandler();
            Naming.bind("rmi://localhost:8807/ClientInputHandler", IClientInputHandler);
            System.out.println("Server > Server online");
            while (true){
                Thread.sleep(1000);
            }


        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (AlreadyBoundException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException("No Remote");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
