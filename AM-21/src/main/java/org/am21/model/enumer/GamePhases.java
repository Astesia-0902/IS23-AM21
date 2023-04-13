package org.am21.model.enumer;


/**
 * The values of this enumeration represent the phases of a player's turn during the game.
 * Each phase allows the player to use different methods.
 * Phases:{@link #Selection}/{@link #Insertion}/{@link #GoalChecking}/{@link #EndTurn}
 * @author Ken Chen
 * @version 1.0
 */
public enum GamePhases {
    Selection,
    Insertion,
    GoalChecking,
    EndTurn
}
