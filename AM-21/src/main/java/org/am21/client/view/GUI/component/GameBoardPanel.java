package org.am21.client.view.GUI.component;

import org.am21.client.view.GUI.Gui;
import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PixelUtil;
import org.am21.utilities.BoardUtil;
import org.am21.utilities.Coordinates;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GameBoardPanel extends JPanel implements MouseListener, ActionListener {
    Gui gui;
    Point p = new Point();
    private final int GridRowsMax=9;
    private final int GridColumnsMax=9;


    public List<Coordinates> boundaries;
    public JLayeredPane gameBoardPane;
    public JLayeredPane[][] grids = new JLayeredPane[GridRowsMax][GridColumnsMax] ;

    public JLabel[][] cells = new JLabel[GridRowsMax][GridColumnsMax];

    public ScoringTokenLabel scoreTokenEndGame;
    public GameBoardPanel(int maxSeat){
        System.out.println(maxSeat);
        boundaries = BoardUtil.boardBounder(maxSeat);
        System.out.println(boundaries.size());
        setBounds(PixelUtil.gameBoardGridX, PixelUtil.gameBoardGridY,GridRowsMax*PixelUtil.gameBoardCellW,GridColumnsMax*PixelUtil.gameBoardCellH);
       // this.setSize(this.GridRowsMax*this.cellSize,this.GridColumnsMax*this.cellSize);
        setLayout(null);
        setOpaque(false);

        gameBoardPane = new JLayeredPane();
        gameBoardPane.setBounds(0,0,GridRowsMax*PixelUtil.gameBoardCellW,GridColumnsMax*PixelUtil.gameBoardCellH);
        gameBoardPane.setLayout(null);
        //gameBoardPane.setOpaque(false);
        add(gameBoardPane);

        //draw a grid container
        int k=0;
        if(maxSeat==2){
            k=1;
        }
        for(int i=0+k; i<GridRowsMax-k;i++)
        {
            for(int j=0;j<GridColumnsMax;j++)
            {
                if(boundaries.get(i).x<=j&&j<=boundaries.get(i).y)
                {
                    grids[i][j] = new JLayeredPane();
                    grids[i][j].setBounds(j*PixelUtil.gameBoardCellW,i*PixelUtil.gameBoardCellH,PixelUtil.gameBoardCellW,PixelUtil.gameBoardCellH);
                    grids[i][j].setLayout(null);
                    gameBoardPane.add(grids[i][j],JLayeredPane.DEFAULT_LAYER);

                    //putItem(i,j);
                }
            }

        }

        //setScoreTokenEndGame(PixelUtil.endGameTokenX,PixelUtil.endGameTokenY);

    }

    public void putItem(int row, int column,String itemName){

        cells[row][column] = new JLabel(itemName);
        cells[row][column].setBounds(0,0,PixelUtil.gameBoardItemW,PixelUtil.gameBoardItemH);
        cells[row][column].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 255)));
        cells[row][column].setIcon(ImageUtil.getItemImage(itemName,PixelUtil.gameBoardItemW,PixelUtil.gameBoardItemH));
        cells[row][column].addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                Border border = cells[row][column].getBorder();
                if(border instanceof LineBorder) {
                    Color edgeColor = ((LineBorder) border).getLineColor(); //get item border color


                    //do select
                    if(edgeColor.equals(new Color(0, 0, 0, 255))){

                            cells[row][column].setBorder(BorderFactory.createLineBorder(new Color(4, 134, 10, 230), 4));
                           // myHandBoard.putItem(cells[row][column]);

                    } else{
                        //do deselect
                        cells[row][column].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 255)));
                       // myHandBoard.removeItem();
                    }


                }

            }
            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }




        });
        addItem(row,column);
    }

    public boolean containItem(int row, int column){
        return this.cells[row][column] != null;
    }

    public void addItem(int row,int column){
        grids[row][column].add(cells[row][column],JLayeredPane.MODAL_LAYER);

    }
    public void removeItem(int row,int column){
        this.grids[row][column].remove(this.cells[row][column]);
    }


    public void setScoreTokenEndGame(){
        scoreTokenEndGame = new ScoringTokenLabel(ImageUtil.getScoreTokenImage(1),PixelUtil.endGameTokenW,PixelUtil.endGameTokenH,PixelUtil.endGameTokenOriented,PixelUtil.endGameTokenRotateX,PixelUtil.endGameTokenRotateY);
        scoreTokenEndGame.setBounds(PixelUtil.endGameTokenX,PixelUtil.endGameTokenY,PixelUtil.endGameTokenBoundsW,PixelUtil.endGameTokenBoundsH);
        //scoreTokenEndGame.setBackground(new Color(0, 0, 0, 0));
        scoreTokenEndGame.setOpaque(false);
        gameBoardPane.add(scoreTokenEndGame,JLayeredPane.PALETTE_LAYER);
    }


    public void getScoreTokenEndGame(){
        gameBoardPane.remove(scoreTokenEndGame);
    }

    public void clearAll(){
        for(int i=0; i<GridRowsMax;i++)
        {
            for(int j=0;j<GridColumnsMax;j++)
            {
                if(cells[i][j]!=null)
                {
                    cells[i][j].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 255)));

                }
            }

        }

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
