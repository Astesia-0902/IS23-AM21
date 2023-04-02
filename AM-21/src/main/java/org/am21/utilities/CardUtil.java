package org.am21.utilities;

import org.am21.model.items.Card.ItemTileCard;
import org.am21.model.items.Card.ItemType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CardUtil {
    // Each set has 22 cards and 3 colours.
    // Each colour has 7 cards (22/3 = 7), one of the colours has 8 cards.
    private final static int numCards = 7;

    /**
     * This block generate the list of all the item's name
     */
    private final static List<String> itemTileNames = new ArrayList<String>();
    static {
        Collections.addAll(itemTileNames, ItemType.__Cats__.name()+"1.1",ItemType.__Cats__.name()+"1.2",ItemType.__Cats__.name()+"1.3",
                ItemType._Books__.name()+"1.1",ItemType._Books__.name()+"1.2",ItemType._Books__.name()+"1.3",
                ItemType._Games__.name()+"1.1",ItemType._Games__.name()+"1.2",ItemType._Games__.name()+"1.3",
                ItemType._Frames_.name()+"1.1",ItemType._Frames_.name()+"1.2",ItemType._Frames_.name()+"1.3",
                ItemType.Trophies.name()+"1.1",ItemType.Trophies.name()+"1.2",ItemType.Trophies.name()+"1.3",
                ItemType._Plants_.name()+"1.1",ItemType._Plants_.name()+"1.2",ItemType._Plants_.name()+"1.3");
    }

    /**
     * Function to assign each item's name to an ItemTileCard element
     * @return The list of ItemTileCards
     */
    public static List<ItemTileCard> buildItemTileCard(){
        List<ItemTileCard> itemTileCards = new ArrayList<ItemTileCard>();
        for (String itemTileName : itemTileNames) {
            for (int j = 0; j < numCards; j++) {
                ItemTileCard itemTileCard = new ItemTileCard(itemTileName);
                itemTileCards.add(itemTileCard);
            }
        }

        // Follows the physical game, adding the 22nd card of each set
        itemTileCards.addAll(Arrays.asList(
                new ItemTileCard(ItemType.__Cats__.name()+"1.2"),
                new ItemTileCard(ItemType._Books__.name()+"1.3"),
                new ItemTileCard(ItemType._Games__.name()+"1.2"),
                new ItemTileCard(ItemType._Frames_.name()+"1.3"),
                new ItemTileCard(ItemType.Trophies.name()+"1.3"),
                new ItemTileCard(ItemType._Plants_.name()+"1.3")
        ));

        Collections.shuffle(itemTileCards);
        return itemTileCards;
    }
}
