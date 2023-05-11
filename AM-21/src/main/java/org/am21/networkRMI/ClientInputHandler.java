package org.am21.networkRMI;

import org.am21.controller.GameController;
import org.am21.controller.PlayerController;
import org.am21.model.GameManager;
import org.am21.model.enumer.ConnectionType;
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
     *
     * @throws RemoteException if failed to export object
     * @since JDK1.1
     */
    public ClientInputHandler() throws RemoteException {
        playerController = new PlayerController("",this);
        playerController.connectionType = ConnectionType.RMI;
    }

    //TODO: Check if the ip address and port are valid

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
                return GameManager.playerMatchMap.containsKey(userName) &&
                        userName.equals(GameManager.matchList.
                                get(GameManager.playerMatchMap.get(userName)).currentPlayer.getNickname());
            }
        }
    }

    /**
     * @param username username
     * @return true if login successfully, false if the username already exists.
     * @throws RemoteException          if failed to export object
     * @throws ServerNotActiveException if the client is not active
     */
    @Override
    public boolean logIn(String username, IClientCallBack clientCallBack) throws RemoteException, ServerNotActiveException {
        playerController = new PlayerController(username, this, null);
        playerController.connectionType = ConnectionType.RMI;
        userHost = getClientHost();
        this.userName = username;
        //TODO: separate CIH from playerController constructor (RMI not needed for model & controller testing)
        return GameController.login(username, playerController);
    }

    /**
     * @param playerNum
     * @return
     * @throws RemoteException
     * @throws ServerNotActiveException
     */
    @Override
    public boolean createMatch(int playerNum) throws RemoteException, ServerNotActiveException {
        return GameController.createMatch(userName, createMatchRequestCount, playerNum, playerController);
    }

    /**
     * @param matchID matchID
     * @return true if the operation is successful, false if the match is full
     * @throws RemoteException if failed to export object
     * @throws ServerNotActiveException if the client is not active
     */
    @Override
    public boolean joinGame(int matchID) throws RemoteException, ServerNotActiveException {
        if (GameController.joinGame(matchID, userName, playerController)) {
            return true;
        }
        return false;
    }


    /**
     * @param row row
     * @param col column
     * @return true if the operation is successful, false if the cell is not selectable
     * @throws ServerNotActiveException if the client is not active
     */
    public boolean selectCell(int row, int col) throws RemoteException, ServerNotActiveException {
        return GameController.selectCell(row, col, playerController);
    }

    @Override
    public boolean confirmSelection() throws RemoteException, ServerNotActiveException {
        return GameController.confirmSelection(playerController);
    }

    /**
     * @param colNum column number
     * @return true if the operation is successful, false if the column is full
     * @throws ServerNotActiveException if the client is not active
     */
    public boolean insertInColumn(int colNum) throws RemoteException, ServerNotActiveException {
        return GameController.insertInColumn(colNum, playerController);
    }

    @Override
    public boolean endTurn() throws RemoteException, ServerNotActiveException {
        return GameController.endTurn(playerController);
    }


    public boolean deselectCards() throws RemoteException, ServerNotActiveException {
        return GameController.deselectCards(playerController);
    }

    /**
     * @param pos1
     * @param pos2
     * @throws ServerNotActiveException
     */
    @Override
    public boolean sortHand(int pos1, int pos2) throws RemoteException, ServerNotActiveException {
        return GameController.sortHand(pos1, pos2, playerController);
    }

    /**
     * Player can leave the match when Match is WaitingPlayers or is GameGoing
     *
     * @return
     */
    @Override
    public boolean leaveMatch() throws RemoteException {
        return GameController.leaveMatch(playerController);
    }

    @Override
    public boolean exitGame() throws RemoteException {
        return GameController.exitGame(playerController);
    }

    /**
     * Use this method to get the virtual view of the match
     *
     * @return JSON string of the virtual view
     * @throws RemoteException
     */
    @Override
    public String getVirtualView() throws RemoteException {
        return GameController.getVirtualView(playerController);
    }

    /**
     * This method is called after the login of the player
     *
     * @param callBack
     * @throws RemoteException
     */
    @Override
    public void registerCallBack(IClientCallBack callBack) throws RemoteException {
        GameController.registerCallBack(callBack, playerController);
        //System.out.println("Client Callback registered:" + GameManager.client_connected);
    }

    /**
     * This method contact player's match ChatManager to send a message
     *
     * @param message
     * @return false if the player is not in a match, so the message was not sent, otherwise true
     * @throws RemoteException
     */
    @Override
    public boolean sendChatMessage(String message) throws RemoteException {
        return GameController.sendChatMessage(message, playerController);
    }

    @Override
    public boolean sendPlayerMessage(String message, String receiver,boolean refresh) throws RemoteException {
        return GameController.sendPlayerMessage(message, receiver, playerController,refresh);
    }

    @Override
    public void printOnlinePlayers() throws RemoteException {
        GameController.printOnlinePlayers(playerController);
    }

    public void requestOnlinePlayers() throws RemoteException{

    }

    public void requestMatchList() throws RemoteException{

    }
    @Override
    public void printMatchList() throws RemoteException {
        GameController.printMatchList(playerController);
    }

    //TODO: method

    /**
     * This method is used to open a specific chat
     * @throws RemoteException
     */
    @Override
    public void openChat() throws RemoteException {

    }

    @Override
    public boolean changeMatchSeats(int newMaxSeats) throws RemoteException {
        return GameController.changeMatchSeats(newMaxSeats, playerController);
    }


    //TODO: to be deleted when the game is complete, this method is useful to accelerate testing
    @Override
    public boolean changeInsertLimit(int newLimit) throws RemoteException {
        return GameController.changeInsertLimit(newLimit, playerController);
    }


    @Override
    public boolean sendPublicMessage(String message, boolean live) throws RemoteException {
        return GameController.forwardPublicMessage(message,playerController,live);
    }

    @Override
    public boolean sendPrivateMessage(String message, String receiver, boolean live) throws RemoteException {
        return GameController.forwardPrivateMessage(message,receiver,playerController,live);
    }
}
