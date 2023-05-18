package org.am21.client.view.GUI.component;

import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;
import java.util.List;

public class MyHandBoardPanel extends JPanel{
    public final int handMax = 3;
    public JLayeredPane[] handGrid = new JLayeredPane[handMax];
    public JLabel[] myHandItem = new JLabel[handMax];

    public MyHandBoardPanel(){

        setBounds(PixelUtil.commonX_4, PixelUtil.commonY_4,PixelUtil.handBoardW,PixelUtil.handBoardH);
        setLayout(null);
        setOpaque(false);

        for(int i = 0; i< handMax; i++)
        {
            handGrid[i] = new JLayeredPane();
            handGrid[i].setBounds(i*((PixelUtil.handBoardW)/3),0,(PixelUtil.handBoardW)/3,PixelUtil.handBoardH);
            handGrid[i].setLayout(null);
            add(handGrid[i]);
        }

    }

    /**
     * refreshing the board view
     * @param myItem list of item
     */
    public void refreshItem(List<String> myItem){

        for(int i=0; i<handMax;i++)
        {
            myHandItem[i] = new JLabel();
            if(i<myItem.size())
                myHandItem[i].setIcon(ImageUtil.getItemImage(myItem.get(i),PixelUtil.gameBoardItemW,PixelUtil.gameBoardItemH));
            else
                myHandItem[i].setIcon(null);

            myHandItem[i].setLocation(PixelUtil.myHandPanelX, PixelUtil.myHandPanelY);
            myHandItem[i].setSize(PixelUtil.gameBoardItemW,PixelUtil.gameBoardItemH);

            handGrid[i].add(myHandItem[i],JLayeredPane.PALETTE_LAYER);
        }

           /* myHandItem[myHandNum] = new JLabel();
            myHandItem[myHandNum].setIcon(cellsItem.getIcon());
            myHandItem[myHandNum].setLocation(PixelUtil.myHandPanelX, PixelUtil.myHandPanelY);
            myHandItem[myHandNum].setSize(PixelUtil.gameBoardItemW,PixelUtil.gameBoardItemH);

            handGrid[myHandNum].add(myHandItem[myHandNum],JLayeredPane.PALETTE_LAYER);

            myHandNum++;*/

    }

}
