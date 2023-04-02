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
        return res;
    }

    private static CommonGoal selectGoal(int index) {
        switch (index) {
            case 0:
                return new CommonGoal2Columns();
            break;
            case 1:
                return new CommonGoal2Lines();
            break;
            case 2:
                return new CommonGoal3Column();
            break;
            case 3:
                return new CommonGoal4Lines();
            break;
            case 4:
                return new CommonGoal8Tiles();
            break;
            case 5:
                return new CommonGoalCorner();
            break;
            case 6:
                return new CommonGoalDiagonal();
            break;
            case 7:
                return new CommonGoalSquare();
            break;
            case 8:
                return new CommonGoalStairs();
            break;
            case 9:
                return new CommonGoalx4Group();
            break;
            case 10:
                return new CommonGoalx6Group();
            break;
            case 11:
                return new CommonGoalXShape();
            break;
        }
    }
}
