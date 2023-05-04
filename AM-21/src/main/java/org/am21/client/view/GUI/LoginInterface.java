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
import java.util.HashMap;

public class LoginInterface extends JFrame {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    public JButton loginButton;
    public JTextField nicknameField;
    public JLabel minusLabel;
    public JLabel closeLabel;


    public LoginInterface() throws Exception{
        setTitle("MyShelfie - Login");
        setIconImage(ImageIO.read(new File(PathUtil.getPath("Publisher material/Icon 50x50px.png"))));

        HashMap<BufferedImage, int[]> background = new HashMap<>();
        //background
        background.put(ImageIO.read(new File(PathUtil.getPath
                ("misc/base_pagina2.jpg"))), new int[]{0, 0, WIDTH, HEIGHT});
        System.out.println("BASE: " + ImageIO.read(new File(PathUtil.getPath("misc/base_pagina2.jpg"))));

        background.put(ImageIO.read(new File(PathUtil.getPath
                ("Publisher material/Title 2000x2000px.png"))), new int[]{35, -100, 525, 450});
        System.out.println("TITOLO: " + ImageIO.read(new File(PathUtil.getPath("Publisher material/Title 2000x2000px.png"))));

        background.put(ImageIO.read(new File(PathUtil.getPath
                ("Publisher material/banner 1386x400px.png"))), new int[]{0, 30, WIDTH, HEIGHT / 2});
        System.out.println("BANNER: " + ImageIO.read(new File(PathUtil.getPath("Publisher material/banner 1386x400px.png"))));

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


        // Login Button
        loginButton = new JButton("Login");
        loginButton.setForeground(new Color(139, 69, 19));
        loginButton.setBackground(new Color(222, 184, 135));
        loginButton.setUI(new ButtonColorUI(new Color(245, 225, 199)));
        loginButton.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 250, 205),
                new Color(255, 250, 205), new Color(139, 69, 19), new Color(139, 69, 19)));
        //loginButton.setBorder(new MatteBorder(0, 0, 5, 6, new Color(139, 69, 19)));
        loginButton.setFont(new Font("Tempus Sans ITC", Font.BOLD, 18));
        loginButton.setBounds(254, 320, 82, 33);
        getContentPane().add(loginButton);

        // Nickname Field
        ImageIcon nicknametIcon = new ImageIcon(PathUtil.getPath("icon tool/user.png"));
        nicknameField = new JTextField(15){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                nicknametIcon.paintIcon(nicknameField,g,5,5);
            }
        };
        nicknameField.setForeground(new Color(255, 255, 240));
        nicknameField.setFont(new Font("Serif", Font.BOLD, 23));
        nicknameField.setBackground(new Color(222, 184, 135));
        nicknameField.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 250, 205),
                new Color(255, 250, 205), new Color(139, 69, 19), new Color(139, 69, 19)),
                new EmptyBorder(0, 50, 0, 0)));
//        nicknameField.setBorder(new CompoundBorder(new MatteBorder(0, 0, 6, 5,
//                new Color(139, 69, 19)), new EmptyBorder(0, 50, 0, 0)));
        nicknameField.setBounds(135, 255, 331, 46);
        getContentPane().add(nicknameField);

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
