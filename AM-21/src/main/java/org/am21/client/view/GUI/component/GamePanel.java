package org.am21.client.view.GUI.component;


import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;
import java.awt.*;


public class GamePanel extends JPanel {

    //menu TODO: complete
    public JButton livingRoomMenuLabel;

    //user me
    public JLabel userMe;
    public JLabel myShelfBoardLabel;

    //enemy A
    public JLabel enemyA;
    public JLabel enemyAShelfBoard;

    //enemy B
    public JLabel enemyB;
    public JLabel enemyBShelfBoard;

    //enemy C
    public JLabel enemyC;
    public JLabel enemyCShelfBoard;

    //insert and clear button
    public JButton jbInsert;
    public JButton jbClear;


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
    public JLabel handBoard;

    //TODO: my Score


    //TODO:enemy score


    //TODO: charMan enemy A,B,C

    //TODO: charMan me

    public GamePanel(){

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
        /*TODO: add Listener event*/
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
        enemyAShelfBoard = new JLabel();
        enemyAShelfBoard.setBounds(PixelUtil.commonX_2,PixelUtil.commonY_1,PixelUtil.enemyShelfW,PixelUtil.enemyShelfH);
        enemyAShelfBoard.setIcon(ImageUtil.getShelfImage(PixelUtil.enemyShelfW,PixelUtil.enemyShelfH));
        panelBoard.add(enemyAShelfBoard,JLayeredPane.PALETTE_LAYER);

        //enemy B shelf
        enemyBShelfBoard = new JLabel();
        enemyBShelfBoard.setBounds(PixelUtil.commonX_2,PixelUtil.commonY_2,PixelUtil.enemyShelfW,PixelUtil.enemyShelfH);
        enemyBShelfBoard.setIcon(ImageUtil.getShelfImage(PixelUtil.enemyShelfW,PixelUtil.enemyShelfH));
        panelBoard.add(enemyBShelfBoard,JLayeredPane.PALETTE_LAYER);

        //enemy C shelf
        enemyCShelfBoard = new JLabel();
        enemyCShelfBoard.setBounds(PixelUtil.commonX_2,PixelUtil.commonY_3,PixelUtil.enemyShelfW,PixelUtil.enemyShelfH);
        enemyCShelfBoard.setIcon(ImageUtil.getShelfImage(PixelUtil.enemyShelfW,PixelUtil.enemyShelfH));
        panelBoard.add(enemyCShelfBoard,JLayeredPane.PALETTE_LAYER);

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

        //option menu
        livingRoomMenuLabel = new JButton();
        //this.jmMenu.setFont(new Font("DejaVu Sans",Font.PLAIN,12));
        livingRoomMenuLabel.setBounds(PixelUtil.livingRoomMenuX,PixelUtil.livingRoomMenuY,PixelUtil.livingRoomMenuW,PixelUtil.livingRoomMenuH);
        livingRoomMenuLabel.setForeground(new Color(164, 91, 9, 255));
        livingRoomMenuLabel.setOpaque(false);
        livingRoomMenuLabel.setIcon(ImageUtil.getBoardImage("iconMenu"));

        /*this.jmiRule = jmMenu.add("Rule");
        this.jmiRule.setFont(new Font("DejaVu Sans",Font.PLAIN,12));
        //TODO:add Action listner
        this.jmMenu.add(this.jmiRule,JLayeredPane.PALETTE_LAYER);

        this.jmiHelp = jmMenu.add("Help");
        this.jmiHelp.setFont(new Font("DejaVu Sans",Font.PLAIN,12));
        //TODO:add Action listner
        this.jmMenu.add(this.jmiHelp,JLayeredPane.PALETTE_LAYER);

        this.jmiLeave = jmMenu.add("Leave");
        this.jmiLeave.setFont(new Font("DejaVu Sans",Font.PLAIN,12));
        //TODO:add Action listner
        this.jmMenu.add(this.jmiHelp,JLayeredPane.PALETTE_LAYER);
*/
        panelBoard.add(livingRoomMenuLabel,JLayeredPane.PALETTE_LAYER);

        //Insert input button
        jbInsert = new JButton("INSERT");
        jbInsert.setFont(new Font("DejaVu Sans",Font.PLAIN,16));
        jbInsert.setBounds(PixelUtil.insertButtonX,PixelUtil.commonY_4,PixelUtil.insertClearButtonW,PixelUtil.insertClearButtonH);
        jbInsert.setOpaque(true);
        jbInsert.setBackground(new Color(4, 134, 10, 230));
        jbInsert.setForeground(new Color(4, 134, 10, 230));
        //TODO: actionListener
        panelBoard.add(this.jbInsert,JLayeredPane.PALETTE_LAYER);

        //clear selection button
        jbClear = new JButton("CLEAR");
        jbClear.setFont(new Font("DejaVu Sans",Font.PLAIN,16));
        jbClear.setBounds(PixelUtil.clearButtonX,PixelUtil.commonY_4,PixelUtil.insertClearButtonW,PixelUtil.insertClearButtonH);
        jbClear.setOpaque(true);
        jbClear.setBackground(new Color(172, 19, 5, 230));
        jbClear.setForeground(new Color(172, 19, 5, 230));

        //TODO: actionListener
        panelBoard.add(jbClear,JLayeredPane.PALETTE_LAYER);




        //chat box
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
        //this.messagePanel.add(this.sendButton,JLayeredPane.MODAL_LAYER);
        /*



        this.messagePanel.setLayout(new BorderLayout());




        this.messagePanel.add(messageField,BorderLayout.CENTER);
        this.messagePanel.add(sendButton,BorderLayout.EAST);
        this.panelBoard.add(messagePanel,BorderLayout.SOUTH);*/




        //take card label
        handBoard = new JLabel();
        handBoard.setBounds(PixelUtil.commonX_4,PixelUtil.commonY_4,PixelUtil.handBoardW,PixelUtil.handBoardH);
        handBoard.setIcon(ImageUtil.getBoardImage("handBoard"));
        panelBoard.add(handBoard,JLayeredPane.PALETTE_LAYER);

        //user Me
        userMe = new JLabel();
        userMe.setBounds(PixelUtil.userMeX,PixelUtil.commonY_1,PixelUtil.userMeW,PixelUtil.userMeH);
        userMe.setIcon(ImageUtil.getBoardImage("iconMe"));
        panelBoard.add(userMe,JLayeredPane.PALETTE_LAYER);

        //enemy A
        enemyA = new JLabel();
        enemyA.setBounds(PixelUtil.commonX_1,PixelUtil.commonY_1,PixelUtil.enemyW,PixelUtil.enemyH);
        enemyA.setIcon(ImageUtil.getBoardImage("enemyA"));
        panelBoard.add(enemyA,JLayeredPane.PALETTE_LAYER);

        //enemy B
        enemyB = new JLabel();
        enemyB.setBounds(PixelUtil.commonX_1,PixelUtil.commonY_2,PixelUtil.enemyW,PixelUtil.enemyH);
        enemyB.setIcon(ImageUtil.getBoardImage("enemyB"));
        panelBoard.add(enemyB,JLayeredPane.PALETTE_LAYER);

        //enemy C
        enemyC = new JLabel();
        enemyC.setBounds(PixelUtil.commonX_1,PixelUtil.commonY_3,PixelUtil.enemyW,PixelUtil.enemyH);
        enemyC.setIcon(ImageUtil.getBoardImage("enemyC"));
        panelBoard.add(enemyC,JLayeredPane.PALETTE_LAYER);

    }




    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;


    }

}
