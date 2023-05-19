package org.am21.client.view;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClientView {
    //Virtual View
    public static int matchID;
    public static int maxSeats;
    public static String admin;
    public static String[][] virtualBoard;
    public static List<String> players;
    public static String currentPlayer;
    public static List<Integer> scores;
    public static List<Integer> hiddenPoints;
    public static List<String> commonGoal;
    public static List<Integer> commonGoalScore;
    public static int personalGoal;
    public static List<String[][]> shelves;
    public static List<String> currentPlayerHand;
    public static boolean endGameToken;
    public static List<String> gameResults;
    //------------------------------------------
    public static List<String> publicChat;
    public static List<List<String>> privateChats;
    public static HashMap<String, Integer> chatMap = new HashMap<>();
    //------------------------------------------
    public static String[][] matchList;
    public static String[][] onlinePlayers;

    /**
     * Once the JSON is received, it is parsed and the data is stored in the corresponding variables
     * the key strings of get methods are generated automatically,
     * check them first when you get a wrong answer
     *
     * @param json        the JSON string received from the server
     * @param playerIndex
     */
    public static void setFullViewVariables(String json, int playerIndex) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        virtualBoard = jsonObject.getObject("board", String[][].class);
        players = jsonObject.getJSONArray("players").toJavaList(String.class);
        currentPlayer = jsonObject.getString("currentPlayer");
        scores = jsonObject.getJSONArray("scores").toJavaList(Integer.class);
        hiddenPoints = jsonObject.getJSONArray("hiddenPoints").toJavaList(Integer.class);
        commonGoal = jsonObject.getJSONArray("commonGoals").toJavaList(String.class);
        commonGoalScore = jsonObject.getJSONArray("commonGoalScores").toJavaList(Integer.class);
        List<Integer> pTmp = jsonObject.getJSONArray("personalGoals").toJavaList(Integer.class);
        if(pTmp.size()>playerIndex) {
            personalGoal = jsonObject.getJSONArray("personalGoals").toJavaList(Integer.class).get(playerIndex);
        }
        JSONArray temp = jsonObject.getJSONArray("shelves");
        shelves = new java.util.ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            shelves.add(temp.getObject(i, String[][].class));
        }
        currentPlayerHand = jsonObject.getJSONArray("currentPlayerHand").toJavaList(String.class);
        matchID = jsonObject.getInteger("matchID");
        admin = jsonObject.getString("admin");
        endGameToken = jsonObject.getBoolean("endGameToken");

        gameResults = jsonObject.getJSONArray("gameResults").toJavaList(String.class);
    }

    /**
     * get the index of the player in the list
     * use this index to map other player data in other lists
     *
     * @param player the name of the player
     * @return the index of the player in the list
     */
    public static int getPlayerIndex(String player) {
        if (players != null) {
            return players.indexOf(player);
        }
        return 0;
    }

    public static void convertBackHand(String jsonHand) {
        JSONArray jsonArray = JSONObject.parseArray(jsonHand);
        currentPlayerHand = jsonArray.toJavaList(String.class);
    }

    public static void convertBackMatchInfo(String jsonInfo) {
        JSONArray jsonArray = JSONObject.parseArray(jsonInfo);
        List<String> tmp = jsonArray.toJavaList(String.class);
        matchID = Integer.parseInt(tmp.get(0));
        maxSeats = Integer.parseInt(tmp.get(1));
        admin = tmp.get(2);

    }

    public static void convertBackPublicChat(String jsonPublic) {
        JSONArray jsonChat = JSONObject.parseArray(jsonPublic);
        publicChat = jsonChat.toJavaList(String.class);
    }

    public static void updateServerView(String jsonServer) {
        JSONObject jsonObject = JSONObject.parseObject(jsonServer);

        matchList = jsonObject.getObject("virtualMatchList", String[][].class);
        onlinePlayers = jsonObject.getObject("virtualOnlinePlayers", String[][].class);
        String[][] tmpChats = jsonObject.getObject("virtualPrivateChats", String[][].class);
        String[][] tmpMap = jsonObject.getObject("virtualChatMap", String[][].class);
        List<List<String>> tmpPrivateChats = new ArrayList<>();
        if (tmpChats != null) {
            for (int i = 0; i < tmpChats.length; i++) {
                tmpPrivateChats.add(new ArrayList<>());
                for (int l = 0; l < tmpChats[i].length; l++) {
                    tmpPrivateChats.get(i).add(tmpChats[i][l]);
                }
            }
        }
        privateChats = tmpPrivateChats;
        HashMap<String, Integer> tmpHashMap = new HashMap<>();
        if (tmpMap != null) {
            for (int i = 0; i < tmpMap.length; i++) {
                tmpHashMap.put(tmpMap[i][0], Integer.valueOf(tmpMap[i][1]));
            }
        }
        chatMap = tmpHashMap;
        /*for (Map.Entry<String, Integer> entry : chatMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }*/
    }
}
