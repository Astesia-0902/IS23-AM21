package org.am21.controller;

import org.am21.model.GameManager;
import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.model.enumer.GameState;
import org.am21.model.enumer.SC;
import org.am21.model.enumer.ServerMessage;
import org.am21.model.enumer.UserStatus;
import org.am21.model.items.Shelf;
import org.am21.networkRMI.IClientCallBack;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public class GameController {

    public GameController() throws RemoteException {
    }

    public static boolean checkPlayerActionPhase(String username) throws ServerNotActiveException {
        synchronized (GameManager.playerMatchMap) {
            synchronized (GameManager.matchList) {
                return GameManager.playerMatchMap.containsKey(username) &&
                        username.equals(GameManager.matchList.
                                get(GameManager.playerMatchMap.get(username)).currentPlayer.getNickname());
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
        if (GameManager.checkNameSake(username)) {
            //TODO:send message to client when client don't have a PlayerController
            return false;
        }

        playerController.getPlayer().setNickname(username);

        synchronized (GameManager.players) {
            if (!GameManager.players.contains(playerController.getPlayer())) {
                GameManager.players.add(playerController.getPlayer());
            }
        }
        CommunicationController.instance.sendMessageToClient(ServerMessage.Login_Ok.value() + username,false , playerController);
        //DEBUG
        System.out.println(username + " joined the game");
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
        synchronized (GameManager.playerMatchMap) {
            synchronized (GameManager.matchList) {
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
        if (GameManager.matchList.size() < (matchID + 1) || GameManager.matchList.get(matchID) == null) {
            //System.out.println("Server >  The specified match does not exist.");
            GameManager.sendReply(playerController, ServerMessage.FindM_No,false);
            return false;
        }

        if (GameManager.matchList.get(matchID).gameState == GameState.GameGoing) {
            if (!GameManager.playerMatchMap.containsKey(userName)) {
                //System.out.println("Message from the server: the player not exists in any match.");
                GameManager.sendReply(playerController, ServerMessage.PExists_No,false);
                return false;
            } else {
                if (!GameManager.matchList.get(matchID).addPlayer(playerController.getPlayer())) {
                    //System.out.println("Message from the server: the match is full.");
                    GameManager.sendReply(playerController, ServerMessage.FullM,false);
                    return false;
                }
            }
            //if the match is not started, the player join the match
        } else if (GameManager.matchList.get(matchID).gameState == GameState.WaitingPlayers) {
            GameManager.sendReply(playerController, ServerMessage.FindM_Ok,false);
            if (!GameManager.matchList.get(matchID).addPlayer(playerController.getPlayer())) {
                //System.out.println("Message from the server: the match is full.");
                GameManager.sendReply(playerController, ServerMessage.FullM,false);
                return false;
            }

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
        synchronized (GameManager.playerMatchMap) {
            synchronized (GameManager.matchList) {
                if (createMatchHelper(userName, createMatchRequestCount, playerNum, playerController)) {
                    //System.out.println("Message from the server: the match is created.");
                    GameManager.sendReply(playerController, ServerMessage.CreateM_Ok,false);
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
            //TODO:
            GameManager.sendReply(playerController, ServerMessage.PExists,false);
            createMatchRequestCount = 1;
        } else if (GameManager.playerMatchMap.containsKey(userName) && createMatchRequestCount == 1) {
            createMatchRequestCount = 0;
            if (GameManager.createMatch(playerNum, playerController)) {
                return true;
            } else {
                //System.out.println("Exceeded players number limit. Try again.");
                GameManager.sendReply(playerController, ServerMessage.PExceed,false);

            }
            //TODO:player leave the current match

        } else if (!GameManager.playerMatchMap.containsKey(userName)) {
            if (GameManager.createMatch(playerNum, playerController)) {

                return true;
            } else {
                //System.out.println("Exceeded players number limit. Try again.");
                GameManager.sendReply(playerController, ServerMessage.PExceed,false);
            }


        }
        return false;
    }

    public static boolean removePlayerFromMatch(PlayerController ctrl, int matchID) {
        if (GameManager.playerMatchMap.containsKey(ctrl.getPlayer().getNickname())) {
            GameManager.matchList.get(matchID).removePlayer(ctrl.getPlayer());
            //TODO: add a method that check if the match is close then delete instance
            return true;
        }
        return false;
    }

    /**
     * This method is called when the CLIENT want to EXIT The GAME.
     * The existence of the player is cancelled from the GAME.
     * --Note: Before calling this method, removePlayerFromMatch() was already called.
     *
     * @param ctrl
     * @return
     */
    public static boolean cancelPlayer(PlayerController ctrl) {
        if (GameManager.players.contains(ctrl.getPlayer())) {
            GameManager.sendTextReply(ctrl, SC.WHITE_BB + "\nServer > Game Closed" + SC.RST,false);
            GameManager.players.remove(GameManager.players.indexOf(ctrl.getPlayer()));
            return true;
        }
        return false;
    }

    public static boolean selectCell(int row, int col, PlayerController playerController) throws ServerNotActiveException {
        return checkPlayerActionPhase(playerController.getPlayer().getNickname()) && playerController.selectCell(row, col);
    }

    public static boolean confirmSelection(PlayerController playerController) throws ServerNotActiveException {
        return checkPlayerActionPhase(playerController.getPlayer().getNickname()) && playerController.callEndSelection();
    }

    public static boolean insertInColumn(int colNum, PlayerController playerController) throws ServerNotActiveException {
        return checkPlayerActionPhase(playerController.getPlayer().getNickname()) && playerController.tryToInsert(colNum);
    }

    public static boolean deselectCards(PlayerController playerController) throws ServerNotActiveException {
        return checkPlayerActionPhase(playerController.getPlayer().getNickname()) && playerController.clearSelectedCards();
    }

    public static boolean sortHand(int pos1, int pos2, PlayerController playerController) throws ServerNotActiveException {
        return checkPlayerActionPhase(playerController.getPlayer().getNickname()) && playerController.changeHandOrder(pos1, pos2);
    }

    public static boolean leaveMatch(PlayerController playerController) {
        if (GameController.removePlayerFromMatch(playerController, playerController.getPlayer().getMatch().matchID)) {
            CommunicationController.instance.sendMessageToClient("Server > Leaving Room...",true , playerController);
            CommunicationController.instance.notifyGoToMenu(playerController);
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

    public static boolean sendChatMessage(String message, PlayerController playerController) throws RemoteException {
        Player p = playerController.getPlayer();
        if (p.getMatch() != null) {
            p.getMatch().chatManager.sendChat(message, p.getNickname());
            return true;
        }
        return false;
    }

    public static boolean sendPlayerMessage(String message, String receiver, PlayerController playerController,boolean refresh) {
        String sender = playerController.getPlayer().getNickname();
        synchronized (GameManager.players) {
            for (Player p : GameManager.players) {
                if (p.getNickname().equals(receiver)) {
                    GameManager.sendTextReply(p.getController(), "\n------------------------------------------",refresh);
                    GameManager.sendTextReply(p.getController(), sender + "[!] > \"" + message + "\"",refresh);
                    GameManager.sendTextReply(p.getController(), "------------------------------------------\n",refresh);

                    return true;
                }
            }

        }
        return false;
    }

    public static void printOnlinePlayers(PlayerController playerController) {
        String message = "";
        message += ServerMessage.ListP.value() + "\n";
        synchronized (GameManager.players) {
            for (Player p : GameManager.players) {
                if (p.getStatus() == UserStatus.Online || p.getStatus() == UserStatus.GameMember) {
                    message += ("[" + p.getNickname() + " | " + p.getStatus() + " ] \n");

                }
            }
        }
        CommunicationController.instance.sendMessageToClient(message,false , playerController);
    }

    public static void printMatchList(PlayerController playerController) {
        String message = "";
        message += "Match List:\n";
        synchronized (GameManager.matchList) {
            if (GameManager.matchList.size() > 0) {
                for (Match m : GameManager.matchList) {
                    message += ("[ID: " + m.matchID + " | " + m.gameState + " | Players: (" + m.playerList.size() + "/" + m.maxSeats + ")]\n");
                }
            }
        }
        CommunicationController.instance.sendMessageToClient(message,false , playerController);
    }

    public static boolean endTurn(PlayerController playerController) throws ServerNotActiveException {
        if (checkPlayerActionPhase(playerController.getPlayer().getNickname())) {
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
                            if (player.getController() != null && player.getStatus()!=UserStatus.Offline) {
                                CommunicationController.instance.sendMessageToClient(SC.YELLOW + "\nServer > Insertion Limit changed to: " + newLimit + SC.RST,true , player.getController());
                            }
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
