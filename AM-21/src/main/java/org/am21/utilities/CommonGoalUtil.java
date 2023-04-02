package org.am21.utilities;

import org.am21.model.items.Card.CommonGoals.*;
import org.am21.model.items.CommonGoal;
import java.util.ArrayList;
import java.util.List;

public class CommonGoalUtil {
    public static List<CommonGoal> getCommonGoals() {
        List<CommonGoal> res = new ArrayList<CommonGoal>(2);
        int firstGoal = (int) (Math.random() * 12);
        res.add(selectGoal(firstGoal));
        int secondGoal = (int) (Math.random() * 12);
        while (secondGoal == firstGoal) {
            secondGoal = (int) (Math.random() * 12);
        }
        res.add(selectGoal(secondGoal));
        System.out.println(res.size()+" CommonGoal generated");

        return res;
    }

    private static CommonGoal selectGoal(int index) {
        switch (index) {
            case 0:
                return new CommonGoal2Columns("CommonGoal2Columns");
            case 1:
                return new CommonGoal2Lines("CommonGoal2Lines");
            case 2:
                return new CommonGoal3Column("CommonGoal3Column");
            case 3:
                return new CommonGoal4Lines("CommonGoal4Lines");
            case 4:
                return new CommonGoal8Tiles("CommonGoal8Tiles");
            case 5:
                return new CommonGoalCorner("CommonGoalCorner");
            case 6:
                return new CommonGoalDiagonal("CommonGoalDiagonal");
            case 7:
                return new CommonGoalSquare("CommonGoalSquare");
            case 8:
                return new CommonGoalStairs("CommonGoalStairs");
            case 9:
                return new CommonGoalx4Group("CommonGoalx4Group");
            case 10:
                return new CommonGoalx6Group("CommonGoalx6Group");
            case 11:
                return new CommonGoalXShape("CommonGoalXShape");
        }
        return null;
    }
}
