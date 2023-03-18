package org.am21.controller;

import org.am21.model.Match;

import java.rmi.RemoteException;

public class GameController {
    public Match matchInstance;
    public InputManager inputManager;
    public float waitTime;
    private float timer;

    GameController() throws RemoteException {
        matchInstance = new Match();
        matchInstance.gameController = this;
        inputManager = new InputManager();
        inputManager.gameController = this;
    }

    /**
     * Initialize the game.
     * Pay attention to the order of the initialization of instances to avoid potential null pointer exception.
     * @param playerNum
     */
    public void initializeGame(int playerNum) {
        matchInstance.initializeMatch(playerNum);
    }

    public void initializePlayers(int playerNum) {

    }

    private void timerLoop() {
    }

    public void distributeCards() {
    }

    public void endGame() {
    }
}
