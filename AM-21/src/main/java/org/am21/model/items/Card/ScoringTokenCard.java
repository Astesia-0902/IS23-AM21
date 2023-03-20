package org.am21.model.items.Card;

public class ScoringTokenCard extends Card {
    private final ScoringToken scoringToken;

    public ScoringTokenCard(ScoringToken scoringToken) {
        super(scoringToken.name());
        this.scoringToken = scoringToken;
    }

    // return the corresponding score
    public int getScoreValue() {
        return scoringToken.getScoringTokenValue();
    }
}

enum ScoringToken{
    scoring_8(8),
    scoring_6(6),
    scoring_4(4),
    scoring_2(2);

    private final int scoringTokenValue;

    ScoringToken(int scoringTokenValue) {
        this.scoringTokenValue = scoringTokenValue;
    }

    public int getScoringTokenValue() {
        return scoringTokenValue;
    }
}
