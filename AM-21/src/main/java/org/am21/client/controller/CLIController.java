package org.am21.client.controller;

import com.alibaba.fastjson.JSONObject;


import java.util.List;

/**
 *
 */
public class CLIController {
    public JSONObject myJson;

    /**
     *
     * @param json
     */
    public void buildCLI(String json) {
        myJson = JSONObject.parseObject(json);
        String[] nicknames = myJson.getObject("nickname", String[].class);
        int[] scores = myJson.getObject("score", int[].class);
        int[][] board = myJson.getObject("board", int[][].class);
        List<int[][]> shelf = myJson.getObject("shelf", List.class);
        List<Integer> commonGoal = myJson.getObject("commonGoal", List.class);
        int[] personalGoals = myJson.getObject("personalGoal", int[].class);
        int timer = myJson.getObject("timer", int.class);
        String currentPlayer = myJson.getString("currentPlayer");
        String gameState = myJson.getString("gameState");
        String gamePhase = myJson.getString("gamePhase");
    }
}
