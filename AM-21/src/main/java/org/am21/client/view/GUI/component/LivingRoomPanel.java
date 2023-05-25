package org.am21.client.view.GUI.component;


import org.am21.client.view.ClientView;
import org.am21.client.view.GUI.Interface.LivingRoomMenuInterface;
import org.am21.client.view.GUI.Interface.MyHandInterface;
import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

import static org.am21.client.SocketClient.gui;


public class LivingRoomPanel extends JPanel {

    public JButton livingRoomMenuButton;

    //user me
    public JLabel myLabel;
    public JLabel myShelfBoardLabel;

    //insert and clear button
    public JButton insertButton;
    public JButton clearButton;


    //background label
    public JLayeredPane panelBoard;
    public JLabel backGroundLabel;

    //game board
    public JLabel gameBoardLabel;


    //personal goal
    public JLabel personalGoalLabel;

    //common goal A
    public JLabel commonGoalALabel;

    //common goal B
    public JLabel commonGoalBLabel;

    //bag
    public JLabel bagLabel;


    //chat box
    public JTextArea chatHistory;
    public JTextField messageField;
    public JScrollPane scrollHistoryPane;
    public JLayeredPane chatPanel;
    public JButton sendButton;

    //hand board
    public JLabel handBoardLabel;

    public JLabel myScoreBand;

    public JLabel myScoreDynamic;


