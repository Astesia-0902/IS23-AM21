package org.am21.model.Cards;

import org.am21.model.Player;
import org.am21.model.items.Shelf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PersonalGoalCard extends Card {
    private Shelf GoalShelf;
    private final static HashMap<String, int[][]> personalGoal = new HashMap<>();
    private final static List<ItemCard> tileNames = new ArrayList<>();
    private final static HashMap<Integer, Integer> currentScore = new HashMap<>();
    static {
        /** {row of shelf, column of shelf, index of tileNames}*/
        //  index of tileNames: Cats(0), Books(1), Games(2), Frames(3), Trophies(4), Plants(5)

        personalGoal.put("PERSONAL_GOALs1", new int[][]{{0, 0, 5}, {0, 2, 3}, {1, 4, 0}, {2, 3, 1}, {3, 1, 2}, {5, 2, 4}});
        personalGoal.put("PERSONAL_GOALs2", new int[][]{{1, 1, 5}, {2, 0, 0}, {2, 2, 2}, {3, 4, 1}, {4, 3, 4}, {5, 4, 3}});
        personalGoal.put("PERSONAL_GOALs3", new int[][]{{1, 0, 3}, {1, 3, 2}, {2, 2, 5}, {3, 1, 0}, {3, 4, 4}, {5, 0, 1}});
        personalGoal.put("PERSONAL_GOALs4", new int[][]{{0, 4, 2}, {2, 0, 4}, {2, 2, 3}, {3, 3, 5}, {4, 1, 1}, {4, 2, 0}});
        personalGoal.put("PERSONAL_GOALs5", new int[][]{{1, 1, 4}, {3, 1, 3}, {3, 2, 1}, {4, 4, 5}, {5, 0, 2}, {5, 3, 0}});
        personalGoal.put("PERSONAL_GOALs6", new int[][]{{0, 2, 4}, {0, 4, 0}, {2, 3, 1}, {4, 1, 2}, {4, 3, 3}, {5, 0, 5}});
        personalGoal.put("PERSONAL_GOALs7", new int[][]{{0, 0, 0}, {1, 3, 3}, {2, 1, 5}, {3, 0, 4}, {4, 4, 2}, {5, 2, 1}});
        personalGoal.put("PERSONAL_GOALs8", new int[][]{{0, 4, 3}, {1, 1, 0}, {2, 2, 4}, {3, 0, 5}, {4, 3, 1}, {5, 3, 2}});
        personalGoal.put("PERSONAL_GOALs9", new int[][]{{0, 2, 2}, {2, 2, 0}, {3, 4, 1}, {4, 1, 4}, {4, 4, 5}, {5, 0, 3}});
        personalGoal.put("PERSONAL_GOALs10", new int[][]{{0, 4, 4}, {1, 1, 2}, {2, 0, 1}, {3, 3, 0}, {4, 1, 3}, {5, 3, 5}});
        personalGoal.put("PERSONAL_GOALs11", new int[][]{{0, 2, 5}, {1, 1, 1}, {2, 0, 2}, {3, 2, 3}, {4, 4, 0}, {5, 3, 4}});
        personalGoal.put("PERSONAL_GOALs12", new int[][]{{0, 2, 1}, {1, 1, 5}, {2, 2, 3}, {3, 3, 4}, {4, 4, 2}, {5, 0, 0}});

        Collections.addAll(tileNames,
                new ItemCard(ItemType.__Cats__.name()), new ItemCard(ItemType._Books__.name()),
                new ItemCard(ItemType._Games__.name()), new ItemCard(ItemType._Frames_.name()),
                new ItemCard(ItemType.Trophies.name()), new ItemCard(ItemType._Plants_.name()));

        currentScore.put(1,1);
        currentScore.put(2,2);
        currentScore.put(3,4);
        currentScore.put(4,6);
        currentScore.put(5,9);
        currentScore.put(6,12);
    }

    public PersonalGoalCard(String nameCard) {
        super(nameCard);
    }

    /**
     * Function to check the goal if matched
     * @return the number of goal completed
     */
    public int checkGoal() {
        System.out.println("Match > Checking PersonalGoal achievement: ");
        int[][] values = personalGoal.get(GoalShelf.player.getMyGoal().getNameCard());
        int count = 0;
        for (int i = 0; i < values.length; i++) {
            int row = values[i][0];
            int col = values[i][1];
            int val = values[i][2];

            // Compare the items on the player's bookshelf(row, col) with the items required by Personal Goal
            if (GoalShelf.player.shelf.getItemName(row, col) != null &&
                    GoalShelf.player.shelf.getItemType(row,col)
                            .equals(tileNames.get(val).getNameCard())) {
                System.out.println("Shelf > ["+row+ "]"+"["+col+ "]: " + GoalShelf.player.shelf.getItemType(row,col));
                System.out.println("Match > +1 item matched!");
                count++;
            }
        }
        return count;
    }

    /**
     * Function to calculate the scores of player to get
     * @return current score of player
     */
    public int calculatePoints(){
        int count = checkGoal();
        return currentScore.getOrDefault(count, 0);
    }



    /**
     * Function to get recent goal position matched in the shelf of the player
     * @return goal shelf with color matches
     */
    public Shelf setupGoalShelf(Player player) {
        this.GoalShelf = new Shelf(player);
        int[][] values = personalGoal.get(this.getNameCard());

        for (int i = 0; i < values.length; i++) {
            int row = values[i][0];
            int col = values[i][1];
            int val = values[i][2];
            this.GoalShelf.setCell(row, col, tileNames.get(val));
        }
        return GoalShelf;
    }

    public Shelf getGoalShelf(){
        return this.GoalShelf;
    }


}