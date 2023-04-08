package org.am21.utilities;

import org.am21.model.Cards.CommonGoals.*;
import org.am21.model.Cards.CommonGoal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommonGoalUtil {
    public static HashMap<Integer, CommonGoal> commonGoalHashMap = new HashMap<>();

    /**
     * @param numPlayer
     * @return
     */
    public static List<CommonGoal> getCommonGoals(int numPlayer) {
        initializeMap(commonGoalHashMap, numPlayer);
        List<CommonGoal> res = new ArrayList<CommonGoal>(2);
        int firstGoal = (int) (Math.random() * 12);
        res.add(commonGoalHashMap.get(firstGoal));
        int secondGoal = (int) (Math.random() * 12);
        while (secondGoal == firstGoal) {
            secondGoal = (int) (Math.random() * 12);
        }
        res.add(commonGoalHashMap.get(secondGoal));
//        System.out.println("Match > "+res.size()+" CommonGoal generated");

        return res;
    }

    /**
     * @param map
     * @param playerNum
     */
    private static void initializeMap(HashMap<Integer, CommonGoal> map, int playerNum) {
        map.clear();
        map.put(0, new CommonGoal2Lines(playerNum));
        map.put(1, new CommonGoal2Columns(playerNum));
        map.put(2, new CommonGoal3Column(playerNum));
        map.put(3, new CommonGoal4Lines(playerNum));
        map.put(4, new CommonGoal8Tiles(playerNum));
        map.put(5, new CommonGoalCorner(playerNum));
        map.put(6, new CommonGoalDiagonal(playerNum));
        map.put(7, new CommonGoalSquare(playerNum));
        map.put(8, new CommonGoalStairs(playerNum));
        map.put(9, new CommonGoalGroup(playerNum, 4, 4));
        map.put(10, new CommonGoalGroup(playerNum, 6, 2));
        map.put(11, new CommonGoalXShape(playerNum));
    }
}

