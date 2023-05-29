package org.am21.client.view.GUI.component;

import org.am21.client.view.ClientView;
import org.am21.client.view.GUI.Gui;
import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PixelUtil;
import org.am21.utilities.BoardUtil;
import org.am21.utilities.Coordinates;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.List;


public class GameBoardPanel extends JPanel {
    private final int GridRowsMax = 9;
    private final int GridColumnsMax = 9;

    public List<Coordinates> boundaries;
    public JLayeredPane gameBoardPane;
    public JLayeredPane[][] grids = new JLayeredPane[GridRowsMax][GridColumnsMax];

    public JLabel[][] cells;
    public ScoringTokenLabel scoreTokenEndGame;

    public GameBoardPanel(int maxSeat) {

        setGameBoardPane();

        drawGameBoardGrids(maxSeat);

    }

    /**
     * base set
     */
    public void setGameBoardPane() {
        setBounds(PixelUtil.gameBoardGridX, PixelUtil.gameBoardGridY, GridRowsMax * PixelUtil.gameBoardCellW, GridColumnsMax * PixelUtil.gameBoardCellH);
        setLayout(null);
        setOpaque(false);

        gameBoardPane = new JLayeredPane();
        gameBoardPane.setBounds(0, 0, GridRowsMax * PixelUtil.gameBoardCellW, GridColumnsMax * PixelUtil.gameBoardCellH);
        gameBoardPane.setLayout(null);
        add(gameBoardPane);
    }

    /**
     * draw a game board grids container
     *
     * @param maxSeat num of player
     */
    public void drawGameBoardGrids(int maxSeat) {
        boundaries = BoardUtil.boardBounder(maxSeat);

        int k = 0;
        if (maxSeat == 2) {
            k = 1;
        }
        for (int i = k; i < GridRowsMax - k; i++) {
            for (int j = 0; j < GridColumnsMax; j++) {
                if (boundaries.get(i).x <= j && j <= boundaries.get(i).y) {
                    grids[i][j] = new JLayeredPane();
                    grids[i][j].setBounds(j * PixelUtil.gameBoardCellW, i * PixelUtil.gameBoardCellH, PixelUtil.gameBoardCellW, PixelUtil.gameBoardCellH);
                    grids[i][j].setLayout(null);
                    gameBoardPane.add(grids[i][j], JLayeredPane.DEFAULT_LAYER);

                }
            }

        }
    }

    /**
     * refreshing the game board
     *
     * @param gameBoard virtual board
     * @param gui       Gui
     */

    public void refreshBoard(String[][] gameBoard, Gui gui) {
        clearBoard();
        fillingBoard(gameBoard, gui);
        SwingUtilities.invokeLater(()->{
            revalidate();
            repaint();
        });
    }

    /**
     * clear board
     */
    public void clearBoard() {
        for (JLayeredPane[] pane : grids) {
            for (int i = 0; i < pane.length; i++) {
                if (pane[i] != null)
                    pane[i].removeAll();
            }
        }
        SwingUtilities.invokeLater(()->{
            System.out.println("Board cleaned");
            revalidate();
            repaint();
        });
    }

    /**
     * filling the game board
     *
     * @param gameBoard virtualView board
     * @param gui       Gui
     */
    public void fillingBoard(String[][] gameBoard, Gui gui) {

        cells = new JLabel[GridRowsMax][GridColumnsMax];

        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {

                if (gameBoard[i][j] != null) {
                    cells[i][j] = new JLabel();
                    cells[i][j].setBounds(0, 0, PixelUtil.gameBoardItemW, PixelUtil.gameBoardItemH);
                    cells[i][j].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 255)));
                    cells[i][j].setIcon(ImageUtil.getItemImage(gameBoard[i][j], PixelUtil.gameBoardItemW, PixelUtil.gameBoardItemH));
                    actionItem(i, j, gui);
                    grids[i][j].add(cells[i][j], JLayeredPane.MODAL_LAYER);
                }
            }
        }
        SwingUtilities.invokeLater(()->{
            System.out.println("Board refilled");
            revalidate();
            repaint();
        });


    }

    /**
     * add mouse listener for every item on the game board
     *
     * @param row    index row
     * @param column index column
     * @param gui    Gui
     */
    public void actionItem(int row, int column, Gui gui) {
        cells[row][column].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                try {
                    gui.askSelection();
                } catch (ServerNotActiveException | RemoteException ex) {
                    throw new RuntimeException(ex);
                }

                //catch the border color
                Border border = cells[row][column].getBorder();
                if (border instanceof LineBorder) {
                    Color edgeColor = ((LineBorder) border).getLineColor(); //get item border color
                    //do select
                    if (edgeColor.equals(new Color(0, 0, 0, 255)) && gui.commCtrl.selectCell(row, column)) {
                        cells[row][column].setBorder(BorderFactory.createLineBorder(new Color(4, 134, 10, 230), 4));
                        // myHandBoard.putItem(cells[row][column]);


                    } else if (edgeColor.equals(new Color(4, 134, 10, 230)) && !gui.commCtrl.selectCell(row, column)) {
                        //do deselect
                        cells[row][column].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 255)));
                        // myHandBoard.removeItem();
                    }
                    gui.myHandBoardPanel.refreshItem(ClientView.currentPlayerHand);


                }
                revalidate();
                repaint();

                //TODO: add server message ?
                //JOptionPane.showMessageDialog(null,"error");

            }


        });
    }

    /**
     * set initially endToken
     */
    public void setScoreTokenEndGame() {
        scoreTokenEndGame = new ScoringTokenLabel(ImageUtil.getScoreTokenImage(1), PixelUtil.endGameTokenW, PixelUtil.endGameTokenH, PixelUtil.endGameTokenOriented, PixelUtil.endGameTokenRotateX, PixelUtil.endGameTokenRotateY);
        scoreTokenEndGame.setBounds(PixelUtil.endGameTokenX, PixelUtil.endGameTokenY, PixelUtil.endGameTokenBoundsW, PixelUtil.endGameTokenBoundsH);
        //scoreTokenEndGame.setBackground(new Color(0, 0, 0, 0));
        scoreTokenEndGame.setOpaque(false);
        gameBoardPane.add(scoreTokenEndGame, JLayeredPane.PALETTE_LAYER);
    }


    /**
     * used for clear button only
     */
    public void clearSelectColor() {
        for (int i = 0; i < GridRowsMax; i++) {
            for (int j = 0; j < GridColumnsMax; j++) {
                if (cells[i][j] != null) {
                    cells[i][j].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 255)));
                }
            }
        }
    }

    public void getScoreTokenEndGame() {
        gameBoardPane.remove(scoreTokenEndGame);
    }

}
