package org.am21.client.view.GUI.Interface;

import org.am21.client.view.GUI.component.BackGroundPanel;
import org.am21.client.view.GUI.utils.FontUtil;
import org.am21.client.view.GUI.utils.IconUtil;
import org.am21.client.view.GUI.utils.ImageUtil;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class MaxSeatsDialog extends JDialog {
    public JLabel maxPlayersLabel;
    public JLabel closeLabel;
    public ButtonGroup group;
    public JRadioButton playerButton_2;
    public JRadioButton playerButton_3;
    public JRadioButton playerButton_4;
    public ImageIcon buttonIcon;
    public ImageIcon buttonSelectedIcon;

    public MaxSeatsDialog() {
        //----------------------Max Seats-------------------------------
        setModal(true);
        setSize(ImageUtil.resizeX(210), ImageUtil.resizeY(155));
        HashMap<BufferedImage, int[]> maxSeatsBG = new HashMap<>();
        BackGroundPanel maxSeatsBGPanel = new BackGroundPanel(maxSeatsBG);

        closeLabel = new JLabel(IconUtil.getIcon("close_Brown"));
        closeLabel.setBounds(ImageUtil.resizeX(180), ImageUtil.resizeY(10),
                ImageUtil.resizeX(25), ImageUtil.resizeY(25));
        add(closeLabel);

        maxPlayersLabel = new JLabel("Max players");
        maxPlayersLabel.setOpaque(false);
        maxPlayersLabel.setForeground(new Color(139, 69, 19));
        maxPlayersLabel.setFont(FontUtil.getFontByName("KaushanScript-Regular-1").deriveFont(Font.PLAIN,
                ImageUtil.resizeY(16)));
        maxPlayersLabel.setBounds(ImageUtil.resizeX(10), ImageUtil.resizeY(10),
                ImageUtil.resizeX(90), ImageUtil.resizeY(29));
        add(maxPlayersLabel);

        buttonIcon = IconUtil.getIcon("button");
        buttonSelectedIcon = IconUtil.getIcon("buttonSelected");

        playerButton_2 = new JRadioButton("2", buttonIcon);
        playerButton_2.setForeground(new Color(139, 69, 19));
        playerButton_2.setOpaque(false);
        playerButton_2.setFocusPainted(false);
        playerButton_2.setFont(FontUtil.getFontByName("KaushanScript-Regular-1").deriveFont(Font.PLAIN,
                ImageUtil.resizeY(14)));
        playerButton_2.setBounds(ImageUtil.resizeX(10), ImageUtil.resizeY(45),
                ImageUtil.resizeX(50), ImageUtil.resizeY(33));
        add(playerButton_2);

        playerButton_3 = new JRadioButton("3", buttonIcon);
        playerButton_3.setForeground(new Color(139, 69, 19));
        playerButton_3.setOpaque(false);
        playerButton_3.setFocusPainted(false);
        playerButton_3.setFont(FontUtil.getFontByName("KaushanScript-Regular-1").deriveFont(Font.PLAIN,
                ImageUtil.resizeY(14)));
        playerButton_3.setBounds(ImageUtil.resizeX(10), ImageUtil.resizeY(78),
                ImageUtil.resizeX(50), ImageUtil.resizeY(33));
        add(playerButton_3);

        playerButton_4 = new JRadioButton("4", buttonIcon);
        playerButton_4.setForeground(new Color(139, 69, 19));
        playerButton_4.setOpaque(false);
        playerButton_4.setFocusPainted(false);
        playerButton_4.setFont(FontUtil.getFontByName("KaushanScript-Regular-1").deriveFont(Font.PLAIN,
                ImageUtil.resizeY(14)));
        playerButton_4.setBounds(ImageUtil.resizeX(10), ImageUtil.resizeY(111),
                ImageUtil.resizeX(50), ImageUtil.resizeY(33));
        add(playerButton_4);


        group = new ButtonGroup();
        group.add(playerButton_2);
        group.add(playerButton_3);
        group.add(playerButton_4);


        //-----------------Background MAX SEATS-------------------------
        maxSeatsBGPanel.setBorder(new MatteBorder(ImageUtil.resizeY(5), ImageUtil.resizeX(5),
                ImageUtil.resizeY(5), ImageUtil.resizeX(5), new Color(139, 69, 19)));

        maxSeatsBGPanel.setBackground(new Color(222, 184, 135, 128));
        add(maxSeatsBGPanel);

        setLocationRelativeTo(this);
        setResizable(false);
        setUndecorated(true);
        setOpacity(0.9f);
    }
}
