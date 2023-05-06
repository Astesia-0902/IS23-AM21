package org.am21.client;

import org.am21.client.view.Storage;
import org.am21.client.view.TUI.Cli;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketClient extends Thread {
    public static final String serverName = "localhost";
    public static final int serverPort = 8080;
    public static Socket socketClient;
    public static Cli cli;

    @Override
    public void run() {
        try {
            socketClient = new Socket(serverName, serverPort);
            System.out.println("Connected to " + socketClient.getRemoteSocketAddress());

            DataInputStream in = new DataInputStream(socketClient.getInputStream());
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
            socketClient.getOutputStream().write(message.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void handleServerMessage(String message) {
        System.out.println("Dealing with server message");
        String[] messageArray = message.split("\\|");
        switch (messageArray[0]) {
            case "Message","ChatMessage"-> System.out.println(messageArray[1]);
            case "VirtualView"-> Storage.setFullViewVariables(messageArray[1], Integer.parseInt(messageArray[2]));
            case "START"-> System.out.println("Match started");
            case "WAIT"-> {System.out.println("Waiting for other players...");
                if(cli!=null){
                    //TODO: need jsonInfo
                    //Storage.convertBackMatchInfo(jsonInfo);
                    cli.setGAME_ON(false);
                    cli.setGO_TO_MENU(false);
                }
            }
            case "GoToMenu"->{
                if(cli!=null){
                    cli.setGO_TO_MENU(true);
                    cli.setGAME_ON(false);
                }
            }
            case "EndMatch"-> System.out.println("Match ended");
            case "VirtualHand"-> Storage.convertBackHand(messageArray[1]);
            default-> System.out.println("Server: " + message);
        }
    }
}
