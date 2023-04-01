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
    public static List<ScoringTokenCard> buildScoringTokenCards(int numPlayer) {
        List<ScoringTokenCard> scoringTokenCards = new ArrayList<>();

        if (numPlayer == 4) {
            for (int i = 0; i < scoringTokens.size(); i++) {
                ScoringTokenCard scoringTokenCard = new ScoringTokenCard(scoringTokens.get(i));
                scoringTokenCards.add(scoringTokenCard);
            }
        } else if (numPlayer == 3) {
            for (int i = 0; i < scoringTokens.size() - 1; i++) {
                ScoringTokenCard scoringTokenCard = new ScoringTokenCard(scoringTokens.get(i));
                scoringTokenCards.add(scoringTokenCard);
            }
        } else if (numPlayer == 2) {
            for (int i = 0; i < scoringTokens.size() - 1; i = i + 2) {
                ScoringTokenCard scoringTokenCard = new ScoringTokenCard(scoringTokens.get(i));
                scoringTokenCards.add(scoringTokenCard);
            }
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
