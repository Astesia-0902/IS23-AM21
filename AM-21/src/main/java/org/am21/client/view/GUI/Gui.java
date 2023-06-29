package org.am21.client.view.GUI;

import org.am21.client.ClientCommunicationController;
import org.am21.client.view.ClientView;
import org.am21.client.view.GUI.Interface.*;
import org.am21.client.view.GUI.component.*;
import org.am21.client.view.GUI.listener.*;
import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PathUtil;
import org.am21.client.view.GUI.utils.PixelUtil;
import org.am21.client.view.TUI.Storage;
import org.am21.networkRMI.ClientCallBack;
import org.am21.networkRMI.IClientInput;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.am21.client.view.ClientView.*;

public class Gui {
    public JFrame frame = new JFrame("MyShelfie");
    public ClientCommunicationController commCtrl;
    public IClientInput iClientInputHandler;
    public ClientCallBack clientCallBack;
    public static String root;
    public static String username; //Client username
    public CommunicationInterface communicationInterface;
    public LoginInterface loginInterface;
    public ServerInfoInterface serverInfoInterface;
    public MenuActionInterface menuActionInterface;
    public WaitingRoomInterface waitingRoomInterface;
    public LivingRoomInterface livingRoomInterface;
    public MyHandInterface myHandInterface;
    public PersonalGoalPanel personalGoalPanel;
    public CommonGoalPanel commonGoalPanel;
    public GameBoardPanel gameBoardPanel;
    public MyHandBoardPanel myHandBoardPanel;
    public ShelfPanel myShelfPanel;
    public GameResultsInterface gameResultsInterface;
    public ChairManLabel chairManLabel;
    public ChatDialog chatDialog;
    public RuleDialog ruleDialog = new RuleDialog(frame);
    public OnlineListDialog onlineListDialog;
    public MatchListInterface matchListInterface;
    //-------------------------------------------------------------
    public static HashMap<String, JButton> myChatMap = new HashMap<>(); //chatPlayer
    public static String chatReceiver = "#Unknown"; //chatUser
    //Key: "Receiver", Value: Private chat History
    public static HashMap<String, JTextArea> privateChatHistoryMap = new HashMap<>();
    public static JTextArea publicChatHistory = new JTextArea();
    public static Boolean newChatWindow = false;
    //-------------------------------------------------------------------

