package org.am21.controller;

import org.am21.model.Cards.PersonalGoalCard;
import org.am21.model.Player;
import org.am21.model.enumer.TurnPhases;
import org.am21.model.items.Board;
import org.am21.model.items.Hand;
import org.am21.utilities.CardPointer;




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
        this.hand = new Hand(this.player);
        this.player.hand = this.hand;
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
    public PersonalGoalCard viewGoal(){

        return player.getMyGoal();
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
//        System.out.println(player.getName() + " > Select: [" + r + "][" + c + "]");
        // verify if it is player turn or if it is the right phase
        if(!isMyTurn(player)||player.match.turnPhase != TurnPhases.Selection) {
            return false;
        }
        if(player.shelf.insertLimit == hand.getSlot().size()){
            // Limit reached
            //System.out.println("Shelf > Cannot pick more item");
            //System.out.println("Shelf > Hand["+hand.getSlot().size()+"]-Limit ["+player.shelf.insertLimit +"]");
            return false;
        }

        Board board = player.match.board;

        if (board.isPlayable(r,c) && board.isOccupied(r,c) && board.hasFreeSide(r, c)) {
//            System.out.println("Board > Cell selectable");
            /*If the cell is selectable then verify second condition*/

            if (hand.getSlot().size()>0)  {
                //quando ci sono altre carte in mano, controllo se è gia stata selezionata
                for (CardPointer tmp : hand.getSlot()) {
                    if ((r == tmp.x) && (c == tmp.y)) {
                        //Gia selezionato
//                        System.out.println("Board[!] > Already selected. Try again.");
                        return false;
                    }
                }

                //Iteriamo slot
                //Condizione 2 (Ortogonalità):
                //NewSelected Cell need to be adjacent to the other in slot.
                //NewSelected Cell need to be in a straight line.
                /*Coordinates have been filtered,
                   so they are valid for Orthogonality check*/
                if (!board.isOrthogonal(r, c, hand)) {
//                    System.out.println("Board > Not Orthogonal");
                    return false;
                }
            }
            //Tutti i controlli passati: posso inserirlo nella hand
            //salvo le coordinate e il riferimento dell'item nella hand*/
            hand.memCard(board.getCell(r, c), r, c);
//            System.out.println("Match > Item selected: [" + tmpBoard.getCellItem(r, c).getNameCard() + "]");
//
            return true;
        }
            //Questo messaggio sara tolto e messo in ClientInputHandler o nelle funzioni dei test
//            System.out.println("Match > Selection Failed");
        return false;
    }

    /**
     * Triggered maybe by right-click
     * During Selection Phase:
     * Clear Hand. The Player need to reselect all the cells
     */
    public boolean unselectCards(){
        if(!isMyTurn(player)) {
            return false;
        }
        if(player.match.turnPhase == TurnPhases.Selection) {
            hand.clearHand();
            return true;
        }else{
            //System.out.println("Match[!] > Not selection phase");
            return false;
        }

    }

    /**
     * Triggered maybe by a Confirm Selection Button
     * Or by the timer
     * It will ask the match to change TurnPhase in Insertion
     */
    public boolean callEndSelection(){
        if(!isMyTurn(player)) {
            return false;
        }
        player.match.changeTurnPhase(TurnPhases.Insertion);
        return true;
    }

    /**
     * During Insertion Phase.
     * Item in hand will be removed from Board through slot iteration.
     * @return true if the items has been removed all from the board
     */
    public boolean moveAllToHand(){
        if(!isMyTurn(player)) {
            return false;
        }
        if(player.match.turnPhase == TurnPhases.Insertion){
            for(CardPointer card: hand.getSlot()){
                if(player.match.board.isOccupied(card.x,card.y)) {
                    player.match.board.setCell(card.x, card.y, null);
                }
            }
            return true;
        }
        return false;
    }


    /**
     * Request for the Shelf to insert all the selected cards in a column(col)
     * @param col
     * @return true if the insertion is successful
     */
    public boolean tryToInsert(int col){
        if(!isMyTurn(player)) {
            return false;
        }
        // Non c'è piu spazio nella shelf quindi in teoria deve passare il turno
        // TODO:TEMP da rimuovere quando viene gestito last round
        if(player.shelf.getTotSlotAvail()==0){
            player.match.nextTurn();
            return false;
        }

        if(player.match.turnPhase == TurnPhases.Insertion){
//            System.out.println(player.getName()+" > Column: ["+col+"]");
            if(player.shelf.slotCol.get(col) < hand.getSlot().size()){
                //System.out.println("Shelf[!] > Not enough space in this column");
                /*
                for(int x: player.shelf.slotCol){
                    System.out.print("["+x+"]");
                }
                System.out.println("");*/
                //System.out.println("Shelf > Limit: ["+player.shelf.insertLimit +"]");
                return false;
            }else{
                for(int i=hand.getSlot().size(),s=0;i>0;i--,s++){
                    //Inserting one card at the time
                    if(player.shelf.insertInColumn(hand.getSlot().get(s).item,col)){
                        //System.out.println("Shelf > Insert...");

                    }else{
                        //If one of the card cannot be put indide the column
                        return false;
                    }

                }
                //Inserimento avvenuto, devo pulire hand
                //e calcolare new InsertLimit
                hand.clearHand();
                player.shelf.checkLimit();
                //TGear.printThisShelf(player.shelf);
                if(!callEndInsertion()){
                    return false;
                }
                return true;
            }
        }
        return false;
    }


    /**
     * This method will call another one in Hand to swap the position of two cards
     *
     * @param i is position 1
     * @param j is position 2
     * @return
     */
    public boolean changeHandOrder(int i,int j){
        if(!isMyTurn(player)) {
            return false;
        }
        int limit =hand.getSlot().size();
        if(i>=0 && i<limit && j>=0 && j<limit && limit>1){
            hand.changeOrder(i, j);
            //System.out.println("Hand > Order Changed");
            return true;
        }
        return false;

    }

    /**
     * Verify if is the current Player correspond with the one calling this method
     * @param player
     * @return
     */
    public boolean isMyTurn(Player player){
        if(player.match.currentPlayer != player) {
            //System.out.println("Match > Not your turn, "+ player.getName());
            return false;
        }
        return true;
    }

    /**
     *
     * @return
     */
    public boolean callEndInsertion(){
        if(!isMyTurn(player)) {
            return false;
        }
        player.match.changeTurnPhase(TurnPhases.GoalChecking);
        player.match.checkingGoals(player);
        return true;
    }


}
