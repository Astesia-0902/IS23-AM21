package org.am21.client.view;


import org.am21.model.items.Shelf;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public interface View {
    //String chooseView();

    void askLogin() throws ServerNotActiveException, RemoteException;
    void askAction() throws ServerNotActiveException, RemoteException;
    void askCreateGame() throws ServerNotActiveException, RemoteException;
    int askMaxSeats();
    void askJoinGame() throws ServerNotActiveException, RemoteException;
    void askLeaveGame() throws RemoteException;
    void showCommonGoals();
    void GoalDescription(int CommonGoalCard);
    void showPersonalGoal();
    void showCurrentPlayer();
    void showShelf(Shelf userShelf);
    void showBoard();
    void showPlayersStats();
    void askPlayerMove() throws RemoteException, ServerNotActiveException;
    void askSelection() throws ServerNotActiveException, RemoteException;
    void askDeselection() throws ServerNotActiveException, RemoteException;
    void askInsertion() throws ServerNotActiveException, RemoteException;
    void askMessage();
    void askEndGameToken();
    void help() throws ServerNotActiveException, RemoteException;
    void showTimer();
}
