package org.am21.client.view;


import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public interface View {
    //String chooseView();

    void askLogin() throws Exception;
    void askMenuAction() throws Exception;
    boolean askCreateMatch() throws Exception;
    int askMaxSeats() throws Exception;
    boolean askJoinMatch() throws Exception;
    boolean askLeaveMatch() throws RemoteException;
    boolean askExitGame() throws RemoteException;
    void showCommonGoals();
    void showPersonalGoal() throws RemoteException;
    void announceCurrentPlayer() throws RemoteException;

    void showWhoIsPlaying();

    void showPlayerShelf() throws RemoteException;

    void showEveryShelf() throws RemoteException;

    void showBoard() throws RemoteException;
    void showPlayersStats() throws RemoteException;
    void askPlayerMove() throws RemoteException, ServerNotActiveException;
    void askSelection() throws ServerNotActiveException, RemoteException;
    void askDeselection() throws ServerNotActiveException, RemoteException;
    void askInsertion() throws ServerNotActiveException, RemoteException;
    void showEndGameToken();
    void showTimer();
    void showMatchList() throws RemoteException;
    void showMatchSetup() throws RemoteException;
    void askShowObject() throws RemoteException;
    void showOnlinePlayer() throws RemoteException;


    void showGoalDescription(String CommonGoalCard);

    void showGameRules();

    void handleChatMessage(String command, boolean live);
}
