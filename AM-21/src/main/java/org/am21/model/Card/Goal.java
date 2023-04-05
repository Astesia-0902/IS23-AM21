package org.am21.model.Card;

public abstract class Goal extends Card {
    public int goalID;
    public int score;

    public Goal(String nameCard) {
        super(nameCard);
    }
}
