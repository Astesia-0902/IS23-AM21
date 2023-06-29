package org.am21.networkSocket;

import org.am21.controller.CommunicationController;
import org.am21.controller.GameController;
import org.am21.controller.PlayerController;
import org.am21.model.enumer.ConnectionType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.rmi.server.ServerNotActiveException;

public class ClientHandlerSocket extends Thread {
    public Socket clientSocket;
    public PlayerController myPlayer;
    public DataInputStream in;
    public DataOutputStream out;
    public Integer createMatchRequestCount = 0;

    public ClientHandlerSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
        myPlayer = new PlayerController("", this);
        myPlayer.connectionType = ConnectionType.SOCKET;
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
                handleClientMessage(req);
            }
        } catch (IOException | ServerNotActiveException e) {
            //CommunicationController.instance.handlePlayerOffline(myPlayer);
        }
    }


    /**
     * Check if client is close
     *
     * @return true if client is close, else false
     */
    public Boolean isServerClose() {
        try {
            //send to client, if success, return false, else return true
            out.writeUTF("ping");
            out.flush();
            return false;
        } catch (Exception se) {
            return true;
        }
    }

    /**
     * Send any message/command to client
     *
     * @param message message or command
     */
    public void callback(String message) {
        try {
            out.writeUTF(message);
            out.flush();
        } catch (IOException e) {
            System.out.println("Error send message to client");
            //CommunicationController.instance.handlePlayerOffline(myPlayer);
        }
    }

    public void handleClientMessage(String message) throws ServerNotActiveException {
        if (message.equals("")) {
            System.out.println("Empty message from:" + clientSocket.getRemoteSocketAddress());
            return;
        }
        String[] messageParts = message.split("\\|");
        switch (messageParts[0]) {
            case "login":
                String username = messageParts[1];
                CommunicationController.instance.returnBool("login", GameController.login(username, myPlayer), myPlayer);
                //System.out.println("> " + username +" logged in from:" + clientSocket.getRemoteSocketAddress());
                break;

            case "join":
                int gameId = Integer.parseInt(messageParts[1]);
                CommunicationController.instance.returnBool("join", GameController.joinGame(gameId, myPlayer.getPlayer().getNickname(), myPlayer), myPlayer);
                break;

            case "createMatch":
                int playerCount = Integer.parseInt(messageParts[1]);
                CommunicationController.instance.returnBool("createMatch",
                        GameController.createMatch(myPlayer.getPlayer().getNickname(), 0, playerCount, myPlayer), myPlayer);
                break;

            case "selectCell":
                int row = Integer.parseInt(messageParts[1]);
                int col = Integer.parseInt(messageParts[2]);
                CommunicationController.instance.returnBool("selectCell", GameController.selectCell(row, col, myPlayer), myPlayer);
                break;

            case "confirmSelection":
                CommunicationController.instance.returnBool("confirmSelection", GameController.confirmSelection(myPlayer), myPlayer);
                break;

            case "deselectCards":
                CommunicationController.instance.returnBool("deselectCards", GameController.deselectCards(myPlayer), myPlayer);
                break;

            case "sortHand":
                int pos1 = Integer.parseInt(messageParts[1]);
                int pos2 = Integer.parseInt(messageParts[2]);
                CommunicationController.instance.returnBool("sortHand", GameController.sortHand(pos1, pos2, myPlayer), myPlayer);
                break;

            case "insertInColumn":
                int column = Integer.parseInt(messageParts[1]);
                CommunicationController.instance.returnBool("insertInColumn", GameController.insertInColumn(column, myPlayer), myPlayer);
                break;

            case "leaveMatch":
                CommunicationController.instance.returnBool("leaveMatch", GameController.leaveMatch(myPlayer), myPlayer);
                break;

            case "exitGame":
                CommunicationController.instance.returnBool("exitGame", GameController.exitGame(myPlayer), myPlayer);
                break;

            case "endTurn":
                CommunicationController.instance.returnBool("endTurn", GameController.endTurn(myPlayer), myPlayer);
                break;

            case "changeMatchSeats":
                int newMaxSeats = Integer.parseInt(messageParts[1]);
                CommunicationController.instance.returnBool("changeMatchSeats", GameController.changeMatchSeats(newMaxSeats, myPlayer), myPlayer);
                break;

            case "sendPublicMessage":
                String publicMessage = messageParts[1];
                Boolean live_public = Boolean.valueOf(messageParts[2]);
                CommunicationController.instance.returnBool("sendPublicMessage", GameController.forwardPublicMessage(publicMessage, myPlayer), myPlayer);
                break;
            case "sendPrivateMessage":
                String privateMessage = messageParts[1];
                String receiver_private = messageParts[2];
                CommunicationController.instance.returnBool("sendPrivateMessage", GameController.forwardPrivateMessage(privateMessage, receiver_private, myPlayer), myPlayer);
                break;
            default:
                System.out.println("[" + messageParts[0] + "] Unknown command from" + clientSocket.getRemoteSocketAddress());
        }
    }
}
