package org.am21.controller;

import org.am21.model.enumer.ConnectionType;
import org.am21.model.enumer.GameState;
import org.am21.model.enumer.UserStatus;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public class CommunicationController implements ICommunication {
    public static final CommunicationController instance = new CommunicationController();

    @Override
    public void sendMessageToClient(String message, PlayerController myPlayer) {
        if (myPlayer.connectionType == ConnectionType.RMI) {
            try {
                myPlayer.clientInput.callBack.sendMessageToClient(message);
            } catch (RemoteException e) {
                //throw new RuntimeException(e);
                myPlayer.getPlayer().setStatus(UserStatus.Offline);
            }
        } else if (myPlayer.connectionType == ConnectionType.SOCKET) {

            String messageToClient = "message" + "|" + message;
            myPlayer.clientHandlerSocket.callback(messageToClient);

        }
    }

    @Override
    public void sendVirtualView(String virtualView, int pIndex, PlayerController myPlayer) {
        if (myPlayer.getPlayer().getStatus() != UserStatus.GameMember)
            return;

        if (myPlayer.connectionType == ConnectionType.RMI) {
            try {
                myPlayer.clientInput.callBack.sendVirtualView(virtualView, pIndex);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else if (myPlayer.connectionType == ConnectionType.SOCKET) {
            String messageToClient = "virtualView" + "|" + virtualView + "|" + pIndex;
            myPlayer.clientHandlerSocket.callback(messageToClient);
        }
    }

    public void sendVirtualPublicChat(String virtualPublicChat, PlayerController pCtrl) {
        if (pCtrl.connectionType == ConnectionType.RMI) {
            try {
                pCtrl.clientInput.callBack.sendVirtualPublicChat(virtualPublicChat);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else if (pCtrl.connectionType == ConnectionType.SOCKET) {
            String messageToClient = "publicChat" + "|" + virtualPublicChat;
            pCtrl.clientHandlerSocket.callback(messageToClient);
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
        } else if (myPlayer.connectionType == ConnectionType.SOCKET) {
            String messageToClient = "notifyStart" + "|" + id;
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
        } else if (myPlayer.connectionType == ConnectionType.SOCKET) {
            String messageToClient = "notifyWait" + "|" + info;
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
        } else if (myPlayer.connectionType == ConnectionType.SOCKET) {
            String messageToClient = "notifyGoToMenu";
            myPlayer.clientHandlerSocket.callback(messageToClient);
        }
    }

    @Override
    public void notifyEndMatch(PlayerController myPlayer) {
        if (myPlayer.getPlayer().getStatus() != UserStatus.GameMember)
            return;
        if (myPlayer.connectionType == ConnectionType.RMI) {
            try {
                myPlayer.clientInput.callBack.notifyEndMatch();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else if (myPlayer.connectionType == ConnectionType.SOCKET) {
            String messageToClient = "notifyEndMatch";
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
        } else if (myPlayer.connectionType == ConnectionType.SOCKET) {
            String messageToClient = "virtualHand" + "|" + JSONHand;
            myPlayer.clientHandlerSocket.callback(messageToClient);
        }
    }

    /**
     * Socket Only
     * Message for return value of the methods
     *
     * @param method the name of the method that needs a feedback
     * @param value  true if the method return true, otherwise false
     * @param pCtrl
     */
    @Override
    public void returnBool(String method, boolean value, PlayerController pCtrl) {
        if (pCtrl.connectionType == ConnectionType.SOCKET) {
            String messageToClient = "return" + "|" + method + "|" + value;
            pCtrl.clientHandlerSocket.callback(messageToClient);
        }
    }

    @Override
    public void sendChatNotification(String message, PlayerController pc) {
        if (pc.connectionType == ConnectionType.RMI) {
            try {
                pc.clientInput.callBack.sendChatNotification(message);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else if (pc.connectionType == ConnectionType.SOCKET) {
            String messageToClient = "chatNotification" + "|" + message;
            pc.clientHandlerSocket.callback(messageToClient);

        }
    }

    @Override
    public void sendServerVirtualView(String serverVirtualView, PlayerController pc) {
        if (pc.connectionType == ConnectionType.RMI) {
            try {
                pc.clientInput.callBack.sendServerVirtualView(serverVirtualView);
            } catch (RemoteException e) {
                pc.getPlayer().setStatus(UserStatus.Offline);

                //throw new RuntimeException(e);
            }
        } else if (pc.connectionType == ConnectionType.SOCKET) {
            String messageToClient = "serverVirtualView" + "|" + serverVirtualView;
            pc.clientHandlerSocket.callback(messageToClient);
        }
    }

    @Override
    public void notifyUpdate(PlayerController ctrl, int milliseconds) {
        if (ctrl.connectionType == ConnectionType.RMI) {
            try {
                ctrl.clientInput.callBack.notifyUpdate(milliseconds);
            } catch (RemoteException e) {
                //ctrl.getPlayer().setStatus(UserStatus.Offline);
            }
        } else if (ctrl.connectionType == ConnectionType.SOCKET) {
            String messageToClient = "notifyUpdate" + "|" + milliseconds;
            ctrl.clientHandlerSocket.callback(messageToClient);
        }
    }

    @Override
    public void ping(PlayerController ctrl) {
        //System.out.println("Checking if " + ctrl.getPlayer().getNickname() + " is online");
        if (ctrl.connectionType == ConnectionType.RMI) {
            try {
                ctrl.clientInput.callBack.ping();
            } catch (RemoteException e) {
                handlePlayerOffline(ctrl);
            }
        } else if (ctrl.connectionType == ConnectionType.SOCKET) {
            if (ctrl.clientHandlerSocket.isServerClose()) {
                handlePlayerOffline(ctrl);
            }
        }
    }

    public void handlePlayerOffline(PlayerController ctrl) {
        if (ctrl.getPlayer().getMatch() != null && ctrl.getPlayer().getMatch().gameState.equals(GameState.GameGoing) && ctrl.getPlayer().getStatus() != UserStatus.Suspended) {
            ctrl.getPlayer().setStatus(UserStatus.Suspended);
            System.out.println(ctrl.getPlayer().getNickname() + " is suspended");
            ctrl.getPlayer().getMatch().sendTextToAll("Player " + ctrl.getPlayer().getNickname() + " is suspended", false);
            ctrl.getPlayer().getMatch().sendNotificationToAll(false);
        } else if ((ctrl.getPlayer().getMatch() == null || !ctrl.getPlayer().getMatch().gameState.equals(GameState.GameGoing)) && ctrl.getPlayer().getStatus() != UserStatus.Offline) {
            ctrl.getPlayer().setStatus(UserStatus.Offline);
            System.out.println(ctrl.getPlayer().getNickname() + " is offline");
        }
    }
}
