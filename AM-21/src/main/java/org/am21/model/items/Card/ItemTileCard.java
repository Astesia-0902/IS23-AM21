package org.am21.model.items.Card;
import java.util.*;

public class ItemTileCard extends Card {
    private boolean isNonSelectable;

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
        for (int i = 0; i < itemTileNames.size(); i++) {
            for (int j = 0; j < 22; j++) {
                ItemTileCard itemTileCard = new ItemTileCard(itemTileNames.get(i));
                itemTileCards.add(itemTileCard);
            }
        }
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