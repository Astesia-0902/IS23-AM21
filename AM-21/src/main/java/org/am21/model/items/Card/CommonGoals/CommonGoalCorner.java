package org.am21.model.items.Card.CommonGoals;

import org.am21.model.Player;
import org.am21.model.items.Card.ScoringTokenCard;
import org.am21.model.items.CommonGoal;
import org.am21.model.items.Shelf;

import java.util.List;

import org.am21.model.items.CommonGoal;

public class CommonGoalCorner extends CommonGoal{
    private int id;
    public List<ScoringTokenCard> tokenStack;

    public List<Player> achievedPlayers;

    public CommonGoalCorner(){
       /**
        * check 2 or more the sequence condition:
        * 1. remember the first row and first column cell;
        * 2. remember the last row and first column cell;
        * 3. remember the first row and last column cell ;
        * 4. remember the last row and last column cell;
        * if fall the 2 or more condition, then return false for this player
        * if success the all 4 condition, then return true and distribuite the actually points
        * */
    }

    public boolean checkGoal(Shelf shelf) {
        return false;
    }

}
