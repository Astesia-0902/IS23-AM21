package org.am21.client.view.GUI.Interface;

import org.am21.client.view.GUI.component.BackGroundPanel;
import org.am21.client.view.GUI.utils.ButtonUtil;
import org.am21.client.view.GUI.utils.PathUtil;

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

public class LoginInterface extends JDialog {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    public JButton loginButton;
    public JTextField nicknameField;
    public JLabel minusLabel;
    public JLabel closeLabel;


    public LoginInterface(JFrame frame) throws Exception{
        super(frame);
        frame.setTitle("MyShelfie - Login");
        HashMap<BufferedImage, int[]> background = new HashMap<>();
        //background

        background.put(ImageIO.read(new File(PathUtil.getPath
                ("background/loginBG.png"))), new int[]{-80, -231, 800, 800});
        BackGroundPanel backGroundPanel = new BackGroundPanel(background);

        // Icon
        ImageIcon minusIcon = new ImageIcon(PathUtil.getPath("icon tool/minus.png"));
        minusLabel = new JLabel(minusIcon);
        minusLabel.setBounds(540, 5, 25, 25);
        getContentPane().add(minusLabel);

        ImageIcon closeIcon = new ImageIcon(PathUtil.getPath("icon tool/close.png"));
        closeLabel = new JLabel(closeIcon);
        closeLabel.setBounds(570, 5, 25, 25);
        getContentPane().add(closeLabel);


        // Login Button
        loginButton = ButtonUtil.getButton("Login");
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

        setBounds(0,0,WIDTH,HEIGHT);
        setUndecorated(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
