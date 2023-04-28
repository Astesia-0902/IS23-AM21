package org.am21.client;


import org.am21.client.view.TUI.Cli;
import org.am21.client.view.TUI.Color;

import java.util.List;

/**
 * This will store all the data received from server
 */
public class LocalStorage {

    public Cli localCli;


    //DATA Received from Server VIRTUAL VIEW
    //--------------------------------------
    public int matchID;
    public String[][] virtualBoard;
    public List<String> players;
    public String currentPlayer;
    public List<Integer> scores;
    public List<String> commonGoal;
    public List<Integer> commonGoalScore;
    //TODO: personalGoal should be a list, each slot for a player, the index should also correspond
    public int personalGoal;
    public List<String[][]> shelf;
    public String gamePhase;
    public String gameState;
    public List<String> currentPlayerHand;
    public boolean endGameToken;
    //---------------------------------------

    //IDEA: maybe store all GOAL description here.
    // And when the CLI need this information, it will access it from here

    //EXAMPLE:
    public final String PG1=
            "[_"+ Color.PLANTS+"_][______._][_"+Color.FRAMES+"_][______._][______._]\n"+
            "[______._][______._][______._][______._][__"+Color.CATS+"__]\n"+
            "[______._][______._][______._][_"+Color.BOOKS+"__][______._]\n"+
            "[______._][_"+Color.GAMES+"__][______._][______._][______._]\n"+
            "[______._][______._][______._][______._][______._]\n"+
            "[______._][______._]["+Color.TROPHIES+"][______._][______._]";




    public final String CG2Lines=
            Color.YELLOW_BOLD + """
                        * CommonGoal2Lines:
                        Two columns each formed by 6 different types of tiles.""" + Color.RESET;


}
