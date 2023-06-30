package org.am21.model;

import org.am21.controller.PlayerController;
import org.am21.model.Cards.PersonalGoalCard;
import org.am21.model.enumer.UserStatus;
import org.am21.model.items.Hand;
import org.am21.model.items.Shelf;

/**
 * This class represent the player model with all its data
 *
 * @version 1.0
 */
public class Player {
    private String nickname;
    private UserStatus status;
    private Shelf shelf;
    private PersonalGoalCard myPersonalGoal;
    private Hand hand;
    private PlayerController controller;
    private Match match;
    /**
     * A player score is visible by everyone during the game (CommonGoal tokens and Final Score)
     */
    private int playerScore;
    /**
     * Personal goal points (0-12)
     */
    private int hiddenPoints;


    /**
     * Constructor for player's data initialization
     * At the beginning, ClientGameController are going to create Player with just basic info, without game items
     * Status: Online when accessing the game, not in a match though.
     * Creating a PlayerController for each player account.
     *
     * @param nickname   Nickname of the player
     * @param controller Player's Controller
     */
    public Player(String nickname, PlayerController controller) {
        this.nickname = nickname;
        this.controller = controller;
        this.status = UserStatus.Online;
        this.playerScore = 0;
        this.hiddenPoints = 0;
        this.myPersonalGoal = null;
        this.shelf = null;
        this.match = null;
    }

    /**
     * Get the player's score
     *
     * @return score
     */
    public int getPlayerScore() {

        return playerScore;
    }

    /**
     * Set the player's score
     *
     * @param playerScore score
     */
    public void setPlayerScore(int playerScore) {

        this.playerScore = playerScore;
    }

    /**
     * Get the player's status
     *
     * @return UserStatus
     */
    public UserStatus getStatus() {

        return status;
    }

    /**
     * Set the player's status
     *
     * @param status UserStatus
     */
    public void setStatus(UserStatus status) {

        this.status = status;
    }

    /**
     * Get the player's current match
     *
     * @return Match
     */
    public Match getMatch() {

        return match;
    }

    /**
     * Set the player's current match
     *
     * @param match Match
     */
    public void setMatch(Match match) {

        this.match = match;
    }

    /**
     * Get the player's nickname
     *
     * @return nickname
     */
    public String getNickname() {

        return nickname;
    }

    /**
     * set the player's nickname
     *
     * @param name nickname
     */
    public void setNickname(String name) {

        this.nickname = name;
    }

    /**
     * Get the player's personal goal
     *
     * @return PersonalGoalCard
     */
    public PersonalGoalCard getMyPersonalGoal() {

        return myPersonalGoal;
    }

    /**
     * Set the player's personal goal
     *
     * @param goal PersonalGoalCard
     */
    public void setMyPersonalGoal(PersonalGoalCard goal) {

        this.myPersonalGoal = goal;
    }

    /**
     * Get the player's shelf
     *
     * @return Shelf
     */
    public Shelf getShelf() {

        return shelf;
    }

    /**
     * Set the player's shelf
     *
     * @param shelf Shelf
     */
    public void setShelf(Shelf shelf) {

        this.shelf = shelf;
    }

    /**
     * Get the player's hand
     *
     * @return Hand
     */
    public Hand getHand() {

        return hand;
    }

    /**
     * Set the player's hand
     *
     * @param hand Hand
     */
    public void setHand(Hand hand) {

        this.hand = hand;
    }

    /**
     * Get the player's controller
     *
     * @return PlayerController
     */
    public PlayerController getController() {

        return controller;
    }

    /**
     * Set the player's controller
     *
     * @param controller PlayerController
     */
    public void setController(PlayerController controller) {

        this.controller = controller;
    }

    /**
     * get the player's hidden points
     *
     * @return hiddenPoints
     */
    public int getHiddenPoints() {
        return hiddenPoints;
    }

    /**
     * set the player's hidden points
     *
     * @param hiddenPoints hiddenPoints
     */
    public void setHiddenPoints(int hiddenPoints) {
        this.hiddenPoints = hiddenPoints;
    }
}
