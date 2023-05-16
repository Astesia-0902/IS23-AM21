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
    public static final String serverName = "localhost";
    public static final int serverPort = 8080;
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
                }
                return;
            }
            case "VirtualView" -> {
                if (cli != null) {
                    ClientView.setFullViewVariables(messageArray[1], Integer.parseInt(messageArray[2]));
                    cli.checkTurn();
                    cli.updateCLI(cli, 500);
                }
            }
            case "START" -> {
                if (cli != null) {
                    cli.setGO_TO_MENU(false);
                    cli.setGAME_ON(true);
                    cli.setSTART(true);
                    cli.updateCLI(cli, 1000);
                }
            }
            case "WAIT" -> {
                if (cli != null) {
                    ClientView.convertBackMatchInfo(messageArray[1]);
                    cli.setGAME_ON(false);
                    cli.setGO_TO_MENU(false);
                }
            }
            case "GoToMenu" -> {
                if (cli != null) {
                    cli.setGO_TO_MENU(true);
                    cli.setGAME_ON(false);
                }
            }
            case "EndMatch" -> {
                if (cli != null) {
                    cli.setEND(true);
                    cli.setGO_TO_MENU(true);
                    cli.setGAME_ON(false);
                    cli.setSTART(false);
                    cli.printer(SC.WHITE_BB + "\nServer > The match ended. Good Bye! Press 'Enter'" + SC.RST);
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
                }
            }

            default -> System.out.println("Unknown Server Message: " + message);
        }
        //Free CLi
        if (cli != null)
            cli.WAIT_SOCKET = false;

    }
}
