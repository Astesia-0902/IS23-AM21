package org.am21.client.view.GUI.Interface;

import org.am21.client.view.ClientView;
import org.am21.client.view.GUI.Gui;
import org.am21.client.view.GUI.component.ButtonColorUI;
import org.am21.client.view.GUI.component.ShelfPanel;
import org.am21.client.view.GUI.utils.FontUtil;
import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class MyHandInterface extends JDialog {

    public int handMax = 3;
    public int rowMax = 6;
    public int columnMax = 5;
    public JLayeredPane myHandInterfacePane;
    public JLabel myHandInterfaceBack;
    public JLabel myHandLabel;
    public JLayeredPane[] handGrid = new JLayeredPane[handMax];
    public JLabel[] myHandItem;


    public JLabel myShelfBoardLabel;
    public JButton sort;
    public ButtonGroup optionGroup;
    public JButton confirm;
    public ShelfPanel originalShelf;
    public ShelfPanel previewPanel;
    public List<Integer> posSort = new ArrayList<>();
    public int[] vectorFreeColumn;
    public String[][] previewShelf;
    public int finalColumn = -1;

    public MyHandInterface(Gui gui) {
        super(gui.livingRoomInterface, true);
        setMyHandInterfacePane();

        setMyHandGrids();

        storeOriginalShelf();

        setPreviewPanel();

        setSortButton(gui);

        setColumnOptionGroupButton();

        setConfirmButton(gui);

        setVisible(true);

    }

    /**
     * base set
     */
    public void setMyHandInterfacePane() {
        setBounds(PixelUtil.myHandBackGroundX, PixelUtil.myHandBackGroundY, PixelUtil.myHandBackGroundW, PixelUtil.myHandBackGroundH);
        setUndecorated(true);
        setResizable(false);
        setTitle("My hand");
        setLayout(null);

        myHandInterfacePane = new JLayeredPane();
        myHandInterfacePane.setBounds(0, 0, PixelUtil.myHandBackGroundW, PixelUtil.myHandBackGroundH);
        myHandInterfacePane.setLayout(null);
        myHandInterfacePane.setOpaque(false);
        add(myHandInterfacePane);

        //background
        myHandInterfaceBack = new JLabel();
        myHandInterfaceBack.setBounds(0, 0, PixelUtil.myHandBackGroundW, PixelUtil.myHandBackGroundH);
        myHandInterfaceBack.setIcon(ImageUtil.getBoardImage("myHandBack"));
        myHandInterfacePane.add(myHandInterfaceBack, JLayeredPane.DEFAULT_LAYER);

        //hand label
        myHandLabel = new JLabel();
        myHandLabel.setBounds(PixelUtil.myHandHandX, PixelUtil.myHandHandY, PixelUtil.myHandHandW, PixelUtil.myHandHandH);
        myHandLabel.setIcon(ImageUtil.getBoardImage("myHandHand"));
        myHandInterfacePane.add(myHandLabel, JLayeredPane.PALETTE_LAYER);

        //shelf label
        myShelfBoardLabel = new JLabel();
        myShelfBoardLabel.setBounds(PixelUtil.myHandShelfX, PixelUtil.myHandHandY, PixelUtil.myShelfBoardW, PixelUtil.myShelfBoardH);
        myShelfBoardLabel.setIcon(ImageUtil.getShelfImage(PixelUtil.myShelfBoardW, PixelUtil.myShelfBoardH));
        myHandInterfacePane.add(myShelfBoardLabel, JLayeredPane.PALETTE_LAYER);
    }

    /**
     * grids base set
     */
    public void setMyHandGrids() {

        for (int i = 0; i < handMax; i++) {
            handGrid[i] = new JLayeredPane();
            handGrid[i].setBounds(PixelUtil.myHandHandX, PixelUtil.myHandHandY + (i * ((PixelUtil.myHandHandH) / 3)), PixelUtil.myHandHandW, (PixelUtil.myHandHandH) / 3);
            handGrid[i].setLayout(null);
            handGrid[i].setBackground(Color.WHITE);
            myHandInterfacePane.add(handGrid[i], JLayeredPane.MODAL_LAYER);
        }
    }

    /**
     * store the original shelf
     */
    public void storeOriginalShelf() {

        originalShelf = new ShelfPanel(PixelUtil.myHandShelfGridX, PixelUtil.myHandShelfGridY, PixelUtil.myCellW, PixelUtil.myCellH, PixelUtil.myItemW, PixelUtil.myItemH);
        originalShelf.refreshShelf(ClientView.shelves.get(ClientView.getPlayerIndex(Gui.username)));

    }

    /**
     * view the preview panel
     */
    public void setPreviewPanel() {
        //available column for preview
        vectorFreeColumn = availableColumn();

        //initialize preview shelf matrix
        previewShelf = restoreShelf(ClientView.shelves.get(ClientView.getPlayerIndex(Gui.username)));

        //set preview panel
        previewPanel = new ShelfPanel(PixelUtil.myHandShelfGridX, PixelUtil.myHandShelfGridY, PixelUtil.myCellW, PixelUtil.myCellH, PixelUtil.myItemW, PixelUtil.myItemH);
        previewPanel.refreshShelf(previewShelf);
        myHandInterfacePane.add(previewPanel, JLayeredPane.MODAL_LAYER);
    }

    /**
     * sort button function
     *
     * @param gui GUI
     */
    public void setSortButton(Gui gui) {
        sort = new JButton();
        sort.setBounds(PixelUtil.myHandSortX, PixelUtil.myHandSortY, PixelUtil.myHandSortW, PixelUtil.myHandSortH);
        sort.setForeground(new Color(164, 91, 9, 255));
        sort.setOpaque(false);
        sort.setIcon(ImageUtil.getBoardImage("iconSort"));
        refreshHand(ClientView.currentPlayerHand); //refresh board
        sort.addActionListener(e -> {

            if (posSort.size() == 2) {
                if (gui.commCtrl.sortHand(posSort.get(0), posSort.get(1)))
                    System.out.println("sort successful");
                else
                    System.out.println("failed");
                refreshHand(ClientView.currentPlayerHand); //refresh new board
                posSort.clear(); //clear list
            }

        });
        myHandInterfacePane.add(sort, JLayeredPane.PALETTE_LAYER);
    }

    /**
     * column function
     */
    public void setColumnOptionGroupButton() {
        //button group
        optionGroup = new ButtonGroup();

        JRadioButton selCol1 = new JRadioButton();
        selCol1.setIcon(ImageUtil.getNumberImage(1 + "gray"));
        selCol1.setBounds(PixelUtil.myHandOptionX + PixelUtil.myHandOptionXDiff, PixelUtil.myHandOptionY, PixelUtil.handNumW, PixelUtil.handNumH);
        selCol1.setOpaque(false);

        JRadioButton selCol2 = new JRadioButton();
        selCol2.setIcon(ImageUtil.getNumberImage(2 + "gray"));
        selCol2.setBounds(PixelUtil.myHandOptionX + 2 * PixelUtil.myHandOptionXDiff, PixelUtil.myHandOptionY, PixelUtil.handNumW, PixelUtil.handNumH);
        selCol2.setOpaque(false);

        JRadioButton selCol3 = new JRadioButton();
        selCol3.setIcon(ImageUtil.getNumberImage(3 + "gray"));
        selCol3.setBounds(PixelUtil.myHandOptionX + 3 * PixelUtil.myHandOptionXDiff, PixelUtil.myHandOptionY, PixelUtil.handNumW, PixelUtil.handNumH);
        selCol3.setOpaque(false);

        JRadioButton selCol4 = new JRadioButton();
        selCol4.setIcon(ImageUtil.getNumberImage(4 + "gray"));
        selCol4.setBounds(PixelUtil.myHandOptionX + 4 * PixelUtil.myHandOptionXDiff, PixelUtil.myHandOptionY, PixelUtil.handNumW, PixelUtil.handNumH);
        selCol4.setOpaque(false);

        JRadioButton selCol5 = new JRadioButton();
        selCol5.setIcon(ImageUtil.getNumberImage(5 + "gray"));
        selCol5.setBounds(PixelUtil.myHandOptionX + 5 * PixelUtil.myHandOptionXDiff, PixelUtil.myHandOptionY, PixelUtil.handNumW, PixelUtil.handNumH);
        selCol5.setOpaque(false);

        optionGroup.add(selCol1);
        optionGroup.add(selCol2);
        optionGroup.add(selCol3);
        optionGroup.add(selCol4);
        optionGroup.add(selCol5);

        selCol1.addActionListener(e -> {
            if (vectorFreeColumn[0] >= ClientView.currentPlayerHand.size()) {
                //show preview
                previewPanel.previewInsertShelf(0, futureShelf(0), previewShelf);
                //testPanel.previewInsertShelf(1,row,matrix);
                previewShelf = restoreShelf(ClientView.shelves.get(ClientView.getPlayerIndex(Gui.username)));
                finalColumn = 0;
            } else
                JOptionPane.showMessageDialog(null, "column 1 is not available, try again!");

        });

        selCol2.addActionListener(e -> {
            if (vectorFreeColumn[1] >= ClientView.currentPlayerHand.size()) {
                //show preview
                previewPanel.previewInsertShelf(1, futureShelf(1), previewShelf);
                previewShelf = restoreShelf(ClientView.shelves.get(ClientView.getPlayerIndex(Gui.username)));
                finalColumn = 1;
            } else
                JOptionPane.showMessageDialog(null, "column 2 is not available, try again!");

        });
        selCol3.addActionListener(e -> {
            if (vectorFreeColumn[2] >= ClientView.currentPlayerHand.size()) {
                //show preview
                previewPanel.previewInsertShelf(2, futureShelf(2), previewShelf);
                previewShelf = restoreShelf(ClientView.shelves.get(ClientView.getPlayerIndex(Gui.username)));
                finalColumn = 2;
            } else
                JOptionPane.showMessageDialog(null, "column 3 is not available, try again!");

        });
        selCol4.addActionListener(e -> {
            if (vectorFreeColumn[3] >= ClientView.currentPlayerHand.size()) {
                //show preview
                previewPanel.previewInsertShelf(3, futureShelf(3), previewShelf);
                previewShelf = restoreShelf(ClientView.shelves.get(ClientView.getPlayerIndex(Gui.username)));
                finalColumn = 3;
            } else
                JOptionPane.showMessageDialog(null, "column 4 is not available, try again!");

        });
        selCol5.addActionListener(e -> {
            if (vectorFreeColumn[4] >= ClientView.currentPlayerHand.size()) {
                //show preview
                previewPanel.previewInsertShelf(4, futureShelf(4), previewShelf);
                previewShelf = restoreShelf(ClientView.shelves.get(ClientView.getPlayerIndex(Gui.username)));
                finalColumn = 4;
            } else
                JOptionPane.showMessageDialog(null, "column 5 is not available, try again!");

        });

        myHandInterfacePane.add(selCol1, JLayeredPane.PALETTE_LAYER);
        myHandInterfacePane.add(selCol2, JLayeredPane.PALETTE_LAYER);
        myHandInterfacePane.add(selCol3, JLayeredPane.PALETTE_LAYER);
        myHandInterfacePane.add(selCol4, JLayeredPane.PALETTE_LAYER);
        myHandInterfacePane.add(selCol5, JLayeredPane.PALETTE_LAYER);
    }

    /**
     * confirm button function
     *
     * @param gui GUI
     */
    public void setConfirmButton(Gui gui) {
        confirm = new JButton("CONFIRM");
        confirm.setFont(FontUtil.getFontByName("HongLeiXingShuJianTi-2")
                .deriveFont(Font.PLAIN, ImageUtil.resizeY(16)));
        confirm.setBounds(PixelUtil.myHandConfirmX, PixelUtil.myHandConfirmY, PixelUtil.myHandConfirmW, PixelUtil.myHandConfirmH);
        confirm.setBorder(new MatteBorder(ImageUtil.resizeY(2), ImageUtil.resizeX(2), ImageUtil.resizeY(2),
                ImageUtil.resizeX(2), new Color(4, 134, 10, 230)));
        confirm.setUI(new ButtonColorUI(new Color(136, 218, 123, 139)));
        confirm.setBackground(Color.WHITE);
        confirm.setForeground(new Color(4, 134, 10, 230));
        confirm.addActionListener(e -> {
            Window window = SwingUtilities.windowForComponent(confirm);
            if (finalColumn != -1) {

                if (gui.commCtrl.insertInColumn(finalColumn)) {
                    gui.timeLimitedNotification("Insertion successful",500);
                    if (gui.commCtrl.endTurn()) {
                        try {
                            gui.announceCurrentPlayer();
                        } catch (RemoteException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                }

                window.dispose();

            } else
                JOptionPane.showMessageDialog(null, "please select the column");


        });
        myHandInterfacePane.add(confirm, JLayeredPane.PALETTE_LAYER);
    }

    /**
     * refresh my current hand
     *
     * @param myItem list of item on hands
     */
    public void refreshHand(List<String> myItem) {

        for (JLayeredPane pane : handGrid) {
            pane.removeAll();
        }

        myHandItem = new JLabel[myItem.size()];

        for (int i = 0; i < myItem.size(); i++) {

            myHandItem[i] = new JLabel();
            myHandItem[i].setIcon(ImageUtil.getItemImage(myItem.get(i), PixelUtil.gameBoardItemW, PixelUtil.gameBoardItemH));
            myHandItem[i].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 255)));
            myHandItem[i].setLocation(PixelUtil.myHandItemX, PixelUtil.myHandItemY);
            myHandItem[i].setSize(PixelUtil.gameBoardItemW, PixelUtil.gameBoardItemH);
            actionItem(i);
            handGrid[handMax - 1 - i].add(myHandItem[i], JLayeredPane.PALETTE_LAYER);

        }

    }

    /**
     * color changed on hand board
     *
     * @param pos position
     */
    public void actionItem(int pos) {
        myHandItem[pos].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //super.mouseClicked(e);
                Border border = myHandItem[pos].getBorder();
                if (border instanceof LineBorder) {
                    Color edgeColor = ((LineBorder) border).getLineColor(); //get item border color
                    //do select
                    if (edgeColor.equals(new Color(0, 0, 0, 255)) && posSort.size() < 2) {
                        myHandItem[pos].setBorder(BorderFactory.createLineBorder(new Color(203, 63, 4, 230), 4));
                        posSort.add(pos);

                    } else if (edgeColor.equals(new Color(203, 63, 4, 230))) {
                        //do deselect
                        myHandItem[pos].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 255)));
                        posSort.remove(pos);
                    }

                }
            }
        });


    }

    /**
     * modified Shelf preview Matrix
     *
     * @param column index of column
     * @return vector of index for highlights
     */
    public int[] futureShelf(int column) {

        int[] rowRefresh = new int[rowMax];
        for (int i = rowMax - 1; i >= 0; i--) {
            if (ClientView.shelves.get(ClientView.getPlayerIndex(Gui.username))[i][column] == null) {
                for (int j = 0; j < ClientView.currentPlayerHand.size(); j++) {
                    previewShelf[i][column] = ClientView.currentPlayerHand.get(j);
                    rowRefresh[i]++;
                    i--;
                }
                return rowRefresh;
            }
        }
        return null;

    }

    /**
     * check the available columns number
     *
     * @return vector of number
     */
    public int[] availableColumn() {
        int[] matrix = new int[columnMax];
        for (int i = 0; i < rowMax; i++) {
            for (int j = 0; j < columnMax; j++) {
                if (ClientView.shelves.get(ClientView.getPlayerIndex(Gui.username))[i][j] == null) {
                    matrix[j]++;
                }
            }
        }
        return matrix;
    }

    /**
     * restore the preview matrix
     *
     * @param originalShelf original shelf
     * @return originalShelf
     */
    public String[][] restoreShelf(String[][] originalShelf) {
        String[][] newShelf = new String[rowMax][columnMax];
        for (int i = 0; i < rowMax; i++) {
            for (int j = 0; j < columnMax; j++) {
                newShelf[i][j] = originalShelf[i][j];
            }
        }
        return newShelf;
    }

}