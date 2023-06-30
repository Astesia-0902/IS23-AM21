package org.am21.networkRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

/**
 * Interface for RMI communication, any class that will be used by the client must implement this interface
 *
 * @version 1.0
 */
public interface IClientInput extends Remote {
    /**
     * This method is used to join a match
     *
     * @param matchID the match id
     * @return true if the match is joined successfully, false otherwise
     * @throws RemoteException          if the remote object is not available
     * @throws ServerNotActiveException if the server is not active
     */
    boolean joinGame(int matchID) throws RemoteException, ServerNotActiveException;

    /**
     * This method is used to check if the player is the current player
     *
     * @return true if the player is the current player, false otherwise
     * @throws RemoteException          if the remote object is not available
     * @throws ServerNotActiveException if the server is not active
     */
    boolean checkPlayerActionPhase() throws RemoteException, ServerNotActiveException;

    /**
     * This method is used to log in
     *
     * @param username       the username
     * @param clientCallBack the client callback
     * @return true if the player is logged in successfully, false otherwise
     * @throws RemoteException          if the remote object is not available
     * @throws ServerNotActiveException if the server is not active
     */
    boolean logIn(String username, IClientCallBack clientCallBack) throws RemoteException, ServerNotActiveException;

    /**
     * This method is used to create a match
     *
     * @param playerNum the number of players
     * @return true if the match is created successfully, false otherwise
     * @throws RemoteException          if the remote object is not available
     * @throws ServerNotActiveException if the server is not active
     */
    boolean createMatch(int playerNum) throws RemoteException, ServerNotActiveException;

    /**
     * This method is used to select a cell
     *
     * @param row the row of the cell
     * @param col the column of the cell
     * @return true if the cell is selected successfully, false otherwise
     * @throws RemoteException          if the remote object is not available
     * @throws ServerNotActiveException if the server is not active
     */
    boolean selectCell(int row, int col) throws RemoteException, ServerNotActiveException;

    /**
     * This method is used to confirm the selection
     *
     * @return true if the selection is confirmed successfully, false otherwise
     * @throws RemoteException          if the remote object is not available
     * @throws ServerNotActiveException if the server is not active
     */
    boolean confirmSelection() throws RemoteException, ServerNotActiveException;

    /**
     * This method is used to insert in the pattern card
     *
     * @param colNum the column number
     * @return true if the cell is inserted successfully, false otherwise
     * @throws RemoteException          if the remote object is not available
     * @throws ServerNotActiveException if the server is not active
     */
    boolean insertInColumn(int colNum) throws RemoteException, ServerNotActiveException;

    /**
     * This method is used to deselect the cards
     *
     * @return true if the cards are deselected successfully, false otherwise
     * @throws RemoteException          if the remote object is not available
     * @throws ServerNotActiveException if the server is not active
     */
    boolean deselectCards() throws RemoteException, ServerNotActiveException;

    /**
     * This method is used to sort the hand
     *
     * @param pos1 the first position
     * @param pos2 the second position
     * @return true if the hand is sorted successfully, false otherwise
     * @throws RemoteException          if the remote object is not available
     * @throws ServerNotActiveException if the server is not active
     */
    boolean sortHand(int pos1, int pos2) throws RemoteException, ServerNotActiveException;

    /**
     * This method is used to leave the match
     *
     * @return true if the player leaves the match successfully, false otherwise
     * @throws RemoteException if the remote object is not available
     */
    boolean leaveMatch() throws RemoteException;

    /**
     * This method is used to exit the game
     *
     * @return true if the player exits the game successfully, false otherwise
     * @throws RemoteException if the remote object is not available
     */
    boolean exitGame() throws RemoteException;

    /**
     * This method is used to register the callback
     *
     * @param path the path of the callback
     * @throws RemoteException if the remote object is not available
     */
    void registerCallBack(String path) throws RemoteException;

    /**
     * This method is used to check if the round is finished
     *
     * @return true if the round is finished, false otherwise
     * @throws RemoteException          if the remote object is not available
     * @throws ServerNotActiveException if the server is not active
     */
    boolean endTurn() throws RemoteException, ServerNotActiveException;

    /**
     * This method is used to change the match seats
     *
     * @param newMaxSeats the new max seats
     * @return true if the seats are changed successfully, false otherwise
     * @throws RemoteException if the remote object is not available
     */
    boolean changeMatchSeats(int newMaxSeats) throws RemoteException;

    /**
     * This method is used to send a public message
     *
     * @param message the message
     * @param live    if the message is live
     * @return true if the message is sent successfully, false otherwise
     * @throws RemoteException if the remote object is not available
     */
    boolean sendPublicMessage(String message, boolean live) throws RemoteException;

    /**
     * This method is used to send a private message
     *
     * @param message  the message
     * @param receiver the receiver
     * @param live     if the message is live
     * @return true if the message is sent successfully, false otherwise
     * @throws RemoteException if the remote object is not available
     */
    boolean sendPrivateMessage(String message, String receiver, boolean live) throws RemoteException;
}

