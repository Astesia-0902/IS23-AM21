package org.am21.model;

import org.am21.controller.PlayerController;
import org.am21.model.items.Card.ItemTileCard;
import org.am21.model.items.Card.PersonalGoalCard;
import org.am21.model.items.Card.ScoringTokenCard;
import org.am21.model.items.Shelf;
import org.am21.model.PlayerHand;
import org.am21.model.user.UserProfile;

import java.util.ArrayList;
import java.util.List;


public class Player {
    public UserProfile user;
    private final String nickname;
    /**
     * A player score is visible by all during a game
     */
    public int playerScore;
    public Shelf myShelf;
    private final PersonalGoalCard myPersonalGoal;
    /**
     * player seat number used to determine playing order 1-4 (counterclockwise)
     */
    public int playerSeat;
    /**
     * memorize 1 to 3 cards selected by player during a turn
     */
    public PlayerHand playerHand;

    public PlayerController controller;


    /**
     * Constructor for player's data initialization
     * -Eventually add a Shelf ID
     */
    public Player(UserProfile user,String nickname,int seat, PersonalGoalCard playerPersonalGoal) {
        this.user= user;
        myShelf = new Shelf();
        myShelf.player = this;
        this.nickname = nickname;
        this.myPersonalGoal = playerPersonalGoal;
        this.playerSeat = seat;
        this.playerHand = new PlayerHand(this);
        this.playerScore = 0;
    }

    public String getName() {
        return nickname;
    }

    public int getPlayerScore(){
        return playerScore;
    }

    public PersonalGoalCard getMyPersonalGoal() {
        return myPersonalGoal;
    }

    public Shelf getMyShelf(){
        return myShelf;
    }




    /**
     * add Item to Player's Hand
     * @param item
     */
    public void addItemToHand(ItemTileCard item){
        //playerHand.add(item);

    }

    /**
     * remove Item from Player's Hand
     * @param item
     */
    public void removeItemFromHand(ItemTileCard item){
        //playerHand.remove(item);
    }

    /**
     * 
     *     Cambio ordine delle carte selezionate
     *     !!!!Rivedere il sistema di controllo dei valori che siano inferiori al numero di carte selezionate
     *
     * @param pos1
     * @param pos2
     */
    public void changeHandOrder(int pos1,int pos2){
        /** Ordine crescente (la prima carta è index-0 e l'ultima è potenzialmente index-2)*/
        //ItemTileCard tmp = playerHand.get(pos1);
        //playerHand.set(pos1,playerHand.get(pos2));
        //playerHand.set(pos2,tmp);

    }

}
