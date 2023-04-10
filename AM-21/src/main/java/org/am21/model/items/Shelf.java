package org.am21.model.items;

import org.am21.model.Cards.ItemCard;
import org.am21.model.Player;
import org.am21.utilities.CardPointer;

import java.util.ArrayList;
import java.util.List;


public class Shelf extends Grid {

    public Player player;
    /**
     * Each element show how many slots are still available in correspondent column
     */
    public List<Integer> slotCol;
    /**
     * Starting Limit for numbers of card insertable in the shelf
     */
    public int insertLimit=3;
    public final int stdLim =3;
    public final static int sRow = 6 ;
    public final static int sColumn = 5;


    /**
     * Construction of an empty shelf:
     * Initialize the grid with superclass
     * Create array, each elem count slot available for each column
     * Row-Index-0 is bottom of the shelf
     * @param player
     */
    public Shelf(Player player){
        super(sRow, sColumn);
        this.player = player;
        this.slotCol = new ArrayList<>();
        for(int i = 0; i< sColumn; i++){
            slotCol.add(sRow);
        }

    }

    /**
     * Calculate the min Limit for hand Capacity
     * Example: If there are only column with 2 slots available,
     * then 'handLimit' = 2
     * @return
     */
    public void checkLimit(){
/*        System.out.println("Shelf > Slot available each column");
        for(int x: player.shelf.slotCol){
            System.out.print("["+x+"]");
        }
        System.out.println("");*/
        int max=0;
//        System.out.println("Shelf > Elaboration Limit... ");
//        System.out.println("OldLimit:"+this.insertLimit);
        for(int j = 0; j< sColumn; j++){
            if(this.slotCol.get(j)>max){
                max = this.slotCol.get(j);
            }
        }
        if(max>=stdLim){
            this.insertLimit=stdLim;
        }else{
            this.insertLimit=max;
        }

    }

    /**
     * number of slot available in total
     * @return Number of Total space available in this shelf
     * */
    public int getTotSlotAvail() {
        int sum=0;
        for(int x: this.slotCol)
            sum = sum+x;
        return sum;
    }




    /**
     * Insert an itemCard in the column, then decrease the count
     * in column (col)
     * @param item
     * @param col
     * @return true if insertion has been successful
     */
    public boolean insertInColumn(ItemCard item, int col){

        if(this.getMatrix()[Shelf.sRow-slotCol.get(col)][col]==null && slotCol.get(col)>0){
            this.getMatrix()[Shelf.sRow-slotCol.get(col)][col]= item;
            this.slotCol.set(col,slotCol.get(col)-1);
            return true;
        }

        return false;
    }


    /**
     * This method is called at the end of the Game
     * It will calculate the points according to a table.
     * Having multiple item of the same type adjacent
     * give different amount of points.
     *
     *  */
    public int colorPoints(){

        List<CardPointer> common = new ArrayList<CardPointer>();

        int points=0;
        int reg_index = -1;

        for(int r=0;r<6;r++) {
            for(int c=0;c<5;c++) {
                if(this.getItemName(r,c)!=null)
                {
                    do {
                        reg_index++;
                        common.add(new CardPointer(r, c));
                        /* save the first not null item in String type*/
                        String recentCard = this.getItemName(common.get(reg_index).x,common.get(reg_index).y);

                        if (this.getItemName(r, c + 1) != null && (recentCard.equals(this.getItemName(r, c + 1)) )) {
                            common.add(new CardPointer(r, c + 1));
                        }
                        if (this.getItemName(r, c - 1) != null && (recentCard.equals(this.getItemName(r, c - 1)) )) {
                            common.add(new CardPointer(r, c - 1));
                        }
                        if (this.getItemName(r + 1, c) != null && (recentCard.equals(this.getItemName(r + 1, c)) )) {
                            common.add(new CardPointer(r + 1, c));
                        }
                        if (this.getItemName(r - 1, c) != null && (recentCard.equals(this.getItemName(r - 1, c)) )) {
                            common.add(new CardPointer(r - 1, c));
                        }
                        /** set the cell to null*/
                        this.setCell(r, c, null);
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


    public void getGroupPoints(){


    }



}

