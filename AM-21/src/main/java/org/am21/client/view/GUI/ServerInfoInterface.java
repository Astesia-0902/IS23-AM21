package org.am21.client.view.GUI;

import org.am21.client.view.GUI.component.BackGroundPanel;
import org.am21.client.view.GUI.component.ButtonColorUI;
import org.am21.client.view.GUI.component.PathUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ServerInfoInterface extends JFrame {
    public static final int WIDTH = 300;
    public static final int HEIGHT = 250;

    public JTextField addressField;
    public JTextField portField;
    public JButton confirmButton;
    public JLabel minusLabel;
    public JLabel closeLabel;
    public JLabel returnLabel;

    public ServerInfoInterface() throws IOException {
        setTitle("MyShelfie - Server Info");
        setIconImage(ImageIO.read(new File(PathUtil.getPath("Publisher material/Icon 50x50px.png"))));

        HashMap<BufferedImage, int[]> background = new HashMap<>();
        // Background
        background.put(ImageIO.read(new File(PathUtil.getPath
                ("misc/base_pagina2.jpg"))), new int[]{0, 0, WIDTH, HEIGHT});
        System.out.println("BASE: " + ImageIO.read(new File(PathUtil.getPath("misc/base_pagina2.jpg"))));

//        background.put(ImageIO.read(new File(PathUtil.getPath
//                ("Publisher material/Title 2000x2000px.png"))), new int[]{35, -100, 525, 450});
//        background.put(ImageIO.read(new File(PathUtil.getPath
//                ("Publisher material/banner 1386x400px.png"))), new int[]{0, 25, WIDTH, HEIGHT / 2});

        // Icon
        ImageIcon minusIcon = new ImageIcon(PathUtil.getPath("icon tool/minus.png"));
        minusLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                minusIcon.paintIcon(minusLabel,g,0,0);
            }
        };
        minusLabel.setBounds(240, 5, 25, 25);
        getContentPane().add(minusLabel);

        ImageIcon closeIcon = new ImageIcon(PathUtil.getPath("icon tool/close.png"));
        closeLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                closeIcon.paintIcon(closeLabel,g,0,0);
            }
        };
        closeLabel.setBounds(270, 5, 25, 25);
        getContentPane().add(closeLabel);

        ImageIcon returnIcon = new ImageIcon(PathUtil.getPath("icon tool/return.png"));
        returnLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                returnIcon.paintIcon(returnLabel,g,0,0);
            }
        };
        returnLabel.setBounds(15, 15, 25, 25);
        getContentPane().add(returnLabel);

        BackGroundPanel backGroundPanel = new BackGroundPanel(background);

        // Login Button
        confirmButton = new JButton("Confirm");
        confirmButton.setFont(new Font("Tempus Sans ITC", Font.BOLD, 18));
        confirmButton.setForeground(new Color(139, 69, 19));
        confirmButton.setUI(new ButtonColorUI(new Color(245, 225, 199)));
        confirmButton.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 250, 205),
                new Color(255, 250, 205), new Color(139, 69, 19), new Color(139, 69, 19)));
        //confirmButton.setBorder(new MatteBorder(0, 0, 5, 6, new Color(139, 69, 19)));
        confirmButton.setBackground(new Color(222, 184, 135));
        confirmButton.setBounds(25, 183, 250, 46);
        getContentPane().add(confirmButton);

        // Address Field
        ImageIcon addressIcon = new ImageIcon(PathUtil.getPath("icon tool/IP.png"));
        addressField = new JTextField(15){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                addressIcon.paintIcon(addressField,g,5,5);
            }
        };
        addressField.setText("localhost");
        addressField.setForeground(new Color(255, 255, 240));
        addressField.setFont(new Font("Serif", Font.BOLD, 23));
        addressField.setBackground(new Color(222, 184, 135));
        addressField.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 250, 205),
                new Color(255, 250, 205), new Color(139, 69, 19), new Color(139, 69, 19)),
                new EmptyBorder(0, 50, 0, 0)));
        addressField.setBounds(25, 40, 250, 46);
        getContentPane().add(addressField);

        // PortField
        ImageIcon portIcon = new ImageIcon(PathUtil.getPath("icon tool/server.png"));
        portField = new JTextField(15){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                portIcon.paintIcon(portField,g,5,5);
            }
        };
        portField.setText("8803");
        portField.setForeground(new Color(255, 255, 240));
        portField.setFont(new Font("Serif", Font.BOLD, 23));
        portField.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 250, 205),
                new Color(255, 250, 205), new Color(139, 69, 19), new Color(139, 69, 19)),
                new EmptyBorder(0, 50, 0, 0)));
        portField.setBackground(new Color(222, 184, 135));
        portField.setBounds(25, 105, 250, 46);
        getContentPane().add(portField);

        backGroundPanel.setBorder(new MatteBorder(5, 5, 5, 5,new Color(139, 69, 19)));
        add(backGroundPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0,0,WIDTH,HEIGHT);
        setUndecorated(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
