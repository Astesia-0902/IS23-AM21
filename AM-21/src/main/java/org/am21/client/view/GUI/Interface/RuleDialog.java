package org.am21.client.view.GUI.Interface;

import org.am21.client.view.GUI.utils.ButtonUtil;
import org.am21.client.view.GUI.utils.IconUtil;
import org.am21.client.view.GUI.utils.ImageUtil;

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
    public int countPage = 0;

    public RuleDialog() {
        setModal(true);
        setSize(ImageUtil.resizeX(1200), ImageUtil.resizeY(1000));
        closeLabel = new JLabel(IconUtil.getIcon("close_White"));
        closeLabel.setBounds(ImageUtil.resizeX(1120),ImageUtil.resizeY(10),ImageUtil.resizeX(25),ImageUtil.resizeY(25));
        add(closeLabel);

        rule_1 = new JLabel(IconUtil.getIcon("rule1"));
        rule_2 = new JLabel(IconUtil.getIcon("rule2"));
        rule_3 = new JLabel(IconUtil.getIcon("rule3"));
        rule_4 = new JLabel(IconUtil.getIcon("rule4"));
        rule_5 = new JLabel(IconUtil.getIcon("rule5"));
        rule_6 = new JLabel(IconUtil.getIcon("rule6"));

        rules = new CardLayout();
        rulesPanel = new JPanel(rules);
        rulesPanel.add(rule_1);
        rulesPanel.add(rule_2);
        rulesPanel.add(rule_3);
        rulesPanel.add(rule_4);
        rulesPanel.add(rule_5);
        rulesPanel.add(rule_6);
        rulesPanel.setBackground(new Color(0, 0, 0, 0));
        //rulesPanel.setPreferredSize(new Dimension((int) (screenSize.width * 0.8),(int) (screenSize.height * 0.8)));

        leftButton = ButtonUtil.getCommandButton();
        leftButton.setIcon(IconUtil.getIcon("left"));
        leftButton.setOpaque(false);

        rightButton = ButtonUtil.getCommandButton();
        rightButton.setIcon(IconUtil.getIcon("right"));
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