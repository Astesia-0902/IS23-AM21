package org.am21.controller;

import org.am21.model.Cards.PersonalGoalCard;
import org.am21.model.GameManager;
import org.am21.model.Player;
import org.am21.model.enumer.GamePhase;
import org.am21.model.enumer.SC;
import org.am21.model.enumer.ServerMessage;
import org.am21.model.items.Board;
import org.am21.model.items.Hand;
import org.am21.networkRMI.ClientInputHandler;
import org.am21.utilities.CardPointer;
import org.am21.utilities.VirtualViewHelper;


/**
 * @version 1.0
 */
public class PlayerController {
    private Player player;
    private Hand hand;
    public ClientInputHandler clientInput;

    /**
     * PlayerController constructor is initialized by ClientGameController, when ClientInputHandler login.
     * It will create the player and add his reference
     *
     */
    public PlayerController(String nickname, ClientInputHandler clientInput){
        this.player = new Player(nickname,this);
        this.hand = new Hand(this.player);
        this.player.setHand(this.hand);
        this.clientInput = clientInput;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
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
    public PersonalGoalCard viewPersonalGoal(){

        return player.getMyPersonalGoal();
    }



    /**
     * During SelectionCards of GamePhase, when the player click on an item,
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
        if(!isMyTurn(player)||player.getMatch().gamePhase != GamePhase.Selection) {
            GameManager.sendCommunication(this,ServerMessage.NotYourTurn);
            return false;
        }
        if(player.getShelf().insertLimit == hand.getSelectedItems().size()){
            // Limit reached
            GameManager.sendCommunication(this,ServerMessage.Hand_Full);
            //System.out.println("Shelf > Cannot pick more item");
            //System.out.println("Shelf > Hand["+hand.getSelectedItems().size()+"]-Limit ["+player.shelves.insertLimit +"]");
            return false;
        }

        Board board = player.getMatch().board;

        if (board.isPlayable(r,c) && board.isOccupied(r,c) && board.hasFreeSide(r, c)) {
            /*If the cell is selectable then verify second condition*/


            if (hand.getSelectedItems().size()>0)  {
                //quando ci sono altre carte in mano, controllo se è gia stata selezionata
                for (CardPointer tmp : hand.getSelectedItems()) {
                    if ((r == tmp.x) && (c == tmp.y)) {
                        //Gia selezionato
                        //System.out.println("Board[!] > Already selected. Try again.");
                        GameManager.sendCommunication(this,ServerMessage.ReSelected);
                        return false;
                    }
                }

                if (!board.isOrthogonal(r, c, hand)) {
                    GameManager.sendCommunication(this,ServerMessage.No_Orthogonal);
                    return false;
                }
            }
            //Tutti i controlli passati: posso inserirlo nella hand
            //salvo le coordinate e il riferimento dell'item nella hand*/
            hand.memCard(board.getCell(r, c), r, c);
            //Virtualize HAND after each Selection
            VirtualViewHelper.virtualizeCurrentPlayerHand(player.getMatch());
            VirtualViewHelper.virtualizeBoard(player.getMatch());
            player.getMatch().updatePlayersVirtualView();
            GameManager.sendCommunication(this,ServerMessage.Selection_Ok);
            //System.out.println("Match > Item selected: [" + tmpBoard.getCellItem(r, c).getNameCard() + "]");

            player.getMatch().sendTextToAll("\n"+
                    SC.YELLOW+player.getNickname()+" selected the cell ["+r+","+c+"]. Press 'Enter'."+SC.RST,false );
            return true;
        }

        GameManager.sendCommunication(this,ServerMessage.Selection_No);
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
        if(player.getMatch().gamePhase == GamePhase.Selection && hand.getSelectedItems().size()>0) {
            hand.clearHand();
            GameManager.sendCommunication(this,ServerMessage.DeSel_Ok);
            //Update Virtual View(Hand and Board)
            VirtualViewHelper.virtualizeCurrentPlayerHand(player.getMatch());
            VirtualViewHelper.virtualizeBoard(player.getMatch());
            player.getMatch().updatePlayersVirtualView();
            return true;
        }

        GameManager.sendCommunication(this,ServerMessage.DeSel_Null);
        return false;
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
        player.getMatch().setGamePhase(GamePhase.Insertion);
        moveAllToHand();
        //TODO: add VV update GamePhase and Board
        VirtualViewHelper.virtualizeBoard(player.getMatch());

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
        if(player.getMatch().gamePhase == GamePhase.Insertion){
            for(CardPointer card: hand.getSelectedItems()){
                if(player.getMatch().board.isOccupied(card.x,card.y)) {
                    player.getMatch().board.setCell(card.x, card.y, null);
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
        if(player.getMatch().gamePhase == GamePhase.Insertion){
            if(player.getShelf().slotCol.get(col) < hand.getSelectedItems().size()){
                //The column has not enough space for insertion
                GameManager.sendCommunication(this,ServerMessage.Hand_Full);
                return false;
            }else{
                for(int i = hand.getSelectedItems().size(), s = 0; i>0; i--,s++){
                    //Inserting one item at the time
                    if(!player.getShelf().insertInColumn(hand.getSelectedItems().get(s).item,col)){
                        return false;
                    }
                }
                //The insertion is complete : Reset Hand and Check Shelf limit
                hand.clearHand();
                player.getShelf().checkLimit();
                //Update Virtual View --> Board, Hand, Shelf
                VirtualViewHelper.virtualizeBoard(player.getMatch());
                VirtualViewHelper.virtualizeCurrentPlayerHand(player.getMatch());
                VirtualViewHelper.updateVirtualShelves(player.getMatch());
                player.getMatch().updatePlayersVirtualView();
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
        if(isMyTurn(player) && player.getMatch().gamePhase==GamePhase.Insertion && hand.changeOrder(i,j)){
            // Virtual View Update --> Hand
            VirtualViewHelper.virtualizeCurrentPlayerHand(player.getMatch());
            player.getMatch().updateVirtualHand();
            GameManager.sendCommunication(this,ServerMessage.Sort_Ok);
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
        if(player.getMatch().currentPlayer != player) {
            // Not player turn
            return false;
        }
        return true;
    }

    /**
     *
     * @return
     */
    public void callEndInsertion(){
        if(player.getMatch().gamePhase==GamePhase.Insertion) {
            player.getMatch().setGamePhase(GamePhase.Default);
            //At the end of each turn, Player's hiddenPoints get updated (0-12)
            player.setHiddenPoints(player.getMyPersonalGoal().calculatePoints());
            player.getMatch().callEndTurnRoutine();
        }
    }

    /**
     * Method to add points to current player's Score
     * @param points
     */
    public void addScore(int points){
        player.setPlayerScore(player.getPlayerScore()+points);
    }


}
