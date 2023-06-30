package org.am21.client.view.GUI.component;

import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * MyHandBoardPanel is a class that extends JPanel
 * and is used to show the hand of the player
 */
public class MyHandBoardPanel extends JPanel {
    public final int handMax = 3;
    public JLayeredPane[] handGrid = new JLayeredPane[handMax];
    public JLabel[] myHandItem;

    public MyHandBoardPanel() {

        setMyHandBoardPanel();

    }

    /**
     * base set
     */
    public void setMyHandBoardPanel() {

        setBounds(PixelUtil.commonX_4, PixelUtil.commonY_4, PixelUtil.handBoardW, PixelUtil.handBoardH);
        setLayout(null);
        setOpaque(false);

        for (int i = 0; i < handMax; i++) {
            handGrid[i] = new JLayeredPane();
            handGrid[i].setBounds(i * ((PixelUtil.handBoardW) / 3), 0, (PixelUtil.handBoardW) / 3, PixelUtil.handBoardH);
            handGrid[i].setLayout(null);
            add(handGrid[i]);
        }
    }

    /**
     * refreshing the board view
     *
     * @param myItem list of item
     */
    public void refreshItem(List<String> myItem) {

        for (JLayeredPane pane : handGrid) {
            pane.removeAll();
        }

        myHandItem = new JLabel[myItem.size()];

        for (int i = 0; i < myItem.size(); i++) {

            myHandItem[i] = new JLabel();
            myHandItem[i].setIcon(ImageUtil.getItemImage(myItem.get(i), PixelUtil.gameBoardItemW, PixelUtil.gameBoardItemH));
            myHandItem[i].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 255)));
            myHandItem[i].setLocation(PixelUtil.myHandPanelX, PixelUtil.myHandPanelY);
            myHandItem[i].setSize(PixelUtil.gameBoardItemW, PixelUtil.gameBoardItemH);

            handGrid[i].add(myHandItem[i], JLayeredPane.PALETTE_LAYER);

        }

    }
}
