package org.am21.client.view.GUI.component;

import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;
import java.awt.*;


public class CommonGoalPanel extends JPanel {
    public JLayeredPane commonGoalPane;
    public JLabel commonGoalTopLabel;
    public JLabel commonGoalBottomLabel;

    public ScoringTokenLabel scoreTokenEmpty;
    public ScoringTokenLabel scoreTokenTop;
    public ScoringTokenLabel scoreTokenBottom;

    public CommonGoalPanel(String topCardName,String bottomCardName) {

        setBounds(PixelUtil.commonX_5, PixelUtil.commonGoalY_A, PixelUtil.commonGoalCardW, PixelUtil.commonGoalCardH + (PixelUtil.commonGoalY_B - PixelUtil.commonGoalY_A));
        setLayout(null);

        commonGoalPane = new JLayeredPane();
        commonGoalPane.setBounds(0, 0, PixelUtil.commonGoalCardW, PixelUtil.commonGoalCardH + (PixelUtil.commonGoalY_B - PixelUtil.commonGoalY_A));
        commonGoalPane.setLayout(null);
        add(commonGoalPane);

        setTopCard(topCardName);
        setBottomCard(bottomCardName);
    }


    public void setTopCard(String topCardName) {

        commonGoalTopLabel = new JLabel();
        commonGoalTopLabel.setBounds(0, 0, PixelUtil.commonGoalCardW, PixelUtil.commonGoalCardH);
        commonGoalTopLabel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 255)));
        commonGoalTopLabel.setIcon(ImageUtil.getCommonGoalCardImage(topCardName));
        commonGoalPane.add(commonGoalTopLabel, JLayeredPane.DEFAULT_LAYER);
        setScoreTokenEmpty(PixelUtil.commonGoalTokenX,PixelUtil.commonGoalTopTokenY);
    }

    public void setBottomCard(String bottomCardName) {

        commonGoalBottomLabel = new JLabel();
        commonGoalBottomLabel.setBounds(0, PixelUtil.commonGoalY_B - PixelUtil.commonGoalY_A, PixelUtil.commonGoalCardW, PixelUtil.commonGoalCardH);
        commonGoalBottomLabel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 255)));
        commonGoalBottomLabel.setIcon(ImageUtil.getCommonGoalCardImage(bottomCardName));
        commonGoalPane.add(commonGoalBottomLabel, JLayeredPane.DEFAULT_LAYER);
        setScoreTokenEmpty(PixelUtil.commonGoalTokenX,PixelUtil.commonGoalBottomTokenY);
    }

    public void setScoreTokenEmpty(int posX,int posY){

        scoreTokenEmpty = new ScoringTokenLabel(ImageUtil.getBoardImage("commonGoalTokenEmpty"),PixelUtil.commonGoalTokenW,PixelUtil.commonGoalTokenH,PixelUtil.commonGoalTokenOriented,PixelUtil.commonGoalTokenRotateX,PixelUtil.commonGoalTokenRotateY);
        scoreTokenEmpty.setBounds(posX,posY,PixelUtil.commonGoalTokenBoundsW,PixelUtil.commonGoalTokenBoundsH);
         scoreTokenEmpty.setOpaque(false);
       // scoreTokenEmpty.setBackground(new Color(0, 0, 0, 0));
        commonGoalPane.add(scoreTokenEmpty,JLayeredPane.PALETTE_LAYER);
    }

    public void setScoreTokenTop(int value){
        scoreTokenTop = new ScoringTokenLabel(ImageUtil.getScoreTokenImage(value),PixelUtil.commonGoalTokenW,PixelUtil.commonGoalTokenH,PixelUtil.commonGoalTokenOriented,PixelUtil.commonGoalTokenRotateX,PixelUtil.commonGoalTokenRotateY);
        scoreTokenTop.setBounds(PixelUtil.commonGoalTokenX,PixelUtil.commonGoalTopTokenY,PixelUtil.commonGoalTokenBoundsW,PixelUtil.commonGoalTokenBoundsH);
        scoreTokenTop.setOpaque(false);
        commonGoalPane.add(scoreTokenTop,JLayeredPane.MODAL_LAYER);
    }

    public void setScoreTokenBottom(int value){
        scoreTokenBottom = new ScoringTokenLabel(ImageUtil.getScoreTokenImage(value),PixelUtil.commonGoalTokenW,PixelUtil.commonGoalTokenH,PixelUtil.commonGoalTokenOriented,PixelUtil.commonGoalTokenRotateX,PixelUtil.commonGoalTokenRotateY);
        scoreTokenBottom.setBounds(PixelUtil.commonGoalTokenX,PixelUtil.commonGoalBottomTokenY,PixelUtil.commonGoalTokenBoundsW,PixelUtil.commonGoalTokenBoundsH);
         scoreTokenBottom.setOpaque(false);
        commonGoalPane.add(scoreTokenBottom,JLayeredPane.MODAL_LAYER);
    }

    public void getScoreTokenTop(){
        commonGoalPane.remove(scoreTokenTop);
    }

    public void getScoreTokenBottom(){
        commonGoalPane.remove(scoreTokenBottom);
    }

}
