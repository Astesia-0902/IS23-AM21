package org.am21.client.view.GUI.Interface;

import org.am21.client.view.GUI.component.BackGroundPanel;
import org.am21.client.view.GUI.utils.PathUtil;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class MaxSeatsDialog extends JDialog {
    public JLabel maxPlayersLabel;
    public JLabel closeLabel;
    public JRadioButton playerButton_2;
    public JRadioButton playerButton_3;
    public JRadioButton playerButton_4;
    public ImageIcon buttonIcon;
    public ImageIcon buttonSelectedIcon;
    public ImageIcon closeIcon;

    public MaxSeatsDialog() {
        //----------------------Max Seats-------------------------------
        setModal(true);
        setSize(210,155);
        HashMap<BufferedImage, int[]> maxSeatsBG = new HashMap<>();
        BackGroundPanel maxSeatsBGPanel = new BackGroundPanel(maxSeatsBG);

        closeIcon = new ImageIcon(PathUtil.getPath("icon tool/close (1) 20x20.png"));
        closeLabel = new JLabel(closeIcon);
        closeLabel.setBounds(180, 10, 25, 25);
        add(closeLabel);

        maxPlayersLabel = new JLabel("Max players");
        maxPlayersLabel.setOpaque(false);
        maxPlayersLabel.setForeground(new Color(139, 69, 19));
        maxPlayersLabel.setFont(new Font("Tempus Sans ITC", Font.BOLD, 16));
        maxPlayersLabel.setBounds(10, 10, 90, 29);
        add(maxPlayersLabel);

        buttonIcon = new ImageIcon(PathUtil.getPath("icon tool/button1.png"));
        buttonSelectedIcon = new ImageIcon(PathUtil.getPath("icon tool/button2 (2).png"));

        playerButton_2 = new JRadioButton("2",buttonIcon);
        playerButton_2.setForeground(new Color(139, 69, 19));
        playerButton_2.setOpaque(false);
        playerButton_2.setFocusPainted(false);
        playerButton_2.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
        playerButton_2.setBounds(10, 45, 50, 33);
        add(playerButton_2);

        playerButton_3 = new JRadioButton("3",buttonIcon);
        playerButton_3.setForeground(new Color(139, 69, 19));
        playerButton_3.setOpaque(false);
        playerButton_3.setFocusPainted(false);
        playerButton_3.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
        playerButton_3.setBounds(10, 78, 50, 33);
        add(playerButton_3);

        playerButton_4 = new JRadioButton("4",buttonIcon);
        playerButton_4.setForeground(new Color(139, 69, 19));
        playerButton_4.setOpaque(false);
        playerButton_4.setFocusPainted(false);
        playerButton_4.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
        playerButton_4.setBounds(10, 111, 50, 33);
        add(playerButton_4);


        ButtonGroup group = new ButtonGroup();
        group.add(playerButton_2);
        group.add(playerButton_3);
        group.add(playerButton_4);


        //-----------------Background MAX SEATS-------------------------
        maxSeatsBGPanel.setBorder(new MatteBorder(5, 5, 5, 5,
                new Color(139, 69, 19)));

        maxSeatsBGPanel.setBackground(new Color(222, 184, 135,128));
        add(maxSeatsBGPanel);

        setLocationRelativeTo(this);
        setResizable(false);
        setUndecorated(true);
    }
}
