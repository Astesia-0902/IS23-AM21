package org.am21.client.view.GUI;

import org.am21.client.ClientCommunicationController;
import org.am21.client.SocketClient;
import org.am21.client.view.ClientView;
import org.am21.client.view.GUI.Interface.*;
import org.am21.client.view.GUI.listener.*;
import org.am21.client.view.GUI.utils.PathUtil;
import org.am21.client.view.View;
import org.am21.networkRMI.IClientInput;
import org.am21.networkRMI.Lobby;

import javax.imageio.ImageIO;
import javax.swing.*;
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
    public String root;
    public String username;
    public CommunicationInterface communicationInterface;
    public LoginInterface loginInterface;
    public ServerInfoInterface serverInfoInterface;
    public MenuActionInterface menuActionInterface;
    public WaitingRoomInterface waitingRoomInterface;
    public LivingRoomInterface livingRoomInterface;
    public ChatDialog chatDialog;
    public RuleDialog ruleDialog = new RuleDialog(frame);
    public OnlineListDialog onlineListDialog;
    public MatchListInterface matchListInterface;

    public static HashMap<String, JButton> chatPlayer = new HashMap<>();
    public static String chatUser;
    public static HashMap<String, JTextArea> chatHistory = new HashMap<>();
    public static boolean newPrivateChat = false;
    private SocketClient socket;

    public Gui() throws Exception {
        frame.setIconImage(ImageIO.read(new File(PathUtil.getPath("Publisher material/Icon 50x50px.png"))));
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        new RuleListener(this);
    }

    public void init() throws Exception {
        commCtrl = new ClientCommunicationController();
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

    public void askServerInfoSocket(){
        socket = new SocketClient();
        socket.start();
    }

    @Override
    public void askLogin() {
        loginInterface = new LoginInterface(frame);
        new LoginListener(this);
    }

    @Override
    public void askMenuAction() {
        menuActionInterface = new MenuActionInterface(frame);
        new MenuActionListener(this);
    }

    @Override
    public boolean askCreateMatch() throws Exception {
        livingRoomInterface = new LivingRoomInterface();
        new LivingRoomListener(this);
        return false;
    }

    @Override
    public int askMaxSeats() {
        menuActionInterface.maxSeatsDialog.setVisible(true);
        return 0;
    }

    public void askWaitingAction() {
        if (chatDialog != null){
            chatDialog.dispose();
            onlineListDialog.dispose();
        }
        waitingRoomInterface = new WaitingRoomInterface(frame);
        new WaitingRoomListener(this);
    }

    @Override
    public boolean askJoinMatch() {
        DefaultListModel<String> matchModel = new DefaultListModel<>();
        if (ClientView.matchList!=null) {
            String[] match = new String[ClientView.matchList.length];
            for (int i = 0; i < ClientView.matchList.length; i++) {
                match[i] = ClientView.matchList[i][0];
            }
            for (String m : match) {
                matchModel.addElement(m);
            }
        }

        // For test:
        matchModel.addElement("Match1");
        matchModel.addElement("Match2");
        matchModel.addElement("Match3");
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

    }

    @Override
    public void showPersonalGoal() throws RemoteException {

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

    }

    @Override
    public void handleChatMessage(String option) throws RemoteException {
        System.out.println(option);
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

    }

    @Override
    public void askShowObject() throws RemoteException {

    }

    public void askChat(){
        if (chatDialog == null || !chatDialog.isVisible() || newPrivateChat) {
            chatDialog = new ChatDialog(frame);
            newPrivateChat = false;
            new ChatListener(this);
        }
        chatDialog.getContentPane().revalidate();
        chatDialog.getContentPane().repaint();
//        if (chatDialog != null) {
//            //gui.chatDialog.setVisible(false);
//            chatDialog.dispose();
//        }
//        chatDialog = new ChatDialog(frame);

    }

    @Override
    public void showOnlinePlayer() throws RemoteException {
        DefaultListModel<String> userModel = new DefaultListModel<>();
        if (ClientView.onlinePlayers!=null) {
            String[] players = new String[ClientView.onlinePlayers.length];
            for (int i = 0; i < ClientView.onlinePlayers.length; i++) {
                players[i] = ClientView.onlinePlayers[i][0];
            }
            for (String player : players) {
                userModel.addElement(player);
            }
        }


        // For test:
        userModel.addElement("Player1");
        userModel.addElement("Player2");
        userModel.addElement("Player3");
        onlineListDialog = new OnlineListDialog(frame, userModel);
        new OnlineListListener(this);
    }

    @Override
    public void printer(String message) throws RemoteException {
        JOptionPane.showMessageDialog(frame,message,"Waring!",JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void showGoalDescription(String CommonGoalCard) {

    }

    @Override
    public void showGameRules() {
        ruleDialog.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            new Gui().init();
//            new Gui().printer("Error");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
