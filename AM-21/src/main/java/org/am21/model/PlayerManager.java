//package org.am21.model;
//
//import org.am21.controller.GameController;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class PlayerManager {
//    private final Match matchInstance;
//    public List<Player> players;
//    private Player firstCompletedPlayer;
//    private Player currentPlayer;
//    private int numPlayers;
//
//    public PlayerManager(Match matchInstance){
//        this.matchInstance = matchInstance;
//        this.numPlayers = 0;
//        this.currentPlayer = null;
//        this.firstCompletedPlayer = null;
//        this.players = new ArrayList<>();
//
//    }
//
//    public Match getMatchInstance() {
//        return matchInstance;
//    }
//
//    public int getNumPlayers() {
//        return numPlayers;
//    }
//
//    public void setNumPlayers(int numPlayers) {
//        this.numPlayers = numPlayers;
//    }
//
//    public void addPlayer(Player player){
//        players.add(player);
//        setNumPlayers(getNumPlayers()+1);
//    }
//    public void removePlayer(Player player){
//        players.remove(player);
//    }
//
//    public void setFirstCompletedPlayer(Player player){
//        firstCompletedPlayer = player;
//    }
//
//    public Player getFirstCompletedPlayer() {
//        return firstCompletedPlayer;
//    }
//
//    public Player getCurrentPlayer() {
//        return currentPlayer;
//    }
//
//    public void setCurrentPlayer(Player currentPlayer) {
//        this.currentPlayer = currentPlayer;
//    }
//}
