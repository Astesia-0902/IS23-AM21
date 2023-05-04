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
            throw new RuntimeException(e);
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
        String[] messageArray = message.split("\\|");
        switch (messageArray[0]) {
            case "Message":
            case "ChatMessage":
                System.out.println(messageArray[1]);
                break;
            case "VirtualView":
                Storage.setFullViewVariables(messageArray[1], Integer.parseInt(messageArray[2]));
                break;
            case "START":
                System.out.println("Match started");
                break;
            case "WAIT":
                System.out.println("Waiting for other players...");
                break;
            case "GoToMenu":
                break;
            case "EndMatch":
                System.out.println("Match ended");
                break;
            case "VirtualHand":
                Storage.convertBackHand(messageArray[1]);
            default:
                System.out.println("Server: " + message);
        }
    }
}
