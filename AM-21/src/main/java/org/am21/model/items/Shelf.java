package org.am21.model.items;

import org.am21.model.Player;
import org.am21.model.items.Card.ItemTileCard;

import java.util.ArrayList;

public class Shelf {
    private int rowNum = 6;
    private int colNum = 5;
    private Cell[][] cells;
    private int maxCard;
    public Player player;

    public ArrayList<ArrayList<ItemTileCard>> bookshelfGrid;

    public Shelf(){
        this.maxCard=rowNum*colNum;
        this.cells= new Cell[rowNum][colNum];
        this.bookshelfGrid=new ArrayList<ArrayList<ItemTileCard>>();
    }

    public Shelf myShelf(){
        //my shelf item matrix
        Shelf myPersonalShelf= new Shelf();
        //Cell[][] cells = Shelf.Cells();
        return null;
    }

    private static Cell[][] getCells() {
        return null;
    }

    public void getCard(){
        //get card from player
    }


    public void setCard(){
        //put card into shelf
        //select the  colum from 1 to 3
    }


    public int slotAvailable(int colNum) {
        return 0;
    }
}
