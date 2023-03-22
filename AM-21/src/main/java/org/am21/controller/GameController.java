package org.am21.controller;

import org.am21.model.GameManager;

import java.rmi.RemoteException;
import java.util.HashMap;

public class GameController {
    public ClientInputHandler clientInputHandler;
    public ClientChatHandler clientChatHandler;

    public GameManager gameManager;
    //Key: player host name, Value: match id
    //Check the legality of the player's request
    public HashMap<String, Integer> playerMatchMap = new HashMap<String, Integer>();
    public int waitTime = 30;


    public GameController() throws RemoteException {

        clientInputHandler = new ClientInputHandler();
        clientChatHandler = new ClientChatHandler();
        clientChatHandler.gameController = this;
        clientInputHandler.gameController = this;
        this.gameManager = new GameManager(this);
    }

    /**
     * Initialize the game.
     * Pay attention to the order of the initialization of instances to avoid potential null pointer exception.
     *
     * @param playerNum
     */
    public void initializeGame(int playerNum) {

    }


    private void timerLoop() {

    }

    public void distributeGoal() {
    }

    public void endGame() {
    }

    public void userJoin() {
    }

    public void createMatch() {
    }

}
