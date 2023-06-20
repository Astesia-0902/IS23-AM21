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
    public LivingRoomMenuInterface livingRoomMenuInterface;
    public MyHandInterface myHandInterface;
    public PersonalGoalPanel personalGoalPanel;
    public CommonGoalPanel commonGoalPanel;
    public GameBoardPanel gameBoardPanel;
    public MyHandBoardPanel myHandBoardPanel;
    public ShelfPanel myShelfPanel;

    public HashMap<String, EnemyPanel> enemiesStatus;

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

    public boolean MENU_REFRESH = false;
    public boolean WAIT_ROOM_REFRESH = false;
    public boolean GAME_BOARD_REFRESH = false;
    public boolean NEED_NEW_FRAME = false;
    public Boolean askChat = false;
    private int matchIndex;
    public Thread guiMinion = new Thread() {
        @Override
        public void run() {
            super.run();
            while (true) {
                while (MATCH_END && GO_TO_MENU && !GAME_ON) {
                    ClientView.setMatchEnd(false);
                    askEndRoom();
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
                        } else if (GAME_BOARD_REFRESH) {
                            SwingUtilities.invokeLater(() -> {
                                try {
                                    announceCurrentPlayer();
                                } catch (RemoteException e) {
                                    throw new RuntimeException(e);
                                }
                            });

                            GAME_BOARD_REFRESH = false;
                            System.out.println("Reload Game Board");
                        }
                        Thread.sleep(200);
                    } catch (RemoteException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (livingRoomInterface != null) {
                    livingRoomInterface.dispose();
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

                    System.out.println("Asking chat...");
                    askChat();
                    System.out.println("Close AskChat");

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

    public void init() throws Exception {
        commCtrl = new ClientCommunicationController();
        commCtrl.gui = this;
        communicationInterface = new CommunicationInterface(frame);
        new CommunicationListener(this);

    }

    public void askServerInfoRMI() {
        serverInfoInterface = new ServerInfoInterface(frame);
        new ServerInfoListener(this);
    }

    public void askServerInfoSocket() {
        serverInfoInterface = new ServerInfoInterface(frame);
        new ServerInfoListener(this);
    }


    public void askLogin() throws RemoteException {
        loginInterface = new LoginInterface(frame);
        new LoginListener(this);
    }


    public void askMenuAction() throws RemoteException {
        if (menuActionInterface == null) {
            menuActionInterface = new MenuActionInterface(frame, username);
            new MenuActionListener(this);
            System.out.println("MenuActionInterface done");
        } else if (menuActionInterface != null && NEED_NEW_FRAME) {
            System.out.println("New MenuAction Interface");
            menuActionInterface = new MenuActionInterface(frame, username);
            new MenuActionListener(this);
            NEED_NEW_FRAME = false;
        } else if (MENU_REFRESH && menuActionInterface != null) {
            SwingUtilities.invokeLater(() -> {
                menuActionInterface.reloadMenu();
                menuActionInterface.revalidate();
                menuActionInterface.repaint();
                System.out.println("Menu repainted");
            });
            MENU_REFRESH = false;

        }


    }

    public boolean askCreateMatch() throws Exception {

        return false;
    }

    public int askMaxSeats() {
        menuActionInterface.maxSeatsDialog.setVisible(true);
        return 0;
    }

    public int askChangeSeats() {
        waitingRoomInterface.maxSeatsDialog.setVisible(true);
        return 0;
    }

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
        if (WAIT_ROOM_REFRESH) {
            SwingUtilities.invokeLater(() -> {
                waitingRoomInterface.reloadPlayerNumber(numMiss, numMax, matchID);
                waitingRoomInterface.revalidate();
                waitingRoomInterface.repaint();
            });
            WAIT_ROOM_REFRESH = false;
        }
    }


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


    public boolean askLeaveMatch() throws RemoteException {

        return commCtrl.leaveMatch();
    }


    public boolean askExitGame() throws RemoteException {

        if (commCtrl.exitGame()) {
            System.exit(0);
            return true;
        }
        return false;
    }


    public void showCommonGoals() {
        //refreshing CommonGoal Token
        commonGoalPanel.refreshScoringTokens(commonGoalScore.get(0), commonGoalScore.get(1));

    }


    public void showPersonalGoal() throws RemoteException {
        personalGoalPanel = new PersonalGoalPanel(personalGoal);
        livingRoomInterface.livingRoomPane.add(personalGoalPanel, JLayeredPane.PALETTE_LAYER);
    }


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

       /* while (MATCH_END && GO_TO_MENU && !GAME_ON) {
            ClientView.setMatchEnd(false);
            askEndRoom();
        }
        if(gameResultsInterface!=null)
        {

            System.exit(0);
        }*/

        /*if (MATCH_END) {
            gameResultsInterface = new GameResultsInterface(this, gameResults);
            ClientView.setMatchEnd(false);
        }*/
        SwingUtilities.invokeLater(() -> {
            livingRoomInterface.revalidate();
            livingRoomInterface.repaint();
        });

    }

    public void askEndRoom() {
        gameResultsInterface = new GameResultsInterface(this, gameResults);
        gameResultsInterface.setVisible(true);
    }


    public void showWhoIsPlaying() {
        if (currentPlayer.equals(username)) {
            // it's my turn
            if (livingRoomInterface.livingRoomPanel.waitTimer != null)
                livingRoomInterface.livingRoomPanel.waitTimer.start();

            for (EnemyPanel enemyPanel : livingRoomInterface.enemiesPanel.values()) {
                if (enemyPanel.waitTimer != null) {
                    enemyPanel.waitTimer.stop();
                    enemyPanel.setStatusBorder();
                }
            }
        } else {
            // enemies turn
            if (livingRoomInterface.livingRoomPanel.waitTimer != null)
                livingRoomInterface.livingRoomPanel.waitTimer.stop();
            livingRoomInterface.livingRoomPanel.setBorderColor();
            

            for (EnemyPanel enemyPanel : livingRoomInterface.enemiesPanel.values()) {
                if (enemyPanel.waitTimer != null) {
                    if (enemyPanel.enemyName.getText().equals(currentPlayer))
                        enemyPanel.waitTimer.start();
                    else {
                        enemyPanel.waitTimer.stop();
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


    public void showPlayerShelf() throws RemoteException {


    }


    public void showEveryShelf() {
        //refresh enemies shelf
        livingRoomInterface.refreshEnemiesShelves(shelves);
    }


    public void showBoard() {
        //refresh game Board
        gameBoardPanel.refreshBoard(virtualBoard, this);

    }


    public void showPlayersStats() {
        //refresh my score
        livingRoomInterface.livingRoomPanel.refreshMyScore(scores.get(getPlayerIndex(username)));
        //refresh enemies score
        livingRoomInterface.refreshEnemiesScores(scores);


    }


    public void askPlayerMove() throws RemoteException, ServerNotActiveException {

    }


    public void askSelection() throws ServerNotActiveException, RemoteException {

    }


    public void askDeselection() {

    }


    public void askInsertion() throws ServerNotActiveException, RemoteException {
        myHandInterface = new MyHandInterface(this);
        myHandInterface.refreshHand(currentPlayerHand);

    }


    public void handleChatMessage(String message, boolean live) {
        System.out.println(message);
    }


    public void showEndGameToken() {
        if (!endGameToken)
            gameBoardPanel.pickScoreTokenEndGame();
    }


    public void showTimer() {

    }


    public void showMatchList() throws RemoteException {

    }


    public void showMatchSetup() throws RemoteException {

        livingRoomInterface = new LivingRoomInterface(frame, this);
        showPersonalGoal();

        //set common goal
        commonGoalPanel = new CommonGoalPanel(commonGoal.get(0), commonGoal.get(1));
        livingRoomInterface.livingRoomPane.add(commonGoalPanel, JLayeredPane.PALETTE_LAYER);

        //set common goal description
        showGoalDescription(commonGoalPanel.commonGoalTopLabel, Storage.goalCommonMap.get(commonGoal.get(0)));
        showGoalDescription(commonGoalPanel.commonGoalBottomLabel, Storage.goalCommonMap.get(commonGoal.get(1)));

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
            livingRoomInterface.livingRoomPanel.waitTimer.start();
        else
            livingRoomInterface.enemiesPanel.get(currentPlayer).waitTimer.start();

    }

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
            dialog.setSize(300, 50);
            dialog.setLocationByPlatform(true);
            dialog.setLocationRelativeTo(null);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            //int x = (screenSize.width - dialog.getWidth());
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

    public void askChat() {
        convertPrivateChatsForGUI();
        convertPublicChatForGUI();
        if (chatDialog == null && !newChatWindow) {
            setAskChat(false);

            guiDialogMinion.start();
            guiChatListenerMinion.start();
            System.out.println("Continue1");

        } else if (chatDialog == null && newChatWindow) {
            setAskChat(false);

            guiDialogMinion.start();
            guiChatListenerMinion.start();
            System.out.println("Continue2");
        } else if (chatDialog != null && newChatWindow) {
            setAskChat(false);

            SwingUtilities.invokeLater(() -> {
                chatDialog.reloadChat();
                chatDialog.getContentPane().revalidate();
                chatDialog.getContentPane().repaint();
                System.out.println("Repaint success(Visible)");
                chatDialog.setVisible(true);

            });
            System.out.println("Continue3");
            setNewChatWindow(false);
        } else {
            setAskChat(false);
            // Normal chat update
            SwingUtilities.invokeLater(() -> {
                chatDialog.reloadChat();
                chatDialog.getContentPane().revalidate();
                chatDialog.getContentPane().repaint();
                //System.out.println("Repaint success");
            });
            System.out.println("Continue4");
        }

    }


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
        }
    }

    public void replyDEBUG(String message) {
        System.out.println(message);
        //timeLimitedNotification(message);
    }


    public void showGoalDescription(JLabel commonGoalLabel, String description) {
        if (commonGoalPanel != null) {
            commonGoalPanel.showDescription(commonGoalLabel, description);
        }

    }


    public void showGameRules() {
        ruleDialog.setVisible(true);
    }

    //for living room test (it will be deleted, don't worry )
    public static void main(String[] args) {
        try {
            new Gui().showMatchSetup();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void convertPrivateChatsForGUI() {
        //HashMap<String, JTextArea> chatMap = new HashMap<>();
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
                //DEBUG print chat
                System.out.println(historyTMP.getText());
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
        // Finally
        //privateChatHistoryMap = chatMap;

    }

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

    public void setAskChat(boolean value) {
        synchronized (askChat) {
            askChat = value;
        }
    }

    public void setNewChatWindow(boolean value) {
        synchronized (newChatWindow) {
            newChatWindow = value;
        }
    }
}
