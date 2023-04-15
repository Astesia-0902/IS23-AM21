package org.am21.client.controller;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.Scanner;

public class CliInputHandler {
    public static void handleInput(String input) throws ServerNotActiveException, RemoteException {
        Scanner in = new Scanner(System.in);
        switch (input) {
            case "login":
                System.out.println("Login, please enter your username:");
                String username = in.nextLine();
                handleLogin(username);
                break;
            case "create match":
                System.out.println("Create match, please enter the number of players:");
                int playerCount = in.nextInt();
                handleCreate(playerCount);
                break;
            case "join":
                System.out.println("Join match, please enter the match ID:");
                int matchID = in.nextInt();
                handleJoin(matchID);
                break;
            case "help":
                handleHelp();
                break;
            case "exit":
                handleExit();
                break;
            default:
                break;
        }
    }

    private static void handleExit() {
    }

    private static void handleHelp() {
        System.out.println("====================================");
        System.out.println("Available commands:");
        System.out.println("\"login\" - log in to the server");
        System.out.println("\"create match\" - create a new match");
        System.out.println("\"join\" - join an existing match");
    }

    private static void handleJoin(int matchID) throws ServerNotActiveException, RemoteException {
        ClientGameController.clientInputHandler.joinGame(matchID);
    }

    private static void handleCreate(int playerCount) throws ServerNotActiveException, RemoteException {
        ClientGameController.clientInputHandler.createMatch(playerCount);
    }

    private static void handleLogin(String username) throws ServerNotActiveException, RemoteException {
        ClientGameController.clientInputHandler.logIn(username);
    }
}
