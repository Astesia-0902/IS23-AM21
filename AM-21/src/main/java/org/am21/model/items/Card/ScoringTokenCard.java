package org.am21.model.items.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoringTokenCard extends Card {
    ScoringToken scoringToken;

    public ScoringTokenCard(ScoringToken scoringToken) {
        super(scoringToken.name());
        this.scoringToken = scoringToken;
    }

    private final static List<ScoringToken> scoringTokens = new ArrayList<>();

    static {
        Collections.addAll(scoringTokens,
                ScoringToken.scoring_8,ScoringToken.scoring_6,
                ScoringToken.scoring_4,ScoringToken.scoring_2);
    }

    // return a set of scoring token cards
    public static List<ScoringTokenCard> buildScoringTokenCards(){
        List<ScoringTokenCard> scoringTokenCards = new ArrayList<>();
        for (ScoringToken token : scoringTokens) {
            ScoringTokenCard scoringTokenCard = new ScoringTokenCard(token);
            scoringTokenCards.add(scoringTokenCard);
        }
        return scoringTokenCards;
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
