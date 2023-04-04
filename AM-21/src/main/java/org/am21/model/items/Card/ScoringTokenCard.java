package org.am21.model.items.Card;

public class ScoringTokenCard extends Card {
    ScoringToken scoringToken;

    public ScoringTokenCard(ScoringToken scoringToken) {
        super(scoringToken.name());
        this.scoringToken = scoringToken;
    }

    // return the corresponding score
    public int getScoreValue() {
        return scoringToken.getScoringTokenValue();
    }
}
