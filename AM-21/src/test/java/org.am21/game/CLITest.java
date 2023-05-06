package org.am21.game;

import org.am21.client.view.TUI.Cli;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;


public class CLITest {
    public static void main(String[] args) throws RemoteException {

        Cli cli = new Cli();

        setUp(cli);

    }

    private static void setUp(Cli cli) {
        try {
            cli.init();
            cli.askLogin();
            cli.askMenuAction();







        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (ServerNotActiveException | MalformedURLException | NotBoundException e) {
            throw new RuntimeException(e);
        }


    }

}
