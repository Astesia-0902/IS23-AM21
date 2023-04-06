package org.am21.controller;

import org.am21.model.items.Board;
import org.am21.model.items.Hand;
import org.am21.model.Player;
import org.am21.model.enumer.TurnPhases;
import org.am21.model.Cards.PersonalGoalCard;
import org.am21.utilities.CardPointer;
import org.am21.utilities.Mx;




public class PlayerController {
    public Player player;
    public Hand hand;
    public static Mx fb= Mx.Neutral;

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
    public PersonalGoalCard viewGoal(){

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
//        System.out.println(player.getName() + " > Select: [" + r + "][" + c + "]");
        // verify if it is player turn
        if(!isMyTurn(player)) {
            return false;
        }
        if (player.getMatch().turnPhase != TurnPhases.Selection) {
            //System.out.println("Match > Not Selection Phase");
//            fb = Mx.WrongPhase;

            return false;
        }

        if(player.shelf.insertLimit == hand.getSlot().size()){
//            fb= Mx.HandLimit;
            //System.out.println("Shelf > Cannot pick more item");
//            System.out.println("Shelf > Hand["+hand.getSlot().size()+"]-Limit ["+player.shelf.insertLimit +"]");
            return false;
        }
        //IL filtro precedente dovrebbe essere abbastanza
        /*if(hand.getSlot().size()==3){
            System.out.println("Board[!] > Hand full. If you want right-click and unselect");
            return false;
        }*/

        Board tmpBoard = player.getMatch().board;

        //TODO: elimina getItemTileCard dall'if, quindi è necessario modificare la struttura della board,
        //      dove le celle dark non contengono nessun oggetto Cell. La cella non deve esistere
        if (tmpBoard.getMatrix()[r][c] == null) {
//            fb= Mx.NoCell;
//            System.out.println("Board[!] > Empty cell. Try again");
            return false;
        }

        if (tmpBoard.hasFreeSide(r, c) == true) {
//            System.out.println("Board > Cell selectable");
            /*If the cell is selectable then verify second condition*/

            if (hand.getSlot().size() == 0) {

//                System.out.println("Board > Empty hand - No Orthogonality check");

            } else {
                //Controllo se è già stato selezionato
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
                if (tmpBoard.isOrthogonal(r, c, hand) == false) {
//                    System.out.println("Board > Not Orthogonal");
//                        fb= Mx.NoOrtho;
                        return false;
                } else {
//                    System.out.println("Board > Orthogonal");
                }
            }
            //salvo le coordinate e il riferimento dell'item nella hand*/
            hand.memCard(tmpBoard.getCellItem(r, c), r, c);
//            System.out.println("Match > Item selected: [" + tmpBoard.getCellItem(r, c).getNameCard() + "]");
//            fb = Mx.SelectWin;
            return true;
        } else {
            //Questo messaggio sara tolto e messo in ClientInputHandler o nelle funzioni dei test
//            System.out.println("Match > Selection Failed");
//            fb = Mx.SelFail;
            return false;
        }
    }

    /**
     * Triggered maybe by right-click
     * During Selection Phase:
     * Clear Hand. The Player need to reselect all the cells
     */
    public boolean unselectCard(){
        if(!isMyTurn(player)) {
            return false;
        }
        if(player.match.turnPhase == TurnPhases.Selection) {
            hand.clearHand();
            return true;
        }else{
            //System.out.println("Match[!] > Not selection phase");
//            fb = Mx.WrongPhase;
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
        if(player.getMatch().turnPhase == TurnPhases.Insertion){
            for(CardPointer card: hand.getSlot()){
                if(player.match.board.isOccupied(card.x,card.y)) {
                    player.match.board.setCell(card.x, card.y, null);
                    return true;
                }
            }
        }
        fb = Mx.WrongPhase;
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
        // temp da rimuovere quando viene gestito last round
        if(player.shelf.getTotSlotAvail()==0){
            player.match.nextTurn();
            return false;
        }
        if(player.match.turnPhase == TurnPhases.Insertion){

//            System.out.println(player.getName()+" > Column: ["+col+"]");

            if(player.shelf.slotCol.get(col)<hand.getSlot().size()){
                fb=Mx.ColSlotF;
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
                player.shelf.elaborateLimit();
                //TGear.printThisShelf(player.shelf);
                if(!callEndInsertion()){
                    return false;
                }
                return true;
            }
        }
        fb=Mx.WrongPhase;
        return false;
    }


    /**
     * This method will call another one in Hand to swap the position of two cards
     * @param pos1
     * @param pos2
     * @return
     */
    public boolean changeHandOrder(int pos1,int pos2){
        if(!isMyTurn(player)) {
            return false;
        }
        if(hand.getSlot().size()>=2){
            hand.changeOrder(pos1,pos2);
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
            fb = Mx.WrongPlayer;
            //System.out.println("Match > Not your turn, "+ player.getName());
            return false;
        }
        return true;
    }

    public boolean callEndInsertion(){
        if(!isMyTurn(player)) {
            return false;
        }
        player.match.changeTurnPhase(TurnPhases.GoalChecking);
        player.match.checkingGoals(player);
        return true;
    }


}
