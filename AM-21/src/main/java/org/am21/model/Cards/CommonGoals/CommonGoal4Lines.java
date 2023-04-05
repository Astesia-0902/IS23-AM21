package org.am21.model.Cards.CommonGoals;

import org.am21.model.Cards.CommonGoal;
import org.am21.model.items.Shelf;

import java.util.HashSet;
import java.util.Set;

public class CommonGoal4Lines extends CommonGoal {
    public CommonGoal4Lines(int numPlayer) {
        super("CommonGoal4Lines",numPlayer);
    }
    public boolean checkGoal(Shelf shelf) {
        //at first check if number of full column in the Shelf is full
        int numRowMatch=0;
        for(int i=0;i<6;i++)
        {
            Set<String> reg= new HashSet<>();
            int j;
            for(j=0;j<5&&shelf.getItemName(i, j) != null;j++)
            {
                reg.add(shelf.getItemName(i, j).substring(0, shelf.getItemName(i, j).length() - 3));
            }
            if(reg.size()<=3&&j==5)
                numRowMatch++;
            reg.clear();
        }

        if(numRowMatch<4)
            return false;
        else
            return true;

    }
}
