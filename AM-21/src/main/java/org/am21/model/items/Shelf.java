package org.am21.model.items;

public class Shelf {
    private int rowNum = 6;
    private int colNum = 5;
    private Cell[][] cells;
    private int maxCard;
    private Player shelfId;

    public List<List<ItemTiteCard>> bookshelfGrid;

    public Shelf(){
        this.maxCard=rowNum*colNum;
        this.cells= new Cell[row][col];
        this.bookshelfGrid=new Shelf();
        this.shelfId= Player(); //associate shelf of player
    }

    public Shelf myShelf(){
        //my shelf item matrix
        Shelf myPersonalShelf= new Shelf();
        Cell[][] cells = Shelf.getCells();
    }
    public void getCard(){
        //get card from player
    }


    public void setCard(){
        //put card into shelf
        //select the the colum from 1 to 3
    }



}
