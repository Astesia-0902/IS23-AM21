package org.am21.client.view.GUI.Interface;

import org.am21.client.view.GUI.component.*;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;

public class LivingRoomInterface extends JFrame {

    public JLayeredPane livingRoomPane;
    public GamePanel gamePanel;
    public GameBoardPanel gameBoardPanel;
    public CommonGoalPanel commonGoalPanel;
    public PersonalGoalPanel personalGoalPanel;

    public ShelfPanel myShelfPanel;
    public ShelfPanel enemyAShelfPanel;
    public ShelfPanel enemyBShelfPanel;
    public ShelfPanel enemyCShelfPanel;
    public LivingRoomInterface(){
        super("living room");

        try{
            this.livingRoomPane = new JLayeredPane();
            this.gamePanel = new GamePanel();
            this.gameBoardPanel = new GameBoardPanel();
            //this.commonGoalPanel = new CommonGoalPanel();
            //this.personalGoalPanel = new PersonalGoalPanel();
            this.myShelfPanel = new ShelfPanel(PixelUtil.myGridX,PixelUtil.myGridY,PixelUtil.myCellH,PixelUtil.myCellW,PixelUtil.myItemHW);

            this.enemyAShelfPanel = new ShelfPanel(PixelUtil.enemyGridX,PixelUtil.enemyAGridY,PixelUtil.enemyCellH,PixelUtil.enemyCellW,PixelUtil.enemyItemHW);
            this.enemyBShelfPanel = new ShelfPanel(PixelUtil.enemyGridX,PixelUtil.enemyBGridY,PixelUtil.enemyCellH,PixelUtil.enemyCellW,PixelUtil.enemyItemHW);
            this.enemyCShelfPanel = new ShelfPanel(PixelUtil.enemyGridX,PixelUtil.enemyCGridY,PixelUtil.enemyCellH,PixelUtil.enemyCellW,PixelUtil.enemyItemHW);

            this.livingRoomPane.setBounds(0,0, PixelUtil.pcWidth,PixelUtil.pcHeight);
            this.livingRoomPane.add(this.gamePanel,JLayeredPane.DEFAULT_LAYER);
            this.livingRoomPane.add(this.gameBoardPanel,JLayeredPane.PALETTE_LAYER);
            //this.livingRoomPane.add(this.commonGoalPanel,JLayeredPane.PALETTE_LAYER);
            //this.livingRoomPane.add(this.personalGoalPanel,JLayeredPane.PALETTE_LAYER);
            this.livingRoomPane.add(this.myShelfPanel,JLayeredPane.PALETTE_LAYER);
            this.livingRoomPane.add(this.enemyAShelfPanel,JLayeredPane.PALETTE_LAYER);
            this.livingRoomPane.add(this.enemyBShelfPanel,JLayeredPane.PALETTE_LAYER);
            this.livingRoomPane.add(this.enemyCShelfPanel,JLayeredPane.PALETTE_LAYER);




            this.getContentPane().add(livingRoomPane);

            this.setLayout(null);
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setUndecorated(true);
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
            this.setVisible(true);

            System.out.println("Your PC have: \nhigh:"+ PixelUtil.pcHeight+"\nwide:"+PixelUtil.pcWidth);


        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"something is wrong, please renter\r\n\r\n"+e.toString(),"Waring",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public static void main(String[] arg){

        new LivingRoomInterface();

    }

}
