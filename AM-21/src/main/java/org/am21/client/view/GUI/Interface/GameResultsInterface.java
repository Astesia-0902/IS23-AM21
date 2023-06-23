package org.am21.client.view.GUI.Interface;

import org.am21.client.view.ClientView;
import org.am21.client.view.GUI.Gui;
import org.am21.client.view.GUI.component.ButtonColorUI;
import org.am21.client.view.GUI.utils.FontUtil;
import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;


public class GameResultsInterface extends JFrame {
    public JLayeredPane gameResultsPane;
    public JTable tableResults;
    public JTableHeader header;
    public JLabel title;
    public JLabel gameResultsBack;
    public JButton leaveGame;
    public String[][] data;
    public static int columnResults = 7;

    public GameResultsInterface(Gui gui, String[][] gameResults) {

        setBackGround();

        String winner = gameResults[gameResults.length - 1][0];

        data = new String[gameResults.length - 1][columnResults];

        for (int i = 0; i < gameResults.length - 1; i++) {
            for (int j = 0; j < columnResults - 1; j++) {
                data[i][j] = gameResults[i][j];
            }
            if (winner != null && winner.equals(data[i][0]))
                data[i][columnResults - 1] = "Winner";
        }

        String[] columnResultsName = {"Name", "C-Goal", "P-Goal", "S-Group", "Endgame", "Total", "Results"};

        DefaultTableModel tableModel = new DefaultTableModel(data, columnResultsName);
        tableResults = new JTable(tableModel);
        tableResults.setFont(FontUtil.getFontByName("HongLeiXingShuJianTi-2")
                .deriveFont(Font.PLAIN, ImageUtil.resizeY(42)));
        tableResults.setRowHeight(PixelUtil.labelRITableRowH);
        tableResults.setOpaque(false);
        tableResults.setShowGrid(false);
        tableResults.setIntercellSpacing(new Dimension(0, 0));
        tableResults.setEnabled(false);

        header = tableResults.getTableHeader();
        header.setFont(FontUtil.getFontByName("HongLeiXingShuJianTi-2")
                .deriveFont(Font.PLAIN, ImageUtil.resizeY(36)));
        header.setBackground(new Color(155, 238, 177, 255));
        header.setForeground(new Color(2, 76, 91, 230));
        header.setBorder(BorderFactory.createLineBorder(Color.BLACK));


        JScrollPane scrollPane = new JScrollPane(tableResults);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBounds(0, PixelUtil.labelRIY, PixelUtil.pcWidth, PixelUtil.labelRITableH);
        gameResultsPane.add(scrollPane, JLayeredPane.PALETTE_LAYER);
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Set different font color based on row
                switch (row) {
                    case 0 -> component.setForeground(new Color(241, 199, 7, 230));
                    case 1 -> component.setForeground(new Color(51, 57, 70, 255));
                    case 2 -> component.setForeground(new Color(164, 91, 9, 255));
                    default -> component.setForeground(new Color(0, 0, 0, 255));
                }

                component.setBackground(new Color(0, 0, 0, 0));
                return component;
            }
        };

        for (int i = 0; i < tableResults.getColumnCount(); i++) {
            tableResults.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        //-button quit ------------------------------------------------------------------------------------------------

        callLeaveGame(gui);

        //-------------------------------------------------------------------------------------------------------

        setLocationRelativeTo(null);

    }

    /**
     * base set
     */
    public void setBackGround() {

        setUndecorated(true);
        setSize(PixelUtil.pcWidth, PixelUtil.pcHeight);
        setLayout(null);

        gameResultsPane = new JLayeredPane();
        gameResultsPane.setBounds(0, 0, PixelUtil.pcWidth, PixelUtil.pcHeight);
        gameResultsPane.setLayout(null);
        gameResultsPane.setOpaque(false);

        add(gameResultsPane);

        gameResultsBack = new JLabel();
        gameResultsBack.setBounds(0, 0, PixelUtil.pcWidth, PixelUtil.pcHeight);
        gameResultsBack.setIcon(ImageUtil.getBoardImage("backGround"));
        gameResultsPane.add(gameResultsBack, JLayeredPane.DEFAULT_LAYER);

        title = new JLabel();
        title.setBounds(PixelUtil.labelRITitleX, PixelUtil.labelRITitleY, PixelUtil.labelRITitleW, PixelUtil.labelRITitleH);
        title.setIcon(ImageUtil.getBoardImage("logoTitle"));
        gameResultsPane.add(title, JLayeredPane.PALETTE_LAYER);
        setTitle("Game Results");

    }

    /**
     * set leave game button
     *
     * @param gui
     */
    public void callLeaveGame(Gui gui) {

        leaveGame = new JButton("LEAVE");
        leaveGame.setFont(FontUtil.getFontByName("HongLeiXingShuJianTi-2")
                .deriveFont(Font.PLAIN, ImageUtil.resizeY(24)));
        leaveGame.setBounds(PixelUtil.labelRIButtonX, PixelUtil.labelRIButtonY, PixelUtil.buttonLRW, PixelUtil.buttonLRH);
        leaveGame.setUI(new ButtonColorUI(new Color(255, 181, 172, 139)));
        leaveGame.setBorder(new MatteBorder(ImageUtil.resizeY(2), ImageUtil.resizeX(2), ImageUtil.resizeY(2),
                ImageUtil.resizeX(2), new Color(178, 34, 34)));
        leaveGame.setBackground(Color.WHITE);
        leaveGame.setForeground(new Color(172, 19, 5, 230));
        leaveGame.addActionListener(e -> {

            if (gui.waitingRoomInterface != null) {
                gui.waitingRoomInterface.setVisible(false);
                Gui.myChatMap.remove("#All");
                gui.waitingRoomInterface.dispose();
            }

            gui.gameResultsInterface.dispose();
            gui.setMenuRefresh(true);
            ClientView.setGoToMenu(true);
            ClientView.setGameOn(false);
            gui.setNeedNewFrame(true);
            ClientView.setMatchEnd(false);

        });
        gameResultsPane.add(leaveGame, JLayeredPane.PALETTE_LAYER);
    }

}
