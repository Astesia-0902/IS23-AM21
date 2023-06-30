package org.am21.model;

import org.am21.controller.CommunicationController;
import org.am21.controller.GameController;
import org.am21.controller.PlayerController;
import org.am21.model.enumer.GameState;
import org.am21.model.enumer.SC;
import org.am21.model.enumer.ServerMessage;
import org.am21.model.enumer.UserStatus;
import org.am21.utilities.VirtualViewHelper;

import java.util.*;

/**
 * This class is the game manager
 */
public class GameManager {
    public static boolean serverComm = true;

    public static GameManager game;
    /**
     * Key: player name, Value: match id
     */
    public static final HashMap<String, Integer> playerMatchMap = new HashMap<>();
    public static Integer matchIndex = 0;
    public static final HashMap<Integer, Match> matchMap = new HashMap<>();
    public static final List<Player> players = new ArrayList<>();
    public static int client_connected = 0;

    /**
     * Constructor
     */
    public GameManager() {
    }

    /**
     * Add a new match to the matchMap
     *
     * @param match match instance
     * @return the match index in the match map
     */
    public static Integer pushNewMatch(Match match) {
        synchronized (matchMap) {
            matchMap.put(matchIndex, match);
            matchIndex++;
            return matchIndex - 1;
        }
    }

    /**
     * Create match
     *
     * @param playerNum        number of this match
     * @param playerController player controller of the creator
     * @return true if succeed false otherwise
     */
    public static boolean createMatch(int playerNum, PlayerController playerController) {
        synchronized (matchMap) {
            if (playerNum < 2 || playerNum > 4) {
                return false;
            }

            Match match = new Match(playerNum);
            match.matchID = pushNewMatch(match);
            match.admin = playerController.getPlayer();
            match.virtualView.setAdmin(playerController.getPlayer().getNickname());
            match.virtualView.setMatchID(match.matchID);
            match.virtualView.setMaxSeats(playerNum);
            match.addPlayer(playerController.getPlayer());

            return true;
        }
    }

