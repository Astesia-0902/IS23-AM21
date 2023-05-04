package org.am21.networkSocket;

import org.am21.controller.GameController;
import org.am21.controller.PlayerController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public class ClientHandlerSocket extends Thread {
    public Socket clientSocket;
    public PlayerController myPlayer;
    public DataInputStream in;
    public DataOutputStream out;
    public Integer createMatchRequestCount = 0;

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
                handleClientMessage(req);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServerNotActiveException e) {
            throw new RuntimeException(e);
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
        } catch (IOException e) {
            throw new RuntimeException(e);
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
                GameController.login(username, myPlayer);
                System.out.println("login command from:" + clientSocket.getRemoteSocketAddress() + " username:" + username);
                break;

            case "join":
                int gameId = Integer.parseInt(messageParts[1]);
                GameController.joinGame(gameId, myPlayer.getPlayer().getNickname(), myPlayer);
                break;

            case "createMatch":
                int playerCount = Integer.parseInt(messageParts[1]);
                GameController.createMatch(myPlayer.getPlayer().getNickname(), 0, playerCount, myPlayer);
                break;

            case "selectCell":
                int row = Integer.parseInt(messageParts[1]);
                int col = Integer.parseInt(messageParts[2]);
                GameController.selectCell(row, col, myPlayer);
                break;

            case "confirmSelection":
                GameController.confirmSelection(myPlayer);
                break;

            case "deselect":
                GameController.deselectCards(myPlayer);
                break;

            case "sortHand":
                int pos1 = Integer.parseInt(messageParts[1]);
                int pos2 = Integer.parseInt(messageParts[2]);
                GameController.sortHand(pos1, pos2, myPlayer);
                break;

            case "insertInColumn":
                int column = Integer.parseInt(messageParts[1]);
                GameController.insertInColumn(column, myPlayer);
                break;

            case "leaveMatch":
                GameController.leaveMatch(myPlayer);
                break;

            case "exitGame":
                GameController.exitGame(myPlayer);
                break;

            case "endTurn":
                GameController.endTurn(myPlayer);
                break;

            case "getVirtualView":
                GameController.getVirtualView(myPlayer);
                break;

            case "sendChatMessage":
                String chatMessage = messageParts[1];
                try {
                    GameController.sendChatMessage(chatMessage, myPlayer);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                break;

            case "printOnlinePlayers":
                GameController.printOnlinePlayers(myPlayer);
                break;

            case "printMatchList":
                GameController.printMatchList(myPlayer);
                break;

            case "changeMatchSeats":
                int newMaxSeats = Integer.parseInt(messageParts[1]);
                GameController.changeMatchSeats(newMaxSeats, myPlayer);
                break;

            case "changeInsertLimit":
                int newInsertLimit = Integer.parseInt(messageParts[1]);
                GameController.changeInsertLimit(newInsertLimit, myPlayer);
                break;

            default:
                System.out.println("Unknown command from" + clientSocket.getRemoteSocketAddress() + " command:" + messageParts[0]);
        }
    }
}
