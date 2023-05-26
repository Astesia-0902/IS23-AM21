package org.am21.client.view.GUI.component;

import org.am21.client.view.GUI.utils.ImageUtil;

import javax.swing.*;

public class ShelfPanel extends JPanel {
    public int GridRowsMax = 6;
    public int GridColumnsMax = 5;

    public int cellHeight;
    public int cellWidth;


    public int itemWidth;
    public int itemHeight;

    public JLayeredPane[][] grids = new JLayeredPane[GridRowsMax][GridColumnsMax];

    public JLabel[][] cells;

    public ShelfPanel(int GridX, int GridY, int cellWidth, int cellHeight, int itemWidth, int itemHeight) {


        this.itemWidth = itemWidth;
        this.itemHeight = itemHeight;
        this.cellHeight = cellHeight;
        this.cellWidth = cellWidth;

        cells = new JLabel[GridRowsMax][GridColumnsMax];

        setBounds(GridX, GridY, GridColumnsMax * this.cellWidth, GridRowsMax * this.cellHeight);
        setLayout(null);
        setOpaque(false);

        /*this.shelfBoardPane = new JLayeredPane();
        this.shelfBoardPane.setBounds(0,0,this.GridColumnsMax*this.cellWidth,this.GridRowsMax*this.cellHeight);
        this.shelfBoardPane.setLayout(null);
        this.shelfBoardPane.setOpaque(false);
        this.add(this.shelfBoardPane);*/

        for (int i = 0; i < GridRowsMax; i++) {
            for (int j = 0; j < GridColumnsMax; j++) {
                grids[i][j] = new JLayeredPane();
                grids[i][j].setBounds(j * this.cellWidth, i * this.cellHeight, this.cellWidth, this.cellHeight);
                grids[i][j].setLayout(null);
                //this.shelfBoardPane.add(this.grids[i][j],JLayeredPane.DEFAULT_LAYER);
                add(grids[i][j]);
                //putItem(i,j);
            }

        }
    }

    //TODO: used when finishing insertion
    public void refreshShelf(String[][] myShelf) {

        for (int i = 0; i < GridRowsMax; i++) {
            for (int j = 0; j < GridColumnsMax; j++) {
                JLayeredPane pane = grids[i][j];
                if (pane != null) {
                    pane.getParent().remove(pane);
                }
            }
        }

        cells = new JLabel[myShelf.length][myShelf[0].length];

        for (int i = 0; i < myShelf.length; i++) {
            for (int j = 0; j < myShelf[0].length; j++) {
                cells[i][j] = new JLabel();
                cells[i][j].setBounds((this.cellWidth - itemWidth) / 2, (this.cellHeight - itemHeight) / 2, itemWidth, itemHeight);
                cells[i][j].setIcon(ImageUtil.getItemImage(myShelf[i][j], itemWidth, itemHeight));
                grids[i][j].add(cells[i][j], JLayeredPane.MODAL_LAYER);
            }
        }

        revalidate();
        repaint();

    }

    public JLabel getItem(int row, int column) {
        return this.cells[row][column];
    }

    public void removeItem(int row, int column) {
        grids[row][column].remove(cells[row][column]);
    }

}
