package org.am21.view;


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


    void showCommonGoals(String username, int commonGoalCard);
    String showDescription(int CommonGoalCard);
    void showPersonalGoal(String username, Shelf personalGoalCard);
    void showCurrentPlayer(String username);
//
//    void askPlayerMove();
//
//    void showBoard();
//    void askSelection();
//    void askDeselection();
//    void askInsertion();
//    boolean askSort();
//    void askIndex(int item1, int item2);
//    void askColumn(int numColumn);
//
//    void askNextMove();
//
//

}
