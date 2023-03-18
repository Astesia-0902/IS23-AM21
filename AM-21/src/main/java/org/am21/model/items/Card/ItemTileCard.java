package org.am21.model.items.Card;

import java.util.*;

public class ItemTileCard {
    public static Random random = new Random();
//    public static double num = random.nextDouble(1.3) - 1.1;

    public static String[] tileNames = {
            ItemType.Cats.name()+"1.1",ItemType.Cats.name()+"1.2",ItemType.Cats.name()+"1.3",
            ItemType.Books.name()+"1.1",ItemType.Books.name()+"1.2",ItemType.Books.name()+"1.3",
            ItemType.Games.name()+"1.1",ItemType.Games.name()+"1.2",ItemType.Games.name()+"1.3",
            ItemType.Frames.name()+"1.1",ItemType.Frames.name()+"1.2",ItemType.Frames.name()+"1.3",
            ItemType.Trophies.name()+"1.1",ItemType.Trophies.name()+"1.2",ItemType.Trophies.name()+"1.3",
            ItemType.Plants.name()+"1.1",ItemType.Plants.name()+"1.2",ItemType.Plants.name()+"1.3"
    };

    // getTileNames: Get a random card name for each call
    public static String getTileNames() {
        int randomIndex = random.nextInt(tileNames.length);
        return tileNames[randomIndex];
    }

    //
    public static Card[] buildCards(){
        Card[] cards = new Card[132];

        for (int i = 0; i < cards.length; i++) {
            String randomTileName = getTileNames();
        }

        return cards;
    }
}

enum ItemType {
    Cats,
    Books,
    Games,
    Frames,
    Trophies,
    Plants,
}