package org.am21.client.view.GUI.component;


import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;
import java.awt.*;


public class GamePanel extends JPanel {

    //menu TODO: complete
    public JButton jmMenu;

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

    public GamePanel(){

        //back board panel
        this.setSize(PixelUtil.pcWidth, PixelUtil.pcHeight);
        this.setLayout(null);
        this.setOpaque(false);

        this.panelBoard = new JLayeredPane();
        this.panelBoard.setBounds(0,0,PixelUtil.pcWidth,PixelUtil.pcHeight);
        this.panelBoard.setLayout(null);
        this.panelBoard.setOpaque(false);
        this.add(this.panelBoard);

        //back board label
        this.backGroundLabel = new JLabel();
        this.backGroundLabel.setBounds(0,0,PixelUtil.pcWidth,PixelUtil.pcHeight);
        this.backGroundLabel.setIcon(ImageUtil.getBoardImage("backGround"));
        /*TODO: add Listener event*/
        this.panelBoard.add(this.backGroundLabel,JLayeredPane.DEFAULT_LAYER);

        //game board label
        this.gameBoardLabel = new JLabel();
        this.gameBoardLabel.setBounds(PixelUtil.commonX_3,PixelUtil.gameBoardY,PixelUtil.gameBoardHW,PixelUtil.gameBoardHW);
        this.gameBoardLabel.setIcon(ImageUtil.getBoardImage("gameBoard"));
        this.panelBoard.add(this.gameBoardLabel,JLayeredPane.PALETTE_LAYER);


        //my shelf board label
        this.myShelfBoardLabel = new JLabel();
        this.myShelfBoardLabel.setBounds(PixelUtil.commonX_4,PixelUtil.myShelfBoardY,PixelUtil.myShelfBoardHW,PixelUtil.myShelfBoardHW);
        this.myShelfBoardLabel.setIcon(ImageUtil.getShelfImage(PixelUtil.myShelfBoardHW));
        this.panelBoard.add(this.myShelfBoardLabel,JLayeredPane.PALETTE_LAYER);


        //enemy A shelf
        this.enemyAShelfBoard = new JLabel();
        this.enemyAShelfBoard.setBounds(PixelUtil.commonX_2,PixelUtil.commonY_1,PixelUtil.enemyShelfHW,PixelUtil.enemyShelfHW);
        this.enemyAShelfBoard.setIcon(ImageUtil.getShelfImage(PixelUtil.enemyShelfHW));
        this.panelBoard.add(this.enemyAShelfBoard,JLayeredPane.PALETTE_LAYER);

        //enemy B shelf
        this.enemyBShelfBoard = new JLabel();
        this.enemyBShelfBoard.setBounds(PixelUtil.commonX_2,PixelUtil.commonY_2,PixelUtil.enemyShelfHW,PixelUtil.enemyShelfHW);
        this.enemyBShelfBoard.setIcon(ImageUtil.getShelfImage(PixelUtil.enemyShelfHW));
        this.panelBoard.add(this.enemyBShelfBoard,JLayeredPane.PALETTE_LAYER);

        //enemy C shelf
        this.enemyCShelfBoard = new JLabel();
        this.enemyCShelfBoard.setBounds(PixelUtil.commonX_2,PixelUtil.commonY_3,PixelUtil.enemyShelfHW,PixelUtil.enemyShelfHW);
        this.enemyCShelfBoard.setIcon(ImageUtil.getShelfImage(PixelUtil.enemyShelfHW));
        this.panelBoard.add(this.enemyCShelfBoard,JLayeredPane.PALETTE_LAYER);

        //personal goal card label
        this.personalGoalLabel = new JLabel();
        this.personalGoalLabel.setBounds(PixelUtil.personalGoalX,PixelUtil.personalGoalY,PixelUtil.goalCardH,PixelUtil.goalCardW);
        this.personalGoalLabel.setIcon(ImageUtil.getBoardImage("personalGoalEmpty"));
        this.panelBoard.add(this.personalGoalLabel,JLayeredPane.PALETTE_LAYER);

        //common goal A card label
        this.commonGoalALabel = new JLabel();
        this.commonGoalALabel.setBounds(PixelUtil.commonX_5,PixelUtil.commonGoalY_A,PixelUtil.goalCardW,PixelUtil.goalCardH);
        this.commonGoalALabel.setIcon(ImageUtil.getBoardImage("commonGoalEmpty"));
        this.panelBoard.add(this.commonGoalALabel,JLayeredPane.PALETTE_LAYER);

        //common goal B card label
        this.commonGoalBLabel = new JLabel();
        this.commonGoalBLabel.setBounds(PixelUtil.commonX_5,PixelUtil.commonGoalY_B,PixelUtil.goalCardW,PixelUtil.goalCardH);
        this.commonGoalBLabel.setIcon(ImageUtil.getBoardImage("commonGoalEmpty"));
        this.panelBoard.add(this.commonGoalBLabel,JLayeredPane.PALETTE_LAYER);

        //Bag
        this.bagLabel = new JLabel();
        this.bagLabel.setBounds(PixelUtil.commonX_3,PixelUtil.bagY,PixelUtil.bagHW,PixelUtil.bagHW);
        this.bagLabel.setIcon(ImageUtil.getBoardImage("bagClose"));
        this.panelBoard.add(this.bagLabel,JLayeredPane.PALETTE_LAYER);

        //option menu
        this.jmMenu = new JButton();
        //this.jmMenu.setFont(new Font("DejaVu Sans",Font.PLAIN,12));
        this.jmMenu.setBounds(PixelUtil.livingRoomMenuX,PixelUtil.livingRoomMenuY,PixelUtil.livingRoomMenuHW,PixelUtil.livingRoomMenuHW);
        this.jmMenu.setForeground(new Color(164, 91, 9, 255));
        this.jmMenu.setOpaque(false);
        this.jmMenu.setIcon(ImageUtil.getBoardImage("iconMenu"));

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
        this.panelBoard.add(this.jmMenu,JLayeredPane.PALETTE_LAYER);

        //Insert input button
        this.jbInsert = new JButton("INSERT");
        this.jbInsert.setFont(new Font("DejaVu Sans",Font.PLAIN,16));
        this.jbInsert.setBounds(PixelUtil.insertButtonX,PixelUtil.commonY_4,PixelUtil.insertClearButtonW,PixelUtil.bottomButtonH);
        this.jbInsert.setOpaque(true);
        this.jbInsert.setBackground(new Color(4, 134, 10, 230));
        this.jbInsert.setForeground(new Color(4, 134, 10, 230));
        //TODO: actionListener
        this.panelBoard.add(this.jbInsert,JLayeredPane.PALETTE_LAYER);

        //clear selection button
        this.jbClear = new JButton("CLEAR");
        this.jbClear.setFont(new Font("DejaVu Sans",Font.PLAIN,16));
        this.jbClear.setBounds(PixelUtil.clearButtonX,PixelUtil.commonY_4,PixelUtil.insertClearButtonW,PixelUtil.bottomButtonH);
        this.jbClear.setOpaque(true);
        this.jbClear.setBackground(new Color(172, 19, 5, 230));
        this.jbClear.setForeground(new Color(172, 19, 5, 230));

        //TODO: actionListener
        this.panelBoard.add(this.jbClear,JLayeredPane.PALETTE_LAYER);




        //chat box
        this.chatPanel = new JLayeredPane();
        this.chatPanel.setBounds(PixelUtil.cPanelX,PixelUtil.cPanelY,PixelUtil.cPanelW,PixelUtil.cPanelH);
        this.chatPanel.setLayout(null);

        this.panelBoard.add(this.chatPanel,JLayeredPane.PALETTE_LAYER);


        this.chatHistory = new JTextArea();
        this.chatHistory.setEditable(false);
        this.chatHistory.setFont(new Font("DejaVu Sans",Font.PLAIN,12));
        //this.chatHistory.setBounds(10,910,340,40);
        this.chatHistory.setOpaque(false);
        //this.chatHistory.setForeground(new Color(0, 0, 0, 64));
        //this.chatHistory.setBorder(BorderFactory.createLineBorder(new Color(172, 19, 5, 230)));

        this.scrollHistoryPane = new JScrollPane(this.chatHistory);
        this.scrollHistoryPane.setBounds(0,0,PixelUtil.cScrollW,PixelUtil.cScrollH);
        this.scrollHistoryPane.setOpaque(false);
        this.scrollHistoryPane.getViewport().setOpaque(false);
        //this.scrollHistoryPane.setBackground(new Color(243, 175, 58, 255));
        this.scrollHistoryPane.setForeground(new Color(85, 35, 222, 230));
        this.scrollHistoryPane.setBorder(BorderFactory.createLineBorder(new Color(85, 35, 222, 230)));
        this.chatPanel.add(this.scrollHistoryPane,JLayeredPane.MODAL_LAYER);


        this.messageField = new JTextField();
        this.messageField.setBounds(0,PixelUtil.cTextFieldY,PixelUtil.cTextFieldW,PixelUtil.cTextFieldH);
        this.messageField.setFont(new Font("DejaVu Sans",Font.PLAIN,16));
        this.messageField.setOpaque(true);
        this.messageField.setForeground(new Color(85, 35, 222, 230));
        this.messageField.setBorder(BorderFactory.createLineBorder(new Color(85, 35, 222, 230)));
        this.chatPanel.add(this.messageField,JLayeredPane.MODAL_LAYER);

        this.sendButton = new JButton("SEND");
        this.sendButton.setBounds(PixelUtil.cButtonX,PixelUtil.cButtonY,PixelUtil.cButtonW,PixelUtil.cButtonH);
        this.sendButton.setFont(new Font("DejaVu Sans",Font.PLAIN,16));
        this.sendButton.setOpaque(true);
        this.sendButton.setBackground(new Color(85, 35, 222, 230));
        this.sendButton.setForeground(new Color(255, 255, 255, 255));
        this.sendButton.setBorder(BorderFactory.createLineBorder(new Color(85, 35, 222, 230)));
        this.chatPanel.add(this.sendButton,JLayeredPane.MODAL_LAYER);
        //this.messagePanel.add(this.sendButton,JLayeredPane.MODAL_LAYER);
        /*



        this.messagePanel.setLayout(new BorderLayout());




        this.messagePanel.add(messageField,BorderLayout.CENTER);
        this.messagePanel.add(sendButton,BorderLayout.EAST);
        this.panelBoard.add(messagePanel,BorderLayout.SOUTH);*/


        /*SwingUtilities.invokeLater(() -> {
            ChatBoxGUI chatBoxGUI = new ChatBoxGUI();
            chatBoxGUI.setVisible(true);
        });*/

        //take card label
        this.handBoard = new JLabel();
        this.handBoard.setBounds(PixelUtil.commonX_4,PixelUtil.commonY_4,PixelUtil.myShelfBoardHW,PixelUtil.bottomButtonH);
        this.handBoard.setIcon(ImageUtil.getBoardImage("handBoard"));
        this.panelBoard.add(this.handBoard,JLayeredPane.PALETTE_LAYER);

        //user Me
        this.userMe = new JLabel();
        this.userMe.setBounds(PixelUtil.userMeX,PixelUtil.commonY_1,PixelUtil.userMeHW,PixelUtil.userMeHW);
        this.userMe.setIcon(ImageUtil.getBoardImage("iconMe"));
        this.panelBoard.add(this.userMe,JLayeredPane.PALETTE_LAYER);

        //enemy A
        this.enemyA = new JLabel();
        this.enemyA.setBounds(PixelUtil.commonX_1,PixelUtil.commonY_1,PixelUtil.enemyHW,PixelUtil.enemyHW);
        this.enemyA.setIcon(ImageUtil.getBoardImage("enemyA"));
        this.panelBoard.add(this.enemyA,JLayeredPane.PALETTE_LAYER);

        //enemy B
        this.enemyB = new JLabel();
        this.enemyB.setBounds(PixelUtil.commonX_1,PixelUtil.commonY_2,PixelUtil.enemyHW,PixelUtil.enemyHW);
        this.enemyB.setIcon(ImageUtil.getBoardImage("enemyB"));
        this.panelBoard.add(this.enemyB,JLayeredPane.PALETTE_LAYER);

        //enemy C
        this.enemyC = new JLabel();
        this.enemyC.setBounds(PixelUtil.commonX_1,PixelUtil.commonY_3,PixelUtil.enemyHW,PixelUtil.enemyHW);
        this.enemyC.setIcon(ImageUtil.getBoardImage("enemyC"));
        this.panelBoard.add(this.enemyC,JLayeredPane.PALETTE_LAYER);

    }




    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;


    }

}
