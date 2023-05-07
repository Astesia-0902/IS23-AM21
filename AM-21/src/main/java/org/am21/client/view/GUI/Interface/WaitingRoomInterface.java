package org.am21.client.view.GUI.Interface;

import org.am21.client.view.GUI.component.BackGroundPanel;
import org.am21.client.view.GUI.component.ButtonColorUI;
import org.am21.client.view.GUI.utils.PathUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

public class WaitingRoomInterface extends JFrame {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    public final JButton leaveButton;

    public WaitingRoomInterface() throws Exception {
        setTitle("MyShelfie - Waiting Room");
        setIconImage(ImageIO.read(new File(PathUtil.getPath("Publisher material/Icon 50x50px.png"))));

        //background
        HashMap<BufferedImage, int[]> background = new HashMap<>();
//        background.put(ImageIO.read(new File(PathUtil.getPath
//                ("background/Waiting Room BG.png"))), new int[]{-100, -220,800,900});
        background.put(ImageIO.read(new File(PathUtil.getPath
                ("background/Waiting Room BG2.png"))), new int[]{-210,-315,980,1050});
        BackGroundPanel backGroundPanel = new BackGroundPanel(background);

        JLabel waitingMessage = new JLabel("Waiting Players...");
        waitingMessage.setBorder(null);
        waitingMessage.setBounds(181,90 , 356, 108);
        waitingMessage.setForeground(new Color(237, 179, 137));
        waitingMessage.setFont(new Font("Snap ITC", Font.PLAIN, 26));
        waitingMessage.setOpaque(false);
        getContentPane().add(waitingMessage);


        // Leave Button
        leaveButton = new JButton("Leave");
        leaveButton.setForeground(new Color(139, 69, 19));
        leaveButton.setBackground(new Color(237, 179, 137));
        leaveButton.setUI(new ButtonColorUI(new Color(245, 225, 199)));
        leaveButton.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 250, 205),
                new Color(255, 250, 205), new Color(139, 69, 19), new Color(139, 69, 19)));
        leaveButton.setFont(new Font("Tempus Sans ITC", Font.BOLD, 18));
        leaveButton.setBounds(254, 280, 82, 33);
        getContentPane().add(leaveButton);


        backGroundPanel.setBorder(new MatteBorder(5, 5, 5, 5,
                new Color(139, 69, 19)));
        add(backGroundPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0,0,WIDTH,HEIGHT);
        setUndecorated(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        new WaitingRoomInterface();
    }
}
