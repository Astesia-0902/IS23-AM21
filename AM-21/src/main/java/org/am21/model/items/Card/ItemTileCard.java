package org.am21.model.items.Card;
import java.util.*;

public class ItemTileCard extends Card {
    private boolean isNonSelectable;
    // Each set has 22 cards and 3 colours.
    // Each colour has 7 cards (22/3 = 7), one of the colours has 8 cards.
    private final static int numCards = 7;

    private final static List<String> itemTileNames = new ArrayList<>();
    static {
        Collections.addAll(itemTileNames,ItemType.Cats.name()+"1.1",ItemType.Cats.name()+"1.2",ItemType.Cats.name()+"1.3",
                ItemType.Books.name()+"1.1",ItemType.Books.name()+"1.2",ItemType.Books.name()+"1.3",
                ItemType.Games.name()+"1.1",ItemType.Games.name()+"1.2",ItemType.Games.name()+"1.3",
                ItemType.Frames.name()+"1.1",ItemType.Frames.name()+"1.2",ItemType.Frames.name()+"1.3",
                ItemType.Trophies.name()+"1.1",ItemType.Trophies.name()+"1.2",ItemType.Trophies.name()+"1.3",
                ItemType.Plants.name()+"1.1",ItemType.Plants.name()+"1.2",ItemType.Plants.name()+"1.3");
    }

    public ItemTileCard(String nameCard) {
        super(nameCard);
        this.isNonSelectable = false;
    }

    public static List<ItemTileCard> buildItemTileCards(){
        List<ItemTileCard> itemTileCards = new ArrayList<>();
        for (String itemTileName : itemTileNames) {
            for (int j = 0; j < numCards; j++) {
                ItemTileCard itemTileCard = new ItemTileCard(itemTileName);
                itemTileCards.add(itemTileCard);
            }
        }

        // Follows the physical game, adding the 22nd card of each set
        itemTileCards.addAll(Arrays.asList(
                new ItemTileCard(ItemType.Cats.name()+"1.2"),
                new ItemTileCard(ItemType.Books.name()+"1.3"),
                new ItemTileCard(ItemType.Games.name()+"1.2"),
                new ItemTileCard(ItemType.Frames.name()+"1.3"),
                new ItemTileCard(ItemType.Trophies.name()+"1.3"),
                new ItemTileCard(ItemType.Plants.name()+"1.3")
        ));

        Collections.shuffle(itemTileCards);
        return itemTileCards;
    }

    public boolean getNonSelectable() {
        return isNonSelectable;
    }

    public void setNonSelectable(boolean nonSelectable) {
        isNonSelectable = nonSelectable;
    }
}

enum ItemType {
    Cats,
    Books,
    Games,
    Frames,
    Trophies,
    Plants
}