package org.am21.client.view.GUI.Interface;

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

        //background
        HashMap<BufferedImage, int[]> background = new HashMap<>();
        background.put(IconUtil.getBufferedImage("waitingRoomBG"), new int[]{ImageUtil.resizeX(-210),
                ImageUtil.resizeY(-315), ImageUtil.resizeX(980), ImageUtil.resizeY(1050)});
        BackGroundPanel backGroundPanel = new BackGroundPanel(background);

        // Waiting Players
        JLabel waitingMessage = new JLabel("Waiting Players...");
        waitingMessage.setBorder(null);
        waitingMessage.setBounds(ImageUtil.resizeX(181), ImageUtil.resizeY(90),
                ImageUtil.resizeX(356), ImageUtil.resizeY(108));
        waitingMessage.setForeground(new Color(237, 179, 137));
        waitingMessage.setFont(FontUtil.getFontByName("KaushanScript-Regular-1")
                .deriveFont(Font.PLAIN, ImageUtil.resizeY(35)));
        waitingMessage.setOpaque(false);
        getContentPane().add(waitingMessage);


        // Leave Button
        leaveButton = ButtonUtil.getButton("Leave");
        leaveButton.setBounds(ImageUtil.resizeX(254), ImageUtil.resizeY(280),
                ImageUtil.resizeX(82), ImageUtil.resizeY(33));
        getContentPane().add(leaveButton);

        // Rules Button
        ruleButton = ButtonUtil.getCommandButton("Rule");
        ruleButton.setBounds(ImageUtil.resizeX(130), ImageUtil.resizeY(95),
                ImageUtil.resizeX(50), ImageUtil.resizeY(20));
        getContentPane().add(ruleButton);

        // Setting Button
        settingButton = ButtonUtil.getCommandButton("Settings");
        settingButton.setBounds(ImageUtil.resizeX(390), ImageUtil.resizeY(95),
                ImageUtil.resizeX(80), ImageUtil.resizeY(20));
        getContentPane().add(settingButton);

        chatIcon = IconUtil.getIcon("chat");
        chatIconColor = IconUtil.getIcon("chatSelected");
        chatButton = ButtonUtil.getCommandButton();
        chatButton.setIcon(chatIcon);
        chatButton.setBounds(ImageUtil.resizeX(220), ImageUtil.resizeY(85),
                ImageUtil.resizeX(35), ImageUtil.resizeY(35));
        getContentPane().add(chatButton);

        onlineIcon = IconUtil.getIcon("online");
        onlineIconColor = IconUtil.getIcon("onlineSelected");
        onlineButton = ButtonUtil.getCommandButton();
        onlineButton.setIcon(onlineIcon);
        onlineButton.setBounds(ImageUtil.resizeX(280), ImageUtil.resizeY(85),
                ImageUtil.resizeX(35), ImageUtil.resizeY(35));
        getContentPane().add(onlineButton);

        helpIcon = IconUtil.getIcon("help");
        helpIconColor = IconUtil.getIcon("helpSelected");
        helpButton = ButtonUtil.getCommandButton();
        helpButton.setIcon(helpIcon);
        helpButton.setBounds(ImageUtil.resizeX(340), ImageUtil.resizeY(85),
                ImageUtil.resizeX(35), ImageUtil.resizeY(35));
        getContentPane().add(helpButton);

        backGroundPanel.setBorder(new MatteBorder(ImageUtil.resizeY(5), ImageUtil.resizeX(5),
                ImageUtil.resizeY(5), ImageUtil.resizeX(5), new Color(139, 69, 19)));
        add(backGroundPanel);

        ruleDialog = new RuleDialog();
        chatDialog = new ChatDialog();

        setBounds(0, 0, ImageUtil.resizeX(WIDTH), ImageUtil.resizeY(HEIGHT));
        setUndecorated(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
