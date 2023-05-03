package org.am21.controller;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public class CommunicationController implements ICommunication {

    @Override
    public void sendMessageToClient(String message, PlayerController myPlayer) {
        if (myPlayer.connectionType) {
            try {
                myPlayer.clientInput.callBack.sendMessageToClient(message);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            //TODO:Socket mode method
        }
    }

    @Override
    public void sendVirtualView(String virtualView, int pIndex, PlayerController myPlayer) {
        if (myPlayer.connectionType) {
            try {
                myPlayer.clientInput.callBack.sendVirtualView(virtualView, pIndex);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {

        }
    }

    @Override
    public void sendChatMessage(String message, PlayerController myPlayer) {
        if (myPlayer.connectionType) {
            try {
                myPlayer.clientInput.callBack.sendChatMessage(message);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {

        }
    }

    @Override
    public void notifyStart(int id, PlayerController myPlayer) {
        if (myPlayer.connectionType) {
            try {
                myPlayer.clientInput.callBack.notifyStart(id);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            } catch (ServerNotActiveException e) {
                throw new RuntimeException(e);
            }

        } else {

        }

    }

    @Override
    public void notifyToWait(String info, PlayerController myPlayer) {
        if (myPlayer.connectionType) {
            try {
                myPlayer.clientInput.callBack.notifyToWait(info);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

        } else {

        }

    }


    @Override
    public void notifyGoToMenu(PlayerController myPlayer) {
        if (myPlayer.connectionType) {
            try {
                myPlayer.clientInput.callBack.notifyGoToMenu();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void notifyEndMatch(PlayerController myPlayer) {
        if (myPlayer.connectionType) {
            try {
                myPlayer.clientInput.callBack.notifyEndMatch();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {

        }

    }

    @Override
    public void sendVirtualHand(String JSONHand, PlayerController myPlayer) {
        if (myPlayer.connectionType) {
            try {
                myPlayer.clientInput.callBack.sendVirtualHand(JSONHand);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {

        }
    }

    //TODO: testClientConnection

}
