package org.am21.model.items;

import org.am21.model.Cards.ItemCard;
import org.am21.model.Player;

import java.util.ArrayList;
import java.util.HashMap;
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

    private final static HashMap<Integer,Integer> pointsMap=new HashMap<>();
    static {
        pointsMap.put(0,0);
        pointsMap.put(3,2);
        pointsMap.put(4,3);
        pointsMap.put(5,5);
        pointsMap.put(6,8);
    }


    /**
     * Construction of an empty shelf:
     * Initialize the grid with superclass
     * Create array, each elem count slot available for each column
     * Row-Index-0 is on the top of the shelf
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

        if(this.getMatrix()[slotCol.get(col)-1][col]==null && slotCol.get(col)>0){
            this.getMatrix()[slotCol.get(col)-1][col]= item;
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
    public int getGroupPoints(){
        boolean[][] visited = new boolean[sRow][sColumn];
        int points=0;

        for(int r=0;r<sRow;r++){
            for(int c=0;c<sColumn;c++){
                if(!isOccupied(r,c) || visited[r][c]){
                    continue;
                }
                points += pointsTable(colorCounter(r,c,visited,1,getItemType(r,c)));
            }
        }
        return points;
    }

    public int colorCounter(int r,int c, boolean[][] visited,int depth,String type){
        int newDepth=depth;
        visited[r][c]=true;

        if(r>0&&!visited[r-1][c] && isOccupied(r-1,c)&&getItemType(r-1,c).equals(type)){
            newDepth=colorCounter(r-1,c,visited,newDepth+1,type);
        }
        if(r+1<sRow&&!visited[r+1][c]&&isOccupied(r+1,c)&&getItemType(r+1,c).equals(type)){
            newDepth=colorCounter(r+1,c,visited,newDepth+1,type);
        }
        if(c>0&&!visited[r][c-1]&&isOccupied(r,c-1)&&getItemType(r,c-1).equals(type)){
            newDepth=colorCounter(r,c-1,visited,newDepth+1,type);
        }
        if(c+1<sColumn&&!visited[r][c+1]&&isOccupied(r,c+1)&&getItemType(r,c+1).equals(type)){
            newDepth=colorCounter(r,c+1,visited,newDepth+1,type);
        }
        return newDepth;
    }

    public int pointsTable(int nItem){
        if(nItem<3){nItem=0;}
        else if(nItem>6){nItem=6;}

        return pointsMap.get(nItem);
    }
}


    /*public int colorPoints(){

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
                        *//* save the first not null item in String type*//*
                        String recentCard = this.getItemName(common.get(reg_index-1).x,common.get(reg_index-1).y);

                        if (c+1<Shelf.sColumn&&this.getItemName(r, c + 1) != null && (recentCard.equals(this.getItemName(r, c + 1)) )) {
                            common.add(new CardPointer(r, c + 1));
                        }
                        if (c-1>=0&&this.getItemName(r, c - 1) != null && (recentCard.equals(this.getItemName(r, c - 1)) )) {
                            common.add(new CardPointer(r, c - 1));
                        }
                        if (r+1<Shelf.sRow&&this.getItemName(r + 1, c) != null && (recentCard.equals(this.getItemName(r + 1, c)) )) {
                            common.add(new CardPointer(r + 1, c));
                        }
                        if (r-1>=0&&this.getItemName(r - 1, c) != null && (recentCard.equals(this.getItemName(r - 1, c)) )) {
                            common.add(new CardPointer(r - 1, c));
                        }
                        *//* set the cell to null*//*
                        this.setCell(r, c, null);
                        *//*now begin check the next element of list to recheck this procedure*//*


                    }while(common.size()>reg_index+1);

                    *//* Iterator <Coordinates> li = null;
                     li = common.listIterator();
                     if(li.hasNext()!=true){
                     li.next();
                     }*//*

                    *//* count  all element of the list (check the duplicate)*//*

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

                *//* clean the list*//*
                common.clear();
            }
        }
        return points;
    }*/

