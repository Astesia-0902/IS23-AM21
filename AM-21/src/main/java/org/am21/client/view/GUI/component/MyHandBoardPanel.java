package org.am21.client.view.GUI.component;

import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;

public class MyHandBoardPanel extends JPanel{
    public final int handMax = 3;
    public int myHandNum=0;
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
    public int getMyHandNum(){
        return this.myHandNum;
    }

    public boolean canPick(){
        return myHandNum < handMax;
    }
    public void putItem(JLabel cellsItem){

            myHandItem[myHandNum] = new JLabel();
            myHandItem[myHandNum].setIcon(cellsItem.getIcon());
            myHandItem[myHandNum].setLocation(PixelUtil.myHandPanelX, PixelUtil.myHandPanelY);
            myHandItem[myHandNum].setSize(PixelUtil.gameBoardItemW,PixelUtil.gameBoardItemH);

            handGrid[myHandNum].add(myHandItem[myHandNum],JLayeredPane.PALETTE_LAYER);

            myHandNum++;

    }

    public void removeItem(){
        myHandNum--;
        myHandItem[myHandNum].setIcon(null);
    }

}
