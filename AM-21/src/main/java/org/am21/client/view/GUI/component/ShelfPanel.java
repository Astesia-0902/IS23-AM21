package org.am21.client.view.GUI.component;

import org.am21.client.view.GUI.utils.PathUtil;

import javax.swing.*;
import java.awt.*;

public class ShelfPanel extends JPanel {
    public int GridRowsMax=6;
    public int GridColumnsMax=5;

    public int cellHeight;
    public int cellWidth;


    public int itemWidth;
    public int itemHeight;

    public JLayeredPane[][] grids ;

    public JLabel[][] cells;

    public ShelfPanel(int GridX, int GridY, int cellWidth, int cellHeight ,int itemWidth, int itemHeight){


        this.itemWidth = itemWidth;
        this.itemHeight = itemHeight;
        this.cellHeight = cellHeight;
        this.cellWidth = cellWidth;
        grids = new JLayeredPane[GridRowsMax][GridColumnsMax] ;
        cells = new JLabel[GridRowsMax][GridColumnsMax];

        setBounds(GridX,GridY,GridColumnsMax*this.cellWidth,GridRowsMax*this.cellHeight);
        setLayout(null);
        setOpaque(false);

        /*this.shelfBoardPane = new JLayeredPane();
        this.shelfBoardPane.setBounds(0,0,this.GridColumnsMax*this.cellWidth,this.GridRowsMax*this.cellHeight);
        this.shelfBoardPane.setLayout(null);
        this.shelfBoardPane.setOpaque(false);
        this.add(this.shelfBoardPane);*/

        for(int i=0; i<GridRowsMax;i++)
        {
            for(int j=0;j<GridColumnsMax;j++)
            {
                grids[i][j] = new JLayeredPane();
                grids[i][j].setBounds(j*this.cellWidth,i*this.cellHeight,this.cellWidth,this.cellHeight);
                grids[i][j].setLayout(null);
                //this.shelfBoardPane.add(this.grids[i][j],JLayeredPane.DEFAULT_LAYER);
                add(grids[i][j],JLayeredPane.DEFAULT_LAYER);
                    putItem(i,j);
            }

        }
    }

    public void putItem(int row, int column){
        cells[row][column] = new JLabel();
        cells[row][column].setBounds((this.cellWidth-itemWidth)/2,(this.cellHeight-itemHeight)/2, itemWidth, itemHeight);
        cells[row][column].setIcon(new ImageIcon(new ImageIcon(PathUtil.getPath("icon tool/U2.jpg")).getImage().getScaledInstance(itemWidth, itemHeight, Image.SCALE_SMOOTH)));
        addItem(row,column);
    }

    public void refreshShelf(String[][] myShelf){
        //TODO: refreshThe shelf maybe replaced with void putItem(int row, int column) method

    }
    public JLabel getItem(int row, int column){
        return this.cells[row][column];
    }

    public void addItem(int row,int column){
        grids[row][column].add(cells[row][column],JLayeredPane.MODAL_LAYER);

    }
    public void removeItem(int row,int column){
        grids[row][column].remove(cells[row][column]);
    }

}
