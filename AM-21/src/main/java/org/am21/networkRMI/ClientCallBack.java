package org.am21.networkRMI;

import org.am21.client.view.ClientView;
import org.am21.client.view.GUI.Gui;
import org.am21.client.view.TUI.Cli;
import org.am21.model.enumer.SC;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * To call the method in the client from the server, go to the PlayController.clientInput.callback
 */
public class ClientCallBack extends UnicastRemoteObject implements IClientCallBack {
    public Cli cli;
    public Gui gui;

    public ClientCallBack() throws RemoteException {
    }

    @Override
    public void sendMessageToClient(String message) throws RemoteException {
        if (cli != null) {
            cli.printer(message);
        } else if (gui != null) {
            new Thread(()->{
                gui.replyDEBUG(message);
                gui.timeLimitedNotification(message,500);
            }).start();

        }
    }

    @Override
    public void sendChatNotification(String message) throws RemoteException {
        if (cli != null) {
            if (cli.CHAT_MODE) {
                cli.refreshChat();
            } else {
                cli.addMessageInLine(message);
                cli.updateCLI(cli, 0);
            }
        } else if (gui != null) {
            gui.timeLimitedNotification(message.substring(0,message.indexOf(" ")) +" sent you a new message",1000 );
            gui.ASK_CHAT = true;
        }
    }

    /**
     * Update the virtual view
     * call this method when the game state changes
     *
     * @param virtualView
     * @throws RemoteException
     */
    @Override
    public void sendVirtualView(String virtualView, int pIndex) throws RemoteException {
        ClientView.setFullViewVariables(virtualView, pIndex);
        if (cli != null) {
            cli.checkTurn();
            cli.updateCLI(cli, 500);
        } else if (gui != null) {
            if(ClientView.GAME_ON && !ClientView.GO_TO_MENU){
                //Gameplay
                gui.GAME_BOARD_REFRESH = true;

            }

        }
    }

    /**
     * Each CLI will receive the notification when the match has officially begun
     *
     * @throws RemoteException
     */
    @Override
    public void notifyStart(int id) throws RemoteException {
        ClientView.setGoToMenu(false);
        ClientView.setGameOn(true);
        ClientView.setMatchStart(true);
        if (cli != null) {
            cli.updateCLI(cli, 1000);
        } else if (gui != null) {



        }
    }

    /**
     * This method change the state in Client to Waiting Room
     *
     * @param jsonInfo
     * @throws RemoteException
     */
    @Override
    public void notifyToWait(String jsonInfo) throws RemoteException {
        ClientView.convertBackMatchInfo(jsonInfo);
        ClientView.setGameOn(false);
        ClientView.setGoToMenu(false);
        if (cli != null) {

        } else if (gui != null) {

        }
    }

    /**
     * This method set the state of the Client to Main Menu
     *
     * @throws RemoteException
     */
    @Override
    public void notifyGoToMenu() throws RemoteException {
        ClientView.setGoToMenu(true);
        ClientView.setGameOn(false);
        if (cli != null) {

        } else if (gui != null) {

        }
    }

    /**
     * This method set the state of the Client to Endgame Room
     *
     * @throws RemoteException
     */
    @Override
    public void notifyEndMatch() throws RemoteException {
        ClientView.setMatchEnd(true);
        ClientView.setGoToMenu(true);
        ClientView.setGameOn(false);
        ClientView.setMatchStart(false);
        if (cli != null) {

        } else if (gui != null) {
            gui.replyDEBUG(SC.WHITE_BB + "\nServer > The match ended. Good Bye!" + SC.RST);
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
    public void sendServerVirtualView(String serverVV) throws RemoteException {
        ClientView.updateServerView(serverVV);
    }

    @Override
    public void notifyUpdate(int milliseconds) throws RemoteException {
        if (cli != null) {
            //System.out.println("Update...");
            cli.updateCLI(cli, milliseconds);
        } else if (gui != null) {
            if(ClientView.GO_TO_MENU){
                //Menu
                gui.MENU_REFRESH = true;
            }else if(!ClientView.GAME_ON && !ClientView.GO_TO_MENU){
                //Waiting room
                gui.WAIT_ROOM_REFRESH = true;
            }else if(ClientView.GAME_ON && !ClientView.GO_TO_MENU){
                //Gameplay
                gui.GAME_BOARD_REFRESH = true;

            }
        }
    }

    @Override
    public void ping() throws RemoteException{
        //Nothing to do
    }
}
