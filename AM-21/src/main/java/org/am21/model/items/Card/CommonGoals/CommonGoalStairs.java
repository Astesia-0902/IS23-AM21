package org.am21.model.items.Card.CommonGoals;

import org.am21.model.Player;
import org.am21.model.items.Card.ScoringTokenCard;
import org.am21.model.items.CommonGoal;
import org.am21.model.items.Shelf;

import java.util.List;

public class CommonGoalStairs {
    private int id;
    public List<ScoringTokenCard> tokenStack;

    public List<Player> achievedPlayers;

    public CommonGoalStairs(){
        /**
         *
         * check in 2 mode:
         * 1. since the first column: record the number of item (5 o 6),
         *      then followed the rest column ordinary:
         *      second column (4 o 5), third (3 o 4), forth (2 o 3), last (1 o 2)
         *      the check fall when one of the request column is superior the number max.
         * 2. the same mode but with reverse ordinary
         *
         * the check past when all of 5 column is matched: (necessary one of the sequence mode)
         *      first mode:  6,5,4,3,2
         *      second mode: 5,4,3,2,1
         *      third mode:  1,2,3,4,5
         *      forth mode:  2,3,4,5,6
         *
         * */
    }

    public boolean checkGoal(Shelf shelf) {
        return false;
    }
}
