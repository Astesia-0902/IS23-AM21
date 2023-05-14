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

public class HelpDialog extends JDialog{
    public final JLabel closeLabel;
    public final JLabel assistMode;
    public final ImageIcon buttonIcon;
    public final ImageIcon buttonSelectedIcon;
    public final JRadioButton onButton;
    public final JRadioButton offButton;
    public final ButtonGroup group;
    public Timer timer;
    public HelpDialog(Frame frame) {
        super(frame,true);
        setSize(ImageUtil.resizeX(210), ImageUtil.resizeY(155));
        HashMap<BufferedImage, int[]> maxSeatsBG = new HashMap<>();
        BackGroundPanel helpBGPanel = new BackGroundPanel(maxSeatsBG);

        closeLabel = new JLabel(IconUtil.getIcon("close_Brown"));
        closeLabel.setBounds(ImageUtil.resizeX(180), ImageUtil.resizeY(10),
                ImageUtil.resizeX(25), ImageUtil.resizeY(25));
        add(closeLabel);

        assistMode = new JLabel("Assist Mode");
        assistMode.setOpaque(false);
        assistMode.setForeground(new Color(139, 69, 19));
        assistMode.setFont(FontUtil.getFontByName("KaushanScript-Regular-1").deriveFont(Font.PLAIN,
                ImageUtil.resizeY(16)));
        assistMode.setBounds(ImageUtil.resizeX(10), ImageUtil.resizeY(10),
                ImageUtil.resizeX(90), ImageUtil.resizeY(29));
        add(assistMode);

        buttonIcon = IconUtil.getIcon("button");
        buttonSelectedIcon = IconUtil.getIcon("buttonSelected");

        onButton = new JRadioButton("ON", buttonSelectedIcon);
        onButton.setForeground(new Color(139, 69, 19));
        onButton.setOpaque(false);
        onButton.setFocusPainted(false);
        onButton.setFont(FontUtil.getFontByName("KaushanScript-Regular-1").deriveFont(Font.PLAIN,
                ImageUtil.resizeY(14)));
        onButton.setBounds(ImageUtil.resizeX(10), ImageUtil.resizeY(45),
                ImageUtil.resizeX(100), ImageUtil.resizeY(33));
        add(onButton);

        offButton = new JRadioButton("OFF", buttonIcon);
        offButton.setForeground(new Color(139, 69, 19));
        offButton.setOpaque(false);
        offButton.setFocusPainted(false);
        offButton.setFont(FontUtil.getFontByName("KaushanScript-Regular-1").deriveFont(Font.PLAIN,
                ImageUtil.resizeY(14)));
        offButton.setBounds(ImageUtil.resizeX(10), ImageUtil.resizeY(78),
                ImageUtil.resizeX(100), ImageUtil.resizeY(33));
        add(offButton);

        group = new ButtonGroup();
        group.add(onButton);
        group.add(offButton);

        helpBGPanel.setBorder(new MatteBorder(ImageUtil.resizeY(5), ImageUtil.resizeX(5),
                ImageUtil.resizeY(5), ImageUtil.resizeX(5), new Color(139, 69, 19)));

        helpBGPanel.setBackground(new Color(222, 184, 135, 128));
        add(helpBGPanel);

        setLocationRelativeTo(this);
        setResizable(false);
        setUndecorated(true);
        setOpacity(0.9f);
    }
}
