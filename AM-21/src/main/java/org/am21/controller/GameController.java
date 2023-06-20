package org.am21.controller;

import org.am21.model.GameManager;
import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.model.chat.ServerChatManager;
import org.am21.model.enumer.*;
import org.am21.model.items.Shelf;
import org.am21.networkRMI.IClientCallBack;
import org.am21.utilities.VirtualViewHelper;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public class GameController {

    public GameController() throws RemoteException {
    }

    public static boolean checkPlayerActionPhase(PlayerController playerController) {
        String username = playerController.getPlayer().getNickname();
        synchronized (GameManager.playerMatchMap) {
            synchronized (GameManager.matchMap) {
                Match match = GameManager.matchMap.get(GameManager.playerMatchMap.get(username));
                if (GameManager.playerMatchMap.containsKey(username) && (match.gameState.equals(GameState.GameGoing)||match.gameState.equals(GameState.LastRound) )&&
                        match.currentPlayer.getNickname().equals(username)) {
                    return true;
                }
                GameManager.sendReply(playerController, ServerMessage.NotYourTurn.value());
                return false;
            }
        }
    }

    /**
     * Login to the game.
     *
     * @param username username
     * @return true if login successfully, false if the username already exists.
     */
    public static boolean login(String username, PlayerController playerController) {
        //GameManager.checkUsersConnection();
        //GameManager.playerCleaner();
        if (GameManager.checkNameReconnection(username)) {
            Match match = GameManager.matchMap.get(GameManager.playerMatchMap.get(username));
            for (Player player : match.playerList) {
                if (player.getNickname().equals(username)) {
//                    if (playerController.connectionType == ConnectionType.SOCKET) {
//                        player.getController().clientHandlerSocket = playerController.clientHandlerSocket;
//                        playerController.clientHandlerSocket.myPlayer = playerInGame;
//                    } else if (playerController.connectionType == ConnectionType.RMI) {
//                        player.getController().clientInput = playerController.clientInput;
//                        playerController.clientInput.playerController = playerInGame;
//                    }
                    player.setController(null);
                    playerController.setPlayer(player);
                    player.setController(playerController);
                    playerController.reconnectPlayer();
                    CommunicationController.instance.notifyStart(match.matchID, playerController);
                    return true;
                }
            }
        }

        if (GameManager.checkNameSake(username)) {
            return false;
        }

        playerController.getPlayer().setNickname(username);

        synchronized (GameManager.players) {
            if (!GameManager.players.contains(playerController.getPlayer())) {
                GameManager.players.add(playerController.getPlayer());
            }
            VirtualViewHelper.virtualizeOnlinePlayers();
        }
        CommunicationController.instance.sendMessageToClient(ServerMessage.Login_Ok.value(), playerController);
        CommunicationController.instance.sendMessageToClient("Server> Hi "+ username, playerController);
        //DEBUG
        System.out.println(username + " joined the game");
        updatePlayersGlobalView();
        notifyAllPlayers();
        return true;
    }

    /**
     * Initialize the game.
     * Pay attention to the order of the initialization of instances to avoid potential null pointer exception.
     *
     * @param matchID
     * @param userName
     * @param playerController
     */
    public static boolean joinGame(int matchID, String userName, PlayerController playerController) {
        if (GameManager.gameCleaner()) {
            System.out.println("Server cleaned");
        }

        synchronized (GameManager.playerMatchMap) {
            synchronized (GameManager.matchMap) {
                if (joinGameHelper(matchID, userName, playerController)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param matchID
     * @param userName
     * @param playerController
     */
    private static boolean joinGameHelper(int matchID, String userName, PlayerController playerController) {
        if (!GameManager.matchMap.containsKey(matchID)) {
            //System.out.println("Server >  The specified match does not exist.");
            GameManager.sendReply(playerController, ServerMessage.FindM_No.value());
            return false;
        }

        if (GameManager.matchMap.get(matchID).gameState == GameState.GameGoing) {
            if (!GameManager.playerMatchMap.containsKey(userName)) {
                //System.out.println("Message from the server: the player not exists in any match.");
                GameManager.sendReply(playerController, ServerMessage.PExists_No.value());
                return false;
            } else {
                if (!GameManager.matchMap.get(matchID).addPlayer(playerController.getPlayer())) {
                    //System.out.println("Message from the server: the match is full.");
                    GameManager.sendReply(playerController, ServerMessage.FullM.value());
                    return false;
                }
            }
            //if the match is not started, the player join the match
        } else if (GameManager.matchMap.get(matchID).gameState == GameState.WaitingPlayers) {
            GameManager.sendReply(playerController, ServerMessage.FindM_Ok.value());
            if (!GameManager.matchMap.get(matchID).addPlayer(playerController.getPlayer())) {
                //System.out.println("Message from the server: the match is full.");
                GameManager.sendReply(playerController, ServerMessage.FullM.value());
                return false;
            }
            updatePlayersGlobalView();
            notifyAllPlayers();
            return true;
        }
        return false;

    }

    /**
     * @param userName
     * @param createMatchRequestCount
     * @param playerNum
     * @param playerController
     */
    public static boolean createMatch(String userName, Integer createMatchRequestCount, int playerNum, PlayerController playerController) {
        if (GameManager.gameCleaner()) {
            System.out.println("Server cleaned");
        }

        synchronized (GameManager.playerMatchMap) {
            synchronized (GameManager.matchMap) {
                if (createMatchHelper(userName, createMatchRequestCount, playerNum, playerController)) {
                    //System.out.println("Message from the server: the match is created.");
                    GameManager.sendReply(playerController, ServerMessage.CreateM_Ok.value());
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * @param userName
     * @param createMatchRequestCount
     * @param playerNum
     * @param playerController
     */
    private static boolean createMatchHelper(String userName, Integer createMatchRequestCount, int playerNum, PlayerController playerController) {
        if (GameManager.playerMatchMap.containsKey(userName) && createMatchRequestCount == 0) {
            /*System.out.println("Message from the server: the player already exists in a match. " +
                    "Create a new match will cause the player leave the current match." +
                    "Do you want to continue?");*/
            GameManager.sendReply(playerController, ServerMessage.PExists.value());
            createMatchRequestCount = 1;
        } else if (GameManager.playerMatchMap.containsKey(userName) && createMatchRequestCount == 1) {
            createMatchRequestCount = 0;
            if (GameManager.createMatch(playerNum, playerController)) {
                VirtualViewHelper.virtualizeMatchMap();
                updatePlayersGlobalView();
                notifyAllPlayers();
                return true;
            } else {
                //System.out.println("Exceeded players number limit. Try again.");
                GameManager.sendReply(playerController, ServerMessage.PExceed.value());

            }
            //TODO:player leave the current match

        } else if (!GameManager.playerMatchMap.containsKey(userName)) {
            if (GameManager.createMatch(playerNum, playerController)) {
                VirtualViewHelper.virtualizeMatchMap();
                updatePlayersGlobalView();
                notifyAllPlayers();
                return true;
            } else {
                //System.out.println("Exceeded players number limit. Try again.");
                GameManager.sendReply(playerController, ServerMessage.PExceed.value());
            }


        }
        return false;
    }

    public static boolean removePlayerFromMatch(PlayerController ctrl, int matchID) {
        if (GameManager.playerMatchMap.containsKey(ctrl.getPlayer().getNickname())) {
            synchronized (GameManager.matchMap) {
                GameManager.matchMap.get(matchID).removePlayer(ctrl.getPlayer());
                //TODO: add a method that check if the match is close then delete instance
                if (GameManager.matchMap.get(matchID).gameState == GameState.Closed) {
                    GameManager.matchMap.remove(matchID);
                    VirtualViewHelper.virtualizeMatchMap();
                }
                updatePlayersGlobalView();
            }

            return true;
        }
        return false;
    }

    /**
     * This method is called when the CLIENT want to EXIT The GAME.
     * The existence of the player is cancelled from the GAME.
     * --Note: Before calling this method, removePlayerFromMatch() was already called.
     *
     * @param ctrl playerController
     * @return true if the operation was successful, otherwise false
     */
    public static boolean cancelPlayer(PlayerController ctrl) {
        if (GameManager.players.contains(ctrl.getPlayer())) {
            GameManager.sendReply(ctrl, SC.WHITE_BB + "\nServer > Game Closed" + SC.RST);
            GameManager.players.remove(GameManager.players.indexOf(ctrl.getPlayer()));
            return true;
        }
        return false;
    }

    public static boolean selectCell(int row, int col, PlayerController playerController) throws ServerNotActiveException {
        return checkPlayerActionPhase(playerController) && playerController.selectCell(row, col);
    }

    public static boolean confirmSelection(PlayerController playerController) {
        return checkPlayerActionPhase(playerController) && playerController.callEndSelection();
    }

    public static boolean insertInColumn(int colNum, PlayerController playerController) throws ServerNotActiveException {
        return checkPlayerActionPhase(playerController) && playerController.tryToInsert(colNum);
    }

    public static boolean deselectCards(PlayerController playerController) {
        return checkPlayerActionPhase(playerController) && playerController.clearSelectedCards();
    }

    public static boolean sortHand(int pos1, int pos2, PlayerController playerController) {
        return checkPlayerActionPhase(playerController) && playerController.changeHandOrder(pos1, pos2);
    }

    public static boolean leaveMatch(PlayerController playerController) {
        if (GameController.removePlayerFromMatch(playerController, playerController.getPlayer().getMatch().matchID)) {
            CommunicationController.instance.sendMessageToClient("Server > Leaving Room...", playerController);
            CommunicationController.instance.notifyGoToMenu(playerController);
            updatePlayersGlobalView();
            notifyAllPlayers();
            return true;
        }
        return false;
    }

    public static boolean exitGame(PlayerController playerController) {
        if (playerController.getPlayer().getMatch() != null) {

            GameController.removePlayerFromMatch(playerController, playerController.getPlayer().getMatch().matchID);
        }
        //When Player Exit The Game, every info abound the Player is cancelled
        if (GameController.cancelPlayer(playerController)) {
            return true;
        }

        return false;
    }

    public static String getVirtualView(PlayerController playerController) {
        return playerController.getPlayer().getMatch().getJSONVirtualView();
    }

    /**
     * RMI only
     *
     * @param callBack         the client callback
     * @param playerController the player controller
     */
    public static void registerCallBack(IClientCallBack callBack, PlayerController playerController) {

        playerController.clientInput.callBack = callBack;
        GameManager.client_connected++;
    }


    public static boolean endTurn(PlayerController playerController) {
        if (checkPlayerActionPhase(playerController)) {
            playerController.callEndInsertion();
            return true;
        }
        return false;
    }

    public static boolean changeMatchSeats(int newMaxSeats, PlayerController playerController) {
        Player p = playerController.getPlayer();
        synchronized (GameManager.playerMatchMap) {
            if (GameManager.playerMatchMap.containsKey(p.getNickname())
                    && p.getMatch().matchID == (GameManager.playerMatchMap.get(p.getNickname()))
                    && p.getMatch().changeSeats(p, newMaxSeats)) {
                VirtualViewHelper.virtualizeMatchMap();
                updatePlayersGlobalView();
                notifyAllPlayers();
                return true;
            }
        }
        return false;

    }

    public static boolean changeInsertLimit(int newLimit, PlayerController playerController) {
        Player p = playerController.getPlayer();
        synchronized (GameManager.playerMatchMap) {
            if (GameManager.playerMatchMap.containsKey(p.getNickname())
                    && p.getMatch().matchID == (GameManager.playerMatchMap.get(p.getNickname()))) {
                if (p.getMatch().admin.equals(p)) {
                    //Limit changed for the whole server
                    Shelf.STD_LIMIT = newLimit;
                    synchronized (GameManager.players) {
                        for (Player player : GameManager.players) {
                            if (player.getController() != null && !player.getStatus().equals(UserStatus.Offline)) {
                                CommunicationController.instance.sendMessageToClient(SC.YELLOW + "\nServer > Insertion Limit changed to: " + newLimit + SC.RST, player.getController());
                                CommunicationController.instance.notifyUpdate(player.getController(), 1000);
                            }
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }


    public static boolean forwardPublicMessage(String message, PlayerController ctrl, boolean live) {
        synchronized (GameManager.playerMatchMap) {
            if (ctrl.getPlayer().getMatch() == null || !GameManager.playerMatchMap.containsKey(ctrl.getPlayer().getNickname())) {
                return false;
            }
        }
        //Ensures that the player is in a match
        Player sender = ctrl.getPlayer();
        sender.getMatch().chatManager.handlePublicChatMessage(sender, message);
        String formalMessage = "\n" + sender.getNickname() + " says > " + message;
        VirtualViewHelper.virtualizePublicChat(sender.getMatch(), sender.getMatch().chatManager.publicChatMessages);
        sender.getMatch().updatePlayersPublicChats();
        sender.getMatch().sendPublicChatNotification(formalMessage, sender.getNickname());
        return true;
    }

    public static boolean forwardPrivateMessage(String message, String receiver, PlayerController ctrl, boolean live) {
        synchronized (GameManager.players) {
            if (!GameManager.players.contains(ctrl.getPlayer())) {
                return false;
            }
        }
        Player sender = ctrl.getPlayer();
        if (sender.getNickname().equals(receiver) || ServerChatManager.isOnline(receiver) == null) {
            return false;
        }
        ServerChatManager.handlePrivateChatMessage(ctrl.getPlayer(), receiver, message);
        String formalMessage = "\n" + sender.getNickname() + " whispers > " + message;
        VirtualViewHelper.virtualizePrivateChats(ServerChatManager.getPrivateChats());
        VirtualViewHelper.virtualizeChatMap(ServerChatManager.getChatMap());
        //DEBUG
        VirtualViewHelper.printJSON();
        updatePlayersGlobalView();
        Player p_receiver = ServerChatManager.isOnline(receiver);
        GameManager.sendChatNotification(p_receiver.getController(), formalMessage);

        return true;
    }

    public static void updatePlayersGlobalView() {
        synchronized (GameManager.players) {
            for (Player p : GameManager.players) {
                if (!p.getStatus().equals(UserStatus.Offline))
                    CommunicationController.instance.sendServerVirtualView(VirtualViewHelper.convertServerVirtualViewToJSON(), p.getController());
            }
        }
    }

    public static void notifyAllPlayers() {
        synchronized (GameManager.players) {
            for (Player p : GameManager.players) {
                if (!p.getStatus().equals(UserStatus.Offline))
                    CommunicationController.instance.notifyUpdate(p.getController(), 0);
            }
        }
    }
}
