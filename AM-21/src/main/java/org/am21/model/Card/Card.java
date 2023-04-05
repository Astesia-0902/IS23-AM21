package org.am21.model.Card;

import java.awt.*;
public abstract class Card extends Component {
    protected String nameCard;
    public Card(String nameCard) {
        this.nameCard = nameCard;
    }
    public String getNameCard() {
        return nameCard;
    }
}
