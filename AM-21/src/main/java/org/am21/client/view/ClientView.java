package org.am21.client.view;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClientView {

    //---------------------------------------------------------------------
    //Virtual View
    public static int matchID; //Match id
    public static int maxSeats; //Match max players number
    public static String admin; //Match admin Nickname
    /**
     * VirtualBoard Legend:
     * null : cell empty
     * "item name" : cell occupied by an item
     * ">item name" : selected cell (related to ">" presence)
     */
    public static String[][] virtualBoard; //Match Virtual board
    public static List<String> playersList; //Match Players List Nickname
    public static String currentPlayer; //Match currentPlayer
    public static List<Integer> scores; //Match players scores
    public static List<Integer> hiddenPoints;   //Match players Personal Goal Points (private)
    public static List<String> commonGoal; //Match Common Goals List (x2)
    public static List<Integer> commonGoalScore; //Match Common Goals Top Score Token (x2)
    public static int personalGoal; //This Client match's personal Goal
    public static List<String[][]> shelves; //Match List of Shelves
    public static List<String> currentPlayerHand; //Match Current player hand
    public static boolean endGameToken; // Match EndgameToken
    /**
     * gameResults legend :
     * for i in players.size() :  i as player index
     * [i][0] - Player name
     * [i][1] - Common Goal points
     * [i][2] - Personal Goal Points
     * [i][3] - Shelf Group Points
     * [i][4] - Eventual Endgame token or null (if no token for the player)
     * [i][5] - Total Score;
     * [players.size()][0] - Winner name or null (if no winner)
     */
    public static String[][] gameResults; //Match Game Results
    //------------------------------------------
    public static List<String> publicChat; //Match group Chat
    public static List<List<String>> privateChats; // Server Private Chats
    public static HashMap<String, Integer> chatMap = new HashMap<>(); //Server ChatMap
    //------------------------------------------
    public static String[][] matchList; //Server Match List
    public static String[][] onlinePlayers; //Server Online Players List

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
        playersList = jsonObject.getJSONArray("players").toJavaList(String.class);
        currentPlayer = jsonObject.getString("currentPlayer");
        scores = jsonObject.getJSONArray("scores").toJavaList(Integer.class);
        hiddenPoints = jsonObject.getJSONArray("hiddenPoints").toJavaList(Integer.class);
        commonGoal = jsonObject.getJSONArray("commonGoals").toJavaList(String.class);
        commonGoalScore = jsonObject.getJSONArray("commonGoalScores").toJavaList(Integer.class);
        List<Integer> pTmp = jsonObject.getJSONArray("personalGoals").toJavaList(Integer.class);
        if (pTmp.size() > playerIndex) {
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
        maxSeats = jsonObject.getInteger("maxSeats");
        gameResults = jsonObject.getObject("gameResults",String[][].class);
    }

    /**
     * get the index of the player in the list
     * use this index to map other player data in other lists
     *
     * @param player the name of the player
     * @return the index of the player in the list
     */
    public static int getPlayerIndex(String player) {
        if (playersList != null) {
            return playersList.indexOf(player);
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


    //-------------------------------------------
    public static boolean GO_TO_MENU = true;
    //If true GameplayRoom, if false WaitingRoom
    public static boolean GAME_ON = false;
    public static boolean MATCH_START = false;
    public static boolean MATCH_END = false;

    public static Boolean needToRefresh = false;

    public static boolean WAIT_SOCKET = false;


    //-----------------------------------------------

    public static void setGoToMenu(boolean goToMenu) {
        GO_TO_MENU = goToMenu;
    }

    public static void setGameOn(boolean gameOn) {
        GAME_ON = gameOn;
    }

    public static void setMatchStart(boolean start) {
        MATCH_START = start;
    }

    public static void setMatchEnd(boolean end) {
        MATCH_END = end;
    }

    public synchronized static void setNeedToRefresh(boolean value) {
            needToRefresh = value;
    }

    public static void setWaitSocket(boolean waitSocket) {
        WAIT_SOCKET = waitSocket;
    }




}
