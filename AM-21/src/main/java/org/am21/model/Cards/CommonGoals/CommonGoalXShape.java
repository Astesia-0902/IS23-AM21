package org.am21.model.Cards.CommonGoals;


import org.am21.model.Cards.CommonGoal;
import org.am21.model.items.Shelf;

public class CommonGoalXShape extends CommonGoal {

    public CommonGoalXShape(int numPlayer) {
        super("CommonGoalXShape", numPlayer);
    }
/**
    public CommonGoalXShape(){
        /**
         * the check since follow the sequence position cell (marked by a   ° )
         * ?????
         * ?°°°?
         * ?°°°?
         * ?°°°?
         * ?°°°?
         * ?????
         * each ° have a adjacent check (4 element), which CENTER can recorde the
         *  [row, column] of the principle position (the ° ) and secondary position
         *  [row-1,column-1], [row-1, column+1],[row+1, column-1], [row+1, column+1],
         *  if one of the 4 rest element is different by the first, the vector is not matched (discard)
         *  so we have completely 12 check , if check fall when the all 12 is different by the CENTER position
         *  if one is completed then return true and get the points.
         *
    *}
    */

    public boolean checkGoal(Shelf shelf) {
        for(int row=1; row<5; row++)
        {
            for(int col=1;col<4;col++)
            {
                String center = shelf.getItemName(row, col);
                String leftDown = shelf.getItemName(row-1, col-1);
                String rightDown = shelf.getItemName(row-1, col+1);
                String leftUp = shelf.getItemName(row+1, col-1);
                String rightUp = shelf.getItemName(row+1, col+1);
                if(center!=null && center.equals(leftDown) && center.equals(rightDown) && center.equals(leftUp) && center.equals(rightUp))
                    return true;
            }
        }
        return false;
    }
}
