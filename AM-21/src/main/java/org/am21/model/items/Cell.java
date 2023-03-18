package org.am21.model.items;

import org.am21.model.items.Card.Card;

public class Cell {
    private Integer state = 0;          // 0: without cards, 1: with cards
    private Card card;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
