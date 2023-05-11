package org.am21.client;

import org.am21.client.view.TUI.Cli;
import org.am21.networkRMI.IClientCallBack;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public class ClientCommunicationController {
    public Cli cli;
    private static String METHOD_KEY = "method";

    private static boolean METHOD_RETURN = true;

    public synchronized static void setMethodKey(String methodKey) {
        METHOD_KEY = methodKey;
    }

    public synchronized static void setMethodReturn(boolean methodReturn) {
        METHOD_RETURN = methodReturn;
    }

    public void makeCliWait() {
        if (cli != null) {
            cli.WAIT_SOCKET = true;
        }
    }

    public void wait_Socket(String method) {
        if (cli != null) {
            while (cli.WAIT_SOCKET && !METHOD_KEY.equals(method)) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            //Reset
            METHOD_KEY = "method";
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
            makeCliWait();
            String messageToServer = "join" + "|" + matchID;
            SocketClient.messageToServer(messageToServer);
            wait_Socket("join");
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
            makeCliWait();
            String messageToServer = "login" + "|" + username;
            SocketClient.messageToServer(messageToServer);
            wait_Socket("login");
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
            makeCliWait();
            String messageToServer = "createMatch" + "|" + playerNum;
            SocketClient.messageToServer(messageToServer);
            wait_Socket("createMatch");
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
            makeCliWait();
            String messageToServer = "selectCell" + "|" + row + "|" + col;
            SocketClient.messageToServer(messageToServer);
            wait_Socket("selectCell");
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
            makeCliWait();
            String messageToServer = "confirmSelection";
            SocketClient.messageToServer(messageToServer);
            wait_Socket("confirmSelection");
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
            makeCliWait();
            String messageToServer = "insertInColumn" + "|" + colNum;
            SocketClient.messageToServer(messageToServer);
            wait_Socket("insertInColumn");
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
            makeCliWait();
            String messageToServer = "deselectCards";
            SocketClient.messageToServer(messageToServer);
            wait_Socket("deselectCards");
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
            makeCliWait();
            String messageToServer = "sortHand" + "|" + pos1 + "|" + pos2;
            SocketClient.messageToServer(messageToServer);
            wait_Socket("sortHand");
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
            makeCliWait();
            String messageToServer = "leaveMatch";
            SocketClient.messageToServer(messageToServer);
            wait_Socket("leaveMatch");
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
            makeCliWait();
            String messageToServer = "exitGame";
            SocketClient.messageToServer(messageToServer);
            wait_Socket("exitGame");
            return METHOD_RETURN;
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
            makeCliWait();
            String messageToServer = "getVirtualView";
            SocketClient.messageToServer(messageToServer);
            wait_Socket("getVirtualView");
        }
    }

    /**
     * RMI only
     *
     * @param callBack the callback to register
     */
    public void registerCallBack(IClientCallBack callBack) {
        try {
            ClientController.iClientInputHandler.registerCallBack(callBack);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean sendChatMessage(String message) {
        if (ClientController.isRMI) {
            try {
                return ClientController.iClientInputHandler.sendChatMessage(message);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            makeCliWait();
            String messageToSend = "sendChatMessage" + "|" + message;
            SocketClient.messageToServer(messageToSend);
            wait_Socket("sendChatMessage");
            return METHOD_RETURN;
        }
    }

    public boolean sendPlayerMessage(String message, String receiver, boolean refresh) {
        if (ClientController.isRMI) {
            try {
                return ClientController.iClientInputHandler.sendPlayerMessage(message, receiver, refresh);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            //TODO: elaborate messageToSend
            makeCliWait();
            String messageToSend = "sendPlayerMessage" + "|" + receiver + "|" + message;
            SocketClient.messageToServer(messageToSend);
            wait_Socket("sendPlayerMessage");
            return METHOD_RETURN;
        }
    }

    public void printOnlinePlayers() {
        if (ClientController.isRMI) {
            try {
                ClientController.iClientInputHandler.printOnlinePlayers();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            makeCliWait();
            String messageToSend = "printOnlinePlayers";
            SocketClient.messageToServer(messageToSend);
            wait_Socket("printOnlinePlayers");
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
            makeCliWait();
            String messageToSend = "printMatchList";
            SocketClient.messageToServer(messageToSend);
            wait_Socket("printMatchList");
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
            makeCliWait();
            String messageToSend = "endTurn";
            SocketClient.messageToServer(messageToSend);
            wait_Socket("endTurn");
            return METHOD_RETURN;
        }

    }

    //TODO: no need just view ClientView in CLI
    public void openChat() {
        if (ClientController.isRMI) {
            try {
                ClientController.iClientInputHandler.openChat();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            makeCliWait();
            String messageToSend = "openChat";
            SocketClient.messageToServer(messageToSend);
            wait_Socket("openChat");
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
            makeCliWait();
            String messageToSend = "changeMatchSeats" + "|" + newMaxSeats;
            SocketClient.messageToServer(messageToSend);
            wait_Socket("changeMatchSeats");
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
            makeCliWait();
            String messageToSend = "changeInsertLimit" + "|" + newLimit;
            SocketClient.messageToServer(messageToSend);
            wait_Socket("changeInsertLimit");
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
            return METHOD_RETURN;
        }
    }

}
