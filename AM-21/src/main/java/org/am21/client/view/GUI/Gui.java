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
import org.am21.client.view.TUI.Storage;
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
import java.util.HashMap;

import static org.am21.client.view.ClientView.maxSeats;

public class Gui implements View {
    public JFrame frame = new JFrame("MyShelfie");
    public ClientCommunicationController commCtrl;
    public IClientInput iClientInputHandler;
    public ClientCallBack clientCallBack;
    public String root;
    public static String username; //Client username
    public CommunicationInterface communicationInterface;
    public LoginInterface loginInterface;
    public ServerInfoInterface serverInfoInterface;
    public MenuActionInterface menuActionInterface;
    public WaitingRoomInterface waitingRoomInterface;
    public LivingRoomInterface livingRoomInterface;
    public LivingRoomMenuInterface livingRoomMenuInterface;
    public PersonalGoalPanel personalGoalPanel;
    public CommonGoalPanel commonGoalPanel;
    public GameBoardPanel gameBoardPanel;
    public MyHandBoardPanel myHandBoardPanel;
    public ShelfPanel myShelfPanel;

    public EnemyPanel enemyPanelA, enemyPanelB, enemyPanelC;
    public ShelfPanel enemyShelfPanelA, enemyShelfPanelB, enemyShelfPanelC;

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

    public JDialog notification;
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
    public Object chatLock = new Object();

    public ChatRunnable chatRun = new ChatRunnable(this);
    public Thread guiDialogMinion = new Thread(chatRun){
        public void setVisibleTrue(){
            chatRun.gui.chatDialog.setVisible(true);
        }
    };


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
        /*livingRoomMenuInterface.leaveMatch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: leave action
            }
        });*/

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
        commonGoalPanel = new CommonGoalPanel(ClientView.commonGoal.get(0), ClientView.commonGoal.get(1));
        //commonGoalPanel = new CommonGoalPanel("CommonGoal2Lines", "CommonGoalDiagonal");
        livingRoomInterface.livingRoomPane.add(commonGoalPanel, JLayeredPane.PALETTE_LAYER);

