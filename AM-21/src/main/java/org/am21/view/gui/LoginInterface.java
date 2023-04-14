package org.am21.view.gui;

import org.am21.model.component.BackGroundPanel;
import org.am21.utilities.PathUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class LoginInterface {
    JFrame frame = new JFrame("MyShelfie");

    public static final int WIDTH = 500;
    public static final int HEIGHT = 250;


    public void init() throws Exception{
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0,0,WIDTH,HEIGHT);
        frame.setResizable(false);

        frame.setIconImage(ImageIO.read(new File(PathUtils.getPath("Publisher material\\Icon 50x50px.png"))));
        BackGroundPanel backGroundPanel = new BackGroundPanel(ImageIO.read(new File(PathUtils.getPath("misc\\sfondo parquet.jpg"))));

        Box vbox = Box.createVerticalBox();

        // Create nickname
        Box nicknameBox = Box.createHorizontalBox();
        JLabel nicknameLabel = new JLabel("Nickname: ");
        JTextField nicknameField = new JTextField(15);
        // Create nickname button
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get user nickname
                String nickname = nicknameField.getText().trim();

                // login successful, login failed...

            }
        });


        nicknameBox.add(nicknameLabel);
        nicknameBox.add(Box.createHorizontalStrut(20));
        nicknameBox.add(nicknameField);
        nicknameBox.add(Box.createHorizontalStrut(20));
        nicknameBox.add(loginButton);

        vbox.add(Box.createVerticalStrut(HEIGHT/2));
        vbox.add(nicknameBox);

        backGroundPanel.add(vbox);
        frame.add(backGroundPanel);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        try {
            new LoginInterface().init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
