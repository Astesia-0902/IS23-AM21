package org.am21.model.items.Card;

import org.am21.model.items.Shelf;

public class PersonalGoalCardNew extends Card{
    public int currentScore;
    public boolean isAchieved;

    public PersonalGoalCardNew(String name, Integer width, Integer height) {
        super(name, width, height);
    }

    public boolean checkGoal(Shelf shelf){
        return false;
    };
    public int tilesMatches; //temporaneo(Ken)

    public void setTilesMatches(int tilesMatches) {
        this.tilesMatches = tilesMatches;
    }   //temporaneo(Ken)
    public int calculatePoints(int tilesMatches){
        return 1;
    }   //temporaneo(Ken)

    //mi serviva un valore da personal goal(Ken)
}