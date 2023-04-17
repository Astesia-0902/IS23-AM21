package org.am21.client.view.cli;

import org.am21.client.view.JSONConverter;
import org.am21.client.view.View;
import org.am21.controller.IClientInput;
import org.am21.model.Player;
import org.am21.model.items.Board;
import org.am21.model.items.Shelf;
import org.am21.utilities.CardPointer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Cli implements View {
    private Thread inputThread;
    private IClientInput IClientInputHandler;
    //TODO: Replace player with JSONConverter
    private Player player;
    private final List<String> chatHistory = new ArrayList<>();

    private static final int SHELF_ROW = 6;
    private static final int SHELF_COLUMN = 5;

    private static final int BOARD_ROW = 9;
    private static final int BOARD_COLUMN = 9;


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

    public void init() throws ExecutionException, MalformedURLException, NotBoundException, RemoteException {
        System.out.println("Welcome to MyShelfie Board Game!");
        askServerInfo();
    }

    public void askServerInfo() throws ExecutionException, MalformedURLException, NotBoundException, RemoteException {
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
        IClientInputHandler = (IClientInput) Naming.lookup("rmi://" + serverInfo.get("address") + ":"
                + serverInfo.get("port") + "/ClientInputHandler");
    }

    public void showGoalDescription(int CommonGoalCard) {
        switch (CommonGoalCard){
            case 0:
                System.out.println("CommonGoal2Lines: Two columns each formed by 6 different types of tiles.");
                break;
            case 1:
                System.out.println("CommonGoal2Columns: Two lines each formed by 5 different types of tiles. " +
                        "One line can show the same or a different combination of the other line.");
                break;
            case 2:
                System.out.println("CommonGoal3Column: Three columns each formed by 6 tiles of maximum three " +
                        "different types. One column can show the same or a different combination of another column.");
                break;
            case 3:
                System.out.println("CommonGoal4Lines: Four lines each formed by 5 tiles of maximum three " +
                        "different types. One line can show the same or a different combination of another line.");
                break;
            case 4:
                System.out.println("CommonGoal8Tiles: Eight tiles of the same type. " +
                        "There’s no restriction about the position of these tiles.");
                break;
            case 5:
                System.out.println("CommonGoalCorner: Four tiles of the same type in the four corners of " +
                        "the bookshelf.");
                break;
            case 6:
                System.out.println("CommonGoalDiagonal: Five tiles of the same type forming a diagonal.");
                break;
            case 7:
                System.out.println("CommonGoalSquare: Two groups each containing 4 tiles of the same type in a 2x2 " +
                        "square. The tiles of one square can be different from those of the other square.");
            case 8:
                System.out.println("CommonGoalStairs: Five columns of increasing or decreasing height. " +
                        "Starting from the first column on the left or on the right, " +
                        "each next column must be made of exactly one more tile. Tiles can be of any type.");
                break;
            case 9:
                System.out.println("CommonGoal4Group: Four groups each containing at least 4 tiles of the same type " +
                        "(not necessarily in the depicted shape). The tiles of one group can be different " +
                        "from those of another group.");
                break;
            case 10:
                System.out.println("CommonGoal6Group: Six groups each containing at least 2 tiles of the same type " +
                        "(not necessarily in the depicted shape). The tiles of one group can be different " +
                        "from those of another group.");
                break;
            case 11:
                System.out.println("CommonGoalXShape: Five tiles of the same type forming an X.");
                break;
        }
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
        } while (!usernameAccepted);
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
        IClientInputHandler.createMatch(playerNumber);

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
                replyAction = IClientInputHandler.joinGame(matchID);

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
        IClientInputHandler.exitMatch();
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
        int commonGoalCard = 0;
        do {
            try {
                System.out.print("Enter the Common Goal you wish to see: ");
                commonGoalCard = Integer.parseInt(readLine());
                if (commonGoalCard < 0 || commonGoalCard > 11){
                    System.out.println("Invalid number! Please try again.");
                }
            } catch (NumberFormatException e){
                System.out.println("Invalid input! Please try again.");
            }

        } while (commonGoalCard < 0 || commonGoalCard > 11);
        showGoalDescription(commonGoalCard);
        //TODO: Replace player with JSONConverter
        System.out.println("You received: " + player.getPlayerScore() + " points.");
    }

    @Override
    public void showPersonalGoal() {
        //TODO: Replace player with JSONConverter
        System.out.println(player.getNickname() + "'s PersonalGoal:" + player.getMyPersonalGoal().getNameCard());
        showShelf(player.getMyPersonalGoal().getPersonalGoalShelf());
        System.out.println("You matched " + player.getMyPersonalGoal().checkGoal() + " item and scored " +
                player.getMyPersonalGoal().calculatePoints() + "points! Good!");
    }

    @Override
    public void showCurrentPlayer() {
        System.out.println(JSONConverter.currentPlayer + ", it's your turn!");
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
        //TODO: Replace player with JSONConverter
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
        //TODO: Replace player with JSONConverter
        List<Player> playerList = player.getMatch().playerList;
        for (int i = 0; i < playerList.size(); i++) {
            System.out.println(playerList.get(i).getNickname() + "'s stats: " + playerList.get(i).getStatus());
            System.out.println(playerList.get(i).getNickname() + "'s score: " + playerList.get(i).getPlayerScore());
            showShelf(playerList.get(i).getShelf());
        }
    }

    @Override
    public void askSelection() throws ServerNotActiveException, RemoteException {
        showBoard();
        int numCard = 0;
        do {
            try {
                System.out.println("How many cards do you want to select [1 to 3]?");
                numCard = Integer.parseInt(readLine());
                if (numCard <= 0 || numCard > 3) {
                    System.out.println("Invalid number! Please try again.");
                } else {
                    int count = 1;
                    while (count != numCard) {
                        int row = askCoordinates().get(0);
                        int column = askCoordinates().get(1);

                        IClientInputHandler.selectCell(row, column);
                        //TODO: Cases of failure
                        System.out.println("Selection Successful!");
                        count++;
                    }
                }
            } catch (NumberFormatException e){
                System.out.println("Invalid input! Please try again.");
            }
        } while (numCard <= 0 || numCard > 3);
    }

    public List<Integer> askCoordinates() {
        System.out.println("Enter the coordinates you wish to select [row, column].");
        boolean selectConfirm = false;
        int selectRow = 0, selectColumn = 0;

        while(!selectConfirm) {
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
            } while (selectRow < 0 || selectRow > BOARD_ROW);

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
            } while (selectColumn < 0 || selectColumn > BOARD_COLUMN);

            System.out.print("The coordinates you have chosen are: [" + selectRow + ", " + selectColumn + "] - ");
            showItemInCell(selectRow, selectColumn);
            System.out.println("Confirm your choice:");
            System.out.println("1. Confirm.");
            System.out.println("0. Re-select.");

            selectConfirm = Boolean.parseBoolean(readLine());
        }
        List<Integer> coordinates = new ArrayList<>();
        coordinates.add(selectRow);
        coordinates.add(selectColumn);
        return coordinates;
    }

    public void showItemInCell(int row, int column) {
        //TODO: Replace player with JSONConverter
        System.out.println(player.getMatch().board.getItemName(row,column));
    }

    @Override
    public void askDeselection() throws ServerNotActiveException, RemoteException {
        showHand();
        System.out.println("Do you want to cancel all selected cards?");
        System.out.println("1. Yes.");
        System.out.println("0. No.");
        boolean deselectConfirm = Boolean.parseBoolean(readLine());
        if(deselectConfirm){
            //IClientInputHandler.unselectCards();?
            System.out.println("Successfully removed all selected cards!");
        }
    }

    @Override
    public void askInsertion() throws ServerNotActiveException, RemoteException {
        showHand();
        boolean sort;
        do {
            System.out.println("Do you want to change insertion order?");
            System.out.println("1. Yes.");
            System.out.println("0. No.");
            sort = Boolean.parseBoolean(readLine());
            if(sort){
                askSort();
            } else {
                int column = askColumn();
                IClientInputHandler.insertInColumn(column);
                System.out.println("Cards are correctly inserted in the shelf!");
            }
        } while (sort);
    }

    public void askSort() throws ServerNotActiveException, RemoteException {
        List<Integer> itemSwapped = askIndex();
        IClientInputHandler.sortHand(itemSwapped.get(0), itemSwapped.get(1));
        System.out.println("Card order changed.");
    }

    public List<Integer> askIndex() {
        System.out.println("Which cards should switch?");
        boolean sortConfirm = false;
        int position1 = 1, position2 = 2;
        //TODO: Replace player with JSONConverter
        ArrayList<CardPointer> cardPointers = player.getHand().getSlot();
        while(!sortConfirm) {
            do {
                try {
                    System.out.print("position1 (1 to " + cardPointers.size() + "): ");
                    position1 = Integer.parseInt(readLine());
                    if (position1 < 1 || position1 > cardPointers.size()) {
                        System.out.println("Invalid number! Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please try again.");
                }
            } while (position1 < 1 || position1 > cardPointers.size());

            do {
                try {
                    System.out.print("position2 (1 to " + cardPointers.size() + "): ");
                    position2 = Integer.parseInt(readLine());
                    if (position2 < 1 || position2 > cardPointers.size()) {
                        System.out.println("Invalid number! Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please try again.");
                }
            } while (position2 < 1 || position2 > cardPointers.size());

            System.out.println("You have chosen to swap " + cardPointers.get(position1).item + " and " +
                    cardPointers.get(position2).item);
            System.out.println("Confirm your choice:");
            System.out.println("1. Confirm.");
            System.out.println("0. Re-select.");

            sortConfirm = Boolean.parseBoolean(readLine());
        }
        List<Integer> index = new ArrayList<>();
        index.add(position1);
        index.add(position2);

        return index;
    }

    public void showHand() {
        //TODO: Replace player with JSONConverter
        ArrayList<CardPointer> cardPointers = player.getHand().getSlot();
        System.out.println(player.getNickname() + "have in hand: ");
        for (CardPointer cardPointer : cardPointers) {
            System.out.println("[" + cardPointer.x + ", " + cardPointer.y + "] - "
                    + cardPointer.item);
        }
    }

    public int askColumn() {
        System.out.println("In which column would you like to insert the cards?");
        boolean columnConfirm = false;
        int column = 0;
        while (!columnConfirm) {
            do {
                try {
                    System.out.print("Column (0 to " + SHELF_COLUMN + "): ");
                    column = Integer.parseInt(readLine());
                    if (column < 0 || column > SHELF_COLUMN) {
                        System.out.println("Invalid number! Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please try again.");
                }
            } while (column < 0 || column > SHELF_COLUMN);
            System.out.println("You have chosen Column " + column);
            System.out.println("Confirm your choice:");
            System.out.println("1. Confirm.");
            System.out.println("0. Re-select.");

            columnConfirm = Boolean.parseBoolean(readLine());
        }
        return column;
    }

    @Override
    public void askMessage() {
        for (int i = 0; i < chatHistory.size(); i++) {
            System.out.println(chatHistory);
        }
        String message = readLine();
        chatHistory.add(player.getNickname() + "> " + message);
    }

    @Override
    public void askEndGameToken() {

    }

    @Override
    public void help() throws ServerNotActiveException, RemoteException {

    }

    @Override
    public void showTimer() {

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
                    askDeselection();
                    break;
                case 3:
                    askInsertion();
                    break;
                case 4:
                    showPersonalGoal();
                    break;
                case 5:
                    showCommonGoals();
                    break;
                case 6:
                    //TODO: Replace player with JSONConverter
                    showShelf(player.getShelf());
                    break;
                case 7:
                    showBoard();
                    break;
                case 8:
                    showPlayersStats();
                    break;
                case 9:
                    help();
                    break;
                case 10:
                    askMessage();
                    break;
                case 11:
                    askEndGameToken();
                    break;
                case 12:
                    showTimer();
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
