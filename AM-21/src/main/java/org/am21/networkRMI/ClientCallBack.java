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
    public void sendMessageToClient(String message,boolean refresh) throws RemoteException {
        if(cli!=null){
            //TODO:Print the message from server
            cli.printer(message);
            if(refresh) {
                cli.refresh(cli);
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
            cli.refresh(cli);
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
            cli.setGO_TO_MENU(false);
            cli.setGAME_ON(true);
            cli.setSTART(true);
            cli.refresh(cli);
        }
    }

    @Override
    public void notifyToWait(String jsonInfo) throws RemoteException {
        if(cli!=null){
            //cli.setMatchID(id);
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
        if(cli!=null){
            cli.refresh(cli);
        }
    }

    @Override
    public void sendVirtualPrivateChats(String virtualPrivateChats) throws RemoteException {
        ClientView.convertBackPrivateChats(virtualPrivateChats);
    }

    @Override
    public void sendChatMap(String virtualChatMap) throws RemoteException {
        ClientView.convertBackChatMap(virtualChatMap);
    }

    @Override
    public void sendMatchList(String virtualMatchList) throws RemoteException {
        ClientView.convertBackMatchList(virtualMatchList);
    }

    @Override
    public void sendOnlinePlayer(String virtualOnlinePlayers) throws RemoteException {
        ClientView.convertBackOnlinePlayers(virtualOnlinePlayers);
    }

    @Override
    public void sendServerVirtualView(String serverVV) throws RemoteException{
        ClientView.updateServerView(serverVV);
    }
}
