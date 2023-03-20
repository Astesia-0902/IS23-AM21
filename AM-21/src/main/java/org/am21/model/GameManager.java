package org.am21.model;

import org.am21.controller.GameController;
import org.am21.model.*;
import org.am21.model.items.*;
import org.am21.model.items.Card.*;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    public List<Match> matchList;

    public List<Goal> goals;

    public List<Card> cards;

    private float waitTime;

    private float timer;

    public GameController controller;


    public GameManager(GameController controller){
        this.matchList = new ArrayList<Match>();
        this.goals = new ArrayList<Goal>();
        this.cards = new ArrayList<Card>();
        this.waitTime = 0;
        this.timer = 0;

    }

}

