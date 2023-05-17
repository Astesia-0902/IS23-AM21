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
    public PersonalGoalPanel personalGoalPanel;
    public CommonGoalPanel commonGoalPanel;
    public GameBoardPanel gameBoardPanel;
    public MyHandBoardPanel myHandBoardPanel;


    public EnemyPanel enemyPanelA, enemyPanelB, enemyPanelC;
    public ShelfPanel enemyShelfPanelA, enemyShelfPanelB, enemyShelfPanelC;
    public ChairManLabel chairManLabel;
    public ChatDialog chatDialog;
    public RuleDialog ruleDialog = new RuleDialog(frame);
    public HelpDialog helpDialog = new HelpDialog(frame);
    public OnlineListDialog onlineListDialog;
    public MatchListInterface matchListInterface;

    public static HashMap<String, JButton> chatPlayer = new HashMap<>();
    public static String chatUser;
    public static HashMap<String, JTextArea> chatHistory = new HashMap<>();
    public static boolean newPrivateChat = false;
    private SocketClient socket;
    private boolean GO_TO_MENU = true;
    //If true askPlayerMove, if false askWaitingAction
    public boolean GAME_ON = false;
    private boolean START = false;
    private boolean SEL_MODE = true;
    private boolean NOT_SEL_YET = true;
    //If true GoToEndRoom
    private boolean END = false;
    public boolean WAIT_SOCKET = false;

    public boolean REFRESH = false;

    public Thread guiMinion = new Thread(){
        @Override
        public void run() {
            //super.run();
            while(!GAME_ON){
                try {
                    //checkGUISTATE();
                    askWaitingAction();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }

            if (waitingRoomInterface!=null) {
                waitingRoomInterface.dispose();
            }
            try {
                showMatchSetup();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

        }
    };



    public Gui() throws Exception {
        this.clientCallBack = new ClientCallBack();
        this.clientCallBack.gui= this;
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
            root = lobby.connect();
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
        menuActionInterface = new MenuActionInterface(frame);
        new MenuActionListener(this);

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

    public void askAssistMode() {
        helpDialog.setVisible(true);
    }

    public void askWaitingAction() throws RemoteException {
        //synchronized (guiMinion) {
            if (chatDialog != null) {
                chatDialog.dispose();
                onlineListDialog.dispose();
            }

        if (waitingRoomInterface == null || !waitingRoomInterface.isVisible()) {
            waitingRoomInterface = new WaitingRoomInterface(frame);
            new WaitingRoomListener(this);
        }
        waitingRoomInterface.getContentPane().revalidate();
        waitingRoomInterface.getContentPane().repaint();


            /*try {
                while (REFRESH){
                    guiMinion.wait();
                }
                waitingRoomInterface.dispose();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }*/
        //}
    }

    @Override
    public boolean askJoinMatch() {
        DefaultListModel<String> matchModel = new DefaultListModel<>();
        if (ClientView.matchList != null) {
            String[] match = new String[ClientView.matchList.length];
            for (int i = 0; i < ClientView.matchList.length; i++) {
                match[i] = ClientView.matchList[i][0] + "  |  "+ ClientView.matchList[i][1]
                        + "  |  "+ ClientView.matchList[i][2] + "  |  "+ ClientView.matchList[i][3];
            }
            for (String m : match) {
                matchModel.addElement(m);
            }
        }

        // For test:
        matchModel.addElement("0  |  Match1  |  WaitingPlayers  |  Players: (1/2)");
        matchModel.addElement("1  |  Match2  |  GameGoing       |  Players: (2/2)");
        matchModel.addElement("2  |  Match3  |  Closed          |  Players: (0/2)");
        matchListInterface = new MatchListInterface(frame, matchModel);
        new MatchListListener(this);

        return false;
    }

    @Override
    public boolean askLeaveMatch() throws RemoteException {
        return false;
    }

    @Override
    public boolean askExitGame() throws RemoteException {

        return false;
    }

    @Override
    public void showCommonGoals() {
        //TODO: commonGoalPanel = new CommonGoalPanel(ClientView.commonGoal.get(0),ClientView.commonGoal.get(1));
        commonGoalPanel = new CommonGoalPanel("CommonGoal2Lines", "CommonGoalDiagonal");
        livingRoomInterface.livingRoomPane.add(commonGoalPanel, JLayeredPane.PALETTE_LAYER);

        //set CommonGoal Token
        //TODO:  commonGoalPanel.setScoreTokenTop(ClientView.commonGoalScore.get(0));
        commonGoalPanel.setScoreTokenTop(2);
        //TODO:  commonGoalPanel.setScoreTokenBottom(ClientView.commonGoalScore.get(1));
        commonGoalPanel.setScoreTokenBottom(4);
    }

    @Override
    public void showPersonalGoal() throws RemoteException {
        //TODO: personalGoalPanel = new PersonalGoalPanel(ClientView.personalGoal);
        personalGoalPanel = new PersonalGoalPanel(7);
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

    }

    @Override
    public void showEveryShelf() throws RemoteException {

    }

    @Override
    public void showBoard() throws RemoteException {
        //set my Hand
        myHandBoardPanel = new MyHandBoardPanel();
        livingRoomInterface.livingRoomPane.add(myHandBoardPanel,JLayeredPane.PALETTE_LAYER);

        //set game Board
        //TODO: for (int i = 0; i < Storage.BOARD_ROW; i++){
        //TODO:    for (int j = 0; j < Storage.BOARD_COLUMN; j++){
        //TODO:        if(ClientView.virtualBoard[i][j]!=null&&!gameBoardPanel.containItem(i,j))
        //TODO:        {
        //TODO:            gameBoardPanel.putItem(i, j,ClientView.virtualBoard[i][j],myHandBoardPanel);
        //TODO:        }
        //TODO:    }
        //TODO: }

        gameBoardPanel.putItem(3,3,"_Games__1.1",myHandBoardPanel);
        gameBoardPanel.putItem(4,5,"_Frames_1.3",myHandBoardPanel);
        gameBoardPanel.putItem(4,3,"__Cats__1.3",myHandBoardPanel);
        gameBoardPanel.putItem(4,4,"__Cats__1.2",myHandBoardPanel);
        gameBoardPanel.putItem(3,4,"_Frames_1.1",myHandBoardPanel);


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
        livingRoomInterface.livingRoomPanel.clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                while(myHandBoardPanel.myHandNum>0){
                    myHandBoardPanel.removeItem();

                }
                JOptionPane.showMessageDialog(null, "clear successful");
            }
        });
    }

    @Override
    public void askInsertion() throws ServerNotActiveException, RemoteException {
        livingRoomInterface.livingRoomPanel.insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                MyHandInterface myHandInterface = new MyHandInterface();
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

        //TODO: livingRoomInterface = new LivingRoomInterface(frame);
        livingRoomInterface = new LivingRoomInterface(frame);
        //new LivingRoomListener(this);
        showPersonalGoal();
        showCommonGoals();

        //TODO: if(maxSeats<=2)
        {
            //setFirst enemy's Label
            enemyPanelA = new EnemyPanel(PixelUtil.commonY_1, ImageUtil.getBoardImage("enemyA"));
            livingRoomInterface.livingRoomPane.add(enemyPanelA, JLayeredPane.PALETTE_LAYER);

            //TODO: itemGrids will be manipulated
            enemyShelfPanelA = new ShelfPanel(PixelUtil.enemyGridX, PixelUtil.enemyAGridY, PixelUtil.enemyCellW, PixelUtil.enemyCellH, PixelUtil.enemyItemW, PixelUtil.enemyItemH);
            livingRoomInterface.livingRoomPane.add(enemyShelfPanelA, JLayeredPane.PALETTE_LAYER);
        }
        //TODO: if(maxSeats<=3)
        {
            //setSecond enemy's Label
            enemyPanelB = new EnemyPanel(PixelUtil.commonY_2, ImageUtil.getBoardImage("enemyB"));
            livingRoomInterface.livingRoomPane.add(enemyPanelB, JLayeredPane.PALETTE_LAYER);

            //TODO: itemGrids will be manipulated
            enemyShelfPanelB = new ShelfPanel(PixelUtil.enemyGridX, PixelUtil.enemyBGridY, PixelUtil.enemyCellW, PixelUtil.enemyCellH, PixelUtil.enemyItemW, PixelUtil.enemyItemH);
            livingRoomInterface.livingRoomPane.add(enemyShelfPanelB, JLayeredPane.PALETTE_LAYER);


        }
        //TODO: if(maxSeats<=4)
        {
            //setThird enemy's Label
            enemyPanelC = new EnemyPanel(PixelUtil.commonY_3, ImageUtil.getBoardImage("enemyC"));
            livingRoomInterface.livingRoomPane.add(enemyPanelC, JLayeredPane.PALETTE_LAYER);

            //TODO: itemGrids will be manipulated
            enemyShelfPanelC = new ShelfPanel(PixelUtil.enemyGridX, PixelUtil.enemyCGridY, PixelUtil.enemyCellW, PixelUtil.enemyCellH, PixelUtil.enemyItemW, PixelUtil.enemyItemH);
            livingRoomInterface.livingRoomPane.add(enemyShelfPanelC, JLayeredPane.PALETTE_LAYER);

        }
        //TODO: who is the chairMan (first current player when start the game)
        chairManLabel = new ChairManLabel(2);
        livingRoomInterface.livingRoomPane.add(chairManLabel, JLayeredPane.PALETTE_LAYER);

        //set initial game board
        //TODO: gameBoardPanel = new GameBoardPanel(maxSeats);
        gameBoardPanel = new GameBoardPanel(4);
        livingRoomInterface.livingRoomPane.add(gameBoardPanel, JLayeredPane.PALETTE_LAYER);

        showBoard();

        //set EndGameToken
        gameBoardPanel.setScoreTokenEndGame();

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

    public void askChat() {
        if (chatDialog == null || !chatDialog.isVisible() || newPrivateChat) {
            chatDialog = new ChatDialog(frame);
            newPrivateChat = false;
            new ChatListener(this);
        }
        chatDialog.getContentPane().revalidate();
        chatDialog.getContentPane().repaint();

    }

    @Override
    public void showOnlinePlayer() throws RemoteException {
        DefaultListModel<String> userModel = new DefaultListModel<>();
        if (ClientView.onlinePlayers != null) {
            for (int i = 0; i < ClientView.onlinePlayers.length; i++) {
                if (ClientView.onlinePlayers[i][0]!=null) {
                    userModel.addElement(ClientView.onlinePlayers[i][0] + "  |  " + ClientView.onlinePlayers[i][1]);
                }
            }
        }


        // For test:
        userModel.addElement("Player1  |  GameMember");
        userModel.addElement("Player2  |  Online");
        userModel.addElement("Player3  |  GameMember");
        onlineListDialog = new OnlineListDialog(frame, userModel);
        new OnlineListListener(this);


        //OnlineList onlineList = new OnlineList(frame);
    }

    public void replyDEBUG(String message){
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

    public boolean isSEL_MODE() {
        return SEL_MODE;
    }

    public void setSEL_MODE(boolean SEL_MODE) {
        this.SEL_MODE = SEL_MODE;
    }

    public boolean isNOT_SEL_YET() {
        return NOT_SEL_YET;
    }

    public void setNOT_SEL_YET(boolean NOT_SEL_YET) {
        this.NOT_SEL_YET = NOT_SEL_YET;
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
/*
    private void checkGUISTATE() throws RemoteException {
        if (END) {
            //TODO:
            //goToEndRoom();
        }
        if (START) {
            waitingRoomInterface.dispose();
            showMatchSetup();
            setSTART(false);
        }
        if (GO_TO_MENU) {
            askMenuAction();
        } else if (!GAME_ON) {
            askWaitingAction();
        }else{
            showMatchSetup();
        }

    }*/

    /*public void wakeMinion(){
        synchronized (guiMinion) {
            guiMinion.notifyAll();
        }
    }*/


}
