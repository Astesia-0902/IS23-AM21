package org.am21.model.enumer;

/**
 * Connection status of the player in the game
 * {@link #Offline}: Not in the game but has an account, maybe due to connection loss or else<br>
 * {@link #Online}: Account logged and in the game menu, but not in a match<br>
 * {@link #GameMember}: Online and playing in a match<br>
 *
 * @version 1.0
 */
public enum UserStatus {
    GameMember,
    Suspended,
    Online,
    Offline
}
