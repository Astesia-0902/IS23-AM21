package org.am21.model;

import org.am21.controller.GameController;
import org.am21.controller.PlayerController;
import org.am21.model.items.*;
import org.am21.model.items.Card.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameManager {
    //Key: player name, Value: match id
    public static HashMap<String, Integer> playerMatchMap = new HashMap<String, Integer>();

    public static List<Match> matchList;

    public static List<Goal> goals;

    public static List<Card> cards;

    private float waitTime;

    private float timer;

    public GameManager(GameController controller) {
        this.matchList = new ArrayList<Match>();
        this.goals = new ArrayList<Goal>();
        this.cards = new ArrayList<Card>();
        this.waitTime = 0;
        this.timer = 0;
    }

    public static void createMatch(int playerNum, PlayerController playerController) {

        if(playerNum < 2 || playerNum > 4){
            System.out.println("Exceeded players number limit. Try again.");
            return;
        }

        Match match = new Match(playerNum);
        match.matchID = matchList.indexOf(match);
        matchList.add(match);
        match.addPlayer(playerController.player);
    }

}

