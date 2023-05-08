package org.am21.client.view.GUI.component;

import org.am21.client.view.GUI.utils.PathUtil;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;
import java.awt.*;

public class MyHandBoardPanel extends JPanel{
    public final int handMax = 3;
    public JLayeredPane myHandPane;
    public JLayeredPane[] handGrid = new JLayeredPane[handMax];
    public JLabel[] myHandItem = new JLabel[handMax];

    public MyHandBoardPanel(){
        setBounds(PixelUtil.commonX_4, PixelUtil.commonY_4,PixelUtil.myShelfBoardHW,PixelUtil.bottomButtonH);
        setLayout(null);
        setOpaque(false);

        myHandPane = new JLayeredPane();
        myHandPane.setBounds(0,0,PixelUtil.myShelfBoardHW,PixelUtil.bottomButtonH);
        myHandPane.setLayout(null);
        myHandPane.setOpaque(false);
        add(myHandPane);

        for(int i = 0; i< handMax; i++)
        {
            handGrid[i] = new JLayeredPane();
            handGrid[i].setBounds(i*((PixelUtil.myShelfBoardHW)/3),0,(PixelUtil.myShelfBoardHW)/3,PixelUtil.bottomButtonH);
            handGrid[i].setLayout(null);
            myHandPane.add(handGrid[i],JLayeredPane.DEFAULT_LAYER);
            putItem (i);
        }

    }
    public void putItem(int column){
        myHandItem[column] = new JLabel();
        myHandItem[column].setBounds(40,3,PixelUtil.gameBoardItemHW,PixelUtil.gameBoardItemHW);
        myHandItem[column].setIcon(new ImageIcon(new ImageIcon(PathUtil.getPath("icon tool/U1.jpg")).getImage().getScaledInstance(PixelUtil.gameBoardItemHW,PixelUtil.gameBoardItemHW, Image.SCALE_SMOOTH)));
        addItem(column);
    }

    public void addItem(int column){
        handGrid[column].add(myHandItem[column],JLayeredPane.MODAL_LAYER);
    }
}
