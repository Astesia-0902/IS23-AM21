package org.am21.model.items;

import org.am21.model.Player;
import org.am21.model.items.Card.ItemTileCard;

import java.util.ArrayList;

public class Shelf extends Grid {
    /**state of col available (default true)*/
    public boolean col1;
    public boolean col2;
    public boolean col3;
    public boolean col4;
    public boolean col5;
    public Player player;
    public Grid playerShelf;
    //public ArrayList<ArrayList<ItemTileCard>> bookshelfGrid;

    public Shelf(){
        super(6, 5);
        this.col1 = true;
        this.col2 = true;
        this.col3 = true;
        this.col4 = true;
        this.col5 = true;
        //this.bookshelfGrid = new ArrayList<ArrayList<ItemTileCard>>();
        this.playerShelf = new Grid(6,5) {};
    }

    /**selected the number of column to fill card*/
    public void pushCard(Cell item, int colNum){

        //this.bookshelfGrid.get(colNum).add(n);
        /**rolling the first row available*/
        for(int i=0;i<6;i++){
            /**if cell available fill in*/
            if (playerShelf.getItemName(i,colNum)==null) {
                playerShelf.setCells(i,colNum,item);
            }
        }

    }

    /**number of slot available*/
    public int slotAvailable() {
        int colNum=0;
        if(col1)
            colNum++;
        if(col2)
            colNum++;
        if(col3)
            colNum++;
        if(col4)
            colNum++;
        if(col5)
            colNum++;
        return colNum;
    }

    //adjacent same item check ? common goal?
}

