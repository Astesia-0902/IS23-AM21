package org.am21.client.view.GUI.component;

import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;

public class MyHandBoardPanel extends JPanel{
    public final int handMax = 3;
    public int myHandNum=0;
    public JLayeredPane myHandPane;
    public JLayeredPane[] handGrid = new JLayeredPane[handMax];
    public JLabel[] myHandItem = new JLabel[handMax];

    public MyHandBoardPanel(){

        setBounds(PixelUtil.commonX_4, PixelUtil.commonY_4,PixelUtil.handBoardW,PixelUtil.handBoardH);
        setLayout(null);
        setOpaque(false);

        myHandPane = new JLayeredPane();
        myHandPane.setBounds(0,0,PixelUtil.handBoardW,PixelUtil.handBoardH);
        myHandPane.setLayout(null);
        myHandPane.setOpaque(false);
        add(myHandPane);

        for(int i = 0; i< handMax; i++)
        {
            handGrid[i] = new JLayeredPane();
            handGrid[i].setBounds(i*((PixelUtil.handBoardW)/3),0,(PixelUtil.handBoardW)/3,PixelUtil.handBoardH);
            handGrid[i].setLayout(null);
            myHandPane.add(handGrid[i],JLayeredPane.DEFAULT_LAYER);
          //  putItem (i);
        }

    }

    public boolean isSelected(JLabel cellsItem){
        if(myHandNum<=handMax) {
            return true;
        }
        return false;
    }
    public void putItem(JLabel cellsItem){
            myHandItem[myHandNum] = new JLabel();
            myHandItem[myHandNum].setIcon(cellsItem.getIcon());
            myHandItem[myHandNum].setLocation(PixelUtil.myHandPanelX, PixelUtil.myHandPanelY);
            myHandItem[myHandNum].setSize(PixelUtil.gameBoardItemW,PixelUtil.gameBoardItemH);
            //myHandItem[myHandNum].setBounds(PixelUtil.myHandPanelX,PixelUtil.myHandHandY,PixelUtil.gameBoardItemW,PixelUtil.gameBoardItemH);


            /* myHandItem[column] = new JLabel();
        myHandItem[column].setBounds(PixelUtil.myHandPanelX,PixelUtil.myHandHandY,PixelUtil.gameBoardItemW,PixelUtil.gameBoardItemH);
        myHandItem[column].setIcon(new ImageIcon(new ImageIcon(PathUtil.getPath("icon tool/U1.jpg")).getImage().getScaledInstance(PixelUtil.gameBoardItemW,PixelUtil.gameBoardItemH, Image.SCALE_SMOOTH)));
        */
            addItem(myHandNum);

            myHandNum++;


    }

    public void addItem(int column){
        handGrid[column].add(myHandItem[column],JLayeredPane.MODAL_LAYER);
    }
    public void removeItem(){
        handGrid[myHandNum].remove(myHandItem[myHandNum]);
        myHandNum--;
    }
}