    public Boolean menuRefresh = false;
    public Boolean waitRoomRefresh = false;
    public Boolean gameBoardRefresh = false;
    public Boolean needNewFrame = false;
    public Boolean askChat = false;
    private int matchIndex;
    public Thread guiMinion = new Thread() {
        @Override
        public void run() {
            super.run();
            while (true) {
                if (livingRoomInterface != null) {
                    livingRoomInterface.dispose();
                }
                while (!MATCH_END && !GAME_ON && GO_TO_MENU) {
                    try {
                        askMenuAction();
                        Thread.sleep(200);
                    } catch (RemoteException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (chatDialog != null) {
                    chatDialog.setVisible(false);
                }
                if (onlineListDialog != null) {
                    onlineListDialog.setVisible(false);
                }

                while (!GAME_ON && !GO_TO_MENU) {
                    try {
                        askWaitingAction();
                        Thread.sleep(200);
                    } catch (RemoteException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }


                if (waitingRoomInterface != null) {
                    System.out.println("WaitingRoomInterface Disposed");
                    waitingRoomInterface.dispose();
                    //waitingRoomInterface = null;
                }

                if (chatDialog != null) {
                    chatDialog.setVisible(false);
                }
                if (onlineListDialog != null) {
                    onlineListDialog.setVisible(false);
                }
                while (GAME_ON && !GO_TO_MENU) {
                    try {
                        if (MATCH_START) {
                            showMatchSetup();
                            ClientView.setMatchStart(false);
                        } else if (gameBoardRefresh) {
                            SwingUtilities.invokeLater(() -> {
                                try {
                                    announceCurrentPlayer();
                                } catch (RemoteException e) {
                                    throw new RuntimeException(e);
                                }
                            });

                            setGameBoardRefresh(false);
                            System.out.println("Reload Game Board");
                        }
                        Thread.sleep(200);
                    } catch (RemoteException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }


                while (MATCH_END && GO_TO_MENU && !GAME_ON) {
                    askEndRoom();
                }

                if (gameResultsInterface != null) {
                    gameResultsInterface = null;
                }

            }

        }
    };


    public Thread guiMinionChat = new Thread() {
        @Override
        public void run() {
            super.run();
            while (true) {
                while (askChat) {
                    askChat();
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }


        }
    };
    public final Object chatLock = new Object();
    public ChatRunnable chatRun = new ChatRunnable(this);
    public Thread guiDialogMinion = new Thread(chatRun);


    public Thread guiChatListenerMinion = new Thread() {
        @Override
        public void run() {
            super.run();
            synchronized (chatLock) {
                System.out.println("ChatListener start");
                new ChatListener(chatRun.gui);
            }
        }
    };


    /**
     * Constructor
     *
     * @throws Exception
     */
    public Gui() throws Exception {
        this.clientCallBack = new ClientCallBack();
        this.clientCallBack.gui = this;
        frame.setIconImage(ImageIO.read(new File(PathUtil.getPath("Publisher material/Icon 50x50px.png"))));
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        new RuleListener(this);
    }

    /**
     * Init the gui
     */
    public void init() throws Exception {
        commCtrl = new ClientCommunicationController();
        commCtrl.gui = this;
        communicationInterface = new CommunicationInterface(frame);
        new CommunicationListener(this);

    }

    /**
     * Ask for a server info RMI
     */
    public void askServerInfoRMI() {
        serverInfoInterface = new ServerInfoInterface(frame);
        new ServerInfoListener(this);
    }

    /**
     * Ask for a server info socket
     */
    public void askServerInfoSocket() {
        serverInfoInterface = new ServerInfoInterface(frame);
        new ServerInfoListener(this);
    }


    /**
     * Ask for a login
     */
    public void askLogin() throws RemoteException {
        loginInterface = new LoginInterface(frame);
        new LoginListener(this);
    }

    /**
     * Ask for menu action
     */
    public void askMenuAction() throws RemoteException {
        if (menuActionInterface == null) {
            menuActionInterface = new MenuActionInterface(frame, username);
            new MenuActionListener(this);
            System.out.println("MenuActionInterface done");
        } else if (menuActionInterface != null && needNewFrame) {
            System.out.println("New MenuAction Interface");
            menuActionInterface = new MenuActionInterface(frame, username);
            new MenuActionListener(this);
            menuActionInterface.setVisible(true);
            setNeedNewFrame(false);
        } else if (menuRefresh && menuActionInterface != null) {
            SwingUtilities.invokeLater(() -> {
                menuActionInterface.reloadMenu();
                menuActionInterface.revalidate();
                menuActionInterface.repaint();
                System.out.println("Menu repainted");
            });
            setMenuRefresh(false);

        }


    }

    /**
     * Ask for a match setup
     */
    public int askMaxSeats() {
        menuActionInterface.maxSeatsDialog.setVisible(true);
        return 0;
    }

    /**
     * Ask for change seats
     */
    public int askChangeSeats() {
        waitingRoomInterface.maxSeatsDialog.setVisible(true);
        return 0;
    }

    /**
     * Ask for waiting action
     */
    public void askWaitingAction() throws RemoteException {

        for (int i = 0; i < ClientView.matchList.length; i++) {
            if (Integer.parseInt(ClientView.matchList[i][0]) == ClientView.matchID) {
                matchIndex = i;
            }
        }
        String numMiss = ClientView.matchList[matchIndex][2], numMax = ClientView.matchList[matchIndex][3];
        int matchID = ClientView.matchID;
        if (waitingRoomInterface == null || !waitingRoomInterface.isVisible()) {
            waitingRoomInterface = new WaitingRoomInterface(frame, numMiss, numMax, matchID);
            new WaitingRoomListener(this);
        }
        if (waitRoomRefresh) {
            SwingUtilities.invokeLater(() -> {
                waitingRoomInterface.reloadPlayerNumber(numMiss, numMax, matchID);
                waitingRoomInterface.revalidate();
                waitingRoomInterface.repaint();
            });
            setWaitRoomRefresh(false);
        }
    }


    /**
     * Ask for join a match
     */
    public boolean askJoinMatch() {
        DefaultListModel<String> matchModel = new DefaultListModel<>();
        if (ClientView.matchList != null) {
            String[] match = new String[ClientView.matchList.length];
            for (int i = 0; i < ClientView.matchList.length; i++) {
                match[i] = "ID: " + ClientView.matchList[i][0] + "  |  " + ClientView.matchList[i][1]
                           + " | Players: (" + ClientView.matchList[i][2] + "/" + ClientView.matchList[i][3] + ")";
            }
            for (String m : match) {
                matchModel.addElement(m);
            }
        }

        matchListInterface = new MatchListInterface(frame, matchModel);
        new MatchListListener(this);

        return false;
    }

    /**
     * Ask for leave a match
     */
    public boolean askLeaveMatch() throws RemoteException {

        return commCtrl.leaveMatch();
    }


    /**
     * Ask for exit the game
     */
    public boolean askExitGame() throws RemoteException {

        if (commCtrl.exitGame()) {
            System.exit(0);
            return true;
        }
        return false;
    }

    /**
     * Ask for show the common goals
     */
    public void showCommonGoals() {
        //refreshing CommonGoal Token
        commonGoalPanel.refreshScoringTokens(commonGoalScore.get(0), commonGoalScore.get(1));

    }

    /**
     * Ask for show the personal goals
     */
    public void showPersonalGoal() throws RemoteException {
        personalGoalPanel = new PersonalGoalPanel(personalGoal);
        livingRoomInterface.livingRoomPane.add(personalGoalPanel, JLayeredPane.PALETTE_LAYER);
    }


    /**
     * Ask for announce the current player
     */
    public void announceCurrentPlayer() throws RemoteException {
        SwingUtilities.invokeLater(() -> {
            myShelfPanel.refreshShelf(shelves.get(getPlayerIndex(username))); //refresh my shelf
        });

        SwingUtilities.invokeLater(() -> {
            myHandBoardPanel.refreshItem(currentPlayerHand); //refresh hand panel
        });

        //refresh board
        SwingUtilities.invokeLater(this::showBoard);
        //refresh enemy's shelf
        SwingUtilities.invokeLater(this::showEveryShelf);
        // refresh shelf
        SwingUtilities.invokeLater(this::showPlayersStats);
        //change player color
        SwingUtilities.invokeLater(this::showWhoIsPlaying);
        //Update common goal token
        SwingUtilities.invokeLater(this::showCommonGoals);
        //Update endgame token
        SwingUtilities.invokeLater(this::showEndGameToken);

        SwingUtilities.invokeLater(() -> {
            livingRoomInterface.revalidate();
            livingRoomInterface.repaint();
        });

    }

    /**
     * Ask for game results
     */
    public void askEndRoom() {

        if (gameResultsInterface == null) {
            gameResultsInterface = new GameResultsInterface(this, gameResults);
            new Thread(() -> {
                gameResultsInterface.setVisible(true);
            }).start();
        }
        if (waitingRoomInterface != null && waitingRoomInterface.isActive()) {
            System.out.println("WaitingRoomInterface Disposed");
            waitingRoomInterface.dispose();
        }
        if (menuActionInterface != null && menuActionInterface.isActive()) {
            menuActionInterface.dispose();
        }
    }

    /**
     * view the current player
     */
    public void showWhoIsPlaying() {
        if (currentPlayer.equals(username)) {
            // it's my turn
            if (livingRoomInterface.livingRoomPanel.flashingTimer != null)
                livingRoomInterface.livingRoomPanel.flashingTimer.start();

            for (EnemyPanel enemyPanel : livingRoomInterface.enemiesPanel.values()) {
                if (enemyPanel.flashingTimer != null) {
                    enemyPanel.flashingTimer.stop();
                    enemyPanel.setStatusBorder();
                }
            }
        } else {
            // enemies turn
            if (livingRoomInterface.livingRoomPanel.flashingTimer != null)
                livingRoomInterface.livingRoomPanel.flashingTimer.stop();
            livingRoomInterface.livingRoomPanel.setBorderColor();


            for (EnemyPanel enemyPanel : livingRoomInterface.enemiesPanel.values()) {
                if (enemyPanel.flashingTimer != null) {
                    if (enemyPanel.enemyName.getText().equals(currentPlayer))
                        enemyPanel.flashingTimer.start();
                    else {
                        enemyPanel.flashingTimer.stop();
                        enemyPanel.setStatusBorder();
                    }

                }


            }

            gameBoardPanel.refreshEnemyView(virtualBoard, this); //refresh enemy action on the game board
        }

        //if my turn
        SwingUtilities.invokeLater(() -> {
            livingRoomInterface.revalidate();
            livingRoomInterface.repaint();
        });
    }

    /**
     * view every shelf
     */
    public void showEveryShelf() {
        //refresh enemies shelf
        livingRoomInterface.refreshEnemiesShelves(shelves);
    }

    /**
     * ask for show the game board
     */
    public void showBoard() {
        //refresh game Board
        gameBoardPanel.refreshBoard(virtualBoard, this);

    }

    /**
     * ask for show the players stats
     */
    public void showPlayersStats() {
        //refresh my score
        livingRoomInterface.livingRoomPanel.refreshMyScore(scores.get(getPlayerIndex(username)),hiddenPoints.get(getPlayerIndex(username)));
        //refresh enemies score
        livingRoomInterface.refreshEnemiesScores(scores);


    }

    public void askSelection() throws ServerNotActiveException, RemoteException {

    }

    /**
     * ask for insertion
     */
    public void askInsertion() throws ServerNotActiveException, RemoteException {
        myHandInterface = new MyHandInterface(this);
        myHandInterface.refreshHand(currentPlayerHand);

    }

    /**
     * Ask for show the end game token
     */
    public void showEndGameToken() {
        if (!endGameToken)
            gameBoardPanel.pickScoreTokenEndGame();
    }

    /**
     * Ask for show the match setup
     */
    public void showMatchSetup() throws RemoteException {

        livingRoomInterface = new LivingRoomInterface(frame, this);
        showPersonalGoal();

        //set common goal
        commonGoalPanel = new CommonGoalPanel(commonGoal.get(0), commonGoal.get(1));
        livingRoomInterface.livingRoomPane.add(commonGoalPanel, JLayeredPane.PALETTE_LAYER);

        //set CommonGoal Token
        commonGoalPanel.setScoreToken(commonGoalScore.get(0), commonGoalScore.get(1));

        //if me is chairMan
        if (username.equals(currentPlayer)) {
            chairManLabel = new ChairManLabel(true);
            livingRoomInterface.livingRoomPane.add(chairManLabel, JLayeredPane.PALETTE_LAYER);
        }

        //set my Hand
        myHandBoardPanel = new MyHandBoardPanel();
        livingRoomInterface.livingRoomPane.add(myHandBoardPanel, JLayeredPane.PALETTE_LAYER);


        //set initial game board
        gameBoardPanel = new GameBoardPanel(maxSeats);
        livingRoomInterface.livingRoomPane.add(gameBoardPanel, JLayeredPane.PALETTE_LAYER);

        //set the game board
        gameBoardPanel.fillingBoard(ClientView.virtualBoard, this);

        //set EndGameToken
        gameBoardPanel.setScoreTokenEndGame();

        //refresh every one score
        showPlayersStats();

        //show the color of current player
        showWhoIsPlaying();

        //set my shelf
        myShelfPanel = new ShelfPanel(PixelUtil.myGridX, PixelUtil.myGridY, PixelUtil.myCellW, PixelUtil.myCellH, PixelUtil.myItemW, PixelUtil.myItemH);
        livingRoomInterface.livingRoomPane.add(myShelfPanel, JLayeredPane.PALETTE_LAYER);

        //set Timer
        if (currentPlayer.equals(username))
            livingRoomInterface.livingRoomPanel.flashingTimer.start();
        else
            livingRoomInterface.enemiesPanel.get(currentPlayer).flashingTimer.start();

    }

    /**
     * Ask for send the message
     */
    public void print(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    public void timeLimitedNotification(String message, int time) {
        if (chatDialog != null && chatDialog.isVisible()) {
            return;
        }
        SwingUtilities.invokeLater(() -> {
            JDialog dialog = new JDialog(frame);
            dialog.setUndecorated(true);

            FontMetrics fontMetrics = dialog.getFontMetrics(dialog.getFont());
            int messageWidth = fontMetrics.stringWidth(message);
            int dialogWidth = Math.max(messageWidth + 50, 300);

            dialog.setSize(dialogWidth, 50);
            dialog.setLocationByPlatform(true);
            dialog.setLocationRelativeTo(null);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


            int x = (screenSize.width / 2 - dialog.getWidth() / 2);
            int y = 0;

            dialog.setLocation(x, y);

            JPanel panel = new JPanel(new BorderLayout());

            JLabel label = new JLabel(message);
            label.setHorizontalAlignment(SwingConstants.CENTER);

            panel.add(label, BorderLayout.CENTER);

            panel.setBackground(Color.LIGHT_GRAY);
            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            dialog.getContentPane().add(panel);

            Timer timer = new Timer(time, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                }
            });
            timer.setRepeats(false);
            timer.start();

            dialog.setVisible(true);
        });
    }


    /**
     * Ask for chat
     */
    public void askChat() {
        convertPrivateChatsForGUI();
        convertPublicChatForGUI();
        if (chatDialog == null && !newChatWindow) {
            setAskChat(false);
            guiDialogMinion.start();
            guiChatListenerMinion.start();
        } else if (chatDialog == null && newChatWindow) {
            setAskChat(false);
            guiDialogMinion.start();
            guiChatListenerMinion.start();
        } else if (chatDialog != null && newChatWindow) {
            setAskChat(false);
            SwingUtilities.invokeLater(() -> {
                chatDialog.reloadChat();
                if (livingRoomInterface != null && chatDialog != null && onlineListDialog != null) {
                    chatDialog.setLocation(PixelUtil.commonX_1, PixelUtil.cWindowY);
                    chatDialog.setSize(PixelUtil.cWindowW, PixelUtil.cWindowH);
                    onlineListDialog.setLocation(PixelUtil.commonX_1, PixelUtil.cPlayerWindowY);
                }
                chatDialog.getContentPane().revalidate();
                chatDialog.getContentPane().repaint();
                System.out.println("Repaint success(Visible)");
                chatDialog.setVisible(true);

            });
            setNewChatWindow(false);
        } else {
            setAskChat(false);
            // Normal chat update
            SwingUtilities.invokeLater(() -> {
                chatDialog.reloadChat();
                if (livingRoomInterface != null && chatDialog != null && onlineListDialog != null) {
                    chatDialog.setLocation(PixelUtil.commonX_1, PixelUtil.cWindowY);
                    chatDialog.setSize(PixelUtil.cWindowW, PixelUtil.cWindowH);
                    onlineListDialog.setLocation(PixelUtil.commonX_1, PixelUtil.cPlayerWindowY);
                }
                chatDialog.getContentPane().revalidate();
                chatDialog.getContentPane().repaint();
            });
        }

    }

    /**
     * Ask for show online player
     */
    public void showOnlinePlayer() throws RemoteException {
        DefaultListModel<String> userModel = new DefaultListModel<>();
        for (int i = 0; i < ClientView.onlinePlayers.length; i++) {
            if (ClientView.onlinePlayers[i][0] != null) {
                if (ClientView.onlinePlayers[i][0].equals(username)) {
                    userModel.addElement(ClientView.onlinePlayers[i][0] + "  |  " + ClientView.onlinePlayers[i][1] + " (You) ");
                } else {
                    userModel.addElement(ClientView.onlinePlayers[i][0] + "  |  " + ClientView.onlinePlayers[i][1]);
                }
            }

        }

        if (onlineListDialog == null || !onlineListDialog.isVisible()) {
            onlineListDialog = new OnlineListDialog(frame, userModel);
            new OnlineListListener(this);
        } else if (onlineListDialog != null && !onlineListDialog.isVisible()) {
            SwingUtilities.invokeLater(() -> {
                onlineListDialog.setVisible(true);
                onlineListDialog.revalidate();
                onlineListDialog.repaint();
            });
        }
    }

    public void replyDEBUG(String message) {
        System.out.println(message);
    }


    /**
     * Ask for show game rules
     */
    public void showGameRules() {
        ruleDialog.setVisible(true);
    }

    /**
     * convert private chat for GUI
     */
    public void convertPrivateChatsForGUI() {

        java.util.List<JTextArea> visualChats = new ArrayList<>();
        System.out.println("Convert Private Chats");
        if (ClientView.privateChats != null && !ClientView.privateChats.isEmpty()) {
            java.util.List<java.util.List<String>> privateChatsList = ClientView.privateChats;
            for (java.util.List<String> chat : privateChatsList) {
                JTextArea historyTMP = new JTextArea(ImageUtil.resizeX(10), ImageUtil.resizeY(20));
                historyTMP.setEditable(false);
                historyTMP.setForeground(new Color(106, 2, 1));
                historyTMP.setFont(new Font("Serif", Font.BOLD, ImageUtil.resizeY(14)));
                historyTMP.setLineWrap(true);
                historyTMP.setWrapStyleWord(true);
                historyTMP.setCaretPosition(historyTMP.getDocument().getLength());
                for (String line : chat) {
                    historyTMP.append(line + "\n");
                }
                historyTMP.setCaretPosition(historyTMP.getDocument().getLength());

                visualChats.add(historyTMP);

            }

            // ChatMap (Keys)
            if (ClientView.chatMap != null && !ClientView.chatMap.isEmpty()) {
                for (Map.Entry<String, Integer> entry : ClientView.chatMap.entrySet()) {
                    String key = entry.getKey();
                    if (key.startsWith(Gui.username) || key.endsWith(Gui.username)) {
                        String[] newKey = key.split("@");
                        String receiver = "";
                        if (newKey[0].equals(Gui.username)) {
                            receiver = newKey[1];
                        } else if (newKey[1].equals(Gui.username)) {
                            receiver = newKey[0];
                        }

                        int value = entry.getValue();
                        //Insert key(receiver) and JTextArea of the Private Chat
                        // Direct Update
                        Gui.privateChatHistoryMap.put(receiver, visualChats.get(value));

                        //chatMap.put(receiver, visualChats.get(value));
                        if (Gui.myChatMap != null && !Gui.myChatMap.containsKey(receiver)) {
                            Gui.myChatMap.put(receiver, new JButton(receiver));
                        }
                    }
                }

            }

        }


    }

    /**
     * convert public chat for GUI
     */
    public void convertPublicChatForGUI() {
        JTextArea historyTMP = new JTextArea(ImageUtil.resizeX(10), ImageUtil.resizeY(20));
        historyTMP.setEditable(false);
        historyTMP.setForeground(new Color(106, 2, 1));
        historyTMP.setFont(new Font("Serif", Font.BOLD, ImageUtil.resizeY(14)));
        historyTMP.setLineWrap(true);
        historyTMP.setWrapStyleWord(true);
        historyTMP.setCaretPosition(historyTMP.getDocument().getLength());
        if (ClientView.publicChat != null && !ClientView.publicChat.isEmpty()) {
            java.util.List<String> tmpChat = ClientView.publicChat;
            for (String line : tmpChat) {
                historyTMP.append(line + "\n");
            }
            historyTMP.setCaretPosition(historyTMP.getDocument().getLength());

            //DEBUG print chat
            System.out.println(historyTMP.getText());
        } else {
            System.out.println("No Public chat");
        }
        Gui.publicChatHistory = historyTMP;
    }

    public synchronized void setAskChat(boolean value) {
            askChat = value;
    }

    public synchronized void setNewChatWindow(boolean value) {
        newChatWindow = value;
    }

    public synchronized void setGameBoardRefresh(boolean value) {
        gameBoardRefresh = value;
    }

    public synchronized void setMenuRefresh(boolean value) {
        menuRefresh = value;
    }

    public synchronized void setNeedNewFrame(boolean value) {
        needNewFrame = value;
    }

    public synchronized void setWaitRoomRefresh(boolean value) {
        waitRoomRefresh = value;
    }

}
