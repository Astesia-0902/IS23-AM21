package org.am21.client.view.GUI.component;

import org.am21.client.view.GUI.Gui;
import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PathUtil;
import org.am21.client.view.GUI.utils.PixelUtil;
import org.am21.utilities.BoardUtil;
import org.am21.utilities.Coordinates;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GameBoardPanel extends JPanel implements MouseListener, ActionListener {
    Gui gui;
    Point p = new Point();
    private final int GridRowsMax=9;
    private final int GridColumnsMax=9;

    //private final int cellSize = 79;

    //private int GridX = 417;
    //private int GridY = 145;
    //private final int itemSize = 72;

    public List<Coordinates> boundaries=BoardUtil.boardBounder(4);
    public JLayeredPane gameBoardPane;
    public JLayeredPane[][] grids = new JLayeredPane[GridRowsMax][GridColumnsMax] ;

    public JLabel[][] cells = new JLabel[GridRowsMax][GridColumnsMax];

    public ScoringTokenLabel scoreTokenEndGame;
    public GameBoardPanel(){

        setBounds(PixelUtil.gameBoardGridX, PixelUtil.gameBoardGridY,GridRowsMax*PixelUtil.gameBoardCellW,GridColumnsMax*PixelUtil.gameBoardCellH);
       // this.setSize(this.GridRowsMax*this.cellSize,this.GridColumnsMax*this.cellSize);
        setLayout(null);
        setOpaque(false);

        gameBoardPane = new JLayeredPane();
        gameBoardPane.setBounds(0,0,GridRowsMax*PixelUtil.gameBoardCellW,GridColumnsMax*PixelUtil.gameBoardCellH);
        gameBoardPane.setLayout(null);
        gameBoardPane.setOpaque(false);
        add(gameBoardPane);

        //draw a grid container

        for(int i=0; i<GridRowsMax;i++)
        {
            for(int j=0;j<GridColumnsMax;j++)
            {
                if(boundaries.get(i).x<=j&&j<=boundaries.get(i).y)
                {
                    grids[i][j] = new JLayeredPane();
                    grids[i][j].setBounds(j*PixelUtil.gameBoardCellW,i*PixelUtil.gameBoardCellH,PixelUtil.gameBoardCellW,PixelUtil.gameBoardCellH);
                    grids[i][j].setLayout(null);
                    gameBoardPane.add(grids[i][j],JLayeredPane.DEFAULT_LAYER);

                    //TODO: refill board randomly

                    putItem(i,j);
                }
            }

        }

        setScoreTokenEndGame(PixelUtil.endGameTokenX,PixelUtil.endGameTokenY);

    }

    public void putItem(int row, int column){
        cells[row][column] = new JLabel();
        cells[row][column].setBounds(0,0,PixelUtil.gameBoardItemW,PixelUtil.gameBoardItemH);
        cells[row][column].setIcon(new ImageIcon(new ImageIcon(PathUtil.getPath("icon tool/U3.jpg")).getImage().getScaledInstance(PixelUtil.gameBoardItemW,PixelUtil.gameBoardItemH, Image.SCALE_SMOOTH)));
        addItem(row,column);
    }

    public JLabel getItem(int row, int column){
        return this.cells[row][column];
    }

    public void addItem(int row,int column){
        grids[row][column].add(cells[row][column],JLayeredPane.MODAL_LAYER);

    }
    public void removeItem(int row,int column){
        this.grids[row][column].remove(this.cells[row][column]);
    }


    public void setScoreTokenEndGame(int posX,int posY){
        scoreTokenEndGame = new ScoringTokenLabel(ImageUtil.getScoreTokenImage(1),PixelUtil.endGameTokenW,PixelUtil.endGameTokenH,PixelUtil.endGameTokenOriented,PixelUtil.endGameTokenRotateX,PixelUtil.endGameTokenRotateY);
        scoreTokenEndGame.setBounds(posX,posY,PixelUtil.endGameTokenBoundsW,PixelUtil.endGameTokenBoundsH);
        scoreTokenEndGame.setBackground(new Color(0, 0, 0, 0));
        gameBoardPane.add(scoreTokenEndGame,JLayeredPane.PALETTE_LAYER);
    }



    public void getScoreTokenEndGame(){
        //TODO:
    }

    public void actionPerformed(ActionEvent e) {

    }
    public void mouseClicked(MouseEvent e) {


    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
}
