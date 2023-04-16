package org.am21.client.view.cli;

import org.am21.client.view.View;
import org.am21.controller.ClientInputHandler;
import org.am21.controller.IClientInput;
import org.am21.model.Player;
import org.am21.model.items.Board;
import org.am21.model.items.Shelf;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Cli implements View {
    private Thread inputThread;
    private IClientInput IClientInputHandler;
    private Player player;

    private static final int SHELF_ROW = 6;
    private static final int SHELF_COLUMN = 5;

    private static final int BOARD_ROW = 8;
    private static final int BOARD_COLUMN = 8;

    public Cli() throws RemoteException {
        this.IClientInputHandler = new ClientInputHandler();
        this.player = new Player(null, null);
    }

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
        //askServerInfo();
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
    public String GoalDescription(int CommonGoalCard) {
        switch (CommonGoalCard){
            case 0:
                return "CommonGoal2Lines: Two columns each formed by 6 different types of tiles.";
            case 1:
                return "CommonGoal2Columns: Two lines each formed by 5 different types of tiles. " +
                        "One line can show the same or a different combination of the other line.";
            case 2:
                return "CommonGoal3Column: Three columns each formed by 6 tiles of maximum three different types. " +
                        "One column can show the same or a different combination of another column.";
            case 3:
                return "CommonGoal4Lines: Four lines each formed by 5 tiles of maximum three different types. " +
                        "One line can show the same or a different combination of another line.";
            case 4:
                return "CommonGoal8Tiles: Eight tiles of the same type. There’s no restriction about the position " +
                        "of these tiles.";
            case 5:
                return "CommonGoalCorner: Four tiles of the same type in the four corners of the bookshelf.";
            case 6:
                return "CommonGoalDiagonal: Five tiles of the same type forming a diagonal.";
            case 7:
                return "CommonGoalSquare: Two groups each containing 4 tiles of the same type in a 2x2 square. " +
                        "The tiles of one square can be different from those of the other square.";
            case 8:
                return "CommonGoalStairs: Five columns of increasing or decreasing height. " +
                        "Starting from the first column on the left or on the right, " +
                        "each next column must be made of exactly one more tile. Tiles can be of any type.";
            case 9:
                return "CommonGoal4Group: Four groups each containing at least 4 tiles of the same type " +
                        "(not necessarily in the depicted shape). The tiles of one group can be different " +
                        "from those of another group.";
            case 10:
                return "CommonGoal6Group: Six groups each containing at least 2 tiles of the same type " +
                        "(not necessarily in the depicted shape). The tiles of one group can be different " +
                        "from those of another group.";
            case 11:
                return "CommonGoalXShape: Five tiles of the same type forming an X.";
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
        boolean usernameAccepted;

        do {
            usernameAccepted = IClientInputHandler.logIn(username);

            if(usernameAccepted){
                System.out.println("Hi, " + username + " is login in the game.");
            } else {
                System.out.println("Username already exists, please re-enter.");
                username = readLine();
            }

        //} while (usernameAccepted);
    } while (!usernameAccepted);

        //player.setNickname(username);
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
        //TODO: fix the null point
        //IClientInputHandler.createMatch(playerNumber);

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
        //} while (playerNumber == 2 || playerNumber == 3 || playerNumber == 4);
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
        //TODO: fixe the null point
       // IClientInputHandler.exitMatch();
        System.out.println("See you soon. Bye.");
    }

    @Override
    public void showCommonGoals() {
        System.out.println("Enter the Common Goal you wish to view: ");
        System.out.println("0. CommonGoal2Lines.");
        System.out.println("1. CommonGoal2Columns.");
        System.out.println("2. CommonGoal3Column.");
        System.out.println("3. CommonGoal4Lines.");
        System.out.println("4. CommonGoal8Tiles.");
        System.out.println("5. CommonGoalCorner.");
        System.out.println("6. CommonGoalDiagonal.");
        System.out.println("7. CommonGoalSquare.");
        System.out.println("8. CommonGoalStairs.");
        System.out.println("9. CommonGoal4Group.");
        System.out.println("10. CommonGoal6Group.");
        System.out.println("11. CommonGoalXShape.");
        int commonGoalCard = -1;
        do {
            try {
                System.out.print("Enter the action you wish to select: ");
                commonGoalCard = Integer.parseInt(readLine());
                if (commonGoalCard < 0 || commonGoalCard > 11){
                    System.out.println("Invalid number! Please try again.");
                }
            } catch (NumberFormatException e){
                System.out.println("Invalid input! Please try again.");
            }

        } while (commonGoalCard >= 0 && commonGoalCard <= 11);
        System.out.println(GoalDescription(commonGoalCard));
    }

    @Override
    public void showPersonalGoal() {
        System.out.println(player.getNickname() + "'s PersonalGoal:" + player.getMyPersonalGoal().getNameCard());
        showShelf(player.getMyPersonalGoal().getPersonalGoalShelf());
    }

    @Override
    public void showCurrentPlayer() {
        System.out.println(player.getNickname() + ", it's your turn!");
    }

    @Override
    public void showShelf(Shelf userShelf) {
        System.out.println(userShelf.player.getNickname() + "'s Shelf:");
        for(int i=0;i<SHELF_ROW;i++){
            for(int j=0;j<SHELF_COLUMN;j++){
                if(userShelf.getMatrix()[i][j]==null){
                    System.out.print("[______._]");
                }else if(userShelf.getMatrix()[i][j]!=null){
                    System.out.print("["+ userShelf.getMatrix()[i][j].getNameCard() +"]");
                }
            }
            System.out.println();
        }
    }

    @Override
    public void showBoard() {
        Board board = player.getMatch().board;
        System.out.println("Board:");
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(!board.isPlayable(i,j)){
                    System.out.print("[______._]");

                }else {
                    if (board.getMatrix()[i][j] == null) {
                        System.out.print("[______._]");
                    } else if (board.getMatrix()[i][j] != null) {
                        System.out.print("[" + board.getMatrix()[i][j].getNameCard() + "]");
                    }
                }
            }
            System.out.println();
        }
    }

    @Override
    public void showPlayersStats() {
        List<Player> playerList = player.getMatch().playerList;
        for (int i = 0; i < playerList.size(); i++) {
            System.out.println(playerList.get(i).getNickname() + "'s stats:");
            System.out.println(playerList.get(i).getNickname() + "'s score: " + playerList.get(i).getPlayerScore());
            showShelf(playerList.get(i).getShelf());
        }
    }

    @Override
    public void askSelection() throws ServerNotActiveException, RemoteException {
        showBoard();
        int row = askCoordinates().get(0);
        int column = askCoordinates().get(1);
        IClientInputHandler.selectCell(row, column);
        System.out.println("Selection Successful!");
    }

    @Override
    public List<Integer> askCoordinates() {
        System.out.println("Enter the coordinates you wish to select [row, column].");
        int selectConfirm = 0;
        int selectRow = 0, selectColumn = 0;

        while(selectConfirm != 1) {
            do {
                try {
                    System.out.print("row (0 to " + BOARD_ROW + "): ");
                    selectRow = Integer.parseInt(readLine());
                    if (selectRow < 0 || selectRow > BOARD_ROW) {
                        System.out.println("Invalid number! Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please try again.");
                }
            } while (selectRow >= 0 && selectRow <= BOARD_ROW);

            do {
                try {
                    System.out.print("column (0 to " + BOARD_COLUMN + "): ");
                    selectColumn = Integer.parseInt(readLine());
                    if (selectColumn < 0 || selectColumn > BOARD_COLUMN) {
                        System.out.println("Invalid number! Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please try again.");
                }
            } while (selectColumn >= 0 && selectColumn <= BOARD_COLUMN);

            System.out.print("The coordinates you have chosen are: [" + selectRow + ", " + selectColumn + "] - ");
            showItemInCell(selectRow, selectColumn);
            System.out.println("Confirm your choice:");
            System.out.println("1. Confirm.");
            System.out.println("0. Re-select.");

            selectConfirm = Integer.parseInt(readLine());
        }
        List<Integer> coordinates = new ArrayList<>();
        coordinates.add(selectRow);
        coordinates.add(selectColumn);
        return coordinates;
    }

    @Override
    public void showItemInCell(int row, int column) {
        System.out.println(player.getMatch().board.getItemName(row,column));
    }

    @Override
    public void askPlayerMove() throws RemoteException, ServerNotActiveException {
        System.out.println("What's your next move?");
        System.out.println("1. Select an item on the board.");
        System.out.println("2. Deselect the cards.");
        System.out.println("3. Insert in the shelf.");
        System.out.println("4. See Personal goal.");
        System.out.println("5. See Common goal.");
        System.out.println("6. See Shelf.");
        System.out.println("7. See Board.");
        System.out.println("8. See Players Stats.");
        System.out.println("9. Help.");
        System.out.println("10. Send Message to General chat.");
        System.out.println("11. Check EndGameToken.");
        System.out.println("12. Show timer.");
        System.out.println("13. Quit Match.");
        System.out.println("0. Cancel option.");

        System.out.print("Enter the action you wish to select: ");
        int option = Integer.parseInt(readLine());
        while (option != 0) {
            switch (option) {
                case 1:
                    askSelection();
                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:
                    showPersonalGoal();
                    break;
                case 5:
                    showCommonGoals();
                    break;
                case 6:
                    showShelf(player.getShelf());
                    break;
                case 7:
                    showBoard();
                    break;
                case 8:
                    showPlayersStats();
                    break;
                case 9:

                    break;
                case 10:

                    break;
                case 11:

                    break;
                case 12:

                    break;
                case 13:
                    askLeaveGame();
                    break;

                default:
                    option = Integer.parseInt(readLine());
                    break;
            }
        }
    }
}
