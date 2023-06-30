package org.am21.model.Cards;

import org.am21.model.Player;
import org.am21.model.items.Shelf;
import org.am21.utilities.CardUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the abstract class of the Common Goals
 */
public abstract class CommonGoal extends Card {
    public List<Integer> tokenStack;
    public List<Player> achievedPlayers;
    public int index;

    /**
     * Constructor
     *
     * @param nameCard the name of the card
     */
    public CommonGoal(String nameCard, int numPlayer) {
        super(nameCard);
        this.tokenStack = CardUtil.buildScoringTokenCards(numPlayer);
        this.index = 0;
        achievedPlayers = new ArrayList<>();
    }

    /**
     * During the CheckingGoal phase of the turn, the Player's Shelf is checked to see if the goal has been completed
     *
     * @param shelf Shelf del Player
     * @return true if match
     */
    public abstract boolean checkGoal(Shelf shelf);


    /**
     * When a CommonGoal is achieved,
     * the player is going to be added to the AchievedPlayers List
     * A player can achieve the goal just one time, so if, for any reasons, it's achieved again, nothing happens
     *
     * @param player player who has achieved Common Goal
     */
    public void commonGoalAchieved(Player player) {
        if (!achievedPlayers.contains(player) && tokenStack.size() > 0) {
            achievedPlayers.add(player);

            player.getController().addScore(tokenStack.get(0));
            tokenStack.remove(0);
        }
    }

}



