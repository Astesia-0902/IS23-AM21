package org.am21.model.items;

import org.am21.model.Player;
import org.am21.utilities.CardUtil;

import java.util.List;
import java.util.Stack;

public abstract class CommonGoal extends Goal {
    public List<Integer> tokenStack;
    public List<Player> achievedPlayers;
    public int index;
    public Stack<Integer> scoreBox ;

    /*
    public CommonGoal(int id, List<Player> achievedPlayers) {
        super();
        this.id = id;
        this.achievedPlayers = achievedPlayers;
        this.tokenStack = new ArrayList<>();
    }*/

    public CommonGoal(String nameCard,int numPlayer){
        super(nameCard);
        this.tokenStack = CardUtil.buildScoringTokenCards(numPlayer);
        this.index = 0;
        this.scoreBox = new Stack<>();
        pushScores(numPlayer);
    }

    public boolean checkGoal(Shelf shelf) {
        /**
         * Durante la fase del turno CheckingGoal, viene controllato la Shelf del Player
         * per verificare se l'obiettivo è stato completato
         *
         * @param shelf
         * @return
         */

        return false;
    }

    public void setAchievedPlayers(Player player) {
        this.achievedPlayers.add(player);
        player.playerScore += this.tokenStack.get(index);
        this.tokenStack.remove(index);

        index++;
    }

    public boolean pushScores(int numPlayer)   {
        switch(numPlayer) {
            case 2:
                this.scoreBox.push(4);
                this.scoreBox.push(8);
                break;
            case 3:
                this.scoreBox.push(4);
                this.scoreBox.push(6);
                this.scoreBox.push(8);
                break;
            case 4:
                this.scoreBox.push(2);
                this.scoreBox.push(4);
                this.scoreBox.push(6);
                this.scoreBox.push(8);
                break;
            default:
                return false;
        }
        return true;
    }

    public boolean popScores(Stack<Integer> scoreBox) {
        if (scoreBox.empty())
            return false;
        else {
            scoreBox.pop();
            return true;
        }
    }
}



