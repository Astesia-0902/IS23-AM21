package org.am21.client.view.GUI.Interface;

import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;

public class GameResultsInterface extends JFrame {
    public JLayeredPane gameResultsPane;
    public JLabel gameResultsBack;

    public GameResultsInterface(){
        setSize(PixelUtil.pcWidth, PixelUtil.pcHeight);
        setLayout(null);

        gameResultsPane = new JLayeredPane();
        gameResultsPane.setBounds(0,0,PixelUtil.pcWidth,PixelUtil.pcHeight);
        gameResultsPane.setLayout(null);
        gameResultsPane.setOpaque(false);
        add(gameResultsPane);

        gameResultsBack = new JLabel();
        gameResultsBack.setBounds(0,0,PixelUtil.pcWidth,PixelUtil.pcHeight);
        gameResultsBack.setIcon(ImageUtil.getBoardImage("backGround"));
        gameResultsPane.add(gameResultsBack,JLayeredPane.DEFAULT_LAYER);

        Object[][] data = {
                {"Player A","32 pts" }
        };

        String[] columnNames = {"Name","points"};

        JTable tableResults = new JTable(data,columnNames);
        tableResults.setBounds(0,0,400,400);
        gameResultsPane.add(tableResults,JLayeredPane.PALETTE_LAYER);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            new GameResultsInterface();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
