package org.am21.controller;

import org.am21.model.*;
import org.am21.model.items.Card.ItemTileCard;
import org.am21.model.items.Card.PersonalGoalCard;

public class PlayerController {
    public Player player;

    /**
     * PlayerController constructor is initialized by GameController, when ClientInputHandler login.
     *
     */
    public PlayerController(String nickname){
        this.player = new Player(nickname);
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
     * Command to request GameManager to join a Game,
     * GameManger will add the player to an available Match or will create one if it doesn't exist.
     */
    public void requestJoinGame(){


    }

    /**
     * During SelectionCards of TurnPhases, when the player click on an item,
     * the command will put the item in the PlayerHand if is Selectable(at least one item adjacent)
     * and if is Orthogonal to the other selected cards
     *
     * To revisit: too many recall to references...lel
     * @param r
     * @param c
     * @return
     */
    public boolean selectCard(int r,int c){
        if(player.getMatch().turnPhase!=TurnPhases.Selection)
            return false;
        if(player.getMatch().livingRoomBoard.isSelectable(r,c)==true){
            for(int i=1;i<=player.hand.getNumCards();i++){
                if(player.getMatch().livingRoomBoard.isOrthogonal(r,c,player,i)==false){
                    return false;
                }

            }


            //inserisco item nel primo slot disponibile della mano
            player.hand.setSlot(player.myManager.getMatch().livingRoomBoard.getItem(r,c),
                    player.hand.getNumCards());
            player.hand.setNumCards(player.hand.getNumCards()+1);
            return true;
        }else{
            return false;
        }

    }

    public void putDownCard(){

    }


    /**
     * Command for insertion of Items in a specific column, selected by the player.
     * This command will activate when player has to choose in which column put the item.
     *
     * @param numTiles
     * @return
     */
    public boolean insertTiles(int numTiles){





        if(numTiles>0 && numTiles<4 && player.myShelf.slotAvailable()>=numTiles){
            /**
             * Se num di slot disponibili nella colonna colNum è >= dei Tiles da inserire allora ok
             *  for(int i=0;i<numTiles;i++){
             *      playerHand.get(i).addItemToShelf(colNum);
             *      playerHand.remove(i);   //???(Ken)
                }
             */
            return true;
        }else{
            return false;
        }

    }


    public void addItemToHand(ItemTileCard item){}

    public void removeItemFromHand(ItemTileCard item){}

    public void changeHandOrder(){}

}
