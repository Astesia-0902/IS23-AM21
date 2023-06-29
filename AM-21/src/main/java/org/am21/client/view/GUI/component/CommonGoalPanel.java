package org.am21.client.view.GUI.component;

import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class CommonGoalPanel extends JPanel {
    public JLayeredPane commonGoalPane;
    public JLabel commonGoalTopLabel;
    public JLabel commonGoalBottomLabel;
    public ScoringTokenLabel scoreTokenEmpty;
    public ScoringTokenLabel scoreTokenTop;
    public ScoringTokenLabel scoreTokenBottom;

    public CommonGoalPanel(String topCardName, String bottomCardName) {

        setCommonGoalPane();

        setTopCard(topCardName);
        setBottomCard(bottomCardName);
    }

    /**
     * base set
     */
    public void setCommonGoalPane() {
        setBounds(PixelUtil.commonX_5, PixelUtil.commonGoalY_A, PixelUtil.commonGoalCardW, PixelUtil.commonGoalCardH + (PixelUtil.commonGoalY_B - PixelUtil.commonGoalY_A));
        setLayout(null);

        commonGoalPane = new JLayeredPane();
        commonGoalPane.setBounds(0, 0, PixelUtil.commonGoalCardW, PixelUtil.commonGoalCardH + (PixelUtil.commonGoalY_B - PixelUtil.commonGoalY_A));
        commonGoalPane.setLayout(null);
        add(commonGoalPane);
    }

    /**
     * label of first common goal
     *
     * @param topCardName name of common goal
     */
    public void setTopCard(String topCardName) {

        commonGoalTopLabel = new JLabel();
        commonGoalTopLabel.setBounds(0, 0, PixelUtil.commonGoalCardW, PixelUtil.commonGoalCardH);
        commonGoalTopLabel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 255)));
        commonGoalTopLabel.setIcon(ImageUtil.getCommonGoalCardImage(topCardName));
        commonGoalPane.add(commonGoalTopLabel, JLayeredPane.DEFAULT_LAYER);

    }

    /**
     * label of second common goal
     *
     * @param bottomCardName name of common goal
     */
    public void setBottomCard(String bottomCardName) {

        commonGoalBottomLabel = new JLabel();
        commonGoalBottomLabel.setBounds(0, PixelUtil.commonGoalY_B - PixelUtil.commonGoalY_A, PixelUtil.commonGoalCardW, PixelUtil.commonGoalCardH);
        commonGoalBottomLabel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 255)));
        commonGoalBottomLabel.setIcon(ImageUtil.getCommonGoalCardImage(bottomCardName));
        commonGoalPane.add(commonGoalBottomLabel, JLayeredPane.DEFAULT_LAYER);

    }

    /**
     * label of empty score
     *
     * @param posX index x of first or second card tokens
     * @param posY index y of first or second card tokens
     */
    public void setScoreTokenEmpty(int posX, int posY) {

        scoreTokenEmpty = new ScoringTokenLabel(ImageUtil.getBoardImage("commonGoalTokenEmpty"), PixelUtil.commonGoalTokenW, PixelUtil.commonGoalTokenH, PixelUtil.commonGoalTokenOriented, PixelUtil.commonGoalTokenRotateX, PixelUtil.commonGoalTokenRotateY);
        scoreTokenEmpty.setBounds(posX, posY, PixelUtil.commonGoalTokenBoundsW, PixelUtil.commonGoalTokenBoundsH);
        scoreTokenEmpty.setOpaque(false);
        commonGoalPane.add(scoreTokenEmpty, JLayeredPane.PALETTE_LAYER);
    }

    /**
     * score token of first common goal
     *
     * @param value value of token
     */
    public void setScoreTokenTop(int value) {
        if (value == 0)
            setScoreTokenEmpty(PixelUtil.commonGoalTokenX, PixelUtil.commonGoalTopTokenY);
        else {
            scoreTokenTop = new ScoringTokenLabel(ImageUtil.getScoreTokenImage(value), PixelUtil.commonGoalTokenW, PixelUtil.commonGoalTokenH, PixelUtil.commonGoalTokenOriented, PixelUtil.commonGoalTokenRotateX, PixelUtil.commonGoalTokenRotateY);
            scoreTokenTop.setBounds(PixelUtil.commonGoalTokenX, PixelUtil.commonGoalTopTokenY, PixelUtil.commonGoalTokenBoundsW, PixelUtil.commonGoalTokenBoundsH);
            scoreTokenTop.setOpaque(false);
            commonGoalPane.add(scoreTokenTop, JLayeredPane.MODAL_LAYER);
        }

    }

    /**
     * score token of second common goal
     *
     * @param value value of token
     */
    public void setScoreTokenBottom(int value) {
        if (value == 0)
            setScoreTokenEmpty(PixelUtil.commonGoalTokenX, PixelUtil.commonGoalBottomTokenY);
        else {
            scoreTokenBottom = new ScoringTokenLabel(ImageUtil.getScoreTokenImage(value), PixelUtil.commonGoalTokenW, PixelUtil.commonGoalTokenH, PixelUtil.commonGoalTokenOriented, PixelUtil.commonGoalTokenRotateX, PixelUtil.commonGoalTokenRotateY);
            scoreTokenBottom.setBounds(PixelUtil.commonGoalTokenX, PixelUtil.commonGoalBottomTokenY, PixelUtil.commonGoalTokenBoundsW, PixelUtil.commonGoalTokenBoundsH);
            scoreTokenBottom.setOpaque(false);
            commonGoalPane.add(scoreTokenBottom, JLayeredPane.MODAL_LAYER);
        }

    }

    /**
     * refreshing tokens of both common goal
     *
     * @param topValue    first common goal token
     * @param bottomValue second common goal token
     */
    public void refreshScoringTokens(int topValue, int bottomValue) {
        commonGoalPane.remove(scoreTokenTop);
        commonGoalPane.remove(scoreTokenBottom);

        setScoreToken(topValue, bottomValue);

        SwingUtilities.invokeLater(() -> {
            revalidate();
            repaint();
        });
    }

    /**
     * put a new score token of both common goal
     *
     * @param topValue    first common goal token
     * @param bottomValue second common goal token
     */
    public void setScoreToken(int topValue, int bottomValue) {
        setScoreTokenTop(topValue);
        setScoreTokenBottom(bottomValue);
    }

}
