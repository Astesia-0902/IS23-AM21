package org.am21.utilities;

import org.am21.model.Cards.ItemTileCard;
import org.am21.model.Cards.ItemType;
import org.am21.model.Cards.PersonalGoalCard;

import java.util.*;

public class CardUtil
{
    // ----------------------------------- ITEM TILE --------------------------------------------------------

    // we have 7+7+8 = 22 cards, distribute in 3 colors

    private final static int numCards = 7;

    /** This block generate the list of all the item's name */
    private final static List<String> itemTileNames = new ArrayList<>();
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
        List<ItemTileCard> itemTileCards = new ArrayList<>();
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

    // ----------------------------------- PERSONAL GOAL --------------------------------------------------------

    public static Random randomPersonalGoal = new Random();
    public static int numPersonalGoal;

    /**
     * Function to generate the PersonalGoalCard for all players
     * @param numPlayer
     * @return PersonalGoalCard List
     */
    public static List<PersonalGoalCard> buildPersonalGoalCard(int numPlayer){
        Set<Integer> usedNumbers = new HashSet<>();
        List<PersonalGoalCard> personalGoalCards = new ArrayList<>();
        for (int i = 0; i < numPlayer; i++) {
            do {
                numPersonalGoal = randomPersonalGoal.nextInt(12) + 1;
            } while (usedNumbers.contains(numPersonalGoal));
            usedNumbers.add(numPersonalGoal);
            personalGoalCards.add(new PersonalGoalCard("PERSONAL_GOALs" + numPersonalGoal));
        }
        System.out.println("Match > "+personalGoalCards.size()+" PersonalGoal generated");
        return personalGoalCards;
    }

    // ----------------------------------- SCORING TOKENS --------------------------------------------------------
    private final static HashMap<Integer, Integer[]> scoringTokens = new HashMap<>();

    /**
     * a set of scoring tokens in base of number of player
     */
    static {
        scoringTokens.put(4, new Integer[]{8,6,4,2});
        scoringTokens.put(3, new Integer[]{8,6,4});
        scoringTokens.put(2, new Integer[]{8,4});
    }

    /**
     * Function to distribute a set of scoring tokens
     * @param numPlayer
     * @return list of a set of scoring token card
     */
    public static List<Integer> buildScoringTokenCards(int numPlayer) {
        return new ArrayList<>(List.of(scoringTokens.get(numPlayer)));
    }
}
