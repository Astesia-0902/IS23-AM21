package org.am21.networkRMI;

import org.am21.controller.GameController;
import org.am21.controller.PlayerController;
import org.am21.model.GameManager;
import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.model.enumer.ServerMessage;
import org.am21.model.enumer.UserStatus;

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
    public boolean logIn(String username, IClientCallBack clientCallBack) throws RemoteException, ServerNotActiveException {
        userHost = getClientHost();
        this.userName = username;
        //TODO: separate CIH from playerController constructor (RMI not needed for model & controller testing)
        playerController = new PlayerController(username, this);
        //TODO:the same username is not allowed to log in(same name not allowed)
        synchronized (GameManager.players) {
            if (!GameManager.players.contains(playerController.getPlayer())) {
                GameManager.players.add(playerController.getPlayer());
            }
        }

        playerController.clientInput.callBack.sendMessageToClient(ServerMessage.Login_Ok.value() + username);
        return true;
    }

    /**
     * @param playerNum
     * @return
     * @throws RemoteException
     * @throws ServerNotActiveException
     */
    @Override
    public boolean createMatch(int playerNum) throws RemoteException, ServerNotActiveException {
        if (GameController.createMatch(userName, createMatchRequestCount, playerNum, playerController)) {
            return true;
        }
        return false;
    }

    /**
     * @param matchID
     * @return
     * @throws RemoteException
     * @throws ServerNotActiveException
     */
    @Override
    public boolean joinGame(int matchID) throws RemoteException, ServerNotActiveException {
        if (GameController.joinGame(matchID, userName, playerController)) {
            return true;
        }
        return false;
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
    public boolean deselectCards() throws RemoteException, ServerNotActiveException {
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
    public boolean leaveMatch() throws RemoteException {
        if (GameController.removePlayerFromMatch(playerController, playerController.getPlayer().getMatch().matchID)) {
            this.callBack.notifyGoToMenu();
            return true;
        }
        return false;
    }

    @Override
    public boolean exitGame() throws RemoteException {

        if (playerController.getPlayer().getMatch() != null) {

            GameController.removePlayerFromMatch(playerController, playerController.getPlayer().getMatch().matchID);
        }
        //When Player Exit The Game, every info abound the Player is cancelled
        if (GameController.cancelPlayer(playerController)) {
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

    /**
     * This method is called after the login of the player
     *
     * @param callBack
     * @throws RemoteException
     */
    @Override
    public void registerCallBack(IClientCallBack callBack) throws RemoteException {
        this.callBack = callBack;
        GameManager.client_connected++;
        System.out.println("Client Callback registered:" + GameManager.client_connected);
    }

    /**
     * This method contact player's match ChatManager to send a message
     * @param message
     * @return false if the player is not in a match, so the message was not sent, otherwise true
     * @throws RemoteException
     */
    @Override
    public boolean sendChatMessage(String message) throws RemoteException {
        Player p = playerController.getPlayer();
        if (p.getMatch() != null) {
            p.getMatch().chatManager.sendChat(message, p.getNickname());
            return true;
        }
        return false;
    }

    @Override
    public boolean sendPlayerMessage(String message, String receiver) throws RemoteException {
        String sender = playerController.getPlayer().getNickname();
        synchronized (GameManager.players) {
            for (Player p : GameManager.players) {
                if (p.getNickname().equals(receiver)) {
                    GameManager.sendTextCommunication(p.getController(),"------------------------------------------");
                    GameManager.sendTextCommunication(p.getController(),"\n"+ sender + "[!] > \"" + message + "\"");
                    GameManager.sendTextCommunication(p.getController(),"------------------------------------------\n");

                    return true;
                }
            }

        }
        return false;
    }

    @Override
    public void printOnlinePlayers() throws RemoteException {
        String message = "";
        this.callBack.sendMessageToClient(ServerMessage.ListP.value());
        synchronized (GameManager.players) {
            for (Player p : GameManager.players) {
                if (p.getStatus() == UserStatus.Online || p.getStatus() == UserStatus.GameMember) {
                    message += ("[" + p.getNickname() + " | "+p.getStatus()+" ] \n");

                }
            }
        }
        callBack.sendMessageToClient(message);

    }

    @Override
    public void printMatchList() throws RemoteException {
        String message = "";
        this.callBack.sendMessageToClient("Match List: ");
        synchronized (GameManager.matchList) {
            if (GameManager.matchList.size() > 0) {
                for (Match m : GameManager.matchList) {
                    message += ("[ID: " + m.matchID + " | " + m.gameState + " | Players: (" + m.playerList.size() + "/" + m.maxSeats + ")]\n");
                }
            }
        }
        this.callBack.sendMessageToClient(message);
    }


}
