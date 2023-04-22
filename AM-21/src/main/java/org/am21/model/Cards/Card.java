package org.am21.model.Cards;

import java.awt.*;
public abstract class Card extends Component {
    private String nameCard;
    public Card(String nameCard) {
        this.nameCard = nameCard;
    }
    public String getNameCard() {
        return nameCard;
    }
}
