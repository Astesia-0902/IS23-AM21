package org.am21.utilities;

import org.am21.model.items.Card.CommonGoals.*;
import org.am21.model.items.CommonGoal;
import java.util.ArrayList;
import java.util.List;

public class CommonGoalUtil {
    public static List<CommonGoal> getCommonGoals(int numPlayer) {
        List<CommonGoal> res = new ArrayList<CommonGoal>(2);
        int firstGoal = (int) (Math.random() * 12);
        res.add(selectGoal(firstGoal, numPlayer));
        int secondGoal = (int) (Math.random() * 12);
        while (secondGoal == firstGoal) {
            secondGoal = (int) (Math.random() * 12);
        }
        res.add(selectGoal(secondGoal, numPlayer));
        System.out.println("Match > "+res.size()+" CommonGoal generated");

        return res;
    }

    private static CommonGoal selectGoal(int index, int numPlayer) {
        switch (index) {
            case 0:
                return new CommonGoal2Columns("CommonGoal2Columns", numPlayer);
            case 1:
                return new CommonGoal2Lines("CommonGoal2Lines", numPlayer);
            case 2:
                return new CommonGoal3Column("CommonGoal3Column", numPlayer);
            case 3:
                return new CommonGoal4Lines("CommonGoal4Lines", numPlayer);
            case 4:
                return new CommonGoal8Tiles("CommonGoal8Tiles", numPlayer);
            case 5:
                return new CommonGoalCorner("CommonGoalCorner", numPlayer);
            case 6:
                return new CommonGoalDiagonal("CommonGoalDiagonal", numPlayer);
            case 7:
                return new CommonGoalSquare("CommonGoalSquare", numPlayer);
            case 8:
                return new CommonGoalStairs("CommonGoalStairs", numPlayer);
            case 9:
                return new CommonGoalx4Group("CommonGoalx4Group", numPlayer);
            case 10:
                return new CommonGoalx6Group("CommonGoalx6Group", numPlayer);
            case 11:
                return new CommonGoalXShape("CommonGoalXShape", numPlayer);
        }
        return null;
    }
}
