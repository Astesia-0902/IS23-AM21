package org.am21.controller;

import org.am21.client.view.TUI.InputReadTask;
import org.am21.model.GameManager;
import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.model.enumer.ServerMessage;
import org.am21.networkRMI.ClientInputHandler;
import org.am21.networkRMI.IClientInput;
import org.am21.networkRMI.Lobby;
import org.am21.networkRMI.Welcome;
import org.am21.networkSocket.SocketServer;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Server class
 * <p>
 * Requirements:
 * - Uniqueness of the nickname is granted by the server in phase of acceptance of the player
 */
public class Server {
    public static int number = 0;
    private static Thread inputThread;
    private static class HeartbeatTask extends TimerTask {
        @Override
        public void run() {
            GameManager.checkUsersConnection();
        }
    }

    public static void main(String[] args) throws RemoteException {
        System.out.print("Server address: ");
        String serverAddress = readLine();


        try {
            SocketServer server = new SocketServer();
            server.start();

            LocateRegistry.createRegistry(1234);
            LocateRegistry.createRegistry(8807);

            Lobby guardian = new Welcome();
            Naming.bind("rmi://"+serverAddress+":1234/Welcome", guardian);

            Timer timer = new Timer();
            timer.schedule(new HeartbeatTask(), 1000, 10000);

            System.out.println("Server is ready");
            while (true) {
                String input = readLine();
                if (input.equals("reset")) {
                    resetServer();
                    System.out.println("Server > Reset Done");
                }
                if (input.equals("pl")) {
                    printPlayers();
                }
                if (input.equals("ml")) {
                    printMatchMap();
                }
                if (input.equals("bl")) {
                    printBindList();
                }
                if (input.equals("end")) {
                    System.exit(0);
                }
                //Thread.sleep(1000);
            }

        } catch (Exception ignored) {

        }
    }

    private static void printBindList() {
        if (number == 0) {
            System.out.println("No bind yet");
            return;
        }
        for (int i = number; i > 0; i--) {
            String path = "";
            path += "rmi://localhost:8807/";
            System.out.println((path + i));
        }

    }

    public static String genNewRoot() {
        number++;
        return String.valueOf(number);
    }

    public static String newBind(String root) {
        String path = "";
        path += "rmi://localhost:8807/";
        path += root;
        return path;
    }

    public static void welcomeNewClient(String path, IClientInput cIH) throws MalformedURLException, AlreadyBoundException, RemoteException {
        Naming.bind(path, cIH);
    }

    public static void done() throws RemoteException, MalformedURLException, AlreadyBoundException {
        IClientInput cIH = new ClientInputHandler();
        welcomeNewClient(newBind(genNewRoot()), cIH);
    }

    private static String readLine() {
        FutureTask<String> futureTask = new FutureTask<>(new InputReadTask());
        inputThread = new Thread(futureTask);
        inputThread.start();


        String input = null;
        try {
            input = futureTask.get();
        } catch (InterruptedException e) {
            futureTask.cancel(true);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return input;
    }

    private static void resetServer() {
        GameManager.matchMap.clear();
        GameManager.playerMatchMap.clear();
        GameManager.client_connected = 0;
        GameManager.players.clear();

        for (int i = number; i > 0; i--) {
            String path = "";
            path += "rmi://localhost:8807/";
            try {
                Naming.unbind(path + i);
            } catch (RemoteException | MalformedURLException | NotBoundException e) {
                throw new RuntimeException(e);
            }
        }
        Server.number = 0;

    }

    private static void printPlayers() {
        String message = "";
        System.out.println(ServerMessage.ListP.value());
        synchronized (GameManager.players) {
            for (Player p : GameManager.players) {

                message += ("[" + p.getNickname() + " | " + p.getStatus() + " ]\n");

            }
        }
        System.out.println(message);

    }

    private static void printMatchMap() {
        String message = "";
        System.out.println("Match List: ");
        synchronized (GameManager.matchMap) {
            if (GameManager.matchMap.size() > 0) {
                for (Map.Entry<Integer, Match>  entry : GameManager.matchMap.entrySet()) {
                    Match m = entry.getValue();
                    message += ("[ID: " + m.matchID + " | " + m.gameState + " | Players: (" + m.playerList.size() + "/" + m.maxSeats + ")]\n");
                }
            }
        }
        System.out.println(message);
    }
}
