package org.am21.client.view.GUI;

import org.am21.client.ClientCommunicationController;
import org.am21.client.SocketClient;
import org.am21.client.view.ClientView;
import org.am21.client.view.GUI.Interface.*;
import org.am21.client.view.GUI.component.*;
import org.am21.client.view.GUI.listener.*;
import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PathUtil;
import org.am21.client.view.GUI.utils.PixelUtil;
import org.am21.client.view.View;
import org.am21.networkRMI.ClientCallBack;
import org.am21.networkRMI.IClientInput;
import org.am21.networkRMI.Lobby;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.am21.client.view.ClientView.*;

public class Gui implements View {
    public JFrame frame = new JFrame("MyShelfie");
    public ClientCommunicationController commCtrl;
    public IClientInput iClientInputHandler;
    public ClientCallBack clientCallBack;
    public String root;
    public static String username; //Client username
    public CommunicationInterface communicationInterface;
    public Timer announceTimer;
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
    public static boolean NEW_CHAT_WINDOW = false;
    //-------------------------------------------------------------------
    private SocketClient socket;
    public boolean GO_TO_MENU = true;
    //If true askPlayerMove, if false askWaitingAction
    public boolean GAME_ON = false;
    public boolean START = false;
    //If true GoToEndRoom
    public boolean END = false;
    public boolean WAIT_SOCKET = false;
    public boolean REFRESH = false;
    public boolean WAIT_ROOM_REFRESH = false;
    public boolean NEED_NEW_FRAME = false;

