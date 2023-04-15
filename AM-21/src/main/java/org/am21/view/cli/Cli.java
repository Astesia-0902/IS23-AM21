package org.am21.view.cli;

import org.am21.controller.ClientInput;
import org.am21.model.items.Shelf;
import org.am21.view.View;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Cli implements View {
    private Thread inputThread;
    private ClientInput clientInputHandler;



    private static final int SHELF_ROW = 6;
    private static final int SHELF_COLUMN = 5;


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

    public void init() throws ExecutionException {

        System.out.println("Welcome to MyShelfie Board Game!");
        askServerInfo();
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

    @Override
    public String showDescription(int CommonGoalCard) {
        switch (CommonGoalCard){
            case 0:
                return "Two columns each formed by 6 different types of tiles.";
            case 1:
                return "Two lines each formed by 5 different types of tiles. One line can show the " +
                        "same or a different combination of the other line.";
            case 2:
                return "Three columns each formed by 6 tiles of maximum three different types. One " +
                        "column can show the same or a different combination of another column.";
            case 3:
                return "Four lines each formed by 5 tiles of maximum three different types. One " +
                        "line can show the same or a different combination of another line.";
            case 4:
                return "Eight tiles of the same type. There’s no restriction about the position of " +
                        "these tiles.";
            case 5:
                return "Four tiles of the same type in the four corners of the bookshelf.";
            case 6:
                return "Five tiles of the same type forming a diagonal.";
            case 7:
                return "Two groups each containing 4 tiles of the same type in a 2x2 square. The tiles " +
                        "of one square can be different from those of the other square.";
            case 8:
                return "Five columns of increasing or decreasing height. Starting from the first column on " +
                        "the left or on the right, each next column must be made of exactly one more tile. " +
                        "Tiles can be of any type.";
            case 9:
                return "Four groups each containing at least 4 tiles of the same type (not necessarily " +
                        "in the depicted shape). The tiles of one group can be different " +
                        "from those of another group.";
            case 10:
                return "Six groups each containing at least 2 tiles of the same type (not necessarily " +
                        "in the depicted shape). The tiles of one group can be different " +
                        "from those of another group.";
            case 11:
                return "Five tiles of the same type forming an X.";
        }
        return null;
    }

    //    public void clearCli(){
//        System.out.println("\033[H\033[2J");
//        System.out.flush();
//    }

    @Override
    public void askLogin() throws ServerNotActiveException, RemoteException {
        System.out.print("Enter the username: ");
        String username = readLine();
        //boolean usernameAccepted = false;
        boolean usernameAccepted = true;

        do {
            //usernameAccepted = clientInputHandler.logIn(username);

            if (usernameAccepted) {
                System.out.println("Hi, " + username + " is login in the game.");
            } else {
                System.out.println("Username already exists, please re-enter.");
                username = readLine();
            }

        } while (!usernameAccepted);
    //}while (usernameAccepted);
        System.out.println("----------------------------");
    }

    @Override
    public void askAction() throws ServerNotActiveException, RemoteException {
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
    public void askCreateGame() throws ServerNotActiveException, RemoteException {
        System.out.println("Room generation in  progress...");
        int playerNumber = askMaxSeats();
        int matchID;

        //TODO: fixe the null point
        clientInputHandler.createMatch(playerNumber);

        System.out.println("Successfully created a game for " + playerNumber + " persons!");
        Random random = new Random();
        matchID = random.nextInt(100);
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
                if (playerNumber != 2 && playerNumber != 3 && playerNumber != 4){
                    System.out.println("Invalid number! Please try again.");
                }
            } catch (NumberFormatException e){
                System.out.println("Invalid input! Please try again.");
            }
        } while (playerNumber != 2 && playerNumber != 3 && playerNumber != 4);
        System.out.println("Ok");
        return playerNumber;
    }

    @Override
    public void askJoinGame() throws ServerNotActiveException, RemoteException {
        boolean replyAction = false;
        int matchID;
        do {
            try {
                System.out.print("Please select the number of players [2 to 4]: ");
                matchID = Integer.parseInt(readLine());

                //replyAction = clientInputHandler.joinGame(matchID);

                if (replyAction){
                    System.out.println("Joining the match: " + matchID);
                }
            } catch (NumberFormatException e){
                System.out.println("Invalid input! Please try again.");
            }
        } while (replyAction);

    }

    @Override
    public void askLeaveGame() throws RemoteException {
        //TODO: fix the null point
        clientInputHandler.exitMatch();
        System.out.println("See you soon. Bye.");
    }

    @Override
    public void showCommonGoals(String username, int commonGoalCard) {
        System.out.println(username+"'s CommonGaol:");
        System.out.println(showDescription(commonGoalCard));
    }

    @Override
    public void showPersonalGoal(String username, Shelf personalGoalCard) {
        System.out.println(username+"'s PersonalGoal:");
        for(int i=0;i<SHELF_ROW;i++){
            for(int j=0;j<SHELF_COLUMN;j++){
                if(personalGoalCard.getMatrix()[i][j]==null){
                    System.out.print("[______._]");
                }else if(personalGoalCard.getMatrix()[i][j]!=null){
                    System.out.print("["+ personalGoalCard.getMatrix()[i][j].getNameCard() +"]");
                }
            }
            System.out.println();
        }
    }

    @Override
    public void showCurrentPlayer(String username) {

    }
}
