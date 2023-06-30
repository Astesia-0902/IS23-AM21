package org.am21.client;

import org.am21.client.view.GUI.Gui;
import org.am21.client.view.TUI.Cli;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

/**
 * This class is used to start the client
 */
public class ClientApp {
    public static void main(String[] args) throws Exception {


        if(args.length>0 && args[0].equals("tui")){
            Cli cli = new Cli();
            run(cli);
        }else{
            Gui gui = new Gui();
            gui.init();
        }


    }

    /**
     * This method is used to start the client
     *
     * @param cli the client
     */
    private static void run(Cli cli) {
        try {
            cli.init();
            cli.askLogin();
            cli.askMenuAction();
        } catch (RemoteException | ServerNotActiveException | MalformedURLException | NotBoundException e) {
            throw new RuntimeException(e);
        }


    }
}
