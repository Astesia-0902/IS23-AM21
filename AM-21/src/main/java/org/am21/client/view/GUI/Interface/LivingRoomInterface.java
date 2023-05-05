package org.am21.client.view.GUI.Interface;

import org.am21.client.view.GUI.component.GameBoardPanel;
import org.am21.client.view.GUI.component.GamePanel;

import javax.swing.*;

import static org.am21.client.view.GUI.utils.PixelUtil.pcHeight;
import static org.am21.client.view.GUI.utils.PixelUtil.pcWidth;

public class LivingRoomInterface extends JFrame {

    public JLayeredPane livingRoomPane;
    public GamePanel gamePanel;
    public GameBoardPanel gameBoardPanel;

    public LivingRoomInterface(){

        try{
            this.livingRoomPane = new JLayeredPane();
            this.gamePanel = new GamePanel();
            this.gameBoardPanel = new GameBoardPanel();
            this.livingRoomPane.setBounds(0,0,pcWidth,pcHeight);
            this.livingRoomPane.add(this.gamePanel,JLayeredPane.DEFAULT_LAYER);
            //this.livingRoomPane.add(this.gameBoardPane,JLayeredPane.MODAL_LAYER);
            //this.add(this.gamePanel);
            this.add(this.livingRoomPane);

            this.setLayout(null);
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setUndecorated(true);
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
            this.setVisible(true);

            System.out.println("Your PC have: \nhigh:"+ pcHeight+"\nwide:"+pcWidth);


        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"something is wrong, please renter\r\n\r\n"+e.toString(),"Waring",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public static void main(String[] arg){

        new LivingRoomInterface();

    }

}
