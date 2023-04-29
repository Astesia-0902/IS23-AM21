package org.am21.model;

import java.io.Serializable;
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


    public int matchID;

    // These need update at the end of each turn
    public String[][] board;
    public List<Integer> commonGoalScores;
    public List<String> currentPlayerHand;
    public List<Integer> scores;
    public List<String[][]> shelves;
    public String currentPlayer;
    public boolean endGameToken;
    //---------------------------------------
    //TODO: Newsletter
    //TODO: Chat History
    //---------------

    //Since we don't process the goal logic on the client side, we don't need to send the goal to the client.
    //We are going to use an integer to map the goal picture
    public List<String> commonGoals;
    //Need update if someone leaves the match
    public List<String> players;




    public List<Integer> personalGoals;

    //TODO: gameState and gamePhase maybe not needed for visualization (maybe should be just server work)
    public String gamePhase;
    public String gameState;


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

    public String getGamePhase() {
        return gamePhase;
    }

    public void setGamePhase(String gamePhase) {
        this.gamePhase = gamePhase;
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

    public String getGameState() {
        return gameState;
    }

    public void setGameState(String gameState) {
        this.gameState = gameState;
    }

}
