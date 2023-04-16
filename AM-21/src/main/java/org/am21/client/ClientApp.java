package org.am21.client;

import org.am21.client.view.cli.Cli;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class ClientApp {
    public static void runCliTest(Cli cli) throws ServerNotActiveException, RemoteException, ExecutionException, MalformedURLException, NotBoundException {
        Scanner in = new Scanner(System.in);
        fine:
        {
            while (true) {
                System.out.println("select option to go test: ");
                System.out.println("- welcome ");
                System.out.println("- action (select menu)");
                System.out.println("- login ");
                System.out.println("- create (the new match)");
                System.out.println("- join ");
                System.out.println("- max (the number of max player you want to)");
                System.out.println("- leave (the game)");
                System.out.println("- info (Server) ");
                System.out.println("- scg (show common goal) [not available] ");
                System.out.println("- spg (show personal goal) [not available]");
                System.out.println("- scp (show current player) [not available]");
                System.out.println("- exit (to ending the test) ");
                String sel = in.nextLine();
                System.out.println("Now beginning the " + sel + " test");
                System.out.println("----------------------------------");
                switch (sel) {
                    case "welcome":cli.init(); break;
                    case "action": cli.askAction(); break;
                    case "login": cli.askLogin(); break;
                    case "create": cli.askCreateGame(); break;
                    case "join": cli.askJoinGame(); break;
                    case "max": cli.askMaxSeats(); break;
                    case "leave": cli.askLeaveGame(); break;
                    case "info": cli.askServerInfo(); break;
                    //TODO:case "scg":cli.showCommonGoals();break;
                    //TODO:case "spg":cli.showCommonGoals();break;
                    //TODO:case "scp":cli.showCurrentPlayer();break;
                    case "exit":break fine;
                    default: System.out.println("Invalid command, please try again"); break;

                }
                System.out.println("----------------------------------");
                System.out.println("Ending " + sel + " test. Please choose other options");
                System.out.println("----------------------------------");
            }

        }
    }

    public static void main(String[] args) throws MalformedURLException, NotBoundException, ServerNotActiveException, ExecutionException, RemoteException {
        Cli cli = new Cli();
        runCliTest(cli);

    }


}
