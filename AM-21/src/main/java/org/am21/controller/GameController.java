package org.am21.controller;

import org.am21.model.GameManager;
import org.am21.model.enumer.GameState;
import org.am21.model.enumer.ServerMessage;

import java.rmi.RemoteException;

public class GameController {

    public GameController() throws RemoteException {
    }

    /**
     * Initialize the game.
     * Pay attention to the order of the initialization of instances to avoid potential null pointer exception.
     * @param matchID
     * @param userName
     * @param playerController
     */
    public static void joinGame(int matchID, String userName, PlayerController playerController) {
        synchronized (GameManager.playerMatchMap) {
            synchronized (GameManager.matchList) {
                joinGameHelper(matchID, userName, playerController);
            }
        }
    }

    /**
     *
     * @param matchID
     * @param userName
     * @param playerController
     */
    private static void joinGameHelper(int matchID, String userName, PlayerController playerController) {
        if (GameManager.matchList.get(matchID) == null) {
            //System.out.println("Server >  The specified match does not exist.");
            sendMessage(playerController,ServerMessage.FindM_No);
            return;
        }

        if (GameManager.matchList.get(matchID).gameState == GameState.GameGoing) {
            if (!GameManager.playerMatchMap.containsKey(userName)) {
                //System.out.println("Message from the server: the player not exists in any match.");
                sendMessage(playerController,ServerMessage.PExists_No);
            } else {
                if (!GameManager.matchList.get(matchID).addPlayer(playerController.getPlayer())) {
                    //System.out.println("Message from the server: the match is full.");
                    sendMessage(playerController,ServerMessage.FullM);
                }
            }
            //if the match is not started, the player join the match
        } else if (GameManager.matchList.get(matchID).gameState == GameState.WaitingPlayers) {
            if (!GameManager.matchList.get(matchID).addPlayer(playerController.getPlayer())) {
                //System.out.println("Message from the server: the match is full.");
                sendMessage(playerController,ServerMessage.FullM);
            }
        }

        sendMessage(playerController,ServerMessage.FindM_Ok);
    }

    /**
     *
     * @param userName
     * @param createMatchRequestCount
     * @param playerNum
     * @param playerController
     */
    public static void createMatch(String userName, Integer createMatchRequestCount, int playerNum, PlayerController playerController) {
        synchronized (GameManager.playerMatchMap) {
            synchronized (GameManager.matchList) {
                createMatchHelper(userName, createMatchRequestCount, playerNum, playerController);
                //System.out.println("Message from the server: the match is created.");
                sendMessage(playerController,ServerMessage.CreateM_Ok);
            }
        }
    }

    /**
     *
     * @param userName
     * @param createMatchRequestCount
     * @param playerNum
     * @param playerController
     */
    private static void createMatchHelper(String userName, Integer createMatchRequestCount, int playerNum, PlayerController playerController) {
        if (GameManager.playerMatchMap.containsKey(userName) && createMatchRequestCount == 0) {
            /*System.out.println("Message from the server: the player already exists in a match. " +
                    "Create a new match will cause the player leave the current match." +
                    "Do you want to continue?");*/
            sendMessage(playerController,ServerMessage.PExists);
            createMatchRequestCount = 1;
        } else if (GameManager.playerMatchMap.containsKey(userName) && createMatchRequestCount == 1) {
            createMatchRequestCount = 0;
            GameManager.createMatch(playerNum, playerController);
            sendMessage(playerController,ServerMessage.CreateM_Ok);
            //TODO:player leave the current match
        } else if (!GameManager.playerMatchMap.containsKey(userName)) {
            GameManager.createMatch(playerNum, playerController);
            sendMessage(playerController,ServerMessage.CreateM_Ok);
        }
    }

    public static boolean removePlayerFromMatch(PlayerController ctrl,int matchID){
        if(GameManager.playerMatchMap.containsKey(ctrl.getPlayer().getNickname())){
                GameManager.matchList.get(matchID).removePlayer(ctrl.getPlayer());
                return true;
        }
        return false;
    }

    /**
     * function to call server message
     * @param pc PlayerController
     * @param m ServerMessage
     */
    private static void sendMessage(PlayerController pc, ServerMessage m){
        try {
            pc.clientInput.callBack.sendMessageToClient(String.valueOf(m));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


}
