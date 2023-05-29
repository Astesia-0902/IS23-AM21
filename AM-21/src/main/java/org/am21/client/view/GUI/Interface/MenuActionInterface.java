package org.am21.client.view.GUI.Interface;

import org.am21.client.view.ClientView;
import org.am21.client.view.GUI.component.BackGroundPanel;
import org.am21.client.view.GUI.utils.ButtonUtil;
import org.am21.client.view.GUI.utils.FontUtil;
import org.am21.client.view.GUI.utils.IconUtil;
import org.am21.client.view.GUI.utils.ImageUtil;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class MenuActionInterface extends JDialog {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    public MaxSeatsDialog maxSeatsDialog;
    public JButton createButton;
    public JButton joinButton;
    public JButton exitButton;

    public JButton chatButton;
    public JButton onlineButton;

    public ImageIcon helpIcon;
    public ImageIcon onlineIcon;
    public ImageIcon chatIcon;
    public ImageIcon helpIconColor;
    public ImageIcon onlineIconColor;
    public ImageIcon chatIconColor;

    public Timer timer;


    public MenuActionInterface(JFrame frame, String user) {
        super(frame);
        frame.setTitle("MyShelfie - Menu Action");
        HashMap<BufferedImage, int[]> background = new HashMap<>();
        //background
        background.put(IconUtil.getBufferedImage("menuActionBG"), new int[]{ImageUtil.resizeX(-120),
                ImageUtil.resizeY(-200), ImageUtil.resizeX(800), ImageUtil.resizeY(800)});
        BackGroundPanel backGroundPanel = new BackGroundPanel(background);

        JLabel username = new JLabel("Welcome, " + user);
        username.setBorder(null);
        username.setBounds(ImageUtil.resizeX(50), ImageUtil.resizeY(8),
                ImageUtil.resizeX(356), ImageUtil.resizeY(108));
        username.setForeground(new Color(237, 179, 137));
        username.setFont(FontUtil.getFontByName("HongLeiXingShuJianTi-2")
                .deriveFont(Font.PLAIN, ImageUtil.resizeY(20)));
        username.setOpaque(false);
        getContentPane().add(username);

        createButton = ButtonUtil.getButton("Create New Match");
        createButton.setBounds(ImageUtil.resizeX(150), ImageUtil.resizeY(110),
                ImageUtil.resizeX(300), ImageUtil.resizeY(50));
        getContentPane().add(createButton);

        joinButton = ButtonUtil.getButton("Join Match");
        joinButton.setEnabled(ClientView.matchList != null);
        joinButton.setBounds(ImageUtil.resizeX(150), ImageUtil.resizeY(180),
                ImageUtil.resizeX(300), ImageUtil.resizeY(50));
        getContentPane().add(joinButton);

        exitButton = ButtonUtil.getButton("Exit Game");
        exitButton.setBounds(ImageUtil.resizeX(150), ImageUtil.resizeY(250),
                ImageUtil.resizeX(300), ImageUtil.resizeY(50));
        getContentPane().add(exitButton);

        chatIcon = IconUtil.getIcon("chat");
        chatIconColor = IconUtil.getIcon("chatSelected");
        chatButton = ButtonUtil.getCommandButton();
        chatButton.setIcon(chatIcon);
        chatButton.setBounds(ImageUtil.resizeX(525), ImageUtil.resizeY(45),
                ImageUtil.resizeX(35), ImageUtil.resizeY(35));
        getContentPane().add(chatButton);

        onlineIcon = IconUtil.getIcon("online");
        onlineIconColor = IconUtil.getIcon("onlineSelected");
        onlineButton = ButtonUtil.getCommandButton();
        onlineButton.setIcon(onlineIcon);
        onlineButton.setBounds(ImageUtil.resizeX(490), ImageUtil.resizeY(43),
                ImageUtil.resizeX(35), ImageUtil.resizeY(35));
        getContentPane().add(onlineButton);

        //----------------------Background MENU ACTION---------------------------

        backGroundPanel.setBorder(new MatteBorder(ImageUtil.resizeY(5), ImageUtil.resizeX(5),
                ImageUtil.resizeY(5), ImageUtil.resizeX(5), new Color(139, 69, 19)));
        add(backGroundPanel);

        maxSeatsDialog = new MaxSeatsDialog();

        setBounds(0, 0, ImageUtil.resizeX(WIDTH), ImageUtil.resizeY(HEIGHT));
        setUndecorated(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        //setOpacity(0.0f);
    }
}
