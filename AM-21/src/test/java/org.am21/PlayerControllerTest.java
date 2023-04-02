package org.am21;

import org.am21.controller.PlayerController;
import org.am21.model.Hand;
import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.utilities.Coordinates;
import org.junit.jupiter.api.Test;


class PlayerControllerTest {


    @Test
    static PlayerController createPlayerController(String name){
        System.out.println("----------------------");
        System.out.println("Player account creation...");
        PlayerController playerController = new PlayerController(name);
        System.out.println("Nickname: "+playerController.player.getName());
        System.out.println("Score: " + playerController.player.playerScore);
        return playerController;
    }

    @Test
    void selectionSimulation(){

        Match match = MatchTest.createMatch(3);
        PlayerController pCtrl1 = PlayerControllerTest.createPlayerController("Ambrogio");
        Player player1 = pCtrl1.player;
        match.addPlayer(player1);
        PlayerController pCtrl2 = PlayerControllerTest.createPlayerController("Ambra");
        Player player2 = pCtrl2.player;
        match.addPlayer(player2);
        PlayerController pCtrl3 = PlayerControllerTest.createPlayerController("Zazà");
        Player player3 = pCtrl3.player;
        match.addPlayer(player3);

        System.out.println("----------------------");
        match.matchStart();
        System.out.println("PlayerTurn: "+ match.currentPlayer.getName());

        MatchTest.printThisBoard(match.livingRoomBoard);

        pCtrl1.selectCell(0,5);
        pCtrl1.selectCell(1,5);
        pCtrl1.selectCell(2,5);

        //pCtrl1.unselectCard();
        pCtrl2.selectCell(7,3);
        pCtrl2.selectCell(8,3);
        pCtrl2.selectCell(2,3);
        pCtrl2.selectCell(5,1);

        pCtrl3.selectCell(3,0);
        pCtrl3.selectCell(4,1);


        showHand(pCtrl1.hand);
        showHand(pCtrl2.hand);
        showHand(pCtrl3.hand);

        pCtrl1.moveAllToHand();
        pCtrl2.moveAllToHand();
        pCtrl3.moveAllToHand();

        MatchTest.printThisBoard(match.livingRoomBoard);

    }

    @Test
    static void showHand(Hand hand){
        System.out.println("--------");
        System.out.println(hand.player.getName() + "'s Hand ["+ hand.getSlot().size()+"]:");
        for(Coordinates x : hand.getSlot()) {
            if(x.item.getNameCard()!=null){
                System.out.print("["+x.item.getNameCard()+"]");
            }
        }
        System.out.println("");


    }

    @Test
    static void randomChoice(PlayerController ctrl){
        int a,b;
        int count =0;
        Coordinates tmp;
        System.out.println("--------");
        do {
            if(count==0){
                a = (int) ((Math.random() * 9)-1);
                b = (int) ((Math.random() * 9)-1);
                if(ctrl.selectCell(a, b))
                    count++;
            }else {
                a = (int) (Math.random() * 2);
                b = (int) (Math.random() * 2);
                tmp = ctrl.hand.getSlot().get((int) (Math.random() * (ctrl.hand.getSlot().size() - 1)));
                a = a + tmp.x;
                b = b + tmp.y;

                ctrl.selectCell(a, b);
                count++;

            }
        }while(count<10);
    }

    @Test void randomSimulation(){
        Match match = MatchTest.createMatch(3);
        PlayerController pCtrl1 = PlayerControllerTest.createPlayerController("Ambrogio");
        Player player1 = pCtrl1.player;
        match.addPlayer(player1);
        PlayerController pCtrl2 = PlayerControllerTest.createPlayerController("Ambra");
        Player player2 = pCtrl2.player;
        match.addPlayer(player2);
        PlayerController pCtrl3 = PlayerControllerTest.createPlayerController("Zazà");
        Player player3 = pCtrl3.player;
        match.addPlayer(player3);

        System.out.println("----------------------");
        match.matchStart();
        System.out.println("PlayerTurn: "+ match.currentPlayer.getName());

        MatchTest.printThisBoard(match.livingRoomBoard);

        randomChoice(pCtrl1);

        randomChoice(pCtrl2);

        randomChoice(pCtrl3);

        showHand(pCtrl1.hand);
        showHand(pCtrl2.hand);
        showHand(pCtrl3.hand);

        pCtrl1.moveAllToHand();
        pCtrl2.moveAllToHand();
        pCtrl3.moveAllToHand();

        MatchTest.printThisBoard(match.livingRoomBoard);

    }

}
