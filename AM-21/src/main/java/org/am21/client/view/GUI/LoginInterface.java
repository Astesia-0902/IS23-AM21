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
import java.util.HashMap;

public class LoginInterface {
    JFrame frame = new JFrame("MyShelfie - Login");

    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    public JButton loginButton;
    public JTextField nicknameField;
    public JLabel minusLabel;
    public JLabel closeLabel;


    public void init() throws Exception{
        frame.setIconImage(ImageIO.read(new File(PathUtil.getPath("Publisher material/Icon 50x50px.png"))));

        HashMap<BufferedImage, int[]> background = new HashMap<>();
        //background
        background.put(ImageIO.read(new File(PathUtil.getPath
                ("misc/base_pagina2.jpg"))), new int[]{0, 0, WIDTH, HEIGHT});
        background.put(ImageIO.read(new File(PathUtil.getPath
                ("Publisher material/Title 2000x2000px.png"))), new int[]{35, -100, 525, 450});
        background.put(ImageIO.read(new File(PathUtil.getPath
                ("Publisher material/banner 1386x400px.png"))), new int[]{0, 25, WIDTH, HEIGHT / 2});

        BackGroundPanel backGroundPanel = new BackGroundPanel(background);

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


        // Login Button
        loginButton = new JButton("Login");
        loginButton.setForeground(new Color(255, 250, 205));
        loginButton.setBackground(new Color(222, 184, 135));
        loginButton.setBorder(new MatteBorder(0, 0, 5, 6, new Color(139, 69, 19)));
        loginButton.setFont(new Font("Tempus Sans ITC", Font.BOLD, 18));
        loginButton.setBounds(254, 320, 82, 33);
        frame.getContentPane().add(loginButton);

        // Nickname Field
        ImageIcon nicknametIcon = new ImageIcon(PathUtil.getPath("icon tool/user.png"));
        nicknameField = new JTextField(15){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                nicknametIcon.paintIcon(nicknameField,g,3,3);
            }
        };
        nicknameField.setForeground(new Color(255, 255, 240));
        nicknameField.setFont(new Font("Serif", Font.BOLD, 23));
        nicknameField.setBackground(new Color(222, 184, 135));
        nicknameField.setBorder(new CompoundBorder(new MatteBorder(0, 0, 6, 5,
                new Color(139, 69, 19)), new EmptyBorder(0, 50, 0, 0)));
        nicknameField.setBounds(135, 255, 331, 46);
        frame.getContentPane().add(nicknameField);

        frame.add(backGroundPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0,0,WIDTH,HEIGHT);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        new LoginListener(this);
    }

    public static void main(String[] args) {
        try {
            new LoginInterface().init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
