package org.am21.client.view.GUI;

import org.am21.client.view.GUI.component.BackGroundPanel;
import org.am21.utilities.PathUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ServerInfoInterface {
    JFrame frame = new JFrame("MyShelfie - Server Info");

    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;

    public JTextField addressField;
    public JTextField portField;
    public JButton enterButton;
    public JLabel minusLabel;
    public JLabel closeLabel;

    public void init() throws IOException {
        frame.setIconImage(ImageIO.read(new File(PathUtil.getPath("Publisher material/Icon 50x50px.png"))));

        HashMap<BufferedImage, int[]> background = new HashMap<>();
        // Background
        background.put(ImageIO.read(new File(PathUtil.getPath
                ("misc/base_pagina2.jpg"))), new int[]{0, 0, WIDTH, HEIGHT});
        background.put(ImageIO.read(new File(PathUtil.getPath
                ("Publisher material/Title 2000x2000px.png"))), new int[]{35, -100, 525, 450});
        background.put(ImageIO.read(new File(PathUtil.getPath
                ("Publisher material/banner 1386x400px.png"))), new int[]{0, 25, WIDTH, HEIGHT / 2});

        // Icon
        ImageIcon minusIcon = new ImageIcon(PathUtil.getPath("icon tool/minus.png"));
        minusLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                minusIcon.paintIcon(minusLabel,g,3,3);
            }
        };
        minusLabel.setBounds(540, -2, 25, 25);
        frame.getContentPane().add(minusLabel);

        ImageIcon closeIcon = new ImageIcon(PathUtil.getPath("icon tool/close.png"));
        closeLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                closeIcon.paintIcon(closeLabel,g,3,3);
            }
        };
        closeLabel.setBounds(570, -2, 25, 25);
        frame.getContentPane().add(closeLabel);



        BackGroundPanel backGroundPanel = new BackGroundPanel(background);

        // Login Button
        enterButton = new JButton("Enter");
        enterButton.setFont(new Font("Tempus Sans ITC", Font.BOLD, 18));
        enterButton.setForeground(new Color(255, 250, 205));
        enterButton.setBorder(new MatteBorder(0, 0, 5, 6, new Color(139, 69, 19)));
        enterButton.setBackground(new Color(222, 184, 135));
        enterButton.setBounds(249, 340, 82, 33);
        frame.getContentPane().add(enterButton);

        // Address Field
        ImageIcon addressIcon = new ImageIcon(PathUtil.getPath("icon tool/IP.png"));
        addressField = new JTextField(15){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                addressIcon.paintIcon(addressField,g,3,0);
            }
        };
        addressField.setText("localhost");
        addressField.setForeground(new Color(255, 255, 240));
        addressField.setFont(new Font("Serif", Font.BOLD, 23));
        addressField.setBackground(new Color(222, 184, 135));
        addressField.setBorder(new CompoundBorder(new MatteBorder(0, 0, 6, 5,
                new Color(139, 69, 19)), new EmptyBorder(0, 50, 0, 0)));
        addressField.setBounds(129, 240, 331, 46);
        frame.getContentPane().add(addressField);

        // PortField
        ImageIcon portIcon = new ImageIcon(PathUtil.getPath("icon tool/server.png"));
        portField = new JTextField(15){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                portIcon.paintIcon(portField,g,3,3);
            }
        };
        portField.setText("8803");
        portField.setForeground(new Color(255, 255, 240));
        portField.setFont(new Font("Serif", Font.BOLD, 23));
        portField.setBorder(new CompoundBorder(new MatteBorder(0, 0, 6, 5,
                new Color(139, 69, 19)), new EmptyBorder(0, 50, 0, 0)));
        portField.setBackground(new Color(222, 184, 135));
        portField.setBounds(129, 286, 331, 46);
        frame.getContentPane().add(portField);


        frame.add(backGroundPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0,0,WIDTH,HEIGHT);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        new ServerInfoListener(this);
    }

    public static void main(String[] args) {
        try {
            new ServerInfoInterface().init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
