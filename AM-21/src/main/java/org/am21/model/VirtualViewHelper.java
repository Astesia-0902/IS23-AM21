package org.am21.model;

import com.alibaba.fastjson2.JSON;
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
     * @param match the match
     */
    public static void buildVirtualView(Match match) {
        match.virtualView = new VirtualView();
        setBoard(match);
        setPlayers(match);
        setCommonGoal(match);
        setGamePhase(match);
    }

    /**
     * This method will set the dynamic data to the virtual view
     * Call this method after each round ends
     * @param match the match
     */
    public static void updateVirtualView(Match match) {
        setRoundUpdatePlayer(match);
        setCurrentPlayer(match);
        setGamePhase(match);
        //TODO: setGameState(match)
        //TODO: update players data
    }

    /**
     * This method will set ALL player related data to the virtual view
     * recommend to use this method when the match starts
     * @param match the match
     */
    //TODO: maybe updatePlayer?
    //TODO: maybe is called when the match has been initialized and not when a player is added
    public static void setPlayers(Match match) {
        List<String> players = new ArrayList<>();
        List<String> personalGoals = new ArrayList<>();
        List<Integer> scores = new ArrayList<>();
        List<String[][]> shelves = new ArrayList<>();
        for (Player player : match.playerList) {
            players.add(player.getNickname());
            //TODO: uncomment
            personalGoals.add(player.getMyPersonalGoal().getNameCard());
            scores.add(player.getPlayerScore());
            shelves.add(buildShelves(player.getShelf()));
        }
        match.virtualView.setPlayers(players);
        //TODO: uncomment
        //match.virtualView.setPersonalGoals(personalGoals);
        match.virtualView.setScores(scores);
        match.virtualView.setShelves(shelves);
    }

    /**
     * This method will set the scores of each player to the virtual view
     * recommend to use this method when each round ends
     * @param match the match
     */
    private static void setRoundUpdatePlayer(Match match) {
        List<Integer> scores = new ArrayList<>();
        List<String[][]> shelves = new ArrayList<>();
        for (Player player : match.playerList) {
            scores.add(player.getPlayerScore());
            shelves.add(buildShelves(player.getShelf()));
        }
        match.virtualView.setScores(scores);
        match.virtualView.setShelves(shelves);
    }

    /**
     * This method will build a shelves to the virtual view
     * @param shelf the shelves
     * @return the virtual view
     */
    private static String[][] buildShelves(Shelf shelf) {
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
     * @param match the match
     */
    public static void setBoard(Match match) {
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
     *
     * @param match the match
     */
    private static void setCurrentPlayer(Match match) {
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
        match.virtualView.setCommonGoal(commonGoal);
    }

    /**
     * Get the virtual view in JSON format
     * @param virtualView the virtual view
     * @return the virtual view in JSON format
     */
    public static String getVirtualViewJSON(VirtualView virtualView) {
        return JSON.toJSONString(virtualView);
    }

    public static String getPlayerNicknameListJSON(VirtualView virtualView) {
        return JSON.toJSONString(virtualView.getPlayers());
    }

    public static String getScoreListJSON(VirtualView virtualView) {
        return JSON.toJSONString(virtualView.getScores());
    }

    public static String getShelfListJSON(VirtualView virtualView) {
        return JSON.toJSONString(virtualView.getShelves());
    }

    public static String getBoardJSON(VirtualView virtualView) {
        return JSON.toJSONString(virtualView.getBoard());
    }

    public static String getCurrentPlayerJSON(VirtualView virtualView) {
        return JSON.toJSONString(virtualView.getCurrentPlayer());
    }

    public static String getCommonGoalJSON(VirtualView virtualView) {
        return JSON.toJSONString(virtualView.getCommonGoal());
    }

    public static String getPersonalGoalJSON(VirtualView virtualView) {
        return JSON.toJSONString(virtualView.getPersonalGoals());
    }
}