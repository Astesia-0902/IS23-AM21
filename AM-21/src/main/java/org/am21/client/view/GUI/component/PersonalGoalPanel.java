package org.am21.client.view.GUI.component;

import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;

public class PersonalGoalPanel extends JPanel {

    public JLayeredPane personalGoalPane;
    public JLabel personalGoalLabel;
    private String cardName;

    public PersonalGoalPanel(){
        this.setBounds(PixelUtil.personalGoalX, PixelUtil.personalGoalY, PixelUtil.goalCardH, PixelUtil.goalCardW);
        this.setLayout(null);
        this.setOpaque(false);

        this.personalGoalPane = new JLayeredPane();
        this.personalGoalPane.setBounds(0,0, PixelUtil.goalCardH, PixelUtil.goalCardW);
        this.personalGoalPane.setLayout(null);
        this.personalGoalPane.setOpaque(false);
        this.add(this.personalGoalPane);

        this.personalGoalLabel = new JLabel();
        this.personalGoalLabel.setBounds(0,0,PixelUtil.goalCardH, PixelUtil.goalCardW);
        this.personalGoalLabel.setIcon(ImageUtil.getPersonalGoalCardImage(this.cardName));
        this.personalGoalPane.add(this.personalGoalLabel,JLayeredPane.DEFAULT_LAYER);
    }


    public void setCard(String cardName){
        this.cardName = cardName;
    }
}
