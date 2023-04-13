package org.am21.controller;

import org.am21.model.GameManager;
import org.am21.model.PlayerManager;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;

//TODO: we need reference of this class in every player instance, so we can send message to the client
public class ClientInputHandler extends UnicastRemoteObject implements ClientInput {
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

    /**
     *
     * @return
     * @throws ServerNotActiveException
     */
    //TODO:When the command is not from the current player, the command should be ignored.
    public boolean checkPlayerActionPhase() throws ServerNotActiveException {
        String userHost = getClientHost();
        synchronized (GameManager.playerMatchMap) {
            synchronized (GameManager.matchList) {
                return GameManager.playerMatchMap.containsKey(userHost) &&
                        userHost.equals(GameManager.matchList.
                                get(GameManager.playerMatchMap.get(userHost)).currentPlayer.getHost());
            }
        }
    }

    /**
     *
     * @param username
     * @throws RemoteException
     * @throws ServerNotActiveException
     */
    @Override
    public void logIn(String username) throws RemoteException, ServerNotActiveException {
        userHost = getClientHost();
        this.userName = username;
        playerController = new PlayerController(username);
        //TODO:the same username is not allowed to log in
        synchronized (PlayerManager.players) {
            if (!PlayerManager.players.contains(playerController.getPlayer())) {
                PlayerManager.players.add(playerController.getPlayer());
            }
        }
    }

    /**
     *
     * @param playerNum
     * @throws RemoteException
     * @throws ServerNotActiveException
     */
    @Override
    public void createMatch(int playerNum) throws RemoteException, ServerNotActiveException {
        GameController.createMatch(userName, createMatchRequestCount, playerNum, playerController);
    }

    /**
     *
     * @param matchID
     * @throws RemoteException
     * @throws ServerNotActiveException
     */
    @Override
    public void joinGame(int matchID) throws RemoteException, ServerNotActiveException {
        GameController.joinGame(matchID, userName, playerController);
    }

//    public void startMatch(){
//        if(GameManager.playerMatchMap.containsKey(playerController.player.getName())){
//            GameManager.matchList.get(GameManager.playerMatchMap.get(playerController.player.getName())).matchStart();
//        }
//    }

    /**
     * @param row
     * @param col
     * @return
     * @throws ServerNotActiveException
     */
    public boolean selectCell(int row, int col) throws ServerNotActiveException {
        if (!checkPlayerActionPhase() &&playerController.selectCell(row, col)) {
            return true;
        }
        return false;
    }

    /**
     * @param colNum
     * @return
     * @throws ServerNotActiveException
     */
    public boolean insertInColumn(int colNum) throws ServerNotActiveException {
        if (!checkPlayerActionPhase() && playerController.tryToInsert(colNum)){
            return true;
        }
        return false;
    }

    /**
     * @throws ServerNotActiveException
     */
    public boolean unselectCards() throws ServerNotActiveException {
        if (!checkPlayerActionPhase()&&playerController.unselectCards()){
            return true;
        }
        return false;
    }

    /**
     * @param pos1
     * @param pos2
     * @throws ServerNotActiveException
     */
    @Override
    public boolean sortHand(int pos1,int pos2) throws ServerNotActiveException {
        if (!checkPlayerActionPhase()&&playerController.changeHandOrder(pos1,pos2)) {
            return true;
        }
        return false;
    }
}
