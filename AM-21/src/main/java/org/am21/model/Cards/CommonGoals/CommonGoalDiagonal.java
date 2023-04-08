package org.am21.model.Cards.CommonGoals;

import org.am21.model.Cards.CommonGoal;
import org.am21.model.items.Shelf;

public class CommonGoalDiagonal extends CommonGoal {
    private static final int nDiagPerSide = 2;

    /**
     *
     * @param numPlayer
     */
    public CommonGoalDiagonal(int numPlayer) {

        super("CommonGoalDiagonal",numPlayer);
    }


    /**
     * Scan the shelf to find five tiles of the same type forming a diagonal.
     * @param s
     * @return
     */
    @Override
    public boolean checkGoal(Shelf s) {
        boolean state = false;
        // The verification need to be done on the following lists of coordinates:
        //  (0,0), (1,1), (2,2), (3,3), (4,4)
        //  (1,0), (2,1), (3,2), (4,3), (5,4)

        //  (0,4), (1,3), (2,2), (3,1), (4,0)
        //  (1,4), (2,3), (3,2), (4,1), (5,0)
        // Diagonals that start from bottom left
        for(int d = 0; d< nDiagPerSide;d++){
            state = false;
            if(s.isOccupied(d,0)) {
                String name = s.getItemName(d, 0).substring(0, s.getItemName(d, 0).length() - 3);
                // Scan one diagonal
                for (int r = d+1, c = 1; r < (Shelf.sRow - 1 + d) && c < Shelf.sColumn; r++, c++) {
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

        // Diagonals that start from bottom right
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





        return state;
    }
}


    /*String tile = shelf.getItemName(row, 0).substring(0, shelf.getItemName(row, 0).length() - 3);
            if (tile != null && tile.equals(shelf.getItemName(row+1, 1).
                    substring(0, shelf.getItemName(row+1, 1).length() - 3))
                    && tile.equals(shelf.getItemName(row+2,2).
                    substring(0, shelf.getItemName(row+2, 2).length() - 3))
                    && tile.equals(shelf.getItemName(row+3,3).
                    substring(0, shelf.getItemName(row+3, 3).length() - 3))
                    && tile.equals(shelf.getItemName(row+4,4).
                    substring(0, shelf.getItemName(row+4, 4).length() - 3))){
                    return true;
                    }*/