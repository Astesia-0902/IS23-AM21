package org.am21.client.view.GUI.Interface;

import org.am21.client.view.GUI.component.ButtonColorUI;
import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.rmi.RemoteException;

import static org.am21.client.SocketClient.gui;

public class GameResultsInterface extends JFrame {
    public JLayeredPane gameResultsPane;
    public JTable tableResults;
    public JTableHeader header;
    public JLabel title;
    public JLabel gameResultsBack;
    public JButton quitGame;

    public GameResultsInterface() {
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
        title.setBounds(PixelUtil.labelRIX, PixelUtil.labelRITitleY, PixelUtil.labelRIW, PixelUtil.labelRITitleH);
        title.setIcon(ImageUtil.getBoardImage("logoTitle"));
        gameResultsPane.add(title, JLayeredPane.PALETTE_LAYER);
        setTitle("Game Results");

        // TODO: fill results

        Object[][] data = {
                {"Player A", 25, 1},
                {"You", 21, 2},
                {"Player B", 14, 3},
                {"Player C", 3, 4}
        };


        String[] columnResultsName = {"Name", "Score", "Position"};


        DefaultTableModel tableModel = new DefaultTableModel(data, columnResultsName);
        tableResults = new JTable(tableModel);
        tableResults.setFont(new Font("DejaVu Sans", Font.PLAIN, 42));
        tableResults.setRowHeight(PixelUtil.labelRIRowH);
        tableResults.setOpaque(false);
        // tableResults.setBorder(BorderFactory.createEmptyBorder());
        tableResults.setShowGrid(false);
        tableResults.setIntercellSpacing(new Dimension(0, 0));
        tableResults.setEnabled(false);
        // tableResults.setDefaultRenderer(Object.class, new TransparentRenderer());

        header = tableResults.getTableHeader();
        header.setFont(new Font("DejaVu Sans", Font.BOLD, 36));
        header.setBackground(new Color(155, 238, 177, 255));
        header.setForeground(new Color(2, 76, 91, 230));
        header.setBorder(BorderFactory.createLineBorder(Color.BLACK));


        JScrollPane scrollPane = new JScrollPane(tableResults);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBounds(PixelUtil.labelRIX, PixelUtil.labelRIY, PixelUtil.labelRIW, PixelUtil.labelRITableH);
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

        // Apply the custom cell renderer to all columns
        for (int i = 0; i < tableResults.getColumnCount(); i++) {
            tableResults.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        quitGame = new JButton("LEAVE");
        quitGame.setFont(new Font("DejaVu Sans", Font.PLAIN, 24));
        quitGame.setBounds(PixelUtil.labelRIButtonX, PixelUtil.labelRIButtonY, PixelUtil.buttonLRW, PixelUtil.buttonLRH);
        quitGame.setUI(new ButtonColorUI(new Color(255, 181, 172, 139)));
        quitGame.setBorder(new MatteBorder(ImageUtil.resizeY(2), ImageUtil.resizeX(2), ImageUtil.resizeY(2),
                ImageUtil.resizeX(2), new Color(178, 34, 34)));
        //quitGame.setOpaque(true);
        quitGame.setBackground(Color.WHITE);
        quitGame.setForeground(new Color(172, 19, 5, 230));
        quitGame.addActionListener(e -> {
            try {
                gui.askExitGame();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }

        });
        gameResultsPane.add(quitGame, JLayeredPane.PALETTE_LAYER);


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
