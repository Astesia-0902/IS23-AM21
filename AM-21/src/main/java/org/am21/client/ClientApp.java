package org.am21.client;

import org.am21.client.view.TUI.Cli;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class ClientApp {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, ServerNotActiveException, ExecutionException, RemoteException {
        Cli cli = new Cli();

        run(cli);
        //runCliTest(cli);

    }

    private static void run(Cli cli) {
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
                    case "action": cli.askMenuAction(); break;
                    case "login": cli.askLogin(); break;
                    case "create": cli.askCreateMatch(); break;
                    case "join": cli.askJoinMatch(); break;
                    case "max": cli.askMaxSeats(); break;
                    case "leave": cli.askLeaveMatch(); break;
                    case "info": cli.askServerInfoRMI(); break;
                    //TODO:case "scg":cli.showCommonGoals();break;
                    //TODO:case "spg":cli.showCommonGoals();break;
                    //TODO:case "scp":cli.announceCurrentPlayer();break;
                    case "exit":break fine;
                    default: System.out.println("Invalid command, please try again"); break;

                }
                System.out.println("----------------------------------");
                System.out.println("Ending " + sel + " test. Please choose other options");
                System.out.println("----------------------------------");
            }

        }
    }

}
