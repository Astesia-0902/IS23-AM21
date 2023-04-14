package org.am21.model;

import java.io.Serializable;
import java.util.List;

//TODO:object to be sent to the client
//TODO:we need to discuss how to map each card to a number
//TODO:maybe we can store the game state in a JSON file
//1. The board map
//2. The list of shelf
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
    public int[][] board;
    //since we don't process the goal logic in the client side, we don't need to send the goal to the client
    //so we use a integer to map the goal picture
    public List<Integer> commonGoal;
    public int timer;
    public String[] nicknames;
    public int[] scores;
    public List<int[][]> shelf;
    public int[] personalGoals;

    public String[] getNicknames() {
        return nicknames;
    }

    public void setNicknames(String[] nicknames) {
        this.nicknames = nicknames;
    }

    public int[] getScores() {
        return scores;
    }

    public void setScores(int[] scores) {
        this.scores = scores;
    }

    public List<int[][]> getShelf() {
        return shelf;
    }

    public void setShelf(List<int[][]> shelf) {
        this.shelf = shelf;
    }

    public int[] getPersonalGoals() {
        return personalGoals;
    }

    public void setPersonalGoals(int[] personalGoals) {
        this.personalGoals = personalGoals;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public List<Integer> getCommonGoal() {
        return commonGoal;
    }

    public void setCommonGoal(List<Integer> commonGoal) {
        this.commonGoal = commonGoal;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }
}