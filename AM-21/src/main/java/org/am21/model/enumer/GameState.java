package org.am21.model.enumer;

/**
 * Each value represent the state of the match
 * {@link #WaitingPlayers}: the match hasn't started yet,
 * and it's still waiting for players to join to reach full capacity.
 * {@link #Ready}: The match is initialized
 * {@link #GameGoing}: the match has started and the players are playing.
 * {@link #LastRound}: the players are playing the last Round, when it ends, the room will close.
 * {@link #Closed}: the match ended, ready to be cleared from GameManager
 * @version 1.0
 */
public enum GameState {
    WaitingPlayers,
    Ready,
    GameGoing,
    LastRound,
    Closed
}
