package org.am21.client.view.TUI;

import org.am21.client.view.Storage;
import org.am21.client.view.View;
import org.am21.networkRMI.ClientCallBack;
import org.am21.networkRMI.IClientInput;
import org.am21.networkRMI.Lobby;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static org.am21.client.view.Storage.*;

public class Cli implements View {
    private String username;
    private int playerIndex;
    private Thread inputThread;
    private IClientInput iClientInputHandler;
    private final ClientCallBack clientCallBack;
    private String player;
    private final List<String> commonGoalList = new ArrayList<>();
    //If true askMenuAction
    private boolean GO_TO_MENU = true;
    //If true askPlayerMove, if false askWaitingAction
    private boolean GAME_ON = false;
    private boolean START = false;
    private boolean SEL_MODE = true;
    private boolean NOT_SEL_YET = true;
    private boolean BABY_PROTOCOL = true;
    //If true GoToEndRoom
    private boolean END = false;
    private int matchID;

    public Cli() throws RemoteException {
        this.clientCallBack = new ClientCallBack();
        //TODO: keep it separate from constructor to avoid test destruction :)
        this.clientCallBack.cli = this;
    }

    /**
     * Read a line from the standard input
     *
     * @return the string read from the input
     */
    public String readLine() {
        FutureTask<String> futureTask = new FutureTask<>(new InputReadTask());
        inputThread = new Thread(futureTask);
        inputThread.start();

        String input = null;
        try {
            input = futureTask.get();
        } catch (InterruptedException e) {
            futureTask.cancel(true);
            //Thread.currentThread().interrupt();
            inputThread.interrupt();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return input;
    }

    public void interruptThread() {
        Thread.currentThread().interrupt();
    }

    public void init() {
        System.out.println(Storage.MYSHELFIE4);
        askToContinue();
        try {
            askServerInfo();
            askLogin();
            askToContinue();
            askMenuAction();
        } catch (ServerNotActiveException | MalformedURLException | NotBoundException | RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void askToContinue() {
        System.out.print("Press 'Enter' to continue");
        readLine();
        System.out.println("-----------------------------------------------------------");
    }

    public void askAssistMode() {
        System.out.print("""
                -----------------------------------------------------------
                Would you like to activate or deactivate ASSIST MODE? (Default: Active)
                Tip: Keep Assist Mode active for an easier experience of the game
                > Press 'Enter' to activate it
                > Type 'off' to deactivate it
                -----------------------------------------------------------
                Enter the option you wish to select:\040""");
        String input = readLine();
        switch (input) {
            case "" -> {
                BABY_PROTOCOL = true;
                System.out.println(Color.CYAN + "ASSIST MODE ON!\n" + Color.RESET);
            }
            case "off" -> {
                BABY_PROTOCOL = false;
                System.out.println(Color.RED + "ASSIST MODE OFF!\n" + Color.RESET);
            }
            default -> askAssistMode();
        }
    }


    /**
     * Is this method redundant?
     * TODO
     *
     * @param username User's name
     */
    public void initPlayer(String username) {
        playerIndex = Storage.getPlayerIndex(username);
        player = Storage.players.get(playerIndex);

    }

    public void askServerInfo() throws MalformedURLException, NotBoundException, RemoteException {
        // First connection to the server --> Obtain the path for RMI
        Lobby lobby = (Lobby) Naming.lookup("rmi://localhost:1234/Welcome");
        String root;
        try {
            root = lobby.connect();
        } catch (AlreadyBoundException e) {
            throw new RuntimeException(e);
        }

        Map<String, String> serverInfo = new HashMap<>();
        String defaultAddress = "localhost";
        String defaultPort = "8807";
        boolean validInput;

        do {
            System.out.print("Enter the server address: [" + defaultAddress + "]");
            String address = readLine();

            if (address.equals("")) {
                serverInfo.put("address", defaultAddress);
                validInput = true;
            } else if (address.equals("localhost")) {
                serverInfo.put("address", address);
                validInput = true;
            } else {
                System.out.println(Color.RED + "Invalid address!" + Color.RESET);
                validInput = false;
            }
        } while (!validInput);

        do {
            System.out.print("Enter the server Port: [" + defaultPort + "]");
            String port = readLine();

            if (port.equals("")) {
                serverInfo.put("port", defaultPort);
                validInput = true;
            } else if (port.equals("8807")) {
                serverInfo.put("port", port);
                validInput = true;
            } else {
                System.out.println(Color.RED + "Invalid port!" + Color.RESET);
                validInput = false;
            }
        } while (!validInput);

        iClientInputHandler = (IClientInput) Naming.lookup("rmi://" + serverInfo.get("address") + ":"
                + serverInfo.get("port") + "/" + root);
        iClientInputHandler.registerCallBack(clientCallBack);
        System.out.println("Connected to " + serverInfo.get("address")
                + ":" + serverInfo.get("port"));
    }

    /*
    public void clearCli(){
       System.out.println("\033[H\033[2J");
        System.out.flush();
    }
    */

    /**
     * Registration of the User in the Game Server and association to CallBack
     *
     * @throws ServerNotActiveException
     * @throws RemoteException
     */
    @Override
    public void askLogin() throws ServerNotActiveException, RemoteException {
        while (true) {
            System.out.print("Enter the username: ");
            String username = readLine();
            if (username.equals("")) {
                System.out.println(Color.RED + "You didn't choose a username. Try again" + Color.RESET);
                continue;
            }
            if (iClientInputHandler.logIn(username, clientCallBack)) {
                this.username = username;
                return;
            }
        }
    }


    public void setGO_TO_MENU(boolean GO_TO_MENU) {
        this.GO_TO_MENU = GO_TO_MENU;
    }

    public void setGAME_ON(boolean GAME_ON) {
        this.GAME_ON = GAME_ON;
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    public void setSTART(boolean START) {
        this.START = START;
        if (START) {
            System.out.print(Color.RED + "Press 'Enter' to play" + Color.RESET);
        }
    }

    public boolean isEND() {
        return END;
    }

    public void setEND(boolean END) {
        this.END = END;
    }

    /**
     * A switcher used to move through the 3 MAIN STATE of the CLI:
     * MENU, WAITING ROOM, GAMEPLAY
     *
     * @throws ServerNotActiveException
     * @throws RemoteException
     */

    public void redirect() throws ServerNotActiveException, RemoteException {
        if (START) {
            showMatchSetup();
            setSTART(false);
        } else if (END) {
            goToEndRoom();
        }
        askMenuAction();
        askWaitingAction();
        askPlayerMove();

    }

    @Override
    public void askMenuAction() throws ServerNotActiveException, RemoteException {
        while (GO_TO_MENU) {
            System.out.println(Storage.menuOption);
            showRandomTip();
            System.out.print("-----------------------------------------------------------\n" +
                    "Enter the Command you wish to use: ");
            String option = readLine();
            if (option.startsWith("/chat")) {
                handleChatMessage(option);
            } else {
                switch (option) {
                    case "create", "c", "cr" -> {
                        if (askCreateMatch()) redirect();
                    }
                    case "join", "j", "jo" -> {
                        if (askJoinMatch()) redirect();
                    }
                    case "online", "on" -> {
                        showOnlinePlayer();
                        askToContinue();
                    }
                    case "exit", "ex" -> {
                        if (askExitGame()) return;
                    }
                    case "help", "h", "he" -> askAssistMode();
                    default -> System.out.println(Color.RED + "The [" + option + "] cannot be found! Please try again."
                            + Color.RESET);
                }
            }
        }
    }

    /**
     * Showcase the Commands available during Waiting Players
     */
    public void askWaitingAction() throws RemoteException, ServerNotActiveException {
        while (!GAME_ON && !GO_TO_MENU) {
            System.out.println("-----------------------------------------------------------\n" +
                    Color.WHITE_BRIGHT + " {| Room " + matchID + " |}" + Color.RESET);

            System.out.println(Storage.waitingAction);
            showRandomTip();
            System.out.print("-----------------------------------------------------------\n"
                    + "Enter the Command you wish to use: ");
            String option = readLine();
            if (option.startsWith("/chat")) {
                handleChatMessage(option);
            } else if (option.equals("")) {
                redirect();
            } else {
                switch (option) {
                    case "rules", "ru" -> showGameRules();
                    case "online", "on" -> showOnlinePlayer();
                    case "more", "mo" -> askMoreOptions();
                    default -> System.out.println(Color.RED + "The [" + option + "] cannot be found! Please try again."
                            + Color.RESET);
                }
                askToContinue();
            }
        }
    }

    private void showCommandMenu() {
        if (BABY_PROTOCOL) {
            if (!SEL_MODE) {
                System.out.println(Storage.commandMenu0);
            } else {
                if (NOT_SEL_YET) System.out.println(Storage.commandMenu1);
                else System.out.println(Storage.commandMenu2);
            }
        } else {
            System.out.println(Storage.commandMenuExtra);
        }
    }

    //TODO: sort command removed from askPlayerMove (now only for askInsertion)
    @Override
    public void askPlayerMove() throws RemoteException, ServerNotActiveException {
        while (GAME_ON && !GO_TO_MENU) {
            showDisplay();
            showWhoIsPlaying();
            showCommandMenu();
            showRandomTip();
            System.out.print("""
                    -----------------------------------------------------------
                    Enter the command you wish to use:\040""");
            String option = readLine();
            if (option.startsWith("/chat")) {
                handleChatMessage(option);
            } else if (option.equals("")) {
                redirect();
            } else {
                switch (option) {
                    case "select", "se" -> askSelection();
                    case "clear", "cl" -> askDeselection();
                    case "insert", "in" -> askInsertion();
                    case "show", "sh" -> askShowObject();
                    case "more", "mo" -> askMoreOptions();
                    default -> System.out.println(Color.RED + "The [" + option + "] cannot be found! Please try again."
                            + Color.RESET);
                }
            }
        }
    }

    @Override
    public boolean askCreateMatch() throws ServerNotActiveException, RemoteException {
        int playerNumber = askMaxSeats();
        if (iClientInputHandler.createMatch(playerNumber)) {
            askToContinue();
            return true;
        }
        return false;
    }

    @Override
    public int askMaxSeats() {
        int playerNumber = 0;
        do {
            try {
                System.out.print("Please select the number of players for this match [2 to 4]: ");
                playerNumber = Integer.parseInt(readLine());
                if (playerNumber < 2 || playerNumber > 4) {
                    System.out.println(Color.RED + "Invalid number! Please try again." + Color.RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(Color.RED + "Invalid input! Please try again." + Color.RESET);
            }
        } while (playerNumber < 2 || playerNumber > 4);
        System.out.println("Selected " + playerNumber + " players.");
        return playerNumber;
    }

    @Override
    public boolean askJoinMatch() throws ServerNotActiveException, RemoteException {
        showMatchList();
        int matchID;
        try {
            System.out.print("Please enter the room number: ");
            matchID = Integer.parseInt(readLine());
            System.out.println("Selected Room [" + matchID + "].");
            if (iClientInputHandler.joinGame(matchID)) {
                readLine();
                return true;
            } else {
                System.out.println(Color.RED + "Invalid number! Please try again." + Color.RESET);
            }
        } catch (NumberFormatException e) {
            System.out.println(Color.RED + "Invalid input! Please try again." + Color.RESET);
        }
        askToContinue();
        return false;
    }

    @Override
    public void showMatchList() throws RemoteException {
        iClientInputHandler.printMatchList();
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
    public void showMatchSetup() {
        showBoard();
        askToContinue();
        showCommonGoals();
        showPersonalGoal();
        askToContinue();
        System.out.println(Color.BLUE_BOLD + "The match has started!" + Color.RESET);
        showWhoIsPlaying();
        initPlayer(username);
        announceCurrentPlayer();
        askToContinue();
    }

    @Override
    public boolean askLeaveMatch() throws RemoteException {
        return iClientInputHandler.leaveMatch();
    }

    @Override
    public boolean askExitGame() throws RemoteException {
        if (iClientInputHandler.exitGame()) {
            System.exit(0);
            return true;
        }
        return false;
    }

    public void askMoreOptions() throws RemoteException, ServerNotActiveException {
        String command;
        System.out.print(Storage.MoreOptions);
        command = readLine();
        switch (command) {
            case "leave", "le" -> {
                if (askLeaveMatch()) redirect();
            }
            case "exit", "ex" -> askExitGame();
            case "help", "h", "he" -> askAssistMode();
            case "" -> {
            }
            default -> System.out.println(Color.RED + "The [" + command + "] cannot be found! Please try again."
                    + Color.RESET);
        }
    }

    @Override
    public void showCommonGoals() {
        System.out.println("Common Goals:");
        for (int i = 0; i < Storage.commonGoal.size(); i++) {
            showGoalDescription(Storage.commonGoal.get(i));
            System.out.println(Color.YELLOW + "> Top score of this Common Goal: " + Color.RESET +
                    Storage.commonGoalScore.get(i) + " points.");
            System.out.println();
            askToContinue();
        }
    }

    @Override
    public void showPersonalGoal() {
        System.out.println(username + "'s PersonalGoal:");
        System.out.println(Storage.goalPersonalMap.get(Storage.personalGoal));
        System.out.println(Storage.PGTable);
        showPersonalPoints();
        System.out.println();
    }

    @Override
    public void announceCurrentPlayer() {
        if (Storage.currentPlayer.equals(player)) {
            System.out.println(Color.RED + "[!] > Hey " + player + "!!! It's your turn!!!" + Color.RESET);
        } else {
            System.out.println(Color.CYAN + "It's " + Storage.currentPlayer + "'s turn." + Color.RESET);
        }
        System.out.println();
    }

    @Override
    public void showWhoIsPlaying() {
        for (String name : Storage.players) {
            String tmp=name;
            if(name.equals(username)){
                tmp="YOU";
            }
            if(name.equals(currentPlayer)){
                System.out.print(Color.RED_BB+" {[ "+tmp+" ]} "+Color.RESET);
            }else {
                System.out.print(Color.WHITE_BOLD+" [ "+tmp+" ] "+Color.RESET);
            }

        }
        System.out.println();
    }

    @Override
    public void showPlayerShelf() {

        String[][] shelf = Storage.shelves.get(Storage.players.indexOf(username));
        System.out.println("----------------------------------------------------");
        System.out.println("Your Shelf:");
        for (int j = 0; j < Storage.SHELF_COLUMN; j++) {
            System.out.print("      " + j + "      ");
        }
        System.out.println();
        for (int i = 0; i < Storage.SHELF_ROW; i++) {
            for (int j = 0; j < Storage.SHELF_COLUMN; j++) {
                String item = shelf[i][j] == null ? "_________._" : checkColorItem(shelf[i][j]);
                System.out.print("[" + item + "]");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void showGroupPoints() {
        System.out.println("Group Points Table: ");
        System.out.print(Storage.GROUP_POINTS);
        System.out.println(Color.YELLOW + "--Tip: See Game Rules for more info" + Color.RESET);
    }

    @Override
    public void showEveryShelf() {
        for (int k = 0; k < Storage.shelves.size(); k++) {
            String[][] userShelf = Storage.shelves.get(k);
            System.out.println(Storage.players.get(k) + "'s Shelf:");
            for (int j = 0; j < SHELF_COLUMN; j++) {
                System.out.print("      " + j + "      ");
            }
            System.out.println();
            for (int i = 0; i < Storage.SHELF_ROW; i++) {
                for (int j = 0; j < SHELF_COLUMN; j++) {
                    String item = userShelf[i][j] == null ? "_________._" : checkColorItem(userShelf[i][j]);
                    System.out.print("[" + item + "]");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    @Override
    public void showBoard() {
        String[][] board = Storage.virtualBoard;
        System.out.println("Board:");
        System.out.print("  ");
        for (int j = 0; j < Storage.BOARD_COLUMN; j++) {
            System.out.print("      " + j + "       ");
        }
        System.out.println();
        for (int i = 0; i < Storage.BOARD_ROW; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < Storage.BOARD_COLUMN; j++) {
                if (board[i][j] != null && board[i][j].startsWith(">")) {
                    //If the cell is temporarily selected by the player
                    String item = checkColorItem(board[i][j].substring(1));
                    System.out.print(Color.WHITE_BG + "[" + "" + item.replace
                            ("_", Color.WHITE_BG + "_") + Color.WHITE_BG + "]" + Color.RESET + " ");
                } else {
                    String item = board[i][j] == null ? "_________._" : checkColorItem(board[i][j]);
                    System.out.print("[" + item + "] ");
                }
            }
            System.out.print(" ");
            System.out.println(i);
        }
        System.out.print("  ");
        for (int j = 0; j < Storage.BOARD_COLUMN; j++) {
            System.out.print("      " + j + "       ");
        }
        System.out.println();
    }

    public String checkColorItem(String item) {
        int index1, index2 = item.length() - 3;

        String itemType = item;
        switch (item.substring(0, item.length() - 3)) {
            case "__Cats__" -> {
                index1 = item.indexOf("Cats");
                itemType = item.substring(0, index1) + Color.CATS + item.substring(index1 + "Cats".length(), index2) +
                        Color.GREEN_BOLD_BRIGHT + item.substring(index2) + Color.RESET;
            }
            case "_Books__" -> {
                index1 = item.indexOf("Books");
                itemType = item.substring(0, index1) + Color.BOOKS + item.substring(index1 + "Books".length(), index2) +
                        Color.WHITE_BOLD_BRIGHT + item.substring(index2) + Color.RESET;
            }
            case "_Games__" -> {
                index1 = item.indexOf("Games");
                itemType = item.substring(0, index1) + Color.GAMES + item.substring(index1 + "Games".length(), index2) +
                        Color.YELLOW_BOLD_BRIGHT + item.substring(index2) + Color.RESET;
            }
            case "_Frames_" -> {
                index1 = item.indexOf("Frames");
                itemType = item.substring(0, index1) + Color.FRAMES + item.substring(index1 + "Frames".length(), index2) +
                        Color.BLUE_BOLD_BRIGHT + item.substring(index2) + Color.RESET;
            }
            case "Trophies" -> {
                itemType = Color.CYAN_BOLD_BRIGHT + item + Color.RESET;
            }
            case "_Plants_" -> {
                index1 = item.indexOf("Plants");
                itemType = item.substring(0, index1) + Color.PLANTS + item.substring(index1 + "Plants".length(), index2) +
                        Color.MAGENTA_BOLD_BRIGHT + item.substring(index2) + Color.RESET;
            }
        }
        return itemType;
    }

    @Override
    public void showPlayersStats() {
        List<String> playerList = Storage.players;
        List<Integer> scoreList = Storage.scores;
        for (int i = 0; i < playerList.size(); i++) {
            System.out.println("> " + playerList.get(i) + "'s stats: ");
            System.out.println("  " + "SCORE:\t" + scoreList.get(i));
            System.out.println("  " + "STATUS:\t");     //Needed if the Connection resilience is implemented
        }
        askToContinue();
        System.out.println();
        showEveryShelf();
        announceCurrentPlayer();
        showHand();
        System.out.println();
    }

    /**
     * This method shows the Selection menu, one of the MAIN PHASES of the game
     *
     * @throws ServerNotActiveException
     * @throws RemoteException
     */
    @Override
    public void askSelection() throws ServerNotActiveException, RemoteException {
        System.out.println("Select a cell on the board");
        boolean selectionConfirm;
        do {
            List<Integer> coordinates = askCoordinates();
            int row = coordinates.get(0);
            int column = coordinates.get(1);
            if (iClientInputHandler.selectCell(row, column)) {
                NOT_SEL_YET = false;
                System.out.println(Color.YELLOW + "Item selected: " +
                        showItemInCell(row, column).replace(">", "").
                                replace("_", "") + Color.RESET);
                showHand();
            }
            askToContinue();
            showDisplay();
            System.out.print(Storage.anotherCard);
            selectionConfirm = "".equals(readLine());
        } while (selectionConfirm);
    }

    /**
     * This method shows a sub menu of the Selection menu
     * Ask the player to insert the coordinates on the board for the selection
     *
     * @return a list that represents the Coordinates (row,column)
     */
    public List<Integer> askCoordinates() {
        showBoard();
        System.out.println("Type the coordinates you wish to select ONE AT THE TIME (first ROW, then COLUMN).");
        List<Integer> coordinates = new ArrayList<>();
        coordinates.add(askTheIndex("ROW", 0, Storage.BOARD_ROW));
        coordinates.add(askTheIndex("COLUMN", 0, Storage.BOARD_COLUMN));
        return coordinates;
    }

    /**
     * Ask a single index
     * Used for:{@link #askCoordinates()} or {@link #askIndex()} or {@link #askColumn()}
     *
     * @param type        "ROW" or "COLUMN" or "Position1" or "Position2"
     * @param lower_limit the minimum value of index
     * @param upper_limit the maximum value of index
     * @return Index(ROW, COLUMN, Position according to type)
     */
    public int askTheIndex(String type, int lower_limit, int upper_limit) {
        int select = -1;
        do {
            try {
                System.out.print(type + " (" + lower_limit + " to " + (upper_limit - 1) + "): ");
                select = Integer.parseInt(readLine());
                if (select < lower_limit || select > upper_limit) {
                    System.out.println(Color.RED + "Invalid number! Please try again." + Color.RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(Color.RED + "Invalid input! Please try again." + Color.RESET);
            }
        } while (select < lower_limit || select > upper_limit);
        return select;
    }

    public String showItemInCell(int row, int column) {
        return Storage.virtualBoard[row][column];
    }

    @Override
    public void askDeselection() throws ServerNotActiveException, RemoteException {
        if (BABY_PROTOCOL && !showHand()) {
            System.out.println(Color.RED + "You have not selected any card yet." + Color.RESET);
        } else {
            System.out.print(Storage.deselectConfirm);

            boolean deselectConfirm = "y".equals(readLine());
            if (deselectConfirm) {
                if (iClientInputHandler.deselectCards()) {
                    NOT_SEL_YET = true;
                }
            }
        }
    }

    /**
     * This method shows the Insertion menu, one of the MAIN PHASES of the game
     *
     * @throws ServerNotActiveException
     * @throws RemoteException
     */
    @Override
    public void askInsertion() throws ServerNotActiveException, RemoteException {
        if (showHand()) {

            System.out.println("These are the cards you have selected:");

            System.out.print(Storage.insertionConfirm);
            boolean confirm = "y".equals(readLine());
            if (confirm) {
                if (iClientInputHandler.confirmSelection()) {
                    String option = "";
                    while (!option.equals("more") && !option.equals("mo")) {
                        showHand();
                        System.out.print(Storage.commandInsert);
                        option = readLine();
                        switch (option) {
                            case "sort", "so" -> askSort();
                            case "show", "sh" -> askShowObject();
                            case "" -> {
                                showPlayerShelf();
                                int column;
                                do {
                                    column = askColumn();
                                } while (column == -2);
                                if (column != -1) {
                                    if (iClientInputHandler.insertInColumn(column)) {
                                        showPlayerShelf();
                                        System.out.println(Color.YELLOW + "Inserted in the column: " + column + Color.RESET);
                                        if(iClientInputHandler.endTurn()){
                                            System.out.println(Color.YELLOW +"- End Turn -"+ Color.RESET);
                                        }
                                        askToContinue();
                                        NOT_SEL_YET = true;
                                        SEL_MODE = false;
                                        return;
                                    }
                                }
                            }
                            case "more", "mo" -> {
                                askMoreOptions();
                            }
                            default ->
                                    System.out.println(Color.RED + "The [" + option + "] cannot be found! Please try again."
                                            + Color.RESET);
                        }
                        //askToContinue();
                    }
                } else {
                    System.out.println(Color.RED + "Selection Confirm failed" + Color.RESET);
                }
            } else {
                System.out.println(Color.RED + "Insertion canceled." + Color.RESET);
            }
        } else {
            System.out.println(Color.RED + "You can’t insert cards if you did not select any cards!" + Color.RESET);
        }
    }

    public void askSort() throws ServerNotActiveException, RemoteException {
        System.out.println("There are the cards you have selected:");
        if (showHand()) {
            System.out.println("Which position do you wish to swap?");
            List<Integer> itemSwapped = askIndex();
            if (itemSwapped == null) {
                System.out.println("Selection canceled.");
            } else if (!itemSwapped.get(0).equals(itemSwapped.get(1))) {
                if (iClientInputHandler.sortHand(itemSwapped.get(0), itemSwapped.get(1))) {
                    System.out.println("Card order changed.");
                }
            } else {
                System.out.println("Same position. Swap failed.");
            }
        } else {
            System.out.println(Color.RED + "You can’t sort cards if you did not select any cards!" + Color.RESET);
        }
    }

    public List<Integer> askIndex() {
        String sortConfirm;
        int position1, position2;
        position1 = askTheIndex("Position1", 1, Storage.currentPlayerHand.size() + 1);
        position2 = askTheIndex("Position2", 1, Storage.currentPlayerHand.size() + 1);
        System.out.println("You have chosen to swap " +
                Storage.currentPlayerHand.get(position1 - 1).replace("_", "") +
                " and " +
                Storage.currentPlayerHand.get(position2 - 1).replace("_", ""));
        System.out.print(Storage.indexConfirm);
        sortConfirm = readLine();
        switch (sortConfirm) {
            case "y":
                List<Integer> index = new ArrayList<>();
                index.add(position1 - 1);
                index.add(position2 - 1);

                return index;
            case "r":
                askIndex();
                break;
            case "n":
                return null;
            default:
                System.out.println(Color.RED + "Invalid command! Please try again." + Color.RESET);
                break;
        }
        return null;
    }

    /**
     * This method shows a sub menu of the Insertion menu.
     * Its task it to get the number of the column in which the player wish to insert the items
     *
     * @return number of the column designated
     */
    public int askColumn() {
        System.out.println("In which column would you like to insert the cards?");
        String columnConfirm;
        int column;
        column = askTheIndex("COLUMN", 0, SHELF_COLUMN);
        do {

            System.out.print("""
                    -----------------------------------------------------------
                    You have chosen Column:\040""" + column + "\n" + Storage.columnConfirm);

            columnConfirm = readLine();
            switch (columnConfirm) {
                case "y":
                    return column;
                case "r":
                    return -2;
                case "n":
                    return -1;
                default:
                    System.out.println(Color.RED + "Invalid command! Please try again." + Color.RESET);
                    break;
            }
        } while (true);
    }

    public void handleChatMessage(String option) throws RemoteException {
        String message = option.substring(option.indexOf(" ") + 1);
        String usernameString = option.substring(5);
        String regex = "\\[|\\]";
        String[] matches = usernameString.split(regex);
        if (matches.length > 1) {
            String playerName = matches[1];
            if (iClientInputHandler.sendPlayerMessage(message, playerName)) {
                System.out.println("Message sent to: " + playerName);
            }
        } else {
            if (!iClientInputHandler.sendChatMessage(message)) {
                System.out.println(Color.RED + "The message was not sent" + Color.RESET);
            }
        }
        readLine();
    }

    /**
     * This method allows the view of the Items selected by the player
     * Improper use of the name Hand, It should be: temporarily list of item selected on the board
     *
     * @return true if the "hand" is not empty, otherwise false
     */
    public boolean showHand() {
        System.out.print(Storage.currentPlayer + " has selected: ");
        if (Storage.currentPlayerHand.isEmpty()) {
            System.out.println("Nothing");
            return false;
        }
        for (int i = 0; i < Storage.currentPlayerHand.size(); i++) {
            System.out.print((i + 1) + ".[" +
                    checkColorItem(Storage.currentPlayerHand.get(i)).replace("_", "") + "]  ");
        }
        System.out.println();
        return true;
    }

    @Override
    public void showEndGameToken() {
        if (Storage.endGameToken) {
            System.out.println("The EndGame Token is taken, it's the last round!");
        } else {
            System.out.println("The EndGame Token is still available. Good Luck!");
        }
        System.out.println();
    }

    @Override
    public void showTimer() {

    }

    /**
     * This is a sub Menu used for showcase the objects of the game,
     * like Board, Shelf, Goals....
     *
     * @throws RemoteException if {@link #showOnlinePlayer()} is called
     *                         and the client is not connected to the server through RMI
     */
    @Override
    public void askShowObject() throws RemoteException {
        String object = "new_command";
        while (!object.equals("")) {
            System.out.print(Storage.listObjects);
            object = readLine();
            switch (object) {
                case "hand", "ha" -> showHand();
                case "pgoal", "pg" -> showPersonalGoal();
                case "cgoal", "cg" -> showCommonGoals();
                case "shelf", "sh" -> {
                    showPlayerShelf();
                    showGroupPoints();
                }
                case "board", "bo" -> showBoard();
                case "stats", "st" -> showPlayersStats();
                case "rules", "ru" -> showGameRules();
                case "end", "en" -> showEndGameToken();
                case "online", "on" -> showOnlinePlayer();
                case "timer", "ti" -> showTimer();
                case "" -> {
                    return;
                }
                default -> System.out.println(Color.RED + "The [" + object + "] cannot be found! Please try again."
                        + Color.RESET);
            }
            askToContinue();
        }
    }

    /**
     * This method forward a request directly to the Server
     * --> It's going to display a list of online Players
     *
     * @throws RemoteException if Client is not connected to the Server through RMI
     */
    @Override
    public void showOnlinePlayer() throws RemoteException {
        iClientInputHandler.printOnlinePlayers();
    }

    public void printer(String message) {
        System.out.println(message);
        //TODO: try to fix the too many Press enter problem, it's a fail :(
        //TODO: maybe try again later
        interruptThread();
    }

    @Override
    public void showGoalDescription(String commonGoalCard) {
        System.out.println(Storage.goalCommonMap.get(commonGoalCard));

    }

    /**
     * This method allows the user to read the GAME RULE
     */
    @Override
    public void showGameRules() {
        System.out.println(Storage.goalOfTheGame);
        askToContinue();
        System.out.println(Storage.gamePlay);
        askToContinue();
        System.out.println(Storage.refillingLivingRoom);
        askToContinue();
        System.out.println(Storage.fulfillingCommonGoal);
        askToContinue();
        System.out.println(Storage.gameEnd);
        //TODO: maybe add this to storage?
        Collections.addAll(commonGoalList, "CommonGoal2Lines", "CommonGoal2Columns", "CommonGoal3Column",
                "CommonGoal4Lines", "CommonGoal8Tiles", "CommonGoalCorner", "CommonGoalDiagonal", "CommonGoalSquare",
                "CommonGoalStairs", "CommonGoal4Group", "CommonGoal6Group", "CommonGoalXShape");
        System.out.println(Color.YELLOW_BOLD + "Common Goals:");
        for (String s : commonGoalList) {
            showGoalDescription(s);
        }
        System.out.println();
    }

    public void checkTurn() {
        SEL_MODE = Storage.currentPlayer.equals(username);

    }

    /**
     * This Method is used for DECORATION of TUI
     */
    public void showRandomTip() {
        int max, min = 0;
        if (GO_TO_MENU) max = Storage.MENU_TIPS;
        else if (!GAME_ON) {
            max = Storage.WAIT_TIPS;
            min = MENU_TIPS;
        } else if (GAME_ON && NOT_SEL_YET) {
            max = Storage.SEL_TIPS;
            min = WAIT_TIPS;
        } else {
            max = Storage.tips.size();
            min = WAIT_TIPS;
        }
        String tip = Storage.tips.get((int) (Math.random() * max - min) + min);
        System.out.println(tip);
    }

    /**
     * This method is used for decoration of the TUI
     */
    public void displayMiniBoard() {
        List<String> display = Storage.current_display;
        String[][] board = Storage.virtualBoard;

        display.set(0, display.get(0) + "\t\t\t{Board}\t\t\t\t\t\t\t");
        for (int i = 0; i < Storage.BOARD_ROW; i++) {
            display.set(i + 1, display.get(i + 1) + i);

            for (int j = 0; j < Storage.BOARD_COLUMN; j++) {
                if (board[i][j] != null && board[i][j].startsWith(">")) {
                    //If the cell is temporarily selected by the player
                    String item = giveMeColor(board[i][j].substring(1));
                    display.set(i + 1, display.get(i + 1) + " " + Color.WHITE_BG + "[" + "" + item + Color.WHITE_BG + "]" + Color.RESET);
                } else {
                    String item = board[i][j] == null ? " " : giveMeColor(board[i][j]);
                    display.set(i + 1, display.get(i + 1) + " [" + item + "]");
                }
            }
            display.set(i + 1, display.get(i + 1) + " ");
            display.set(i + 1, display.get(i + 1) + i + "|\t");
        }
        display.set(10, display.get(10) + "  ");

        for (int j = 0; j < Storage.BOARD_COLUMN; j++) {
            display.set(10, display.get(10) + " " + j + "  ");
        }
        display.set(10, display.get(10) + "   ");
        Storage.current_display = display;
    }

    public String giveMeColor(String item) {
        String itemType = item;
        switch (item.substring(0, item.length() - 3)) {
            case "__Cats__" -> {
                itemType = "\033[1;92m*\033[0m";
            }
            case "_Books__" -> {

                itemType = "\033[1;97m*\033[0m";
            }
            case "_Games__" -> {
                itemType = "\033[1;93m*\033[0m";
            }
            case "_Frames_" -> {
                itemType = "\033[1;94m*\033[0m";
            }
            case "Trophies" -> {

                itemType = "\033[1;96m*\033[0m";
            }
            case "_Plants_" -> {
                itemType = "\033[1;95m*\033[0m";
            }
        }
        return itemType;

    }

    /**
     * This method is used for decoration of the TUI
     */
    public void displayMiniShelf() {
        String[][] shelf = Storage.shelves.get(Storage.players.indexOf(username));
        List<String> display = Storage.current_display;
        display.set(3, display.get(3) + "  {Your Shelf}\t ");
        for (int i = 0; i < Storage.SHELF_ROW; i++) {
            for (int j = 0; j < SHELF_COLUMN; j++) {
                String item = shelf[i][j] == null ? " " : giveMeColor(shelf[i][j]);
                display.set(4 + i, display.get(4 + i) + "[" + item + "]");
            }
            display.set(4 + i, display.get(4 + i) + "  \t");
        }
        display.set(10, display.get(10) + " \t");
        for (int j = 0; j < SHELF_COLUMN; j++) {
            display.set(10, display.get(10) + " " + j + " ");
        }
        display.set(10, display.get(10) + "  \t");
    }

    /**
     * This method is used for decoration of the TUI
     */
    public void displayMiniHand() {
        List<String> display = Storage.current_display;
        display.set(0, display.get(0) + "{Selected cards}\t");
        display.set(1, display.get(1) + "   ");

        for (int i = 0; i < 3; i++) {
            if (i < Storage.currentPlayerHand.size()) {
                String item = giveMeColor(Storage.currentPlayerHand.get(i));
                display.set(1, display.get(1) + "[" + item + "]");
            } else {
                display.set(1, display.get(1) + "[ ]");
            }
        }
        display.set(1, display.get(1) + "\t\t");
        display.set(2, display.get(2) + "\t\t\t\t\t");
    }

    /**
     * This method is used for decoration of the TUI
     */
    public void displayMiniPGoal(int pID) {
        List<String> display = Storage.current_display;
        String[] tmp = Storage.PGoals.get(pID);
        display.set(3, display.get(3) + "\t{PersonalGoal} \t");

        for (int i = 4; i < Storage.current_display.size() - 1; i++) {
            display.set(i, display.get(i) + tmp[i - 4] + " \t");
        }
        for (int j = 0; j < SHELF_COLUMN; j++) {
            display.set(10, display.get(10) + " " + j + " ");
        }
        display.set(10, display.get(10) + "  \t");

    }


    /**
     * The first idea was to display in real time the progression of the Game
     * to ONLY the player that were not playing, so while they were waiting they could watch at something more lively
     * But then it was extended to the Current Player display too
     */
    public void showDisplay() {
        Storage.reset_display();
        displayMiniBoard();
        displayMiniShelf();
        if (SEL_MODE) displayMiniHand();
        displayMiniPGoal(Storage.personalGoal);
        displayMiniCGoal();
        for (int i = 0; i < Storage.current_display.size(); i++) {
            System.out.println(Storage.current_display.get(i));
        }
    }

    /**
     * This method is used for decoration of the TUI
     */
    public void displayMiniCGoal() {
        List<String> display = Storage.current_display;
        List<String> cGoal = Storage.commonGoal;
        List<Integer> scores = Storage.commonGoalScore;

        for (int i = 0; i < cGoal.size(); i++) {
            display.set(i, display.get(i) + "{" + cGoal.get(i) + "}" + ": [" + scores.get(i) + "]");
        }
    }


    public void showPersonalPoints() {
        int index = Storage.getPlayerIndex(username);
        int points = Storage.hiddenPoints.get(index);
        System.out.println("Personal Goal's points (Private): " + points);

    }

    public void showGameResults() {
        for (String r : gameResults) {
            System.out.println(r);
            askToContinue();
        }
    }

    public void goToEndRoom() throws ServerNotActiveException, RemoteException {
        System.out.println("The match ended. Press 'anything' to see the results");
        readLine();
        showGameResults();
        setEND(false);
        redirect();
    }

}
