package org.am21.networkSocket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketClient extends Thread {
    public static final String serverName = "localhost";
    public static final int serverPort = 8080;

    @Override
    public void run() {
        try {
            Socket clientSocket = new Socket(serverName, serverPort);
            System.out.println("Connected to " + clientSocket.getRemoteSocketAddress());

            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            while (true) {
                String response = in.readUTF();
                //TODO:Handle message from the server
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
