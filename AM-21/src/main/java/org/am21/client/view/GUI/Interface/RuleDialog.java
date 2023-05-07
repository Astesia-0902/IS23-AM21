package org.am21.client.view.GUI.Interface;

import org.am21.client.view.GUI.utils.PathUtil;

import javax.swing.*;
import java.awt.*;

public class RuleDialog extends JDialog {
    public JLabel closeLabel;
    public CardLayout rules;
    public JPanel rulesPanel;
    public JLabel rule_1;
    public JLabel rule_2;
    public JLabel rule_3;
    public JLabel rule_4;
    public JLabel rule_5;
    public JLabel rule_6;
    public JButton leftButton;
    public JButton rightButton;

    public RuleDialog() {
        setModal(true);
        setSize(1200, 1000);

        ImageIcon closeIcon = new ImageIcon(PathUtil.getPath("icon tool/close_white.png"));
        closeLabel = new JLabel(closeIcon);
        closeLabel.setBounds(1120, 10, 25, 25);

        add(closeLabel);

        rule_1 = new JLabel(new ImageIcon(PathUtil.getPath("background/Rule1 1250x1250.png")));
        rule_2 = new JLabel(new ImageIcon(PathUtil.getPath("background/Rule2 1250x1250.png")));
        rule_3 = new JLabel(new ImageIcon(PathUtil.getPath("background/Rule3 1250x1250.png")));
        rule_4 = new JLabel(new ImageIcon(PathUtil.getPath("background/Rule4 1250x1250.png")));
        rule_5 = new JLabel(new ImageIcon(PathUtil.getPath("background/Rule5 1250x1250.png")));
        rule_6 = new JLabel(new ImageIcon(PathUtil.getPath("background/Rule6 1250x1250.png")));

        rules = new CardLayout();
        rulesPanel = new JPanel(rules);
        rulesPanel.add(rule_1);
        rulesPanel.add(rule_2);
        rulesPanel.add(rule_3);
        rulesPanel.add(rule_4);
        rulesPanel.add(rule_5);
        rulesPanel.add(rule_6);
        rulesPanel.setBackground(new Color(0,0,0,0));

        leftButton = new JButton(new ImageIcon(PathUtil.getPath("icon tool/left2 (2).png")));
        leftButton.setContentAreaFilled(false);
        leftButton.setBorder(null);
        leftButton.setFocusPainted(false);
        leftButton.setOpaque(false);

        rightButton = new JButton(new ImageIcon(PathUtil.getPath("icon tool/right2 (2).png")));
        rightButton.setContentAreaFilled(false);
        rightButton.setBorder(null);
        rightButton.setFocusPainted(false);
        rightButton.setOpaque(false);


        add(rulesPanel, BorderLayout.CENTER);
        add(leftButton, BorderLayout.WEST);
        add(rightButton, BorderLayout.EAST);

        setLocationRelativeTo(this);
        setResizable(false);
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
    }
}
