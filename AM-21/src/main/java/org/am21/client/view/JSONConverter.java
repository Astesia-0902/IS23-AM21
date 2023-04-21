package org.am21.client.view;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


import java.util.List;

/**
 * You can find the virtual view data in this class
 * TODO: virtual view update every time the server sends a JSON
 */
public class JSONConverter {
    public static int matchID;
    public static String[][] virtualBoard;
    public static List<String> players;
    public static String currentPlayer;
    public static List<Integer> scores;
    public static List<String> commonGoal;
    public static int personalGoal;
    public static List<String[][]> shelf;
    public static String gamePhase;
    public static String gameState;
    public static List<String> currentPlayerHand;

    /**
     * Once the JSON is received, it is parsed and the data is stored in the corresponding variables
     * the key strings of get methods are generated automatically,
     * check them first when you get a wrong answer
     *
     * @param json the JSON string received from the server
     */
    public static void setViewVariables(String json) {
        //TODO: add the player's commonGoal score
        JSONObject jsonObject = JSONObject.parseObject(json);
        virtualBoard = jsonObject.getObject("board", String[][].class);
        players = jsonObject.getJSONArray("players").toJavaList(String.class);
        currentPlayer = jsonObject.getString("currentPlayer");
        scores = jsonObject.getJSONArray("scores").toJavaList(Integer.class);
        commonGoal = jsonObject.getJSONArray("commonGoals").toJavaList(String.class);
        //TODO:we only need the personal goal of the player of this client
        personalGoal = jsonObject.getJSONArray("personalGoals").toJavaList(Integer.class).get(getPlayerIndex(currentPlayer));
        JSONArray temp = jsonObject.getJSONArray("shelves");
        shelf = new java.util.ArrayList<>();
        for(int i = 0; i < temp.size(); i++) {
            shelf.add(temp.getObject(i, String[][].class));
        }
        gamePhase = jsonObject.getString("gamePhase");
        gameState = jsonObject.getString("gameState");
        currentPlayerHand = jsonObject.getJSONArray("currentPlayerHand").toJavaList(String.class);
        matchID = jsonObject.getInteger("matchID");
    }

    /**
     * get the index of the player in the list
     * use this index to map other player data in other lists
     *
     * @param player the name of the player
     * @return the index of the player in the list
     */
    public static int getPlayerIndex(String player) {
        return players.indexOf(player);
    }
}
