package org.am21.model.items.Card;

public class ScoringTokenCard extends Card {
//<<<<<<< Updated upstream

    private int scoreValue;


//    public ScoringTokenCard(int value){
//        this.scoreValue = value;
//    }


    public ScoringTokenCard(String name, Integer x, Integer y, Integer width, Integer height, int scoreValue) {
        super(name, x, y, width, height);
        this.scoreValue = scoreValue;
    }
    public int getScoreValue() {
        return scoreValue;
//=======
//    public ScoringTokenCard(int id) {
//        super(id);
//>>>>>>> Stashed changes
    }
}
