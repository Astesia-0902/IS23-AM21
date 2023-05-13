package org.am21.client.view.GUI.Interface;

import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyHandInterface extends JFrame {

    public JLayeredPane myHandPane;
    public JLabel myHandBack;
    public JLabel myHandHand;
    public JLabel myShelfBoardLabel;
    public JButton sort;
    public ButtonGroup optionGroup;
    public JButton confirm;

    public MyHandInterface() {
        setBounds(PixelUtil.myHandBackGroundX, PixelUtil.myHandBackGroundY, PixelUtil.myHandBackGroundW,PixelUtil.myHandBackGroundH);
        setResizable(false);
        setTitle("My hand");


        myHandPane = new JLayeredPane();
        myHandPane.setBounds(0,0, PixelUtil.myHandBackGroundW,PixelUtil.myHandBackGroundH);
        myHandPane.setLayout(null);
        myHandPane.setOpaque(false);
        add(myHandPane);

        //background
        myHandBack = new JLabel();
        myHandBack.setBounds(0,0,PixelUtil.myHandBackGroundW,PixelUtil.myHandBackGroundH);
        myHandBack.setIcon(ImageUtil.getBoardImage("myHandBack"));
        myHandPane.add(myHandBack,JLayeredPane.DEFAULT_LAYER);

        myHandHand = new JLabel();
        myHandHand.setBounds(PixelUtil.myHandHandX,PixelUtil.myHandHandY,PixelUtil.myHandHandW,PixelUtil.myHandHandH);
        myHandHand.setIcon(ImageUtil.getBoardImage("myHandHand"));
        myHandPane.add(myHandHand,JLayeredPane.PALETTE_LAYER);

        myShelfBoardLabel = new JLabel();
        myShelfBoardLabel.setBounds(PixelUtil.myHandShelfX,PixelUtil.myHandHandY,PixelUtil.myShelfBoardW,PixelUtil.myShelfBoardH);
        myShelfBoardLabel.setIcon(ImageUtil.getShelfImage(PixelUtil.myShelfBoardW,PixelUtil.myShelfBoardH));
        myHandPane.add(myShelfBoardLabel,JLayeredPane.PALETTE_LAYER);

        sort = new JButton();
        sort.setBounds(PixelUtil.myHandSortX,PixelUtil.myHandSortY,PixelUtil.myHandSortW,PixelUtil.myHandSortH);
        sort.setForeground(new Color(164, 91, 9, 255));
        sort.setOpaque(false);
        sort.setIcon(ImageUtil.getBoardImage("iconSort"));
        myHandPane.add(sort,JLayeredPane.PALETTE_LAYER);

        optionGroup = new ButtonGroup();
        for(int i=1; i<=5;i++){
            JRadioButton radioButton = new JRadioButton();
            radioButton.setIcon(ImageUtil.getNumberImage(i+"gray"));
            radioButton.setBounds(PixelUtil.myHandOptionX+i*PixelUtil.myHandOptionXDiff,PixelUtil.myHandOptionY,PixelUtil.handNumW,PixelUtil.handNumH);
            radioButton.setOpaque(false);
            optionGroup.add(radioButton);
            myHandPane.add(radioButton,JLayeredPane.PALETTE_LAYER);
        }

        confirm = new JButton("CONFIRM");
        confirm.setFont(new Font("DejaVu Sans",Font.PLAIN,16));
        confirm.setBounds(PixelUtil.myHandConfirmX,PixelUtil.myHandConfirmY,PixelUtil.myHandConfirmW,PixelUtil.myHandConfirmH);
        confirm.setForeground(new Color(4, 134, 10, 230));
        confirm.setOpaque(true);
        confirm.setBackground(new Color(4, 134, 10, 230));
        confirm.setForeground(new Color(4, 134, 10, 230));
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window window = SwingUtilities.windowForComponent(confirm);
                if(confirm != null)
                {
                    //TODO: insert the item
                    window.dispose();
                }

                JOptionPane.showMessageDialog(null, "insertion successful");

            }
        });
        myHandPane.add(confirm,JLayeredPane.PALETTE_LAYER);

    }


}