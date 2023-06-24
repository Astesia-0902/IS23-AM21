package org.am21.client;

import org.am21.client.view.ClientView;
import org.am21.client.view.GUI.Gui;
import org.am21.client.view.TUI.Cli;
import org.am21.model.enumer.SC;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketClient extends Thread {
    public static final String defaultServerName = "localhost";
    public static final int defaultServerPort = 8080;
    public static String serverName = "localhost";
    public static int serverPort = 8080;
    public static Socket socketClient;
    private static DataInputStream in;
    private static DataOutputStream out;
    public static Cli cli;
    public static Gui gui;

    @Override
    public void run() {
        try {
            while (true) {
                String response = in.readUTF();
                //TODO:Handle message from the server
                handleServerMessage(response);
            }
        } catch (IOException e) {
            System.out.println("Server Disconnected");
        }
    }

    public static void messageToServer(String message) {
        try {
            out.writeUTF(message);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean connectToServer() {
        try {
            socketClient = new Socket(serverName, serverPort);

            System.out.println("Connected to " + socketClient.getRemoteSocketAddress());
            in = new DataInputStream(socketClient.getInputStream());
            out = new DataOutputStream(socketClient.getOutputStream());
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public void handleServerMessage(String message) {
        String[] messageArray = message.split("\\|", 3);
        switch (messageArray[0]) {
            case "return" -> {
                ClientCommunicationController.setMethodKey(messageArray[1]);
                ClientCommunicationController.setMethodReturn(Boolean.parseBoolean(messageArray[2]));
            }
            case "message" -> {
                if (cli != null) {
                    String updateMessage = "\nServer > "+ messageArray[1];
                    cli.printer(updateMessage);
                } else if (gui != null) {
                    new Thread(()->{
                        gui.replyDEBUG(messageArray[1]);
                        gui.timeLimitedNotification(messageArray[1].substring(6),5000);
                    }).start();

                }
                return;
            }
            case "virtualView" -> {
                ClientView.setFullViewVariables(messageArray[1], Integer.parseInt(messageArray[2]));
                if (cli != null) {
                    cli.checkTurn();
                    cli.updateCLI(500);
                } else if (gui != null) {
                    if (ClientView.GAME_ON && !ClientView.GO_TO_MENU) {
                        //Gameplay
                        gui.setGameBoardRefresh(true);

                    }
                }
            }
            case "notifyStart" -> {
                ClientView.setGoToMenu(false);
                ClientView.setGameOn(true);
                ClientView.setMatchStart(true);
                cli.checkTurn();
                if (cli != null) {

                    cli.updateCLI( 1000);
                }
            }
            case "notifyWait" -> {
                ClientView.convertBackMatchInfo(messageArray[1]);
                ClientView.setGameOn(false);
                ClientView.setGoToMenu(false);

            }
            case "notifyGoToMenu" -> {
                ClientView.setGoToMenu(true);
                ClientView.setGameOn(false);

            }
            case "notifyEndMatch" -> {
                ClientView.setMatchEnd(true);
                ClientView.setGoToMenu(true);
                ClientView.setGameOn(false);
                ClientView.setMatchStart(false);
                if (gui != null) {
                    gui.replyDEBUG(SC.WHITE_BB + "\nServer > The match ended. Good Bye!" + SC.RST);
                }
            }
            case "virtualHand" -> {
                ClientView.convertBackHand(messageArray[1]);
            }
            case "chatNotification" -> {
                if (cli != null) {
                    if (cli.chatMode) {
                        cli.refreshChat();
                    } else {
                        cli.addMessageInLine(messageArray[1]);
                        cli.updateCLI(0);
                    }
                } else if (gui != null) {

                    gui.timeLimitedNotification(messageArray[1].substring(0, messageArray[1].indexOf(" ")) + " sent you a new message", 5000);
                    gui.setAskChat(true);

                }
            }
            case "serverVirtualView" -> {
                ClientView.updateServerView(messageArray[1]);
            }
            case "publicChat" -> {
                ClientView.convertBackPublicChat(messageArray[1]);
            }
            case "notifyUpdate" -> {
                int milliseconds = Integer.parseInt(messageArray[1]);
                if (cli != null) {
                    //System.out.println("Update...");
                    cli.updateCLI(milliseconds);
                } else if (gui != null) {
                    if (ClientView.GO_TO_MENU) {
                        //Menu
                        gui.setMenuRefresh(true);
                    } else if (!ClientView.GAME_ON && !ClientView.GO_TO_MENU) {
                        //Waiting room
                        gui.setWaitRoomRefresh(true);
                    } else if (ClientView.GAME_ON && !ClientView.GO_TO_MENU) {
                        //Gameplay
                        gui.setGameBoardRefresh(true);

                    }
                }
            }
            case "ping" -> {
                return;
            }

            default -> System.out.println("Unknown Server Message: " + message);
        }
        //Free Client from Waiting method return in Socket

        ClientView.setWaitSocket(false);

    }
}
