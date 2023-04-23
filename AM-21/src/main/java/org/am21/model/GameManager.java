package org.am21.model;

import org.am21.controller.GameController;
import org.am21.controller.PlayerController;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.am21.model.enumer.ServerMessage.PExceed;

public class GameManager {
    public static GameManager game;
    //Key: player name, Value: match id
    public static final HashMap<String, Integer> playerMatchMap = new HashMap<String, Integer>();
    public static final List<Match> matchList = new ArrayList<Match>();
    public static final List<Player> players = new ArrayList<>();

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

    public static void createMatch(int playerNum, PlayerController playerController) {
        synchronized (matchList) {
            if (playerNum < 2 || playerNum > 4) {

                //System.out.println("Exceeded players number limit. Try again.");
                try {
                    playerController.clientInput.callBack.sendMessageToClient(String.valueOf(PExceed));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                return;
            }

            Match match = new Match(playerNum);
            matchList.add(match);
            match.matchID = matchList.indexOf(match);
            match.addPlayer(playerController.getPlayer());
        }
    }

}

