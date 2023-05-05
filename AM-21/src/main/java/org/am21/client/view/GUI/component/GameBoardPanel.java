package org.am21.client.view.GUI.component;

import javax.swing.*;
import java.awt.*;

public class GameBoardPanel extends JPanel {
    private final int GridRows=9;
    private final int GridColumn=9;
    //TODO:use global screen value
    private final int GridWidth=999;
    private final int GridHeight=999;
    private final int GridX = 999;
    private final int GridY = 999;
    private final int itemSize = 99;
    private JPanel gridPanel;

    public GameBoardPanel(){

        //draw a grid container
        this.gridPanel = new JPanel(new GridLayout(this.GridRows,this.GridColumn,this.GridWidth,this.GridHeight));
       // padding setting TODO:use global screen value
        this.gridPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        //this.gridPanel.setLayout(new GridLayout(this.GridRows,this.GridColumn,this.GridWidth,this.GridHeight));
        this.gridPanel.setBackground(Color.WHITE);

        for(int i=0; i<this.GridRows*this.GridColumn;i++)
        {
            JPanel cell = new JPanel();
            cell.setBackground(Color.LIGHT_GRAY);
            cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            cell.setOpaque(true);
            this.gridPanel.add(cell);

            //TODO: add item on
            JLabel item1 = new JLabel("some pic you want ");
            cell.add(item1);
            //end to do
        }

    }
}
