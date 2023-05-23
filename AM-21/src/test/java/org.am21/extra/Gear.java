package org.am21.extra;

import org.am21.controller.PlayerController;
import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.model.enumer.GameState;
import org.am21.utilities.CardPointer;
import org.am21.utilities.GameGear;
import org.junit.jupiter.api.DisplayName;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.am21.utilities.GameGear.spacer;

public class Gear {
    static int numMatch = 0;
    public static int t = 0;
    public static int counter_AI = 0;
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
        posArr.addAll(Arrays.asList(new CardPointer(8, 3),
                new CardPointer(8, 4),
                new CardPointer(4, 8),
                new CardPointer(5, 8)));
        Collections.shuffle(posArr);
    }


    public static void robotMoves(PlayerController pC, Player p) {
        if (pC.isMyTurn(p)) {
            randomChoice(pC);
            GameGear.showHand(p.getHand());
            pC.moveAllToHand();
            while (!pC.tryToInsert((int) (Math.random() * 5))) {
//                System.out.println(p.getName()+" > Finding column...");
            }

        }

    }


    @DisplayName("Player choices")
    public static void randomChoice(PlayerController ctrl) {
        int a, b;
        int attempt = 0;
        int fail = 0;
        CardPointer tmp;

        do {
            if (attempt == 0) {
                do {
                    a = (int) ((Math.random() * 9));
                    b = (int) ((Math.random() * 9));
                } while (!ctrl.getPlayer().getMatch().board.isPlayable(a, b));
                if (ctrl.selectCell(a, b)) {
                    attempt++;
                } else {
                    fail++;
                }
                if (fail == 5) {
                    for (CardPointer point : posArr) {
                        if (ctrl.selectCell(point.x, point.y)) {
                            attempt++;
                            counter_AI++;
                        }
                        if (fail == 10) {
                            attempt = 10;
                            break;
                        } else {
                            fail++;
                        }
                    }
                }
            } else {
                do {
                    a = ((int) (Math.random() * 5)) - 2;
                    b = ((int) (Math.random() * 5)) - 2;
                } while (a != 0 && b != 0);

                /*System.out.print("Board > Selection difference: ");
                System.out.print("["+a+"]");
                System.out.println("["+b+"]");*/
                int numGen = (int) (Math.random() * (ctrl.getHand().getSelectedItems().size()));
                tmp = ctrl.getHand().getSelectedItems().get(numGen);
                a = a + tmp.x;
                b = b + tmp.y;
                if (a < 0 || a > 8 || b < 0 || b > 8) {
                } else {
                    if (ctrl.getPlayer().getMatch().board.isPlayable(a, b)) {
                        ctrl.selectCell(a, b);
                        attempt++;
                    }
                }

            }
        } while (attempt < 10);
        ctrl.callEndSelection();

    }

    public static Match buildGame(int seats, int nRounds) throws RemoteException {
        Match m = new Match(seats);
        numMatch++;
        System.out.println("Game > [[Match n. " + numMatch + "]]");
        PlayerController pC1 = new PlayerController("Kratos");
        Player p1 = pC1.getPlayer();
        PlayerController pC2 = new PlayerController("Omar");
        Player p2 = pC2.getPlayer();
        PlayerController pC3 = new PlayerController("Silvestro");
        Player p3 = pC3.getPlayer();
        PlayerController pC4 = new PlayerController("Jane");
        Player p4 = pC4.getPlayer();
        m.addPlayer(p1);
        m.addPlayer(p2);
        m.addPlayer(p3);
        m.addPlayer(p4);

        spacer();
        GameGear.printThisBoard(m.board);
        GameGear.printfThisBag(m.board.bag);
        GameGear.printCommGoals(m.commonGoals);
        GameGear.printPersonalGoals(m.playerList);
        spacer();
        Player p;
        for (t = 0; t < nRounds; t++) {      //Number of round
            if (m.gameState != GameState.Closed) System.out.println("Match > {[ Round number: " + (t + 1) + " ]}");
            for (int f = 0; f < m.playerList.size(); f++) {
                p = m.playerList.get(f);
                if (m.currentPlayer.equals(p)) {
                    robotMoves(p.getController(), p);
                }
            }
            if (m.gameState == GameState.Closed) {
                break;
            }
            GameGear.viewStats(m, t);
        }


        return m;
    }


}
