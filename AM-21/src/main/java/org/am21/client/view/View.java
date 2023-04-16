package org.am21.client.view;


import org.am21.model.items.Shelf;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.List;

public interface View {
    //String chooseView();

    void askLogin() throws ServerNotActiveException, RemoteException;
    void askAction() throws ServerNotActiveException, RemoteException;

    void askCreateGame() throws ServerNotActiveException, RemoteException;
    int askMaxSeats();
    void askJoinGame() throws ServerNotActiveException, RemoteException;
    void askLeaveGame() throws RemoteException;


    void showCommonGoals();
    String GoalDescription(int CommonGoalCard);
    void showPersonalGoal();
    void showCurrentPlayer();
    void showShelf(Shelf userShelf);
    void showBoard();
    void showPlayersStats();
    void askPlayerMove() throws RemoteException, ServerNotActiveException;
    void askSelection() throws ServerNotActiveException, RemoteException;
    List<Integer> askCoordinates();
    void showItemInCell(int row, int column);
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
