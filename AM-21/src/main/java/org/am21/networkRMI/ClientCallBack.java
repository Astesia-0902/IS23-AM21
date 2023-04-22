package org.am21.networkRMI;

import org.am21.client.view.JSONConverter;
import org.am21.client.view.cli.Cli;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * To call the method in the client from the server, go to the PlayController.clientInput.callback
 */
public class ClientCallBack extends UnicastRemoteObject implements IClientCallBack{
    public Cli cli;
    public ClientCallBack() throws RemoteException {

    }

    @Override
    public void sendMessageFromServer(String message) throws RemoteException {
        //TODO:Print the message from server
    }

    /**
     * Update the virtual view
     * call this method when the game state changes
     * @param virtualView
     * @throws RemoteException
     */
    @Override
    public void sendVirtualView(String virtualView) throws RemoteException {
        //TODO:Update the virtual view
        JSONConverter.setViewVariables(virtualView);
    }

    @Override
    public void sendChatMessage(String message) throws RemoteException {

    }

    /**
     * Each CLI will receive the notification when the match has officially begun
     * @throws RemoteException
     */
    @Override
    public void notifyStart() throws RemoteException{
        //TODO: Method invocation in CLI (for example: showMatchSetup) which will print the game first setup:
        //      Filled Board, 2 Common Goals, Player's Personal Goal
        //      Furthermore, if the Client nickname correspond to JSONConverter.currentPlayer(String),
        //      then the CLI will invoke showCurrentPlayer()
        cli.showMatchSetup();
    }
}
