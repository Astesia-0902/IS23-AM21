package org.am21;

import org.am21.controller.PlayerController;
import org.am21.model.Match;
import org.am21.model.Player;
import org.junit.jupiter.api.Test;

import static org.am21.Gear.createMatch;
import static org.am21.Gear.createPlayerController;
import static org.am21.Printer.*;

class MatchTest {
    @Test
    void run(){
        Match match = createMatch(4);
        PlayerController pCtrl1 = createPlayerController("Ambrogio");
        Player player1 = pCtrl1.player;
        match.addPlayer(player1);
        PlayerController pCtrl2 = createPlayerController("Ambra");
        Player player2 = pCtrl2.player;
        match.addPlayer(player2);
        PlayerController pCtrl3 = createPlayerController("Anna");
        Player player3 = pCtrl3.player;
        match.addPlayer(player3);
        PlayerController pCtrl4 = createPlayerController("Andrea");
        Player player4 = pCtrl4.player;
        match.addPlayer(player4);

        printfThisBag(match.bag);
        printCommGoals(match.commonGoals);
        printPersonalGoals(match.playerList);

        printScoringTokens(match.commonGoals);
    }















}
