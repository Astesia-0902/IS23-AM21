package org.am21.client.view.cli;

import org.am21.client.ClientGameController;
import org.am21.client.view.JSONConverter;
import org.am21.client.view.View;
import org.am21.networkRMI.ClientCallBack;
import org.am21.networkRMI.IClientInput;

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
    private IClientInput iClientInputHandler;
    private ClientCallBack clientCallBack;
    private String player;
    private int playerIndex;
    private int personalGoal;
    private boolean insertPhase;

    private static final int SHELF_ROW = 6;
    private static final int SHELF_COLUMN = 5;
    private static final int BOARD_ROW = 6;
    private static final int BOARD_COLUMN = 5;
    private static final int HAND_SIZE = 3;

    public Cli() throws RemoteException {
        this.clientCallBack = new ClientCallBack();
        //TODO: keep it separate from constructor to avoid test destruction :)
        this.clientCallBack.cli = this;
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

    public void init() throws ExecutionException, MalformedURLException, NotBoundException, RemoteException {
        System.out.println("Welcome to MyShelfie Board Game!");
        askServerInfo();
    }

    public void initPlayer(String username) {
        //TODO: Replace player with JSONConverter
        playerIndex = JSONConverter.getPlayerIndex(username);
        player = JSONConverter.players.get(playerIndex);
        personalGoal = JSONConverter.personalGoal;
    }

    public void askServerInfo() throws ExecutionException, MalformedURLException, NotBoundException, RemoteException {
        Map<String, String> serverInfo = new HashMap<>();
        String defaultAddress = "localhost";
        String defaultPort = "8807";

        clientCallBack.sendMessageFromServer("Enter the server address: ["+ defaultAddress + "]");
        String address = readLine();

        if (address.equals("")){
            serverInfo.put("address", defaultAddress);
        }else {
            serverInfo.put("address", address);
        }

        clientCallBack.sendMessageFromServer("Enter the server Port: ["+ defaultPort + "]");
        String port = readLine();

        if (port.equals("")){
            serverInfo.put("port", defaultPort);
        }else {
            serverInfo.put("port", port);
        }
        System.out.println(address);
        System.out.println(port);
        System.out.println("rmi://" + serverInfo.get("address") + ":"
                + serverInfo.get("port") + "/ClientInputHandler");

        iClientInputHandler = (IClientInput) Naming.lookup("rmi://" + serverInfo.get("address") + ":"
                + serverInfo.get("port") + "/ClientInputHandler");
        ClientGameController.IClientInputHandler = iClientInputHandler;
        iClientInputHandler.registerCallBack(clientCallBack);
        clientCallBack.sendMessageFromServer("Successfully joined at " + serverInfo.get("address")
                + ":" + serverInfo.get("port"));
    }

    public void showGoalDescription(String CommonGoalCard) throws RemoteException {
        switch (CommonGoalCard){
            case "CommonGoal2Lines":
                clientCallBack.sendMessageFromServer("CommonGoal2Lines: Two columns each formed by 6 different types of tiles.");
                break;
            case "CommonGoal2Columns":
                clientCallBack.sendMessageFromServer("CommonGoal2Columns: Two lines each formed by 5 different types of tiles. " +
                        "One line can show the same or a different combination of the other line.");
                break;
            case "CommonGoal3Column":
                clientCallBack.sendMessageFromServer("CommonGoal3Column: Three columns each formed by 6 tiles of maximum three " +
                        "different types. One column can show the same or a different combination of another column.");
                break;
            case "CommonGoal4Lines":
                clientCallBack.sendMessageFromServer("CommonGoal4Lines: Four lines each formed by 5 tiles of maximum three " +
                        "different types. One line can show the same or a different combination of another line.");
                break;
            case "CommonGoal8Tiles":
                clientCallBack.sendMessageFromServer("CommonGoal8Tiles: Eight tiles of the same type. " +
                        "There’s no restriction about the position of these tiles.");
                break;
            case "CommonGoalCorner":
                clientCallBack.sendMessageFromServer("CommonGoalCorner: Four tiles of the same type in the four corners of " +
                        "the bookshelf.");
                break;
            case "CommonGoalDiagonal":
                clientCallBack.sendMessageFromServer("CommonGoalDiagonal: Five tiles of the same type forming a diagonal.");
                break;
            case "CommonGoalSquare":
                clientCallBack.sendMessageFromServer("CommonGoalSquare: Two groups each containing 4 tiles of the same type in a 2x2 " +
                        "square. The tiles of one square can be different from those of the other square.");
            case "CommonGoalStairs":
                clientCallBack.sendMessageFromServer("CommonGoalStairs: Five columns of increasing or decreasing height. " +
                        "Starting from the first column on the left or on the right, " +
                        "each next column must be made of exactly one more tile. Tiles can be of any type.");
                break;
            case "CommonGoal4Group":
                clientCallBack.sendMessageFromServer("CommonGoal4Group: Four groups each containing at least 4 tiles of the same type " +
                        "(not necessarily in the depicted shape). The tiles of one group can be different " +
                        "from those of another group.");
                break;
            case "CommonGoal6Group":
                clientCallBack.sendMessageFromServer("CommonGoal6Group: Six groups each containing at least 2 tiles of the same type " +
                        "(not necessarily in the depicted shape). The tiles of one group can be different " +
                        "from those of another group.");
                break;
            case "CommonGoalXShape":
                clientCallBack.sendMessageFromServer("CommonGoalXShape: Five tiles of the same type forming an X.");
                break;
        }
    }

    //    public void clearCli(){
//        System.out.println("\033[H\033[2J");
//        System.out.flush();
//    }

    @Override
    public void askLogin() throws ServerNotActiveException, RemoteException {
        clientCallBack.sendMessageFromServer("Enter the username: ");
        String username = readLine();
        boolean usernameAccepted;

        do {
            usernameAccepted = iClientInputHandler.logIn(username);

            if(usernameAccepted){
                clientCallBack.sendMessageFromServer("Hi, " + username + " is login in the game.");
                initPlayer(username);
            } else {
                clientCallBack.sendMessageFromServer("Username already exists, please re-enter.");
                username = readLine();
            }
        } while (!usernameAccepted);
    }

    @Override
    public void askAction() throws ServerNotActiveException, RemoteException {
        clientCallBack.sendMessageFromServer("""
                Game Option:
                1. Create a new match.
                2. Join a match.
                3. Leave the game.
                0. Cancel option.
                
                Enter the action you wish to select:""");

        int option = Integer.parseInt(readLine());
        while (option != 0) {
            switch (option) {
                case 1:
                    askCreateMatch();
                    break;
                case 2:
                    askJoinMatch();
                    break;
                case 3:
                    askLeaveMatch();
                    break;
                default:
                    option = Integer.parseInt(readLine());
                    break;
            }
        }
        //System.exit(0);
    }

    @Override
    public void askCreateMatch() throws ServerNotActiveException, RemoteException {
        clientCallBack.sendMessageFromServer("Room generation in  progress...");
        int playerNumber = askMaxSeats();
        int matchID;
        //TODO: fix the null point
        iClientInputHandler.createMatch(playerNumber);

        clientCallBack.sendMessageFromServer("Successfully created a game for " + playerNumber + " persons!");
        Random random = new Random();
        matchID = random.nextInt(100);
        clientCallBack.sendMessageFromServer("The room number is " + matchID + "\nWaiting for players...");
    }

    @Override
    public int askMaxSeats() throws RemoteException {
        int playerNumber = 0;
        do {
            try {
                clientCallBack.sendMessageFromServer("Please select the number of players [2 to 4]: ");
                playerNumber = Integer.parseInt(readLine());
                if (playerNumber != 2 && playerNumber != 3 && playerNumber != 4){
                    clientCallBack.sendMessageFromServer("Invalid number! Please try again.");
                }
            } catch (NumberFormatException e){
                clientCallBack.sendMessageFromServer("Invalid input! Please try again.");
            }
        } while (playerNumber != 2 && playerNumber != 3 && playerNumber != 4);
        clientCallBack.sendMessageFromServer("Ok");
        return playerNumber;
    }

    @Override
    public void askJoinMatch() throws ServerNotActiveException, RemoteException {
        boolean replyAction = false;
        int matchID;
        do {
            try {
                clientCallBack.sendMessageFromServer("Please enter the room number: ");
                matchID = Integer.parseInt(readLine());
                replyAction = iClientInputHandler.joinGame(matchID);

                if (replyAction){
                    clientCallBack.sendMessageFromServer("Joining the match: " + matchID);
                }
            } catch (NumberFormatException e){
                clientCallBack.sendMessageFromServer("Invalid input! Please try again.");
            }
        } while (replyAction);

    }

    @Override
    public void askLeaveMatch() throws RemoteException {
        //TODO: fixe the null point
        iClientInputHandler.leaveMatch();
        clientCallBack.sendMessageFromServer("See you soon. Bye.");
    }

    @Override
    public void askExitGame() throws RemoteException {

    }

    @Override
    public void showCommonGoals() throws RemoteException {
        for (int i = 0; i < JSONConverter.commonGoal.size(); i++) {
            showGoalDescription(JSONConverter.commonGoal.get(i));
        }

//        System.out.println("You received: " + player.getPlayerScore() + " points.");
    }

    @Override
    public void showPersonalGoal() throws RemoteException {
        clientCallBack.sendMessageFromServer(player + "'s PersonalGoal:");
        switch (personalGoal){
            case 1:
                clientCallBack.sendMessageFromServer("""
                        [_Plants_][______._][_Frames_][______._][______._]
                        [______._][______._][______._][______._][__Cats__]
                        [______._][______._][______._][_Books__][______._]
                        [______._][_Games__][______._][______._][______._]
                        [______._][______._][______._][______._][______._]
                        [______._][______._][Trophies][______._][______._]""");
                break;
            case 2:
                clientCallBack.sendMessageFromServer("""
                        [______._][______._][______._][______._][______._]
                        [______._][_Plants_][______._][______._][______._]
                        [__Cats__][______._][_Games__][______._][______._]
                        [______._][______._][______._][______._][_Books__]
                        [______._][______._][______._][Trophies][______._]
                        [______._][______._][______._][______._][_Frames_]""");
                break;
            case 3:
                clientCallBack.sendMessageFromServer("""
                        [______._][______._][______._][______._][______._]
                        [_Frames_][______._][______._][_Games__][______._]
                        [______._][______._][_Plants_][______._][______._]
                        [______._][__Cats__][______._][______._][Trophies]
                        [______._][______._][______._][______._][______._]
                        [_Books__][______._][______._][______._][______._]""");
                break;
            case 4:
                clientCallBack.sendMessageFromServer("""
                        [______._][______._][______._][______._][_Games__]
                        [______._][______._][______._][______._][______._]
                        [Trophies][______._][_Frames_][______._][______._]
                        [______._][______._][______._][_Plants_][______._]
                        [______._][_Books__][__Cats__][______._][______._]
                        [______._][______._][______._][______._][______._]""");
                break;
            case 5:
                clientCallBack.sendMessageFromServer("""
                        [______._][______._][______._][______._][______._]
                        [______._][Trophies][______._][______._][______._]
                        [______._][______._][______._][______._][______._]
                        [______._][_Frames_][_Books__][______._][______._]
                        [______._][______._][______._][______._][_Plants_]
                        [_Games__][______._][______._][__Cats__][______._]""");
                break;
            case 6:
                clientCallBack.sendMessageFromServer("""
                        [______._][______._][Trophies][______._][__Cats__]
                        [______._][______._][______._][______._][______._]
                        [______._][______._][______._][_Books__][______._]
                        [______._][______._][______._][______._][______._]
                        [______._][_Games__][______._][_Frames_][______._]
                        [_Plants_][______._][______._][______._][______._]""");
                break;
            case 7:
                clientCallBack.sendMessageFromServer("""
                        [__Cats__][______._][______._][______._][______._]
                        [______._][______._][______._][_Frames_][______._]
                        [______._][_Plants_][______._][______._][______._]
                        [Trophies][______._][______._][______._][______._]
                        [______._][______._][______._][______._][_Games__]
                        [______._][______._][_Books__][______._][______._]""");
                break;
            case 8:
                clientCallBack.sendMessageFromServer("""
                        [______._][______._][______._][______._][_Frames_]
                        [______._][__Cats__][______._][______._][______._]
                        [______._][______._][Trophies][______._][______._]
                        [_Plants_][______._][______._][______._][______._]
                        [______._][______._][______._][_Books__][______._]
                        [______._][______._][______._][_Games__][______._]""");
                break;
            case 9:
                clientCallBack.sendMessageFromServer("""
                        [______._][______._][_Games__][______._][______._]
                        [______._][______._][______._][______._][______._]
                        [______._][______._][__Cats__][______._][______._]
                        [______._][______._][______._][______._][_Books__]
                        [______._][Trophies][______._][______._][_Plants_]
                        [_Frames_][______._][______._][______._][______._]""");
                break;
            case 10:
                clientCallBack.sendMessageFromServer("""
                        [______._][______._][______._][______._][Trophies]
                        [______._][_Games__][______._][______._][______._]
                        [_Books__][______._][______._][______._][______._]
                        [______._][______._][______._][__Cats__][______._]
                        [______._][_Frames_][______._][______._][______._]
                        [______._][______._][______._][_Plants_][______._]""");
                break;
            case 11:
                clientCallBack.sendMessageFromServer("""
                        [______._][______._][_Plants_][______._][______._]
                        [______._][_Books__][______._][______._][______._]
                        [_Games__][______._][______._][______._][______._]
                        [______._][______._][_Frames_][______._][______._]
                        [______._][______._][______._][______._][__Cats__]
                        [______._][______._][______._][Trophies][______._]""");
                break;
            case 12:
                clientCallBack.sendMessageFromServer("""
                        [______._][______._][_Books__][______._][______._]
                        [______._][_Plants_][______._][______._][______._]
                        [______._][______._][_Frames_][______._][______._]
                        [______._][______._][______._][Trophies][______._]
                        [______._][______._][______._][______._][_Games__]
                        [__Cats__][______._][______._][______._][______._]""");
                break;
        }
    }

    @Override
    public void showCurrentPlayer() {
        if(JSONConverter.currentPlayer.equals(player)){
            System.out.println("Hey "+player+"! It's your turn");
        }else{
            System.out.println("It's "+ JSONConverter.currentPlayer+"'s turn. ");
        }
    }

    @Override
    public void showShelf() throws RemoteException {
        clientCallBack.sendMessageFromServer(player + "'s Shelf:");
        for (String[][] userShelf : JSONConverter.shelf) {
            for (int i = 0; i < SHELF_ROW; i++) {
                for (int j = 0; j < SHELF_COLUMN; j++) {
                    String item = userShelf[i][j] == null? "[______._] " : userShelf[i][j];
                    clientCallBack.sendMessageFromServer("[" + item + "] ");
                }
                clientCallBack.sendMessageFromServer("\n");
            }
        }
    }

    @Override
    public void showBoard() throws RemoteException {
        String[][] board = JSONConverter.virtualBoard;
        clientCallBack.sendMessageFromServer("Board:");
        for(int i = 0; i < BOARD_ROW; i++){
            for(int j = 0; j < BOARD_COLUMN; j++){
                String item = board[i][j] == null? "[______._] " : board[i][j];
                clientCallBack.sendMessageFromServer("[" + item + "] ");
            }
            clientCallBack.sendMessageFromServer("\n");
        }
    }

    @Override
    public void showPlayersStats() throws RemoteException {
        List<String> playerList = JSONConverter.players;
        List<Integer> scoreList = JSONConverter.scores;
        for (int i = 0; i < playerList.size(); i++) {
            clientCallBack.sendMessageFromServer(playerList.get(i) + "'s stats: ");
            clientCallBack.sendMessageFromServer(playerList.get(i) + "'s score: " + scoreList.get(i));
            showShelf();
        }
    }

    @Override
    public void askSelection() throws ServerNotActiveException, RemoteException {
        if (!insertPhase) {
            //TODO: Questo controllo andrebbe fatto solo dal server
            if (JSONConverter.currentPlayerHand.size() < HAND_SIZE) {
                showBoard();
                boolean selectionConfirm;
                do {
                    List<Integer> coordinates = askCoordinates();
                    int row = coordinates.get(0);
                    int column = coordinates.get(1);

                    if (iClientInputHandler.selectCell(row, column)) {
                        clientCallBack.sendMessageFromServer("Selection Successful!");
                    }
                    clientCallBack.sendChatMessage("""
                            Do you want to continue with selection?
                            1. Yes.
                            0. No.""");
                    selectionConfirm = Boolean.parseBoolean(readLine());
                } while (selectionConfirm);
            } else {
                clientCallBack.sendMessageFromServer("Hand full!");
            }
        } else {
            clientCallBack.sendChatMessage("You cannot select the cards anymore.");
        }
    }

    public List<Integer> askCoordinates() throws RemoteException {
        clientCallBack.sendMessageFromServer("Enter the coordinates you wish to select [row, column].");
        boolean selectConfirm;
        int selectRow = 0, selectColumn = 0;

        do {
            do {
                try {
                    clientCallBack.sendMessageFromServer("row (0 to " + BOARD_ROW + "): ");
                    selectRow = Integer.parseInt(readLine());
                    if (selectRow < 0 || selectRow > BOARD_ROW) {
                        clientCallBack.sendMessageFromServer("Invalid number! Please try again.");
                    }
                } catch (NumberFormatException e) {
                    clientCallBack.sendMessageFromServer("Invalid input! Please try again.");
                }
            } while (selectRow < 0 || selectRow > BOARD_ROW);

            do {
                try {
                    clientCallBack.sendMessageFromServer("column (0 to " + BOARD_COLUMN + "): ");
                    selectColumn = Integer.parseInt(readLine());
                    if (selectColumn < 0 || selectColumn > BOARD_COLUMN) {
                        clientCallBack.sendMessageFromServer("Invalid number! Please try again.");
                    }
                } catch (NumberFormatException e) {
                    clientCallBack.sendMessageFromServer("Invalid input! Please try again.");
                }
            } while (selectColumn < 0 || selectColumn > BOARD_COLUMN);

            clientCallBack.sendMessageFromServer("The coordinates you have chosen are: [" + selectRow + ", " +
                    selectColumn + "] - " + showItemInCell(selectRow, selectColumn));
            clientCallBack.sendMessageFromServer("""
            Confirm your choice:
            1. Confirm.
            0. Re-select.""");

            selectConfirm = Boolean.parseBoolean(readLine());
        } while(!selectConfirm);
        List<Integer> coordinates = new ArrayList<>();
        coordinates.add(selectRow);
        coordinates.add(selectColumn);
        return coordinates;
    }

    public String showItemInCell(int row, int column) throws RemoteException {
        return JSONConverter.virtualBoard[row][column];
    }

    @Override
    public void askDeselection() throws ServerNotActiveException, RemoteException {
        if (!insertPhase) {
            showHand();
            clientCallBack.sendMessageFromServer("""
                    Do you want to cancel all selected cards?
                    1. Yes.
                    0. No.""");

            boolean deselectConfirm = Boolean.parseBoolean(readLine());
            if (deselectConfirm) {
                if (iClientInputHandler.unselectCards()) {
                    clientCallBack.sendMessageFromServer("Successfully removed all selected cards!");
                }
            }
        } else {
            clientCallBack.sendChatMessage("You cannot deselect the cards anymore.");
        }
    }

    @Override
    public void askInsertion() throws ServerNotActiveException, RemoteException {
        insertPhase = true;
        if (showHand()) {
            boolean sort;
            do {
                clientCallBack.sendMessageFromServer("""
                        Do you want to change insertion order?
                        1. Yes.
                        0. No.""");
                sort = Boolean.parseBoolean(readLine());
                if (sort) {
                    askSort();
                } else {
                    int column = askColumn();
                    iClientInputHandler.insertInColumn(column);
                    clientCallBack.sendMessageFromServer("Cards are correctly inserted in the shelves!");
                }
            } while (sort);
        } else {
            clientCallBack.sendMessageFromServer("You can’t insert cards if you did not select any cards!");
            insertPhase = false;
        }
    }

    public void askSort() throws ServerNotActiveException, RemoteException {
        List<Integer> itemSwapped = askIndex();
        if (iClientInputHandler.sortHand(itemSwapped.get(0), itemSwapped.get(1))) {
            clientCallBack.sendMessageFromServer("Card order changed.");
        }
    }

    public List<Integer> askIndex() throws RemoteException {
        clientCallBack.sendMessageFromServer("Which cards should switch?");
        boolean sortConfirm;
        int position1 = 1, position2 = 2;
        //TODO: Controllo che pos1 e pos2 siano diversi per evitare scambio della stessa posizione
        // (che si puo fare, ma vogliamo evitare di far perdere tempo al server)
        //TODO: Nel server la hand ha index-0 come prima posizione quindi è necessario manipolazione dei numeri
        do {
            do {
                try {
                    System.out.print("position1 (1 to " + JSONConverter.currentPlayerHand.size() + "): ");
                    position1 = Integer.parseInt(readLine());
                    if (position1 < 1 || position1 > JSONConverter.currentPlayerHand.size()) {
                        System.out.println("Invalid number! Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please try again.");
                }
            } while (position1 < 1 || position1 > JSONConverter.currentPlayerHand.size());

            do {
                try {
                    System.out.print("position2 (1 to " + JSONConverter.currentPlayerHand.size() + "): ");
                    position2 = Integer.parseInt(readLine());
                    if (position2 < 1 || position2 > JSONConverter.currentPlayerHand.size()) {
                        System.out.println("Invalid number! Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please try again.");
                }
            } while (position2 < 1 || position2 > JSONConverter.currentPlayerHand.size());

            System.out.println("You have chosen to swap " + JSONConverter.currentPlayerHand.get(position1) + " and " +
                    JSONConverter.currentPlayerHand.get(position2));
            System.out.println("Confirm your choice:");
            System.out.println("1. Confirm.");
            System.out.println("0. Re-select.");

            sortConfirm = Boolean.parseBoolean(readLine());
        } while(!sortConfirm);
        List<Integer> index = new ArrayList<>();
        index.add(position1);
        index.add(position2);

        return index;
    }

    public boolean showHand() throws RemoteException {
        clientCallBack.sendMessageFromServer(player +" have in hand:");
        if (JSONConverter.currentPlayerHand.isEmpty()){
            return false;
        }
        for (int i = 0; i < JSONConverter.currentPlayerHand.size(); i++) {
            clientCallBack.sendMessageFromServer(JSONConverter.currentPlayerHand.get(i));
        }
        return true;
    }

    public int askColumn() throws RemoteException {
        clientCallBack.sendMessageFromServer("In which column would you like to insert the cards?");
        boolean columnConfirm;
        int column = 0;
         do {
            do {
                try {
                    clientCallBack.sendMessageFromServer("Column (0 to " + SHELF_COLUMN + "): ");
                    column = Integer.parseInt(readLine());
                    if (column < 0 || column > SHELF_COLUMN) {
                        clientCallBack.sendMessageFromServer("Invalid number! Please try again.");
                    }
                } catch (NumberFormatException e) {
                    clientCallBack.sendMessageFromServer("Invalid input! Please try again.");
                }
            } while (column < 0 || column > SHELF_COLUMN);
             clientCallBack.sendMessageFromServer("""
            You have chosen Column\040""" + column + """
            Confirm your choice:
            1. Confirm.
            0. Re-select.""");

            columnConfirm = Boolean.parseBoolean(readLine());
        } while (!columnConfirm);
        return column;
    }

    @Override
    public void askMessage() throws RemoteException {
        String message = readLine();
        clientCallBack.sendChatMessage(message);

    }

    @Override
    public void askEndGameToken() {

    }

    @Override
    public void help() throws ServerNotActiveException, RemoteException {
        clientCallBack.sendMessageFromServer("You can do the following commands in this phase:");
        if (!insertPhase){
            showMenu();
        } else {
            clientCallBack.sendMessageFromServer("""
        insert- Insert in the shelves.
        pGoal- See Personal goal.
        cGoal- See Common goal.
        shelf- See Shelf.
        board- See Board.
        stats- See Players Stats.
        help- Help.
        msg- Send Message to General chat.
        end- Check EndGameToken.
        time- Show timer.
        quit- Quit Match.
        exit- Cancel option.
        """);
        }
    }

    @Override
    public void showTimer() {

    }

    /**
     * Called by CALLBACK
     * Displays the initial setup:
     * - Filled Board
     * - 2 commonGoals
     * - Player's personal goal
     * - and current player
     */
    @Override
    public void showMatchSetup() throws RemoteException {
        //TODO: Redo better
        showBoard();
        showCommonGoals();
        showPersonalGoal();
        showCurrentPlayer();
    }

    private void showMenu() throws RemoteException {
        clientCallBack.sendMessageFromServer("""
        select- Select an item on the board.
        deselect - Deselect the cards.
        insert- Insert in the shelves.
        pGoal- See Personal goal.
        cGoal- See Common goal.
        shelf- See Shelf.
        board- See Board.
        stats- See Players Stats.
        help- Help.
        msg- Send Message to General chat.
        end- Check EndGameToken.
        time- Show timer.
        leave- Leave Match.
        exit- Exit Game.
        """);
    }

    @Override
    public void askPlayerMove() throws RemoteException, ServerNotActiveException {
        clientCallBack.sendMessageFromServer("What's your next move?");
        showMenu();
        clientCallBack.sendMessageFromServer("Enter the action you wish to select:");

        String option = readLine();
        do {
            switch (option) {
                case "select":
                    askSelection();
                    break;
                case "deselect":
                    askDeselection();
                    break;
                case "insert":
                    askInsertion();
                    break;
                case "pGoal":
                    showPersonalGoal();
                    break;
                case "cGoal":
                    showCommonGoals();
                    break;
                case "shelf":
                    showShelf();
                    break;
                case "board":
                    showBoard();
                    break;
                case "stats":
                    showPlayersStats();
                    break;
                case "help":
                    help();
                    break;
                case "msg":
                    askMessage();
                    break;
                case "end":
                    askEndGameToken();
                    break;
                case "time":
                    showTimer();
                    break;
                case "leave":
                    askLeaveMatch();
                    break;
                case "exit":
                    askExitGame();
                    break;
                default:
                    option = readLine();
                    break;
            }
        } while (!option.equals("quit"));
    }
}
