package org.am21.controller;

import org.am21.model.enumer.ConnectionType;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public class CommunicationController implements ICommunication {

    @Override
    public void sendMessageToClient(String message, PlayerController myPlayer) {
        if (myPlayer.connectionType == ConnectionType.RMI) {
            try {
                myPlayer.clientInput.callBack.sendMessageToClient(message);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            String messageToClient = "Message" + "|" + message;
            myPlayer.clientHandlerSocket.callback(messageToClient);
        }
    }

    @Override
    public void sendVirtualView(String virtualView, int pIndex, PlayerController myPlayer) {
        if (myPlayer.connectionType == ConnectionType.RMI) {
            try {
                myPlayer.clientInput.callBack.sendVirtualView(virtualView, pIndex);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            String messageToClient = "VirtualView" + "|" + virtualView + "|" + pIndex;
            myPlayer.clientHandlerSocket.callback(messageToClient);
        }
    }

    @Override
    public void sendChatMessage(String message, PlayerController myPlayer) {
        if (myPlayer.connectionType == ConnectionType.RMI) {
            try {
                myPlayer.clientInput.callBack.sendChatMessage(message);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            String messageToClient = "ChatMessage" + "|" + message;
            myPlayer.clientHandlerSocket.callback(messageToClient);
        }
    }

    @Override
    public void notifyStart(int id, PlayerController myPlayer) {
        if (myPlayer.connectionType == ConnectionType.RMI) {
            try {
                myPlayer.clientInput.callBack.notifyStart(id);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            } catch (ServerNotActiveException e) {
                throw new RuntimeException(e);
            }
        } else {
            String messageToClient = "START" + "|" + id;
            myPlayer.clientHandlerSocket.callback(messageToClient);
        }

    }

    @Override
    public void notifyToWait(String info, PlayerController myPlayer) {
        if (myPlayer.connectionType == ConnectionType.RMI) {
            try {
                myPlayer.clientInput.callBack.notifyToWait(info);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            String messageToClient = "WAIT" + "|" + info;
            myPlayer.clientHandlerSocket.callback(messageToClient);
        }
    }


    @Override
    public void notifyGoToMenu(PlayerController myPlayer) {
        if (myPlayer.connectionType == ConnectionType.RMI) {
            try {
                myPlayer.clientInput.callBack.notifyGoToMenu();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            String messageToClient = "GoToMenu";
            myPlayer.clientHandlerSocket.callback(messageToClient);
        }
    }

    @Override
    public void notifyEndMatch(PlayerController myPlayer) {
        if (myPlayer.connectionType == ConnectionType.RMI) {
            try {
                myPlayer.clientInput.callBack.notifyEndMatch();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            String messageToClient = "EndMatch";
            myPlayer.clientHandlerSocket.callback(messageToClient);
        }
    }

    @Override
    public void sendVirtualHand(String JSONHand, PlayerController myPlayer) {
        if (myPlayer.connectionType == ConnectionType.RMI) {
            try {
                myPlayer.clientInput.callBack.sendVirtualHand(JSONHand);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            String messageToClient = "VirtualHand" + "|" + JSONHand;
            myPlayer.clientHandlerSocket.callback(messageToClient);
        }
    }
    //TODO: testClientConnection
}
