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
import org.am21.networkRMI.IClientCallBack;
import org.am21.utilities.VirtualViewHelper;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.ArrayList;
import java.util.List;

public class GameController {

    public GameController() throws RemoteException {
    }

    public static boolean checkPlayerActionPhase(PlayerController playerController) {
        String username = playerController.getPlayer().getNickname();
        synchronized (GameManager.playerMatchMap) {
            synchronized (GameManager.matchList) {
                if (GameManager.playerMatchMap.containsKey(username) &&
                        username.equals(GameManager.matchList.
                                get(GameManager.playerMatchMap.get(username)).currentPlayer.getNickname())) {
                    return true;
                }
                GameManager.sendReply(playerController, ServerMessage.NotYourTurn);
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
        CommunicationController.instance.sendMessageToClient(ServerMessage.Login_Ok.value() + username, playerController);
        //DEBUG
        System.out.println(username + " joined the game");
        updatePlayersGlobalView();
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
            GameManager.sendReply(playerController, ServerMessage.FindM_No);
            return false;
        }

        if (GameManager.matchList.get(matchID).gameState == GameState.GameGoing) {
            if (!GameManager.playerMatchMap.containsKey(userName)) {
                //System.out.println("Message from the server: the player not exists in any match.");
                GameManager.sendReply(playerController, ServerMessage.PExists_No);
                return false;
            } else {
                if (!GameManager.matchList.get(matchID).addPlayer(playerController.getPlayer())) {
                    //System.out.println("Message from the server: the match is full.");
                    GameManager.sendReply(playerController, ServerMessage.FullM);
                    return false;
                }
            }
            //if the match is not started, the player join the match
        } else if (GameManager.matchList.get(matchID).gameState == GameState.WaitingPlayers) {
            GameManager.sendReply(playerController, ServerMessage.FindM_Ok);
            if (!GameManager.matchList.get(matchID).addPlayer(playerController.getPlayer())) {
                //System.out.println("Message from the server: the match is full.");
                GameManager.sendReply(playerController, ServerMessage.FullM);
                return false;
            }
            updatePlayersGlobalView();
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
        if(gameCleaner()){
            System.out.println("Server cleaned");
        }

        synchronized (GameManager.playerMatchMap) {
            synchronized (GameManager.matchList) {
                if (createMatchHelper(userName, createMatchRequestCount, playerNum, playerController)) {
                    //System.out.println("Message from the server: the match is created.");
                    GameManager.sendReply(playerController, ServerMessage.CreateM_Ok);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Control if there is any Match Closed, if so destroy it
     * @return
     */
    public static boolean gameCleaner(){
        synchronized (GameManager.matchList){
            List<Integer> toDoList = new ArrayList<>();
            for(Match m:GameManager.matchList){
                if(m.gameState.equals(GameState.Closed)){
                    toDoList.add(GameManager.matchList.indexOf(m));
                }
            }
            for (Integer x : toDoList){
                GameManager.matchList.remove(x);
            }
            if(toDoList.size()>0){
                return true;
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
            GameManager.sendReply(playerController, ServerMessage.PExists);
            createMatchRequestCount = 1;
        } else if (GameManager.playerMatchMap.containsKey(userName) && createMatchRequestCount == 1) {
            createMatchRequestCount = 0;
            if (GameManager.createMatch(playerNum, playerController)) {
                VirtualViewHelper.virtualizeMatchList();
                updatePlayersGlobalView();
                return true;
            } else {
                //System.out.println("Exceeded players number limit. Try again.");
                GameManager.sendReply(playerController, ServerMessage.PExceed);

            }
            //TODO:player leave the current match

        } else if (!GameManager.playerMatchMap.containsKey(userName)) {
            if (GameManager.createMatch(playerNum, playerController)) {
                VirtualViewHelper.virtualizeMatchList();
                updatePlayersGlobalView();
                return true;
            } else {
                //System.out.println("Exceeded players number limit. Try again.");
                GameManager.sendReply(playerController, ServerMessage.PExceed);
            }


        }
        return false;
    }

    public static boolean removePlayerFromMatch(PlayerController ctrl, int matchID) {
        if (GameManager.playerMatchMap.containsKey(ctrl.getPlayer().getNickname())) {
            synchronized (GameManager.matchList) {
                GameManager.matchList.get(matchID).removePlayer(ctrl.getPlayer());
                //TODO: add a method that check if the match is close then delete instance
                if (GameManager.matchList.get(matchID).gameState == GameState.Closed) {
                    GameManager.matchList.remove(matchID);
                    VirtualViewHelper.virtualizeMatchList();

                }
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
            GameManager.sendTextReply(ctrl, SC.WHITE_BB + "\nServer > Game Closed" + SC.RST);
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
            //CommunicationController.instance.notifyUpdate(playerController,0);
            updatePlayersGlobalView();
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
        CommunicationController.instance.sendMessageToClient(message, playerController);
    }

    //TODO: to clean
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
        CommunicationController.instance.sendMessageToClient(message, playerController);
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
                                CommunicationController.instance.notifyUpdate(player.getController(),1000);
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

    public static boolean forwardPrivateMessage(String message,String receiver, PlayerController ctrl,boolean live) {
        synchronized (GameManager.players){
            if(!GameManager.players.contains(ctrl.getPlayer()) ){
                return false;
            }
        }
        Player sender = ctrl.getPlayer();
        if(sender.getNickname().equals(receiver) || ServerChatManager.isOnline(receiver)==null){
            return false;
        }
        ServerChatManager.handlePrivateChatMessage(ctrl.getPlayer(),receiver,message);
        String formalMessage = "\n" + sender.getNickname() + " whispers > " + message;
        VirtualViewHelper.virtualizePrivateChats(ServerChatManager.getPrivateChats());
        VirtualViewHelper.virtualizeChatMap(ServerChatManager.getChatMap());
        //DEBUG
        VirtualViewHelper.printJSON();
        updatePlayersGlobalView();
        Player p_receiver=ServerChatManager.isOnline(receiver);
        GameManager.sendChatNotification(p_receiver.getController(),formalMessage);

        return true;
    }

    public static void updatePlayersGlobalView(){
        synchronized (GameManager.players){
            for (Player p:GameManager.players){
                if(!p.getStatus().equals(UserStatus.Offline))
                    CommunicationController.instance.sendServerVirtualView(VirtualViewHelper.convertServerVirtualViewToJSON(),p.getController());

                    CommunicationController.instance.notifyUpdate(p.getController(),0);
            }
        }
    }
}
