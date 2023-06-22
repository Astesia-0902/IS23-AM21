package org.am21.model.virtualview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//1. The board map
//2. The list of shelves
//3. The list of player
//4. The score of each player
//5. The current player
//6. The current turn
//7. The current phase
//8. The current common goal and the personal goal of current player

/**
 * This class will be serialized and sent to the client
 */
public class VirtualView implements Serializable {


    private int matchID;
    private String admin;
    private int maxSeats;

    // These need update at the end of each turn
    private String[][] board;
    private List<Integer> commonGoalScores;
    private List<String> currentPlayerHand;
    private List<String[][]> shelves;
    private String currentPlayer;
    private boolean endGameToken;
    private List<Integer> scores;
    private List<Integer> hiddenPoints;
    private String[][] gameResults;
    //---------------------------------------
    public List<String> publicChat;
    //--------------------------------

    //Since we don't process the goal logic on the client side, we don't need to send the goal to the client.
    //We are going to use an integer to map the goal picture
    public List<String> commonGoals;
    //Need update if someone leaves the match
    public List<String> players;
    public List<Integer> personalGoals;

    public VirtualView() {
        this.matchID = -1;
        this.admin = "";
        this.maxSeats = -1;
        this.board = new String[1][1];
        this.commonGoalScores = new ArrayList<>();
        this.currentPlayerHand = new ArrayList<>();
        this.shelves = new ArrayList<>();
        this.currentPlayer = "";
        this.endGameToken = false;
        this.scores = new ArrayList<>();
        this.hiddenPoints = new ArrayList<>();
        this.gameResults = new String[1][1];
        this.publicChat = new ArrayList<>();
        this.commonGoals = new ArrayList<>();
        this.players = new ArrayList<>();
        this.personalGoals = new ArrayList<>();
    }






    public List<String> getPublicChat() {
        return publicChat;
    }

    public void setPublicChat(List<String> publicChat) {
        this.publicChat = publicChat;
    }



    public int getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    public List<Integer> getHiddenPoints() {
        return hiddenPoints;
    }

    public void setHiddenPoints(List<Integer> hiddenPoints) {
        this.hiddenPoints = hiddenPoints;
    }

    public boolean isEndGameToken() {
        return endGameToken;
    }

    public void setEndGameToken(boolean endGameToken) {
        this.endGameToken = endGameToken;
    }

    public List<String> getCurrentPlayerHand() {
        return currentPlayerHand;
    }

    public void setCurrentPlayerHand(List<String> currentPlayerHand) {
        this.currentPlayerHand = currentPlayerHand;
    }

    public List<Integer> getCommonGoalScores() {
        return commonGoalScores;
    }

    public void setCommonGoalScores(List<Integer> commonGoalScores) {
        this.commonGoalScores = commonGoalScores;
    }

    public int getMatchID() {
        return matchID;
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }


    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public List<Integer> getScores() {
        return scores;
    }

    public void setScores(List<Integer> scores) {
        this.scores = scores;
    }

    public List<String[][]> getShelves() {
        return shelves;
    }

    public void setShelves(List<String[][]> shelves) {
        this.shelves = shelves;
    }

    public List<Integer> getPersonalGoals() {
        return personalGoals;
    }

    public void setPersonalGoals(List<Integer> personalGoals) {
        this.personalGoals = personalGoals;
    }

    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    public List<String> getCommonGoals() {
        return commonGoals;
    }

    public void setCommonGoals(List<String> commonGoals) {
        this.commonGoals = commonGoals;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String[][] getGameResults() {
        return gameResults;
    }

    public void setGameResults(String[][] gameResults) {
        this.gameResults = gameResults;
    }

}
