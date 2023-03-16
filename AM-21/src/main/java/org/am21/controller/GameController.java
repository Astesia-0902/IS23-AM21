package org.am21.controller;

import org.am21.model.Match;

public class GameController implements Controller{
    public Match matchInstance;

    GameController(){
        matchInstance = new Match();
        matchInstance.gameController = this;
    }
}
