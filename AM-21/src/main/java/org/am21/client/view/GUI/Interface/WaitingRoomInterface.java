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
    public final JLabel roomID;
    public final JButton leaveButton;
    public final JButton ruleButton;
    public final JButton settingButton;
    public final ImageIcon chatIcon;
    public final JButton chatButton;
    public final ImageIcon chatIconColor;
    public final ImageIcon onlineIcon;
    public final JButton onlineButton;
    public final ImageIcon onlineIconColor;

    public final MaxSeatsDialog maxSeatsDialog;
    public String minNum;
    public String maxNum;
    public int ID;
    public Timer timer;

    public JLabel numPlayer;

    public WaitingRoomInterface(JFrame frame, String numMiss, String numMax, int matchID) {
        super(frame);
        frame.setTitle("MyShelfie - Waiting Room");

        //background
        HashMap<BufferedImage, int[]> background = new HashMap<>();
        background.put(IconUtil.getBufferedImage("waitingRoomBG"), new int[]{ImageUtil.resizeX(-210),
                ImageUtil.resizeY(-315), ImageUtil.resizeX(980), ImageUtil.resizeY(1050)});
        BackGroundPanel backGroundPanel = new BackGroundPanel(background);

        ID = matchID;
        roomID = new JLabel("ID: " + ID);
        roomID.setBorder(null);
        roomID.setBounds(ImageUtil.resizeX(275), ImageUtil.resizeY(90),
                ImageUtil.resizeX(356), ImageUtil.resizeY(108));
        roomID.setForeground(new Color(237, 179, 137));
        roomID.setFont(FontUtil.getFontByName("KaushanScript-Regular-1")
                .deriveFont(Font.BOLD, ImageUtil.resizeY(20)));
        roomID.setOpaque(false);

        // Waiting Players... (x/y)
        JLabel waitingMessage = new JLabel("Waiting Players...");
        waitingMessage.setBorder(null);
        waitingMessage.setBounds(ImageUtil.resizeX(181), ImageUtil.resizeY(140),
                ImageUtil.resizeX(356), ImageUtil.resizeY(108));
        waitingMessage.setForeground(new Color(237, 179, 137));
        waitingMessage.setFont(FontUtil.getFontByName("KaushanScript-Regular-1")
                .deriveFont(Font.PLAIN, ImageUtil.resizeY(35)));
        waitingMessage.setOpaque(false);

        minNum = numMiss;
        maxNum = numMax;
        numPlayer = new JLabel("(" + minNum + "/" + maxNum + ")");
        numPlayer.setBorder(null);
        numPlayer.setBounds(ImageUtil.resizeX(420), ImageUtil.resizeY(145),
                ImageUtil.resizeX(356), ImageUtil.resizeY(108));
        numPlayer.setForeground(new Color(237, 179, 137));
        numPlayer.setFont(FontUtil.getFontByName("KaushanScript-Regular-1")
                .deriveFont(Font.PLAIN, ImageUtil.resizeY(20)));
        numPlayer.setOpaque(false);

        getContentPane().add(roomID);
        getContentPane().add(waitingMessage);
        getContentPane().add(numPlayer);

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
        onlineButton.setBounds(ImageUtil.resizeX(340), ImageUtil.resizeY(85),
                ImageUtil.resizeX(35), ImageUtil.resizeY(35));
        getContentPane().add(onlineButton);

        backGroundPanel.setBorder(new MatteBorder(ImageUtil.resizeY(5), ImageUtil.resizeX(5),
                ImageUtil.resizeY(5), ImageUtil.resizeX(5), new Color(139, 69, 19)));
        add(backGroundPanel);

        maxSeatsDialog = new MaxSeatsDialog();
        setBounds(0, 0, ImageUtil.resizeX(WIDTH), ImageUtil.resizeY(HEIGHT));
        setUndecorated(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * This method allow to reload numPlayers data
     *
     * @param minNum players in room
     * @param maxNum max number of players for the room
     */
    public void reloadPlayerNumber(String minNum, String maxNum, int matchID) {
        this.numPlayer.setText("(" + minNum + "/" + maxNum + ")");
        this.roomID.setText("ID: " + ID);
    }
}
