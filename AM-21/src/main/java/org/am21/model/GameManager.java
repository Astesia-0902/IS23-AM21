package org.am21.model;

import org.am21.controller.GameController;
import org.am21.controller.PlayerController;
import org.am21.model.Cards.Card;
import org.am21.model.Cards.Goal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameManager {
    public static GameManager game;
    //Key: player name, Value: match id
    public static final HashMap<String, Integer> playerMatchMap = new HashMap<String, Integer>();
    public static final List<Match> matchList = new ArrayList<Match>();

    public GameManager(GameController controller) {

    }

    public static void createMatch(int playerNum, PlayerController playerController) {
        synchronized (matchList) {
            if (playerNum < 2 || playerNum > 4) {
                System.out.println("Exceeded players number limit. Try again.");
                return;
            }

            Match match = new Match(playerNum);
            match.matchID = matchList.indexOf(match);
            matchList.add(match);
            match.addPlayer(playerController.player);
        }
    }

}

