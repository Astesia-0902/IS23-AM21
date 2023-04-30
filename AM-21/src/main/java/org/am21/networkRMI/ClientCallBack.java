package org.am21.networkRMI;

import org.am21.client.view.Storage;
import org.am21.client.view.TUI.Cli;
import org.am21.model.enumer.SC;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * To call the method in the client from the server, go to the PlayController.clientInput.callback
 */
public class ClientCallBack extends UnicastRemoteObject implements IClientCallBack{
    public Cli cli;

    public ClientCallBack() throws RemoteException {}

    @Override
    public void sendMessageToClient(String message) throws RemoteException {
        if(cli!=null){
            //TODO:Print the message from server
            cli.printer(message);
        }
    }

    /**
     * Update the virtual view
     * call this method when the game state changes
     * @param virtualView
     * @throws RemoteException
     */
    @Override
    public void sendVirtualView(String virtualView, int pIndex) throws RemoteException {
        //TODO:Update the virtual view
        if(cli!=null){
            Storage.setFullViewVariables(virtualView,pIndex );
            cli.checkTurn();
        }
    }

    @Override
    public void sendChatMessage(String message) throws RemoteException {

    }

    /**
     * Each CLI will receive the notification when the match has officially begun
     * @throws RemoteException
     */
    @Override
    public void notifyStart(int id) throws RemoteException {
        if(cli!=null) {
            cli.setMatchID(id);
            cli.setSTART(true);
            cli.setGO_TO_MENU(false);
            cli.setGAME_ON(true);
        }
    }

    @Override
    public void notifyToWait(int id) throws RemoteException {
        if(cli!=null){
            cli.setMatchID(id);
            cli.setGAME_ON(false);
            cli.setGO_TO_MENU(false);
        }
    }

    @Override
    public void notifyGoToMenu() throws RemoteException {
        if(cli!=null){
            cli.setGO_TO_MENU(true);
            cli.setGAME_ON(false);
        }
    }

    @Override
    public void notifyEndMatch() throws RemoteException{
        if(cli!=null){
            cli.setGO_TO_MENU(true);
            cli.setGAME_ON(false);
            cli.printer(SC.WHITE_BB+"\nServer > The match ended. Good Bye! Press 'Enter'"+SC.RST);
        }
    }

    @Override
    public void sendVirtualHand(String JSONHand) throws RemoteException {
        Storage.convertBackHand(JSONHand);
    }


}
