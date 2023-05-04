package org.am21.client.view.GUI;

import org.am21.client.view.GUI.component.GamePanel;

import javax.swing.*;
import java.awt.*;

public class LivingRoomInterface extends JFrame {

    public GamePanel gamePanel;
    public LivingRoomInterface(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int pcWidth = (int) screenSize.getWidth();
        int pcHeight = (int) screenSize.getHeight();


        try{
            //option menu
            JMenuBar jmbLivingRoom = new JMenuBar();
            JMenu jmOption = new JMenu("Option");
            jmOption.setFont(new Font("DejaVu Sans",Font.PLAIN,16));
            JMenuItem jmiRule = jmOption.add("Rule");
            jmiRule.setFont(new Font("DejaVu Sans",Font.PLAIN,16));
            //TODO:add Action listner
            JMenuItem jmiHelp = jmOption.add("Help");
            jmiHelp.setFont(new Font("DejaVu Sans",Font.PLAIN,16));
            //TODO:add Action listner
            JMenuItem jmiLeave = jmOption.add("Leave");
            jmiLeave.setFont(new Font("DejaVu Sans",Font.PLAIN,16));
            //TODO:add Action listner

            this.setJMenuBar(jmbLivingRoom);

            //Board Panel interface
            this.gamePanel = new GamePanel();
            this.add(this.gamePanel);

            // item visualization
            this.setTitle("My Shelf Room");
            this.setLayout(null);
            //this.setSize(666,666);
            //this.setResizable(false);
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setUndecorated(true);
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
            this.setVisible(true);
            System.out.println(" your PC have: \nhigh:"+pcHeight+"\n wide:"+pcWidth);



        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"something is wrong, please renter\r\n\r\n"+e.toString(),"Waring",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public static void main(String[] arg){
        new LivingRoomInterface();
    }

}
