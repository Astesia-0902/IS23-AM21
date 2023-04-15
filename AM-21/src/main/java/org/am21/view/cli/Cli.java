package org.am21.view.cli;

import org.am21.view.View;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Cli implements View {
    private Thread inputThread;

    /**
     * Read a line from the standard input
     *
     * @return the string read from the input
     * */
    public String readLine() {
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

    public void init(){
        System.out.println("Welcome to MyShelfie Board Game!");
    }

    public void askServerInfo()throws ExecutionException{
        Map<String, String> serverInfo = new HashMap<>();
        String defaultAddress = "localhost";
        String defaultPort = "8807";

        System.out.println("Enter the server address: ["+ defaultAddress + "]");
        String address = readLine();

        if (address.equals("")){
            serverInfo.put("address", defaultAddress);
        }else {
            serverInfo.put("address", address);
        }

        System.out.print("Enter the server Port: ["+ defaultPort + "]");
        String port = readLine();

        if (port.equals("")){
            serverInfo.put("port", defaultPort);
        }else {
            serverInfo.put("port", port);
        }
    }

//    public void clearCli(){
//        System.out.println("\033[H\033[2J");
//        System.out.flush();
//    }

    @Override
    public void askLogin() {
        System.out.print("Enter the username: ");
        String username = readLine();
        boolean usernameAccepted = false;

        do {
            //usernameAccepted = login(username);

            if(usernameAccepted){
                System.out.println("Hi, " + username + " Login games.");
            } else {
                System.out.println("Username already exists, please re-enter.");
                username = readLine();
            }

        } while (usernameAccepted);

    }

    @Override
    public void askAction() {
        System.out.println("Game Option:");
        System.out.println("1. Create a new match.");
        System.out.println("2. Join a match.");
        System.out.println("3. Leave the game.");
        System.out.println("0. Cancel option.");

        System.out.print("Enter the action you wish to select: ");
        int option = Integer.parseInt(readLine());
        while (option != 0) {
            switch (option) {
                case 1:
                    askCreateGame();
                    break;
                case 2:
                    askJoinGame();
                    break;
                case 3:
                    askLeaveGame();
                    break;
                default:
                    option = Integer.parseInt(readLine());
                    break;
            }
        }
        //System.exit(0);
    }

    @Override
    public void askCreateGame() {
        System.out.println("Room generation in  progress...");
        int playerNumber = askMaxSeats();
        int matchID;

        //createMatch(playerNumber);
        System.out.println("Successfully created a game for " + playerNumber + " persons!");
        Random random = new Random();
        matchID = random.nextInt();
        System.out.println("The room number is " + matchID);
        System.out.println("Waiting for players...");
    }

    @Override
    public int askMaxSeats() {
        int playerNumber = 0;
        do {
            try {
                System.out.print("Please select the number of players [2 to 4]: ");
                playerNumber = Integer.parseInt(readLine());
                if (playerNumber == 2 || playerNumber == 3 || playerNumber == 4){
                    System.out.println("Ok");
                }
            } catch (NumberFormatException e){
                System.out.println("Invalid input! Please try again.");
            }
        } while (playerNumber == 2 || playerNumber == 3 || playerNumber == 4);
        return playerNumber;
    }

    @Override
    public void askJoinGame() {
        boolean replyAction = false;
        int matchID;
        do {
            try {
                System.out.print("Please select the number of players [2 to 4]: ");
                matchID = Integer.parseInt(readLine());

                //boolean replyAction = joinGame(matchID);

                if (replyAction){
                    System.out.println("Joining the match: " + matchID);
                }
            } catch (NumberFormatException e){
                System.out.println("Invalid input! Please try again.");
            }
        } while (replyAction);

    }

    @Override
    public void askLeaveGame() {
        //leaveGame();
        System.out.println("See you soon. Bye.");
    }

    @Override
    public void showCommonGoals(String username) {

    }

    @Override
    public void showPersonalGoal(String username) {

    }

    @Override
    public void showCurrentPlayer(String username) {

    }
}
