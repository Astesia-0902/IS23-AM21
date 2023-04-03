package org.am21.controller;

import org.am21.model.Hand;
import org.am21.model.Player;
import org.am21.model.TurnPhases;
import org.am21.model.items.Card.PersonalGoalCard;
import org.am21.model.items.LivingRoomBoard;
import org.am21.utilities.Coordinates;
import org.am21.utilities.TGear;

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
     *
     * @param r
     * @param c
     * @return false : Selection failed
     */
    public boolean selectCell(int r,int c){
        System.out.println(player.getName() + " > Select: [" + r + "][" + c + "]");
        // verify if it is player turn
        if(!isMyTurn(player)) {
            return false;
        }
        if (player.getMatch().turnPhase != TurnPhases.Selection) {
            System.out.println("Match > Not Selection Phase");
            return false;
        }

        if(player.shelf.insertLimit == hand.getSlot().size()){
            System.out.println("Shelf > Cannot pick more item");
            System.out.println("Shelf > Hand["+hand.getSlot().size()+"]-Limit ["+player.shelf.insertLimit +"]");
            return false;
        }

        if(hand.getSlot().size()==3){
            System.out.println("Board[!] > Hand full. If you want right-click and unselect");
            return false;
        }

        LivingRoomBoard tmpBoard = player.getMatch().livingRoomBoard;

        //TODO: elimina getItemTileCard dall'if, quindi è necessario modificare la struttura della board,
        //      dove le celle dark non contengono nessun oggetto Cell. La cella non deve esistere
        if (tmpBoard.getCellGrid()[r][c].getItem() == null) {
            System.out.println("Board[!] > Empty cell. Try again");
            return false;
        }

        if (tmpBoard.isSelectable(r, c) == true) {
            System.out.println("Board > Cell selectable");
            /**If the cell is selectable then verify second condition*/

            if (hand.getSlot().size() == 0) {

                System.out.println("Board > Empty hand - No Orthogonality check");

            } else {
                //Controllo se è già stato selezionato
                for (Coordinates tmp : hand.getSlot()) {
                    if ((r == tmp.x) && (c == tmp.y)) {
                        //Gia selezionato
                        System.out.println("Board[!] > Already selected. Try again.");
                        return false;
                    }
                }

                //Iteriamo slot
                //Condizione 2 (Ortogonalità):
                //NewSelected Cell need to be adjacent to the other in slot.
                //NewSelected Cell need to be in a straight line.
                /**Coordinates have been filtered,
                 * *  so they are valid for Orthogonality check*/
                if (tmpBoard.isOrthogonal(r, c, hand) == false) {
                    System.out.println("Board > Not Orthogonal");
                    return false;
                } else {
                    System.out.println("Board > Orthogonal");
                }
            }
            /**salvo le coordinate e il riferimento dell'item nella hand*/
            hand.memCard(tmpBoard.getItemInCell(r, c), r, c);
            System.out.println("Match > Item selected: [" + tmpBoard.getItemInCell(r, c).getNameCard() + "]");
            return true;
        } else {
            //Questo messaggio sara tolto e messo in ClientInputHandler o nelle funzioni dei test
            System.out.println("Match > Selection Failed");
            return false;
        }
    }

    /**
     * Triggered maybe by right-click
     * During Selection Phase:
     * Clear Hand. The Player need to reselect all the cells
     */
    public void unselectCard(){
        if(!isMyTurn(player)) {
            return;
        }
        if(player.match.turnPhase == TurnPhases.Selection) {
            hand.clearHand();
        }else{
            System.out.println("Match[!] > Not selection phase");
        }
    }

    /**
     * Triggered maybe by a Confirm Selection Button
     * Or by the timer
     * It will ask the match to change TurnPhase in Insertion
     */
    public void callEndSelection(){
        if(!isMyTurn(player)) {
            return;
        }
        player.match.changeTurnPhase(TurnPhases.Insertion);
    }

    /**
     * During Insertion Phase.
     * Item in hand will be removed from Board through slot iteration.
     */
    public void moveAllToHand(){
        if(!isMyTurn(player)) {
            return;
        }
        if(player.getMatch().turnPhase == TurnPhases.Insertion){
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
        if(!isMyTurn(player)) {
            return false;
        }
        if(player.match.turnPhase == TurnPhases.Insertion){

            System.out.println(player.getName()+" > Column: ["+col+"]");

            if(player.shelf.slotCol.get(col)<hand.getSlot().size()){
                System.out.println("Shelf[!] > Not enough space");
                for(int x: player.shelf.slotCol){
                    System.out.print("["+x+"]");
                }
                System.out.println("");
                System.out.println("Shelf > Limit: ["+player.shelf.insertLimit +"]");
                return false;
            }else{
                for(int i=hand.getSlot().size(),s=0;i>0;i--,s++){

                    player.shelf.insertCard2(hand.getSlot().get(s).item,col);
                    System.out.println("Shelf > Insert...");
                }
                hand.clearHand();
                player.shelf.elaborateLimit();
                TGear.printThisShelf(player.shelf);
                callEndInsertion();
                return true;
            }
        }
        return false;
    }


    /**
     * This method will call another one in Hand to swap the position of two cards
     */
    public void changeHandOrder(int pos1,int pos2){
        if(!isMyTurn(player)) {
            return;
        }
        if(hand.getSlot().size()>=2){
            hand.changeOrder(pos1,pos2);
            System.out.println("Hand > Order Changed");
        }

    }

    public boolean isMyTurn(Player player){
        if(player.match.currentPlayer != player) {
            System.out.println("Match > Not your turn, "+ player.getName());
            return false;
        }
        return true;
    }

    public void callEndInsertion(){
        if(!isMyTurn(player)) {
            return;
        }
        player.match.changeTurnPhase(TurnPhases.GoalChecking);
        player.match.checkingGoals(player);
    }

}
