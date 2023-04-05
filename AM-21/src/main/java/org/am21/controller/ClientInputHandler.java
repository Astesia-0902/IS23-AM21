package org.am21.controller;

import org.am21.model.GameManager;
import org.am21.model.PlayerManager;
import org.am21.model.Cards.ItemTileCard;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;

public class ClientInputHandler extends UnicastRemoteObject implements IClientHandler {
    public String userName;
    public String userHost;
    private Integer createMatchRequestCount = 0;
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
        if (!PlayerManager.players.contains(playerController.player)) {
            PlayerManager.players.add(playerController.player);
        }
    }

    public void createMatch(int playerNum) throws RemoteException, ServerNotActiveException {
        GameController.createMatch(userName, createMatchRequestCount, playerNum, playerController);
    }

    public void joinGame(int matchID) throws RemoteException, ServerNotActiveException {
        GameController.joinGame(matchID, userName, playerController);
    }

//    public void startMatch(){
//        if(GameManager.playerMatchMap.containsKey(playerController.player.getName())){
//            GameManager.matchList.get(GameManager.playerMatchMap.get(playerController.player.getName())).matchStart();
//        }
//    }

    public void selectCell(int row, int col) throws ServerNotActiveException {
        if (!checkPlayerActionPhase()) return;
        playerController.selectCell(row, col);
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
