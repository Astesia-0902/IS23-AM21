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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.am21.client.view.ClientView.maxSeats;

public class Gui implements View {
    public JFrame frame = new JFrame("MyShelfie");
    public ClientCommunicationController commCtrl;
    public IClientInput iClientInputHandler;
    public ClientCallBack clientCallBack;
    public String root;
    public String username;
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
    public HelpDialog helpDialog = new HelpDialog(frame);
    public OnlineListDialog onlineListDialog;
    public MatchListInterface matchListInterface;
    //-------------------------------------------------------------
    public static HashMap<String, JButton> myChatMap = new HashMap<>(); //chatPlayer
    public static String chatReceiver; //chatUser
    //Key: "Receiver", Value: Private chat History
    public static HashMap<String, JTextArea> privateChatHistoryMap = new HashMap<>();
    public static JTextArea publicChatHistory = new JTextArea();
    public static boolean NEW_PrivateChat = false;


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


    public Gui() throws Exception {
        this.clientCallBack = new ClientCallBack();
        this.clientCallBack.gui = this;
        frame.setIconImage(ImageIO.read(new File(PathUtil.getPath("Publisher material/Icon 50x50px.png"))));
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        new RuleListener(this);
        new HelpListener(this);
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
            menuActionInterface = new MenuActionInterface(frame);
            new MenuActionListener(this);
            System.out.println("MenuActionInterface done");
        } else if (menuActionInterface != null && NEED_NEW_FRAME) {
            System.out.println("New MenuAction Interface");
            menuActionInterface = new MenuActionInterface(frame);
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

    public int askChangeSeats(){
        waitingRoomInterface.maxSeatsDialog.setVisible(true);
        return 0;
    }

    public void askAssistMode() {
        helpDialog.setVisible(true);
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
        if (waitingRoomInterface == null || !waitingRoomInterface.isVisible()) {
            waitingRoomInterface = new WaitingRoomInterface(frame, numMiss, numMax);
            new WaitingRoomListener(this);
        }
        if (WAIT_ROOM_REFRESH) {
            SwingUtilities.invokeLater(() -> {
                waitingRoomInterface.reloadPlayerNumber(numMiss, numMax);
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
                        + "\t| Players: (" + ClientView.matchList[i][2] + "/" + ClientView.matchList[i][3] + ")";
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
        if(livingRoomMenuInterface!=null) {
            livingRoomMenuInterface.quitGame.addActionListener(e -> livingRoomInterface.dispose());
        }
        if(gameResultsInterface!=null) {
            gameResultsInterface.quitGame.addActionListener(e -> gameResultsInterface.dispose());
        }
        return commCtrl.leaveMatch();
    }

    @Override
    public boolean askExitGame() throws RemoteException {
        if(livingRoomMenuInterface!=null) {
            livingRoomMenuInterface.quitGame.addActionListener(e -> livingRoomInterface.dispose());
        }
        if(gameResultsInterface!=null) {
            gameResultsInterface.quitGame.addActionListener(e -> gameResultsInterface.dispose());
        }
        if (commCtrl.exitGame()) {
            System.exit(0);
            return true;
        }
        return false;
    }

    @Override
    public void showCommonGoals() {
        commonGoalPanel = new CommonGoalPanel(ClientView.commonGoal.get(0),ClientView.commonGoal.get(1));
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
        for (int i = 0; i < Storage.BOARD_ROW; i++){
            for (int j = 0; j < Storage.BOARD_COLUMN; j++){
                if(ClientView.virtualBoard[i][j]!=null&&!gameBoardPanel.containItem(i,j))
                {
                    gameBoardPanel.putItem(i, j,ClientView.virtualBoard[i][j]);
                }
            }
         }

        //for test
        //gameBoardPanel.putItem(3, 3, "_Games__1.1");
        //gameBoardPanel.putItem(4, 5, "_Frames_1.3");
        //gameBoardPanel.putItem(4, 3, "__Cats__1.3");
        //gameBoardPanel.putItem(4, 4, "__Cats__1.2");
        //gameBoardPanel.putItem(3, 4, "_Frames_1.1");


    }

    @Override
    public void showPlayersStats() throws RemoteException {

    }

    @Override
    public void askPlayerMove() throws RemoteException, ServerNotActiveException {

    }

    @Override
    public void askSelection() throws ServerNotActiveException, RemoteException {
        myHandBoardPanel.refreshItem(ClientView.currentPlayerHand);
    }

    @Override
    public void askDeselection() throws ServerNotActiveException, RemoteException {

        livingRoomInterface.livingRoomPanel.clearButton.addActionListener(e -> {
            myHandBoardPanel.refreshItem(ClientView.currentPlayerHand);
            gameBoardPanel.clearAll();
            JOptionPane.showMessageDialog(null, "clear successful");

        });
    }

    @Override
    public void askInsertion() throws ServerNotActiveException, RemoteException {
        livingRoomInterface.livingRoomPanel.insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //open the myHandInterface
                MyHandInterface myHandInterface = new MyHandInterface(ClientView.currentPlayerHand);
                myHandInterface.setVisible(true);


            }
        });
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
        //new LivingRoomListener(this);
        showPersonalGoal();
        showCommonGoals();

         if(maxSeats<=2)
        {
            //setFirst enemy's Label
            enemyPanelA = new EnemyPanel(PixelUtil.commonY_1, ImageUtil.getBoardImage("enemyA"));
            livingRoomInterface.livingRoomPane.add(enemyPanelA, JLayeredPane.PALETTE_LAYER);

            //TODO: itemGrids will be manipulated
            enemyShelfPanelA = new ShelfPanel(PixelUtil.enemyGridX, PixelUtil.enemyAGridY, PixelUtil.enemyCellW, PixelUtil.enemyCellH, PixelUtil.enemyItemW, PixelUtil.enemyItemH);
            livingRoomInterface.livingRoomPane.add(enemyShelfPanelA, JLayeredPane.PALETTE_LAYER);
            //TODO: showEveryShelf ???
        }
        if(maxSeats<=3)
        {
            //setSecond enemy's Label
            enemyPanelB = new EnemyPanel(PixelUtil.commonY_2, ImageUtil.getBoardImage("enemyB"));
            livingRoomInterface.livingRoomPane.add(enemyPanelB, JLayeredPane.PALETTE_LAYER);

            //TODO: itemGrids will be manipulated
            enemyShelfPanelB = new ShelfPanel(PixelUtil.enemyGridX, PixelUtil.enemyBGridY, PixelUtil.enemyCellW, PixelUtil.enemyCellH, PixelUtil.enemyItemW, PixelUtil.enemyItemH);
            livingRoomInterface.livingRoomPane.add(enemyShelfPanelB, JLayeredPane.PALETTE_LAYER);
            //TODO: showEveryShelf ???

        }
        if(maxSeats<=4)
        {
            //setThird enemy's Label
            enemyPanelC = new EnemyPanel(PixelUtil.commonY_3, ImageUtil.getBoardImage("enemyC"));
            livingRoomInterface.livingRoomPane.add(enemyPanelC, JLayeredPane.PALETTE_LAYER);

            //TODO: itemGrids will be manipulated
            enemyShelfPanelC = new ShelfPanel(PixelUtil.enemyGridX, PixelUtil.enemyCGridY, PixelUtil.enemyCellW, PixelUtil.enemyCellH, PixelUtil.enemyItemW, PixelUtil.enemyItemH);
            livingRoomInterface.livingRoomPane.add(enemyShelfPanelC, JLayeredPane.PALETTE_LAYER);
            //TODO: showEveryShelf ???
        }
        //TODO: who is the chairMan (first current player when start the game)
        chairManLabel = new ChairManLabel(2);
        livingRoomInterface.livingRoomPane.add(chairManLabel, JLayeredPane.PALETTE_LAYER);

        //set initial game board
        gameBoardPanel = new GameBoardPanel(maxSeats);
        //gameBoardPanel = new GameBoardPanel(4);
        livingRoomInterface.livingRoomPane.add(gameBoardPanel, JLayeredPane.PALETTE_LAYER);

        showBoard();

        //set EndGameToken
        gameBoardPanel.setScoreTokenEndGame();

        //set my Hand
        myHandBoardPanel = new MyHandBoardPanel();
        livingRoomInterface.livingRoomPane.add(myHandBoardPanel, JLayeredPane.PALETTE_LAYER);

        //set my shelf
        myShelfPanel = new ShelfPanel(PixelUtil.myGridX,PixelUtil.myGridY,PixelUtil.myCellW,PixelUtil.myCellH,PixelUtil.myItemW,PixelUtil.myItemH);
        livingRoomInterface.livingRoomPane.add(myShelfPanel,JLayeredPane.PALETTE_LAYER);
        showPlayerShelf();

        //setButton Function
        try {
            askDeselection();
        } catch (ServerNotActiveException e) {
            throw new RuntimeException(e);
        }

        try {
            askInsertion();
        } catch (ServerNotActiveException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void askShowObject() throws RemoteException {

    }

    public void print(String message){
        JOptionPane.showMessageDialog(frame, message);
    }

    public void askChat() {
        convertPrivateChatsForGUI();
        if (chatDialog == null || !chatDialog.isVisible() || NEW_PrivateChat) {
            chatDialog = new ChatDialog(frame);
            NEW_PrivateChat = false;
            new ChatListener(this);
            //runChatMinion();
        }else {
            chatDialog.reloadData();
            chatDialog.getContentPane().revalidate();
            chatDialog.getContentPane().repaint();
        }
    }

    @Override
    public void showOnlinePlayer() throws RemoteException {
        DefaultListModel<String> userModel = new DefaultListModel<>();
        for (int i = 0; i < ClientView.onlinePlayers.length; i++) {
            if (ClientView.onlinePlayers[i][0] != null) {
                if(ClientView.onlinePlayers[i][0].equals(username)){
                    userModel.addElement(ClientView.onlinePlayers[i][0] + "  |  " + ClientView.onlinePlayers[i][1] + " (You) ");
                }else {
                    userModel.addElement(ClientView.onlinePlayers[i][0] + "  |  " + ClientView.onlinePlayers[i][1]);
                }
            }

        }

        onlineListDialog = new OnlineListDialog(frame, userModel);
        new OnlineListListener(this);


        //OnlineList onlineList = new OnlineList(frame);
    }

    public void replyDEBUG(String message) {
        System.out.println(message);
    }

    @Override
    public void showGoalDescription(String CommonGoalCard) {

    }

    @Override
    public void showGameRules() {
        ruleDialog.setVisible(true);
    }

    /*public static void main(String[] args) {
        try {
            new Gui().init();
//            new Gui().printer("Error");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/


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


    public void runChatMinion() {



    }


    public void convertPrivateChatsForGUI() {
        HashMap<String, JTextArea> chatMap = new HashMap<>();
        List<JTextArea> visualChats = new ArrayList<>();
        System.out.println("Convert Private Chats");
        if (ClientView.privateChats != null && !ClientView.privateChats.isEmpty()) {
            List<List<String>> privateChatsList = ClientView.privateChats;
            for (List<String> chat : privateChatsList) {
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
                System.out.println("CHAT update");
            }

            // ChatMap (Keys)
            if (ClientView.chatMap != null && !ClientView.chatMap.isEmpty()) {
                for (Map.Entry<String, Integer> entry : ClientView.chatMap.entrySet()) {
                    String key = entry.getKey();
                    if (key.startsWith(username) || key.endsWith(username)) {
                        String[] newKey = key.split("@");
                        String receiver = "";
                        if (newKey[0].equals(username)) {
                            receiver = newKey[1];
                        }else if(newKey[1].equals(username)){
                            receiver = newKey[0];
                        }

                        int value = entry.getValue();
                        //Insert key(receiver) and JTextArea of the Private Chat
                        chatMap.put(receiver, visualChats.get(value));
                    }
                }

            }

        }
        // Finally
        privateChatHistoryMap = chatMap;

    }

}
