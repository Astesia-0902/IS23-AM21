package org.am21.model.items.Card;

import org.am21.model.items.Shelf;

public abstract class CommonGoalCard {
    public int currentScore;
    public boolean isAchieved;
    public abstract boolean checkGoal(Shelf shelf);
}
