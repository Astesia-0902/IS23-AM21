package org.am21.networkRMI;

import org.am21.controller.GameController;
import org.am21.controller.PlayerController;
import org.am21.model.GameManager;
import org.am21.model.enumer.ConnectionType;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;

/**
 * This class is the client input handler
 */
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
        playerController = new PlayerController("", this);
        playerController.connectionType = ConnectionType.RMI;
    }

    /**
     * Check the player is the current player
     * @return the result of the operation
     */
    public boolean checkPlayerActionPhase() {
        synchronized (GameManager.playerMatchMap) {
            synchronized (GameManager.matchMap) {
                return GameManager.playerMatchMap.containsKey(userName) &&
                       userName.equals(GameManager.matchMap.
                               get(GameManager.playerMatchMap.get(userName)).currentPlayer.getNickname());
            }
        }
    }

    /**
     * log in
     *
     * @param username username
     * @return true if login successfully, false if the username already exists.
     * @throws RemoteException          if failed to export object
     * @throws ServerNotActiveException if the client is not active
     */
    @Override
    public boolean logIn(String username, IClientCallBack clientCallBack) throws RemoteException, ServerNotActiveException {
        playerController = new PlayerController(username, this);
        playerController.connectionType = ConnectionType.RMI;
        userHost = getClientHost();
        this.userName = username;
        return GameController.login(username, playerController);
    }

    /**
     * create a match
     *
     * @param playerNum number of players
     * @return true if the operation is successful, false if the match is full
     * @throws RemoteException          if failed to export object
     * @throws ServerNotActiveException if the client is not active
     */
    @Override
    public boolean createMatch(int playerNum) throws RemoteException, ServerNotActiveException {
        return GameController.createMatch(userName, createMatchRequestCount, playerNum, playerController);
    }

    /**
     * @param matchID matchID
     * @return true if the operation is successful, false if the match is full
     * @throws RemoteException          if failed to export object
     * @throws ServerNotActiveException if the client is not active
     */
    @Override
    public boolean joinGame(int matchID) throws RemoteException, ServerNotActiveException {
        return GameController.joinGame(matchID, userName, playerController);
    }


    /**
     * select a cell
     *
     * @param row row
     * @param col column
     * @return true if the operation is successful, false if the cell is not selectable
     * @throws ServerNotActiveException if the client is not active
     */
    public boolean selectCell(int row, int col) throws RemoteException, ServerNotActiveException {
        return GameController.selectCell(row, col, playerController);
    }

    /**
     * confirm the selection
     *
     * @return true if the operation is successful, false if the selection is not valid
     * @throws RemoteException          if failed to export object
     * @throws ServerNotActiveException if the client is not active
     */
    @Override
    public boolean confirmSelection() throws RemoteException, ServerNotActiveException {
        return GameController.confirmSelection(playerController);
    }

    /**
     * insert cards in the column
     *
     * @param colNum column number
     * @return true if the operation is successful, false if the column is full
     * @throws ServerNotActiveException if the client is not active
     */
    public boolean insertInColumn(int colNum) throws RemoteException, ServerNotActiveException {
        return GameController.insertInColumn(colNum, playerController);
    }

    /**
     * end the turn
     *
     * @return true if the operation is successful, false if the selection is not valid
     * @throws RemoteException          if failed to export object
     * @throws ServerNotActiveException if the client is not active
     */
    @Override
    public boolean endTurn() throws RemoteException, ServerNotActiveException {
        return GameController.endTurn(playerController);
    }

    /**
     * deselect the selected cards
     *
     * @return true if the operation is successful, false if the selection is not valid
     * @throws RemoteException          if failed to export object
     * @throws ServerNotActiveException if the client is not active
     */
    @Override
    public boolean deselectCards() throws RemoteException, ServerNotActiveException {
        return GameController.deselectCards(playerController);
    }

    /**
     * @param pos1 position 1
     * @param pos2 position 2
     * @throws ServerNotActiveException if the client is not active
     */
    @Override
    public boolean sortHand(int pos1, int pos2) throws RemoteException, ServerNotActiveException {
        return GameController.sortHand(pos1, pos2, playerController);
    }

    /**
     * Player can leave the match when Match is WaitingPlayers or is GameGoing
     *
     * @return true if the method is successful, otherwise false
     */
    @Override
    public boolean leaveMatch() throws RemoteException {
        return GameController.leaveMatch(playerController);
    }

    /**
     * THis method allows the player to exit from the game
     *
     * @return true if the method is successful, otherwise false
     * @throws RemoteException when remote is not connected
     */
    @Override
    public boolean exitGame() throws RemoteException {
        return GameController.exitGame(playerController);
    }

    /**
     * This method is called after the login of the player
     * It allows the server to register Client's CallBack Interface
     *
     * @param path the path to find client callback
     * @throws RemoteException when Remote is not connected
     */
    @Override
    public void registerCallBack(String path) throws RemoteException {
        try {
            callBack = (IClientCallBack) Naming.lookup(path);
            String clientIP = RemoteServer.getClientHost();
            System.out.println("Client IP address: " + clientIP);
            callBack.ping();
        } catch (NotBoundException | MalformedURLException | ServerNotActiveException e) {
            throw new RuntimeException(e);
        }
        GameManager.client_connected++;
        System.out.println("Client Callback registered:" + GameManager.client_connected);
    }

    /**
     * change the max player number of the match
     *
     * @param newMaxSeats new max player number
     * @return true if the operation is successful, false if the new max player number is not valid
     * @throws RemoteException if failed to export object
     */
    @Override
    public boolean changeMatchSeats(int newMaxSeats) throws RemoteException {
        return GameController.changeMatchSeats(newMaxSeats, playerController);
    }


    /**
     * This method allows the Client to send a Message in the Match's Chat
     *
     * @param message the message
     * @param live    indicates if the message was sent by live Chat (true) or not (false)
     * @return true if the message was correctly sent, otherwise false
     * @throws RemoteException when the Remote Invocation fails
     */
    @Override
    public boolean sendPublicMessage(String message, boolean live) throws RemoteException {
        return GameController.forwardPublicMessage(message, playerController);
    }

    /**
     * This method allows the Client to Send a Message to Specific Online Players
     *
     * @param message  the message
     * @param receiver the online player who will receive the message
     * @param live     indicates if the message was sent by live Chat (true) or not (false)
     * @return true if the message was sent to the receiver, otherwise false
     * @throws RemoteException when the Remote Invocation fails
     */
    @Override
    public boolean sendPrivateMessage(String message, String receiver, boolean live) throws RemoteException {
        return GameController.forwardPrivateMessage(message, receiver, playerController);
    }
}
