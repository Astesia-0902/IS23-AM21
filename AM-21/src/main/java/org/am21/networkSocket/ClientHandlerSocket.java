package org.am21.networkSocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandlerSocket extends Thread {
    public Socket clientSocket;
    public DataInputStream in;
    public DataOutputStream out;

    public ClientHandlerSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Thread run
     */
    @Override
    public void run() {
        try {

            //Receive message from client
            while (true) {
                String req = in.readUTF();
                //TODO:Deal with client input
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Send any message/command to client
     * @param message message or command
     */
    public void callback(String message){
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
