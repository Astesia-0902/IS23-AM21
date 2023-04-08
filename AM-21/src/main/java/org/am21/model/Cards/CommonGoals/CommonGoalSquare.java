package org.am21.model.Cards.CommonGoals;

import org.am21.model.Cards.CommonGoal;
import org.am21.model.items.Shelf;

public class CommonGoalSquare extends CommonGoal {

    private boolean[][] visited;

    /**
     *
     * @param numPlayer the number of players
     */
    public CommonGoalSquare(int numPlayer) {

        super("CommonGoalSquare",numPlayer);
    }


    /*public boolean checkGoal2(Shelf shelf) {
        boolean[][] visited = new boolean[Shelf.sRow][Shelf.sColumn];
        int count = 0;
        String root;
        for (int i = 0; i < Shelf.sRow-1; i++) {
            for (int j = 0; j < Shelf.sColumn; j++) {
                if (shelf.isOccupied(i, j)) {
                    //Item type of our analysis
                    root = shelf.getItemName(i,j).substring(0,shelf.getItemName(i,j).length()-3);
                    //Once we find a tile != 0 and not visited, we check if it's the upper left tile of a 2x2 group
                    if (!visited[i][j]) {
                        System.out.println("A");
                        boolean result = process(shelf, i, j, visited,root)
                                && process(shelf, i + 1, j, visited,root)
                                && process(shelf, i, j + 1, visited,root)
                                && process(shelf, i + 1, j + 1, visited,root);
                        if (result) {
                            System.out.println("b");
                            count++;
                            //If we find 2 groups of 4 tiles, we return true
                            if (count >= 2) {
                                return true;
                        }
                    }
                }
            }
            }
        }
        return false;
    }

    private boolean process(Shelf shelf, int i, int j, boolean[][] visited,String root) {
        //Check if the item is in the shelf and not visited
        if (i < 0 || i >= 6 || j < 0 || j >= 5 || visited[i][j]
                || !shelf.isOccupied(i,j) || shelf.getItemType(i,j)!=root)
        {
            System.out.println("c");
            return false;
        }

        System.out.println("d");
        //mark the tile as visited
        visited[i][j] = true;
        return true;
    }*/

    /**
     * Scan the shelf to find 2 groups of 4 tiles of the same color
     * @param s is the shelf
     * @return true if the goal is reached, false otherwise
     */
    @Override
    public boolean checkGoal(Shelf s){
        visited = new boolean[Shelf.sRow][Shelf.sColumn];

        String itemRef=null;


        //Scan full matrix(not row=5, it's useless to check)
        for(int i=0;i<(Shelf.sRow-1);i++){
            for(int j=0; j<Shelf.sColumn-1;j++){

                //(i,j) is the square root
                if(s.isOccupied(i,j) && !visited[i][j]){
                    if(itemRef==null){
                        itemRef=s.getItemType(i,j);
                    }

                    // Cell get visited
                    visited[i][j]=true;
                    // Check if there is a Square with same type
                    if(controlStatus(i+1,j,s,itemRef)
                            &&controlStatus(i+1,j+1,s,itemRef)
                            &&controlStatus(i,j+1,s,itemRef))
                    {
                        //Yes, We got a square!

                        if(find2ndSquare(i,j,itemRef,s)){
                            //End Check goal, we got 2 squares with same type
                            return true;
                        }else{
                            //reset itemRef
                            itemRef=null;
                        }
                    }


                }
                // when we end the check of the square(win or lost)
                // we go back to (i,j+1) in the for
            }
        }
        return false;
    }

    /**
     *
     * @param x is the row of the first square
     * @param y is the column of the first square
     * @param itemRef is the type of the first square
     * @param s is the shelf
     * @return
     */
    private boolean find2ndSquare(int x,int y,String itemRef,Shelf s){

        for(int i=x;i<Shelf.sRow-1;i++){
            for(int j=y;j<Shelf.sColumn-1;j++){
                //(i,j) is the square root
                if(controlStatus(i,j,s,itemRef)) {
                    if(controlStatus(i+1,j,s,itemRef)
                            &&controlStatus(i+1,j+1,s,itemRef)
                            &&controlStatus(i,j+1,s,itemRef))
                    {
                        //2nd Square found
                        return true;


                    }


                }

            }
        }

        return false;

    }

    /**
     *
     * @param r is the row of the cell
     * @param c is the column of the cell
     * @param s is the shelf
     * @param itemRef is the type of the first square
     * @return
     */
    private boolean controlStatus(int r,int c,Shelf s,String itemRef){
        if(s.isOccupied(r,c) &&s.getItemType(r,c).equals(itemRef) &&!visited[r][c]) {
            visited[r][c]=true;
            return true;
        }
        return false;


    }


}
