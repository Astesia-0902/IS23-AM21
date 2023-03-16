package org.am21.model.items.Card;

import org.am21.model.items.Shelf;

public abstract class PersonalGoalCard extends Card{
    public int currentScore;
    public boolean isAchieved;
    public abstract boolean checkGoal(Shelf shelf);
}
