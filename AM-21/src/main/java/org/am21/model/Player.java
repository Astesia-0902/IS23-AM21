package org.am21.model;

import org.am21.controller.PlayerController;
import org.am21.model.items.Card.PersonalGoalCard;
import org.am21.model.items.Shelf;


public class Player {

    private String nickname;

    public UserStatus status;

    private String host;

    /**
     * A player score is visible by everyone during the game
     */
    public int playerScore;
    public Shelf shelf;
    private PersonalGoalCard myPersonalGoal;
    /**
     * player seat number used to determine playing order 1-4 (counterclockwise)
     */
    public int playerSeat;
    /**
     * memorize 1 to 3 cards selected by player during a turn
     */
    public Hand hand;

    public PlayerController controller;

    public Match match;


    /**
     * Constructor for player's data initialization
     * At the beginning, GameController are going to create Player with just basic info, without game utilities
     * Status: Online when accessing the game, not in a match though.
     * Creating a PlayerController for each player account.
     * MyManager will be assigned when the player join a match.
     */
    public Player(String nickname, PlayerController controller) {
        this.nickname = nickname;
        this.controller = controller;
        this.status = UserStatus.Online;
        this.playerScore = 0;
        this.myPersonalGoal = null;
        this.shelf = null;
        this.hand = null;
        this.playerSeat = 0;
        this.match = null;
    }

    public String getName() {
        return nickname;
    }

    public void setNickname(String name){ this.nickname = name;}
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public int getPlayerScore(){
        return playerScore;
    }

    public PersonalGoalCard getMyPersonalGoal() {
        return myPersonalGoal;
    }

    public void setOwnGoal(PersonalGoalCard goal){
        this.myPersonalGoal = goal;
    }
    public Shelf getShelf(){
        return shelf;
    }
    public Match getMatch(){
        return match;
    }

    public void setHand(Hand hand){
        this.hand = hand;
    }

    /**
     * Creation of the Player's Hand when joinMatch
     * Assign Hand reference to Player and PlayerController
     * @return
     */
    public void createHand(){
        this.hand = new Hand(this);
        this.controller.hand = this.hand;
        System.out.println("Match > "+getName()+"'s Hand created and assigned.");
    }


}
