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
    private int personalGoal;
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
        int playerIndex = JSONConverter.getPlayerIndex(username);
        player = JSONConverter.players.get(playerIndex);
        personalGoal = JSONConverter.personalGoal;
    }

    public void askServerInfo() throws MalformedURLException, NotBoundException, RemoteException {
        Map<String, String> serverInfo = new HashMap<>();
        String defaultAddress = "localhost";
        String defaultPort = "8807";
        boolean validInput;

        do {
            System.out.println("Enter the server address: [" + defaultAddress + "]");
            String address = readLine();

            if (address.equals("")) {
                serverInfo.put("address", defaultAddress);
                validInput = true;
            } else if (address.equals("localhost")){
                serverInfo.put("address", address);
                validInput = true;
            } else {
                System.out.println("Invalid address!");
                validInput = false;
            }
        } while (!validInput);

        do {
            System.out.println("Enter the server Port: [" + defaultPort + "]");
            String port = readLine();

            if (port.equals("")) {
                serverInfo.put("port", defaultPort);
                validInput = true;
            } else if (port.equals("8807")){
                serverInfo.put("port", port);
                validInput = true;
            } else {
                System.out.println("Invalid port!");
                validInput = false;
            }
        } while (!validInput);

        System.out.println(serverInfo.get("address"));
        System.out.println(serverInfo.get("port"));
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
        boolean usernameAccepted;

        do {
            System.out.print("Enter the username: ");
            String username = readLine();
            usernameAccepted = iClientInputHandler.logIn(username);

            if(usernameAccepted){
                this.username=username;
                askMenuAction();
            }
        } while (!usernameAccepted);
    }

    @Override
    public void askMenuAction() throws ServerNotActiveException, RemoteException {
        System.out.println("""
                Menu Option:
                create - Create a new match.
                join - Join a match.
                exit - Exit game.
                To send a message to a online player type ‘/chat[nickname]:’ followed by your message in the console.
                """);
        String option = "";
        while (!option.equals("exit")) {
            System.out.print("Enter the option you wish to select: ");
            option = readLine();
            if (option.startsWith("/chat")){
                handleChatMessage(option);
            } else {
                switch (option) {
                    case "create" -> askCreateMatch();
                    case "join" -> askJoinMatch();
                    case "exit" -> askExitGame();
                    default -> System.out.println("Invalid command! Please try again.");
                }
            }
        }
    }

    @Override
    public void askCreateMatch() throws ServerNotActiveException, RemoteException {
        System.out.println("Room generation in  progress...");
        int playerNumber = askMaxSeats();
        iClientInputHandler.createMatch(playerNumber);
    }

    @Override
    public int askMaxSeats() {
        int playerNumber = 0;
        do {
            try {
                System.out.print("Please select the number of players [2 to 4]: ");
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
        //TODO: CLI display> Match_List
        int matchID;

        try {
            System.out.print("Please enter the room number: ");
            matchID = Integer.parseInt(readLine());
            if(iClientInputHandler.joinGame(matchID)){
                System.out.println("Selected Room [" + matchID + "].");
                askWaitingAction();
            }else{
                System.out.println("Invalid number! Please try again.");
                askMenuAction();
            }
        } catch (NumberFormatException e){
            System.out.println("Invalid input! Please try again.");
        }
    }

    /**
     * Showcase the Commands available during Waiting Players
     */
    private void askWaitingAction() throws RemoteException {
        System.out.println("The match has not started yet. Waiting for more players to join... [" +
                JSONConverter.players.size() + "]seats available");
        System.out.println("""
                These are the commands available:
                leave - Leave Match.
                rules - Read Game Rules.
                online - Show Online Players.
                To send a message in the Match type ‘/chat:’ followed by your message in the console.
                To send a message to a online player type ‘/chat[nickname]:’ followed by your message in the console.
                """);

        String option = "";
        while (!option.equals("exit")) {
            System.out.print("Enter the option you wish to select: ");
            option = readLine();
            if (option.startsWith("/chat")){
                handleChatMessage(option);
            } else {
                switch (option) {
                    case "leave" -> askLeaveMatch();
                    case "rules" -> showGameRules();
                    case "online" -> showOnlinePlayer();
                    default -> System.out.println("Invalid command! Please try again.");
                }
            }
        }
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
        //TODO: Redo better

        initPlayer(username);
        showBoard();
        showCommonGoals();
        showPersonalGoal();
        System.out.println("The match has started!");
        showCurrentPlayer();
    }
    private void showGameRules() {
        System.out.println("""
                Goal of the game:
                Players take item tiles from the living room and place them in their bookshelves to score points;
                the game ends when a player completely fills their bookshelf. The player with more points at
                the end will win the game. There are 4 ways to score points:
                1. Personal Goal card
                    The personal goal card grants points if you match the highlighted spaces with
                    the corresponding item tiles.
                    Example: In the illustrated situation, at the end of the game the tile disposal
                    shows 3 matches, that is worth 4 points.
                2. Common Goal cards
                    The common goal cards grant points to the players who achieve the illustrated
                    pattern. See the last page for a detailed descriptions of the common goal cards.
                    Example: In a 3-player game on both Common Goal cards will be stacked
                    the 4-, 6-, 8- scoring tokens (from bottom to top).
                3. Adjacent Item tiles
                    Groups of adjacent item tiles of the same type on your bookshelf grant
                    points depending on how many tiles are connected (with one side touching).
                    Note: Item tiles with the same background color are considered to be of the same type.
                    Example: In the situation above, at the end of the game there are 5 groups of
                    adjacent item tiles of the same type:
                    8 Plant tiles: 8 pt
                    4 Trophy tiles: 3 pt
                    5 Cat tiles: 5 pt
                    4 Frame tiles: 3 pt
                    3 Boardgame tiles: 2 pt
                    Total:
                    21 points
                4. Gane-end trigger
                    The first player who completely fills their bookshelf scores 1 additional point.
                """);
        System.out.println("""
                Gameplay:
                The game is divided in turns that take place in a clockwise order starting from the first player.
                During your turn, you must take 1, 2 or 3 item tiles from the living room board, following these rules:
                The tiles you take must be adjacent to each other and form a straight line.
                All the tiles you take must have at least one side free (not touching directly other tiles) at the
                beginning of your turn (i.e. you cannot take a tile that becomes free after your first pick).
                Then, you must place all the tiles you’ve picked into 1 column of your bookshelf. You can decide
                the order, but you cannot place tiles in more than 1 column in a single turn.
                Note: You cannot take tiles if you don’t have enough available spaces in your bookshelf.
                """);
        System.out.println("""
                Refilling the living room:
                The living room will be refiled when, at the end of your turn, on the board there are only item tiles
                without any other adjacent tile, i.e. the next player can only take single tiles.
                Put the item tiles left on the board back into the bag. Then, draw new item tiles from the bag and
                place them randomly in all the spaces of the board (remember that spaces with dots are only available\s
                in 3- or 4-player games).
                """);
        System.out.println("""
                Fulfilling a common goal:
                If at the end of your turn you have achieved the requirements of a common goal card, take the topmost
                available scoring token from that card. You can achieve and take scoring tokens from both common goal
                cards in the same turn. You can only score points from common goal cards once per game, so you can’t
                take more scoring tokens with the same back number. Players who achieve the common goals requirements
                first will score more points than the other players, so try to be faster than your opponents!
                """);
        System.out.println("""
                Game end:
                The first player who fills all the spaces of their bookshelf takes the end game token. The game
                continues until the player sitting to the right to the player holding the first player seat (if the end
                of the game is triggered by the player sitting to the right to the first player, the game ends
                immediately). Now you can proceed to the final scoring.
                Each player will score:
                    - The points indicated by the tokens they hold (scoring tokens and end game token);
                    - 1/2/4/6/9/12 points for 1/2/3/4/5/6 item tiles in the exact position illustrated by their
                      personal goal card;
                    - 2/3/5/8 points for groups of 3/4/5/6+ item tiles of the same type adjacent on their bookshelf.
                The player who scored most points wins the game. In case of a tie, the tied player sitting further
                (clockwise) from the first player wins the game.
                Scoring Example:
                Example: Helena scores 12 points from scoring tokens, 6 points from her personal goal card, and 
                18 points from the groups of adjacent tiles in her bookshelf:
                6 adjacent Trophy tiles: 8 points
                5 adjacent Cat tiles: 5 points
                5 adjacent Plants tiles: 5 points
                4 matches on the personal goal: 6 points
                Scoring tokens: 12 points
                Total: 36 points
                """);
        showCommonGoals();

    }

    @Override
    public void askLeaveMatch() throws RemoteException {
        //TODO: fixe the null point
        iClientInputHandler.leaveMatch();
        System.out.println("See you soon. Bye.");
    }

    @Override
    public void askExitGame() {

    }

    @Override
    public void showCommonGoals(){
        for (int i = 0; i < JSONConverter.commonGoal.size(); i++) {
            showGoalDescription(JSONConverter.commonGoal.get(i));
        }

//        System.out.println("You received: " + player.getPlayerScore() + " points.");
    }

    @Override
    public void showPersonalGoal() {
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
    public void showShelf() {
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
    public void showBoard() {
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
    public void showPlayersStats() {
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
        if (!JSONConverter.gamePhase.equals("Insertion")) {
                System.out.println("Select a cell on the board");
                boolean selectionConfirm;
                do {
                    String confirm = "";
                    List<Integer> coordinates = askCoordinates();

                    System.out.println("""
                            Now you can:
                            y - Confirm you choice.
                            n - Cancel your choice.
                            r - Retry again.
                            show - See a Game Object(Board, Shelf, Goals, ...)
                            """);

                    while (!confirm.equals("y")) {
                        confirm = readLine();
                        switch (confirm) {
                            case "y":
                                int row = coordinates.get(0);
                                int column = coordinates.get(1);
                                if (iClientInputHandler.selectCell(row, column)) {
                                    //System.out.println("Selection Successful!");
                                    System.out.print("Item selected: ");
                                    showItemInCell(row, column);
                                } else {
                                    confirm = "r";
                                }
                                break;
                            case "n":
                                return;
                            case "r":
                                coordinates = askCoordinates();
                                System.out.println("""
                                    Now you can:
                                    y - Confirm you choice.
                                    n - Cancel your choice.
                                    r - Retry again.
                                    show - See a Game Object(Board, Shelf, Goals, ...)
                                    """);
                                break;
                            case "show":
                                askShowObject();
                                break;
                        }
                    }

                    System.out.println("""
                            Do you want to continue with selection?
                            1. Yes.
                            0. No.""");
                    selectionConfirm = Boolean.parseBoolean(readLine());
                } while (selectionConfirm);
        }
    }

    public List<Integer> askCoordinates() {
        System.out.println("Enter the coordinates you wish to select [row, column].");
        boolean selectConfirm;
        int selectRow = 0, selectColumn = 0;

        do {
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

    public String showItemInCell(int row, int column) {
        return JSONConverter.virtualBoard[row][column];
    }

    @Override
    public void askDeselection() throws ServerNotActiveException, RemoteException {
        if (!JSONConverter.gamePhase.equals("Insertion")) {
            showHand();
            System.out.println("""
                    Are you sure to cancel all of your selection?
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
        System.out.println("These are the cards you have selected:");
        if (showHand()) {
            String option = "";
            while (!option.equals("n")){
                System.out.println("""
                        These are the commands available:
                        sort - Change the order of your cards.
                        show - Look at game board objects.
                        go - Go directly to insertion.
                        n - Cancel your choice.
                        """);
                option = readLine();
                switch (option) {
                    case "sort" -> askSort();
                    case "show" -> askShowObject();
                    case "go" -> {
                        showShelf();
                        int column = askColumn();
                        iClientInputHandler.insertInColumn(column);
                    }
                }
            }
        } else {
            System.out.println("You can’t insert cards if you did not select any cards!");
        }
    }

    public void askSort() throws ServerNotActiveException, RemoteException {
        System.out.println("There are the cards you have selected:");
        showHand();
        System.out.println("Which position do you wish to swap?");
        List<Integer> itemSwapped = askIndex();
        if (iClientInputHandler.sortHand(itemSwapped.get(0), itemSwapped.get(1))) {
            System.out.println("Card order changed.");
        }
    }

    public List<Integer> askIndex() {
        boolean sortConfirm;
        int position1 = 1, position2 = 2;
        //TODO: Controllo che pos1 e pos2 siano diversi per evitare scambio della stessa posizione
        // (che si puo fare, ma vogliamo evitare di far perdere tempo al server)

        //Ma se qualcuno non vuole più cambiare la carta? Penso che pos1 == pos2 sia un buon modo per
        //uscire da askSort().

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

//            if (position1 == position2){
//                System.out.println("Same position! Please try again.");
//            }
            System.out.println("You have chosen to swap " + JSONConverter.currentPlayerHand.get(position1 - 1) + " and " +
                    JSONConverter.currentPlayerHand.get(position2 - 1));
            System.out.println("Confirm your choice:");
            System.out.println("1. Confirm.");
            System.out.println("0. Re-select.");

            sortConfirm = Boolean.parseBoolean(readLine());
        } while(!sortConfirm);
        List<Integer> index = new ArrayList<>();
        index.add(position1 - 1);
        index.add(position2 - 1);

        return index;
    }

    public boolean showHand() {
        System.out.println(player +" have in hand:");
        if (JSONConverter.currentPlayerHand.isEmpty()){
            return false;
        }
        for (int i = 0; i < JSONConverter.currentPlayerHand.size(); i++) {
            System.out.println(JSONConverter.currentPlayerHand.get(i));
        }
        return true;
    }

    public int askColumn() {
        System.out.println("In which column would you like to insert the cards?");
        boolean columnConfirm;
        int column = 0;
         do {
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
             System.out.println("""
            You have chosen Column\040""" + column + """
            Confirm your choice:
            1. Confirm.
            0. Re-select.""");

            columnConfirm = Boolean.parseBoolean(readLine());
        } while (!columnConfirm);
        return column;
    }

    public void handleChatMessage(String option) throws RemoteException {
        String usernameString = option.substring(5);
        String regex = "\\[(.*?)\\]";
        String[] matches = usernameString.split(regex);

        if (matches.length > 1){
            // esiste username = playerName
            String playerName = matches[1];
        } else {
            // non contiene username
        }

        String message = readLine();
        //TODO:
        iClientInputHandler.sendChatMessage(message);

    }

    @Override
    public void showEndGameToken() {
        if (JSONConverter.endGameToken){
            System.out.println("EndGame Token is taken, it's the last round!");
        } else {
            System.out.println("EndGame Token has not been taken.");
        }
    }

    @Override
    public void showTimer() {

    }

    private void showCommandMenu() {
        if (!JSONConverter.gamePhase.equals("Insertion")) {
            System.out.println("""
                    select - Select an item on the board.
                    deselect - Deselect the cards.
                    sort - Change selected items order(at least 2 items selected).
                    insert - Insert in the shelves.
                    show - Show Game Object(Hand, Goals, Board, Shelf, ...).
                    leave - Leave Match.
                    exit - Exit Game.
                    To send a message in the Match type ‘/chat:’ followed by your message in the console.
                    To send a message to a online player type ‘/chat[nickname]:’ followed by your message in the console.
                    """);
        } else {
            System.out.println("""
                    sort - Change selected items order(at least 2 items selected).
                    insert - Insert in the shelves.
                    show - Show Game Object(Hand, Goals, Board, Shelf, ...).
                    leave - Leave Match.
                    exit - Exit Game.
                    To send a message in the Match type ‘/chat:’ followed by your message in the console.
                    To send a message to a online player type ‘/chat[nickname]:’ followed by your message in the console.
                    """);
        }


    }

    @Override
    public void askPlayerMove() throws RemoteException, ServerNotActiveException {
        System.out.println("What do you wish to do? These are the commands available:");
        showCommandMenu();
        String option = "";
        while (!option.equals("leave") && !option.equals("exit")) {
            System.out.println("Enter the command you wish to use:");
            option = readLine();

            if (option.startsWith("/chat")){
                handleChatMessage(option);
            } else {
                switch (option) {
                    case "select" -> askSelection();
                    case "deselect" -> askDeselection();
                    case "show" -> askShowObject();
                    case "leave" -> askLeaveMatch();
                    case "exit" -> askExitGame();
                }
            }
        }
    }

    @Override
    public void askShowObject() {
        System.out.println("""
                List of Objects:
                hand - Show selected items.
                pgoal - See your Personal Goal.
                cgoal - See Common Goals.
                shelf - See your shelf and the insertion limit.
                board - See Living Room Board.
                stats - See Players Stats.
                rules - See Game Rules.
                end - Show if the Endgame Token is taken (if it is, then it's the last round).
                timer - Show timer.
                n - Cancel your choice.
                """);

        String object = "";
        while (!object.equals("n")){
            System.out.print("Enter the object you wish to be shown: ");
            object = readLine();
            switch (object) {
                case "hand" -> showHand();
                case "pgoal" -> showPersonalGoal();
                case "cgoal" -> showCommonGoals();
                case "shelf" -> showShelf();
                case "board" -> showBoard();
                case "stats" -> showPlayersStats();
                case "rules" -> showGameRules();
                case "end" -> showEndGameToken();
                case "timer" -> showTimer();
                default -> System.out.println("The [" + object + "] cannot be found! Please try again.");
            }
        }
    }

    @Override
    public void showOnlinePlayer() throws RemoteException {
        iClientInputHandler.printOnlinePlayers();
    }
    public void printer(String message){
        System.out.println(message);
    }

    public void showGoalDescription(String CommonGoalCard) {
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
