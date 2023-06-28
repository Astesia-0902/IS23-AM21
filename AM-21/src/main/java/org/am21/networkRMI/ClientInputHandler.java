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
        playerController = new PlayerController("", this);
        playerController.connectionType = ConnectionType.RMI;
    }

    //TODO: Check if the ip address and port are valid

//        Get the IP address of the client
//        System.out.println("Hello, I am " + getClientHost() + ":" + getClientPort());

    /**
     * @return the result of the operation
     */
    //TODO:When the command is not from the current player, the command should be ignored.
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
     * @throws RemoteException          if failed to export object
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

    @Override
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
     * @return true if the method is successful, otherwise false
     */
    @Override
    public boolean leaveMatch() throws RemoteException {
        return GameController.leaveMatch(playerController);
    }

    /**
     * THis method allows the player to exit from the game
     * @return true if the method is successful, otherwise false
     * @throws RemoteException when remote is not connected
     */
    @Override
    public boolean exitGame() throws RemoteException {
        return GameController.exitGame(playerController);
    }

    /**
     * Use this method to get the virtual view of the match
     *
     * @return JSON string of the virtual view
     * @throws RemoteException when remote is not connected
     */
    @Override
    public String getVirtualView() throws RemoteException {
        return GameController.getVirtualView(playerController);
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
