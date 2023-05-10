package org.am21.utilities;

import com.alibaba.fastjson2.JSON;
import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.model.VirtualView;
import org.am21.model.items.Shelf;

import java.util.ArrayList;
import java.util.HashMap;
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
        virtualizeBoard(match);
        virtualizePlayersData(match);
        virtualizeCurrentPlayer(match);
        virtualizeCommonGoal(match);
        updateCommonGoalScore(match);
        virtualizeCurrentPlayerHand(match);
        virtualizeEndGame(match);
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
        List<Integer> hiddenPoints = new ArrayList<>();
        List<String[][]> shelves = new ArrayList<>();
        List<String> tmp = new ArrayList<>();
        for (Player player : match.playerList) {
            players.add(player.getNickname());
            int stringLength = player.getMyPersonalGoal().getNameCard().length();
            String temp = player.getMyPersonalGoal().getNameCard().substring(stringLength - 2, stringLength);
            int goalID = Integer.parseInt(temp);
            personalGoals.add(goalID);  //PersonalGoal
            scores.add(player.getPlayerScore());
            shelves.add(virtualizeShelves(player.getShelf()));
            hiddenPoints.add(player.getHiddenPoints());
            tmp.add("");
        }
        match.virtualView.setPlayers(players);
        match.virtualView.setPersonalGoals(personalGoals);
        match.virtualView.setShelves(shelves);
        match.virtualView.setScores(scores);
        match.virtualView.setHiddenPoints(hiddenPoints);
        match.virtualView.gameResults = tmp;
    }

    /**
     * This method will set the scores of each player to the virtual view
     *
     * @param match the match
     */
    public static void updateVirtualScores(Match match) {
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
    public static void updateVirtualShelves(Match match) {
        List<String[][]> shelves = new ArrayList<>();
        for (Player player : match.playerList) {
            shelves.add(virtualizeShelves(player.getShelf()));
        }
        match.virtualView.setShelves(shelves);
    }


    /**
     * This method will virtualize the shelves and put them in the Virtual View
     *
     * @param shelf the shelves
     * @return the virtual view
     */
    private static String[][] virtualizeShelves(Shelf shelf) {
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
     * This method will virtualize the Board.
     * Code:
     * - null -> empty cell
     * - "value" -> ItemName
     * - ">value" -> Selected Cell
     * Call this method for setup or for update.
     *
     * @param match the match
     */
    public static void virtualizeBoard(Match match) {
        int row = match.board.gRow;
        int column = match.board.gColumn;
        List<CardPointer> hl = match.currentPlayer.getHand().getSelectedItems();
        String[][] board = new String[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (match.board.getCell(i, j) != null) {
                    if (checkMark(hl, i, j)) {
                        board[i][j] = ">" + match.board.getCell(i, j).getNameCard();
                    } else {
                        board[i][j] = match.board.getCell(i, j).getNameCard();
                    }
                }
            }
        }
        match.virtualView.setBoard(board);
    }

    private static boolean checkMark(List<CardPointer> selected_items, int i, int j) {
        if (!selected_items.isEmpty()) {
            for (CardPointer item : selected_items) {
                if (item.x == i && item.y == j) {
                    return true;
                }
            }

        }

        return false;
    }

    /**
     * This method will set the common goal to the virtual view
     * call this method every time the round starts
     *
     * @param match the match
     */
    public static void virtualizeCurrentPlayer(Match match) {
        match.virtualView.setCurrentPlayer(match.currentPlayer.getNickname());
    }

    /**
     * This method will set the common goal to the virtual view
     * call this method only when the match starts
     *
     * @param match the match
     */
    private static void virtualizeCommonGoal(Match match) {
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
    public static void updateCommonGoalScore(Match match) {
        List<Integer> commonGoalScore = new ArrayList<>();
        for (int i = 0; i < match.commonGoals.size(); i++) {
            if (match.commonGoals.get(i).tokenStack.size() == 0) {
                commonGoalScore.add(0);
            } else {
                commonGoalScore.add(match.commonGoals.get(i).tokenStack.get(0));
            }
        }
        match.virtualView.setCommonGoalScores(commonGoalScore);
    }

    //TODO: set the end game token
    public static void virtualizeEndGame(Match match) {
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
     *
     * @param match the match
     */
    public static void printJSON(Match match) {
        String json = convertVirtualViewToJSON(match.virtualView);
        System.out.println(json);
    }

    public static void printJSONHand(Match match) {
        String json = convertVirtualHandToJSON(match.virtualView);
        System.out.println(json);
    }

    public static void printJSONBSH(Match m) {
        String json = "";
        json += convertVirtualBoardToJSON(m.virtualView);
        json += convertVirtualShelfListToJSON(m.virtualView);
        json += convertVirtualHandToJSON(m.virtualView);
        System.out.println(json);

    }

    public static String convertVirtualHandToJSON(VirtualView virtualView) {
        return JSON.toJSONString(virtualView.getCurrentPlayerHand());
    }

    /**
     * Virtualize current player's hand in String
     * Can be used for setup or for update
     *
     * @param match the match
     */
    public static void virtualizeCurrentPlayerHand(Match match) {
        List<String> hand = new ArrayList<>();
        for (int i = 0; i < match.currentPlayer.getHand().getSelectedItems().size(); i++) {
            hand.add(match.currentPlayer.getHand().getSelectedItems().get(i).item.getNameCard());
        }
        match.virtualView.setCurrentPlayerHand(hand);
    }

    /**
     * Update only the hidden points of the current player at the end of each turn
     *
     * @param m
     */
    public static void updateHiddenPoints(Match m) {
        if (m.virtualView.hiddenPoints != null) {
            int index = m.playerList.indexOf(m.currentPlayer);
            m.virtualView.getHiddenPoints().set(index, m.currentPlayer.getHiddenPoints());
        }
    }

    public static void virtualizeGameResults(Match m, List<String> gR) {
        m.virtualView.gameResults = gR;
    }

    public static String convertGameResultsToJSON(VirtualView virtualView) {
        return JSON.toJSONString(virtualView.gameResults);
    }

    public static String convertMatchInfoToJSON(Match m) {
        List<Object> info = new ArrayList<>();
        info.add(m.virtualView.getMatchID());
        info.add(m.virtualView.getMaxSeats());
        info.add(m.virtualView.getAdmin());

        return JSON.toJSONString(info);
    }

    /**
     * Use chatManager openChat method
     *
     * @param m
     * @param chat
     */
    public static void virtualizePublicChat(Match m, List<String> chat) {
        List<String> tmpChat = new ArrayList<>();
        tmpChat.addAll(chat);
        m.virtualView.setPublicChat(tmpChat);
    }

    public static String convertPublicChatToJSON(VirtualView v) {
        return JSON.toJSONString(v.getPublicChat());
    }

    public static void virtualizePrivateChats(Match m, List<List<String>> chats) {
        List<List<String>> tmpChats = new ArrayList<>();
        for(List<String> chat : chats){
            List<String> tmpPrivateChat = new ArrayList<>();
            tmpPrivateChat.addAll(chat);
            tmpChats.add(tmpPrivateChat);
        }
        m.virtualView.setPrivateChats(tmpChats);
    }

    public static String convertPrivateChats(VirtualView v){
        return JSON.toJSONString(v.getPrivateChats());
    }

    public static String convertVirtualChatMapToJSON(VirtualView v){
        return JSON.toJSONString(v.getChatMap());
    }

    public static void virtualizeChatMap(Match m, HashMap<String,Integer> map){
        HashMap<String,Integer> tmp = new HashMap<>();
        for(String key: map.keySet()) {
            tmp.put(key,map.get(key));
        }
        m.virtualView.setChatMap(tmp);

    }

}