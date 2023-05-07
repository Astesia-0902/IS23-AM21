package org.am21.client.view.GUI;

import org.am21.client.view.GUI.Interface.*;
import org.am21.client.view.GUI.listener.*;
import org.am21.client.view.View;

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public class Gui implements View {
    public JFrame frame = new JFrame("MyShelfie");
    public CommunicationInterface communicationInterface;
    public LoginInterface loginInterface;
    public ServerInfoInterface serverInfoInterface;
    public MenuActionInterface menuActionInterface;
    public MaxSeatsInterface maxSeatsInterface;
    public WaitingRoomInterface waitingRoomInterface;
    public LivingRoomInterface livingRoomInterface;


    public Gui() {


    }

    public void init() throws Exception {
        communicationInterface = new CommunicationInterface();
        new CommunicationListener(this);

    }

    public void askServerInfo() throws Exception {
        serverInfoInterface = new ServerInfoInterface();
        new ServerInfoListener(this);
    }

    @Override
    public void askLogin() throws Exception {
        loginInterface = new LoginInterface();
        new LoginListener(this);
    }

    @Override
    public void askMenuAction() throws Exception {
        menuActionInterface = new MenuActionInterface();
        new MenuActionListener(this);
    }

    @Override
    public boolean askCreateMatch() throws Exception {
        maxSeatsInterface = new MaxSeatsInterface();
        new MaxSeatsListener(this);
//        livingRoomInterface = new LivingRoomInterface();
//        new LivingRoomListener(this);
        return false;
    }

    @Override
    public int askMaxSeats() throws RemoteException {
        return 0;
    }

    @Override
    public boolean askJoinMatch() throws Exception {
        waitingRoomInterface = new WaitingRoomInterface();
        new WaitingRoomListener(this);
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

    }

    public static void main(String[] args) {
        try {
            new Gui().init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
