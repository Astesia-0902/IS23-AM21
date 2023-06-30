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

    /**
     * Constructor
     */
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

    /**
     * This method is used to get the public chat
     *
     * @return the public chat
     */
    public List<String> getPublicChat() {
        return publicChat;
    }

    /**
     * This method is used to set the public chat
     *
     * @param publicChat the public chat
     */
    public void setPublicChat(List<String> publicChat) {
        this.publicChat = publicChat;
    }

    /**
     * This method is used to get the max seats
     *
     * @return the max seats
     */
    public int getMaxSeats() {
        return maxSeats;
    }

    /**
     * This method is used to set the max seats
     *
     * @param maxSeats the max seats
     */
    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    /**
     * This method is used to get the hidden points
     *
     * @return the hidden points
     */
    public List<Integer> getHiddenPoints() {
        return hiddenPoints;
    }

    /**
     * This method is used to set the hidden points
     *
     * @param hiddenPoints the hidden points
     */
    public void setHiddenPoints(List<Integer> hiddenPoints) {
        this.hiddenPoints = hiddenPoints;
    }

    /**
     * This method is used to check if the end game token is taken
     *
     * @return true if the end game token is taken, false otherwise
     */
    public boolean isEndGameToken() {
        return endGameToken;
    }

    /**
     * This method is used to set the end game token
     *
     * @param endGameToken true if the end game token is taken, false otherwise
     */
    public void setEndGameToken(boolean endGameToken) {
        this.endGameToken = endGameToken;
    }

    /**
     * This method is used to get the current player hand
     *
     * @return the current player hand
     */
    public List<String> getCurrentPlayerHand() {
        return currentPlayerHand;
    }

    /**
     * This method is used to set the current player hand
     *
     * @param currentPlayerHand the current player hand
     */
    public void setCurrentPlayerHand(List<String> currentPlayerHand) {
        this.currentPlayerHand = currentPlayerHand;
    }

    /**
     * This method is used to get the common goal scores
     *
     * @return the common goal scores
     */
    public List<Integer> getCommonGoalScores() {
        return commonGoalScores;
    }

    /**
     * This method is used to set the common goal scores
     *
     * @param commonGoalScores the common goal scores
     */
    public void setCommonGoalScores(List<Integer> commonGoalScores) {
        this.commonGoalScores = commonGoalScores;
    }

    /**
     * This method is used to get the match ID
     *
     * @return the match ID
     */
    public int getMatchID() {
        return matchID;
    }

    /**
     * This method is used to set the match ID
     *
     * @param matchID the match ID
     */
    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    /**
     * This method is used to get the current player
     *
     * @return the current player
     */
    public String getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * This method is used to set the current player
     *
     * @param currentPlayer the current player
     */
    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * This method is used to get the player list
     *
     * @return the player list
     */
    public List<String> getPlayers() {
        return players;
    }

    /**
     * This method is used to set the player list
     *
     * @param players the player list
     */
    public void setPlayers(List<String> players) {
        this.players = players;
    }

    /**
     * This method is used to get the game score
     *
     * @return the game score
     */
    public List<Integer> getScores() {
        return scores;
    }

    /**
     * This method is used to set the game score
     *
     * @param scores the game score
     */
    public void setScores(List<Integer> scores) {
        this.scores = scores;
    }

    /**
     * This method is used to get the shelves
     *
     * @return the shelves
     */
    public List<String[][]> getShelves() {
        return shelves;
    }

    /**
     * This method is used to set the shelves
     *
     * @param shelves the shelves
     */
    public void setShelves(List<String[][]> shelves) {
        this.shelves = shelves;
    }

    /**
     * This method is used to get the personal goals
     *
     * @return the personal goals
     */
    public List<Integer> getPersonalGoals() {
        return personalGoals;
    }

    /**
     * This method is used to set the personal goals
     *
     * @param personalGoals the personal goals
     */
    public void setPersonalGoals(List<Integer> personalGoals) {
        this.personalGoals = personalGoals;
    }

    /**
     * This method is used to get the board
     *
     * @return the board
     */
    public String[][] getBoard() {
        return board;
    }

    /**
     * This method is used to set the board
     *
     * @param board the board
     */
    public void setBoard(String[][] board) {
        this.board = board;
    }

    /**
     * This method is used to get the common goals
     *
     * @return the common goals
     */
    public List<String> getCommonGoals() {
        return commonGoals;
    }

    /**
     * This method is used to set the common goals
     *
     * @param commonGoals the common goals
     */
    public void setCommonGoals(List<String> commonGoals) {
        this.commonGoals = commonGoals;
    }

    /**
     * This method is used to get the admin
     *
     * @return the admin
     */
    public String getAdmin() {
        return admin;
    }

    /**
     * This method is used to set the admin
     *
     * @param admin the admin
     */
    public void setAdmin(String admin) {
        this.admin = admin;
    }

    /**
     * This method is used to get the game results
     *
     * @return the game results
     */
    public String[][] getGameResults() {
        return gameResults;
    }

    /**
     * This method is used to set the game results
     *
     * @param gameResults the game results
     */
    public void setGameResults(String[][] gameResults) {
        this.gameResults = gameResults;
    }

}
