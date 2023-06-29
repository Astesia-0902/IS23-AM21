package org.am21.controller;

import org.am21.model.GameManager;
import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.model.chat.ServerChatManager;
import org.am21.model.enumer.GameState;
import org.am21.model.enumer.SC;
import org.am21.model.enumer.ServerMessage;
import org.am21.model.enumer.UserStatus;
import org.am21.model.items.Shelf;
import org.am21.utilities.VirtualViewHelper;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public class GameController {

    public GameController() throws RemoteException {
    }

    /**
     * Check if the player is the current player in the correspondent match
     * @param playerController player's player controller
     * @return true if the player is the current player
     */
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

        if (GameManager.checkNameReconnection(username)) {
            Match match = GameManager.matchMap.get(GameManager.playerMatchMap.get(username));
            for (Player player : match.playerList) {
                if (player.getNickname().equals(username)) {
                    player.setController(null);
                    playerController.setPlayer(player);
                    player.setController(playerController);
                    playerController.reconnectPlayer();
                    updatePlayersGlobalView();
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
        CommunicationController.instance.sendMessageToClient("Hi "+ username, playerController);
        GameManager.serverLog(username + " joined the game");
        updatePlayersGlobalView();
        notifyAllPlayers();
        return true;
    }

    /**
     * Initialize the game.
     * Pay attention to the order of the initialization of instances to avoid potential null pointer exception.
     *
     * @param matchID match id
     * @param userName player nickname
     * @param playerController player controller
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
     * TODO
     * @param matchID match id
     * @param userName player nickname
     * @param playerController player's controller
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
     * TODO
     * @param userName player's nickname
     * @param createMatchRequestCount number of match creation request
     * @param playerNum max number of players for the match
     * @param playerController player's controller
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
     * TODO
     * @param userName player's nickname
     * @param createMatchRequestCount number of match creation request
     * @param playerNum max number of players for the match
     * @param playerController player's controller
     */
    private static boolean createMatchHelper(String userName, Integer createMatchRequestCount, int playerNum, PlayerController playerController) {
        if (GameManager.playerMatchMap.containsKey(userName) && createMatchRequestCount == 0) {
            //TODO: Enter menu when already in match. Not happening anymore with reconnection
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

    /**
     * Remove player from a match
     * @param ctrl player controller
     * @param matchID match id
     * @return true if the operation is successful, otherwise false
     */
    public static boolean removePlayerFromMatch(PlayerController ctrl, int matchID) {
        if (GameManager.playerMatchMap.containsKey(ctrl.getPlayer().getNickname())) {
            synchronized (GameManager.matchMap) {
                GameManager.matchMap.get(matchID).removePlayer(ctrl.getPlayer());
                if (GameManager.matchMap.get(matchID).gameState.equals(GameState.Closed)) {
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

    /**
     * Method to call selectCell from Player controller
     * @param row row
     * @param col column
     * @param playerController player controller
     * @return true if the player is current player and selection is successful,otherwise false
     */
    public static boolean selectCell(int row, int col, PlayerController playerController) {
        return checkPlayerActionPhase(playerController) && playerController.selectCell(row, col);
    }

    /**
     * Method to call player Controller method which it will confirm the selected items
     * @param playerController player controller
     * @return true if the player is current player and selection is successful,otherwise false
     */
    public static boolean confirmSelection(PlayerController playerController) {
        return checkPlayerActionPhase(playerController) && playerController.callEndSelection();
    }

    /**
     * insert cards in the shelf
     * @param colNum col number
     * @param playerController pc instance
     * @return true if succeed false otherwise
     */
    public static boolean insertInColumn(int colNum, PlayerController playerController)  {
        return checkPlayerActionPhase(playerController) && playerController.tryToInsert(colNum);
    }

    /**
     * deselect card in the player hand
     * @param playerController pc instance
     * @return true if succeed false otherwise
     */
    public static boolean deselectCards(PlayerController playerController) {
        return checkPlayerActionPhase(playerController) && playerController.clearSelectedCards();
    }

    /**
     * change the position of cards in player hand
     * @param pos1 pos1
     * @param pos2 pos2
     * @param playerController pc instance
     * @return true if succeed false otherwise
     */
    public static boolean sortHand(int pos1, int pos2, PlayerController playerController) {
        return checkPlayerActionPhase(playerController) && playerController.changeHandOrder(pos1, pos2);
    }

    /**
     * leave the current match
     * @param playerController pc instance
     * @return true if succeed false otherwise
     */
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

    /**
     * Exit game
     * @param playerController pc instance
     * @return true if succeed false otherwise
     */
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

    /**
     * Get virtual view from the server
     * @param playerController pc instance
     * @return virtual view json
     */
    public static String getVirtualView(PlayerController playerController) {
        return playerController.getPlayer().getMatch().getJSONVirtualView();
    }

    /**
     * End player's turn
     * @param playerController player controller instance
     * @return true if succeed false otherwise
     */
    public static boolean endTurn(PlayerController playerController) {
        if (checkPlayerActionPhase(playerController)) {
            playerController.callEndInsertion();
            return true;
        }
        return false;
    }

    /**
     * This method allows the player who has the role of admin to change the maxim number of seats for the match, if th match has not started yet
     * @param newMaxSeats new number of max seats
     * @param playerController admin player controller
     * @return true if the operation is successful, otherwise false
     */
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


    /**
     * This method forward the group chat message to match chat manager
     * @param message message
     * @param ctrl player controller of the sender
     * @return true if the message is sent, false if the player is not in the match
     */
    public static boolean forwardPublicMessage(String message, PlayerController ctrl) {
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

    /**
     * This method forward a private message to a specific online player
     * @param message message
     * @param receiver receiver name
     * @param ctrl player controller of the sender
     * @return true if the message is sent, false if the receiver is not found or is not valid
     */
    public static boolean forwardPrivateMessage(String message, String receiver, PlayerController ctrl) {
        synchronized (GameManager.players) {
            if (!GameManager.players.contains(ctrl.getPlayer())) {
                return false;
            }
        }
        Player p_receiver = ServerChatManager.isOnline(receiver);
        Player sender = ctrl.getPlayer();
        if (sender.getNickname().equals(receiver) || p_receiver == null) {
            return false;
        }
        if(!ServerChatManager.handlePrivateChatMessage(ctrl.getPlayer(), receiver, message)){
            return false;
        }
        String formalMessage = "\n" + sender.getNickname() + " whispers > " + message;
        VirtualViewHelper.virtualizePrivateChats(ServerChatManager.getPrivateChats());
        VirtualViewHelper.virtualizeChatMap(ServerChatManager.getChatMap());
        updatePlayersGlobalView();
        GameManager.sendChatNotification(p_receiver.getController(), formalMessage);

        return true;
    }

    /**
     * Update all players Server Virtual View
     */
    public static void updatePlayersGlobalView() {
        synchronized (GameManager.players) {
            for (Player p : GameManager.players) {
                if (!p.getStatus().equals(UserStatus.Offline))
                    CommunicationController.instance.sendServerVirtualView(VirtualViewHelper.convertServerVirtualViewToJSON(), p.getController());
            }
        }
    }

    /**
     * Send a notification to all players fot update their interfaces
     */
    public static void notifyAllPlayers() {
        synchronized (GameManager.players) {
            for (Player p : GameManager.players) {
                if (!p.getStatus().equals(UserStatus.Offline))
                    CommunicationController.instance.notifyUpdate(p.getController(), 0);
            }
        }
    }
}
