package org.am21.controller;

import org.am21.model.*;
import org.am21.model.items.*;
import org.am21.model.items.Card.ItemTileCard;

public class PlayerController {
    public Player player;

    public PlayerController(Player player){
        this.player = player;
    }



    /**
     * restituisce false se l'inserimento in una colonna è fallito, causato da mancanza di slot
     */
    public boolean insertTiles(int colNum,int numTiles){

        if(numTiles>0 && numTiles<4 && player.myShelf.slotAvailable(colNum)>=numTiles){
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
