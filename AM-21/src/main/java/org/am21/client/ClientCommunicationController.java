package org.am21.client;

import org.am21.networkRMI.IClientCallBack;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public class ClientCommunicationController {

    public boolean joinGame(int matchID) {
        if (ClientController.isRMI) {
            try {
                return ClientController.iClientInputHandler.joinGame(matchID);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            } catch (ServerNotActiveException e) {
                throw new RuntimeException(e);
            }
        } else {
            String messageToServer = "join" + "|" + matchID;
            SocketClient.messageToServer(messageToServer);
            return true;
        }
    }

    public boolean logIn(String username, IClientCallBack clientCallBack) {
        if (ClientController.isRMI) {
            try {
                return ClientController.iClientInputHandler.logIn(username, clientCallBack);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            } catch (ServerNotActiveException e) {
                throw new RuntimeException(e);
            }
        } else {
            String messageToServer = "login" + "|" + username;
            SocketClient.messageToServer(messageToServer);
            return true;
        }
    }

    public boolean createMatch(int playerNum) {
        if (ClientController.isRMI) {
            try {
                return ClientController.iClientInputHandler.createMatch(playerNum);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            } catch (ServerNotActiveException e) {
                throw new RuntimeException(e);
            }
        } else {
            String messageToServer = "createMatch" + "|" + playerNum;
            SocketClient.messageToServer(messageToServer);
            return true;
        }
    }


    public boolean selectCell(int row, int col) {
        if (ClientController.isRMI) {
            try {
                return ClientController.iClientInputHandler.selectCell(row, col);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            } catch (ServerNotActiveException e) {
                throw new RuntimeException(e);
            }
        } else {
            String messageToServer = "selectCell" + "|" + row + "|" + col;
            SocketClient.messageToServer(messageToServer);
            return true;
        }
    }

    public boolean confirmSelection() {
        if (ClientController.isRMI) {
            try {
                return ClientController.iClientInputHandler.confirmSelection();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            } catch (ServerNotActiveException e) {
                throw new RuntimeException(e);
            }
        } else {
            String messageToServer = "confirmSelection";
            SocketClient.messageToServer(messageToServer);
            return true;
        }
    }

    public boolean insertInColumn(int colNum) {
        if (ClientController.isRMI) {
            try {
                return ClientController.iClientInputHandler.insertInColumn(colNum);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            } catch (ServerNotActiveException e) {
                throw new RuntimeException(e);
            }
        } else {
            String messageToServer = "insertInColumn" + "|" + colNum;
            SocketClient.messageToServer(messageToServer);
            return true;
        }
    }

    public boolean deselectCards() {
        if (ClientController.isRMI) {
            try {
                return ClientController.iClientInputHandler.deselectCards();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            } catch (ServerNotActiveException e) {
                throw new RuntimeException(e);
            }
        } else {
            String messageToServer = "deselectCards";
            SocketClient.messageToServer(messageToServer);
            return true;
        }
    }

    public boolean sortHand(int pos1, int pos2) {
        if (ClientController.isRMI) {
            try {
                return ClientController.iClientInputHandler.sortHand(pos1, pos2);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            } catch (ServerNotActiveException e) {
                throw new RuntimeException(e);
            }
        } else {
            String messageToServer = "sortHand" + "|" + pos1 + "|" + pos2;
            SocketClient.messageToServer(messageToServer);
            return true;
        }
    }

    public boolean leaveMatch() {
        if (ClientController.isRMI) {
            try {
                return ClientController.iClientInputHandler.leaveMatch();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            String messageToServer = "leaveMatch";
            SocketClient.messageToServer(messageToServer);
            return true;
        }
    }

    public boolean exitGame() {
        if (ClientController.isRMI) {
            try {
                return ClientController.iClientInputHandler.exitGame();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            String messageToServer = "exitGame";
            SocketClient.messageToServer(messageToServer);
            return true;
        }
    }

    public void getVirtualView() {
        if (ClientController.isRMI) {
            try {
                ClientController.iClientInputHandler.getVirtualView();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            String messageToServer = "getVirtualView";
            SocketClient.messageToServer(messageToServer);
        }
    }

    /**
     * RMI only
     *
     * @param callBack the callback to register
     * @return true if the callback was registered successfully
     */
    public boolean registerCallBack(IClientCallBack callBack) {
        return false;
    }

    public boolean sendChatMessage(String message) {
        if (ClientController.isRMI) {
            try {
                return ClientController.iClientInputHandler.sendChatMessage(message);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            String messageToSend = "sendChatMessage" + "|" + message;
            SocketClient.messageToServer(messageToSend);
            return true;
        }
    }

    public boolean sendPlayerMessage(String message, String receiver) {
        if (ClientController.isRMI) {
            try {
                return ClientController.iClientInputHandler.sendPlayerMessage(message, receiver);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            //TODO: elaborate messageToSend
            String messageToSend = "sendPlayerMessage" + "|" + receiver + "|" + message;
            SocketClient.messageToServer(messageToSend);
        }
        return false;
    }

    public void printOnlinePlayers() {
        if (ClientController.isRMI) {
            try {
                ClientController.iClientInputHandler.printOnlinePlayers();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            String messageToSend = "printOnlinePlayers";
            SocketClient.messageToServer(messageToSend);
        }
    }

    public void printMatchList() {
        if (ClientController.isRMI) {
            try {
                ClientController.iClientInputHandler.printMatchList();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            String messageToSend = "printMatchList";
            SocketClient.messageToServer(messageToSend);
        }
    }

    public boolean endTurn() {
        if (ClientController.isRMI) {
            try {
                return ClientController.iClientInputHandler.endTurn();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            } catch (ServerNotActiveException e) {
                throw new RuntimeException(e);
            }
        } else {
            String messageToSend = "endTurn";
            SocketClient.messageToServer(messageToSend);
        }
        return false;
    }

    //TODO:
    public void openChat() {
        if (ClientController.isRMI) {
            try {
                ClientController.iClientInputHandler.openChat();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            String messageToSend = "openChat";
            SocketClient.messageToServer(messageToSend);
        }
    }

    public boolean changeMatchSeats(int newMaxSeats) {
        if (ClientController.isRMI) {
            try {
                return ClientController.iClientInputHandler.changeMatchSeats(newMaxSeats);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            String messageToSend = "changeMatchSeats" + "|" + newMaxSeats;
            SocketClient.messageToServer(messageToSend);
        }
        return false;
    }

    public boolean changeInsertLimit(int newLimit) {
        if (ClientController.isRMI) {
            try {
                return ClientController.iClientInputHandler.changeInsertLimit(newLimit);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            String messageToSend = "changeInsertLimit" + "|" + newLimit;
            SocketClient.messageToServer(messageToSend);
        }
        return false;
    }

}
