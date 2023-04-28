package org.am21.model.enumer;


/**
 * The values of this enumeration represent the phases of a player's turn during the game.
 * Each phase allows the player to use different methods.
 * Phases:{@link #Selection}/{@link #Insertion}/{@link #Default}
 * @version 1.0
 */
public enum GamePhase {
    Default,
    Selection,
    Insertion
}
