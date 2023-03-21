package org.am21.model.items.Card.CommonGoals;


import org.am21.model.Player;
import org.am21.model.items.Card.ScoringTokenCard;
import org.am21.model.items.CommonGoal;
import org.am21.model.items.Shelf;

import java.util.List;

public class CommonGoalXShape {
    private int id;
    public List<ScoringTokenCard> tokenStack;

    public List<Player> achievedPlayers;

    public CommonGoalXShape(){
        /**
         * the check since follow the sequence position cell (marked by a   ° )
         * ?????
         * ?°°°?
         * ?°°°?
         * ?°°°?
         * ?°°°?
         * ?????
         * each ° have a vector check (5 element), which can recorde the
         *  [row, column] of the principle position (the ° ) and secondary position
         *  [row-1,column-1], [row-1, column+1],[row+1, column-1], [row+1, column+1],
         *  if one of the 4 rest element is different by the first, the vector is not matched (discard)
         *  so we have 12 vector, if check fall when the all 12 vector is different by the 0 position
         *  if one is completed then return true and get the points.
         * */
    }

    public boolean checkGoal(Shelf shelf) {
        return false;
    }
}