    /**
     * This method check if there is a nickname is already picked by someone else.
     *
     * @param name player name
     * @return true if the name is already picked, false otherwise
     */
    public static boolean checkNameSake(String name) {
        synchronized (players) {
            for (Player p : players) {
                if (name.equals(p.getNickname())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check the player with the same id is reconnecting
     *
     * @param name player name
     * @return true if the player is reconnecting, false otherwise
     */
    public static boolean checkNameReconnection(String name) {
        synchronized (players) {
            for (Player p : players) {
                if (name.equals(p.getNickname()) && p.getStatus().equals(UserStatus.Suspended)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * remove offline player
     *
     * @param p player instance
     */
    public static void removeOfflinePlayer(Player p) {
        synchronized (players) {
            if (p.getStatus().equals(UserStatus.Offline)) {
                players.remove(p);
            }
        }

    }

    /**
     * Control if there is any Match Closed, if so destroy it
     *
     * @return true if there is a match closed, false otherwise
     */
    public static boolean gameCleaner() {
        //checkUsersConnection();
        synchronized (matchMap) {
            List<Match> toDoList = new ArrayList<>();
            for (Match m : matchMap.values()) {
                //Check if match members are all offline, if so close it
                if (m.gameState.equals(GameState.GameGoing)) {
                    boolean toDelete = false;
                    synchronized (m.playerList) {
                        for (Player p : m.playerList) {
                            // If a player is Offline, then the match need to be cleaned
                            if (p.getStatus().equals(UserStatus.Offline)) {
                                toDelete = true;
                                break;
                            }
                        }
                    }

                    if (toDelete) {
                        synchronized (m.playerList) {
                            for (Player p : m.playerList) {
                                p.setStatus(UserStatus.Online);
                                p.setMatch(null);
                                p.setShelf(null);
                                synchronized (playerMatchMap) {
                                    playerMatchMap.remove(p.getNickname());
                                }
                            }
                            m.playerList.clear();
                        }
                        m.setGameState(GameState.Closed);
                    }
                }


                //Check if match is closed
                if (m.gameState.equals(GameState.Closed)) {
                    toDoList.add(m);
                }
            }
            for (Match x : toDoList) {
                matchMap.remove(x.matchID);
                System.out.println("Removed match " + x.matchID);
            }
            if (toDoList.size() > 0) {
                VirtualViewHelper.virtualizeMatchMap();
                GameController.updatePlayersGlobalView();
                GameController.notifyAllPlayers();
                return true;
            }
        }
        return false;
    }

    /**
     * Call a generic method to test if the user is still connected.
     * If not the user status should change to Offline
     */
    public static void checkUsersConnection() {
        List<PlayerController> toRemove = new ArrayList<>();
        synchronized (players) {
            for (Player p : players) {
                if (p.getStatus() == UserStatus.Offline || p.getStatus() == UserStatus.Suspended) {
                    continue;
                }
                // Test players connection
                //Not synchronized because it will be called from the controller
                CommunicationController.instance.ping(p.getController());
                if (p.getStatus().equals(UserStatus.Offline)) {
                    toRemove.add(p.getController());
                } else if (p.getStatus().equals(UserStatus.Suspended)) {
                    //if the player suspended is the current player, then skip his turn
                    if (p.getMatch() != null && p.getMatch().gameState.equals(GameState.GameGoing) && p.getMatch().currentPlayer.equals(p)) {
                        p.getController().dropHand();
                        p.getMatch().callEndTurnRoutine();
                    }
                    p.getMatch().sendTextToAll(SC.YELLOW_BB + p.getNickname() + " suspended." + SC.RST, false);
                    checkMatchPause(p.getMatch().matchID);
                }
            }
        }

        if (!toRemove.isEmpty()) {
            // Game Cleaner
            for (PlayerController pc : toRemove) {
                if (pc.getPlayer().getMatch() != null) {
                    pc.getPlayer().getMatch().sendTextToAll("Player " + pc.getPlayer().getNickname() + " is offline, he has been removed from the match", false);
                    GameController.removePlayerFromMatch(pc, pc.getPlayer().getMatch().matchID);
                }
                removeOfflinePlayer(pc.getPlayer());
            }
            GameController.updatePlayersGlobalView();
            GameController.notifyAllPlayers();
        }

    }

    /**
     * everytime a player leave the match, check if the game need to pause
     *
     * @param matchID match index
     */
    public static void checkMatchPause(int matchID) {
        Match m = matchMap.get(matchID);
        int activePlayers = 0;
        for (Player p : m.playerList) {
            if (p.getStatus().equals(UserStatus.GameMember)) {
                activePlayers++;
            }
        }

        if (activePlayers == 1) {
            pauseMatch(matchID);
        } else if (activePlayers < 1) {
            m.setGameState(GameState.Closed);
        } else {
            matchResume(matchID);
        }
    }

    /**
     * if there were only one player, the match will pause
     *
     * @param matchID match index
     */
    private static void pauseMatch(int matchID) {
        Match m = matchMap.get(matchID);
        m.pauseMatch();
        startPauseTimer(matchID, m);
        m.sendTextToAll(ServerMessage.MatchPaused.value(), true);
        m.sendNotificationToAll(true);
    }

    /**
     * When the game paused, start timer
     *
     * @param matchID match index
     * @param m       match instance
     */
    private static void startPauseTimer(int matchID, Match m) {
        m.pauseTimer = new Timer();
        m.pauseTimer.schedule(new MatchPauseTask(matchID), 1000 * 60);
    }

    /**
     * Timer class
     */
    private static class MatchPauseTask extends TimerTask {
        private final int matchID;

        public MatchPauseTask(int matchID) {
            this.matchID = matchID;
        }

        @Override
        public void run() {
            handleMatchPauseTimeout(matchID);
        }
    }

    /**
     * If game pause timer expired, end the match
     *
     * @param matchID match index
     */
    private static void handleMatchPauseTimeout(int matchID) {
        if (matchMap.get(matchID) == null || matchMap.get(matchID).gameState.equals(GameState.Closed)) {
            return;
        }
        cancelMatchPauseTimer(matchID);
        Match m = matchMap.get(matchID);
        m.endMatch();
        System.out.println("Match " + matchID + " ended because of timeout, the last active player won.");
    }

    /**
     * cancel the match pause timer
     *
     * @param matchID match index
     */
    private static void cancelMatchPauseTimer(int matchID) {
        Match m = matchMap.get(matchID);
        if (m.pauseTimer != null) {
            m.pauseTimer.cancel();
            m.pauseTimer = null;
        }
    }

    /**
     * Resume a match that was paused
     *
     * @param matchIndex match index
     */
    public static void matchResume(int matchIndex) {
        Match m = matchMap.get(matchIndex);
        if (!m.gameState.equals(GameState.Pause))
            return;
        m.setGameState(GameState.GameGoing);
        cancelMatchPauseTimer(matchIndex);
        m.callEndTurnRoutine();
        System.out.println("Match " + matchIndex + " resumed.");
    }

    /**
     * Whenever the server has to reply to a player action with a pre-defined message
     *
     * @param pc PlayerController
     * @param m  ServerMessage
     */
    public static void sendReply(PlayerController pc, String m) {
        if (pc.getPlayer().getStatus().equals(UserStatus.Suspended) || pc.getPlayer().getStatus().equals(UserStatus.Offline)) {
            return;
        }
        if (serverComm) {
            CommunicationController.instance.sendMessageToClient(m, pc);
        }
    }


    /**
     * Whenever the server has to notify a player with a pre-defined message
     *
     * @param pc PlayerController
     * @param m  ServerMessage
     */
    public static void sendChatNotification(PlayerController pc, String m) {
        if (pc.getPlayer().getStatus().equals(UserStatus.Suspended) || pc.getPlayer().getStatus().equals(UserStatus.Offline)) {
            return;
        }
        if (serverComm) {
            CommunicationController.instance.sendChatNotification(m, pc);
        }
    }

    /**
     * notify client to update the view
     *
     * @param ctrl         PlayerController
     * @param milliseconds milliseconds to wait before update
     */
    public static void notifyUpdate(PlayerController ctrl, int milliseconds) {
        if (ctrl.getPlayer().getStatus().equals(UserStatus.Suspended) || ctrl.getPlayer().getStatus().equals(UserStatus.Offline)) {
            return;
        }
        CommunicationController.instance.notifyUpdate(ctrl, milliseconds);
    }

    /**
     * This method print the message in server console if ServerComm are active
     *
     * @param message message
     */
    public static void serverLog(String message) {
        if (serverComm) {
            System.out.println(message);
        }
    }

}

