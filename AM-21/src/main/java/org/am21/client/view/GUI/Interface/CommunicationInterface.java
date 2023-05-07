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
import java.io.IOException;
import java.util.HashMap;

public class CommunicationInterface extends JFrame {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    public JLabel minusLabel;
    public JLabel closeLabel;
    public JButton socketButton;
    public JButton rmiButton;

    public CommunicationInterface() throws IOException {
        setTitle("MyShelfie - Communication");
        setIconImage(ImageIO.read(new File(PathUtil.getPath("Publisher material/Icon 50x50px.png"))));

        HashMap<BufferedImage, int[]> background = new HashMap<>();
        // Background
        background.put(ImageIO.read(new File(PathUtil.getPath("background/communicationBG.png"))), new int[]{0, -90, 600, 600});
        BackGroundPanel backGroundPanel = new BackGroundPanel(background);
        JLabel question = new JLabel("Socket or RMI");
        question.setBorder(null);
        question.setBounds(181,195 , 356, 108);
        question.setForeground(new Color(139, 69, 19));
        question.setFont(new Font("Snap ITC", Font.PLAIN, 26));
        question.setOpaque(false);
        getContentPane().add(question);

        // Icon
        ImageIcon minusIcon = new ImageIcon(PathUtil.getPath("icon tool/minus.png"));
        minusLabel = new JLabel(minusIcon);
        minusLabel.setBounds(540, 5, 25, 25);
        getContentPane().add(minusLabel);

        ImageIcon closeIcon = new ImageIcon(PathUtil.getPath("icon tool/close.png"));
        closeLabel = new JLabel(closeIcon);
        closeLabel.setBounds(570, 5, 25, 25);
        getContentPane().add(closeLabel);

        // Socket Button
        socketButton = new JButton("Socket");
        socketButton.setForeground(new Color(139, 69, 19));
        socketButton.setBackground(new Color(222, 184, 135));
        socketButton.setUI(new ButtonColorUI(new Color(245, 225, 199)));
        socketButton.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 250, 205),
                new Color(255, 250, 205), new Color(139, 69, 19), new Color(139, 69, 19)));
        socketButton.setFont(new Font("Tempus Sans ITC", Font.BOLD, 18));
        socketButton.setBounds(30, 295, 250, 46);
        getContentPane().add(socketButton);

        // RMI Button
        rmiButton = new JButton("RMI");
        rmiButton.setForeground(new Color(139, 69, 19));
        rmiButton.setBackground(new Color(222, 184, 135));
        rmiButton.setUI(new ButtonColorUI(new Color(245, 225, 199)));
        rmiButton.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 250, 205),
                new Color(255, 250, 205), new Color(139, 69, 19), new Color(139, 69, 19)));
        rmiButton.setFont(new Font("Tempus Sans ITC", Font.BOLD, 18));
        rmiButton.setBounds(320, 295, 250, 46);
        getContentPane().add(rmiButton);


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

}
