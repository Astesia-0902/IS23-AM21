package org.am21.model.items;

import org.am21.model.Cards.ItemCard;
import org.am21.model.GameManager;
import org.am21.model.enumer.ServerMessage;

public abstract class Grid {
    public int gRow;
    public int gColumn;
    public int capacity;
    private ItemCard[][] matrix;

    public Grid(int gRow, int gColumn) {
        this.gRow = gRow;
        this.gColumn = gColumn;
        this.capacity = this.gRow * this.gColumn;
        this.matrix = new ItemCard[this.gRow][this.gColumn];
    }

    public ItemCard[][] getMatrix() {

        return matrix;
    }

    public ItemCard getCell(int r, int c){

        return matrix[r][c];
    }



    public void setCell(int rowNum, int colNum, ItemCard value){

        this.matrix[rowNum][colNum] = value;
    }



    /**
     * Obtain Item's Reference
     * @param rowNum
     * @param colNum
     * @return ItemName
     */
    public String getItemName(int rowNum,int colNum){

        if (matrix[rowNum][colNum]!= null) {
            return matrix[rowNum][colNum].getNameCard();
        }
        return null;
    }

    /**
     * Setting a new name to the item in the cell
     * @param r
     * @param c
     * @param name
     * @return true if name changed
     *          false if name changing failed cause there are no item in the cell
     */
    public boolean setItemName(int r,int c,String name){
        if(matrix[r][c]!=null){
            matrix[r][c].setName(name);
            return true;
        }
        return false;

    }

    public boolean isOccupied(int r, int c) {

        if (getCell(r, c) != null) {

            /*Cell (r,c) occupied*/
            return true;
        }
        return false;
    }

    /**
     * Estract Item Type from the name of the item in the cell
     * @return
     */
    public String getItemType(int r,int c){
        if (matrix[r][c]!= null) {
            return matrix[r][c].getNameCard().substring(0,matrix[r][c].getNameCard().length()-3);
        }
        return null;
    }


}
