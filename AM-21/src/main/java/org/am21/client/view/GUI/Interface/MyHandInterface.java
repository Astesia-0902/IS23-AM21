package org.am21.client.view.GUI.Interface;

import org.am21.client.view.GUI.component.ButtonColorUI;
import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MyHandInterface extends JFrame {
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

    public MyHandInterface() {
         setSize(PixelUtil.myHandBackGroundW,PixelUtil.myHandBackGroundH);
         setLocation(PixelUtil.myHandBackGroundX, PixelUtil.myHandBackGroundY);
        //setBounds(PixelUtil.myHandBackGroundX, PixelUtil.myHandBackGroundY, PixelUtil.myHandBackGroundW, PixelUtil.myHandBackGroundH);
        setUndecorated(true);
        setResizable(false);
        setTitle("My hand");


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


        for (int i = 0; i < handMax; i++) {
            handGrid[i] = new JLayeredPane();
            handGrid[i].setBounds(0, i * ((PixelUtil.myHandHandH) / 3), PixelUtil.myHandHandW, (PixelUtil.myHandHandH) / 3);
            handGrid[i].setLayout(null);

            add(handGrid[i]);
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

    }

    public void refreshItem(List<String> myItem) {

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
            handGrid[i].add(myHandItem[i], JLayeredPane.PALETTE_LAYER);

        }

        revalidate();
        repaint();
    }


}