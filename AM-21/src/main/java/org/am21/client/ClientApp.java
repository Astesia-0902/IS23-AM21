package org.am21.client;

import org.am21.client.view.cli.Cli;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.concurrent.ExecutionException;

public class ClientApp {

    public static void main(String[] args) throws MalformedURLException, NotBoundException, ServerNotActiveException, ExecutionException, RemoteException {
        Cli cli = new Cli();
        cli.init();
    }


}
