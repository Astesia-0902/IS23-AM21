package org.am21.model.items.Card;

import java.awt.*;
public abstract class Card extends Component {
    private String nameCard;
    public Card(String nameCard) {
        this.nameCard = nameCard;
    }

    public String getNameCard() {
        return nameCard;
    }

    public void setNameCard(String nameCard) {
        this.nameCard = nameCard;
    }
}
