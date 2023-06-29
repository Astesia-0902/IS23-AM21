package org.am21.client;

import org.am21.client.view.ClientView;
import org.am21.client.view.GUI.Gui;
import org.am21.client.view.TUI.Cli;
import org.am21.networkRMI.IClientCallBack;
import org.am21.networkRMI.IClientInput;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public class ClientCommunicationController {
    public Cli cli;
    public Gui gui;
    public static boolean isRMI = false;
    public static IClientInput iClientInputHandler;
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
        if (ClientCommunicationController.isRMI) {
            try {
                return ClientCommunicationController.iClientInputHandler.joinGame(matchID);
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
        if (ClientCommunicationController.isRMI) {
            try {
                return ClientCommunicationController.iClientInputHandler.logIn(username, clientCallBack);
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
        if (ClientCommunicationController.isRMI) {
            try {
                return ClientCommunicationController.iClientInputHandler.createMatch(playerNum);
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
        if (ClientCommunicationController.isRMI) {
            try {
                return ClientCommunicationController.iClientInputHandler.selectCell(row, col);
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
        if (ClientCommunicationController.isRMI) {
            try {
                return ClientCommunicationController.iClientInputHandler.confirmSelection();
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
        if (ClientCommunicationController.isRMI) {
            try {
                return ClientCommunicationController.iClientInputHandler.insertInColumn(colNum);
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
        if (ClientCommunicationController.isRMI) {
            try {
                return ClientCommunicationController.iClientInputHandler.deselectCards();
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
        if (ClientCommunicationController.isRMI) {
            try {
                return ClientCommunicationController.iClientInputHandler.sortHand(pos1, pos2);
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
        if (ClientCommunicationController.isRMI) {
            try {
                return ClientCommunicationController.iClientInputHandler.leaveMatch();
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
        if (ClientCommunicationController.isRMI) {
            try {
                return ClientCommunicationController.iClientInputHandler.exitGame();
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

    /**
     * RMI only
     *
     * @param callBackPath the callback to register
     */
    public void registerCallBack(String callBackPath) {
        try {
            ClientCommunicationController.iClientInputHandler.registerCallBack(callBackPath);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean endTurn() {
        if (ClientCommunicationController.isRMI) {
            try {
                return ClientCommunicationController.iClientInputHandler.endTurn();
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
        if (ClientCommunicationController.isRMI) {
            try {
                return ClientCommunicationController.iClientInputHandler.changeMatchSeats(newMaxSeats);
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

    public boolean sendPublicMessage(String message, boolean live) {
        if (ClientCommunicationController.isRMI) {
            try {
                return ClientCommunicationController.iClientInputHandler.sendPublicMessage(message, live);
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
        if (ClientCommunicationController.isRMI) {
            try {
                return ClientCommunicationController.iClientInputHandler.sendPrivateMessage(message, receiver, live);
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
