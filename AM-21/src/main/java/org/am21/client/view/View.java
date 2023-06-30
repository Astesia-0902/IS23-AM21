package org.am21.client.view;


import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

/**
 * View is an interface that is used to show the game to the player
 * and to ask him what he wants to do
 */
public interface View {
    //String chooseView();

    /**
     * This method is used to ask the player if he wants to login or register
     *
     * @throws Exception if the operation fails
     */
    void askLogin() throws Exception;

    /**
     * This method is used to ask the menu action to the player
     *
     * @throws Exception if the operation fails
     */
    void askMenuAction() throws Exception;

    /**
     * This method is used to ask the player if he wants to create a match
     *
     * @throws Exception if the operation fails
     */
    boolean askCreateMatch() throws Exception;

    /**
     * This method is used to ask the max number of players in the match
     *
     * @throws Exception if the operation fails
     */
    int askMaxSeats() throws Exception;

    /**
     * This method is used to ask the player if he wants to join a match
     *
     * @throws Exception if the operation fails
     */
    boolean askJoinMatch() throws Exception;

    /**
     * This method is used to ask the player if he wants to leave the match
     *
     * @throws RemoteException if the operation fails
     */
    boolean askLeaveMatch() throws RemoteException;

    /**
     * This method is used to ask the player if he wants to exit the game
     *
     * @throws RemoteException if the operation fails
     */
    boolean askExitGame() throws RemoteException;

    /**
     * This method is used to show the common goals
     *
     * @throws RemoteException if the operation fails
     */
    void showCommonGoals();

    /**
     * This method is used to show the personal goals
     *
     * @throws RemoteException if the operation fails
     */
    void showPersonalGoal() throws RemoteException;

    /**
     * This method is used to announce the current player
     *
     * @throws RemoteException if the operation fails
     */
    void announceCurrentPlayer() throws RemoteException;

    /**
     * This method is used to show the current player
     */
    void showWhoIsPlaying();

    /**
     * This method is used to show the player's shelf
     *
     * @throws RemoteException if the operation fails
     */
    void showPlayerShelf() throws RemoteException;

    /**
     * This method is used to show the every player's shelf
     *
     * @throws RemoteException if the operation fails
     */
    void showEveryShelf() throws RemoteException;

    /**
     * This method is used to show the board
     *
     * @throws RemoteException if the operation fails
     */
    void showBoard() throws RemoteException;

    /**
     * This method is used to show the player's stats
     *
     * @throws RemoteException if the operation fails
     */
    void showPlayersStats() throws RemoteException;

    /**
     * This method is used to ask the player's move
     *
     * @throws RemoteException if the operation fails
     */
    void askPlayerMove() throws RemoteException, ServerNotActiveException;

    /**
     * This method is used to ask the player's selection
     *
     * @throws ServerNotActiveException if the operation fails
     * @throws RemoteException          if the operation fails
     */
    void askSelection() throws ServerNotActiveException, RemoteException;

    /**
     * This method is used to ask deselection
     *
     * @throws ServerNotActiveException if the operation fails
     * @throws RemoteException          if the operation fails
     */
    void askDeselection() throws ServerNotActiveException, RemoteException;

    /**
     * This method is used to ask the player's insertion
     *
     * @throws ServerNotActiveException if the operation fails
     * @throws RemoteException          if the operation fails
     */
    void askInsertion() throws ServerNotActiveException, RemoteException;

    /**
     * This method is used to show the end game token
     *
     * @throws ServerNotActiveException if the operation fails
     * @throws RemoteException          if the operation fails
     */
    void showEndGameToken();

    /**
     * This method is used to show the timer
     *
     * @throws RemoteException if the operation fails
     */
    void showTimer();

    /**
     * This method is used to show the match list
     *
     * @throws RemoteException if the operation fails
     */
    void showMatchList() throws RemoteException;

    /**
     * This method is used to show the match setup
     *
     * @throws RemoteException if the operation fails
     */
    void showMatchSetup() throws RemoteException;

    /**
     * This method is used to show the object
     *
     * @throws RemoteException if the operation fails
     */
    void askShowObject() throws RemoteException;

    /**
     * This method is used to show the online player
     *
     * @throws RemoteException if the operation fails
     */
    void showOnlinePlayer() throws RemoteException;

    /**
     * This method is used to show the goal's description
     *
     * @param CommonGoalCard is the goal's description
     */
    void showGoalDescription(String CommonGoalCard);

    /**
     * This method is used to show the game rules
     */
    void showGameRules();

    /**
     * This method is handle the chat message
     *
     * @param command is the message
     * @param live    is true if the message is live
     */
    void handleChatMessage(String command, boolean live);
}
