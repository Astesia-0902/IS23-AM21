package org.am21.game;

import org.am21.client.view.cli.InputReadTask;
import org.am21.model.GameManager;
import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.model.enumer.ServerMessage;
import org.am21.model.enumer.UserStatus;
import org.am21.networkRMI.ClientInputHandler;
import org.am21.networkRMI.IClientInput;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class GameAppTest {
    private static Thread inputThread;

    public static void main(String[] args) {

        try {

            LocateRegistry.createRegistry(8807);
            IClientInput IClientInputHandler = new ClientInputHandler();
            Naming.bind("rmi://localhost:8807/ClientInputHandler", IClientInputHandler);

            System.out.println("Server is ready");
            while (true) {
                String input = readLine();
                if (input.equals("reset")) {
                    resetServer();
                    System.out.println("Server > Reset Done");
                }
                if (input.equals("pl")) {
                    printOnlinePlayers();
                }
                if (input.equals("ml")) {
                    printMatchList();
                }

                //Thread.sleep(1000);
            }


        } catch (Exception e) {

        }
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
        GameManager.matchList.clear();
        GameManager.playerMatchMap.clear();
        GameManager.client_connected = 0;
        GameManager.players.clear();

    }

    private static void printOnlinePlayers() {
        String message = "";
        System.out.println(ServerMessage.ListP.value());
        synchronized (GameManager.players) {
            for (Player p : GameManager.players) {
                if (p.getStatus() == UserStatus.Online || p.getStatus() == UserStatus.GameMember) {
                    message += ("[" + p.getNickname() + " | "+p.getStatus()+" ]\n ");

                }
            }
        }
        System.out.println(message);

    }

    private static void printMatchList() throws RemoteException {
        String message="";
        System.out.println("Match List: ");
        synchronized (GameManager.matchList) {
            if(GameManager.matchList.size()>0){
                for (Match m : GameManager.matchList){
                    message += ("[ID: "+m.matchID+" | "+m.gameState+" | Players: ("+m.playerList.size()+"/"+m.maxSeats+")]\n");
                }
            }
        }
        System.out.println(message);
    }
}
