package org.am21.client.controller;

import com.alibaba.fastjson2.JSONObject;

import java.util.List;

public class CLIController {
    public JSONObject myJson;

    //we use a json (sent by the server) string to build the cli
    public void buildCLI(String json) {
        myJson = JSONObject.parseObject(json);
        String[] nicknames = myJson.getObject("nicknames", String[].class);
        int[] scores = myJson.getObject("scores", int[].class);
        int[][] board = myJson.getObject("board", int[][].class);
        List<int[][]> shelf = myJson.getObject("shelf", List.class);
        List<Integer> commonGoal = myJson.getObject("commonGoal", List.class);
        int[] personalGoals = myJson.getObject("personalGoals", int[].class);
        int timer = myJson.getObject("timer", int.class);
    }
}
