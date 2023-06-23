package org.am21.client.view.GUI.component;


import org.am21.client.view.ClientView;
import org.am21.client.view.GUI.Gui;
import org.am21.client.view.GUI.Interface.LivingRoomMenuInterface;
import org.am21.client.view.GUI.utils.FontUtil;
import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;


public class LivingRoomPanel extends JPanel {

    public JButton livingRoomMenuButton;
    public JLabel myLabel;
    public JLabel myShelfBoardLabel;
    public JButton insertButton;
    public JButton clearButton;
    public JLayeredPane panelBoard;
    public JLabel backGroundLabel;
    public JLabel gameBoardLabel;
    public JLabel personalGoalLabel;
    public JLabel commonGoalALabel;
    public JLabel commonGoalBLabel;
    public JLabel bagLabel;
    public JButton openChat;
    public JLabel handBoardLabel;
    public JLabel myScoreBand;
    public JLabel myScoreDynamic;
    public Timer flashingTimer;


    public LivingRoomPanel(Gui gui) {

        setLivingRoomPanel();
//------------------------------------------------------------------ function of game --------------------------------------------------------------------------------------------------------
        setMenuButton(gui);

        setInsertButton(gui);

        setClearButton(gui);

        setChatButton(gui);

        setFlashingTimer();
    }

    /**
     * base set
     */
    public void setLivingRoomPanel() {
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
        myScoreBand.setFont(FontUtil.getFontByName("HongLeiXingShuJianTi-2")
                .deriveFont(Font.PLAIN, ImageUtil.resizeY(30)));
        myScoreBand.setForeground(new Color(85, 35, 222, 230));
        panelBoard.add(myScoreBand, JLayeredPane.PALETTE_LAYER);

        myScoreDynamic = new JLabel();
        myScoreDynamic.setBounds(PixelUtil.myScoreDynamicX, PixelUtil.commonY_1, PixelUtil.myScoreDynamicW, PixelUtil.myScoreDynamicH);
        myScoreDynamic.setFont(FontUtil.getFontByName("HongLeiXingShuJianTi-2")
                .deriveFont(Font.PLAIN, ImageUtil.resizeY(30)));
        myScoreDynamic.setForeground(new Color(0, 0, 0, 255));
        panelBoard.add(myScoreDynamic, JLayeredPane.PALETTE_LAYER);

    }

    /**
     * menu button function
     */
    public void setMenuButton(Gui gui) {

        livingRoomMenuButton = new JButton();
        livingRoomMenuButton.setBounds(PixelUtil.livingRoomMenuX, PixelUtil.livingRoomMenuY, PixelUtil.livingRoomMenuW, PixelUtil.livingRoomMenuH);
        livingRoomMenuButton.setForeground(new Color(164, 91, 9, 255));
        livingRoomMenuButton.setOpaque(false);
        livingRoomMenuButton.setIcon(ImageUtil.getBoardImage("iconMenu"));

        // open menu interface
        livingRoomMenuButton.addActionListener(e -> {
            LivingRoomMenuInterface livingRoomMenuInterface = new LivingRoomMenuInterface(gui);
            livingRoomMenuInterface.setVisible(true);
        });

        panelBoard.add(livingRoomMenuButton, JLayeredPane.PALETTE_LAYER);
    }

