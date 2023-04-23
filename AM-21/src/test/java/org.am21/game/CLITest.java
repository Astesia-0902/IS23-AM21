package org.am21.game;

import org.am21.client.view.cli.Cli;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.concurrent.ExecutionException;

public class CLITest {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, ServerNotActiveException, ExecutionException, RemoteException {
        Cli cli = new Cli();

        setUp(cli);

    }

    private static void setUp(Cli cli) {
        try {
            cli.init();
            cli.askLogin();
            cli.askMenuAction();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (ServerNotActiveException e) {
            throw new RuntimeException(e);
        }


    }

}
