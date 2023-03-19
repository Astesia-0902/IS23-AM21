package org.am21.model.items;

import org.am21.model.items.Card.ScoringTokenCard;

public class CommonGoal extends Goal {
    private ScoringTokenCard scoringTokenCard;

    public ScoringTokenCard getScoringTokenCard() {
        return scoringTokenCard;
    }

    public void setScoringTokenCard(ScoringTokenCard scoringTokenCard) {
        this.scoringTokenCard = scoringTokenCard;
    }
}
