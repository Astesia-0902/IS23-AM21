package org.am21.controller;

import org.am21.model.Hand;
import org.am21.model.Player;
import org.am21.model.TurnPhases;
import org.am21.model.items.Card.PersonalGoalCard;
import org.am21.model.items.LivingRoomBoard;
import org.am21.utilities.Coordinates;

public class PlayerController {
    public Player player;
    public Hand hand;



    /**
     * PlayerController constructor is initialized by GameController, when ClientInputHandler login.
     * It will create the player and add his reference
     *
     */
    public PlayerController(String nickname){

        this.player = new Player(nickname,this);
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
    public boolean selectCell(int r,int c){
        //TODO: verify if is player turn
            if (player.getMatch().turnPhase != TurnPhases.Selection) {
                System.out.println("Not Selection Phase");
                return false;
            }

            LivingRoomBoard tmpBoard = player.getMatch().livingRoomBoard;

            System.out.println(player.getName()+" selected: [" + r + "][" + c+ "]");
            //TODO: se slot ha gia 3 elem, dico che la mano è piena, prova a deselezionare

            //TODO: elimina getItemTileCard dall'if, quindi è necessario modificare la struttura della board,
            //      dove le celle dark non contengono nessun oggetto Cell. La cella non deve esistere
            if(tmpBoard.getCellGrid()[r][c].getItem()==null){
                System.out.println("Empty cell. Try again");
                return false;
            }

            if (tmpBoard.isSelectable(r, c) == true) {
                /**If the cell is selectable then verify second condition*/

                if(hand.getSlot().size()==0) {

                    System.out.println("Empty hand - No Orthogonality check");

                }else{
                    //Controllo se è già stato selezionato
                    for(Coordinates tmp:hand.getSlot()){
                        if((r == tmp.x) && (c == tmp.y)){
                            //Gia selezionato
                            System.out.println("Already selected. Try again.");
                            return false;
                        }
                    }

                    //Iteriamo slot
                    //Condizione 2 (Ortogonalità):
                    //NewSelected Cell need to be adjacent to the other in slot.
                    //NewSelected Cell need to be in a straight line.
                        /**Coordinates have been filtered,
                         *  so they are valid for Orthogonality check*/
                        if (tmpBoard.isOrthogonal(r, c,hand) == false) {
                            System.out.println("Not Orthogonal");
                            return false;
                        }else {
                            System.out.println("Orthogonal");
                        }
                }
                /**salvo le coordinate e il riferimento dell'item nella hand*/
                hand.memCard(tmpBoard.getItemInCell(r,c),r,c);

                System.out.println("Item selected: ["+tmpBoard.getItemInCell(r,c).getNameCard()+"]");
                return true;
            } else {
                System.out.println("Selection Failed");
                return false;
            }
    }

    /**
     * Triggered maybe by right-click
     * During Selection Phase:
     * Clear Hand. The Player need to reselect all the cells
     */
    public void unselectCard(){
        if(player.match.turnPhase == TurnPhases.Selection) {
            hand.clearHand();
        }else{
            System.out.println("Not selection phase");
        }
    }

    /**
     * Triggered maybe by a Confirm Selection Button
     * Or by the timer
     * It will ask the match to change TurnPhase in Insertion
     */
    public void callEndSelection(){
        player.match.changeTurnPhase(TurnPhases.Insertion);
    }

    /**
     * During Insertion Phase.
     * Item in hand will be removed from Board through slot iteration.
     */
    public void moveAllToHand(){
        if(player.getMatch().turnPhase != TurnPhases.Insertion){
            for(Coordinates card: hand.getSlot()){
                player.match.livingRoomBoard.insertInCell(card.x,card.y,null);
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
