package org.am21.model;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {
    public static List<Player> players;

    public PlayerManager(){
        players = new ArrayList<Player>();
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

}
