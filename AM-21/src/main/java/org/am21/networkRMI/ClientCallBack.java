package org.am21.networkRMI;

import org.am21.client.view.JSONConverter;

import java.rmi.RemoteException;

/**
 * To call the method in the client from the server, go to the PlayController.clientInput.callback
 */
public class ClientCallBack implements IClientCallBack{
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
}
