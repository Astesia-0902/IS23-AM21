package org.am21.controller;

import org.am21.model.Hand;
import org.am21.model.Player;
import org.am21.model.TurnPhases;
import org.am21.model.items.Card.PersonalGoalCard;
import org.am21.model.items.LivingRoomBoard;

public class PlayerController {
    public Player player;
    public Hand hand;



    /**
     * PlayerController constructor is initialized by GameController, when ClientInputHandler login.
     *
     */
    public PlayerController(String nickname){

        this.player = new Player(nickname);
        this.hand = player.hand;
    }

    /**
     * Command to set player's nickname
     * @param newName
     */
    public void changeName(String newName){
        player.setNickname(newName);
    }

    /**
     * Command to see my own goal
     * !Implementation incomplete due to Lack of View Component!
     * @return
     */
    public PersonalGoalCard viewMyGoal(){

        return player.getMyPersonalGoal();
    }


    /**
     * During SelectionCards of TurnPhases, when the player click on an item,
     * the command will memorize the item position and reference in the PlayerHand
     * if is Selectable(at least one item adjacent)
     * and if is Orthogonal to the other selected cards
     *
     * To revisit
     * TODO: Condition: how many cards can i select in relation to how many free cells there are in Shelf
     * @param r
     * @param c
     * @return
     */
    public boolean selectCard(int r,int c){
            if (player.getMatch().turnPhase != TurnPhases.Selection) {
                System.out.println("Not Selection Time");
                return false;
            }
            LivingRoomBoard tmpBoard = player.getMatch().livingRoomBoard;

            if (tmpBoard.isSelectable(r, c) == true) {
                for (int i = 1; i <= hand.getNumCards(); i++) {
                    if (tmpBoard.isOrthogonal(r, c, player.hand, i) == false) {
                        System.out.println("Not Orthogonal");
                        return false;
                    }
                }
                /**salvo le coordinate e il riferimento dell'item nella hand*/
                hand.setSlot(tmpBoard.getItem(r, c), hand.getNumCards(), r, c);
                hand.setNumCards(hand.getNumCards() + 1);

                return true;
            } else {
                System.out.println("Not Selectable");
                return false;
            }
    }

    /**
     * During Selection Phase
     * Selecting a card in player hand, it will put it back to his original position in the Board
     * @param slotNum
     */
    public void unselectCard(int slotNum){
        if(player.match.turnPhase == TurnPhases.Selection) {

            hand.setSlot(null, slotNum, -1, -1);
            hand.setNumCards(hand.getNumCards() - 1);
        }
    }

    /**
     * During Insertion Phase.
     * Item removal from Board through slots iteration.
     */
    public void moveAllToHand(){
        if(player.getMatch().turnPhase != TurnPhases.Insertion){
            int x=0;
            int y=0;
            for(int i=0;i<hand.getNumCards();i++){
                x = hand.getSlot().get(i).x;
                y = hand.getSlot().get(i).y;
                player.match.livingRoomBoard.getCells()[x][y].setItemTileCard(null);
            }
        }

    }

    /**
     * Request for the Shelf to insert all the selected cards in a column(col)
     * @param col
     * @return
     */
    public boolean tryToInsert(int col){



        return false;
    }


    /**
     * This method will call another one in Hand to swap the position of two cards
     */
    public void changeHandOrder(int pos1,int pos2){
        hand.changeOrder(pos1,pos2);
    }

}
