package org.am21.client.view.GUI.component;

import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;


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
        //setOpaque(false);

        commonGoalPane = new JLayeredPane();
        commonGoalPane.setBounds(0, 0, PixelUtil.commonGoalCardW, PixelUtil.commonGoalCardH + (PixelUtil.commonGoalY_B - PixelUtil.commonGoalY_A));
        commonGoalPane.setLayout(null);
        //commonGoalPane.setOpaque(false);
        add(commonGoalPane);


        setTopCard(topCardName);
        //setTopBeginToken(value);
        setBottomCard(bottomCardName);
        //setBottomBeginToken(value);
    }


    public void setTopCard(String topCardName) {

        commonGoalTopLabel = new JLabel();
        commonGoalTopLabel.setBounds(0, 0, PixelUtil.commonGoalCardW, PixelUtil.commonGoalCardH);
        commonGoalTopLabel.setIcon(ImageUtil.getCommonGoalCardImage(topCardName));
        commonGoalPane.add(commonGoalTopLabel, JLayeredPane.DEFAULT_LAYER);
        setScoreTokenEmpty(PixelUtil.commonGoalTokenX,PixelUtil.commonGoalTopTokenY);
    }

    public void setBottomCard(String bottomCardName) {

        commonGoalBottomLabel = new JLabel();
        commonGoalBottomLabel.setBounds(0, PixelUtil.commonGoalY_B - PixelUtil.commonGoalY_A, PixelUtil.commonGoalCardW, PixelUtil.commonGoalCardH);
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
        //scoreTokenTop.setBackground(new Color(0, 0, 0, 0));
        scoreTokenTop.setOpaque(false);
        commonGoalPane.add(scoreTokenTop,JLayeredPane.MODAL_LAYER);
    }

    public void setScoreTokenBottom(int value){
        scoreTokenBottom = new ScoringTokenLabel(ImageUtil.getScoreTokenImage(value),PixelUtil.commonGoalTokenW,PixelUtil.commonGoalTokenH,PixelUtil.commonGoalTokenOriented,PixelUtil.commonGoalTokenRotateX,PixelUtil.commonGoalTokenRotateY);
        scoreTokenBottom.setBounds(PixelUtil.commonGoalTokenX,PixelUtil.commonGoalBottomTokenY,PixelUtil.commonGoalTokenBoundsW,PixelUtil.commonGoalTokenBoundsH);
        //scoreTokenBottom.setBackground(new Color(0, 0, 0, 0));
        scoreTokenBottom.setOpaque(false);
        commonGoalPane.add(scoreTokenBottom,JLayeredPane.MODAL_LAYER);
    }

    public void getScoreTokenTop(){
        commonGoalPane.remove(scoreTokenTop);
    }

    public void getScoreTokenBottom(){
        commonGoalPane.remove(scoreTokenBottom);
    }
    // TODO: list of image ? or HashMap with value ?
    /*public List<ScoringTokenLabel> setTopBeginToken(HashMap<Integer,ImageIcon[]> scoringTokens, int posX, int posY){

        ScoringTokenLabel score = new ScoringTokenLabel(ImageUtil.getScoreTokenImage(value),PixelUtil.commonGoalTokenHW,PixelUtil.commonGoalTokenOriented);
        score.setBounds(PixelUtil.commonGoalTokenX,PixelUtil.commonGoalTopTokenY,PixelUtil.commonGoalTokenBounds,PixelUtil.commonGoalTokenBounds);
        score.setBackground(new Color(0, 0, 0, 0));
        scoreTokenListTop.add(score);
        return scoreTokenListTop;
    }*/

}
