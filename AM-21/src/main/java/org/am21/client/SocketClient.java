package org.am21.client;

import org.am21.client.view.Storage;
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
        String[] messageArray = message.split("\\|",3);
        switch (messageArray[0]) {
            case "Return"->{
                ClientCommunicationController.setMethodKey(messageArray[1]);
                ClientCommunicationController.setMethodReturn(Boolean.parseBoolean(messageArray[2]));
            }
            case "Message", "ChatMessage" -> {
                if(cli!=null){
                    //TODO:Print the message from server
                    cli.printer(messageArray[2]);
                    if(Boolean.parseBoolean(messageArray[1])) {
                        cli.refresh(cli);
                    }
                }
                return;
            }
            case "VirtualView" -> {
                if(cli!=null){
                    Storage.setFullViewVariables(messageArray[1],Integer.parseInt(messageArray[2]));
                    cli.checkTurn();
                }
            }
            case "START" -> {
                if(cli!=null) {
                    cli.setGO_TO_MENU(false);
                    cli.setGAME_ON(true);
                    cli.setSTART(true);
                    cli.refresh(cli);
                }
            }
            case "WAIT" -> {
                if (cli != null) {
                    //TODO: need jsonInfo
                    Storage.convertBackMatchInfo(messageArray[1]);
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
                if(cli!=null){
                    cli.setEND(true);
                    cli.setGO_TO_MENU(true);
                    cli.setGAME_ON(false);
                    cli.setSTART(false);
                    cli.printer(SC.WHITE_BB+"\nServer > The match ended. Good Bye! Press 'Enter'"+SC.RST);
                }

            }
            case "VirtualHand" -> {
                Storage.convertBackHand(messageArray[1]);
            }
            default -> System.out.println("Server: " + message);
        }
        //Free CLi
        cli.WAIT_SOCKET = false;

    }
}
