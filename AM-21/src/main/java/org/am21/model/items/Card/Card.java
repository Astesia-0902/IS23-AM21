package org.am21.model.items.Card;

import java.awt.*;
public abstract class Card extends Component {
    private String name;

    public Card(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
