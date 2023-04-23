package org.am21.model;

import java.io.Serializable;
import java.util.List;

//TODO:object to be sent to the client
//TODO:we need to discuss how to map each card to a number
//TODO:maybe we can store the game state in a JSON file
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
    public String[][] board;
    //since we don't process the goal logic in the client side, we don't need to send the goal to the client
    //so we use a integer to map the goal picture
    public List<String> commonGoals;
    public List<Integer> commonGoalScores;
    public List<String> players;
    public List<String> currentPlayerHand;
    public List<Integer> scores;
    public List<String[][]> shelves;
    public List<Integer> personalGoals;
    public String currentPlayer;
    public String gamePhase;
    public String gameState;
    //TODO: endgame token

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
