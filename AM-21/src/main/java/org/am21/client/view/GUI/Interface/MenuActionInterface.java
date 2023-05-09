package org.am21.client.view.GUI.Interface;

import org.am21.client.view.GUI.component.BackGroundPanel;
import org.am21.client.view.GUI.component.ButtonColorUI;
import org.am21.client.view.GUI.utils.FontUtil;
import org.am21.client.view.GUI.utils.PathUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

public class MenuActionInterface extends JDialog {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    public MaxSeatsDialog maxSeatsDialog;
    public ChatDialog chatDialog;

    public JButton createButton;
    public JButton joinButton;
    public JButton exitButton;

    public JButton chatButton;
    public JButton helpButton;
    public JButton onlineButton;

    public ImageIcon helpIcon;
    public ImageIcon onlineIcon;
    public ImageIcon chatIcon;
    public ImageIcon helpIconColor;
    public ImageIcon onlineIconColor;
    public ImageIcon chatIconColor;

    public Timer timer;
    public MenuActionInterface(JFrame frame) throws Exception{
        super(frame);
        frame.setTitle("MyShelfie - Menu Action");
        setIconImage(ImageIO.read(new File(PathUtil.getPath("Publisher material/Icon 50x50px.png"))));

        HashMap<BufferedImage, int[]> background = new HashMap<>();
        //background
        background.put(ImageIO.read(new File(PathUtil.getPath
                ("background/MenuActionBG.png"))), new int[]{-120, -200, 800, 800});
        BackGroundPanel backGroundPanel = new BackGroundPanel(background);


        createButton = new JButton("Create New Match");
        createButton.setForeground(new Color(139, 69, 19));
        createButton.setBackground(new Color(222, 184, 135));
        createButton.setUI(new ButtonColorUI(new Color(245, 225, 199)));
        createButton.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 250, 205),
                new Color(255, 250, 205), new Color(139, 69, 19), new Color(139, 69, 19)));
        //createButton.setFont(new Font("Tempus Sans ITC", Font.BOLD, 18));
        createButton.setFont(FontUtil.getFontByName("Leira-Lite-2").deriveFont(Font.PLAIN,20));
        createButton.setBounds(150, 110, 300, 50);
        getContentPane().add(createButton);

        joinButton = new JButton("Join Match");
        joinButton.setForeground(new Color(139, 69, 19));
        joinButton.setBackground(new Color(222, 184, 135));
        joinButton.setUI(new ButtonColorUI(new Color(245, 225, 199)));
        joinButton.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 250, 205),
                new Color(255, 250, 205), new Color(139, 69, 19), new Color(139, 69, 19)));
        //joinButton.setFont(new Font("Tempus Sans ITC", Font.BOLD, 18));
        joinButton.setFont(FontUtil.getFontByName("Leira-Lite-2").deriveFont(Font.PLAIN,20));
        joinButton.setBounds(150, 180, 300, 50);
        getContentPane().add(joinButton);

        exitButton = new JButton("Exit Game");
        exitButton.setForeground(new Color(139, 69, 19));
        exitButton.setBackground(new Color(222, 184, 135));
        exitButton.setUI(new ButtonColorUI(new Color(245, 225, 199)));
        exitButton.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 250, 205),
                new Color(255, 250, 205), new Color(139, 69, 19), new Color(139, 69, 19)));
        //exitButton.setFont(new Font("Tempus Sans ITC", Font.BOLD, 18));
        exitButton.setFont(FontUtil.getFontByName("Leira-Lite-2").deriveFont(Font.PLAIN,20));
        exitButton.setBounds(150, 250, 300, 50);
        getContentPane().add(exitButton);

        chatIcon = new ImageIcon(PathUtil.getPath("icon tool/chat (2).png"));
        chatIconColor = new ImageIcon(PathUtil.getPath("icon tool/chatColor.png"));
        chatButton = new JButton(chatIcon);
        chatButton.setBounds(450, 45, 35, 35);
        chatButton.setContentAreaFilled(false);
        chatButton.setBorder(null);
        chatButton.setFocusPainted(false);
        getContentPane().add(chatButton);

        onlineIcon = new ImageIcon(PathUtil.getPath("icon tool/online.png"));
        onlineIconColor = new ImageIcon(PathUtil.getPath("icon tool/onlineColor.png"));
        onlineButton = new JButton(onlineIcon);
        onlineButton.setBounds(490, 43, 35, 35);
        onlineButton.setContentAreaFilled(false);
        onlineButton.setBorder(null);
        onlineButton.setFocusPainted(false);
        getContentPane().add(onlineButton);

        helpIcon = new ImageIcon(PathUtil.getPath("icon tool/help.png"));
        helpIconColor = new ImageIcon(PathUtil.getPath("icon tool/helpColor.png"));
        helpButton = new JButton(helpIcon);
        helpButton.setBounds(525, 45, 35, 35);
        helpButton.setContentAreaFilled(false);
        helpButton.setBorder(null);
        helpButton.setFocusPainted(false);
        getContentPane().add(helpButton);




        //----------------------Background MENU ACTION---------------------------

        backGroundPanel.setBorder(new MatteBorder(5, 5, 5, 5,
                new Color(139, 69, 19)));
        add(backGroundPanel);

        maxSeatsDialog = new MaxSeatsDialog();
        chatDialog = new ChatDialog();

        setBounds(0,0,WIDTH,HEIGHT);
        setUndecorated(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
