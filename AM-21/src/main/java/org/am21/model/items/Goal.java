package org.am21.model.items;

import org.am21.model.items.Card.Card;

public abstract class Goal extends Card {
    public int goalID;
    public int score;

    public Goal(){}
    public Goal(String nameCard) {
        super(nameCard);
    }
}
