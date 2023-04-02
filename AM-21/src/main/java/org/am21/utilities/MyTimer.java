package org.am21.utilities;

import org.am21.model.Match;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Timer to count down the time for each player
 */
public class MyTimer {
    private Timer timer;

    public MyTimer() {
        timer = new Timer();
    }

    /**
     * start timer at the beginning of each player's turn
     * @param waitTime
     */
    public void startTimer(final int waitTime, final Match match){
        final int[] count = {0};
        System.out.println("Timer started!");
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                count[0]++;
                if(count[0] >= waitTime){
                    System.out.println("Time's up!");
                    timer.cancel();
                    match.nextTurn();
                }
            }
        };
        timer.scheduleAtFixedRate(task,  0, 1000);
    }

    /**
     * stop timer when player finish his/her turn
     */
    public void stopTimer(){
        System.out.println("Timer stopped!");
        timer.cancel();
    }
}