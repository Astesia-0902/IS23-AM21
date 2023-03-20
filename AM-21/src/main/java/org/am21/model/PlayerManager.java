package org.am21.model;

import org.am21.controller.GameController;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {
    private final Match match;
    public List<Player> players;
    private Player firstToComplete;
    public Player playerTurn;
    private int numPlayers;
    public Player chairman;

    public int maxSeats;

    public PlayerManager(Match match){
        this.match = match;
        this.numPlayers = 0;
        this.playerTurn = null;
        this.firstToComplete = null;
        this.players = new ArrayList<Player>();
        this.maxSeats = maxSeats;

    }

    public Match getMatch() {
        return match;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public void addPlayer(Player player){
        players.add(player);
        setNumPlayers(getNumPlayers()+1);
    }
    public void removePlayer(Player player){
        players.remove(player);
    }

    public void setFirstToCompleter(Player player){
        firstToComplete = player;
    }

    public Player getFirstCompletedPlayer() {
        return firstToComplete;
    }

    public Player getPlayerTurner() {
        return playerTurn;
    }

    public void setPlayerTurn(Player nextPlayer) {
        this.playerTurn = nextPlayer;
    }

    public Player getChairman(){ return chairman;}
}
