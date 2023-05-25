package org.am21.client.view.GUI.component;

import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;
import java.awt.*;

public class EnemyPanel extends JPanel {
    public JLayeredPane enemyPane;
    public JLabel enemyPic;
    public JLabel enemyBoard;
    public ShelfPanel enemyShelf;
    public JLabel enemyScoreDynamic;

    public EnemyPanel(int posY, ImageIcon imgPic, String nickname) {
        setBounds(0, posY, PixelUtil.commonX_2 + PixelUtil.enemyShelfW, PixelUtil.enemyShelfH);
        setLayout(null);
        setOpaque(false);
        enemyPane = new JLayeredPane();
        enemyPane.setBounds(0, 0, PixelUtil.commonX_2 + PixelUtil.enemyShelfW, PixelUtil.enemyShelfH);
        enemyPane.setLayout(null);
        enemyPane.setOpaque(false);
        add(enemyPane);

        enemyPic = new JLabel(nickname);
        enemyPic.setBounds(PixelUtil.commonX_1, 0, PixelUtil.enemyW, PixelUtil.enemyH);
        enemyPic.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 255)));
        enemyPic.setIcon(imgPic);
        enemyPane.add(enemyPic, JLayeredPane.DEFAULT_LAYER);

        enemyBoard = new JLabel(nickname);
        enemyBoard.setBounds(PixelUtil.commonX_2, 0, PixelUtil.enemyShelfW, PixelUtil.enemyShelfH);
        enemyBoard.setIcon(ImageUtil.getShelfImage(PixelUtil.enemyShelfW, PixelUtil.enemyShelfH));
        enemyPane.add(enemyBoard, JLayeredPane.DEFAULT_LAYER);

        enemyScoreDynamic = new JLabel("99");
        enemyScoreDynamic.setBounds(PixelUtil.enemyScoreX, PixelUtil.enemyScoreY, PixelUtil.enemyScoreW, PixelUtil.enemyScoreH);
        enemyScoreDynamic.setFont(new Font("DejaVu Sans", Font.PLAIN, 20));
        enemyScoreDynamic.setForeground(new Color(0, 0, 0, 255));
        enemyPane.add(enemyScoreDynamic, JLayeredPane.MODAL_LAYER);

        enemyShelf = new ShelfPanel(PixelUtil.enemyGridX, PixelUtil.enemyGridY, PixelUtil.enemyCellW, PixelUtil.enemyCellH, PixelUtil.enemyItemW, PixelUtil.enemyItemH);
        enemyPane.add(enemyShelf, JLayeredPane.PALETTE_LAYER);

    }
}
