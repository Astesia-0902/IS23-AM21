package org.am21.model.items.Card;

import org.am21.model.Player;
import org.am21.model.items.Shelf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PersonalGoalCard extends Card {
    private Shelf MyPersonalGoalShelf;
    private final static HashMap<String, int[][]> personalGoal = new HashMap<>();
    private final static List<ItemTileCard> tileNames = new ArrayList<>();
    private final static HashMap<Integer, Integer> currentScore = new HashMap<>();
    static {
        // param1 : row of player's bookshelf
        // param2 : column of player's bookshelf
        // param3 : index of tileNames: Cats(0), Books(1), Games(2), Frames(3), Trophies(4), Plants(5)

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
                new ItemTileCard(ItemType.__Cats__.name()), new ItemTileCard(ItemType._Books__.name()),
                new ItemTileCard(ItemType._Games__.name()), new ItemTileCard(ItemType._Frames_.name()),
                new ItemTileCard(ItemType.Trophies.name()), new ItemTileCard(ItemType._Plants_.name()));

        currentScore.put(1,1);
        currentScore.put(2,2);
        currentScore.put(3,4);
        currentScore.put(4,6);
        currentScore.put(5,9);
        currentScore.put(6,12);
    }

    public static int[][] values;


    public PersonalGoalCard(String nameCard) {
        super(nameCard);
    }

    // return the number of completed goals
    public int checkGoal(){
        int[][] values = personalGoal.get(MyPersonalGoalShelf.player.getMyPersonalGoal().getNameCard());
        int count = 0;
        for (int i = 0; i < values.length; i++) {
            int row = values[i][0];
            int col = values[i][1];
            int val = values[i][2];
            if (MyPersonalGoalShelf.player.myShelf.getItemName(row, col) != null){
            System.out.println("Checking: " + MyPersonalGoalShelf.player.myShelf.
                    getItemName(row, col).substring(0, MyPersonalGoalShelf.player.myShelf.getItemName(row, col).length() - 3));
        }
            // Compare the items on the player's bookshelf(row, col) with the items required by Personal Goal
            if (MyPersonalGoalShelf.player.myShelf.getItemName(row, col) != null &&
                    MyPersonalGoalShelf.player.myShelf.getItemName(row, col).substring(0,
                            MyPersonalGoalShelf.player.myShelf.getItemName(row, col).length() - 3)
                            .equals(tileNames.get(val).getNameCard())) {

                count++;
            }
        }
        return count;
    }

    // return the number of points the player has scored
    public int calculatePoints() {
        int count = checkGoal();
        return currentScore.getOrDefault(count, 0);
    }

    public static int getPersonaGoalCardSize() {
        return personalGoal.size();
    }

    public void setPlayer(Player player) {
        this.MyPersonalGoalShelf = new Shelf(player);
    }

    public Shelf getMyPersonalGoalShelf() {
        int[][] values = personalGoal.get(MyPersonalGoalShelf.player.getMyPersonalGoal().getNameCard());

        for (int i = 0; i < values.length; i++) {
            int row = values[i][0];
            int col = values[i][1];
            int val = values[i][2];
            this.MyPersonalGoalShelf.insertInCell(row, col, tileNames.get(val));
        }
        return MyPersonalGoalShelf;
    }
}