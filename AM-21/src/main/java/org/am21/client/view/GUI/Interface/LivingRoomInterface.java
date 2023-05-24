package org.am21.client.view.GUI.Interface;

import org.am21.client.view.GUI.component.*;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;

public class LivingRoomInterface extends JDialog{
    public JLayeredPane livingRoomPane;
    public LivingRoomPanel livingRoomPanel;
    public LivingRoomInterface(JFrame frame){
        super(frame);

        try{

            livingRoomPane = new JLayeredPane();
            livingRoomPane.setBounds(0,0, PixelUtil.pcWidth,PixelUtil.pcHeight);

            livingRoomPanel = new LivingRoomPanel();
            livingRoomPane.add(livingRoomPanel,JLayeredPane.DEFAULT_LAYER);

            getContentPane().add(livingRoomPane);

            setSize(PixelUtil.pcWidth,PixelUtil.pcHeight);
            setLayout(null);
            setUndecorated(true);
            setLocationRelativeTo(null);
            setVisible(true);
            System.out.println("Your PC have: \nhigh:"+ PixelUtil.pcHeight+"\nwide:"+PixelUtil.pcWidth);


        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"something is wrong, please renter\r\n\r\n"+e.toString(),"Waring",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
}
