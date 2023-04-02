package org.am21.utilities;

import org.am21.model.items.Card.*;

import java.util.*;

public class CardUtil {
    // ----------------------------------- ITEM TILE --------------------------------------------------------

    // Each set has 22 cards and 3 colours.
    // Each colour has 7 cards (22/3 = 7), one of the colours has 8 cards.
    private final static int numCards = 7;

    /**
     * This block generate the list of all the item's name
     */
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
    public static Set<Integer> usedNumbers = new HashSet<>();
    public static List<PersonalGoalCard> buildPersonalGoalCard(int numPlayer){
        List<PersonalGoalCard> personalGoalCards = new ArrayList<>();
        for (int i = 0; i < numPlayer; i++) {
            do {
                numPersonalGoal = randomPersonalGoal.nextInt(12) - 1;
            } while (usedNumbers.contains(numPersonalGoal));
            usedNumbers.add(numPersonalGoal);
            personalGoalCards.add(new PersonalGoalCard("PERSONAL_GOALs" + numPersonalGoal, null));
        }
        System.out.println(personalGoalCards.size()+" PersonalGoal generated");
        return personalGoalCards;
    }

    // ----------------------------------- SCORING TOKENS --------------------------------------------------------

    private final static List<ScoringToken> scoringTokens = new ArrayList<>();

    static {
        Collections.addAll(scoringTokens,
                ScoringToken.scoring_8,ScoringToken.scoring_6,
                ScoringToken.scoring_4,ScoringToken.scoring_2);
    }

    // return a set of scoring token cards
    public static List<ScoringTokenCard> buildScoringTokenCards(int numPlayer) {
        List<ScoringTokenCard> scoringTokenCards = new ArrayList<>();

        if (numPlayer == 4) {
            for (int i = 0; i < scoringTokens.size(); i++) {
                ScoringTokenCard scoringTokenCard = new ScoringTokenCard(scoringTokens.get(i));
                scoringTokenCards.add(scoringTokenCard);
            }
        } else if (numPlayer == 3) {
            for (int i = 0; i < scoringTokens.size() - 1; i++) {
                ScoringTokenCard scoringTokenCard = new ScoringTokenCard(scoringTokens.get(i));
                scoringTokenCards.add(scoringTokenCard);
            }
        } else if (numPlayer == 2) {
            for (int i = 0; i < scoringTokens.size() - 1; i = i + 2) {
                ScoringTokenCard scoringTokenCard = new ScoringTokenCard(scoringTokens.get(i));
                scoringTokenCards.add(scoringTokenCard);
            }
        }
        return scoringTokenCards;
    }
}
