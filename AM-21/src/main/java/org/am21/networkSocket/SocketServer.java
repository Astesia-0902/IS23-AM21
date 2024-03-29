package org.am21.networkSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class is the socket server
 */
public class SocketServer extends Thread {
    public static int clientCountSocket = 0;
    public static final int port = 8080;

    /**
     * Thread run
     */
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Socket > Server started on port: " + port);
            while(true){
                Socket socketClient = serverSocket.accept();
                clientCountSocket++;
                System.out.println("Socket > Client " + clientCountSocket + " connected from " + socketClient.getRemoteSocketAddress());
                Thread clientHandler = new ClientHandlerSocket(socketClient);
                clientHandler.start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
