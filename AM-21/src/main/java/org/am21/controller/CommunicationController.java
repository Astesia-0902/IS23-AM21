package org.am21.controller;

import org.am21.model.enumer.ConnectionType;
import org.am21.model.enumer.UserStatus;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public class CommunicationController implements ICommunication {
    public static final CommunicationController instance = new CommunicationController();

    @Override
    public void sendMessageToClient(String message,boolean refresh, PlayerController myPlayer) {
        if (myPlayer.connectionType == ConnectionType.RMI) {
            try {
                myPlayer.clientInput.callBack.sendMessageToClient(message,refresh);
            } catch (RemoteException e) {
                //throw new RuntimeException(e);
                myPlayer.getPlayer().setStatus(UserStatus.Offline);
            }
        } else if(myPlayer.connectionType == ConnectionType.SOCKET){

                String messageToClient = "Message"+ "|"+ refresh + "|" + message;
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
        } else if(myPlayer.connectionType == ConnectionType.SOCKET){
            String messageToClient = "VirtualView" + "|" + virtualView + "|" + pIndex;
            myPlayer.clientHandlerSocket.callback(messageToClient);
        }
    }

    public void sendVirtualPublicChat(String virtualPublicChat, PlayerController pCtrl){
        if (pCtrl.connectionType == ConnectionType.RMI) {
            try {
                pCtrl.clientInput.callBack.sendVirtualPublicChat(virtualPublicChat);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else if(pCtrl.connectionType == ConnectionType.SOCKET){
            String messageToClient = "VirtualView" + "|" + virtualPublicChat;
            pCtrl.clientHandlerSocket.callback(messageToClient);
        }

    }

    public void sendVirtualPrivateChat(String virtualPrivateChats, PlayerController pCtrl){
        if (pCtrl.connectionType == ConnectionType.RMI) {
            try {
                pCtrl.clientInput.callBack.sendVirtualPrivateChats(virtualPrivateChats);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else if(pCtrl.connectionType == ConnectionType.SOCKET){
            String messageToClient = "VirtualView" + "|" + virtualPrivateChats;
            pCtrl.clientHandlerSocket.callback(messageToClient);
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
        } else if(myPlayer.connectionType == ConnectionType.SOCKET){
            String messageToClient = "ChatMessage" + "|" + message;
            myPlayer.clientHandlerSocket.callback(messageToClient);
        }
    }

    @Override
    public void notifyStart(int id, PlayerController myPlayer) {
        if (myPlayer.connectionType == ConnectionType.RMI) {
            try {
                myPlayer.clientInput.callBack.notifyStart(id);
            } catch (RemoteException | ServerNotActiveException e) {
                throw new RuntimeException(e);
            }
        } else if(myPlayer.connectionType == ConnectionType.SOCKET){
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
        } else if(myPlayer.connectionType == ConnectionType.SOCKET){
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
        } else if(myPlayer.connectionType == ConnectionType.SOCKET){
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
        } else if(myPlayer.connectionType == ConnectionType.SOCKET){
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
        } else if(myPlayer.connectionType == ConnectionType.SOCKET){
            String messageToClient = "VirtualHand" + "|" + JSONHand;
            myPlayer.clientHandlerSocket.callback(messageToClient);
        }
    }

    /**
     * Socket Only
     * Message for return value of the methods
     * @param method the name of the method that needs a feedback
     * @param value true if the method return true, otherwise false
     * @param pCtrl
     */
    @Override
    public void returnBool(String method, boolean value,PlayerController pCtrl) {
        if(pCtrl.connectionType == ConnectionType.SOCKET){
            String messageToClient = "Return" + "|" + method +"|"+ value;
            pCtrl.clientHandlerSocket.callback(messageToClient);
        }
    }
    //TODO: testClientConnection
}
