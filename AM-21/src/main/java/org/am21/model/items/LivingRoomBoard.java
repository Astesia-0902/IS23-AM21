package org.am21.model.items;

import org.am21.model.items.Card.ItemTileCard;

public class LivingRoomBoard {
    //我先假设是个11*12的Matrice, 到时候你自己改改 --Simona
    private Integer rowNum = 11;
    private Integer colNum = 12;
    private Integer capacity;

    // 我先默认 132个格子, 然后到时候你可以按照人数改size --Simona
    private Integer size;               // The number of cards required varies according to the number of players

    private Cell[][] cells;

    public LivingRoomBoard() {
        this.capacity = this.rowNum * this.colNum;            // 132 item tiles
        this.cells = new Cell[this.rowNum][this.colNum];
        this.size = 0;
    }

    // 为了测试...      --Simona
    public void showCells(){
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                ItemTileCard itemTileCard1 = cells[row][col].getItemTileCard();
                System.out.print(itemTileCard1.getName()+"\t");
            }
            System.out.println();
        }
    }

    // 创建个房间...     --Simona
    public static LivingRoomBoard livingRoomBoardBuild(){
        LivingRoomBoard livingRoomBoard = new LivingRoomBoard();
        ItemTileCard[] itemTileCards = ItemTileCard.buildCards();        // Create 132 random cards

        Cell[][] cells = livingRoomBoard.getCells();                    // Create a matrix of 11*12
        int index = 0;
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                ItemTileCard itemTileCard1 = itemTileCards[index++];    // Recording each card

                Cell cell = new Cell();
                cell.setState(1);                                       // State = 1 means there are cards here
                cell.setItemTileCard(itemTileCard1);                    // Adding card to cell

                itemTileCard1.setCell(cell);                            // For better subsequent removal of cards from the screen

                cells[row][col] = cell;                                 // Adding cell to matrix
            }
        }

        return livingRoomBoard;
    }


    // 我把以上的variabile的 get 和 set 都加上了, 你看着需求删除添加吧 --Simona
    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public Integer getColNum() {
        return colNum;
    }

    public void setColNum(Integer colNum) {
        this.colNum = colNum;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }
}

