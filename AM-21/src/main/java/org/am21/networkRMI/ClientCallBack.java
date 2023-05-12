package org.am21.networkRMI;

import org.am21.client.view.ClientView;
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
            cli.printer(message);
        }
    }

    @Override
    public void sendChatNotification(String message) throws RemoteException{
        if(cli!=null){
            if(cli.CHAT_MODE){
                cli.refreshChat();
            }else{
                cli.printer(message);
            }
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
        if(cli!=null){
            ClientView.setFullViewVariables(virtualView,pIndex );
            cli.checkTurn();
            cli.updateCLI(cli,0);
        }
    }

    /**
     * Each CLI will receive the notification when the match has officially begun
     * @throws RemoteException
     */
    @Override
    public void notifyStart(int id) throws RemoteException {
        if(cli!=null) {
            cli.setGO_TO_MENU(false);
            cli.setGAME_ON(true);
            cli.setSTART(true);
            cli.updateCLI(cli,1000);
        }
    }

    @Override
    public void notifyToWait(String jsonInfo) throws RemoteException {
        if(cli!=null){
            ClientView.convertBackMatchInfo(jsonInfo);
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

            cli.setEND(true);
            cli.setGO_TO_MENU(true);
            cli.setGAME_ON(false);
            cli.setSTART(false);
            cli.printer(SC.WHITE_BB+"\nServer > The match ended. Good Bye! Press 'Enter'"+SC.RST);
        }
    }

    @Override
    public void sendVirtualHand(String JSONHand) throws RemoteException {
        ClientView.convertBackHand(JSONHand);
    }

    @Override
    public void sendVirtualPublicChat(String virtualPublicChat) throws RemoteException {
        ClientView.convertBackPublicChat(virtualPublicChat);
    }

    @Override
    public void sendServerVirtualView(String serverVV) throws RemoteException{
        ClientView.updateServerView(serverVV);
    }

    @Override
    public void notifyUpdate(int milliseconds) throws RemoteException {
        if(cli!=null){
            System.out.println("Update...");
            cli.updateCLI(cli,milliseconds);
        }
    }
}
