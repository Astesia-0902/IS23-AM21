package org.am21.model.items.Card.CommonGoals;

import org.am21.model.items.CommonGoal;
import org.am21.model.items.Shelf;

public class CommonGoalCorner extends CommonGoal{

    public CommonGoalCorner(String name) {
        super(name);
    }

    /*
        public CommonGoalCorner(){
           /**
            * check 2 or more the sequence condition:
            * 1. remember the first row and first column cell;
            * 2. remember the last row and first column cell;
            * 3. remember the first row and last column cell ;
            * 4. remember the last row and last column cell;
            * if fall the 2 or more condition, then return false for this player
            * if success the all 4 condition, then return true and distribuite the actually points
            *
        }

     */
    @Override
    public boolean checkGoal(Shelf shelf) {
        String corn1 = shelf.getItemName(1, 1);
        String corn2 = shelf.getItemName(1, 5);
        String corn3 = shelf.getItemName(6, 1);
        String corn4 = shelf.getItemName(6, 5);
        /**  if all corner are filled  */
        if(corn1 !=null && corn2!= null && corn3!=null && corn4!=null)
        {
            /**  if they are the same then return true*/
            if(corn1.equals(corn2) && corn2.equals(corn3) && corn3.equals(corn4))
                return true;
            else /**  else return false (so this player is missed the points)*/
                return false;
        }
           /**  return to continuing  checking
            * ( the player has still chance for accumulating the points )*/
        return false;
    }

}
