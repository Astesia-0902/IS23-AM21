package org.am21.client.view;

import com.alibaba.fastjson.JSONObject;


import java.util.List;

/**
 * You can find the virtual view data in this class
 * TODO: virtual view update every time the server sends a JSON
 */
public class JSONConverter {
    public static JSONObject jsonObject;
    public static int[][] virtualBoard;
    public static List<String> players;
    public static String currentPlayer;
    public static List<Integer> scores;
    public static List<Integer> commonGoal;
    public static int personalGoal;
    public static List<int[][]> shelf;

    /**
     * Once the JSON is received, it is parsed and the data is stored in the corresponding variables
     * the key strings of get methods are generated automatically,
     * check them first when you get a wrong answer
     * @param json the JSON string received from the server
     */
    public void setViewVariables(String json) {
        jsonObject = JSONObject.parseObject(json);
        virtualBoard = jsonObject.getObject("board", int[][].class);
        players = jsonObject.getJSONArray("players").toJavaList(String.class);
        currentPlayer = jsonObject.getString("currentPlayer");
        scores = jsonObject.getJSONArray("scores").toJavaList(Integer.class);
        commonGoal = jsonObject.getJSONArray("commonGoal").toJavaList(Integer.class);
        //TODO:we only need the common goal of the player of this client
        personalGoal = jsonObject.getJSONArray("personalGoal").toJavaList(Integer.class).get(0);
        shelf = jsonObject.getJSONArray("shelf").toJavaList(int[][].class);
    }
}
