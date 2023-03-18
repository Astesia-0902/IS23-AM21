package org.am21.test;

import org.am21.model.items.Card.ItemTileCard;
import org.am21.model.items.Cell;
import org.am21.model.items.LivingRoomBoard;

import javax.swing.*;
public class TestRender1 extends JFrame {
    private LivingRoomBoard livingRoomBoard = LivingRoomBoard.livingRoomBoardBuild();     // Create a living room of 11*12
    public TestRender1() {
        init();             // Initialize window
        render();
        autoRefresh();
    }

    private void init(){
        this.setTitle("MyShelfie-test1");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    // Exit when close the window
        this.setLayout(null);
        this.setBounds(0,0,1000,1000);
        this.setLocationRelativeTo(null);                       // Window centering
        this.setVisible(true);
    }

    private void render(){
        Cell[][] cells = livingRoomBoard.getCells();
        livingRoomBoard.showCells();                    // Test
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                ItemTileCard itemTileCard1 = cells[row][col].getItemTileCard();
                int x = row * 90;
                int y = col * 90;
                itemTileCard1.setBounds(x,y,90,90);
                this.getContentPane().add(itemTileCard1);
            }
            System.out.println();
        }
    }

    private void autoRefresh(){
        final JFrame start = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    start.repaint();

                    try {
                        Thread.sleep(40);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        new TestRender1();
    }
}
