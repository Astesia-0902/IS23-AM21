package org.am21.client.view;


import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public interface View {
    //String chooseView();

    void askLogin() throws ServerNotActiveException, RemoteException;
    void askAction() throws ServerNotActiveException, RemoteException;
    void askCreateGame() throws ServerNotActiveException, RemoteException;
    int askMaxSeats() throws RemoteException;
    void askJoinGame() throws ServerNotActiveException, RemoteException;
    void askLeaveGame() throws RemoteException;
    void showCommonGoals() throws RemoteException;
    void showPersonalGoal() throws RemoteException;
    void showCurrentPlayer() throws RemoteException;
    void showShelf() throws RemoteException;
    void showBoard() throws RemoteException;
    void showPlayersStats() throws RemoteException;
    void askPlayerMove() throws RemoteException, ServerNotActiveException;
    void askSelection() throws ServerNotActiveException, RemoteException;
    void askDeselection() throws ServerNotActiveException, RemoteException;
    void askInsertion() throws ServerNotActiveException, RemoteException;
    void askMessage() throws RemoteException;
    void askEndGameToken();
    void help() throws ServerNotActiveException, RemoteException;
    void showTimer();
}
