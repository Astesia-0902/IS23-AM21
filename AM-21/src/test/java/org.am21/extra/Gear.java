package org.am21.extra;

import org.am21.controller.PlayerController;
import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.utilities.CardPointer;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Gear {
    public static int counter_AI =0;
    public static List<CardPointer> posArr = new ArrayList<>();
     static {
        /*
        for (int i = 0; i < 6; i++) {
            posArr.add(new Coordinates(i, 4));
            posArr.add(new Coordinates(4, i));
        }
        for (int i = 0; i < 6; i++) {
            posArr.add(new Coordinates(i, 3));
            posArr.add(new Coordinates(5, i));
        }
        */
         posArr.addAll(Arrays.asList(new CardPointer(8,3),
                 new CardPointer(8,4),
                 new CardPointer(4,8),
                 new CardPointer(5,8)));
        Collections.shuffle(posArr);
    }


    @DisplayName("Match creation")
    public static Match createMatch(int numSeats){
//        System.out.println("Game > Creating new match...");
        Match match = new Match(numSeats);
//        System.out.println("Match > Number of seats:" + match.maxSeats);
//        System.out.println("Game > Match created.");
        return match;
    }

    @DisplayName("PlayerController creation")
    public static PlayerController createPlayerController(String name){
//        System.out.println("----------------------");
//        System.out.println("Game > Player account creation...");
        PlayerController playerController = new PlayerController(name);
//        System.out.println("Game > Nickname: "+playerController.player.getName());
        return playerController;
    }

    public static void robotMoves(PlayerController pC, Player p){
        if(pC.isMyTurn(p)) {
            randomChoice(pC);
//            Printer.showHand(p.hand);
            pC.moveAllToHand();
            while(!pC.tryToInsert((int) (Math.random() * 5))){
//                System.out.println(p.getName()+" > Finding column...");
            }

        }

    }

    @DisplayName("Spacer")
    public static void spacer(){
        System.out.println("-------------------------");
    }


    @DisplayName("Player choices")
    public static void randomChoice(PlayerController ctrl){
        int a,b;
        int attempt =0;
        int fail=0;
        CardPointer tmp;

        do {
            if(attempt ==0){
                a = (int) ((Math.random() * 9));
                b = (int) ((Math.random() * 9));
                if(ctrl.selectCell(a, b)) {
                    attempt++;

                }else{
                    fail++;
                }
                if(fail==5){
                    for(CardPointer popo: posArr){

                        if(ctrl.selectCell(popo.x,popo.y)){

                            attempt++;
                            counter_AI++;
                        }
                        if(fail==10){
                            attempt =10;
                            break;
                        }else{
                            fail++;
                        }
                    }

                }


            }else {
                do {
                    a = ((int) (Math.random() * 3)) - 1;
                    b = ((int) (Math.random() * 3)) - 1;
                }while(a!=0 && b!=0);

//                System.out.print("Board > Selection difference: ");
//                System.out.print("["+a+"]");
//                System.out.println("["+b+"]");
                tmp = ctrl.getHand().getSlot().get((int) (Math.random() * (ctrl.getHand().getSlot().size())));
                a = a + tmp.x;
                b = b + tmp.y;
                if(a<0 || a>8 || b<0 || b>8){

                }else{
                    ctrl.selectCell(a, b);
                    attempt++;
                }

            }
        }while(attempt <10);
        ctrl.callEndSelection();

    }





}
