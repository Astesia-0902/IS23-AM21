//package org.am21.client.view.GUI;
//
//import org.am21.client.view.GUI.component.BackGroundPanel;
//import org.am21.utilities.PathUtil;
//
//import javax.imageio.ImageIO;
//import javax.swing.*;
//import javax.swing.border.CompoundBorder;
//import javax.swing.border.EmptyBorder;
//import javax.swing.border.MatteBorder;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.util.HashMap;
//
//public class LoginInterface {
//    JFrame frame = new JFrame("MyShelfie");
//
//    public static final int WIDTH = 600;
//    public static final int HEIGHT = 400;
//
//
//    public void init() throws Exception {
//
//        frame.setIconImage(ImageIO.read(new File(PathUtil.getPath("Publisher material\\Icon 50x50px.png"))));
//
//        HashMap<BufferedImage, int[]> background = new HashMap<>();
//        background.put(ImageIO.read(new File(PathUtil.getPath("misc\\base_pagina2.jpg"))), new int[]{0, 0, WIDTH, HEIGHT});
//        background.put(ImageIO.read(new File(PathUtil.getPath("Publisher material\\Title 2000x2000px.png"))), new int[]{35, -100, 525, 450});
//        background.put(ImageIO.read(new File(PathUtil.getPath("Publisher material\\banner 1386x400px.png"))), new int[]{0, 25, WIDTH, HEIGHT / 2});
//
//        BackGroundPanel backGroundPanel = new BackGroundPanel(background);
//
//        // Create nickname
//        Box nicknameBox = Box.createVerticalBox();
//
//        JTextField nicknameField = new JTextField(15);
//        nicknameField.setBackground(new Color(222, 184, 135));
//        nicknameField.setBorder(new CompoundBorder(new MatteBorder(0, 0, 6, 5, new Color(139, 69, 19)), new EmptyBorder(0, 50, 0, 0)));
//        nicknameField.setPreferredSize(new Dimension(250, 35));
//
//        // Create nickname button
//        JButton loginButton = new JButton("Login");
//        loginButton.setBackground(new Color(222, 184, 135));
//        loginButton.setBorder(new MatteBorder(0, 0, 6, 5, new Color(139, 69, 19)));
//        loginButton.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
//        loginButton.setPreferredSize(new Dimension(250, loginButton.getPreferredSize().height));
//
//
//
//        loginButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // get user nickname
//                String nickname = nicknameField.getText().trim();
//
//                // login successful, login failed...
//
//            }
//        });
//
//        nicknameBox.add(Box.createVerticalStrut(210));
//        nicknameBox.add(Box.createVerticalStrut(20));
//        nicknameBox.add(nicknameField);
//        nicknameBox.add(Box.createVerticalStrut(20));
//        nicknameBox.add(Box.createHorizontalStrut(100));
//        nicknameBox.add(loginButton);
//
//        backGroundPanel.add(nicknameBox);
//        frame.add(nicknameBox);
//        frame.add(backGroundPanel);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setBounds(0, 0, WIDTH, HEIGHT);
//        //frame.setUndecorated(true);
//        frame.setResizable(false);
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//    }
//
//
//    public static void main(String[] args) {
//        try {
//            new LoginInterface().init();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
