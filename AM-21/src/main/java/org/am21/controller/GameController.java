package org.am21.controller;

import org.am21.model.GameManager;

import java.rmi.RemoteException;

public class GameController {
    public InputManager inputManager;


    public GameManager game;

    public GameController() throws RemoteException {

        inputManager = new InputManager();
        inputManager.gameController = this;
        this.game = new GameManager(this);
    }

    /**
     * Initialize the game.
     * Pay attention to the order of the initialization of instances to avoid potential null pointer exception.
     * @param playerNum
     */
    public void initializeGame(int playerNum) {

    }


    private void timerLoop() {
    }

    public void distributeGoal() {
    }

    public void endGame() {}

    public void userJoin(){}

    public void createMatch(){}

}
