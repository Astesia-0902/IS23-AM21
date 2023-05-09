package org.am21.client.view.GUI.Interface;

import org.am21.client.view.GUI.component.BackGroundPanel;
import org.am21.client.view.GUI.utils.ButtonUtil;
import org.am21.client.view.GUI.utils.FontUtil;
import org.am21.client.view.GUI.utils.PathUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

public class WaitingRoomInterface extends JDialog {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    public final JButton leaveButton;
    public final JButton ruleButton;
    public final JButton settingButton;
    public final ImageIcon chatIcon;
    public final JButton chatButton;
    public final ImageIcon chatIconColor;
    public final ImageIcon onlineIcon;
    public final JButton onlineButton;
    public final ImageIcon onlineIconColor;
    public final ImageIcon helpIcon;
    public final ImageIcon helpIconColor;
    public final JButton helpButton;
    public final ChatDialog chatDialog;

    public RuleDialog ruleDialog;


    public WaitingRoomInterface(JFrame frame) throws Exception {
        super(frame);
        frame.setTitle("MyShelfie - Waiting Room");
        setIconImage(ImageIO.read(new File(PathUtil.getPath("Publisher material/Icon 50x50px.png"))));

        //background
        HashMap<BufferedImage, int[]> background = new HashMap<>();
//        background.put(ImageIO.read(new File(PathUtil.getPath
//                ("background/Waiting Room BG.png"))), new int[]{-100, -220,800,900});
        background.put(ImageIO.read(new File(PathUtil.getPath
                ("background/Waiting Room BG2.png"))), new int[]{-210,-315,980,1050});
        BackGroundPanel backGroundPanel = new BackGroundPanel(background);

        // Waiting Players
        JLabel waitingMessage = new JLabel("Waiting Players...");
        waitingMessage.setBorder(null);
        waitingMessage.setBounds(181,90 , 356, 108);
        waitingMessage.setForeground(new Color(237, 179, 137));
        //waitingMessage.setFont(new Font("Snap ITC", Font.PLAIN, 26));
        waitingMessage.setFont(FontUtil.getFontByName("KaushanScript-Regular-1").deriveFont(Font.PLAIN,35));
        waitingMessage.setOpaque(false);
        getContentPane().add(waitingMessage);


        // Leave Button
        leaveButton = ButtonUtil.getButton("Leave");
        leaveButton.setBounds(254, 280, 82, 33);
        getContentPane().add(leaveButton);

        // Rules Button
        ruleButton = ButtonUtil.getCommandButton("Rule");
        ruleButton.setBounds(130,95 , 50, 20);
        getContentPane().add(ruleButton);

        // Setting Button
        settingButton = ButtonUtil.getCommandButton("Settings");
        settingButton.setBounds(390,95 , 80, 20);
        getContentPane().add(settingButton);

        chatIcon = new ImageIcon(PathUtil.getPath("icon tool/chat (2).png"));
        chatIconColor = new ImageIcon(PathUtil.getPath("icon tool/chatColor.png"));
        chatButton = new JButton(chatIcon);
        chatButton.setBounds(220, 85, 35, 35);
        chatButton.setContentAreaFilled(false);
        chatButton.setBorder(null);
        chatButton.setFocusPainted(false);
        getContentPane().add(chatButton);

        onlineIcon = new ImageIcon(PathUtil.getPath("icon tool/online.png"));
        onlineIconColor = new ImageIcon(PathUtil.getPath("icon tool/onlineColor.png"));
        onlineButton = new JButton(onlineIcon);

        onlineButton.setContentAreaFilled(false);
        onlineButton.setBorder(null);
        onlineButton.setFocusPainted(false);
        onlineButton.setBounds(280, 85, 35, 35);
        getContentPane().add(onlineButton);

        helpIcon = new ImageIcon(PathUtil.getPath("icon tool/help.png"));
        helpIconColor = new ImageIcon(PathUtil.getPath("icon tool/helpColor.png"));
        helpButton = new JButton(helpIcon);
        helpButton.setBounds(340, 85, 35, 35);
        helpButton.setContentAreaFilled(false);
        helpButton.setBorder(null);
        helpButton.setFocusPainted(false);
        getContentPane().add(helpButton);



        backGroundPanel.setBorder(new MatteBorder(5, 5, 5, 5,
                new Color(139, 69, 19)));
        add(backGroundPanel);

        ruleDialog = new RuleDialog();
        chatDialog = new ChatDialog();

        setBounds(0,0,WIDTH,HEIGHT);
        setUndecorated(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
