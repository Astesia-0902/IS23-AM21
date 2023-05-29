package org.am21.model;

import org.am21.controller.CommunicationController;
import org.am21.controller.GameController;
import org.am21.controller.PlayerController;
import org.am21.model.enumer.GameState;
import org.am21.model.enumer.SC;
import org.am21.model.enumer.UserStatus;
import org.am21.utilities.VirtualViewHelper;

import java.util.*;

public class GameManager {
    public static boolean SERVER_COMM = true;

    public static GameManager game;
    /**
     * Key: player name, Value: match id
     */
    public static final HashMap<String, Integer> playerMatchMap = new HashMap<String, Integer>();
    public static final List<Match> matchList = new ArrayList<Match>();
    public static Integer matchIndex = 0;
    public static final HashMap<Integer, Match> matchMap = new HashMap<Integer, Match>();
    public static final List<Player> players = new ArrayList<>();

    //TODO: for testing
    public static int client_connected = 0;

    public GameManager(GameController controller) {
    }

    public int getNumPlayers() {
        return players.size();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }
    public static Integer pushNewMatch(Match match){
        synchronized (matchMap){
            matchMap.put(matchIndex, match);
            matchIndex++;
            return matchIndex-1;
        }
    }


    public static boolean createMatch(int playerNum, PlayerController playerController) {
        synchronized (matchMap) {
            if (playerNum < 2 || playerNum > 4) {
                return false;
            }

            Match match = new Match(playerNum);
            int matchID = pushNewMatch(match);
            match.matchID = matchID;
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
     * @param name
     * @return
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
     * Check each player from the game, if their status are Offline, they will be removed from the players list
     */
    public static void playerCleaner() {
        synchronized (players) {
            List<Player> copy = new ArrayList<>(players);
            for (Player p : copy) {
                if (p.getStatus().equals(UserStatus.Offline)) {
                    players.remove(p);
                }
            }

            VirtualViewHelper.virtualizeOnlinePlayers();
            GameController.updatePlayersGlobalView();
            GameController.notifyAllPlayers();
            System.out.println("Player cleaned");
        }
    }

    public static void removeOfflinePlayer(Player p) {
        synchronized (players) {
            if (p.getStatus().equals(UserStatus.Offline)) {
                players.remove(p);
//                VirtualViewHelper.virtualizeOnlinePlayers();
//                GameController.updatePlayersGlobalView();
            }
        }

    }

    /**
     * Control if there is any Match Closed, if so destroy it
     *
     * @return
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
                VirtualViewHelper.virtualizeMatchList();
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
                    //TODO:Suspend the player
                    //if the player suspended is the current player, then skip his turn
                    if (p.getMatch() != null && p.getMatch().gameState.equals(GameState.GameGoing) && p.getMatch().currentPlayer.equals(p)) {
                        p.getMatch().callEndTurnRoutine();
                    }
                    p.getMatch().sendTextToAll(SC.YELLOW_BB + "\nServer > " + p.getNickname() + " suspended." + SC.RST, false, false);
                    checkMatchPause(p.getMatch().matchID);
                }
            }
        }

        if (!toRemove.isEmpty()) {
            // Game Cleaner
            for (PlayerController pc : toRemove) {
                if (pc.getPlayer().getMatch() != null) {
                    pc.getPlayer().getMatch().sendTextToAll("Player " + pc.getPlayer().getNickname() + " is offline, he has been removed from the match", false, false);
                    GameController.removePlayerFromMatch(pc, pc.getPlayer().getMatch().matchID);
                }
                removeOfflinePlayer(pc.getPlayer());
            }
        }
        GameController.updatePlayersGlobalView();
        GameController.notifyAllPlayers();
    }

    private static void checkMatchPause(int matchID) {
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
            //TODO: Eliminate the match
            m.setGameState(GameState.Closed);
        }
    }

    private static void pauseMatch(int matchID) {
        Match m = matchList.get(matchID);
        m.pauseMatch();
        startPauseTimer(matchID, m);
        m.sendTextToAll(SC.YELLOW_BB + "\nServer > Match paused, waiting for other players to reconnect. If non one reconnect within 60s, the last active player will be the winner." + SC.RST, true, false);
    }

    private static void startPauseTimer(int matchID, Match m) {
        m.pauseTimer = new Timer();
        m.pauseTimer.schedule(new MatchPauseTask(matchID), 1000 * 60);
    }

    private static class MatchPauseTask extends TimerTask {
        private int matchID;

        public MatchPauseTask(int matchID) {
            this.matchID = matchID;
        }

        @Override
        public void run() {
            handleMatchPauseTimeout(matchID);
        }
    }

    private static void handleMatchPauseTimeout(int matchID) {
        //TODO:the last player should be the winner
        if (matchList.get(matchID).gameState.equals(GameState.Closed)) {
            return;
        }
        cancelMatchPauseTimer(matchID);
        Match m = matchList.get(matchID);
        m.endMatch();
        System.out.println("Match " + matchID + " ended because of timeout, the last active player won.");
    }

    private static void resetMatchPauseTimer(int matchID) {
        Match m = matchList.get(matchID);
        if (m.pauseTimer != null) {
            m.pauseTimer.cancel();
            startPauseTimer(matchID, m);
        }
    }

    private static void cancelMatchPauseTimer(int matchID) {
        Match m = matchList.get(matchID);
        if (m.pauseTimer != null) {
            m.pauseTimer.cancel();
            m.pauseTimer = null;
            m.setGameState(GameState.GameGoing);
        }
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
        if (SERVER_COMM) {
            CommunicationController.instance.sendMessageToClient(m, pc);
        }
    }


    public static void sendChatNotification(PlayerController pc, String m) {
        if (pc.getPlayer().getStatus().equals(UserStatus.Suspended) || pc.getPlayer().getStatus().equals(UserStatus.Offline)) {
            return;
        }
        if (SERVER_COMM) {
            CommunicationController.instance.sendChatNotification(m, pc);
        }
    }

    public static void notifyUpdate(PlayerController ctrl, int milliseconds) {
        if (ctrl.getPlayer().getStatus().equals(UserStatus.Suspended) || ctrl.getPlayer().getStatus().equals(UserStatus.Offline)) {
            return;
        }
        CommunicationController.instance.notifyUpdate(ctrl, milliseconds);
    }

}

