package org.am21.model.items.Card;

public class ScoringTokenCard extends Card {

    private int scoreValue;

    public ScoringTokenCard(int value){
        this.scoreValue = value;
    }
    public int getScoreValue() {
        return scoreValue;
    }
}
