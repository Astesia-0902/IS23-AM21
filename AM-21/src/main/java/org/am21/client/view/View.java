package org.am21.client.view;


import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public interface View {
    //String chooseView();

    void askLogin() throws ServerNotActiveException, RemoteException;
    void askMenuAction() throws ServerNotActiveException, RemoteException;
    boolean askCreateMatch() throws ServerNotActiveException, RemoteException;
    int askMaxSeats() throws RemoteException;
    boolean askJoinMatch() throws ServerNotActiveException, RemoteException;
    boolean askLeaveMatch() throws RemoteException;
    boolean askExitGame() throws RemoteException;
    void showCommonGoals();
    void showPersonalGoal() throws RemoteException;
    void showCurrentPlayer() throws RemoteException;
    void showShelf() throws RemoteException;
    void showBoard() throws RemoteException;
    void showPlayersStats() throws RemoteException;
    void askPlayerMove() throws RemoteException, ServerNotActiveException;
    void askSelection() throws ServerNotActiveException, RemoteException;
    void askDeselection() throws ServerNotActiveException, RemoteException;
    void askInsertion() throws ServerNotActiveException, RemoteException;
    void handleChatMessage(String option) throws RemoteException;
    void showEndGameToken();
    void showTimer();
    void showMatchList() throws RemoteException;
    void showMatchSetup() throws RemoteException;
    void askShowObject() throws RemoteException;
    void showOnlinePlayer() throws RemoteException;
    void printer(String message) throws RemoteException;
}
