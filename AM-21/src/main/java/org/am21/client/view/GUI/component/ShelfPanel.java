package org.am21.client.view.GUI.component;

import org.am21.client.view.GUI.utils.PathUtil;

import javax.swing.*;
import java.awt.*;

public class ShelfPanel extends JPanel {
    protected final int GridRowsMax=6;
    protected final int GridColumnsMax=5;
    //TODO:use global screen value

    protected int cellSize;

    protected int GridX;
    protected int GridY ;
    protected int itemSize;

    protected JLayeredPane shelfBoardPane;
    protected JLayeredPane[][] grids = new JLayeredPane[this.GridRowsMax][this.GridColumnsMax] ;

    protected JLabel[][] cells = new JLabel[this.GridRowsMax][this.GridColumnsMax];

    public ShelfPanel(){
        this.setBounds(GridX,GridY,this.GridRowsMax*this.cellSize,this.GridColumnsMax*this.cellSize);
        this.setLayout(null);
        this.setOpaque(false);

        this.shelfBoardPane = new JLayeredPane();
        this.shelfBoardPane.setBounds(0,0,this.GridRowsMax*this.cellSize,this.GridColumnsMax*this.cellSize);
        this.shelfBoardPane.setLayout(null);
        this.shelfBoardPane.setOpaque(false);
        this.add(this.shelfBoardPane);

        for(int i=0; i<this.GridRowsMax;i++)
        {
            for(int j=0;j<this.GridColumnsMax;j++)
            {
                this.grids[i][j] = new JLayeredPane();
                this.grids[i][j].setBounds(j*this.cellSize,i*this.cellSize,this.cellSize,this.cellSize);
                this.grids[i][j].setLayout(null);
                this.shelfBoardPane.add(this.grids[i][j],JLayeredPane.DEFAULT_LAYER);

                    putItem(i,j);
            }

        }
    }

    public void putItem(int row, int column){
        this.cells[row][column] = new JLabel();
        this.cells[row][column].setBounds(0,0,itemSize,itemSize);
        this.cells[row][column].setIcon(new ImageIcon(new ImageIcon(PathUtil.getPath("icon tool/U2.jpg")).getImage().getScaledInstance(itemSize,itemSize, Image.SCALE_SMOOTH)));
        addItem(row,column);
    }

    public JLabel getItem(int row, int column){
        return this.cells[row][column];
    }

    public void addItem(int row,int column){
        this.grids[row][column].add(this.cells[row][column],JLayeredPane.MODAL_LAYER);

    }
    public void removeItem(int row,int column){
        this.grids[row][column].remove(this.cells[row][column]);
    }

    public void setInfo(int cellSize,int itemSize,int GridX, int GridY){
        this.cellSize=cellSize;
        this.itemSize=itemSize;
        this.GridX=GridX;
        this.GridY=GridY;

    }
}
