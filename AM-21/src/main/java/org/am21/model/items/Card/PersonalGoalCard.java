package org.am21.model.items.Card;

import org.am21.model.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PersonalGoalCard extends Card {
    private final Player player;
    private int currentScore;

    private final static List<String> tileNames = new ArrayList<>();
    static {
            Collections.addAll(tileNames,
                    ItemType.Cats.name(), ItemType.Books.name(),
                    ItemType.Games.name(), ItemType.Frames.name(),
                    ItemType.Trophies.name(), ItemType.Plants.name());
    }

    public static int[][] values;


    public PersonalGoalCard(String nameCard, Player player) {
        super(nameCard);
        this.player = player;
        this.currentScore = 0;
    }

    // return the number of completed goals
    public int checkGoal(){
        switch (this.player.getMyPersonalGoal().getNameCard()){
            case "PERSONAL_GOALs":
                // values :
                // param1 : row of player's bookshelf
                // param2 : column of player's bookshelf
                // param3 : index of tileNames
                // Cats(0)
                // Books(1)
                // Games(2)
                // Frames(3)
                // Trophies(4)
                // Plants(5)
                values = new int[][]{{0, 0, 5}, {0, 2, 3}, {1, 4, 0}, {2, 3, 1}, {3, 1, 2}, {5, 3, 4}};
                break;
            case "PERSONAL_GOALs2":
                values = new int[][]{{1, 1, 5}, {2, 0, 0}, {2, 2, 2}, {3, 4, 1}, {4, 3, 4}, {5, 4, 3}};
                break;
            case "PERSONAL_GOALs3":
                values = new int[][]{{1, 0, 3}, {1, 3, 2}, {2, 2, 5}, {3, 1, 0}, {3, 4, 4}, {5, 0, 1}};
                break;
            case "PERSONAL_GOALs4":
                values = new int[][]{{0, 4, 2}, {2, 0, 4}, {2, 2, 3}, {3, 3, 5}, {4, 1, 1}, {4, 2, 0}};
                break;
            case "PERSONAL_GOALs5":
                values = new int[][]{{1, 1, 4}, {3, 1, 3}, {3, 2, 1}, {4, 4, 5}, {5, 4, 2}, {5, 3, 0}};
                break;
            case "PERSONAL_GOALs6":
                values = new int[][]{{0, 2, 4}, {0, 4, 0}, {2, 3, 1}, {4, 1, 2}, {4, 3, 3}, {5, 0, 5}};
                break;
            case "PERSONAL_GOALs7":
                values = new int[][]{{0, 0, 0}, {1, 3, 3}, {2, 1, 5}, {3, 0, 4}, {4, 4, 2}, {5, 2, 1}};
                break;
            case "PERSONAL_GOALs8":
                values = new int[][]{{0, 4, 3}, {1, 1, 0}, {2, 2, 4}, {3, 0, 5}, {4, 3, 1}, {5, 3, 2}};
                break;
            case "PERSONAL_GOALs9":
                values = new int[][]{{0, 0, 2}, {2, 2, 0}, {3, 4, 1}, {4, 1, 4}, {4, 4, 5}, {5, 0, 3}};
                break;
            case "PERSONAL_GOALs10":
                values = new int[][]{{0, 4, 4}, {1, 1, 2}, {2, 0, 1}, {3, 3, 0}, {4, 1, 3}, {5, 3, 5}};
                break;
            case "PERSONAL_GOALs11":
                values = new int[][]{{0, 2, 5}, {1, 1, 1}, {2, 0, 2}, {3, 2, 3}, {4, 4, 0}, {5, 3, 4}};
                break;
            case "PERSONAL_GOALs12":
                values = new int[][]{{0, 2, 1}, {1, 1, 5}, {2, 2, 3}, {3, 3, 4}, {4, 4, 2}, {5, 0, 0}};
        }
        int count = 0;
        for (int i = 0; i < values.length; i++) {
            int row = values[i][0];
            int col = values[i][1];
            int val = values[i][2];

            // Compare the items on the player's bookshelf(row, col) with the items required by Personal Goal
            if(player.myShelf.getItemName(row, col).equals(tileNames.get(val))){
                count++;
            }
        }
        return count;
    }

    // return the number of points the player has scored
    public int calculatePoints() {
        int count = checkGoal();
        switch (count){
            case 1:
                currentScore = 1;
                break;
            case 2:
                currentScore = 2;
                break;
            case 3:
                currentScore = 4;
                break;
            case 4:
                currentScore = 6;
                break;
            case 5:
                currentScore = 9;
                break;
            case 6:
                currentScore = 12;
                break;
        }
        return currentScore;
    }
}