package org.am21.model;

import org.am21.controller.GameController;
import org.am21.controller.PlayerController;
import org.am21.model.enumer.ServerMessage;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameManager {
    public static boolean SERVER_COMM=true;

    public static GameManager game;
    //Key: player name, Value: match id
    public static final HashMap<String, Integer> playerMatchMap = new HashMap<String, Integer>();
    public static final List<Match> matchList = new ArrayList<Match>();
    public static final List<Player> players = new ArrayList<>();

    //TODO: for testing
    public static int client_connected=0;

    public GameManager(GameController controller) {

    }
    public int getNumPlayers() {
        return players.size();
    }
    public void addPlayer(Player player){
        players.add(player);
    }
    public void removePlayer(Player player){
        players.remove(player);
    }

    public static boolean createMatch(int playerNum, PlayerController playerController) {
        synchronized (matchList) {
            if (playerNum < 2 || playerNum > 4) {
                return false;
            }

            Match match = new Match(playerNum);
            matchList.add(match);
            match.matchID = matchList.indexOf(match);
            match.addPlayer(playerController.getPlayer());
            return true;
        }
    }

    /**
     * function to call server message
     * @param pc PlayerController
     * @param m ServerMessage
     */
    public static void sendCommunication(PlayerController pc, ServerMessage m){
        if(SERVER_COMM) {
            try {
                pc.clientInput.callBack.sendMessageToClient(m.value());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void sendTextCommunication(PlayerController pc, String m){
        if(SERVER_COMM) {
            try {
                pc.clientInput.callBack.sendMessageToClient(m);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

