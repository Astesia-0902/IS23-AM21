package org.am21.client.view.GUI;

import org.am21.client.view.GUI.component.BackGroundPanel;
import org.am21.utilities.PathUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ServerInfoInterface {
    JFrame frame = new JFrame("MyShelfie - Server Info");

    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;

    private JTextField addressField;
    private JTextField portField;
    private JButton loginButton;
    private JLabel minusLabel;
    private JPanel panel;
    public void init() throws IOException {
        frame.setIconImage(ImageIO.read(new File(PathUtil.getPath("Publisher material\\Icon 50x50px.png"))));

        HashMap<BufferedImage, int[]> background = new HashMap<>();
        // Background
        background.put(ImageIO.read(new File(PathUtil.getPath
                ("misc\\base_pagina2.jpg"))), new int[]{0, 0, WIDTH, HEIGHT});
        background.put(ImageIO.read(new File(PathUtil.getPath
                ("Publisher material\\Title 2000x2000px.png"))), new int[]{35, -100, 525, 450});
        background.put(ImageIO.read(new File(PathUtil.getPath
                ("Publisher material\\banner 1386x400px.png"))), new int[]{0, 25, WIDTH, HEIGHT / 2});

        // Icon
        background.put(ImageIO.read(new File(PathUtil.getPath
                ("icon tool\\close.png"))),new int[]{570,0,25,25});
        background.put(ImageIO.read(new File(PathUtil.getPath
                ("icon tool\\minus.png"))),new int[]{540,0,25,25});


        BackGroundPanel backGroundPanel = new BackGroundPanel(background);

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
        loginButton.setBorder(new MatteBorder(0, 0, 5, 6, new Color(139, 69, 19)));
        loginButton.setBackground(new Color(222, 184, 135));
        loginButton.setBounds(249, 301, 82, 33);
        frame.getContentPane().add(loginButton);

        // Address Field
        addressField = new JTextField(15);
        addressField.setBackground(new Color(222, 184, 135));
        addressField.setBorder(new CompoundBorder(new MatteBorder(0, 0, 6, 5,
                new Color(139, 69, 19)), new EmptyBorder(0, 50, 0, 0)));
        addressField.setBounds(129, 200, 331, 46);
        frame.getContentPane().add(addressField);

        // PortField
        portField = new JTextField(15);
        portField.setBorder(new CompoundBorder(new MatteBorder(0, 0, 6, 5,
                new Color(139, 69, 19)), new EmptyBorder(0, 50, 0, 0)));
        portField.setBackground(new Color(222, 184, 135));
        portField.setBounds(129, 246, 331, 46);
        frame.getContentPane().add(portField);


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get user address and port
                String address = addressField.getText().trim();
                String port = portField.getText().trim();
                // login successful, login failed...

            }
        });

        frame.add(backGroundPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0,0,WIDTH,HEIGHT);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            new ServerInfoInterface().init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
