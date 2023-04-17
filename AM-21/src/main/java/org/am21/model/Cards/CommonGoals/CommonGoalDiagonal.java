package org.am21.model.Cards.CommonGoals;

import org.am21.model.Cards.CommonGoal;
import org.am21.model.items.Shelf;

public class CommonGoalDiagonal extends CommonGoal {
    private static final int nDiag = 2;

    /**
     *
     * @param numPlayer
     */
    public CommonGoalDiagonal(int numPlayer) {

        super("CommonGoalDiagonal",numPlayer);
    }


    /**
     * Scan the shelves to find five tiles of the same type forming a diagonal.
     * (0,0), (1,1), (2,2), (3,3), (4,4)
     * (1,0), (2,1), (3,2), (4,3), (5,4)
     *
     * (0,4), (1,3), (2,2), (3,1), (4,0)
     * (1,4), (2,3), (3,2), (4,1), (5,0)
     * @param s
     * @return
     */
    @Override
    public boolean checkGoal(Shelf s) {

        for(int r = 0; r < nDiag; r++){

            if(s.isOccupied(r,0)) {
                String type = s.getItemType(r, 0);
                // Scan diagonal \
                if(s.isOccupied(r,0)&& type.equals(s.getItemType(r,0))&&
                s.isOccupied(r+1,1)&& type.equals(s.getItemType(r+1,1))&&
                s.isOccupied(r+2,2)&&type.equals(s.getItemType(r+2,2))&&
                s.isOccupied(r+3,3)&&type.equals(s.getItemType(r+3,3))&&
                s.isOccupied(r+4,4)&&type.equals(s.getItemType(r+4,4))){
                    return true;
                }
            }

            if(s.isOccupied(r,4)) {
                String type = s.getItemType(r, 4);
                // Scan diagonal /
                if(s.isOccupied(r,4)&& type.equals(s.getItemType(r,4))&&
                        s.isOccupied(r+1,3)&& type.equals(s.getItemType(r+1,3))&&
                        s.isOccupied(r+2,2)&&type.equals(s.getItemType(r+2,2))&&
                        s.isOccupied(r+3,1)&&type.equals(s.getItemType(r+3,1))&&
                        s.isOccupied(r+4,0)&&type.equals(s.getItemType(r+4,0))){
                    return true;
                }
            }
        }
        return false;
    }
}


    /*String tile = shelves.getItemName(row, 0).substring(0, shelves.getItemName(row, 0).length() - 3);
            if (tile != null && tile.equals(shelves.getItemName(row+1, 1).
                    substring(0, shelves.getItemName(row+1, 1).length() - 3))
                    && tile.equals(shelves.getItemName(row+2,2).
                    substring(0, shelves.getItemName(row+2, 2).length() - 3))
                    && tile.equals(shelves.getItemName(row+3,3).
                    substring(0, shelves.getItemName(row+3, 3).length() - 3))
                    && tile.equals(shelves.getItemName(row+4,4).
                    substring(0, shelves.getItemName(row+4, 4).length() - 3))){
                    return true;
                    }*/


/*// Diagonals that start from bottom right
        for(int p = 0; p < nDiagPerSide; p++){
        state = false;
        if(s.isOccupied(p,4)) {
        String name = s.getItemName(p, 4).substring(0, s.getItemName(p, 4).length() - 3);
        // Scan one diagonal
        for (int r = p +1, c = 3; r < (Shelf.sRow - 1 + p) && c>=0; r++, c--) {
        if(s.isOccupied(r,c) &&
        name.equals(s.getItemName(r,c)
        .substring(0,s.getItemName(r,c).length()-3)))
        {
        state = true;
        }else{
        state = false;
        break;
        }
        }
        if(state){
        return true;
        }
        }
        }

    */