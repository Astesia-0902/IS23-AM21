package org.am21.client.view.GUI;

import org.am21.client.view.GUI.Interface.*;
import org.am21.client.view.GUI.listener.*;
import org.am21.client.view.GUI.utils.PathUtil;
import org.am21.client.view.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public class Gui implements View {
    public JFrame frame = new JFrame("MyShelfie");
    public CommunicationInterface communicationInterface;
    public LoginInterface loginInterface;
    public ServerInfoInterface serverInfoInterface;
    public MenuActionInterface menuActionInterface;
    public WaitingRoomInterface waitingRoomInterface;
    public LivingRoomInterface livingRoomInterface;


    public Gui() throws Exception {
        frame.setIconImage(ImageIO.read(new File(PathUtil.getPath("Publisher material/Icon 50x50px.png"))));
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void init() throws Exception {
        communicationInterface = new CommunicationInterface(frame);
        new CommunicationListener(this);

    }

    public void askServerInfo() throws Exception {
        serverInfoInterface = new ServerInfoInterface(frame);
        new ServerInfoListener(this);
    }

    @Override
    public void askLogin() throws Exception {
        loginInterface = new LoginInterface(frame);
        new LoginListener(this);
    }

    @Override
    public void askMenuAction() throws Exception {
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
    public int askMaxSeats() throws RemoteException {
        menuActionInterface.maxSeatsDialog.setVisible(true);
        return 0;
    }

    public void askWaitingAction() throws Exception {
        waitingRoomInterface = new WaitingRoomInterface(frame);
        new WaitingRoomListener(this);
    }

    @Override
    public boolean askJoinMatch() throws Exception {

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

    @Override
    public void showOnlinePlayer() throws RemoteException {

    }

    @Override
    public void printer(String message) throws RemoteException {

    }

    @Override
    public void showGoalDescription(String CommonGoalCard) {

    }

    @Override
    public void showGameRules() {
        waitingRoomInterface.ruleDialog.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            new Gui().askMenuAction();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
