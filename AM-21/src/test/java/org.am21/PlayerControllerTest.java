package org.am21;

import org.am21.controller.PlayerController;
import org.junit.jupiter.api.Test;

class PlayerControllerTest {

    @Test
    void createPlayer(){
        PlayerController playerController = new PlayerController("Ambrogio");
        System.out.println(playerController.player.getName());
        System.out.println(playerController.player.playerScore);
    }

}
