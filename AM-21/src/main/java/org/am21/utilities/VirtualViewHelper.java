package org.am21.utilities;

import com.alibaba.fastjson2.JSON;
import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.model.VirtualView;
import org.am21.model.items.Shelf;

import java.util.ArrayList;
import java.util.List;

/**
 * We use this class to build the virtual view
 */
public class VirtualViewHelper {
    /**
     * This method will build the virtual view
     * Call this method before the match starts, after the match data is initialized
     *
     * @param match the match
     */
    public static void buildVirtualView(Match match) {
        match.virtualView = new VirtualView();
        virtualizeMatchID(match);
        setVirtualBoard(match);
        virtualizePlayersData(match);
        setVirtualCurrentPlayer(match);
        setCommonGoal(match);
        setGamePhase(match);
        setCommonGoalScore(match);
        virtualizeCurrentPlayerHand(match);
        setGamePhase(match);
        setGameState(match);
    }

    /**
     * This method will set ALL player related data to the virtual view
     * recommend to use this method when the match starts
     *
     * @param match the match
     */
    private static void virtualizePlayersData(Match match) {
        List<String> players = new ArrayList<>();
        List<Integer> personalGoals = new ArrayList<>();
        List<Integer> scores = new ArrayList<>();
        List<String[][]> shelves = new ArrayList<>();
        for (Player player : match.playerList) {
            players.add(player.getNickname());
            int stringLength = player.getMyPersonalGoal().getNameCard().length();
            String temp = player.getMyPersonalGoal().getNameCard().substring(stringLength - 2, stringLength);
            int goalID = Integer.parseInt(temp);
            personalGoals.add(goalID);
            scores.add(player.getPlayerScore());
            shelves.add(buildVirtualShelves(player.getShelf()));
        }
        match.virtualView.setPlayers(players);
        match.virtualView.setPersonalGoals(personalGoals);
        match.virtualView.setShelves(shelves);
        match.virtualView.setScores(scores);
    }

    /**
     * This method will set the scores of each player to the virtual view
     * @param match the match
     */
    public static void setVirtualScores(Match match) {
        List<Integer> scores = new ArrayList<>();
        for (Player player : match.playerList) {
            scores.add(player.getPlayerScore());
        }
        match.virtualView.setScores(scores);
    }

    /**
     * This method will set the shelves of each player to the virtual view
     * recommend to use this method when each round ends
     *
     * @param match the match
     */
    public static void setVirtualShelves(Match match) {
        List<String[][]> shelves = new ArrayList<>();
        for (Player player : match.playerList) {
            shelves.add(buildVirtualShelves(player.getShelf()));
        }
        match.virtualView.setShelves(shelves);
    }

    /**
     * This method will build a shelves to the virtual view
     *
     * @param shelf the shelves
     * @return the virtual view
     */
    private static String[][] buildVirtualShelves(Shelf shelf) {
        String[][] tempShelf = new String[Shelf.SHELF_ROW][Shelf.SHELF_COLUMN];
        for (int i = 0; i < Shelf.SHELF_ROW; i++) {
            for (int j = 0; j < Shelf.SHELF_COLUMN; j++) {
                if (shelf.getCell(i, j) != null) {
                    tempShelf[i][j] = shelf.getCell(i, j).getNameCard();
                }
            }
        }
        return tempShelf;
    }

    /**
     * This method will set the board to the virtual view
     * Call this method once the board changes
     *
     * @param match the match
     */
    public static void setVirtualBoard(Match match) {
        int row = match.board.gRow;
        int column = match.board.gColumn;
        String[][] board = new String[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (match.board.getCell(i, j) != null) {
                    board[i][j] = match.board.getCell(i, j).getNameCard();
                }
            }
        }
        match.virtualView.setBoard(board);
    }

    /**
     * This method will set the common goal to the virtual view
     * call this method every time the round starts
     * @param match the match
     */
    public static void setVirtualCurrentPlayer(Match match) {
        match.virtualView.setCurrentPlayer(match.currentPlayer.getNickname());
    }

    private static void setGamePhase(Match match) {
        match.virtualView.setGamePhase(match.gamePhase.toString());
    }

    private static void setGameState(Match match) {
        match.virtualView.setGameState(match.gameState.toString());
    }

    /**
     * This method will set the common goal to the virtual view
     * call this method only when the match starts
     * @param match the match
     */
    private static void setCommonGoal(Match match) {
        List<String> commonGoal = new ArrayList<>();
        for (int i = 0; i < match.commonGoals.size(); i++) {
            commonGoal.add(match.commonGoals.get(i).getNameCard());
        }
        match.virtualView.setCommonGoals(commonGoal);
    }

    /**
     * This method will set the common goal score to the virtual view
     * call this method once someone completes a common goal
     *
     * @param match the match
     */
    public static void setCommonGoalScore(Match match) {
        List<Integer> commonGoalScore = new ArrayList<>();
        for (int i = 0; i < match.commonGoals.size(); i++) {
            commonGoalScore.add(match.commonGoals.get(i).tokenStack.get(0));
        }
        match.virtualView.setCommonGoalScores(commonGoalScore);
    }

    //TODO: set the end game token
    public static void setEndGame(Match match) {
        match.virtualView.setEndGameToken(match.isEndGameToken());
    }

    public static void virtualizeMatchID(Match match) {
        match.virtualView.setMatchID(match.matchID);
    }


    /**
     * Get the virtual view in JSON format
     *
     * @param virtualView the virtual view
     * @return the virtual view in JSON format
     */
    public static String convertVirtualViewToJSON(VirtualView virtualView) {
        return JSON.toJSONString(virtualView);
    }

    public static String convertVirtualPlayerNicknameListToJSON(VirtualView virtualView) {
        return JSON.toJSONString(virtualView.getPlayers());
    }

    public static String convertVirtualScoreListToJSON(VirtualView virtualView) {
        return JSON.toJSONString(virtualView.getScores());
    }

    public static String convertVirtualShelfListToJSON(VirtualView virtualView) {
        return JSON.toJSONString(virtualView.getShelves());
    }

    public static String convertVirtualBoardToJSON(VirtualView virtualView) {
        return JSON.toJSONString(virtualView.getBoard());
    }

    public static String convertVirtualCurrentPlayerToJSON(VirtualView virtualView) {
        return JSON.toJSONString(virtualView.getCurrentPlayer());
    }

    public static String convertVirtualCommonGoalToJSON(VirtualView virtualView) {
        return JSON.toJSONString(virtualView.getCommonGoals());
    }

    public static String convertVirtualPersonalGoalToJSON(VirtualView virtualView) {
        return JSON.toJSONString(virtualView.getPersonalGoals());
    }

    /**
     * This method will print the virtual view in JSON format
     * @param match the match
     */
    public static void printJSON(Match match) {
        String json = convertVirtualViewToJSON(match.virtualView);
        System.out.println(json);
    }

    public static void printJSONHand(Match match){
        String json = convertVirtualHandToJSON(match.virtualView);
        System.out.println(json);
    }

    public static String convertVirtualHandToJSON(VirtualView virtualView) {
        return JSON.toJSONString(virtualView.getCurrentPlayerHand());
    }

    /**
     * Virtualize current player's hand in String
     * @param match the match
     */
    public static void virtualizeCurrentPlayerHand(Match match) {
        List<String> hand = new ArrayList<>();
        for (int i = 0; i < match.currentPlayer.getHand().getSlot().size(); i++) {
            hand.add(match.currentPlayer.getHand().getSlot().get(i).item.getNameCard());
        }
        match.virtualView.setCurrentPlayerHand(hand);
    }
}