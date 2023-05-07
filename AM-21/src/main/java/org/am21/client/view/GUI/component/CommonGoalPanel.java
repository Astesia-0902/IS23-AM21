package org.am21.client.view.GUI.component;

import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;

public class CommonGoalPanel extends JPanel {

    public JLayeredPane commonGoalPane;
    public JLabel commonGoalTopLabel;
    public JLabel commonGoalBottomLabel;

    public String topCardName;
    public String bottomCardName;

    public ImageIcon tokenBack;

    public CommonGoalPanel() {
        this.setBounds(PixelUtil.commonX_5, PixelUtil.commonGoalY_A, PixelUtil.goalCardW, PixelUtil.goalCardH + (PixelUtil.commonGoalY_B - PixelUtil.commonGoalY_A));
        this.setLayout(null);
        this.setOpaque(false);

        this.commonGoalPane = new JLayeredPane();
        this.commonGoalPane.setBounds(0, 0, PixelUtil.goalCardW, PixelUtil.goalCardH + (PixelUtil.commonGoalY_B - PixelUtil.commonGoalY_A));
        this.commonGoalPane.setLayout(null);
        this.commonGoalPane.setOpaque(false);
        this.add(this.commonGoalPane);

        setTopCard(this.topCardName);
        setBottomCard(this.bottomCardName);

    }


    public void setTopCard(String topCardName) {
        this.commonGoalTopLabel = new JLabel();
        this.commonGoalTopLabel.setBounds(0, 0, PixelUtil.goalCardW, PixelUtil.goalCardH);
        this.commonGoalTopLabel.setIcon(ImageUtil.getCommonGoalCardImage(topCardName));
        this.commonGoalPane.add(this.commonGoalTopLabel, JLayeredPane.DEFAULT_LAYER);
    }

    public void setBottomCard(String bottomCardName) {
        this.commonGoalBottomLabel = new JLabel();
        this.commonGoalBottomLabel.setBounds(0, PixelUtil.commonGoalY_B - PixelUtil.commonGoalY_A, PixelUtil.goalCardW, PixelUtil.goalCardH);
        this.commonGoalBottomLabel.setIcon(ImageUtil.getCommonGoalCardImage(topCardName));
        this.commonGoalPane.add(this.commonGoalBottomLabel, JLayeredPane.DEFAULT_LAYER);
    }

    public ImageIcon loadTokenImage(int value){
        return null;
    }

    public void setCard(String topCardName,String bottomCardName){
        this.topCardName=topCardName;
        this.bottomCardName=bottomCardName;
    }
}
