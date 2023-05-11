package org.am21.client.view.GUI.component;

import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;

public class EnemyPanel extends JPanel {
    public JLayeredPane enemyPane;
    public JLabel enemyPic;
    public JLabel enemyBoard;

    public EnemyPanel(int posY,ImageIcon imgPic){
        setBounds(0,posY,PixelUtil.commonX_2+PixelUtil.enemyShelfW,PixelUtil.enemyShelfH);
        setLayout(null);
        setOpaque(false);
        enemyPane = new JLayeredPane();
        enemyPane.setBounds(0,0,PixelUtil.commonX_2+PixelUtil.enemyShelfW,PixelUtil.enemyShelfH);
        enemyPane.setLayout(null);
        enemyPane.setOpaque(false);
        add(enemyPane);

        enemyPic = new JLabel();
        enemyPic.setBounds(PixelUtil.commonX_1,0,PixelUtil.enemyW,PixelUtil.enemyH);
        enemyPic.setIcon(imgPic);
        enemyPane.add(enemyPic,JLayeredPane.DEFAULT_LAYER);

        enemyBoard = new JLabel();
        enemyBoard.setBounds(PixelUtil.commonX_2,0,PixelUtil.enemyShelfW,PixelUtil.enemyShelfH);
        enemyBoard.setIcon(ImageUtil.getShelfImage(PixelUtil.enemyShelfW,PixelUtil.enemyShelfH));
        enemyPane.add(enemyBoard,JLayeredPane.DEFAULT_LAYER);

    }
}