package org.am21.client.view.GUI.component;


import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;
import java.awt.*;


public class LivingRoomPanel extends JPanel {

    //menu TODO: complete
    public JButton livingRoomMenuButton;

    //user me
    public JLabel myLabel;
    public JLabel myShelfBoardLabel;

    //enemy A
    public JLabel enemyALabel;
    public JLabel enemyAShelfBoardLabel;

    //enemy B
    public JLabel enemyBLabel;
    public JLabel enemyBShelfBoardLabel;

    //enemy C
    public JLabel enemyCLabel;
    public JLabel enemyCShelfBoardLabel;

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

    //TODO: my Score


    //TODO:enemy score



    public LivingRoomPanel(){

        //back board panel
        setSize(PixelUtil.pcWidth, PixelUtil.pcHeight);
        setLayout(null);
        setOpaque(false);

        panelBoard = new JLayeredPane();
        panelBoard.setBounds(0,0,PixelUtil.pcWidth,PixelUtil.pcHeight);
        panelBoard.setLayout(null);
        panelBoard.setOpaque(false);
        add(panelBoard);

        //back board label
        backGroundLabel = new JLabel();
        backGroundLabel.setBounds(0,0,PixelUtil.pcWidth,PixelUtil.pcHeight);
        backGroundLabel.setIcon(ImageUtil.getBoardImage("backGround"));
        panelBoard.add(backGroundLabel,JLayeredPane.DEFAULT_LAYER);

        //game board label
        gameBoardLabel = new JLabel();
        gameBoardLabel.setBounds(PixelUtil.commonX_3,PixelUtil.gameBoardY,PixelUtil.gameBoardW,PixelUtil.gameBoardH);
        gameBoardLabel.setIcon(ImageUtil.getBoardImage("gameBoard"));
        panelBoard.add(gameBoardLabel,JLayeredPane.PALETTE_LAYER);

        //my shelf board label
        myShelfBoardLabel = new JLabel();
        myShelfBoardLabel.setBounds(PixelUtil.commonX_4,PixelUtil.myShelfBoardY,PixelUtil.myShelfBoardW,PixelUtil.myShelfBoardH);
        myShelfBoardLabel.setIcon(ImageUtil.getShelfImage(PixelUtil.myShelfBoardW,PixelUtil.myShelfBoardH));
        panelBoard.add(myShelfBoardLabel,JLayeredPane.PALETTE_LAYER);


        //enemy A shelf
        /*enemyAShelfBoardLabel = new JLabel();
        enemyAShelfBoardLabel.setBounds(PixelUtil.commonX_2,PixelUtil.commonY_1,PixelUtil.enemyShelfW,PixelUtil.enemyShelfH);
        enemyAShelfBoardLabel.setIcon(ImageUtil.getShelfImage(PixelUtil.enemyShelfW,PixelUtil.enemyShelfH));
        panelBoard.add(enemyAShelfBoardLabel,JLayeredPane.PALETTE_LAYER);

        //enemy B shelf
        enemyBShelfBoardLabel = new JLabel();
        enemyBShelfBoardLabel.setBounds(PixelUtil.commonX_2,PixelUtil.commonY_2,PixelUtil.enemyShelfW,PixelUtil.enemyShelfH);
        enemyBShelfBoardLabel.setIcon(ImageUtil.getShelfImage(PixelUtil.enemyShelfW,PixelUtil.enemyShelfH));
        panelBoard.add(enemyBShelfBoardLabel,JLayeredPane.PALETTE_LAYER);

        //enemy C shelf
        enemyCShelfBoardLabel = new JLabel();
        enemyCShelfBoardLabel.setBounds(PixelUtil.commonX_2,PixelUtil.commonY_3,PixelUtil.enemyShelfW,PixelUtil.enemyShelfH);
        enemyCShelfBoardLabel.setIcon(ImageUtil.getShelfImage(PixelUtil.enemyShelfW,PixelUtil.enemyShelfH));
        panelBoard.add(enemyCShelfBoardLabel,JLayeredPane.PALETTE_LAYER);*/

        //personal goal card label
        personalGoalLabel = new JLabel();
        personalGoalLabel.setBounds(PixelUtil.personalGoalX,PixelUtil.personalGoalY,PixelUtil.personalGoalCardW, PixelUtil.personalGoalCardH);
        personalGoalLabel.setIcon(ImageUtil.getBoardImage("personalGoalEmpty"));
        panelBoard.add(personalGoalLabel,JLayeredPane.PALETTE_LAYER);

        //common goal A card label
        commonGoalALabel = new JLabel();
        commonGoalALabel.setBounds(PixelUtil.commonX_5,PixelUtil.commonGoalY_A,PixelUtil.commonGoalCardW,PixelUtil.commonGoalCardH);
        commonGoalALabel.setIcon(ImageUtil.getBoardImage("commonGoalEmpty"));
        panelBoard.add(commonGoalALabel,JLayeredPane.PALETTE_LAYER);

        //common goal B card label
        commonGoalBLabel = new JLabel();
        commonGoalBLabel.setBounds(PixelUtil.commonX_5,PixelUtil.commonGoalY_B,PixelUtil.commonGoalCardW,PixelUtil.commonGoalCardH);
        commonGoalBLabel.setIcon(ImageUtil.getBoardImage("commonGoalEmpty"));
        panelBoard.add(commonGoalBLabel,JLayeredPane.PALETTE_LAYER);

        //Bag
        bagLabel = new JLabel();
        bagLabel.setBounds(PixelUtil.commonX_3,PixelUtil.bagY,PixelUtil.bagW,PixelUtil.bagH);
        bagLabel.setIcon(ImageUtil.getBoardImage("bagClose"));
        panelBoard.add(bagLabel,JLayeredPane.PALETTE_LAYER);

        //Menu button
        livingRoomMenuButton = new JButton();
        livingRoomMenuButton.setBounds(PixelUtil.livingRoomMenuX,PixelUtil.livingRoomMenuY,PixelUtil.livingRoomMenuW,PixelUtil.livingRoomMenuH);
        livingRoomMenuButton.setForeground(new Color(164, 91, 9, 255));
        livingRoomMenuButton.setOpaque(false);
        livingRoomMenuButton.setIcon(ImageUtil.getBoardImage("iconMenu"));


        panelBoard.add(livingRoomMenuButton,JLayeredPane.PALETTE_LAYER);

        //Insert input button
        insertButton = new JButton("INSERT");
        insertButton.setFont(new Font("DejaVu Sans",Font.PLAIN,16));
        insertButton.setBounds(PixelUtil.insertButtonX,PixelUtil.commonY_4,PixelUtil.insertClearButtonW,PixelUtil.insertClearButtonH);
        insertButton.setOpaque(true);
        insertButton.setBackground(new Color(4, 134, 10, 230));
        insertButton.setForeground(new Color(4, 134, 10, 230));
        //TODO: actionListener
        panelBoard.add(this.insertButton,JLayeredPane.PALETTE_LAYER);

        //clear selection button
        clearButton = new JButton("CLEAR");
        clearButton.setFont(new Font("DejaVu Sans",Font.PLAIN,16));
        clearButton.setBounds(PixelUtil.clearButtonX,PixelUtil.commonY_4,PixelUtil.insertClearButtonW,PixelUtil.insertClearButtonH);
        clearButton.setOpaque(true);
        clearButton.setBackground(new Color(172, 19, 5, 230));
        clearButton.setForeground(new Color(172, 19, 5, 230));

        //TODO: actionListener
        panelBoard.add(clearButton,JLayeredPane.PALETTE_LAYER);




        //chat box TODO: completed with chat waitingRoomInterface
        chatPanel = new JLayeredPane();
        chatPanel.setBounds(PixelUtil.cPanelX,PixelUtil.cPanelY,PixelUtil.cPanelW,PixelUtil.cPanelH);
        chatPanel.setLayout(null);
        panelBoard.add(chatPanel,JLayeredPane.PALETTE_LAYER);

        chatHistory = new JTextArea();
        chatHistory.setEditable(false);
        chatHistory.setFont(new Font("DejaVu Sans",Font.PLAIN,12));
        //this.chatHistory.setBounds(10,910,340,40);
        chatHistory.setOpaque(false);
        //this.chatHistory.setForeground(new Color(0, 0, 0, 64));
        //this.chatHistory.setBorder(BorderFactory.createLineBorder(new Color(172, 19, 5, 230)));

        scrollHistoryPane = new JScrollPane(chatHistory);
        scrollHistoryPane.setBounds(0,0,PixelUtil.cScrollW,PixelUtil.cScrollH);
        scrollHistoryPane.setOpaque(false);
        scrollHistoryPane.getViewport().setOpaque(false);
        //this.scrollHistoryPane.setBackground(new Color(243, 175, 58, 255));
        scrollHistoryPane.setForeground(new Color(85, 35, 222, 230));
        scrollHistoryPane.setBorder(BorderFactory.createLineBorder(new Color(85, 35, 222, 230)));
        chatPanel.add(scrollHistoryPane,JLayeredPane.MODAL_LAYER);


        messageField = new JTextField();
        messageField.setBounds(0,PixelUtil.cTextFieldY,PixelUtil.cTextFieldW,PixelUtil.cTextFieldH);
        messageField.setFont(new Font("DejaVu Sans",Font.PLAIN,16));
        messageField.setOpaque(true);
        messageField.setForeground(new Color(85, 35, 222, 230));
        messageField.setBorder(BorderFactory.createLineBorder(new Color(85, 35, 222, 230)));
        chatPanel.add(messageField,JLayeredPane.MODAL_LAYER);

        sendButton = new JButton("SEND");
        sendButton.setBounds(PixelUtil.cButtonX,PixelUtil.cButtonY,PixelUtil.cButtonW,PixelUtil.cButtonH);
        sendButton.setFont(new Font("DejaVu Sans",Font.PLAIN,16));
        sendButton.setOpaque(true);
        sendButton.setBackground(new Color(85, 35, 222, 230));
        sendButton.setForeground(new Color(255, 255, 255, 255));
        sendButton.setBorder(BorderFactory.createLineBorder(new Color(85, 35, 222, 230)));
        chatPanel.add(sendButton,JLayeredPane.MODAL_LAYER);




        //take card label
        handBoardLabel = new JLabel();
        handBoardLabel.setBounds(PixelUtil.commonX_4,PixelUtil.commonY_4,PixelUtil.handBoardW,PixelUtil.handBoardH);
        handBoardLabel.setIcon(ImageUtil.getBoardImage("handBoard"));
        panelBoard.add(handBoardLabel,JLayeredPane.PALETTE_LAYER);

        //user Me
        myLabel = new JLabel();
        myLabel.setBounds(PixelUtil.userMeX,PixelUtil.commonY_1,PixelUtil.userMeW,PixelUtil.userMeH);
        myLabel.setIcon(ImageUtil.getBoardImage("iconMe"));
        panelBoard.add(myLabel,JLayeredPane.PALETTE_LAYER);

        //enemy A
        /*enemyALabel = new JLabel();
        enemyALabel.setBounds(PixelUtil.commonX_1,PixelUtil.commonY_1,PixelUtil.enemyW,PixelUtil.enemyH);
        enemyALabel.setIcon(ImageUtil.getBoardImage("enemyA"));
        panelBoard.add(enemyALabel,JLayeredPane.PALETTE_LAYER);

        //enemy B
        enemyBLabel = new JLabel();
        enemyBLabel.setBounds(PixelUtil.commonX_1,PixelUtil.commonY_2,PixelUtil.enemyW,PixelUtil.enemyH);
        enemyBLabel.setIcon(ImageUtil.getBoardImage("enemyB"));
        panelBoard.add(enemyBLabel,JLayeredPane.PALETTE_LAYER);

        //enemy C
        enemyCLabel = new JLabel();
        enemyCLabel.setBounds(PixelUtil.commonX_1,PixelUtil.commonY_3,PixelUtil.enemyW,PixelUtil.enemyH);
        enemyCLabel.setIcon(ImageUtil.getBoardImage("enemyC"));
        panelBoard.add(enemyCLabel,JLayeredPane.PALETTE_LAYER);*/

    }




    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;


    }

}