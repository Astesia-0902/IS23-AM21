package org.am21.client;

import org.am21.client.view.ClientView;
import org.am21.client.view.GUI.Gui;
import org.am21.client.view.TUI.Cli;
import org.am21.networkRMI.IClientCallBack;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public class ClientCommunicationController {
    public Cli cli;
    public Gui gui;
    private static String methodKey = "method";

    private static boolean METHOD_RETURN = true;

    public synchronized static void setMethodKey(String methodKey) {
        ClientCommunicationController.methodKey = methodKey;
    }

    public synchronized static void setMethodReturn(boolean methodReturn) {
        METHOD_RETURN = methodReturn;
    }

    /**
     * This method set the Attribute WAIT_SOCKET to true
     */
    public void makeClientWait() {
        ClientView.setWaitSocket(true);
    }

    /**
     * While the WAIT_SOCKET is true the Client main thread will wait until the socket Receives a Return of the method
     * @param method the specific method name the socket is waiting
     */
    public void waitingMethodReturn(String method) {
        if (cli != null) {
            while (ClientView.WAIT_SOCKET && !methodKey.equals(method)) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            //Reset
            methodKey = "method";
        }else if (gui != null) {
            while (ClientView.WAIT_SOCKET && !methodKey.equals(method)) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            //Reset
            methodKey = "method";
        }
    }

    public boolean joinGame(int matchID) {
        if (ClientController.isRMI) {
            try {
                return ClientController.iClientInputHandler.joinGame(matchID);
            } catch (RemoteException | ServerNotActiveException e) {
                throw new RuntimeException(e);
            }
        } else {
            makeClientWait();
            String messageToServer = "join" + "|" + matchID;
            SocketClient.messageToServer(messageToServer);
            waitingMethodReturn("join");
            return METHOD_RETURN;
        }
    }

    public boolean logIn(String username, IClientCallBack clientCallBack) {
        if (ClientController.isRMI) {
            try {
                return ClientController.iClientInputHandler.logIn(username, clientCallBack);
            } catch (RemoteException | ServerNotActiveException e) {
                throw new RuntimeException(e);
            }
        } else {
            makeClientWait();
            String messageToServer = "login" + "|" + username;
            SocketClient.messageToServer(messageToServer);
            waitingMethodReturn("login");
            return METHOD_RETURN;
        }
    }

    public boolean createMatch(int playerNum) {
        if (ClientController.isRMI) {
            try {
                return ClientController.iClientInputHandler.createMatch(playerNum);
            } catch (RemoteException | ServerNotActiveException e) {
                throw new RuntimeException(e);
            }
        } else {
            makeClientWait();
            String messageToServer = "createMatch" + "|" + playerNum;
            SocketClient.messageToServer(messageToServer);
            waitingMethodReturn("createMatch");
            return METHOD_RETURN;
        }
    }


    public boolean selectCell(int row, int col) {
        if (ClientController.isRMI) {
            try {
                return ClientController.iClientInputHandler.selectCell(row, col);
            } catch (RemoteException | ServerNotActiveException e) {
                throw new RuntimeException(e);
            }
        } else {
            makeClientWait();
            String messageToServer = "selectCell" + "|" + row + "|" + col;
            SocketClient.messageToServer(messageToServer);
            waitingMethodReturn("selectCell");
            return METHOD_RETURN;
        }
    }


    public boolean confirmSelection() {
        if (ClientController.isRMI) {
            try {
                return ClientController.iClientInputHandler.confirmSelection();
            } catch (RemoteException | ServerNotActiveException e) {
                throw new RuntimeException(e);
            }
        } else {
            makeClientWait();
            String messageToServer = "confirmSelection";
            SocketClient.messageToServer(messageToServer);
            waitingMethodReturn("confirmSelection");
            return METHOD_RETURN;
        }
    }

    public boolean insertInColumn(int colNum) {
        if (ClientController.isRMI) {
            try {
                return ClientController.iClientInputHandler.insertInColumn(colNum);
            } catch (RemoteException | ServerNotActiveException e) {
                throw new RuntimeException(e);
            }
        } else {
            makeClientWait();
            String messageToServer = "insertInColumn" + "|" + colNum;
            SocketClient.messageToServer(messageToServer);
            waitingMethodReturn("insertInColumn");
            return METHOD_RETURN;
        }
    }

    public boolean deselectCards() {
        if (ClientController.isRMI) {
            try {
                return ClientController.iClientInputHandler.deselectCards();
            } catch (RemoteException | ServerNotActiveException e) {
                throw new RuntimeException(e);
            }
        } else {
            makeClientWait();
            String messageToServer = "deselectCards";
            SocketClient.messageToServer(messageToServer);
            waitingMethodReturn("deselectCards");
            return METHOD_RETURN;
        }
    }

    public boolean sortHand(int pos1, int pos2) {
        if (ClientController.isRMI) {
            try {
                return ClientController.iClientInputHandler.sortHand(pos1, pos2);
            } catch (RemoteException | ServerNotActiveException e) {
                throw new RuntimeException(e);
            }
        } else {
            makeClientWait();
            String messageToServer = "sortHand" + "|" + pos1 + "|" + pos2;
            SocketClient.messageToServer(messageToServer);
            waitingMethodReturn("sortHand");
            return METHOD_RETURN;
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
            makeClientWait();
            String messageToServer = "leaveMatch";
            SocketClient.messageToServer(messageToServer);
            waitingMethodReturn("leaveMatch");
            return METHOD_RETURN;
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
            makeClientWait();
            String messageToServer = "exitGame";
            SocketClient.messageToServer(messageToServer);
            waitingMethodReturn("exitGame");
            return METHOD_RETURN;
        }
    }

    //TODO:Never used
    public void getVirtualView() {
        if (ClientController.isRMI) {
            try {
                ClientController.iClientInputHandler.getVirtualView();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            makeClientWait();
            String messageToServer = "getVirtualView";
            SocketClient.messageToServer(messageToServer);
            waitingMethodReturn("getVirtualView");
        }
    }

    /**
     * RMI only
     *
     * @param callBackPath the callback to register
     */
    public void registerCallBack(String callBackPath) {
        try {
            ClientController.iClientInputHandler.registerCallBack(callBackPath);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean endTurn() {
        if (ClientController.isRMI) {
            try {
                return ClientController.iClientInputHandler.endTurn();
            } catch (RemoteException | ServerNotActiveException e) {
                throw new RuntimeException(e);
            }
        } else {
            makeClientWait();
            String messageToSend = "endTurn";
            SocketClient.messageToServer(messageToSend);
            waitingMethodReturn("endTurn");
            return METHOD_RETURN;
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
            makeClientWait();
            String messageToSend = "changeMatchSeats" + "|" + newMaxSeats;
            SocketClient.messageToServer(messageToSend);
            waitingMethodReturn("changeMatchSeats");
            return METHOD_RETURN;
        }
    }

    public boolean changeInsertLimit(int newLimit) {
        if (ClientController.isRMI) {
            try {
                return ClientController.iClientInputHandler.changeInsertLimit(newLimit);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            makeClientWait();
            String messageToSend = "changeInsertLimit" + "|" + newLimit;
            SocketClient.messageToServer(messageToSend);
            waitingMethodReturn("changeInsertLimit");
            return METHOD_RETURN;
        }

    }

    public boolean sendPublicMessage(String message, boolean live) {
        if (ClientController.isRMI) {
            try {
                return ClientController.iClientInputHandler.sendPublicMessage(message, live);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            makeClientWait();
            String messageToSend = "sendPublicMessage" + "|" + message + "|" + live;
            SocketClient.messageToServer(messageToSend);
            waitingMethodReturn("sendPublicMessage");
            return METHOD_RETURN;
        }
    }

    public boolean sendPrivateMessage(String message, String receiver, boolean live) {
        if (ClientController.isRMI) {
            try {
                return ClientController.iClientInputHandler.sendPrivateMessage(message, receiver, live);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            makeClientWait();
            String messageToSend = "sendPrivateMessage" + "|" + message + "|" + receiver + "|" + live;
            SocketClient.messageToServer(messageToSend);
            waitingMethodReturn("sendPrivateMessage");
            return METHOD_RETURN;
        }
    }

}
