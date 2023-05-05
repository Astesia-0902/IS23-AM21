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
        background.put(ImageIO.read(new File
                (PathUtil.getPath("misc/sfondo parquet.jpg"))), new int[]{0, 0, WIDTH, HEIGHT});
        System.out.println("SFONDO: " + ImageIO.read(new File(PathUtil.getPath("misc/sfondo parquet.jpg"))));
        background.put(ImageIO.read(new File(PathUtil.getPath("Publisher material/Title 2000x2000px.png"))), new int[]{35, -100, 525, 450});
        System.out.println("Titolo" + ImageIO.read(new File(PathUtil.getPath("Publisher material/Title 2000x2000px.png"))));
        BackGroundPanel backGroundPanel = new BackGroundPanel(background);


        // Icon
        ImageIcon minusIcon = new ImageIcon(PathUtil.getPath("icon tool/minus.png"));
        minusLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                minusIcon.paintIcon(minusLabel,g,0,0);
            }
        };
        minusLabel.setBounds(540, 5, 25, 25);
        getContentPane().add(minusLabel);

        ImageIcon closeIcon = new ImageIcon(PathUtil.getPath("icon tool/close.png"));
        closeLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                closeIcon.paintIcon(closeLabel,g,0,0);
            }
        };
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
        socketButton.setBounds(25, 280, 250, 46);
        getContentPane().add(socketButton);

        // RMI Button
        rmiButton = new JButton("RMI");
        rmiButton.setForeground(new Color(139, 69, 19));
        rmiButton.setBackground(new Color(222, 184, 135));
        rmiButton.setUI(new ButtonColorUI(new Color(245, 225, 199)));
        rmiButton.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 250, 205),
                new Color(255, 250, 205), new Color(139, 69, 19), new Color(139, 69, 19)));
        rmiButton.setFont(new Font("Tempus Sans ITC", Font.BOLD, 18));
        rmiButton.setBounds(315, 280, 250, 46);
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

        //new CommunicationListener(this);

    }

    public static void main(String[] args) {
        try {
            new CommunicationInterface();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
