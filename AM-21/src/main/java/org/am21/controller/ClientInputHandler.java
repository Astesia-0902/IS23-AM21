package org.am21.controller;

import org.am21.model.GamePhases;
import org.am21.model.items.Card.ItemTileCard;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;

public class ClientInputHandler extends UnicastRemoteObject implements IClientHandler {
    public GameController gameController;
    private int createMatchRequestCount = 0;

    /**
     * Creates and exports a new UnicastRemoteObject object using an
     * anonymous port.
     *
     * @throws RemoteException if failed to export object
     * @since JDK1.1
     */
    protected ClientInputHandler() throws RemoteException {
    }
//        Get the IP address of the client
//        System.out.println("Hello, I am " + getClientHost() + ":" + getClientPort());

    //TODO:When the command is not from the current player, the command should be ignored.
    private boolean checkPlayerActionPhase() throws ServerNotActiveException {
        String userHost = getClientHost();
        return gameController.playerMatchMap.containsKey(userHost) &&
                userHost.equals(gameController.gameManager.matchList.
                        get(gameController.playerMatchMap.get(userHost)).currentPlayer.getHost());
    }

    public void logIn(String username) throws RemoteException, ServerNotActiveException {
        String userHost = getClientHost();
        //TODO:Insert the player into the player list
    }

    public void createMatch() throws RemoteException, ServerNotActiveException {
        if (gameController.playerMatchMap.containsKey(getClientHost()) && createMatchRequestCount == 0) {
            System.out.println("Message from the server: the player already exists in a match. " +
                    "Create a new match will cause the player leave the current match." +
                    "Do you want to continue?");
            createMatchRequestCount = 1;
        } else if (gameController.playerMatchMap.containsKey(getClientHost()) && createMatchRequestCount == 1) {
            createMatchRequestCount = 0;
            //TODO:player create a new match
            //TODO:player leave the current match
        } else if (!gameController.playerMatchMap.containsKey(getClientHost())) {
            //TODO:player create a new match
        }
    }

    public void joinGame(int matchID) throws RemoteException, ServerNotActiveException {
        if(gameController.gameManager.matchList.get(matchID) == null) {
            System.out.println("Message from the server: the indicate match not exists.");
            return;
        }

        if (gameController.gameManager.matchList.get(matchID).gamePhase == GamePhases.GameOnGoing) {
            if (!gameController.playerMatchMap.containsKey(getClientHost())) {
                System.out.println("Message from the server: the player not exists in any match.");
            } else {
                //TODO:player join an ongoing match
            }
            //if the match is not started, the player join the match
        } else if (gameController.gameManager.matchList.get(matchID).gamePhase == GamePhases.StartGame) {
            //TODO:player join the match not started
        }
    }

    public void selectCard(int x, int y, int dir, int num) throws ServerNotActiveException {
        if (!checkPlayerActionPhase()) return;
    }

    public boolean insertTiles(int colNum, int numTiles) throws ServerNotActiveException {
        if (!checkPlayerActionPhase()) return false;
        return false;
    }

    public void removeItemFromHand(ItemTileCard item) throws ServerNotActiveException {
        if (!checkPlayerActionPhase()) return;
    }

    public void changeHandOrder() throws ServerNotActiveException {
        if (!checkPlayerActionPhase()) return;
    }
}
