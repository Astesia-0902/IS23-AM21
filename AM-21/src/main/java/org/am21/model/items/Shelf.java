package org.am21.model.items;

import org.am21.model.Player;
import org.am21.model.items.Card.ItemTileCard;

import java.util.ArrayList;

public class Shelf {
    private int rowNum = 6;
    private int colNum = 5;
    private Cell[][] cells;
    private int maxCardAvailable;

    //state of col available (default true)
    public boolean col1;
    public boolean col2;
    public boolean col3;
    public boolean col4;
    public boolean col5;
    public Player player;

    public ArrayList<ArrayList<ItemTileCard>> bookshelfGrid;

    public Shelf(){
        this.maxCardAvailable=rowNum*colNum;
        this.col1 = true;
        this.col2 = true;
        this.col3 = true;
        this.col4 = true;
        this.col5 = true;
        this.cells= new Cell[rowNum][colNum];
        this.bookshelfGrid=new ArrayList<ArrayList<ItemTileCard>>();
    }



    public int getColNum(){
        return colNum;
    }


    private static Cell[][] getCells() {

        return null;
    }

    public void pushCard(ItemTileCard n, int colNum){
        //selected the n° column to fill card
        //put card into shelf from playerhand
        this.bookshelfGrid.get(colNum).add(n);
    }


    public int slotAvailable(int colNum) {

        this.colNum-=colNum;
        return this.colNum;
    }
}

