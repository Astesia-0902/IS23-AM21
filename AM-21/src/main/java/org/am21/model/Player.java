package org.am21.model;

import org.am21.model.items.Shelf;

public class Player {
    private String name;
    private int score;
    public Shelf myShelf;

    Player(String name) {
        myShelf = new Shelf();
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
