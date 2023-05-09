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

    public MyHandBoardPanel myHandBoardPanel;
    public LivingRoomInterface(){
        super("living room");

        try{

            livingRoomPane = new JLayeredPane();
            livingRoomPane.setBounds(0,0, PixelUtil.pcWidth,PixelUtil.pcHeight);

            gamePanel = new GamePanel();
            gameBoardPanel = new GameBoardPanel();
            commonGoalPanel = new CommonGoalPanel("2Columns","XShape");
            personalGoalPanel = new PersonalGoalPanel("Goals6");
            myShelfPanel = new ShelfPanel(PixelUtil.myGridX,PixelUtil.myGridY,PixelUtil.myCellH,PixelUtil.myCellW,PixelUtil.myItemW,PixelUtil.myItemH);
            enemyAShelfPanel = new ShelfPanel(PixelUtil.enemyGridX,PixelUtil.enemyAGridY,PixelUtil.enemyCellH,PixelUtil.enemyCellW,PixelUtil.enemyItemW,PixelUtil.enemyItemH);
            enemyBShelfPanel = new ShelfPanel(PixelUtil.enemyGridX,PixelUtil.enemyBGridY,PixelUtil.enemyCellH,PixelUtil.enemyCellW,PixelUtil.enemyItemW,PixelUtil.enemyItemH);
            enemyCShelfPanel = new ShelfPanel(PixelUtil.enemyGridX,PixelUtil.enemyCGridY,PixelUtil.enemyCellH,PixelUtil.enemyCellW,PixelUtil.enemyItemW,PixelUtil.enemyItemH);
            myHandBoardPanel = new MyHandBoardPanel();



            livingRoomPane.add(gamePanel,JLayeredPane.DEFAULT_LAYER);
            livingRoomPane.add(gameBoardPanel,JLayeredPane.PALETTE_LAYER);
            livingRoomPane.add(commonGoalPanel,JLayeredPane.PALETTE_LAYER);
            livingRoomPane.add(personalGoalPanel,JLayeredPane.PALETTE_LAYER);
            livingRoomPane.add(myShelfPanel,JLayeredPane.PALETTE_LAYER);
            livingRoomPane.add(enemyAShelfPanel,JLayeredPane.PALETTE_LAYER);
            livingRoomPane.add(enemyBShelfPanel,JLayeredPane.PALETTE_LAYER);
            livingRoomPane.add(enemyCShelfPanel,JLayeredPane.PALETTE_LAYER);
            livingRoomPane.add(myHandBoardPanel,JLayeredPane.PALETTE_LAYER);



            getContentPane().add(livingRoomPane);

            setLayout(null);
            setUndecorated(true);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setLocationRelativeTo(null);



            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
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