    public boolean ASK_CHAT = false;
    private int matchIndex;
    private Thread numThread;
    public Thread guiMinion = new Thread() {
        @Override
        public void run() {
            super.run();
            while (true) {
                while (!GAME_ON && GO_TO_MENU) {
                    try {
                        askMenuAction();
                        Thread.sleep(200);
                    } catch (RemoteException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
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
                    waitingRoomInterface = null;
                }
                while (GAME_ON && !GO_TO_MENU) {
                    try {
                        if (START) {
                            showMatchSetup();
                            START = false;
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
                while (ASK_CHAT) {

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

    public void askServerInfoRMI() throws MalformedURLException, NotBoundException, RemoteException {

        Lobby lobby = (Lobby) Naming.lookup("rmi://localhost:1234/Welcome");
        try {
            HashMap<String, String> serverInfo = lobby.connect();
            root = serverInfo.get("root");
        } catch (AlreadyBoundException e) {
            throw new RuntimeException(e);
        }

        serverInfoInterface = new ServerInfoInterface(frame);
        new ServerInfoListener(this);
    }

    public void askServerInfoSocket() {
        socket = new SocketClient();
        SocketClient.gui = this;
        socket.start();
        try {
            askLogin();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void askLogin() throws RemoteException {
        loginInterface = new LoginInterface(frame);
        new LoginListener(this);
    }

    @Override
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
        }

    }

    @Override
    public boolean askCreateMatch() throws Exception {

        return false;
    }

    @Override
    public int askMaxSeats() {
        menuActionInterface.maxSeatsDialog.setVisible(true);
        return 0;
    }

    public int askChangeSeats() {
        waitingRoomInterface.maxSeatsDialog.setVisible(true);
        return 0;
    }

    public void askWaitingAction() throws RemoteException {
        //synchronized (guiMinion) {
        if (chatDialog != null) {
            //chatDialog.dispose();
            //onlineListDialog.dispose();
        }

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

    @Override
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

    @Override
    public boolean askLeaveMatch() throws RemoteException {

        return commCtrl.leaveMatch();
    }

    @Override
    public boolean askExitGame() throws RemoteException {

        if (commCtrl.exitGame()) {
            System.exit(0);
            return true;
        }
        return false;
    }

    @Override
    public void showCommonGoals() {
        //refreshing CommonGoal Token
        commonGoalPanel.refreshScoringTokens(commonGoalScore.get(0), commonGoalScore.get(1));

    }

    @Override
    public void showPersonalGoal() throws RemoteException {
        personalGoalPanel = new PersonalGoalPanel(personalGoal);
        livingRoomInterface.livingRoomPane.add(personalGoalPanel, JLayeredPane.PALETTE_LAYER);
    }

    @Override
    public void announceCurrentPlayer() throws RemoteException {

        myShelfPanel.refreshShelf(shelves.get(getPlayerIndex(username)));
        //go to end turn
        myHandBoardPanel.refreshItem(currentPlayerHand);
        //end turn

        //TODO: need notifyToAll for update all board, shelf and score view
        showBoard(); //refresh board
        showEveryShelf(); //refresh enemy's shelf

        showPlayersStats(); //TODO:refresh users scores change in real time ???

        showWhoIsPlaying(); //TODO: fix change color player problem

        //TODO: end token ???

        if (isEND()) {
            gameResultsInterface = new GameResultsInterface(this,gameResults);
            setEND(false);
        }
    }

    @Override
    public void showWhoIsPlaying() {
        if (currentPlayer.equals(username)) {
            // it's my turn
            if (livingRoomInterface.livingRoomPanel.waitTimer != null)
                livingRoomInterface.livingRoomPanel.waitTimer.start();
            // livingRoomInterface.enemiesPanel.get(currentPlayer).waitTimer.stop();

            for (EnemyPanel enemyPanel : livingRoomInterface.enemiesPanel.values()) {
                if (enemyPanel.waitTimer != null) {
                    enemyPanel.waitTimer.stop();
                }
            }
        } else {
            // enemies turn
            if (livingRoomInterface.livingRoomPanel.waitTimer != null)
                livingRoomInterface.livingRoomPanel.waitTimer.stop();
            //  livingRoomInterface.enemiesPanel.get(currentPlayer).waitTimer.start();

            if (livingRoomInterface.enemiesPanel.containsKey(currentPlayer)) {
                EnemyPanel enemyPanel = livingRoomInterface.enemiesPanel.get(currentPlayer);
                if (enemyPanel.waitTimer != null) {
                    enemyPanel.waitTimer.start();
                }
            }
        }

        //if my turn

    }

    @Override
    public void showPlayerShelf() throws RemoteException {


    }

    @Override
    public void showEveryShelf() throws RemoteException {
        //refresh enemies shelf
        livingRoomInterface.refreshEnemiesShelves(shelves);
    }

    @Override
    public void showBoard() throws RemoteException {
        //refresh game Board
        gameBoardPanel.refreshBoard(virtualBoard, this);

    }

    @Override
    public void showPlayersStats() throws RemoteException {
        //refresh my score
        livingRoomInterface.livingRoomPanel.refreshMyScore(scores.get(getPlayerIndex(username)));
        //refresh enemies score
        livingRoomInterface.refreshEnemiesScores(scores);


    }

    @Override
    public void askPlayerMove() throws RemoteException, ServerNotActiveException {

    }

    @Override
    public void askSelection() throws ServerNotActiveException, RemoteException {

    }

    @Override
    public void askDeselection() throws ServerNotActiveException, RemoteException {

    }

    @Override
    public void askInsertion() throws ServerNotActiveException, RemoteException {
        myHandInterface = new MyHandInterface(this);
        myHandInterface.refreshHand(currentPlayerHand);

    }

    @Override
    public void handleChatMessage(String message, boolean live) {
        System.out.println(message);
    }

    @Override
    public void showEndGameToken() {

    }

    @Override
    public void showTimer() {

    }

    @Override
    public void showMatchList() throws RemoteException {

    }

    @Override
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
            livingRoomInterface.livingRoomPanel.waitTimer.start();
        else
            livingRoomInterface.enemiesPanel.get(currentPlayer).waitTimer.start();

    }

    @Override
    public void askShowObject() throws RemoteException {

    }

    public void print(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    public void timeLimitedNotification(String message) {
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

            int x = screenSize.width - dialog.getWidth();
            int y = 0;

            // Imposta le coordinate per posizionare la notifica in alto a destra
            dialog.setLocation(x, y);

            JPanel panel = new JPanel(new BorderLayout());

            JLabel label = new JLabel(message);
            label.setHorizontalAlignment(SwingConstants.CENTER);

            panel.add(label, BorderLayout.CENTER);

            panel.setBackground(Color.LIGHT_GRAY);
            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            dialog.getContentPane().add(panel);

            Timer timer = new Timer(5000, new ActionListener() {
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
        if (chatDialog == null && !NEW_CHAT_WINDOW) {
            ASK_CHAT = false;
            guiDialogMinion.start();
            guiChatListenerMinion.start();
            System.out.println("Continue1");

        } else if (chatDialog == null && NEW_CHAT_WINDOW) {
            ASK_CHAT = false;
            guiDialogMinion.start();
            guiChatListenerMinion.start();
            System.out.println("Continue2");
        } else if (chatDialog != null && NEW_CHAT_WINDOW) {
            ASK_CHAT = false;
            SwingUtilities.invokeLater(() -> {
                chatDialog.setVisible(true);
                chatDialog.reloadChat();
                chatDialog.getContentPane().revalidate();
                chatDialog.getContentPane().repaint();
                System.out.println("Repaint success(Visible)");
                chatDialog.setVisible(true);

            });
            System.out.println("Continue3");
            NEW_CHAT_WINDOW = false;
        } else {
            ASK_CHAT = false;
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

    @Override
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

        onlineListDialog = new OnlineListDialog(frame, userModel);
        new OnlineListListener(this);
    }

    public void replyDEBUG(String message) {
        System.out.println(message);
        //timeLimitedNotification(message);
    }

    @Override
    public void showGoalDescription(String CommonGoalCard) {

    }

    @Override
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

    public boolean isGO_TO_MENU() {
        return GO_TO_MENU;
    }

    public void setGO_TO_MENU(boolean GO_TO_MENU) {
        this.GO_TO_MENU = GO_TO_MENU;
    }

    public boolean isGAME_ON() {
        return GAME_ON;
    }

    public void setGAME_ON(boolean GAME_ON) {
        this.GAME_ON = GAME_ON;
    }

    public boolean isSTART() {
        return START;
    }

    public void setSTART(boolean START) {
        this.START = START;
    }


    public boolean isEND() {
        return END;
    }

    public void setEND(boolean END) {
        this.END = END;
    }

    public boolean isWAIT_SOCKET() {
        return WAIT_SOCKET;
    }

    public void setWAIT_SOCKET(boolean WAIT_SOCKET) {
        this.WAIT_SOCKET = WAIT_SOCKET;
    }

    public boolean isREFRESH() {
        return REFRESH;
    }

    public void setREFRESH(boolean REFRESH) {
        this.REFRESH = REFRESH;
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
}
