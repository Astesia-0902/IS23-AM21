package org.am21.model.items.Card;

import java.awt.*;
public abstract class Card extends Component {
    private final String nameCard;
    public Card(String nameCard) {
        this.nameCard = nameCard;
    }

    public String getNameCard() {
        return nameCard;
    }
}
