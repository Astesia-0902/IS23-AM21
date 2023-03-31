package org.am21.controller;

import org.am21.model.GameManager;
import org.am21.model.GamePhases;
import org.am21.model.PlayerManager;
import org.am21.model.items.Card.ItemTileCard;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;

public class ClientInputHandler extends UnicastRemoteObject implements IClientHandler {
    public String userName;
    public String userHost;
    private int createMatchRequestCount = 0;
    public PlayerController playerController;
    public ClientChatHandler clientChatHandler;

    /**
     * Creates and exports a new UnicastRemoteObject object using an
     * anonymous port.
     *
     * @throws RemoteException if failed to export object
     * @since JDK1.1
     */
    protected ClientInputHandler() throws RemoteException {
        clientChatHandler = new ClientChatHandler();
    }
//        Get the IP address of the client
//        System.out.println("Hello, I am " + getClientHost() + ":" + getClientPort());

    //TODO:When the command is not from the current player, the command should be ignored.
    private boolean checkPlayerActionPhase() throws ServerNotActiveException {
        String userHost = getClientHost();
        return GameManager.playerMatchMap.containsKey(userHost) &&
                userHost.equals(GameManager.matchList.
                        get(GameManager.playerMatchMap.get(userHost)).currentPlayer.getHost());
    }

    public void logIn(String username) throws RemoteException, ServerNotActiveException {
        userHost = getClientHost();
        this.userName = username;
        playerController = new PlayerController(username);
        //TODO:the same username is not allowed to log in
        if(!PlayerManager.players.contains(playerController.player)) {
            PlayerManager.players.add(playerController.player);
        }
    }

    public void createMatch(int playerNum) throws RemoteException, ServerNotActiveException {
        if (GameManager.playerMatchMap.containsKey(getClientHost()) && createMatchRequestCount == 0) {
            System.out.println("Message from the server: the player already exists in a match. " +
                    "Create a new match will cause the player leave the current match." +
                    "Do you want to continue?");
            createMatchRequestCount = 1;
        } else if (GameManager.playerMatchMap.containsKey(getClientHost()) && createMatchRequestCount == 1) {
            createMatchRequestCount = 0;
            GameManager.createMatch(playerNum,playerController);
            //TODO:player leave the current match
        } else if (!GameManager.playerMatchMap.containsKey(getClientHost())) {
            GameManager.createMatch(playerNum,playerController);
        }
    }

    public void joinGame(int matchID) throws RemoteException, ServerNotActiveException {
        if(GameManager.matchList.get(matchID) == null) {
            System.out.println("Message from the server: the indicate match not exists.");
            return;
        }

        if (GameManager.matchList.get(matchID).gamePhase == GamePhases.GameOnGoing) {
            if (!GameManager.playerMatchMap.containsKey(userName)) {
                System.out.println("Message from the server: the player not exists in any match.");
            } else {
                if(!GameManager.matchList.get(matchID).addPlayer(playerController.player)){
                    System.out.println("Message from the server: the match is full.");
                }
            }
            //if the match is not started, the player join the match
        } else if (GameManager.matchList.get(matchID).gamePhase == GamePhases.StartGame) {
            if(!GameManager.matchList.get(matchID).addPlayer(playerController.player)){
                System.out.println("Message from the server: the match is full.");
            }
        }
    }

//    public void startMatch(){
//        if(GameManager.playerMatchMap.containsKey(playerController.player.getName())){
//            GameManager.matchList.get(GameManager.playerMatchMap.get(playerController.player.getName())).matchStart();
//        }
//    }

    public void selectCard(int row, int col) throws ServerNotActiveException {
        if (!checkPlayerActionPhase()) return;
        playerController.selectCard(row,col);
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
