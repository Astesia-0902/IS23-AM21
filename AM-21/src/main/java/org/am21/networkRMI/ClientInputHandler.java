package org.am21.networkRMI;

import org.am21.controller.GameController;
import org.am21.controller.PlayerController;
import org.am21.model.GameManager;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;

//TODO: we need reference of this class in every player instance, so we can send message to the client
public class ClientInputHandler extends UnicastRemoteObject implements IClientInput {
    public String userName;
    public String userHost;
    private Integer createMatchRequestCount = 0;
    public PlayerController playerController;
    public IClientCallBack callBack;

    /**
     * Creates and exports a new UnicastRemoteObject object using an
     * anonymous port.
     * @throws RemoteException if failed to export object
     * @since JDK1.1
     */
    public ClientInputHandler() throws RemoteException {
    }


//        Get the IP address of the client
//        System.out.println("Hello, I am " + getClientHost() + ":" + getClientPort());

    /**
     * @return the result of the operation
     * @throws ServerNotActiveException if the client is not active
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
     * @param username
     * @return
     * @throws RemoteException
     * @throws ServerNotActiveException
     */
    @Override
    public boolean logIn(String username) throws RemoteException, ServerNotActiveException {
        userHost = getClientHost();
        this.userName = username;
        playerController = new PlayerController(username, this);

        //TODO:the same username is not allowed to log in
        synchronized (GameManager.players) {
            if (!GameManager.players.contains(playerController.getPlayer())) {
                GameManager.players.add(playerController.getPlayer());
            }
        }
        return true;
    }

    /**
     * @param playerNum
     * @throws RemoteException
     * @throws ServerNotActiveException
     */
    @Override
    public void createMatch(int playerNum) throws RemoteException, ServerNotActiveException {
        GameController.createMatch(userName, createMatchRequestCount, playerNum, playerController);
    }

    /**
     * @param matchID
     * @return
     * @throws RemoteException
     * @throws ServerNotActiveException
     */
    @Override
    public boolean joinGame(int matchID) throws RemoteException, ServerNotActiveException {
        GameController.joinGame(matchID, userName, playerController);
        return true;
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
    public boolean selectCell(int row, int col) throws RemoteException, ServerNotActiveException {
        if (!checkPlayerActionPhase() && playerController.selectCell(row, col)) {
            return true;
        }
        return false;
    }

    /**
     * @param colNum
     * @return
     * @throws ServerNotActiveException
     */
    public boolean insertInColumn(int colNum) throws RemoteException, ServerNotActiveException {
        if (!checkPlayerActionPhase() && playerController.tryToInsert(colNum)) {
            return true;
        }
        return false;
    }

    /**
     * @throws ServerNotActiveException
     */
    public boolean unselectCards() throws RemoteException, ServerNotActiveException {
        if (!checkPlayerActionPhase() && playerController.unselectCards()) {
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
    public boolean sortHand(int pos1, int pos2) throws RemoteException, ServerNotActiveException {
        if (!checkPlayerActionPhase() && playerController.changeHandOrder(pos1, pos2)) {
            return true;
        }
        return false;
    }

    /**
     * Player can leave the match when Match is WaitingPlayers or is GameGoing
     *
     * @return
     */
    @Override
    public boolean exitMatch() throws RemoteException {
        if (GameController.removePlayerFromMatch(playerController, playerController.getPlayer().getMatch().matchID)) {
            return true;
        }
        return false;
    }

    /**
     * Use this method to get the virtual view of the match
     *
     * @return JSON string of the virtual view
     * @throws RemoteException
     */
    @Override
    public String getVirtualView() throws RemoteException {
        return playerController.getPlayer().getMatch().getVirtualView();
    }

    @Override
    public void registerCallBack(IClientCallBack callBack) throws RemoteException {
        this.callBack = callBack;
        System.out.println("Client registered callback");
    }
}