    public LivingRoomPanel() {
        //back board panel
        setSize(PixelUtil.pcWidth, PixelUtil.pcHeight);
        setLayout(null);
        setOpaque(false);

        panelBoard = new JLayeredPane();
        panelBoard.setBounds(0, 0, PixelUtil.pcWidth, PixelUtil.pcHeight);
        panelBoard.setLayout(null);
        panelBoard.setOpaque(false);
        add(panelBoard);

        //back board label
        backGroundLabel = new JLabel();
        backGroundLabel.setBounds(0, 0, PixelUtil.pcWidth, PixelUtil.pcHeight);
        backGroundLabel.setIcon(ImageUtil.getBoardImage("backGround"));
        panelBoard.add(backGroundLabel, JLayeredPane.DEFAULT_LAYER);

        //game board label
        gameBoardLabel = new JLabel();
        gameBoardLabel.setBounds(PixelUtil.commonX_3, PixelUtil.gameBoardY, PixelUtil.gameBoardW, PixelUtil.gameBoardH);
        gameBoardLabel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 255)));
        gameBoardLabel.setIcon(ImageUtil.getBoardImage("gameBoard"));
        panelBoard.add(gameBoardLabel, JLayeredPane.PALETTE_LAYER);

        //my shelf board label
        myShelfBoardLabel = new JLabel();
        myShelfBoardLabel.setBounds(PixelUtil.commonX_4, PixelUtil.myShelfBoardY, PixelUtil.myShelfBoardW, PixelUtil.myShelfBoardH);
        myShelfBoardLabel.setIcon(ImageUtil.getShelfImage(PixelUtil.myShelfBoardW, PixelUtil.myShelfBoardH));
        panelBoard.add(myShelfBoardLabel, JLayeredPane.PALETTE_LAYER);

        //personal goal card label
        personalGoalLabel = new JLabel();
        personalGoalLabel.setBounds(PixelUtil.personalGoalX, PixelUtil.personalGoalY, PixelUtil.personalGoalCardW, PixelUtil.personalGoalCardH);
        personalGoalLabel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 255)));
        personalGoalLabel.setIcon(ImageUtil.getBoardImage("personalGoalEmpty"));
        panelBoard.add(personalGoalLabel, JLayeredPane.PALETTE_LAYER);

        //common goal A card label
        commonGoalALabel = new JLabel();
        commonGoalALabel.setBounds(PixelUtil.commonX_5, PixelUtil.commonGoalY_A, PixelUtil.commonGoalCardW, PixelUtil.commonGoalCardH);
        commonGoalALabel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 255)));
        commonGoalALabel.setIcon(ImageUtil.getBoardImage("commonGoalEmpty"));
        panelBoard.add(commonGoalALabel, JLayeredPane.PALETTE_LAYER);

        //common goal B card label
        commonGoalBLabel = new JLabel();
        commonGoalBLabel.setBounds(PixelUtil.commonX_5, PixelUtil.commonGoalY_B, PixelUtil.commonGoalCardW, PixelUtil.commonGoalCardH);
        commonGoalBLabel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 255)));
        commonGoalBLabel.setIcon(ImageUtil.getBoardImage("commonGoalEmpty"));
        panelBoard.add(commonGoalBLabel, JLayeredPane.PALETTE_LAYER);

        //Bag
        bagLabel = new JLabel();
        bagLabel.setBounds(PixelUtil.commonX_3, PixelUtil.bagY, PixelUtil.bagW, PixelUtil.bagH);
        bagLabel.setIcon(ImageUtil.getBoardImage("bagClose"));
        panelBoard.add(bagLabel, JLayeredPane.PALETTE_LAYER);

        //Menu button
        livingRoomMenuButton = new JButton();
        livingRoomMenuButton.setBounds(PixelUtil.livingRoomMenuX, PixelUtil.livingRoomMenuY, PixelUtil.livingRoomMenuW, PixelUtil.livingRoomMenuH);
        livingRoomMenuButton.setForeground(new Color(164, 91, 9, 255));
        livingRoomMenuButton.setOpaque(false);
        livingRoomMenuButton.setIcon(ImageUtil.getBoardImage("iconMenu"));
        /**
         * open menu interface
         */
        livingRoomMenuButton.addActionListener(e -> {
            LivingRoomMenuInterface livingRoomMenuInterface = new LivingRoomMenuInterface();
            livingRoomMenuInterface.setVisible(true);
        });

        panelBoard.add(livingRoomMenuButton, JLayeredPane.PALETTE_LAYER);

        //Insert input button
        insertButton = new JButton("INSERT");
        insertButton.setFont(new Font("DejaVu Sans", Font.PLAIN, 16));
        insertButton.setBounds(PixelUtil.insertButtonX, PixelUtil.commonY_4, PixelUtil.insertClearButtonW, PixelUtil.insertClearButtonH);
        insertButton.setBorder(new MatteBorder(ImageUtil.resizeY(2), ImageUtil.resizeX(2), ImageUtil.resizeY(2),
                ImageUtil.resizeX(2), new Color(4, 134, 10, 230)));
        insertButton.setUI(new ButtonColorUI(new Color(136, 218, 123, 139)));
        insertButton.setBackground(Color.WHITE);
        insertButton.setForeground(new Color(4, 134, 10, 230));
        /**
         *open my hand interface
         */
        insertButton.addActionListener(e -> {
            MyHandInterface myHandInterface = new MyHandInterface();
            myHandInterface.setVisible(true);
        });
        panelBoard.add(insertButton, JLayeredPane.PALETTE_LAYER);

        //clear selection button
        clearButton = new JButton("CLEAR");
        clearButton.setFont(new Font("DejaVu Sans", Font.PLAIN, 16));
        clearButton.setBounds(PixelUtil.clearButtonX, PixelUtil.commonY_4, PixelUtil.insertClearButtonW, PixelUtil.insertClearButtonH);
        clearButton.setBorder(new MatteBorder(ImageUtil.resizeY(2), ImageUtil.resizeX(2), ImageUtil.resizeY(2),
                ImageUtil.resizeX(2), new Color(178, 34, 34)));
        clearButton.setUI(new ButtonColorUI(new Color(255, 181, 172, 139)));
        clearButton.setBackground(Color.WHITE);
        clearButton.setForeground(new Color(172, 19, 5, 230));
        clearButton.addActionListener(e -> {
            if(gui.commCtrl.deselectCards())
            {
                gui.myHandBoardPanel.refreshItem(ClientView.currentPlayerHand);
                gui.gameBoardPanel.clearAll();
                JOptionPane.showMessageDialog(null, "clear successful");
            }

        });
        panelBoard.add(clearButton, JLayeredPane.PALETTE_LAYER);


        //chat box TODO: completed with chat waitingRoomInterface
        chatPanel = new JLayeredPane();
        chatPanel.setBounds(PixelUtil.cPanelX, PixelUtil.cPanelY, PixelUtil.cPanelW, PixelUtil.cPanelH);
        chatPanel.setLayout(null);
        panelBoard.add(chatPanel, JLayeredPane.PALETTE_LAYER);

        chatHistory = new JTextArea();
        chatHistory.setEditable(false);
        chatHistory.setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
        //this.currentChatHistory.setBounds(10,910,340,40);
        chatHistory.setOpaque(false);
        //this.currentChatHistory.setForeground(new Color(0, 0, 0, 64));
        //this.currentChatHistory.setBorder(BorderFactory.createLineBorder(new Color(172, 19, 5, 230)));

        scrollHistoryPane = new JScrollPane(chatHistory);
        scrollHistoryPane.setBounds(0, 0, PixelUtil.cScrollW, PixelUtil.cScrollH);
        scrollHistoryPane.setOpaque(false);
        scrollHistoryPane.getViewport().setOpaque(false);
        //this.scrollHistoryPane.setBackground(new Color(243, 175, 58, 255));
        scrollHistoryPane.setForeground(new Color(85, 35, 222, 230));
        scrollHistoryPane.setBorder(BorderFactory.createLineBorder(new Color(85, 35, 222, 230)));
        chatPanel.add(scrollHistoryPane, JLayeredPane.MODAL_LAYER);


        messageField = new JTextField();
        messageField.setBounds(0, PixelUtil.cTextFieldY, PixelUtil.cTextFieldW, PixelUtil.cTextFieldH);
        messageField.setFont(new Font("DejaVu Sans", Font.PLAIN, 16));
        messageField.setOpaque(true);
        messageField.setForeground(new Color(85, 35, 222, 230));
        messageField.setBackground(new Color(255, 255, 255, 255));
        chatPanel.add(messageField, JLayeredPane.MODAL_LAYER);

        sendButton = new JButton("SEND");
        sendButton.setBounds(PixelUtil.cButtonX, PixelUtil.cButtonY, PixelUtil.cButtonW, PixelUtil.cButtonH);
        sendButton.setFont(new Font("DejaVu Sans", Font.PLAIN, 16));
        sendButton.setOpaque(true);
        sendButton.setBackground(new Color(85, 35, 222, 230));
        sendButton.setForeground(new Color(255, 255, 255, 255));
        sendButton.setBorder(new LineBorder(new Color(85, 35, 222, 230)));
        chatPanel.add(sendButton, JLayeredPane.MODAL_LAYER);

        //my hand label
        handBoardLabel = new JLabel();
        handBoardLabel.setBounds(PixelUtil.commonX_4, PixelUtil.commonY_4, PixelUtil.handBoardW, PixelUtil.handBoardH);
        handBoardLabel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 255)));
        handBoardLabel.setIcon(ImageUtil.getBoardImage("handBoard"));
        panelBoard.add(handBoardLabel, JLayeredPane.PALETTE_LAYER);

        //user Me
        myLabel = new JLabel();
        myLabel.setBounds(PixelUtil.userMeX, PixelUtil.commonY_1, PixelUtil.userMeW, PixelUtil.userMeH);
        myLabel.setIcon(ImageUtil.getBoardImage("iconMe"));
        panelBoard.add(myLabel, JLayeredPane.PALETTE_LAYER);

        //my score
        myScoreBand = new JLabel("Score:");
        myScoreBand.setBounds(PixelUtil.commonX_5, PixelUtil.livingRoomMenuY, PixelUtil.myScoreW, PixelUtil.myScoreH);
        myScoreBand.setFont(new Font("DejaVu Sans", Font.PLAIN, 30));
        myScoreBand.setForeground(new Color(85, 35, 222, 230));
        panelBoard.add(myScoreBand, JLayeredPane.PALETTE_LAYER);

        //my score dynamic view
        myScoreDynamic = new JLabel("99");
        myScoreDynamic.setBounds(PixelUtil.myScoreDynamicX, PixelUtil.commonY_1, PixelUtil.myScoreDynamicW, PixelUtil.myScoreDynamicH);
        myScoreDynamic.setFont(new Font("DejaVu Sans", Font.PLAIN, 30));
        myScoreDynamic.setForeground(new Color(0, 0, 0, 255));
        panelBoard.add(myScoreDynamic, JLayeredPane.PALETTE_LAYER);


    }


}
