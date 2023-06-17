package org.am21.client.view.GUI.component;

import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnemyPanel extends JPanel {
    public JLayeredPane enemyPane;
    public JLabel enemyPic;
    public JLabel enemyBoard;
    public ShelfPanel enemyShelf;
    public JLabel enemyScoreDynamic;
    public JLabel enemyName;
    public ChairManLabel chairManLabel;
    public Timer waitTimer;

    public EnemyPanel(int posY, ImageIcon imgPic, String nickname, String chairMan, String[][] virtualShelf) {

        setEnemyPane(posY);

        setEnemyBackGround(imgPic, nickname);

        setEnemyShelf(virtualShelf);

        setEnemyChair(chairMan, nickname);

        setFlashingTimer();

    }

    /**
     * base set
     *
     * @param posY pane position Y of enemy
     */

    public void setEnemyPane(int posY) {
        setBounds(0, posY, PixelUtil.commonX_2 + PixelUtil.enemyShelfW+ PixelUtil.enemyNameW, PixelUtil.enemyShelfH);
        setLayout(null);
        setOpaque(false);
        enemyPane = new JLayeredPane();
        enemyPane.setBounds(0, 0, PixelUtil.commonX_2 + PixelUtil.enemyShelfW+ PixelUtil.enemyNameW, PixelUtil.enemyShelfH);
        enemyPane.setLayout(null);
        enemyPane.setOpaque(false);
        add(enemyPane);
    }

    /**
     * base set
     *
     * @param imgPic   pic of profile
     * @param nickname name of enemy
     */
    public void setEnemyBackGround(ImageIcon imgPic, String nickname) {
        enemyPic = new JLabel(nickname);
        enemyPic.setBounds(PixelUtil.commonX_1, 0, PixelUtil.enemyW, PixelUtil.enemyH);
        enemyPic.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 255)));
        enemyPic.setIcon(imgPic);
        enemyPane.add(enemyPic, JLayeredPane.DEFAULT_LAYER);

        enemyBoard = new JLabel(nickname);
        enemyBoard.setBounds(PixelUtil.commonX_2, 0, PixelUtil.enemyShelfW, PixelUtil.enemyShelfH);
        enemyBoard.setIcon(ImageUtil.getShelfImage(PixelUtil.enemyShelfW, PixelUtil.enemyShelfH));
        enemyPane.add(enemyBoard, JLayeredPane.DEFAULT_LAYER);

        enemyScoreDynamic = new JLabel();
        enemyScoreDynamic.setBounds(PixelUtil.enemyScoreX, PixelUtil.enemyScoreY, PixelUtil.enemyScoreW, PixelUtil.enemyScoreH);
        enemyScoreDynamic.setFont(new Font("DejaVu Sans", Font.PLAIN, 20));
        enemyScoreDynamic.setForeground(new Color(0, 0, 0, 255));
        enemyPane.add(enemyScoreDynamic, JLayeredPane.MODAL_LAYER);

        enemyName = new JLabel(nickname);
        enemyName.setBounds(PixelUtil.commonX_2+PixelUtil.enemyShelfW, 0, PixelUtil.enemyNameW, PixelUtil.enemyShelfH);
        enemyName.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
        enemyName.setForeground(new Color(4, 30, 134, 230));
        enemyPane.add(enemyName, JLayeredPane.POPUP_LAYER);
    }

    /**
     * shelf of enemy
     *
     * @param virtualShelf virtualShelf of enemy
     */

    public void setEnemyShelf(String[][] virtualShelf) {
        enemyShelf = new ShelfPanel(PixelUtil.enemyGridX, PixelUtil.enemyGridY, PixelUtil.enemyCellW, PixelUtil.enemyCellH, PixelUtil.enemyItemW, PixelUtil.enemyItemH);
        enemyShelf.refreshShelf(virtualShelf);
        enemyPane.add(enemyShelf, JLayeredPane.PALETTE_LAYER);

    }

    /**
     * chairMan set, if he does, set
     *
     * @param chairMan name of chairMan
     * @param nickname name of enemy
     */
    public void setEnemyChair(String chairMan, String nickname) {
        if (chairMan.equals(nickname)) {
            chairManLabel = new ChairManLabel(false);
            enemyPane.add(chairManLabel, JLayeredPane.MODAL_LAYER);
        }
    }

    /**
     * action border flashing
     */
    public void setFlashingTimer() {

        Border originalBorder = enemyPic.getBorder();
        Border flashingBorder = new LineBorder(Color.GREEN);

        waitTimer = new Timer(350, new ActionListener() {
            private boolean isFlashing = false;

            public void actionPerformed(ActionEvent e) {
                if (isFlashing) {
                    enemyPic.setBorder(originalBorder);
                } else {
                    enemyPic.setBorder(flashingBorder);
                }
                isFlashing = !isFlashing;
            }
        });
        waitTimer.setRepeats(true);
    }

    /**
     * refresh enemy Shelf
     *
     * @param shelfStatus virtual shelf
     */
    public void refreshEnemyShelf(String[][] shelfStatus) {
        enemyShelf.refreshShelf(shelfStatus);
    }

    /**
     * refresh enemy score
     *
     * @param score virtual commonGoal score
     */
    public void refreshEnemyScores(int score) {
        enemyScoreDynamic.setText(String.valueOf(score));
    }

    /**
     * restore border color from flashing action
     */
    public void setStatusBorder() {
        enemyPic.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 255)));
    }
}


