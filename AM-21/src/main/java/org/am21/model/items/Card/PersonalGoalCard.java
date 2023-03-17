package org.am21.model.items.Card;

import org.am21.model.items.Shelf;

public abstract class PersonalGoalCard extends Card{
    public int currentScore;
    public boolean isAchieved;
    public abstract boolean checkGoal(Shelf shelf);
    public int tilesMatches; //temporaneo(Ken)

    public void setTilesMatches(int tilesMatches) {
        this.tilesMatches = tilesMatches;
    }   //temporaneo(Ken)
    public int calculatePoints(int tilesMatches){
        return 1;
    }   //temporaneo(Ken)

    //mi serviva un valore da personal goal(Ken)
}
