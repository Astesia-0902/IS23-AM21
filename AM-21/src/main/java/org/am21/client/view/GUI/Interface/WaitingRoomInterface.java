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

public class WaitingRoomInterface extends JDialog {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    public final JButton leaveButton;
    public final JButton ruleButton;
    public final JButton settingButton;

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
        leaveButton = new JButton("Leave");
        leaveButton.setForeground(new Color(139, 69, 19));
        leaveButton.setBackground(new Color(237, 179, 137));
        leaveButton.setUI(new ButtonColorUI(new Color(245, 225, 199)));
        leaveButton.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 250, 205),
                new Color(255, 250, 205), new Color(139, 69, 19), new Color(139, 69, 19)));
        //leaveButton.setFont(new Font("Tempus Sans ITC", Font.BOLD, 18));
        leaveButton.setFont(FontUtil.getFontByName("Leira-Lite-2").deriveFont(Font.PLAIN,20));
        leaveButton.setBounds(254, 280, 82, 33);
        getContentPane().add(leaveButton);

        // Rules Button
        ruleButton = new JButton("Rule");
        ruleButton.setContentAreaFilled(false);
        ruleButton.setBorder(null);
        ruleButton.setFocusPainted(false);
        ruleButton.setBounds(100,55 , 100, 100);
        ruleButton.setForeground(new Color(237, 179, 137));
        //ruleButton.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 26));
        ruleButton.setFont(FontUtil.getFontByName("Leira-Lite-2").deriveFont(Font.PLAIN,20));
        ruleButton.setOpaque(false);
        getContentPane().add(ruleButton);

        // Setting Button
        settingButton = new JButton("Settings");
        settingButton.setContentAreaFilled(false);
        settingButton.setBorder(null);
        settingButton.setFocusPainted(false);
        settingButton.setBounds(390,55 , 100, 100);
        settingButton.setForeground(new Color(237, 179, 137));
        //settingButton.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 26));
        settingButton.setFont(FontUtil.getFontByName("Leira-Lite-2").deriveFont(Font.PLAIN,20));
        settingButton.setOpaque(false);
        getContentPane().add(settingButton);

        backGroundPanel.setBorder(new MatteBorder(5, 5, 5, 5,
                new Color(139, 69, 19)));
        add(backGroundPanel);

        ruleDialog = new RuleDialog();

        setBounds(0,0,WIDTH,HEIGHT);
        setUndecorated(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
