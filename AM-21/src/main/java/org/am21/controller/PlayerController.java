package org.am21.controller;

import org.am21.model.Cards.PersonalGoalCard;
import org.am21.model.GameManager;
import org.am21.model.Player;
import org.am21.model.enumer.GamePhase;
import org.am21.model.enumer.SC;
import org.am21.model.enumer.ServerMessage;
import org.am21.model.items.Board;
import org.am21.model.items.Hand;
import org.am21.model.items.Shelf;
import org.am21.networkRMI.ClientInputHandler;
import org.am21.utilities.CardPointer;
import org.am21.utilities.VirtualViewHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * @version 1.0
 */
public class PlayerController {
    private Player player;
    private Hand hand;
    public ClientInputHandler clientInput;
    /**
     * True: RMI
     * False: Socket
     */
    public boolean connectionType;

    /**
     * PlayerController constructor is initialized by ClientGameController, when ClientInputHandler login.
     * It will create the player and add his reference
     */
    public PlayerController(String nickname, ClientInputHandler clientInput) {
        this.player = new Player(nickname, this);
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
     *
     * @param newName
     */
    public void changeName(String newName) {

        player.setNickname(newName);
    }

    /**
     * Command to see my own goal
     * !Implementation incomplete due to Lack of View Component!
     * @return
     */
    public PersonalGoalCard viewPersonalGoal() {

        return player.getMyPersonalGoal();
    }


    /**
     * During SelectionCards of GamePhase, when the player click on an item,
     * the command will memorize the item position and reference in the PlayerHand
     * if is Selectable(at least one item adjacent)
     * and if is Orthogonal to the other selected cards
     * <p>
     * To revisit
     *
     * @param r
     * @param c
     * @return false : Selection failed
     */
    public boolean selectCell(int r, int c) {
        if (!isMyTurn(player) || !isGamePhase(GamePhase.Selection)) {
            return false;
        }

        Board board = player.getMatch().board;

        if (board.isPlayable(r, c) && board.isOccupied(r, c) && board.hasFreeSide(r, c)) {
            /*If the cell is selectable then verify second condition*/
            if (hand.getSelectedItems().size() > 0) {
                //Check if the item is already selected
                CardPointer item = isAlreadySelected(r,c);
                if(item!=null){
                    //Item already selected
                    //TODO: NEW: if a player select an already selected cell, it will deselect it
                    if(!deselectCell(item)){
                        //The other cards do not respect the conditions, they will be cleared
                        clearSelectedCards();
                    }else {
                        player.getMatch().selectionUpdate();
                        GameManager.sendReply(this,ServerMessage.DeSel_Ok);
                    }
                    return false;
                    //Need VV update
                }else {
                    //Check Orthogonality
                    if (!board.isOrthogonal(r, c, hand.getSelectedItems())) {
                        GameManager.sendReply(this, ServerMessage.No_Orthogonal);
                        return false;
                    }
                }
            }
            if (player.getShelf().insertLimit == hand.getSelectedItems().size()) {
                // Limit reached
                GameManager.sendReply(this, ServerMessage.Hand_Full);
                return false;
            }
            //Tutti i controlli passati: posso inserirlo nella hand
            //salvo le coordinate e il riferimento dell'item nella hand*/
            hand.memCard(board.getCell(r, c), r, c);
            //Virtualize HAND and board after each Selection and Sent to the players
            player.getMatch().selectionUpdate();
            GameManager.sendReply(this, ServerMessage.Selection_Ok);
            player.getMatch().sendTextToAll("\n" +
                    SC.YELLOW + player.getNickname() + " selected the cell [" + r + "," + c + "]." + SC.RST, false);
            return true;
        }

        GameManager.sendReply(this, ServerMessage.Selection_No);
        //Questo messaggio sara tolto e messo in ClientInputHandler o nelle funzioni dei test
//            System.out.println("Match > Selection Failed");

        return false;
    }


    /**
     * This method is called exclusively by {@link #selectCell(int, int)}
     * After the deselection, it needs to check the other selected items.
     * There is no need to check orthogonality again, because the items in the Hand.selectedItems are already
     * all confirmed to be in line. The characteristic that need to be controlled is if they are still adjacent
     *
     * @return true if just one card is deselected, false if other cards needs to be deselected
     */
    private boolean deselectCell(CardPointer item) {
        //TODO: to test
        if (hand.getSelectedItems().size() > 2) {
            //Re-selected item removed
            hand.getSelectedItems().remove(item);
            //Check the other, Copy the hand elements
            Board board_ref = player.getMatch().board;
            List<CardPointer> copy = new ArrayList<>(hand.getSelectedItems());
            for (CardPointer x : hand.getSelectedItems()) {
                copy.remove(x);
                if (board_ref.isOrthogonal(x.x, x.y, copy)) {
                    //La carta va bene
                    copy.add(x);
                } else {
                    //If I find an item which is not orthogonal --> return false
                    return false;
                }
            }
            //The selected cell is removed and the other are okay
        }else{
            hand.getSelectedItems().remove(item);
        }
        return true;
    }

    /**
     * Triggered maybe by right-click
     * During Selection Phase:
     * Clear Hand. The Player need to reselect all the cells
     */
    public boolean clearSelectedCards() {
        if (!isMyTurn(player)) {
            return false;
        }
        if (player.getMatch().gamePhase == GamePhase.Selection && hand.getSelectedItems().size() > 0) {
            hand.clearHand();
            GameManager.sendReply(this, ServerMessage.DeSel_Ok);
            //Update Virtual View(Hand and Board)
            player.getMatch().selectionUpdate();
            return true;
        }

        GameManager.sendReply(this, ServerMessage.DeSel_Null);
        return false;
    }

    /**
     * Triggered maybe by a Confirm Selection Button
     * Or by the timer
     * It will ask the match to change TurnPhase in Insertion
     */
    public boolean callEndSelection() {
        if (!isMyTurn(player)) {
            return false;
        }
        if (hand.getSelectedItems().size() == 0) {
            //TODO: test
            //Cannot confirm selection there are no selected items
            return false;
        }
        player.getMatch().setGamePhase(GamePhase.Insertion);
        moveAllToHand();
        //Update Virtual View --> Board
        VirtualViewHelper.virtualizeBoard(player.getMatch());
        player.getMatch().updatePlayersView();
        return true;
    }

    /**
     * During Insertion Phase.
     * Item in hand will be removed from Board through slot iteration.
     * (Emulate the human action of PICKING the items from the board)
     *
     * @return true if the items has been removed all from the board
     */
    public boolean moveAllToHand() {
        if (!isMyTurn(player)) {
            return false;
        }
        if (player.getMatch().gamePhase == GamePhase.Insertion) {
            for (CardPointer card : hand.getSelectedItems()) {
                if (player.getMatch().board.isOccupied(card.x, card.y)) {
                    player.getMatch().board.setCell(card.x, card.y, null);
                }
            }
            return true;
        }
        return false;
    }


    /**
     * Request for the Shelf to insert all the selected cards in a column(col)
     *
     * @param col
     * @return true if the insertion is successful
     */
    public boolean tryToInsert(int col) {
        if (!isMyTurn(player) || !isGamePhase(GamePhase.Insertion) || isHandEmpty() && col>=0 && col<Shelf.SHELF_COLUMN) {
            return false;
        }
        if (player.getShelf().slotCol.get(col) < hand.getSelectedItems().size()) {
            //The column has not enough space for insertion
            GameManager.sendReply(this, ServerMessage.ColNo);
            return false;
        } else {
            for (int i = hand.getSelectedItems().size(), s = 0; i > 0; i--, s++) {
                //Inserting one item at the time
                if (!player.getShelf().insertInColumn(hand.getSelectedItems().get(s).item, col)) {
                    return false;
                }
            }
            //The insertion is complete : Reset Hand and Check Shelf limit
            hand.clearHand();
            player.getShelf().checkLimit();
            //Update Virtual View --> Board, Hand, Shelf
            player.getMatch().insertionUpdate();
            return true;
        }

    }


    /**
     * This method will call another one in Hand to swap the position of two cards
     *
     * @param i is position 1
     * @param j is position 2
     * @return
     */
    public boolean changeHandOrder(int i, int j) {
        if (isMyTurn(player) && player.getMatch().gamePhase == GamePhase.Insertion && hand.changeOrder(i, j)) {
            // Virtual View Update --> Hand
            player.getMatch().sortUpdate();
            GameManager.sendReply(this, ServerMessage.Sort_Ok);
            return true;
        }
        return false;
    }

    /**
     * Verify if is the current Player correspond with the one calling this method
     *
     * @param player
     * @return
     */
    public boolean isMyTurn(Player player) {
        if (player.getMatch().currentPlayer != player) {
            // Not player turn
            GameManager.sendReply(this, ServerMessage.NotYourTurn);
            return false;
        }
        return true;
    }

    /**
     * Control if the game phase is the same of the one you want
     *
     * @param gamePhase is the phase needed to be
     * @return true if the gamePhase is the same of the match game phase
     */
    public boolean isGamePhase(GamePhase gamePhase) {
        if (player.getMatch().gamePhase == gamePhase) {
            return true;
        }
        //TODO: message of wrong phase
        return false;
    }

    /**
     * @return
     */
    public void callEndInsertion() {
        if (player.getMatch().gamePhase == GamePhase.Insertion) {
            player.getMatch().setGamePhase(GamePhase.Default);
            //At the end of each turn, Player's hiddenPoints get updated (0-12)
            player.setHiddenPoints(player.getMyPersonalGoal().calculatePoints());
            player.getMatch().callEndTurnRoutine();
        }
    }

    /**
     * Method to add points to current player's Score
     *
     * @param points
     */
    public void addScore(int points) {
        player.setPlayerScore(player.getPlayerScore() + points);
    }

    public boolean isHandEmpty(){
        if(hand.getSelectedItems().size()==0){
            return true;
        }
        //TODO: server message: No card selected
        return false;
    }

    /**
     * Check if the cell is already selected by iterating the hand
     * @param r coordinate x of the board
     * @param c coordinate y of the board
     * @return the CardPointer of the item already selected, otherwise null
     */
    public CardPointer isAlreadySelected(int r, int c){
        for (CardPointer item : hand.getSelectedItems()) {
            if ((r == item.x) && (c == item.y)) {
                GameManager.sendReply(this, ServerMessage.ReSelected);
                return item;
            }
        }//TODO: test
        return null;
    }

}
