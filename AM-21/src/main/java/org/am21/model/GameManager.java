package org.am21.model;

import org.am21.controller.CommunicationController;
import org.am21.controller.GameController;
import org.am21.controller.PlayerController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameManager {
    public static boolean SERVER_COMM = true;

    public static GameManager game;
    //Key: player name, Value: match id
    public static final HashMap<String, Integer> playerMatchMap = new HashMap<String, Integer>();
    public static final List<Match> matchList = new ArrayList<Match>();
    public static final List<Player> players = new ArrayList<>();

    //TODO: for testing
    public static int client_connected = 0;

    public GameManager(GameController controller) {

    }

    public int getNumPlayers() {
        return players.size();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
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
            match.admin = playerController.getPlayer();
            match.virtualView.setAdmin(playerController.getPlayer().getNickname());
            match.virtualView.setMatchID(match.matchID);
            match.virtualView.setMaxSeats(playerNum);
            match.addPlayer(playerController.getPlayer());

            return true;
        }
    }

    /**
     * This method check if there is a nickname is already picked by someone else.
     *
     * @param name
     * @return
     */
    public static boolean checkNameSake(String name) {
        synchronized (GameManager.players) {
            for (Player p : players) {
                if (name.equals(p.getNickname())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Whenever the server has to reply to a player action with a pre-defined message
     *
     * @param pc PlayerController
     * @param m  ServerMessage
     */
    public static void sendReply(PlayerController pc, String m) {
        if (SERVER_COMM) {
            CommunicationController.instance.sendMessageToClient(m,pc);
        }
    }


    public static void sendChatNotification(PlayerController pc, String m){
        if(SERVER_COMM){
            CommunicationController.instance.sendChatNotification(m,pc);
        }
    }

    public static void notifyUpdate(PlayerController ctrl,int milliseconds){
        CommunicationController.instance.notifyUpdate(ctrl,milliseconds);
    }

}

