package org.am21.model.Cards;

import java.awt.*;

/**
 * This class is the abstract class of the cards
 * */
public abstract class Card extends Component {
    private final String nameCard;

    /**
     * Constructor
     * @param nameCard the name of the card
     * */
    public Card(String nameCard) {
        this.nameCard = nameCard;
    }

    /**
     * This method is used to get the name of the card
     * @return the name of the card
     * */
    public String getNameCard() {
        return nameCard;
    }
}
