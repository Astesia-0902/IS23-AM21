package org.am21.client.view.cli;

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
    String username;
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

        System.out.println("Enter the server address: ["+ defaultAddress + "]");
        String address = readLine();

        if (address.equals("")){
            serverInfo.put("address", defaultAddress);
        }else {
            serverInfo.put("address", address);
        }

        System.out.println("Enter the server Port: ["+ defaultPort + "]");
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
//        ClientGameController.IClientInputHandler = iClientInputHandler;
        iClientInputHandler.registerCallBack(clientCallBack);
        System.out.println("Successfully joined at " + serverInfo.get("address")
                + ":" + serverInfo.get("port"));
    }

    //    public void clearCli(){
//        System.out.println("\033[H\033[2J");
//        System.out.flush();
//    }

    @Override
    public void askLogin() throws ServerNotActiveException, RemoteException {
        System.out.println("Enter the username: ");
        String username = readLine();
        boolean usernameAccepted;

        do {
            usernameAccepted = iClientInputHandler.logIn(username);

            if(usernameAccepted){
                this.username=username;
            } else {
                username = readLine();
            }
        } while (!usernameAccepted);
    }

    @Override
    public void askMenuAction() throws ServerNotActiveException, RemoteException {
        System.out.println("""
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
        System.out.println("Room generation in  progress...");
        int playerNumber = askMaxSeats();
        //TODO: fix the null point
        iClientInputHandler.createMatch(playerNumber);

    }

    @Override
    public int askMaxSeats() throws RemoteException {
        int playerNumber = 0;
        do {
            try {
                System.out.println("Please select the number of players [2 to 4]: ");
                playerNumber = Integer.parseInt(readLine());
                if (playerNumber < 2 || playerNumber > 4){
                    System.out.println("Invalid number! Please try again.");
                }
            } catch (NumberFormatException e){
                System.out.println("Invalid input! Please try again.");
            }
        } while (playerNumber < 2 || playerNumber > 4);
        return playerNumber;
    }

    @Override
    public void askJoinMatch() throws ServerNotActiveException, RemoteException {
        int matchID;

        try {
            System.out.println("Please enter the room number: ");
            matchID = Integer.parseInt(readLine());
            if(iClientInputHandler.joinGame(matchID)){
                askWaitingAction();
            }else{
                askMenuAction();
            }
        } catch (NumberFormatException e){
            System.out.println("Invalid input! Please try again.");
        }


    }

    /**
     * Showcase the Commands available during Waiting Players
     */
    private void askWaitingAction() {



    }

    @Override
    public void askLeaveMatch() throws RemoteException {
        //TODO: fixe the null point
        iClientInputHandler.leaveMatch();
        System.out.println("See you soon. Bye.");
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
        System.out.println(player + "'s PersonalGoal:");
        switch (personalGoal){
            case 1:
                System.out.println("""
                        [_Plants_][______._][_Frames_][______._][______._]
                        [______._][______._][______._][______._][__Cats__]
                        [______._][______._][______._][_Books__][______._]
                        [______._][_Games__][______._][______._][______._]
                        [______._][______._][______._][______._][______._]
                        [______._][______._][Trophies][______._][______._]""");
                break;
            case 2:
                System.out.println("""
                        [______._][______._][______._][______._][______._]
                        [______._][_Plants_][______._][______._][______._]
                        [__Cats__][______._][_Games__][______._][______._]
                        [______._][______._][______._][______._][_Books__]
                        [______._][______._][______._][Trophies][______._]
                        [______._][______._][______._][______._][_Frames_]""");
                break;
            case 3:
                System.out.println("""
                        [______._][______._][______._][______._][______._]
                        [_Frames_][______._][______._][_Games__][______._]
                        [______._][______._][_Plants_][______._][______._]
                        [______._][__Cats__][______._][______._][Trophies]
                        [______._][______._][______._][______._][______._]
                        [_Books__][______._][______._][______._][______._]""");
                break;
            case 4:
                System.out.println("""
                        [______._][______._][______._][______._][_Games__]
                        [______._][______._][______._][______._][______._]
                        [Trophies][______._][_Frames_][______._][______._]
                        [______._][______._][______._][_Plants_][______._]
                        [______._][_Books__][__Cats__][______._][______._]
                        [______._][______._][______._][______._][______._]""");
                break;
            case 5:
                System.out.println("""
                        [______._][______._][______._][______._][______._]
                        [______._][Trophies][______._][______._][______._]
                        [______._][______._][______._][______._][______._]
                        [______._][_Frames_][_Books__][______._][______._]
                        [______._][______._][______._][______._][_Plants_]
                        [_Games__][______._][______._][__Cats__][______._]""");
                break;
            case 6:
                System.out.println("""
                        [______._][______._][Trophies][______._][__Cats__]
                        [______._][______._][______._][______._][______._]
                        [______._][______._][______._][_Books__][______._]
                        [______._][______._][______._][______._][______._]
                        [______._][_Games__][______._][_Frames_][______._]
                        [_Plants_][______._][______._][______._][______._]""");
                break;
            case 7:
                System.out.println("""
                        [__Cats__][______._][______._][______._][______._]
                        [______._][______._][______._][_Frames_][______._]
                        [______._][_Plants_][______._][______._][______._]
                        [Trophies][______._][______._][______._][______._]
                        [______._][______._][______._][______._][_Games__]
                        [______._][______._][_Books__][______._][______._]""");
                break;
            case 8:
                System.out.println("""
                        [______._][______._][______._][______._][_Frames_]
                        [______._][__Cats__][______._][______._][______._]
                        [______._][______._][Trophies][______._][______._]
                        [_Plants_][______._][______._][______._][______._]
                        [______._][______._][______._][_Books__][______._]
                        [______._][______._][______._][_Games__][______._]""");
                break;
            case 9:
                System.out.println("""
                        [______._][______._][_Games__][______._][______._]
                        [______._][______._][______._][______._][______._]
                        [______._][______._][__Cats__][______._][______._]
                        [______._][______._][______._][______._][_Books__]
                        [______._][Trophies][______._][______._][_Plants_]
                        [_Frames_][______._][______._][______._][______._]""");
                break;
            case 10:
                System.out.println("""
                        [______._][______._][______._][______._][Trophies]
                        [______._][_Games__][______._][______._][______._]
                        [_Books__][______._][______._][______._][______._]
                        [______._][______._][______._][__Cats__][______._]
                        [______._][_Frames_][______._][______._][______._]
                        [______._][______._][______._][_Plants_][______._]""");
                break;
            case 11:
                System.out.println("""
                        [______._][______._][_Plants_][______._][______._]
                        [______._][_Books__][______._][______._][______._]
                        [_Games__][______._][______._][______._][______._]
                        [______._][______._][_Frames_][______._][______._]
                        [______._][______._][______._][______._][__Cats__]
                        [______._][______._][______._][Trophies][______._]""");
                break;
            case 12:
                System.out.println("""
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
        System.out.println(player + "'s Shelf:");
        for (String[][] userShelf : JSONConverter.shelf) {
            for (int i = 0; i < SHELF_ROW; i++) {
                for (int j = 0; j < SHELF_COLUMN; j++) {
                    String item = userShelf[i][j] == null? "[______._] " : userShelf[i][j];
                    System.out.println("[" + item + "] ");
                }
                System.out.println("\n");
            }
        }
    }

    @Override
    public void showBoard() throws RemoteException {
        String[][] board = JSONConverter.virtualBoard;
        System.out.println("Board:");
        for(int i = 0; i < BOARD_ROW; i++){
            for(int j = 0; j < BOARD_COLUMN; j++){
                String item = board[i][j] == null? "[______._] " : board[i][j];
                System.out.println("[" + item + "] ");
            }
            System.out.println("\n");
        }
    }

    @Override
    public void showPlayersStats() throws RemoteException {
        List<String> playerList = JSONConverter.players;
        List<Integer> scoreList = JSONConverter.scores;
        for (int i = 0; i < playerList.size(); i++) {
            System.out.println(playerList.get(i) + "'s stats: ");
            System.out.println(playerList.get(i) + "'s score: " + scoreList.get(i));
            showShelf();
        }
    }

    @Override
    public void askSelection() throws ServerNotActiveException, RemoteException {
        //TODO: Json.GamePhase dependence
        if (!insertPhase) {

                showBoard();
                System.out.println("Select a cell on the board");
                boolean selectionConfirm;
                do {
                    List<Integer> coordinates = askCoordinates();
                    int row = coordinates.get(0);
                    int column = coordinates.get(1);

                    if (iClientInputHandler.selectCell(row, column)) {
                        //System.out.println("Selection Successful!");
                        System.out.print("Item selected: ");
                        showItemInCell(row,column);
                    }
                    System.out.println("""
                            Do you want to continue with selection?
                            1. Yes.
                            0. No.""");
                    selectionConfirm = Boolean.parseBoolean(readLine());
                } while (selectionConfirm);
        }
    }

    public List<Integer> askCoordinates() throws RemoteException {
        System.out.println("Enter the coordinates you wish to select [row, column].");
        boolean selectConfirm;
        int selectRow = 0, selectColumn = 0;

        do {
            do {
                try {
                    System.out.println("row (0 to " + BOARD_ROW + "): ");
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
                    System.out.println("column (0 to " + BOARD_COLUMN + "): ");
                    selectColumn = Integer.parseInt(readLine());
                    if (selectColumn < 0 || selectColumn > BOARD_COLUMN) {
                        System.out.println("Invalid number! Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please try again.");
                }
            } while (selectColumn < 0 || selectColumn > BOARD_COLUMN);

            System.out.println("The coordinates you have chosen are: [" + selectRow + ", " +
                    selectColumn + "] - " + showItemInCell(selectRow, selectColumn));
            System.out.println("""
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
        //TODO: Json.Gamephase
        if (!insertPhase) {
            showHand();
            System.out.println("""
                    Do you want to cancel all selected cards?
                    1. Yes.
                    0. No.""");

            boolean deselectConfirm = Boolean.parseBoolean(readLine());
            if (deselectConfirm) {
                iClientInputHandler.deselectCards();

            }
        } else {
            System.out.println("The conditions to use this command are not respected. Try again");
        }
    }

    @Override
    public void askInsertion() throws ServerNotActiveException, RemoteException {
        insertPhase = true;
        if (showHand()) {
            boolean sort;
            do {
                System.out.println("""
                        Do you want to change insertion order?
                        1. Yes.
                        0. No.""");
                sort = Boolean.parseBoolean(readLine());
                if (sort) {
                    askSort();
                } else {
                    int column = askColumn();
                    iClientInputHandler.insertInColumn(column);
                    System.out.println("Cards are correctly inserted in the shelves!");
                }
            } while (sort);
        } else {
            System.out.println("You can’t insert cards if you did not select any cards!");
            insertPhase = false;
        }
    }

    public void askSort() throws ServerNotActiveException, RemoteException {
        List<Integer> itemSwapped = askIndex();
        if (iClientInputHandler.sortHand(itemSwapped.get(0), itemSwapped.get(1))) {
            System.out.println("Card order changed.");
        }
    }

    public List<Integer> askIndex() throws RemoteException {
        System.out.println("Which cards should switch?");
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
        System.out.println(player +" have in hand:");
        if (JSONConverter.currentPlayerHand.isEmpty()){
            return false;
        }
        for (int i = 0; i < JSONConverter.currentPlayerHand.size(); i++) {
            System.out.println(JSONConverter.currentPlayerHand.get(i));
        }
        return true;
    }

    public int askColumn() throws RemoteException {
        System.out.println("In which column would you like to insert the cards?");
        boolean columnConfirm;
        int column = 0;
         do {
            do {
                try {
                    System.out.println("Column (0 to " + SHELF_COLUMN + "): ");
                    column = Integer.parseInt(readLine());
                    if (column < 0 || column > SHELF_COLUMN) {
                        System.out.println("Invalid number! Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please try again.");
                }
            } while (column < 0 || column > SHELF_COLUMN);
             System.out.println("""
            You have chosen Column\040""" + column + """
            Confirm your choice:
            1. Confirm.
            0. Re-select.""");

            columnConfirm = Boolean.parseBoolean(readLine());
        } while (!columnConfirm);
        return column;
    }

    public void handleChatMessage() throws RemoteException {
        String message = readLine();
        //TODO:
        iClientInputHandler.sendChatMessage(message);

    }

    @Override
    public void showEndGameToken() {

    }

    @Override
    public void help() throws ServerNotActiveException, RemoteException {
        System.out.println("You can do the following commands in this phase:");
        if (!insertPhase){
            showCommandMenu();
        } else {
            System.out.println("""
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

        initPlayer(username);
        showBoard();
        showCommonGoals();
        showPersonalGoal();
        showCurrentPlayer();
    }

    private void showCommandMenu() throws RemoteException {
        System.out.println("""
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
        System.out.println("What's your next move?");
        showCommandMenu();
        System.out.println("Enter the action you wish to select:");

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
                    handleChatMessage();
                    break;
                case "end":
                    showEndGameToken();
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

    @Override
    public void askShowObject() throws RemoteException {

    }

    @Override
    public void showOnlinePlayer() throws RemoteException {
        iClientInputHandler.printOnlinePlayers();
    }
    public void printer(String message){
        System.out.println(message);
    }

    public void showGoalDescription(String CommonGoalCard) throws RemoteException {
        switch (CommonGoalCard){
            case "CommonGoal2Lines":
                System.out.println("CommonGoal2Lines: Two columns each formed by 6 different types of tiles.");
                break;
            case "CommonGoal2Columns":
                System.out.println("CommonGoal2Columns: Two lines each formed by 5 different types of tiles. " +
                        "One line can show the same or a different combination of the other line.");
                break;
            case "CommonGoal3Column":
                System.out.println("CommonGoal3Column: Three columns each formed by 6 tiles of maximum three " +
                        "different types. One column can show the same or a different combination of another column.");
                break;
            case "CommonGoal4Lines":
                System.out.println("CommonGoal4Lines: Four lines each formed by 5 tiles of maximum three " +
                        "different types. One line can show the same or a different combination of another line.");
                break;
            case "CommonGoal8Tiles":
                System.out.println("CommonGoal8Tiles: Eight tiles of the same type. " +
                        "There’s no restriction about the position of these tiles.");
                break;
            case "CommonGoalCorner":
                System.out.println("CommonGoalCorner: Four tiles of the same type in the four corners of " +
                        "the bookshelf.");
                break;
            case "CommonGoalDiagonal":
                System.out.println("CommonGoalDiagonal: Five tiles of the same type forming a diagonal.");
                break;
            case "CommonGoalSquare":
                System.out.println("CommonGoalSquare: Two groups each containing 4 tiles of the same type in a 2x2 " +
                        "square. The tiles of one square can be different from those of the other square.");
            case "CommonGoalStairs":
                System.out.println("CommonGoalStairs: Five columns of increasing or decreasing height. " +
                        "Starting from the first column on the left or on the right, " +
                        "each next column must be made of exactly one more tile. Tiles can be of any type.");
                break;
            case "CommonGoal4Group":
                System.out.println("CommonGoal4Group: Four groups each containing at least 4 tiles of the same type " +
                        "(not necessarily in the depicted shape). The tiles of one group can be different " +
                        "from those of another group.");
                break;
            case "CommonGoal6Group":
                System.out.println("CommonGoal6Group: Six groups each containing at least 2 tiles of the same type " +
                        "(not necessarily in the depicted shape). The tiles of one group can be different " +
                        "from those of another group.");
                break;
            case "CommonGoalXShape":
                System.out.println("CommonGoalXShape: Five tiles of the same type forming an X.");
                break;
        }
    }
}
