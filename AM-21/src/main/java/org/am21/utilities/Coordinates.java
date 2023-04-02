package org.am21.utilities;

import org.am21.model.items.Card.ItemTileCard;

public class Coordinates {
    public int x;
    public int y;

    public ItemTileCard item;

    public Coordinates(int r,int c){
        this.x = r;
        this.y = c;
        this.item= new ItemTileCard("none");
    }
}
