package org.am21.client.view.TUI;

import org.am21.client.ClientCommunicationController;
import org.am21.client.SocketClient;
import org.am21.client.view.ClientView;
import org.am21.client.view.View;
import org.am21.networkRMI.ClientCallBack;
import org.am21.networkRMI.IClientCallBack;
import org.am21.networkRMI.IClientInput;
import org.am21.networkRMI.Lobby;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static org.am21.client.view.ClientView.*;
import static org.am21.client.view.TUI.Storage.*;

/**
 * This class is the Cli
 */
public class Cli implements View {
    private String username;
    private final ClientCommunicationController commCtrl;
    private final ClientCallBack clientCallBack;
    private boolean BABY_PROTOCOL = true;
    private boolean SEL_MODE = true;
    private boolean NOT_SEL_YET = true;
    private Boolean busy = false;
    private boolean SHOW = false;
    public Integer waitingThreads;
    public final Object chatModeLock = new Object();
    public Boolean chatMode = false;

    public final Object waiterLock = new Object();

    /**
     * Cli constructor
     *
     * @throws RemoteException when construction of ClientCallBack is failed
     */
    public Cli() throws RemoteException {
        this.clientCallBack = new ClientCallBack();
        this.clientCallBack.cli = this;
        waitingThreads = 0;
        commCtrl = new ClientCommunicationController();
        commCtrl.cli = this;
    }

    /**
     * This method is used to ask the user to insert the server's IP address
     *
     * @throws RemoteException          when the connection to the server is failed
     * @throws NotBoundException        when the connection to the server is failed
     * @throws MalformedURLException    when the connection to the server is failed
     * @throws ServerNotActiveException when the connection to the server is failed
     */
    public void init() throws MalformedURLException, NotBoundException, RemoteException, ServerNotActiveException {
        System.out.println(Storage.MYSHELFIE4);
        askToContinue();
        askConnectionType();
        if (ClientCommunicationController.isRMI) {
            askServerInfoRMI();
        } else {
            askServerInfoSocket();
        }
        askLogin();
        while (true) redirect();
    }

    /**
     * This interface ask the user to choose a type of connection between RMI or SOCKET
     */
    public void askConnectionType() {
        int option = 0;
        do {
            System.out.print("""
                    What would you like to use to contact the server? RMI or Socket?
                        1  --> RMI
                        2  --> Socket
                    Enter your choice.
                    >\040""");
            try {
                option = Integer.parseInt(readLine());
            } catch (NumberFormatException e) {
                System.out.println(Color.RED + "Invalid input. Try again. (1 or 2)" + Color.RESET);
            }

            if (option == 1) {
                ClientCommunicationController.isRMI = true;

            } else if (option == 2) {
                ClientCommunicationController.isRMI = false;
            }
        } while (option != 1 && option != 2);

    }
    //-------------------RMI----------------------------------------------------------------------------

    /**
     * RMI : TUI allows the user to insert the Server Address and Port to Connect with The Server
     */
    public void askServerInfoRMI() {
        // Determine my address
        String clientBind;
        String clientAddress;
        clientAddress = askInfo("client address", "localhost");

        // Create and set the custom socket factory
        System.setProperty("java.rmi.server.hostname", clientAddress);
        System.out.println("Your ip address is : " + clientAddress);
        int freePort;
        while (true) {
            freePort = findFreePort();
            if (freePort != -1) {
                System.out.println("Free port found: " + freePort);
                break;
            } else {
                System.out.println("Free port not found");
            }
        }
        try {
            Registry registry = LocateRegistry.createRegistry(freePort);
            UnicastRemoteObject.unexportObject(clientCallBack, true);
            IClientCallBack callbackStub = (IClientCallBack) UnicastRemoteObject.exportObject(clientCallBack, freePort);
            registry.bind("Callback", callbackStub);
            clientBind = "rmi://" + clientAddress + ":" + freePort + "/Callback";

        } catch (IOException | AlreadyBoundException e) {
            throw new RuntimeException(e);
        }

        // First connection to the server --> Obtain the path for RMI
        String defaultAddress = "localhost";
        String defaultPort = "1234";
        HashMap<String, String> serverInfo;
        do {
            serverInfo = connectToServerLobby(askInfo("server address", defaultAddress), askInfo("port", defaultPort));
            if (serverInfo != null) {
                break;
            }
            System.out.println("Server not found");
        } while (true);
        ClientCommunicationController.iClientInputHandler = getControllerStub(serverInfo);
        commCtrl.registerCallBack(clientBind);
        System.out.println("Controller registered from " + serverInfo.get("address")
                           + ":" + serverInfo.get("port"));
    }


