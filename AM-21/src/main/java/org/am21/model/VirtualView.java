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
    public String[][] board;
    //since we don't process the goal logic in the client side, we don't need to send the goal to the client
    //so we use a integer to map the goal picture
    public List<String> commonGoal;
    public List<String> players;
    public List<Integer> scores;
    public List<String[][]> shelves;
    public List<String> personalGoals;
    public String currentPlayer;
    public String gamePhase;

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

    public List<String> getPersonalGoals() {
        return personalGoals;
    }

    public void setPersonalGoals(List<String> personalGoals) {
        this.personalGoals = personalGoals;
    }

    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    public List<String> getCommonGoal() {
        return commonGoal;
    }

    public void setCommonGoal(List<String> commonGoal) {
        this.commonGoal = commonGoal;
    }
}
