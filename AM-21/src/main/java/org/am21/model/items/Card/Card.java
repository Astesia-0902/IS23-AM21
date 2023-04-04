package org.am21.model.items.Card;

import java.awt.*;
public abstract class Card extends Component {
    protected String nameCard = null;
    public Card(String nameCard) {
        this.nameCard = nameCard;
    }
    public Card(){}
    public String getNameCard() {
        return nameCard;
    }
}