    /**
     * This method is used to find a free port
     *
     * @return the free port
     */
    public int findFreePort() {
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(0);
            return socket.getLocalPort();
        } catch (IOException e) {
            return -1;
        } finally {

            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println(Color.RED + "Socket not closed" + Color.RESET);
                }
            }
        }
    }

    /**
     * This method is used to connect to the server
     *
     * @param address the server's IP address
     * @param port    the server's port
     * @return the server's info
     */
    public HashMap<String, String> connectToServerLobby(String address, String port) {
        HashMap<String, String> infoMap;
        try {
            Registry registry = LocateRegistry.getRegistry(address, Integer.parseInt(port));
            Lobby lobby = (Lobby) registry.lookup("Welcome");
            infoMap = lobby.connect();
        } catch (AlreadyBoundException | NotBoundException | RemoteException | MalformedURLException e) {
            return null;
        }
        return infoMap;
    }

    /**
     * This method is used to get the controller's stub
     *
     * @param serverInfo the server's info
     * @return the controller's stub
     */
    public IClientInput getControllerStub(HashMap<String, String> serverInfo) {
        try {
            System.out.println("Server path to stub: " + "rmi://" + serverInfo.get("address") + ":"
                               + serverInfo.get("port") + "/" + serverInfo.get("root"));
            return (IClientInput) Naming.lookup("rmi://" + serverInfo.get("address") + ":"
                                                + serverInfo.get("port") + "/" + serverInfo.get("root"));

        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    //--------------------SOCKET----------------------------------------------------------------------------

    /**
     * This method is used to connect to the server with socket
     */
    public void askServerInfoSocket() {
        SocketClient socket;
        do {
            String defaultAddress = SocketClient.defaultServerName;
            String defaultPort = String.valueOf(SocketClient.defaultServerPort);
            socket = new SocketClient();
            SocketClient.serverName = askInfo("server address", defaultAddress);
            SocketClient.serverPort = Integer.parseInt(askInfo("server port", defaultPort));
            SocketClient.cli = this;
        } while (!SocketClient.connectToServer());
        socket.start();
        delayer(500);
    }


    //-------------------------------------------------------------

    /**
     * Registration of the User in the Game Server and association to CallBack
     *
     * @throws ServerNotActiveException when Server is not Active
     * @throws RemoteException          when Remote Invocation fails
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
            waitingThreads++;
            if (commCtrl.logIn(username, clientCallBack)) {
                this.username = username;
                return;
            } else {
                System.out.println(Color.RED + "This nickname is already taken. Choose another one." + Color.RESET);
            }
        }
    }
    //------------------------GAME MENUS ----------------------------------------

    /**
     * A switcher used to move through the 3 MAIN STATE of the CLI:
     * MENU, WAITING ROOM, GAMEPLAY
     *
     * @throws ServerNotActiveException when Server is not Active
     * @throws RemoteException          when Remote Invocation fails
     */
    public void redirect() throws ServerNotActiveException, RemoteException {
        if (ClientView.MATCH_END) {
            goToEndRoom();
        }
        if (ClientView.MATCH_START) {
            showMatchSetup();
            ClientView.setMatchStart(false);
        }
        if (GO_TO_MENU)
            askMenuAction();
        else if (!GAME_ON)
            askWaitingAction();
        else askPlayerMove();

    }

    /**
     * Method used to show the Menu of the Game
     *
     * @throws ServerNotActiveException when Server is not Active
     * @throws RemoteException          when Remote Invocation fails
     */
    @Override
    public void askMenuAction() throws ServerNotActiveException, RemoteException {
        waitingThreads = 0;
        showLostMessages();
        System.out.println(Storage.menuOption);
        showRandomTip();
        System.out.print("-----------------------------------------------------------\n" +
                         "Enter the Command you wish to use: ");
        String option = readLine();
        if (option.startsWith("/chat")) {
            handleChatMessage(option, false);
        } else if (option.startsWith("/open")) {
            askChat(option);
        } else {
            switch (option) {
                case "" -> {
                }
                case "create", "c", "cr" -> askCreateMatch();
                case "join", "j", "jo" -> askJoinMatch();
                case "online", "on", "open", "op" -> showOnlinePlayer();
                case "exit", "ex" -> {
                    if (askExitGame()) return;
                }
                case "help", "h", "he" -> askHelp();

                default ->
                        System.out.println(Color.RED + "The [" + option + "] command cannot be found! Please try again."
                                           + Color.RESET);
            }
        }
        setNeedToRefresh(false);
    }

    /**
     * Showcase the Commands available during Waiting Players
     */
    public void askWaitingAction() {
        showLostMessages();
        System.out.print("-----------------------------------------------------------\n" +
                         Color.WHITE_BRIGHT + " {| Room " + matchID + " |}" + Color.RESET);
        System.out.println(Color.WHITE_BOLD + "   [ Admin: " + admin + " ]" + Color.RESET);
        System.out.println(Storage.waitingAction);
        showRandomTip();
        System.out.print("-----------------------------------------------------------\n"
                         + "Enter the Command you wish to use: ");
        String option = readLine();
        setBusy(true);
        if (option.startsWith("/chat")) {
            handleChatMessage(option, false);
        } else if (option.startsWith("/open")) {
            askChat(option);
        } else {
            switch (option) {
                case "" -> {
                }
                case "rules", "ru" -> showGameRules();
                case "online", "on", "open", "op" -> showOnlinePlayer();
                case "settings", "se" -> askSettings();
                case "more", "mo" -> askMoreOptions();
                default -> System.out.println(Color.RED + "The [" + option + "] cannot be found! Please try again."
                                              + Color.RESET);
            }

        }
        setNeedToRefresh(false);
        setBusy(false);
    }

    /**
     * Show a different Menu based on the C
     */
    private void showCommandMenu() {
        if (SHOW) {
            System.out.println(listObjects);
            SHOW = false;
            return;
        }
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

    /**
     * This method is used to ask the Player to choose the Action he wants to do
     */
    @Override
    public void askPlayerMove() {
        showLostMessages();
        showDisplay();
        showWhoIsPlaying();
        showCommandMenu();
        showRandomTip();
        System.out.print("""
                -----------------------------------------------------------
                Enter the command you wish to use:\040""");
        String option = readLine();
        //System.out.print("\033[1A");
        //System.out.print("\033[2K");
        setBusy(true);
        if (option.startsWith("/chat")) {
            handleChatMessage(option, false);
        } else if (option.startsWith("/open")) {
            askChat(option);
        } else {
            switch (option) {
                case "" -> {
                }
                case "select", "se" -> askSelection();
                case "clear", "cl" -> askDeselection();
                case "insert", "in" -> askInsertion();
                case "show", "sh" -> {
                    SHOW = true;
                    //askShowObject();
                }
                case "more", "mo" -> askMoreOptions();
                case "open", "op" -> askPrivateChat();
                case "hand", "ha" -> {
                    showHand();
                    askToContinue();
                }
                case "pgoal", "pg" -> {
                    showPersonalGoal();
                    askToContinue();
                }
                case "cgoal", "cg" -> showCommonGoals();
                case "shelf", "sf" -> {
                    showPlayerShelf();
                    askToContinue();
                    showGroupPointsTable();
                    askToContinue();
                }
                case "board", "bo" -> showBoard();
                case "stats", "st" -> {
                    showPlayersStats();
                    askToContinue();
                }
                case "rules", "ru" -> showGameRules();
                case "end", "en" -> showEndGameToken();
                case "online", "on" -> showOnlinePlayer();
                case "timer", "ti" -> showTimer();
                default -> System.out.println(Color.RED + "The [" + option + "] cannot be found! Please try again."
                                              + Color.RESET);
            }
        }
        setNeedToRefresh(false);
        setBusy(false);
    }

    /**
     * This method is used to mark the end of the match
     */
    public void goToEndRoom() {
        System.out.println(Color.WHITE_BOLD_BRIGHT + "The match ended. Press 'Enter' to see the results" + Color.RESET);
        readLine();
        showGameResults();
        setMatchEnd(false);
    }

    //----------------------------------------------------------------------------------------------

    /**
     * This method is used to Help to the Player
     */
    public void askHelp() {
        busy = true;
        System.out.println();
        System.out.print("""
                [Commands] Choose an option:
                    1      --> Chat Commands Help
                    2      --> General Commands
                    3      --> Assist Mode
                Type the number you wish to select.
                -------------------------------------------
                >\040""");
        String num = readLine();
        switch (num) {
            case "1" -> {
                System.out.println(chatHelp);
                askToContinue();
            }
            case "2" -> {
                System.out.println(commandHelp);
                askToContinue();
            }
            case "3" -> askAssistMode();
            default -> {
            }
        }
    }

    /**
     * Ask the user if he wants to activate or deactivate the Assist Mode
     */
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
     * This Method is used to setting the number of players who can play in this match
     */
    public void askSettings() {
        System.out.print("""
                [Commands] Commands available to change settings:
                  size     --> Change the number of players who can play in this match
                Enter the command you wish to use: \040""");
        String setting = readLine();
        switch (setting) {
            case "size", "si" -> {
                if (commCtrl.changeMatchSeats(askTheIndex("MAX number of players", 2, 4))) {
                    System.out.println("Number of Seats available changed");
                } else {
                    System.out.println("Operation failed: Only the admin are allowed to change settings");
                }
                delayer(1000);
            }

            default -> {
            }

        }

    }

    //---------------------------------------------------------------------------------------------------

    /**
     * This method is used to create a new match
     */
    @Override
    public boolean askCreateMatch() {
        int playerNumber = askMaxSeats();
        if (commCtrl.createMatch(playerNumber)) {
            return true;
        }
        return false;
    }

    /**
     * Ask the user to select the number of players for the match
     */
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

    /**
     * This method is used to join a match
     */
    @Override
    public boolean askJoinMatch() {
        if (matchList == null || matchList.length == 0) {
            System.out.println(Color.RED + "No Match available. Create a new one." + Color.RESET);
            delayer(1000);
            return false;
        } else {
            boolean noGame = false;
            for (String[] strings : matchList) {
                if (!strings[1].equals("Closed")) {
                    noGame = true;
                }

            }
            if (!noGame) {
                return false;
            }
        }

        int matchID;

        try {
            String value;
            do {
                System.out.println();
                System.out.println("---------------------------------------------");
                showMatchList();
                System.out.print("Please enter the room number: ");
                value = readLine();
            } while (value.equals(""));
            matchID = Integer.parseInt(value);


            System.out.println("Selected Room [" + matchID + "].");
            waitingThreads++;
            if (commCtrl.joinGame(matchID)) {
                return true;
            } else {
                System.out.println(Color.RED + "Invalid number! Please try again." + Color.RESET);
            }
        } catch (NumberFormatException e) {
            System.out.println(Color.RED + "Invalid input! Please try again." + Color.RESET);
        }
        return false;
    }

    /**
     * This method is used to leave a match
     */
    @Override
    public boolean askLeaveMatch() {
        return commCtrl.leaveMatch();
    }

    /**
     * This method is used to exit the game
     */
    @Override
    public boolean askExitGame() {
        if (commCtrl.exitGame()) {
            System.exit(0);
            return true;
        }
        return false;
    }

    /**
     * This method is used to ask the user to get more options
     */
    public void askMoreOptions() {
        String command;
        System.out.print(Storage.MoreOptions);
        command = readLine();
        switch (command) {
            case "leave", "le" -> askLeaveMatch();
            case "exit", "ex" -> askExitGame();
            case "help", "h", "he" -> askHelp();
            case "" -> {
            }
            default -> System.out.println(Color.RED + "The [" + command + "] cannot be found! Please try again."
                                          + Color.RESET);
        }
    }

    //------------SHOW---------------------------------------------------------------------------------

    /**
     * This method is used to show the match list
     */
    @Override
    public void showMatchList() {
        System.out.println("Match List:");
        if (matchList == null || matchList.length == 0) System.out.println("No Match Found");
        else {
            for (int i = 0; i < matchList.length; i++) {
                if (matchList[i][0] == null) {
                    continue;
                }
                System.out.println("[ID: " + matchList[i][0] + " | " + matchList[i][1] + " | Players: (" + matchList[i][2] + "/" + matchList[i][3] + ")]");
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
        showBoard();
        delayer(1000);
        showCommonGoals();
        showPersonalGoal();
        delayer(1000);
        System.out.println(Color.BLUE_BOLD + "The match has started!" + Color.RESET);
        announceCurrentPlayer();
        delayer(1000);
    }

    /**
     * This method is used to show the common goals
     */
    @Override
    public void showCommonGoals() {
        System.out.println("Common Goals:");
        for (int i = 0; i < ClientView.commonGoal.size(); i++) {
            showGoalDescription(ClientView.commonGoal.get(i));
            System.out.println(Color.YELLOW + "> Top score of this Common Goal: " + Color.RESET +
                               ClientView.commonGoalScore.get(i) + " points.");
            System.out.println();
            //askToContinue();
            delayer(1000);
        }
    }

    /**
     * This method is used to show the personal goals
     */
    @Override
    public void showPersonalGoal() {
        System.out.println(username + "'s PersonalGoal:");
        System.out.println(Storage.goalPersonalMap.get(ClientView.personalGoal));
        System.out.println(Storage.PGTable);
        showPersonalPoints();
        System.out.println();
    }

    /**
     * This method is used to announce the current player
     */
    @Override
    public void announceCurrentPlayer() {
        if (currentPlayer.equals(username)) {
            System.out.print(Color.RED + "[!] > Hey " + username + "!!! It's your turn!!!" + Color.RESET);
        } else {
            System.out.print(Color.CYAN + "It's " + currentPlayer + "'s turn." + Color.RESET);
        }
        System.out.println();
    }

    /**
     * This method is used to show the current player
     */
    @Override
    public void showWhoIsPlaying() {
        String tmp;
        System.out.print("> Player's Turn: ");
        for (String name : ClientView.playersList) {
            tmp = name;
            if (name.equals(username)) {
                tmp = "You";
            }
            if (name.equals(currentPlayer)) {
                System.out.print(Color.YELLOW_BRIGHT + " [  " + tmp + "  ] " + Color.RESET);
            } else {
                System.out.print(" [  " + tmp + "  ] ");
            }
        }
        System.out.println();
    }

    /**
     * This method is used to show the player's shelf
     */
    @Override
    public void showPlayerShelf() {

        String[][] shelf = ClientView.shelves.get(ClientView.playersList.indexOf(username));
        System.out.println("----------------------------------------------------");
        System.out.println("Your Shelf:");
        System.out.println();
        for (int i = 0; i < Storage.SHELF_ROW; i++) {
            for (int j = 0; j < Storage.SHELF_COLUMN; j++) {
                String item = shelf[i][j] == null ? "_________._" : checkColorItem(shelf[i][j]);
                System.out.print("[" + item + "]");
            }
            System.out.println();
        }
        for (int j = 1; j <= Storage.SHELF_COLUMN; j++) {
            System.out.print("      " + j + "      ");
        }
        System.out.println();
    }

    /**
     * This method is used to show the player's group points
     */
    public void showGroupPointsTable() {
        System.out.println("Group Points Table: ");
        System.out.print(Storage.GROUP_POINTS);
        System.out.println(Color.YELLOW + "--Tip: See Game Rules for more info" + Color.RESET);
    }

    /**
     * This method is used to show the every player's shelf
     */
    @Override
    public void showEveryShelf() {
        for (int k = 0; k < ClientView.shelves.size(); k++) {
            String[][] userShelf = ClientView.shelves.get(k);
            System.out.println(ClientView.playersList.get(k) + "'s Shelf:");
            System.out.println();
            for (int i = 0; i < Storage.SHELF_ROW; i++) {
                for (int j = 0; j < SHELF_COLUMN; j++) {
                    String item = userShelf[i][j] == null ? "_________._" : checkColorItem(userShelf[i][j]);
                    System.out.print("[" + item + "]");
                }
                System.out.println();
            }
            for (int j = 1; j <= SHELF_COLUMN; j++) {
                System.out.print("      " + j + "      ");
            }
            System.out.println();
        }
    }

    /**
     * This method is used to show the board
     */
    @Override
    public void showBoard() {
        String[][] board = ClientView.virtualBoard;
        System.out.println("Board:");
        System.out.print("    ");
        for (int j = 1; j <= Storage.BOARD_COLUMN; j++) {
            System.out.print("      " + j + "       ");
        }
        System.out.println();
        for (int i = 0; i < Storage.BOARD_ROW; i++) {
            System.out.print(Color.WHITE_BOLD_BRIGHT + " " + (i + 1) + "  " + Color.RESET);
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
            System.out.println(Color.WHITE_BOLD_BRIGHT + String.valueOf(i + 1) + Color.RESET);
        }
        System.out.print("    ");
        for (int j = 1; j <= Storage.BOARD_COLUMN; j++) {
            System.out.print("      " + j + "       ");
        }
        System.out.println();
    }

    /**
     * This method is used to check the color of the item
     *
     * @param item is the item to check
     * @return the item with the right color
     */
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

    /**
     * This method is used to show the player's stats
     */
    @Override
    public void showPlayersStats() {
        List<String> playerList = playersList;
        List<Integer> scoreList = ClientView.scores;
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
     * This method forward a request directly to the Server
     * --> It's going to display a list of online Players
     */
    @Override
    public void showOnlinePlayer() {
        do {
            System.out.println("------------------------------------------");
            System.out.println("List of online players:");
            if (onlinePlayers == null) System.out.println("No player online");
            else {
                for (int i = 0; i < onlinePlayers.length; i++) {
                    if (onlinePlayers[i][0] == null || onlinePlayers[i][1] == null) {
                        continue;
                    }
                    System.out.println("[" + onlinePlayers[i][0] + " | " + onlinePlayers[i][1] + " ]");

                }
            }
            System.out.println();
            //setBusyFalse();
        } while (askPrivateChat());

    }

    /**
     * This method is used to show the goal description
     */
    @Override
    public void showGoalDescription(String commonGoalCard) {
        System.out.println(Storage.goalCommonMap.get(commonGoalCard));

    }

    /**
     * This method allows the user to read the GAME RULES
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

        System.out.println(Color.YELLOW_BOLD + "Common Goals:");
        for (String s : commonGoalList) {
            showGoalDescription(s);
        }
        System.out.println();
        askToContinue();
    }
    //-----------------------------------------------------------------------------------------------------------------

    /**
     * This method shows the Selection menu, one of the MAIN PHASES of the game
     */
    @Override
    public void askSelection() {
        System.out.println("Select a cell on the board");
        boolean selectionConfirm;
        do {
            List<Integer> coordinates = askCoordinates();
            int row = coordinates.get(0) - 1;
            int column = coordinates.get(1) - 1;
            if (commCtrl.selectCell(row, column)) {
                NOT_SEL_YET = false;
                System.out.println(Color.YELLOW + "Item selected: " +
                                   showItemInCell(row, column).replace(">", "").
                                           replace("_", "") + Color.RESET);
                showHand();
            }
            //askToContinue();
            delayer(750);
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
        coordinates.add(askTheIndex("ROW", 1, Storage.BOARD_ROW));
        coordinates.add(askTheIndex("COLUMN", 1, Storage.BOARD_COLUMN));
        return coordinates;
    }

    /**
     * This method shows a sub menu of the Deselection menu
     */
    @Override
    public void askDeselection() {
        if (BABY_PROTOCOL && !showHand()) {
            System.out.println(Color.RED + "You have not selected any card yet." + Color.RESET);
        } else {
            System.out.print(Storage.deselectConfirm);

            boolean deselectConfirm = "y".equals(readLine());
            if (deselectConfirm) {
                if (commCtrl.deselectCards()) {
                    NOT_SEL_YET = true;
                }
            }
        }

    }

    /**
     * This method shows the Insertion menu, one of the MAIN PHASES of the game
     */
    @Override
    public void askInsertion() {
        if (showHand()) {
            System.out.print(Storage.insertionConfirm);
            boolean confirm = "y".equals(readLine());
            if (confirm) {
                if (commCtrl.confirmSelection()) {
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
                                    if (commCtrl.insertInColumn(column - 1)) {
                                        showPlayerShelf();
                                        System.out.println(Color.YELLOW + "Inserted in the column: " + column + Color.RESET);
                                        NOT_SEL_YET = true;
                                        SEL_MODE = false;
                                        if (commCtrl.endTurn()) {
                                            System.out.println(Color.YELLOW + "- End Turn -" + Color.RESET);
                                        }
                                        delayer(1000);
                                        return;
                                    }
                                }
                            }
                            case "more", "mo" -> askMoreOptions();
                            default ->
                                    System.out.println(Color.RED + "The [" + option + "] cannot be found! Please try again."
                                                       + Color.RESET);
                        }
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
        delayer(1000);
    }

    /**
     * This method is used to ask the player to sort the cards in his hand
     */
    public void askSort() {
        System.out.println("There are the cards you have selected:");
        if (showHand()) {
            System.out.println("Which position do you wish to swap?");
            List<Integer> itemSwapped = askIndex();
            if (itemSwapped == null) {
                System.out.println("Selection canceled.");
            } else if (!itemSwapped.get(0).equals(itemSwapped.get(1))) {
                if (commCtrl.sortHand(itemSwapped.get(0), itemSwapped.get(1))) {
                    System.out.println("Card order changed.");
                }
            } else {
                System.out.println("Same position. Swap failed.");
            }
        } else {
            System.out.println(Color.RED + "You can’t sort cards if you did not select any cards!" + Color.RESET);
        }
    }

    /**
     * This method is used to ask the card index to swap
     */
    public List<Integer> askIndex() {
        String sortConfirm;
        int position1, position2;
        position1 = askTheIndex("Position1", 1, ClientView.currentPlayerHand.size());
        position2 = askTheIndex("Position2", 1, ClientView.currentPlayerHand.size());
        System.out.println("You have chosen to swap " +
                           ClientView.currentPlayerHand.get(position1 - 1).replace("_", "") +
                           " and " +
                           ClientView.currentPlayerHand.get(position2 - 1).replace("_", ""));
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
        column = askTheIndex("COLUMN", 1, SHELF_COLUMN);
        do {

            System.out.print("""
                                     -----------------------------------------------------------
                                     You have chosen Column:\040""" + column + "\n" + Storage.columnConfirm);

            columnConfirm = readLine();
            switch (columnConfirm) {
                case "y":
                    return (column);
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

    /**
     * This method allows the view of the Items selected by the player
     * Improper use of the name Hand, It should be: temporarily list of item selected on the board
     *
     * @return true if the "hand" is not empty, otherwise false
     */
    public boolean showHand() {
        System.out.print(ClientView.currentPlayer + " has selected: ");
        if (ClientView.currentPlayerHand.isEmpty()) {
            System.out.println("Nothing");
            return false;
        }
        for (int i = 0; i < ClientView.currentPlayerHand.size(); i++) {
            System.out.print((i + 1) + ".[" +
                             checkColorItem(ClientView.currentPlayerHand.get(i)).replace("_", "") + "]  ");
        }
        System.out.println();
        return true;
    }

    /**
     * This method is used to show the items in the player's shelf
     *
     * @param row    the row of the shelf
     * @param column the column of the shelf
     * @return the item in the shelf
     */
    public String showItemInCell(int row, int column) {
        return ClientView.virtualBoard[row][column];
    }

    /**
     * This method is used to show the end game token
     */
    @Override
    public void showEndGameToken() {
        if (ClientView.endGameToken) {
            System.out.println("The EndGame Token is still available. Good Luck!");
        } else {
            System.out.println("The EndGame Token is taken, it's the last round!");
        }
        System.out.println();
    }

    /**
     * This method is used to show the player's personal points
     */
    public void showPersonalPoints() {
        int index = ClientView.getPlayerIndex(username);
        int points = ClientView.hiddenPoints.get(index);
        System.out.println("Personal Goal's points (Private): " + points);

    }

    /**
     * This method convert the gameResults in the TUI view format
     */
    public void showGameResults() {
        if (ClientView.gameResults == null || ClientView.gameResults.length == 1) {
            System.out.println("No Results");
            askToContinue();
        } else {
            List<String> gameResultsTmp = new ArrayList<>(ClientView.gameResults.length);

            for (int i = 0; i < ClientView.gameResults.length - 1; i++) {
                String line =
                        "* " + ClientView.gameResults[i][0] + " Score:\n" +
                        "+ " + ClientView.gameResults[i][1] + " Common points\n" +
                        "+ " + ClientView.gameResults[i][2] + " Personal points\n" +
                        "+ " + ClientView.gameResults[i][3] + " Group points\n";
                if (ClientView.gameResults[i][4] != null) {
                    line += "+ 1 Endgame Token\n";
                }
                line += "Total: " + ClientView.gameResults[i][5] + "\n";

                gameResultsTmp.add(i, line);

            }
            String winner = "\n No winner for this match!\n";
            if (ClientView.gameResults[ClientView.gameResults.length - 1][0] != null) {
                winner = "\n The winner is " + ClientView.gameResults[ClientView.gameResults.length - 1][0] + "!!\n";
            }
            gameResultsTmp.add(ClientView.gameResults.length - 1, winner);

            for (String r : gameResultsTmp) {
                System.out.println(r);
                askToContinue();
            }

        }
    }

    /**
     * This method is used to show the timer (not implemented)
     */
    @Override
    public void showTimer() {

    }

    /**
     * This is a sub Menu used for showcase the objects of the game,
     * like Board, Shelf, Goals....
     */
    @Override
    public void askShowObject() {
        String object = "new_command";
        while (!object.equals("")) {
            System.out.print(Storage.listObjects);
            object = readLine();
            setBusy(true);
            switch (object) {
                case "open", "op" -> askPrivateChat();
                case "hand", "ha" -> showHand();
                case "pgoal", "pg" -> {
                    showPersonalGoal();
                    askToContinue();
                }
                case "cgoal", "cg" -> showCommonGoals();
                case "shelf", "sh" -> {
                    showPlayerShelf();
                    askToContinue();
                    showGroupPointsTable();
                    askToContinue();
                }
                case "board", "bo" -> showBoard();
                case "stats", "st" -> showPlayersStats();
                case "rules", "ru" -> showGameRules();
                case "end", "en" -> showEndGameToken();
                case "online", "on" -> showOnlinePlayer();
                case "timer", "ti" -> showTimer();
                case "" -> {
                    setBusy(false);
                    return;
                }
                default -> System.out.println(Color.RED + "The [" + object + "] cannot be found! Please try again."
                                              + Color.RESET);
            }
            delayer(750);
        }
        setBusy(false);
    }


    /**
     * This Method is used for DECORATION of TUI.
     * Shows a Random Tip for the user
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
    //------------------------DISPLAY----------------------------------

    /**
     * The first idea was to display in real time the progression of the Game
     * to ONLY the player that were not playing, so while they were waiting they could watch at something more lively
     * But then it was extended to the Current Player display too
     */
    public void showDisplay() {
        System.out.println();
        Storage.reset_display();
        displayMiniBoard();
        displayMiniShelf();
        if (SEL_MODE) displayMiniHand();
        displayMiniPGoal(ClientView.personalGoal);
        displayMiniCGoal();
        for (int i = 0; i < Storage.current_display.size(); i++) {
            System.out.println(Storage.current_display.get(i));
        }
    }

    /**
     * This method is used for decoration of the TUI
     */
    public void displayMiniBoard() {
        List<String> display = Storage.current_display;
        String[][] board = ClientView.virtualBoard;

        display.set(0, display.get(0) + "\t\t\t{Board}\t\t\t\t\t\t\t");
        for (int i = 0; i < Storage.BOARD_ROW; i++) {
            display.set(i + 1, display.get(i + 1) + (i + 1));

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
            display.set(i + 1, display.get(i + 1) + (i + 1) + "|\t");
        }
        display.set(10, display.get(10) + "  ");

        for (int j = 1; j <= Storage.BOARD_COLUMN; j++) {
            display.set(10, display.get(10) + " " + j + "  ");
        }
        display.set(10, display.get(10) + "   ");
        Storage.current_display = display;
    }

    /**
     * This method is used to give a color to the item
     *
     * @param item is the item to be colored
     * @return the colored item
     */
    public String giveMeColor(String item) {
        String itemType = item;
        switch (item.substring(0, item.length() - 3)) {
            case "__Cats__" -> itemType = "\033[1;92m*\033[0m";
            case "_Books__" -> itemType = "\033[1;97m*\033[0m";
            case "_Games__" -> itemType = "\033[1;93m*\033[0m";
            case "_Frames_" -> itemType = "\033[1;94m*\033[0m";
            case "Trophies" -> itemType = "\033[1;96m*\033[0m";
            case "_Plants_" -> itemType = "\033[1;95m*\033[0m";
        }
        return itemType;

    }

    /**
     * This method is used for decoration of the TUI
     */
    public void displayMiniShelf() {
        String[][] shelf = ClientView.shelves.get(playersList.indexOf(username));
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
        for (int j = 1; j <= SHELF_COLUMN; j++) {
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
            if (i < ClientView.currentPlayerHand.size()) {
                String item = giveMeColor(ClientView.currentPlayerHand.get(i));
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
        for (int j = 1; j <= SHELF_COLUMN; j++) {
            display.set(10, display.get(10) + " " + j + " ");
        }
        display.set(10, display.get(10) + "  \t");

    }


    /**
     * This method is used for decoration of the TUI
     */
    public void displayMiniCGoal() {
        List<String> display = Storage.current_display;
        List<String> cGoal = ClientView.commonGoal;
        List<Integer> scores = ClientView.commonGoalScore;

        for (int i = 0; i < cGoal.size(); i++) {
            display.set(i, display.get(i) + "{" + cGoal.get(i) + "}" + ": \t[" + scores.get(i) + "]");
        }
    }


//--------------------------CHAT--------------------------------------------------

    /**
     * Show the list of chat with players who is available
     *
     * @return true to refresh, false to exit
     */
    public boolean askPrivateChat() {
        List<String> playersList = convertPlayersNames(onlinePlayers);
        int num = 1;
        System.out.println("[ N ] Private Chats List:");
        for (String x : playersList) {
            System.out.println("  " + num + "  --> " + x);
            num++;
        }
        System.out.println("  0  --> Exit");
        System.out.println("Enter the number of the player you wish to chat with:");
        int value = playersList.size() + 1;
        do {
            try {
                System.out.print("> ");
                String tmp = readLine();
                if (tmp.equals("")) {
                    return true;
                }
                value = Integer.parseInt(tmp);

            } catch (NumberFormatException nE) {
                System.out.println("Wrong input. Try with a valid number");
            }
            if (value == 0) {
                return false;
            }
        } while (value > playersList.size());

        //Valid value
        askChat("/open[" + playersList.get(value - 1) + "]");
        return false;

    }

    /**
     * convert players list from virtual view format to Java List format
     *
     * @param playersMap client virtual view
     * @return list of players name
     */
    private List<String> convertPlayersNames(String[][] playersMap) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < playersMap.length; i++) {
            if (playersMap[i][0].equals(username)) {
                continue;
            }
            list.add(playersMap[i][0]);
        }
        return list;
    }

    /**
     * Sending a message in a specific chat (public or private)
     *
     * @param command command includes message and, in case, receiver
     * @param live    true if the message is sent in live chat
     */
    @Override
    public void handleChatMessage(String command, boolean live) {
        String message = command.substring(command.indexOf(" ") + 1);
        String usernameString = command.substring(5);
        String regex = "\\[|\\]";
        String[] matches = usernameString.split(regex);
        if (matches.length > 1) {
            String playerName = matches[1];

            if (commCtrl.sendPrivateMessage(message, playerName, live)) {
                System.out.println("Message sent to: " + playerName);
            } else {
                System.out.println("Message not sent");
            }
        } else {

            if (!commCtrl.sendPublicMessage(message, live)) {
                System.out.println(Color.RED + "The message was not sent" + Color.RESET);
            } else {
                System.out.println("Message sent");
            }
        }
        delayer(500);
    }

    /**
     * Print match group chat from ClientView
     */
    public void printPublicChat() {
        System.out.println();
        if (publicChat == null) {
            System.out.println("\"No records\"");
            return;
        }
        for (String line : publicChat) {
            System.out.println(line);
        }

    }

    /**
     * Print the private Chat
     *
     * @param index index of the private chat in ClientView.privateChats
     */
    public void printPrivateChat(int index) {
        System.out.println();
        if (privateChats == null || privateChats.size() <= index || index == -1) {
            System.out.println("\"No records\"");
            return;
        }
        for (String line : privateChats.get(index)) {
            System.out.println(line);
        }
    }

    /**
     * Open chat (public or private)
     * OpenChat Command: /open[name]
     *
     * @param command the message and, in case, receiver name
     */
    public void askChat(String command) {
        //String message = command.substring(command.indexOf(" ") + 1);
        String usernameString = command.substring(5);
        String regex = "\\[|\\]";
        String[] matches = usernameString.split(regex);
        if (matches.length > 1) {
            String playerName = matches[1];
            System.out.println("Opening Chat with " + playerName);
            delayer(500);
            setChatMode(true);
            while (chatMode) {
                printPrivateChat(chatMap.getOrDefault(getChatKey(username, playerName), -1));
                System.out.println("Type /exit to close this chat");
                System.out.print("> ");
                String live_message = readLine();
                if (live_message.equals("/exit")) {
                    setChatMode(false);

                } else if (!live_message.equals("")) {
                    commCtrl.sendPrivateMessage(live_message, playerName, true);
                }
            }

        } else {
            if (GO_TO_MENU && !MATCH_END) {
                //If the user is not in a match
                System.out.println(Color.RED + "The group chat is not available. Try to create or join a match." + Color.RESET);
                delayer(1000);
                return;
            }

            //Enter chat mode
            setChatMode(true);
            while (chatMode) {
                printPublicChat();
                System.out.println("--Type /exit to close this chat");
                System.out.print("> ");
                String live_message = readLine();
                if (live_message.equals("/exit")) {
                    //EXIT chat mode
                    setChatMode(false);

                } else if (!live_message.equals("")) {
                    commCtrl.sendPublicMessage(live_message, true);
                }
            }
        }
    }

    /**
     * Method to get the Chat Key
     *
     * @param name1 player 1 name
     * @param name2 player 2 name
     * @return key for ClientView.chatMap
     */
    private String getChatKey(String name1, String name2) {
        return (name1.compareTo(name2)) < 0 ? (name1 + "@" + name2) : (name2 + "@" + name1);
    }

    public List<String> lostMessages = new ArrayList<>();

    /**
     * This method is used to add a message in the lostMessages list
     *
     * @param message message to add
     */
    public void addMessageInLine(String message) {
        synchronized (lostMessages) {
            lostMessages.add(message);
        }
    }

    /**
     * This method is used to show the lost messages
     */
    public void showLostMessages() {
        System.out.println();

        synchronized (lostMessages) {
            if (lostMessages.size() < 1)
                return;
            System.out.print(Color.RED + "Received Messages :" + Color.RESET);
            for (String line : lostMessages) {
                System.out.print(line);
                delayer(500);
            }
            System.out.println();
            delayer(1000);
            lostMessages.clear();
        }
    }

    //------------------------------CLI tools-------------------------------------------
    public final Object busyLock = new Object();

    /**
     * This method is used to set the busy status
     *
     * @param value the value to set
     */
    public void setBusy(Boolean value) {
        synchronized (busyLock) {
            busy = value;
        }
    }

    /**
     * This method is used to ask the user to press enter to continue
     */
    public void askToContinue() {
        System.out.print("Press 'Enter' to continue");
        readLine();
        System.out.println("-----------------------------------------------------------");
    }

    /**
     * This method is used to print a message
     *
     * @param message the message to print
     */
    public void printer(String message) {
        System.out.println(message);
    }

    /**
     * This method is used to delayer the execution
     *
     * @param milliseconds the milliseconds to wait
     */
    public void delayer(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to check if it's the user's turn
     * If it's not, it will wait until it's his turn
     */
    public void checkTurn() {
        SEL_MODE = ClientView.currentPlayer.equals(username);
    }

    /**
     * This method is used to ask the communication info
     *
     * @param type         the type of info to ask
     *                     "IP" or "PORT"
     *                     "IP" is the IP address of the server
     *                     "PORT" is the port of the server
     *                     <p>
     *                     If the user press enter, the default value will be used
     *                     If the user insert an invalid value, he will be asked again
     * @param defaultValue the default value to use
     *                     If the user press enter, this value will be used
     *                     If the user insert an invalid value, he will be asked again
     * @return the value inserted by the user
     */
    public String askInfo(String type, String defaultValue) {

        do {
            System.out.print("Enter the " + type + " [Press 'Enter' for " + defaultValue + "]: ");
            String value = readLine();
            String[] fragments = value.split("\\.");

            if (value.equals("")) {
                return defaultValue;
            } else if (type.endsWith("address") && fragments.length == 4) {
                return value;
            } else if (type.equals("port")) {
                return value;
            } else {
                System.out.println(Color.RED + "Invalid " + type + "!" + Color.RESET);
            }
        } while (true);
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
                System.out.print(type + " (" + lower_limit + " to " + (upper_limit) + "): ");
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

    /**
     * This method refresh the TUI only when chatMode is true
     */
    public void refreshChat() {
        synchronized (chatModeLock) {
            if (chatMode) {
                new Thread(() -> {
                    setNeedToRefresh(true);
                }).start();
            }
        }
    }

    /**
     * This method is used to update the CLI
     *
     * @param milliseconds the milliseconds to wait
     */
    public void updateCLI(int milliseconds) {
        Thread refreshMinion = new Thread(() -> {
            synchronized (waiterLock) {
                waitingThreads++;
            }
            synchronized (busyLock) {
                if (!busy || chatMode) {
                    // Client is not using any important command
                    delayer(milliseconds);

                    setNeedToRefresh(true);
                }
            }
            synchronized (waiterLock) {
                waitingThreads = 0;
            }
        });
        synchronized (waiterLock) {
            //If there are some thread waiting then there is no need to add another one
            if (waitingThreads > 0) {
                return;
            }
        }
        refreshMinion.start();
    }

    /**
     * Read a line from the standard input
     *
     * @return the string read from the input
     */
    public String readLine() {
        FutureTask<String> futureTask = new FutureTask<>(new InputReadTask());
        Thread inputThread = new Thread(futureTask);
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

    /**
     * This method is used to set the chat mode
     * If the chat mode is true, the CLI will not be refreshed
     *
     * @param value the value to set
     */
    public void setChatMode(Boolean value) {
        synchronized (chatModeLock) {
            chatMode = value;
        }
    }


}
