package org.am21;

import org.am21.controller.PlayerController;
import org.am21.model.Hand;
import org.am21.model.Match;
import org.am21.model.TurnPhases;
import org.am21.model.items.LivingRoomBoard;
import org.junit.jupiter.api.Test;



class PlayerControllerTest {


    @Test
    static PlayerController createPlayer(){
        PlayerController playerController = new PlayerController("Ambrogio");
        System.out.println(playerController.player.getName());
        System.out.println("Score:" + playerController.player.playerScore);
        return playerController;
    }
    @Test
    static void printThisRow(LivingRoomBoard board,int r){
        System.out.println("");
        for(int j=0;j<9;j++){
            if(board.getCells()[r][j].getItemTileCard()==null){
                System.out.print(j+":[_________._]");
            }else if(board.getCells()[r][j].getItemTileCard()!=null){
                System.out.print(j+":["+ board.getCells()[r][j].getItemTileCard().getNameCard() +"]");
            }

        }
        System.out.println("");
    }


    @Test
    void selectionSimulation(){
        Match match = new Match(4);
        MatchTest.printThisBoard(match.livingRoomBoard);

        printThisRow(match.livingRoomBoard,0);

        PlayerController playerController = PlayerControllerTest.createPlayer();
        playerController.player.match = match;
        playerController.player.setHand(new Hand(playerController.player));
        playerController.hand = playerController.player.hand;
        playerController.hand.getSlot().get(0);
        match.turnPhase = TurnPhases.Selection;
        if(playerController.player.match.livingRoomBoard.getCells()==null){
            System.out.println("oooo!");
        }
        System.out.println(playerController.player.getName());
        if(playerController.selectCard(0,4)==true){
            System.out.println("Selected!");
        }else{
            System.out.println("NotSelected!");
        }
        System.out.println(playerController.hand.getNumCards());
        //System.out.println(playerController.hand.getSlot().get(0).item.getNameCard());
    }

}
