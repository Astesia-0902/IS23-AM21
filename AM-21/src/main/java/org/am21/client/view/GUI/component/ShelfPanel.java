package org.am21.client.view.GUI.component;

import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PathUtil;

import javax.swing.*;
import java.awt.*;

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

    public void refreshShelf(String[][] myShelf) {

        for (JLayeredPane[] pane : grids) {
            for (int i = 0; i < pane.length; i++) {
                pane[i].removeAll();
            }
        }

        cells = new JLabel[myShelf.length][myShelf[0].length];

        for (int i = 0; i < myShelf.length; i++) {
            for (int j = 0; j < myShelf[0].length; j++) {
                if (myShelf[i][j] != null) {
                    cells[i][j] = new JLabel();
                    cells[i][j].setBounds((this.cellWidth - itemWidth) / 2, (this.cellHeight - itemHeight) / 2, itemWidth, itemHeight);
                    cells[i][j].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 255)));
                    cells[i][j].setIcon(ImageUtil.getItemImage(myShelf[i][j], itemWidth, itemHeight));
                    grids[i][j].add(cells[i][j], JLayeredPane.MODAL_LAYER);
                }

            }
        }

        revalidate();
        repaint();

    }

    public void previewInsertShelf(int column, int[] row, String[][] futureShelf) {
        for (JLayeredPane[] pane : grids) {
            for (int i = 0; i < pane.length; i++) {
                pane[i].removeAll();
            }
        }

        cells = new JLabel[futureShelf.length][futureShelf[0].length];

        for (int i = 0; i < futureShelf.length; i++) {
            for (int j = 0; j < futureShelf[0].length; j++) {
                if (futureShelf[i][j] != null) {
                    cells[i][j] = new JLabel();
                    cells[i][j].setBounds((this.cellWidth - itemWidth) / 2, (this.cellHeight - itemHeight) / 2, itemWidth, itemHeight);
                    cells[i][j].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 255)));
                    cells[i][j].setIcon(ImageUtil.getItemImage(futureShelf[i][j], itemWidth, itemHeight));
                    grids[i][j].add(cells[i][j], JLayeredPane.MODAL_LAYER);
                }

            }
        }
        for (int i = 0; i < row.length; i++) {
            if (row[i] == 1)
                cells[i][column].setBorder(BorderFactory.createLineBorder(new Color(4, 245, 237, 230), 3));
        }

        revalidate();
        repaint();

    }

    public void putItem(int row, int column) {
        cells[row][column] = new JLabel();
        cells[row][column].setBounds((this.cellWidth - itemWidth) / 2, (this.cellHeight - itemHeight) / 2, itemWidth, itemHeight);
        cells[row][column].setIcon(new ImageIcon(new ImageIcon(PathUtil.getPath("icon tool/U2.jpg")).getImage().getScaledInstance(itemWidth, itemHeight, Image.SCALE_SMOOTH)));
        grids[row][column].add(cells[row][column], JLayeredPane.MODAL_LAYER);
    }
}
