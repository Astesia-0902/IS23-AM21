package org.am21.client.view.GUI.Interface;

import org.am21.client.view.ClientView;
import org.am21.client.view.GUI.Gui;
import org.am21.client.view.GUI.component.ButtonColorUI;
import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MyHandInterface extends JFrame {
    Gui gui;
    public int handMax = 3;
    public JLayeredPane myHandInterfacePane;
    public JLabel myHandInterfaceBack;
    public JLabel myHandLabel;
    public JLayeredPane[] handGrid = new JLayeredPane[handMax];
    public JLabel[] myHandItem;


    public JLabel myShelfBoardLabel;
    public JButton sort;
    public ButtonGroup optionGroup;
    public JButton confirm;
    public JButton backToSelect;
    public List<Integer> posSort = new ArrayList<>();

    public MyHandInterface() {
         //setSize(PixelUtil.myHandBackGroundW,PixelUtil.myHandBackGroundH);
         //setLocationRelativeTo(null);
        setBounds(PixelUtil.myHandBackGroundX, PixelUtil.myHandBackGroundY, PixelUtil.myHandBackGroundW, PixelUtil.myHandBackGroundH);
        //setBounds(0,0, PixelUtil.myHandBackGroundW, PixelUtil.myHandBackGroundH);

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

        myHandLabel = new JLabel();
        myHandLabel.setBounds(PixelUtil.myHandHandX, PixelUtil.myHandHandY, PixelUtil.myHandHandW, PixelUtil.myHandHandH);
        myHandLabel.setIcon(ImageUtil.getBoardImage("myHandHand"));
        myHandInterfacePane.add(myHandLabel, JLayeredPane.PALETTE_LAYER);


        //hand grids
        for (int i = 0; i < handMax; i++) {
            handGrid[i] = new JLayeredPane();
            handGrid[i].setBounds(PixelUtil.myHandHandX, PixelUtil.myHandHandY+(i * ((PixelUtil.myHandHandH) / 3)), PixelUtil.myHandHandW, (PixelUtil.myHandHandH) / 3);
            handGrid[i].setLayout(null);
            handGrid[i].setBackground(Color.WHITE);
            myHandInterfacePane.add(handGrid[i],JLayeredPane.MODAL_LAYER);
        }




        myShelfBoardLabel = new JLabel();
        myShelfBoardLabel.setBounds(PixelUtil.myHandShelfX, PixelUtil.myHandHandY, PixelUtil.myShelfBoardW, PixelUtil.myShelfBoardH);
        myShelfBoardLabel.setIcon(ImageUtil.getShelfImage(PixelUtil.myShelfBoardW, PixelUtil.myShelfBoardH));
        myHandInterfacePane.add(myShelfBoardLabel, JLayeredPane.PALETTE_LAYER);


        sort = new JButton();
        sort.setBounds(PixelUtil.myHandSortX, PixelUtil.myHandSortY, PixelUtil.myHandSortW, PixelUtil.myHandSortH);
        sort.setForeground(new Color(164, 91, 9, 255));
        sort.setOpaque(false);
        sort.setIcon(ImageUtil.getBoardImage("iconSort"));
        if(posSort.size()==2)
        {
            sort.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gui.commCtrl.sortHand(posSort.get(0),posSort.get(1));
                    refreshItem(ClientView.currentPlayerHand,gui); //refresh new board
                    posSort.clear(); //clear list

                }
            });
        }

        myHandInterfacePane.add(sort, JLayeredPane.PALETTE_LAYER);

        optionGroup = new ButtonGroup();
        for (int i = 1; i <= 5; i++) {
            JRadioButton radioButton = new JRadioButton();
            radioButton.setIcon(ImageUtil.getNumberImage(i + "gray"));
            radioButton.setBounds(PixelUtil.myHandOptionX + i * PixelUtil.myHandOptionXDiff, PixelUtil.myHandOptionY, PixelUtil.handNumW, PixelUtil.handNumH);
            radioButton.setOpaque(false);
            optionGroup.add(radioButton);
            myHandInterfacePane.add(radioButton, JLayeredPane.PALETTE_LAYER);
        }

       /* backToSelect = new JButton("BACK");
        backToSelect.setFont(new Font("DejaVu Sans", Font.PLAIN, 16));
        backToSelect.setBounds(PixelUtil.myHandConfirmX, PixelUtil.myHandBackY, PixelUtil.myHandConfirmW, PixelUtil.myHandConfirmH);
        backToSelect.setBorder(new MatteBorder(ImageUtil.resizeY(2), ImageUtil.resizeX(2), ImageUtil.resizeY(2),
                ImageUtil.resizeX(2), new Color(172, 19, 5, 230)));
        backToSelect.setUI(new ButtonColorUI(new Color(182, 150, 146, 230)));
        backToSelect.setBackground(Color.WHITE);
        backToSelect.setForeground(new Color(172, 19, 5, 230));
        myHandInterfacePane.add(backToSelect, JLayeredPane.PALETTE_LAYER);*/

        confirm = new JButton("CONFIRM");
        confirm.setFont(new Font("DejaVu Sans", Font.PLAIN, 16));
        confirm.setBounds(PixelUtil.myHandConfirmX, PixelUtil.myHandConfirmY, PixelUtil.myHandConfirmW, PixelUtil.myHandConfirmH);
        confirm.setBorder(new MatteBorder(ImageUtil.resizeY(2), ImageUtil.resizeX(2), ImageUtil.resizeY(2),
                ImageUtil.resizeX(2), new Color(4, 134, 10, 230)));
        confirm.setUI(new ButtonColorUI(new Color(136, 218, 123, 139)));
        confirm.setBackground(Color.WHITE);
        confirm.setForeground(new Color(4, 134, 10, 230));
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window window = SwingUtilities.windowForComponent(confirm);
                if (confirm != null) {
                    //TODO: insert the item
                    window.dispose();
                }

                JOptionPane.showMessageDialog(null, "insertion successful");

            }
        });
        myHandInterfacePane.add(confirm, JLayeredPane.PALETTE_LAYER);


        setVisible(true);

    }

    public void refreshItem(List<String> myItem,Gui gui) {

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

            actionItem(i,gui);
            handGrid[i].add(myHandItem[i], JLayeredPane.PALETTE_LAYER);

        }

        revalidate();
        repaint();
    }

    public void actionItem(int column, Gui gui){
        myHandItem[column].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //super.mouseClicked(e);
                Border border = myHandItem[column].getBorder();
                if (border instanceof LineBorder) {
                    Color edgeColor = ((LineBorder) border).getLineColor(); //get item border color
                    //do select
                    if (edgeColor.equals(new Color(0, 0, 0, 255))&&posSort.size()<2) {
                        myHandItem[column].setBorder(BorderFactory.createLineBorder(new Color(203, 63, 4, 230), 4));
                        posSort.add(column);

                    } else if (edgeColor.equals(new Color(203, 63, 4, 230))) {
                        //do deselect
                        myHandItem[column].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 255)));
                        posSort.remove(column);
                    }


                }
                revalidate();
                repaint();
            }
        });
    }
    


    public static void main(String[] args) {
        MyHandInterface my = new MyHandInterface();
        List<String> stringList = new ArrayList<>();

        stringList.add("_Games__1.1");
        stringList.add("_Plants_1.3");
        stringList.add("Trophies1.3");
        //my.refreshItem(stringList);

    }
}