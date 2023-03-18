package org.am21.model.items.Card;

import org.am21.model.items.Shelf;

public abstract class PersonalGoalCard extends Card{
    public int currentScore;
    public boolean isAchieved;
    public abstract boolean checkGoal(Shelf shelf);
//<<<<<<< Updated upstream
    public int tilesMatches; //temporaneo(Ken)

    public void setTilesMatches(int tilesMatches) {
        this.tilesMatches = tilesMatches;
    }   //temporaneo(Ken)
    public int calculatePoints(int tilesMatches){
        return 1;
    }   //temporaneo(Ken)

    //mi serviva un valore da personal goal(Ken)
//=======

    public PersonalGoalCard(String name, Integer x, Integer y, Integer width, Integer height, int currentScore, boolean isAchieved, int tilesMatches) {
        super(name, x, y, width, height);
        this.currentScore = currentScore;
        this.isAchieved = isAchieved;
        this.tilesMatches = tilesMatches;
    }

//>>>>>>> Stashed changes
}
