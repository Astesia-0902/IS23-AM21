package org.am21.model.items;

import org.am21.model.Player;
import org.am21.model.items.Card.ItemTileCard;
import org.am21.utilities.Coordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * Attributes needed:
 * - Number of cells available for each column              <--check by method getSlotCol1,2,3,4,5
 * - Total number of cells available in the Shelf           <--check by method slotTotAvailable
 * - Limit for insertion overall (it could be 3,2 or 1)  <--check by method slotTotAvailable
 *
 * Method needed:
 * - Insertion of one item in a column(cells available in the column will decrease)  <--check by method pushCard
 * -
 */

public class Shelf extends Grid {
    /**state of col available (default true)*/

    public int slotCol1;
    public int slotCol2;
    public int slotCol3;
    public int slotCol4;
    public int slotCol5;

    public Player player;
    /**
     * Each element show how many slots are still available in correspondent column
     */
    public List<Integer> slotCol;
    public int insertLimit=3;
    private final static int row = 6 ;
    private final static int column = 5;



    public Shelf(Player player){
        super(row, column);
        this.player = player;
        this.slotCol1 = 6;
        this.slotCol2 = 6;
        this.slotCol3 = 6;
        this.slotCol4 = 6;
        this.slotCol5 = 6;
        this.slotCol = new ArrayList<>();
        for(int i=0;i<colNum;i++){
            slotCol.add(row);
        }
        this.initiateShelfGrid(row,column);
        System.out.println("Match > "+player.getName()+"'s Shelf created.");

    }

    public boolean initiateShelfGrid(int r,int c){
        for(int i=0; i<r;i++){
            for(int j=0;j<c;j++){
                this.getCellGrid()[i][j] = new Cell();
            }
        }

        return false;
    }

    /**
     * Calculate the min Limit for hand Capacity
     * Example: If there are only column with 2 slots available,
     * then 'handLimit' = 2
     * @return
     */
    public void elaborateLimit(){
        System.out.println("Shelf > Slot available each column");
        for(int x: player.shelf.slotCol){
            System.out.print("["+x+"]");
        }
        System.out.println("");
        int max=0;
        System.out.println("Shelf > Elaboration Limit...\n Max :"+max);
        System.out.println("");
        for(int j=0;j<column;j++){
            if(this.slotCol.get(j)>max){
                max = this.slotCol.get(j);
            }
        }
        if(max>=3){
            this.insertLimit =3;
        }else{
            this.insertLimit =max;
        }
        System.out.println("Shelf > Limit:" + this.insertLimit);
    }

    @Override
    public Cell[][] getCellGrid() {
        return super.getCellGrid();
    }

    @Override
    public void setCell(int rowNum, int colNum, Cell value) {
        super.setCell(rowNum, colNum, value);
    }

    @Override
    public ItemTileCard getCellItem(int r, int c) {
        return super.getCellItem(r, c);
    }

    @Override
    public void insertInCell(int r, int c, ItemTileCard item) {
        super.insertInCell(r, c, item);
    }

    @Override
    public String getItemName(int rowNum, int colNum) {
        return super.getItemName(rowNum, colNum);
    }

    /**number of slot available in total*/
    public int getTotSlotAvail() {
        int sum=0;
        for(int x: this.slotCol)
            sum = sum+x;
        return sum;
    }
    public int getCol1(){return this.slotCol1;}
    public int getCol2(){return this.slotCol2;}
    public int getCol3(){return this.slotCol3;}
    public int getCol4(){return this.slotCol4;}
    public int getCol5(){return this.slotCol5;}
    private void setCol1(int numDec ){ this.slotCol1-=numDec;}
    private void setCol2(int numDec ){ this.slotCol2-=numDec;}
    private void setCol3(int numDec ){ this.slotCol3-=numDec;}
    private void setCol4(int numDec ){ this.slotCol4-=numDec;}
    private void setCol5(int numDec ){ this.slotCol5-=numDec;}

    public void insertCard(ItemTileCard item, int numCard,int numCol){
        /** fill the first cell available*/
        switch (numCol) {
            case 1: if(numCard<=getCol1())
                    {
                        for (int i = 6 - getCol1(); numCard != 0; i++)
                        {
                            this.insertInCell(i, 1, item);
                            numCard--;
                        }
                        setCol1(numCard);
                    }
                    /** else return false */
                    break;
            case 2: if(numCard<=getCol2())
                    {
                        for (int i = 6 - getCol2(); numCard != 0; i++)
                        {
                            this.insertInCell(i, 2, item);
                            numCard--;
                        }

                        setCol2(numCard);
                    }
                /** else return false */
                break;
            case 3: if(numCard<=getCol3())
                    {
                        for (int i = 6 - getCol3(); numCard != 0; i++)
                        {
                            this.insertInCell(i, 3, item);
                            numCard--;
                        }

                        setCol3(numCard);
                    }
                /** else return false */
                break;
            case 4: if(numCard<=getCol4())
                    {
                        for (int i = 6 - getCol4(); numCard != 0; i++)
                        {
                            this.insertInCell(i, 4, item);
                            numCard--;
                        }

                        setCol4(numCard);
                    }
                /** else return false */
                break;
            case 5: if(numCard<=getCol5())
                    {
                        for (int i = 6 - getCol5(); numCard != 0; i++)
                        {
                            this.insertInCell(i, 5, item);
                            numCard--;
                        }

                        setCol5(numCard);
                    }
                /** else return false */
                break;
        }
    }

    /**
     * Insert an itemCard in the column
     * @param item
     * @param col
     */
    public void insertCard2(ItemTileCard item,int col){
        this.insertInCell(slotCol.get(col)-1,col,item);
        this.slotCol.set(col,slotCol.get(col)-1);
    }


    /**adjacent same item check */
    public int colorsPoints(){

        List<Coordinates> common = new ArrayList<Coordinates>();

        int points=0;
        int reg_index = -1;

        for(int r=0;r<6;r++) {
            for(int c=0;c<5;c++) {
                if(this.getItemName(r,c)!=null)
                {
                    do {
                        reg_index++;
                        common.add(new Coordinates(r, c));
                        /** save the first not null item in String type*/
                        String recentCard = this.getItemName(common.get(reg_index).x,common.get(reg_index).y);

                        if (this.getItemName(r, c + 1) != null && (recentCard.equals(this.getItemName(r, c + 1)) )) {
                            common.add(new Coordinates(r, c + 1));
                        }
                        if (this.getItemName(r, c - 1) != null && (recentCard.equals(this.getItemName(r, c - 1)) )) {
                            common.add(new Coordinates(r, c - 1));
                        }
                        if (this.getItemName(r + 1, c) != null && (recentCard.equals(this.getItemName(r + 1, c)) )) {
                            common.add(new Coordinates(r + 1, c));
                        }
                        if (this.getItemName(r - 1, c) != null && (recentCard.equals(this.getItemName(r - 1, c)) )) {
                            common.add(new Coordinates(r - 1, c));
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




}

