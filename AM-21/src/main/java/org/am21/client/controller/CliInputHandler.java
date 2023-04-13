package org.am21.client.controller;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public class CliInputHandler {
    public static void handleInput(String input) throws ServerNotActiveException, RemoteException {
        switch (input) {
            case "login":
                System.out.println("Login, please enter your username:");
                String username = System.console().readLine();
                handleLogin(username);
                break;
            case "create match":
                System.out.println("Create match, please enter the number of players:");
                int playerCount = Integer.parseInt(System.console().readLine());
                handleCreate(playerCount);
                break;
            case "join":
                System.out.println("Join match, please enter the match ID:");
                int matchID = Integer.parseInt(System.console().readLine());
                handleJoin(matchID);
                break;
            case "help":
                handleHelp();
                break;
            default:
                break;
        }
    }

    private static void handleHelp() {
        System.out.println("====================================");
        System.out.println("Available commands:");
        System.out.println("\"login\" - log in to the server");
        System.out.println("\"create match\" - create a new match");
        System.out.println("\"join\" - join an existing match");
    }

    private static void handleJoin(int matchID) throws ServerNotActiveException, RemoteException {
        GameController.clientInputHandler.joinGame(matchID);
    }

    private static void handleCreate(int playerCount) throws ServerNotActiveException, RemoteException {
        GameController.clientInputHandler.createMatch(playerCount);
    }

    private static void handleLogin(String username) throws ServerNotActiveException, RemoteException {
        GameController.clientInputHandler.logIn(username);
    }
}
