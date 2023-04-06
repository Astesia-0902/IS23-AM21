package org.am21.controller;

import org.am21.model.GameManager;
import org.am21.model.enumer.GameState;

import java.rmi.RemoteException;

public class GameController {

    public GameController() throws RemoteException {
    }

    /**
     * Initialize the game.
     * Pay attention to the order of the initialization of instances to avoid potential null pointer exception.
     */

    public static void joinGame(int matchID, String userName, PlayerController playerController) {
        synchronized (GameManager.playerMatchMap) {
            synchronized (GameManager.matchList) {
                joinGameHelper(matchID, userName, playerController);
            }
        }
    }

    private static void joinGameHelper(int matchID, String userName, PlayerController playerController) {
        if (GameManager.matchList.get(matchID) == null) {
            System.out.println("Message from the server: the indicate match not exists.");
            return;
        }

        if (GameManager.matchList.get(matchID).gamePhase == GameState.GameGoing) {
            if (!GameManager.playerMatchMap.containsKey(userName)) {
                System.out.println("Message from the server: the player not exists in any match.");
            } else {
                if (!GameManager.matchList.get(matchID).addPlayer(playerController.player)) {
                    System.out.println("Message from the server: the match is full.");
                }
            }
            //if the match is not started, the player join the match
        } else if (GameManager.matchList.get(matchID).gamePhase == GameState.WaitingPlayers) {
            if (!GameManager.matchList.get(matchID).addPlayer(playerController.player)) {
                System.out.println("Message from the server: the match is full.");
            }
        }
    }

    public static void createMatch(String userName, Integer createMatchRequestCount, int playerNum, PlayerController playerController) {
        synchronized (GameManager.playerMatchMap) {
            synchronized (GameManager.matchList) {
                createMatchHelper(userName, createMatchRequestCount, playerNum, playerController);
            }
        }
    }

    private static void createMatchHelper(String userName, Integer createMatchRequestCount, int playerNum, PlayerController playerController) {
        if (GameManager.playerMatchMap.containsKey(userName) && createMatchRequestCount == 0) {
            System.out.println("Message from the server: the player already exists in a match. " +
                    "Create a new match will cause the player leave the current match." +
                    "Do you want to continue?");
            createMatchRequestCount = 1;
        } else if (GameManager.playerMatchMap.containsKey(userName) && createMatchRequestCount == 1) {
            createMatchRequestCount = 0;
            GameManager.createMatch(playerNum, playerController);
            //TODO:player leave the current match
        } else if (!GameManager.playerMatchMap.containsKey(userName)) {
            GameManager.createMatch(playerNum, playerController);
        }
    }

}
