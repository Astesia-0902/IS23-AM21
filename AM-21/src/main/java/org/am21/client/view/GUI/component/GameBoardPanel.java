package org.am21.client.view.GUI.component;

import org.am21.client.view.GUI.utils.PathUtil;
import org.am21.utilities.BoardUtil;
import org.am21.utilities.Coordinates;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameBoardPanel extends JPanel {
    private final int GridRowsMax=9;
    private final int GridColumnsMax=9;
    //TODO:use global screen value

    private final int cellSize = 79;

    private int GridX = 410;
    private int GridY = 140;
    private final int itemSize = 72;

    private List<Coordinates> boundaries=BoardUtil.boardBounder(4);
    private JLayeredPane gameBoardPane;
    private JLayeredPane[][] grids = new JLayeredPane[this.GridRowsMax][this.GridColumnsMax] ;

    private JLabel[][] cells = new JLabel[this.GridRowsMax][this.GridColumnsMax];

    public GameBoardPanel(){

        this.setBounds(GridX,GridY,this.GridRowsMax*this.cellSize,this.GridColumnsMax*this.cellSize);
       // this.setSize(this.GridRowsMax*this.cellSize,this.GridColumnsMax*this.cellSize);
        this.setLayout(null);
        this.setOpaque(false);

        this.gameBoardPane = new JLayeredPane();
        this.gameBoardPane.setBounds(0,0,this.GridRowsMax*this.cellSize,this.GridColumnsMax*this.cellSize);
        this.gameBoardPane.setLayout(null);
        this.gameBoardPane.setOpaque(false);
        this.add(this.gameBoardPane);

        //draw a grid container

        for(int i=0; i<this.GridRowsMax;i++)
        {
            for(int j=0;j<this.GridColumnsMax;j++)
            {
                if(boundaries.get(i).x<=j&&j<=boundaries.get(i).y)
                {
                    this.grids[i][j] = new JLayeredPane();
                    this.grids[i][j].setBounds(j*this.cellSize,i*this.cellSize,this.cellSize,this.cellSize);
                    this.grids[i][j].setLayout(null);
                    this.gameBoardPane.add(this.grids[i][j],JLayeredPane.DEFAULT_LAYER);

                    putItem(i,j);
                }
            }

        }

    }

    public void putItem(int row, int column){
        this.cells[row][column] = new JLabel();
        this.cells[row][column].setBounds(0,0,itemSize,itemSize);
        this.cells[row][column].setIcon(new ImageIcon(new ImageIcon(PathUtil.getPath("icon tool/U3.jpg")).getImage().getScaledInstance(itemSize,itemSize, Image.SCALE_SMOOTH)));
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


}
