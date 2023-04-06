package org.am21.model;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {
    public static final List<Player> players = new ArrayList<>();

    public PlayerManager(){
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
