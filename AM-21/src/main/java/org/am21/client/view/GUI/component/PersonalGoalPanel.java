package org.am21.client.view.GUI.component;

import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;
import java.awt.*;

public class PersonalGoalPanel extends JPanel {

    public JLayeredPane personalGoalPane;
    public JLabel personalGoalLabel;

    public PersonalGoalPanel(int cardNum){

        setBounds(PixelUtil.personalGoalX, PixelUtil.personalGoalY, PixelUtil.personalGoalCardW, PixelUtil.personalGoalCardH);
        setLayout(null);
        setOpaque(false);

        personalGoalPane = new JLayeredPane();
        personalGoalPane.setBounds(0,0, PixelUtil.personalGoalCardW, PixelUtil.personalGoalCardH);
        personalGoalPane.setLayout(null);
        personalGoalPane.setOpaque(false);
        add(personalGoalPane);

        personalGoalLabel = new JLabel();
        personalGoalLabel.setBounds(0,0,PixelUtil.personalGoalCardW, PixelUtil.personalGoalCardH);
        personalGoalLabel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 255)));
        personalGoalLabel.setIcon(ImageUtil.getPersonalGoalCardImage(cardNum));
        personalGoalPane.add(personalGoalLabel,JLayeredPane.DEFAULT_LAYER);
    }



}
