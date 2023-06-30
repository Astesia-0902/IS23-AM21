package org.am21.model.Cards;

import org.am21.model.Player;
import org.am21.model.items.Shelf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * This class is the abstract class of the cards
 */
public class PersonalGoalCard extends Card {
    private Shelf PersonalGoalShelf;
    public Player player;
    private final static HashMap<String, int[][]> personalGoal = new HashMap<>();
    private final static List<ItemCard> tileNames = new ArrayList<>();
    private final static HashMap<Integer, Integer> currentScore = new HashMap<>();

    static {
        /*
         * {row of shelves, column of shelves, index of tileNames}
         * */
        //  index of tileNames: Cats(0), Books(1), Games(2), Frames(3), Trophies(4), Plants(5)

        personalGoal.put("PERSONAL_GOAL01", new int[][]{{0, 0, 5}, {0, 2, 3}, {1, 4, 0}, {2, 3, 1}, {3, 1, 2}, {5, 2, 4}});
        personalGoal.put("PERSONAL_GOAL02", new int[][]{{1, 1, 5}, {2, 0, 0}, {2, 2, 2}, {3, 4, 1}, {4, 3, 4}, {5, 4, 3}});
        personalGoal.put("PERSONAL_GOAL03", new int[][]{{1, 0, 3}, {1, 3, 2}, {2, 2, 5}, {3, 1, 0}, {3, 4, 4}, {5, 0, 1}});
        personalGoal.put("PERSONAL_GOAL04", new int[][]{{0, 4, 2}, {2, 0, 4}, {2, 2, 3}, {3, 3, 5}, {4, 1, 1}, {4, 2, 0}});
        personalGoal.put("PERSONAL_GOAL05", new int[][]{{1, 1, 4}, {3, 1, 3}, {3, 2, 1}, {4, 4, 5}, {5, 0, 2}, {5, 3, 0}});
        personalGoal.put("PERSONAL_GOAL06", new int[][]{{0, 2, 4}, {0, 4, 0}, {2, 3, 1}, {4, 1, 2}, {4, 3, 3}, {5, 0, 5}});
        personalGoal.put("PERSONAL_GOAL07", new int[][]{{0, 0, 0}, {1, 3, 3}, {2, 1, 5}, {3, 0, 4}, {4, 4, 2}, {5, 2, 1}});
        personalGoal.put("PERSONAL_GOAL08", new int[][]{{0, 4, 3}, {1, 1, 0}, {2, 2, 4}, {3, 0, 5}, {4, 3, 1}, {5, 3, 2}});
        personalGoal.put("PERSONAL_GOAL09", new int[][]{{0, 2, 2}, {2, 2, 0}, {3, 4, 1}, {4, 1, 4}, {4, 4, 5}, {5, 0, 3}});
        personalGoal.put("PERSONAL_GOAL10", new int[][]{{0, 4, 4}, {1, 1, 2}, {2, 0, 1}, {3, 3, 0}, {4, 1, 3}, {5, 3, 5}});
        personalGoal.put("PERSONAL_GOAL11", new int[][]{{0, 2, 5}, {1, 1, 1}, {2, 0, 2}, {3, 2, 3}, {4, 4, 0}, {5, 3, 4}});
        personalGoal.put("PERSONAL_GOAL12", new int[][]{{0, 2, 1}, {1, 1, 5}, {2, 2, 3}, {3, 3, 4}, {4, 4, 2}, {5, 0, 0}});

        Collections.addAll(tileNames,
                new ItemCard(ItemType.__Cats__.name()), new ItemCard(ItemType._Books__.name()),
                new ItemCard(ItemType._Games__.name()), new ItemCard(ItemType._Frames_.name()),
                new ItemCard(ItemType.Trophies.name()), new ItemCard(ItemType._Plants_.name()));

        currentScore.put(1, 1);
        currentScore.put(2, 2);
        currentScore.put(3, 4);
        currentScore.put(4, 6);
        currentScore.put(5, 9);
        currentScore.put(6, 12);
    }

    /**
     * Constructor
     *
     * @param nameCard the name of the card
     */
    public PersonalGoalCard(String nameCard) {

        super(nameCard);
    }

    /**
     * Function to check the goal if matched
     *
     * @return the number of goal completed
     */
    public int checkGoal() {

        int[][] values = personalGoal.get(player.getMyPersonalGoal().getNameCard());
        int count = 0;
        for (int[] value : values) {
            int row = value[0];
            int col = value[1];
            int val = value[2];

            // Compare the items on the player's bookshelf(row, col) with the items required by Personal Goal
            if (player.getShelf().getItemName(row, col) != null &&
                    player.getShelf().getItemType(row, col)
                            .equals(tileNames.get(val).getNameCard())) {
                count++;
            }
        }
        return count;
    }

    /**
     * Function to calculate the scores of player to get
     *
     * @return current score of player
     */
    public int calculatePoints() {
        int count = checkGoal();
        return currentScore.getOrDefault(count, 0);
    }

    /**
     * Function to get recent goal position matched in the shelves of the player
     * Used for CLI
     */
    public void setupGoalShelf(Player player) {
        this.PersonalGoalShelf = new Shelf(player);
        int[][] values = personalGoal.get(this.getNameCard());

        for (int[] value : values) {
            int row = value[0];
            int col = value[1];
            int val = value[2];
            this.PersonalGoalShelf.setCell(row, col, tileNames.get(val));
        }
    }

    /**
     * Function to get recent goal position matched in the shelves of the player
     * Used for Test
     *
     * @return goal shelves with color matches
     */
    public Shelf getPersonalGoalShelf() {
        return this.PersonalGoalShelf;
    }

}