package org.am21.client.view.GUI.Interface;

import org.am21.client.view.GUI.component.*;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;

public class LivingRoomInterface extends JDialog{

    public JLayeredPane livingRoomPane;
    public LivingRoomPanel livingRoomPanel;
    public GameBoardPanel gameBoardPanel;
    public CommonGoalPanel commonGoalPanel;
    public PersonalGoalPanel personalGoalPanel;

    public ShelfPanel myShelfPanel;
    public ShelfPanel enemyAShelfPanel;
    public ShelfPanel enemyBShelfPanel;
    public ShelfPanel enemyCShelfPanel;

    public ChairManLabel chairManLabel;

    public MyHandBoardPanel myHandBoardPanel;
    public LivingRoomInterface(JFrame frame){
        //frame = new JFrame("livingRoom");
        super(frame);

        try{

            livingRoomPane = new JLayeredPane();
            livingRoomPane.setBounds(0,0, PixelUtil.pcWidth,PixelUtil.pcHeight);

            livingRoomPanel = new LivingRoomPanel();
            gameBoardPanel = new GameBoardPanel();
            myShelfPanel = new ShelfPanel(PixelUtil.myGridX,PixelUtil.myGridY,PixelUtil.myCellW,PixelUtil.myCellH,PixelUtil.myItemW,PixelUtil.myItemH);


            //commonGoalPanel = new CommonGoalPanel("2Columns","XShape");
            //personalGoalPanel = new PersonalGoalPanel("Goals6");
            //enemyAShelfPanel = new ShelfPanel(PixelUtil.enemyGridX,PixelUtil.enemyAGridY,PixelUtil.enemyCellW,PixelUtil.enemyCellH,PixelUtil.enemyItemW,PixelUtil.enemyItemH);
            //enemyBShelfPanel = new ShelfPanel(PixelUtil.enemyGridX,PixelUtil.enemyBGridY,PixelUtil.enemyCellW,PixelUtil.enemyCellH,PixelUtil.enemyItemW,PixelUtil.enemyItemH);
           // enemyCShelfPanel = new ShelfPanel(PixelUtil.enemyGridX,PixelUtil.enemyCGridY,PixelUtil.enemyCellW,PixelUtil.enemyCellH,PixelUtil.enemyItemW,PixelUtil.enemyItemH);
            myHandBoardPanel = new MyHandBoardPanel();
            //chairManLabel = new ChairManLabel(3);

            livingRoomPane.add(livingRoomPanel,JLayeredPane.DEFAULT_LAYER);
            livingRoomPane.add(gameBoardPanel,JLayeredPane.PALETTE_LAYER);

            //livingRoomPane.add(commonGoalPanel,JLayeredPane.PALETTE_LAYER);
            //livingRoomPane.add(personalGoalPanel,JLayeredPane.PALETTE_LAYER);


            livingRoomPane.add(myShelfPanel,JLayeredPane.PALETTE_LAYER);
           // livingRoomPane.add(enemyAShelfPanel,JLayeredPane.PALETTE_LAYER);
            //livingRoomPane.add(enemyBShelfPanel,JLayeredPane.PALETTE_LAYER);
            //livingRoomPane.add(enemyCShelfPanel,JLayeredPane.PALETTE_LAYER);
            livingRoomPane.add(myHandBoardPanel,JLayeredPane.PALETTE_LAYER);

           // livingRoomPane.add(chairManLabel,JLayeredPane.PALETTE_LAYER);

            getContentPane().add(livingRoomPane);

            setSize(PixelUtil.pcWidth,PixelUtil.pcHeight);
            setLayout(null);
            setUndecorated(true);
            setLocationRelativeTo(null);
            //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
            System.out.println("Your PC have: \nhigh:"+ PixelUtil.pcHeight+"\nwide:"+PixelUtil.pcWidth);


        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"something is wrong, please renter\r\n\r\n"+e.toString(),"Waring",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
}
