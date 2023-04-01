package org.am21.model.items;

import org.am21.model.Player;
import org.am21.model.items.Card.ItemTileCard;
import org.am21.util.Coordinates;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    /**adjacent same item check */
    public int colorsPoints(){

        List<Coordinates> common = new ArrayList<Coordinates>();

        int points=0;
        int reg_index = -1;

        for(int r=0;r<6;r++) {
            for(int c=0;c<5;c++) {
                if(playerShelf.getItemName(r,c)!=null)
                {
                    do {
                        reg_index++;
                        common.add(new Coordinates(r, c));
                        /** save the first not null item in String type*/
                        String recentCard = playerShelf.getItemName(common.get(reg_index).x,common.get(reg_index).y);

                        if (playerShelf.getItemName(r, c + 1) != null && (recentCard == playerShelf.getItemName(r, c + 1))) {
                            common.add(new Coordinates(r, c + 1));
                        }
                        if (playerShelf.getItemName(r, c - 1) != null && (recentCard == playerShelf.getItemName(r, c - 1))) {
                            common.add(new Coordinates(r, c - 1));
                        }
                        if (playerShelf.getItemName(r + 1, c) != null && (recentCard == playerShelf.getItemName(r + 1, c))) {
                            common.add(new Coordinates(r + 1, c));
                        }
                        if (playerShelf.getItemName(r - 1, c) != null && (recentCard == playerShelf.getItemName(r - 1, c))) {
                            common.add(new Coordinates(r - 1, c));
                        }
                        /** set the cell to null*/
                        playerShelf.setCells(r, c, null);
                        /**now begin check the next element of list to recheck this procedure*/


                    }while(common.size()>reg_index+1);

                   /** Iterator <Coordinates> li = null;
                    li = common.listIterator();
                    if(li.hasNext()!=true){
                        li.next();
                    }*/

                   /** count  all element of the list (check the duplicate)*/

                        for(int i=0; i<common.size();i++)
                        {
                            for(int j=1;j<common.size();j++) {
                                if (common.get(i) == common.get(j))
                                {
                                    common.remove(j);
                                    j--;
                                }
                            }
                        }

                        if(common.size()==3)
                            points+=2;
                        else if(common.size()==4)
                            points+=3;
                        else if(common.size()==5)
                            points+=5;
                        else if(common.size()>=6)
                            points+=8;

                }

                /** clean the list*/
                common.clear();
            }
        }
        return points;
    }


}