    /**
     * insert action button
     *
     * @param gui GUI
     */
    public void setInsertButton(Gui gui) {

        insertButton = new JButton("INSERT");
        insertButton.setFont(FontUtil.getFontByName("HongLeiXingShuJianTi-2")
                .deriveFont(Font.PLAIN, ImageUtil.resizeY(16)));
        insertButton.setBounds(PixelUtil.insertButtonX, PixelUtil.commonY_4, PixelUtil.insertClearButtonW, PixelUtil.insertClearButtonH);
        insertButton.setBorder(new MatteBorder(ImageUtil.resizeY(2), ImageUtil.resizeX(2), ImageUtil.resizeY(2),
                ImageUtil.resizeX(2), new Color(4, 134, 10, 230)));
        insertButton.setUI(new ButtonColorUI(new Color(136, 218, 123, 139)));
        insertButton.setBackground(Color.WHITE);
        insertButton.setForeground(new Color(4, 134, 10, 230));

        //open my hand interface
        insertButton.addActionListener(e -> {
            if (gui.commCtrl.confirmSelection()) {
                try {
                    gui.askInsertion();
                } catch (ServerNotActiveException | RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panelBoard.add(insertButton, JLayeredPane.PALETTE_LAYER);
    }

    /**
     * clear action button
     *
     * @param gui GUI
     */
    public void setClearButton(Gui gui) {

        clearButton = new JButton("CLEAR");
        clearButton.setFont(FontUtil.getFontByName("HongLeiXingShuJianTi-2")
                .deriveFont(Font.PLAIN, ImageUtil.resizeY(16)));
        clearButton.setBounds(PixelUtil.clearButtonX, PixelUtil.commonY_4, PixelUtil.insertClearButtonW, PixelUtil.insertClearButtonH);
        clearButton.setBorder(new MatteBorder(ImageUtil.resizeY(2), ImageUtil.resizeX(2), ImageUtil.resizeY(2),
                ImageUtil.resizeX(2), new Color(178, 34, 34)));
        clearButton.setUI(new ButtonColorUI(new Color(255, 181, 172, 139)));
        clearButton.setBackground(Color.WHITE);
        clearButton.setForeground(new Color(172, 19, 5, 230));
        clearButton.addActionListener(e ->
        {
            if (gui.commCtrl.deselectCards()) {
                gui.myHandBoardPanel.refreshItem(ClientView.currentPlayerHand);
                gui.gameBoardPanel.clearSelectColor();
                JOptionPane.showMessageDialog(null, "clear successful");
            }
        });
        panelBoard.add(clearButton, JLayeredPane.PALETTE_LAYER);
    }

    /**
     * chat action button
     *
     * @param gui GUI
     */
    public void setChatButton(Gui gui) {

        openChat = new JButton("CHAT");
        openChat.setBounds(PixelUtil.commonX_1, PixelUtil.cButtonY, PixelUtil.cButtonW, PixelUtil.cButtonH);
        openChat.setFont(FontUtil.getFontByName("HongLeiXingShuJianTi-2")
                .deriveFont(Font.PLAIN, ImageUtil.resizeY(16)));
        openChat.setBorder(new MatteBorder(ImageUtil.resizeY(2), ImageUtil.resizeX(2), ImageUtil.resizeY(2),
                ImageUtil.resizeX(2), new Color(85, 35, 222, 230)));
        openChat.setUI(new ButtonColorUI(new Color(178, 157, 225, 230)));
        openChat.setBackground(Color.WHITE);
        openChat.setForeground(new Color(85, 35, 222, 230));
        openChat.addActionListener(e -> {
            if (!Gui.myChatMap.containsKey("#All")) {
                Gui.myChatMap.put("#All", new JButton("#All"));
            }

            Gui.chatReceiver = "#All";
            if (gui.chatDialog != null) {
                gui.chatDialog.localChatMap.get(Gui.chatReceiver).setBackground(new Color(83, 46, 91, 230));
                FontMetrics fm = gui.chatDialog.chatMessageInput.getFontMetrics(gui.chatDialog.chatMessageInput.getFont());
                gui.chatDialog.chatMessageInput.setBorder(new EmptyBorder(0, ImageUtil.resizeX(fm.stringWidth(Gui.chatReceiver) + 30), 0, 0));
            }

            gui.setNewChatWindow(true);
            gui.setAskChat(true);

            try {
                if (gui.onlineListDialog != null && gui.onlineListDialog.isVisible()) {
                    gui.onlineListDialog.setVisible(false);
                }
                gui.showOnlinePlayer();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }

        });
        panelBoard.add(openChat, JLayeredPane.MODAL_LAYER);
    }

    /**
     * set border flashing
     */
    public void setFlashingTimer() {
        Border originalBorder = gameBoardLabel.getBorder();
        Border flashingBorder = new LineBorder(Color.GREEN);

        flashingTimer = new Timer(350, new ActionListener() {
            private boolean isFlashing = false;

            public void actionPerformed(ActionEvent e) {
                if (isFlashing) {
                    gameBoardLabel.setBorder(originalBorder);
                } else {
                    gameBoardLabel.setBorder(flashingBorder);
                }
                isFlashing = !isFlashing;
            }
        });
        flashingTimer.setRepeats(true);
    }

    /**
     * refreshing score on the board
     *
     * @param score common goal score
     */
    public void refreshMyScore(int score) {

        myScoreDynamic.setText(String.valueOf(score));
    }

    /**
     * restore the border color from flashing action
     */
    public void setBorderColor() {

        gameBoardLabel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 255)));
    }

}
