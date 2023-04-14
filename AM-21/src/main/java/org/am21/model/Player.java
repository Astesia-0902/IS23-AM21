package org.am21.model;

import org.am21.controller.PlayerController;
import org.am21.model.Cards.PersonalGoalCard;
import org.am21.model.items.Hand;
import org.am21.model.items.Shelf;
import org.am21.model.enumer.UserStatus;

/**
 * This class represent the player model with all its data
 * @version 1.0
 */
public class Player {
    private String nickname;
    private UserStatus status;
    private String host;
    private Shelf shelf;
    private PersonalGoalCard myPersonalGoal;
    private Hand hand;
    private PlayerController controller;
    private Match match;
    /**
     * A player score is visible by everyone during the game
     */
    private int playerScore;

    /**
     * Constructor for player's data initialization
     * At the beginning, GameController are going to create Player with just basic info, without game items
     * Status: Online when accessing the game, not in a match though.
     * Creating a PlayerController for each player account.
     * @param nickname Nickname of the player
     * @param controller Player's Controller
     */
    public Player(String nickname, PlayerController controller) {
        this.nickname = nickname;
        this.controller = controller;
        this.status = UserStatus.Online;
        this.playerScore = 0;
        this.myPersonalGoal = null;
        this.shelf = null;
        this.match = null;
    }

    public int getPlayerScore() {

        return playerScore;
    }
    public void setPlayerScore(int playerScore) {

        this.playerScore = playerScore;
    }
    public UserStatus getStatus() {

        return status;
    }
    public void setStatus(UserStatus status) {

        this.status = status;
    }

    public Match getMatch() {

        return match;
    }

    public void setMatch(Match match) {

        this.match = match;
    }


    public String getNickname() {

        return nickname;
    }
    public void setNickname(String name){

        this.nickname = name;
    }
    public String getHost() {

        return host;
    }
    public void setHost(String host) {

        this.host = host;
    }

    public PersonalGoalCard getMyPersonalGoal() {

        return myPersonalGoal;
    }

    public void setMyPersonalGoal(PersonalGoalCard goal){

        this.myPersonalGoal = goal;
    }
    public Shelf getShelf(){

        return shelf;
    }
    public void setShelf(Shelf shelf) {

        this.shelf = shelf;
    }
    public Hand getHand() {

        return hand;
    }
    public void setHand(Hand hand){

        this.hand = hand;
    }
    public PlayerController getController() {

        return controller;
    }
    public void setController(PlayerController controller) {

        this.controller = controller;
    }
}
