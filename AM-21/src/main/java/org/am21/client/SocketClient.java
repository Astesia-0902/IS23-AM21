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
            socketClient = new Socket(serverName, serverPort);
            System.out.println("Connected to " + socketClient.getRemoteSocketAddress());
            in = new DataInputStream(socketClient.getInputStream());
            out = new DataOutputStream(socketClient.getOutputStream());
            while (true) {
                String response = in.readUTF();
                //TODO:Handle message from the server
                handleServerMessage(response);
            }
        } catch (IOException e) {
            System.out.println("Server Disconnected");
            //throw new RuntimeException(e);
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

    public void handleServerMessage(String message) {
        String[] messageArray = message.split("\\|", 3);
        switch (messageArray[0]) {
            case "Return" -> {
                ClientCommunicationController.setMethodKey(messageArray[1]);
                ClientCommunicationController.setMethodReturn(Boolean.parseBoolean(messageArray[2]));
            }
            case "Message" -> {
                if (cli != null) {
                    cli.printer(messageArray[1]);
                } else if (gui != null) {
                    //gui.printer(message,"Successful");
                    gui.replyDEBUG(messageArray[1]);
                }
                return;
            }
            case "VirtualView" -> {
                ClientView.setFullViewVariables(messageArray[1], Integer.parseInt(messageArray[2]));
                if (cli != null) {
                    cli.checkTurn();
                    cli.updateCLI(cli, 500);
                } else if (gui != null) {

                }
            }
            case "MATCH_START" -> {
                if (cli != null) {
                    ClientView.setGoToMenu(false);
                    ClientView.setGameOn(true);
                    ClientView.setMatchStart(true);
                    cli.updateCLI(cli, 1000);
                } else if (gui != null) {
                    gui.setGO_TO_MENU(false);
                    gui.setGAME_ON(true);
                    gui.setSTART(true);

                }
            }
            case "WAIT" -> {
                if (cli != null) {
                    ClientView.convertBackMatchInfo(messageArray[1]);
                    ClientView.setGameOn(false);
                    ClientView.setGoToMenu(false);
                } else if (gui != null) {
                    gui.setGAME_ON(false);
                    gui.setGO_TO_MENU(false);
                }
            }
            case "GoToMenu" -> {
                if (cli != null) {
                    ClientView.setGoToMenu(true);
                    ClientView.setGameOn(false);
                } else if (gui != null) {
                    gui.setGO_TO_MENU(true);
                    gui.setGAME_ON(false);
                }

            }
            case "EndMatch" -> {
                if (cli != null) {
                    ClientView.setMatchEnd(true);
                    ClientView.setGoToMenu(true);
                    ClientView.setGameOn(false);
                    ClientView.setMatchStart(false);
                } else if (gui != null) {
                    gui.setEND(true);
                    gui.setGO_TO_MENU(true);
                    gui.setGAME_ON(false);
                    gui.setSTART(false);
                    gui.replyDEBUG(SC.WHITE_BB + "\nServer > The match ended. Good Bye! Press 'Enter'" + SC.RST);
                }
            }
            case "VirtualHand" -> {
                ClientView.convertBackHand(messageArray[1]);
            }
            case "ChatNotification" -> {
                if (cli != null) {
                    if (cli.CHAT_MODE) {
                        cli.refreshChat();
                    } else {
                        cli.addMessageInLine(messageArray[1]);
                        cli.updateCLI(cli, 0);
                    }
                } else if (gui != null) {

                    gui.timeLimitedNotification(messageArray[1].substring(0, messageArray[1].indexOf(" ")) + " sent you a new message");
                    gui.ASK_CHAT = true;

                }
            }
            case "ServerVirtualView" -> {
                ClientView.updateServerView(messageArray[1]);
            }
            case "PublicChat" -> {
                ClientView.convertBackPublicChat(messageArray[1]);
            }
            case "notifyUpdate" -> {
                int milliseconds = Integer.parseInt(messageArray[1]);
                if (cli != null) {
                    //System.out.println("Update...");
                    cli.updateCLI(cli, milliseconds);
                } else if (gui != null) {
                    if (!gui.GAME_ON && !gui.GO_TO_MENU) {
                        //Waiting room
                        gui.WAIT_ROOM_REFRESH = true;


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
