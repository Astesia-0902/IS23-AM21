package org.am21.client.view.GUI.Interface;

import org.am21.client.view.GUI.component.BackGroundPanel;
import org.am21.client.view.GUI.utils.ButtonUtil;
import org.am21.client.view.GUI.utils.IconUtil;
import org.am21.client.view.GUI.utils.ImageUtil;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class LoginInterface extends JDialog {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    public JButton loginButton;
    public JTextField nicknameField;
    public JLabel minusLabel;
    public JLabel closeLabel;

    /**
     * Constructor
     *
     * @param frame
     */
    public LoginInterface(JFrame frame) {
        super(frame);
        frame.setTitle("MyShelfie - Login");
        HashMap<BufferedImage, int[]> background = new HashMap<>();
        //background

        background.put(IconUtil.getBufferedImage("loginBG"), new int[]{ImageUtil.resizeX(-80),
                ImageUtil.resizeY(-231), ImageUtil.resizeX(800), ImageUtil.resizeY(800)});
        BackGroundPanel backGroundPanel = new BackGroundPanel(background);

        // Icon
        minusLabel = new JLabel(IconUtil.getIcon("minus"));
        minusLabel.setBounds(ImageUtil.resizeX(541), ImageUtil.resizeY(6),
                ImageUtil.resizeX(25), ImageUtil.resizeY(25));
        getContentPane().add(minusLabel);

        closeLabel = new JLabel(IconUtil.getIcon("close"));
        closeLabel.setBounds(ImageUtil.resizeX(571), ImageUtil.resizeY(6),
                ImageUtil.resizeX(25), ImageUtil.resizeY(25));
        getContentPane().add(closeLabel);


        // Login Button
        loginButton = ButtonUtil.getButton("Login");
        loginButton.setBounds(ImageUtil.resizeX(254), ImageUtil.resizeY(320),
                ImageUtil.resizeX(82), ImageUtil.resizeY(33));
        loginButton.setEnabled(false);
        getContentPane().add(loginButton);

        // Nickname Field
        ImageIcon nicknametIcon = IconUtil.getIcon("user");
        nicknameField = new JTextField(15) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                nicknametIcon.paintIcon(nicknameField, g, ImageUtil.resizeX(5), ImageUtil.resizeY(5));
            }
        };
        nicknameField.setForeground(new Color(255, 255, 240));
        nicknameField.setFont(new Font("Serif", Font.BOLD, ImageUtil.resizeY(23)));
        nicknameField.setBackground(new Color(222, 184, 135));
        nicknameField.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 250, 205),
                new Color(255, 250, 205), new Color(139, 69, 19), new Color(139, 69, 19)),
                new EmptyBorder(0, ImageUtil.resizeX(50), 0, 0)));
        nicknameField.setBounds(ImageUtil.resizeX(135), ImageUtil.resizeY(255),
                ImageUtil.resizeX(331), ImageUtil.resizeY(46));
        getContentPane().add(nicknameField);

        backGroundPanel.setBorder(new MatteBorder(ImageUtil.resizeY(5), ImageUtil.resizeX(5),
                ImageUtil.resizeY(5), ImageUtil.resizeX(5),
                new Color(139, 69, 19)));
        add(backGroundPanel);

        setBounds(0, 0, ImageUtil.resizeX(WIDTH), ImageUtil.resizeY(HEIGHT));
        setUndecorated(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