        //set CommonGoal Token
        commonGoalPanel.setScoreTokenTop(ClientView.commonGoalScore.get(0));
        //commonGoalPanel.setScoreTokenTop(2);
        commonGoalPanel.setScoreTokenBottom(ClientView.commonGoalScore.get(1));
        //commonGoalPanel.setScoreTokenBottom(4);
    }

    @Override
    public void showPersonalGoal() throws RemoteException {
        personalGoalPanel = new PersonalGoalPanel(ClientView.personalGoal);
        //personalGoalPanel = new PersonalGoalPanel(7);
        livingRoomInterface.livingRoomPane.add(personalGoalPanel, JLayeredPane.PALETTE_LAYER);
    }

    @Override
    public void announceCurrentPlayer() throws RemoteException {

    }

    @Override
    public void showWhoIsPlaying() {

    }

    @Override
    public void showPlayerShelf() throws RemoteException {
        //TODO: myShelfPanel.refreshShelf(ClientView.Shelves.get);
        //TODO: ClientView.Shelves
    }

    @Override
    public void showEveryShelf() throws RemoteException {

    }

    @Override
    public void showBoard() throws RemoteException {

        //set game Board
        for (int i = 0; i < Storage.BOARD_ROW; i++) {
            for (int j = 0; j < Storage.BOARD_COLUMN; j++) {
                if (ClientView.virtualBoard[i][j] != null && !gameBoardPanel.containItem(i, j)) {
                    gameBoardPanel.putItem(i, j, ClientView.virtualBoard[i][j], this);
                }
            }
        }

    }

    @Override
    public void showPlayersStats() throws RemoteException {

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
       /* livingRoomInterface.livingRoomPanel.insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //open the myHandInterface
                MyHandInterface myHandInterface = new MyHandInterface(ClientView.currentPlayerHand);
                myHandInterface.setVisible(true);


            }
        });*/
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

        livingRoomInterface = new LivingRoomInterface(frame);
        showPersonalGoal();
        showCommonGoals();


        if (maxSeats >= 9) {
            //setFirst enemy's Label
            enemyPanelA = new EnemyPanel(PixelUtil.commonY_1, ImageUtil.getBoardImage("enemyA"));
            livingRoomInterface.livingRoomPane.add(enemyPanelA, JLayeredPane.PALETTE_LAYER);

            //TODO: itemGrids will be manipulated
            enemyShelfPanelA = new ShelfPanel(PixelUtil.enemyGridX, PixelUtil.enemyAGridY, PixelUtil.enemyCellW, PixelUtil.enemyCellH, PixelUtil.enemyItemW, PixelUtil.enemyItemH);
            livingRoomInterface.livingRoomPane.add(enemyShelfPanelA, JLayeredPane.PALETTE_LAYER);
            //TODO: showEveryShelf ???
        }
        if (maxSeats >= 3) {
            //setSecond enemy's Label
            enemyPanelB = new EnemyPanel(PixelUtil.commonY_2, ImageUtil.getBoardImage("enemyB"));
            livingRoomInterface.livingRoomPane.add(enemyPanelB, JLayeredPane.PALETTE_LAYER);

            //TODO: itemGrids will be manipulated
            enemyShelfPanelB = new ShelfPanel(PixelUtil.enemyGridX, PixelUtil.enemyBGridY, PixelUtil.enemyCellW, PixelUtil.enemyCellH, PixelUtil.enemyItemW, PixelUtil.enemyItemH);
            livingRoomInterface.livingRoomPane.add(enemyShelfPanelB, JLayeredPane.PALETTE_LAYER);
            //TODO: showEveryShelf ???

        }
        if (maxSeats >= 4) {
            //setThird enemy's Label
            enemyPanelC = new EnemyPanel(PixelUtil.commonY_3, ImageUtil.getBoardImage("enemyC"));
            livingRoomInterface.livingRoomPane.add(enemyPanelC, JLayeredPane.PALETTE_LAYER);

            //TODO: itemGrids will be manipulated
            enemyShelfPanelC = new ShelfPanel(PixelUtil.enemyGridX, PixelUtil.enemyCGridY, PixelUtil.enemyCellW, PixelUtil.enemyCellH, PixelUtil.enemyItemW, PixelUtil.enemyItemH);
            livingRoomInterface.livingRoomPane.add(enemyShelfPanelC, JLayeredPane.PALETTE_LAYER);
            //TODO: showEveryShelf ???
        }
        //TODO: who is the chairMan (first current player when start the game)
        chairManLabel = new ChairManLabel(1);
        livingRoomInterface.livingRoomPane.add(chairManLabel, JLayeredPane.PALETTE_LAYER);

        //set my Hand
        myHandBoardPanel = new MyHandBoardPanel();
        livingRoomInterface.livingRoomPane.add(myHandBoardPanel, JLayeredPane.PALETTE_LAYER);


        //set initial game board
        gameBoardPanel = new GameBoardPanel(maxSeats);
        livingRoomInterface.livingRoomPane.add(gameBoardPanel, JLayeredPane.PALETTE_LAYER);

        showBoard();

        //set EndGameToken
        gameBoardPanel.setScoreTokenEndGame();


        //set my shelf
        //myShelfPanel = new ShelfPanel(PixelUtil.myGridX, PixelUtil.myGridY, PixelUtil.myCellW, PixelUtil.myCellH, PixelUtil.myItemW, PixelUtil.myItemH);
        //livingRoomInterface.livingRoomPane.add(myShelfPanel, JLayeredPane.PALETTE_LAYER);
        //showPlayerShelf();


       /* try {
            askInsertion();
        } catch (ServerNotActiveException e) {
            throw new RuntimeException(e);
        }*/


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
        //convertPrivateChatsForGUI();
        //convertPublicChatForGUI();
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




}
