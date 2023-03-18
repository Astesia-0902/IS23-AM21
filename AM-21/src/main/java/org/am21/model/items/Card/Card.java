package org.am21.model.items.Card;

import java.awt.*;
public abstract class Card extends Component {
    private String name;

    private final Integer x;
    private final Integer y;
    private final Integer width;
    private final Integer height;

    public Card(String name, Integer width, Integer height) {
        this.name = name;
        this.x = 0;
        this.y = 0;
        this.width = width;
        this.height = height;
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
