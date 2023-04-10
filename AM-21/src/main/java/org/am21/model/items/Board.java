package org.am21.model.items;

import org.am21.model.Cards.ItemCard;
import org.am21.model.Match;
import org.am21.utilities.BoardUtil;
import org.am21.utilities.CardPointer;
import org.am21.utilities.Coordinates;

import java.util.List;

public class Board extends Grid {

    /**
     * The number of required cards depends on the number of players
     */
    public final int maxSeats;

    public Match match;
    public Bag bag;
    /**
     * List that indicates the boundaries of the board
     */
    public List<Coordinates> boundaries;

    public static final int BOARD_ROW = 9;
    public static final int BOARD_COLUMN = 9;

    /**
     * Construction of the LivingRoom:
     * - initialize cells of the grid
     * - set number of Players
     * - building the Board with all the item
     *
     * @param match is where the board belongs
     */
    public Board(Match match) {
        super(BOARD_ROW, BOARD_COLUMN);
        this.maxSeats = match.maxSeats;
        this.match = match;
        this.bag = new Bag(this);
        boundaries = BoardUtil.boardBounder(maxSeats);
    }

    /**
     * This method is used to refill the board ONLY for the first time
     * @return true if the refill is successful
     */
    public boolean firstSetup() {

        if (BoardUtil.refillBoard(this)) {
//            System.out.println("Match > Living Room Successfully filled");
            return true;
        }
        return false;
    }

    /**
     * Setting the size of the grid according to the number of player
     * @return
     **/
    public int getSize() {
        if (maxSeats == 2)
            return 29;
        else if (maxSeats == 3)
            return 37;
        else
            return 45;
    }



    /**
     * Condition 1:
     * Verify if the celle exists
     * Verify if the Item(r,c) is available to the player to pick.
     * To be available, it needs to have at least one side free and also,
     * if the player pick more items, they need to form a straight line
     * Index (0-rowNum-1,0-colNum-1)
     * Checking four side of the item(r,c) if they are free
     * <p>
     * Could be improved, maybe need to be more efficient
     * <p>
     * It is different from isSingle
     *
     * @param r
     * @param c
     * @return
     */
    public boolean hasFreeSide(int r, int c) {

        if (this.getMatrix()[r][c] == null) {
//            System.out.println("Board[!] > Out of boundaries: Cell doesn't exist. ");
            return false;
        }


        if ((r + 1 < gRow) && !isOccupied(r + 1, c)) {

            return true;
        } else if (r - 1 >= 0 && !isOccupied(r - 1, c)) {

            return true;
        } else if ((c + 1 < gColumn) && !isOccupied(r, c + 1)) {

            return true;
        } else if (c - 1 >= 0 && !isOccupied(r, c - 1)) {

            return true;
        } else {
            return false;
        }
    }


    /**
     * Condition 2: depends on the others selected card
     * The selected tiles need to be on a Straight Line
     * And the new card needs to be adjacent to one of the old cards
     * <p>
     * La differenza delle coordinate lungo una direzione deve essere 0
     * La differenza delle coordinate rimanente puo essere o 1 o 2
     *
     * @param r
     * @param c
     * @param pHand
     * @return check
     */
    public boolean isOrthogonal(int r, int c, Hand pHand) {

        int a;
        int b;
        boolean check = true; // Se check resta true allora è ortogonale
        for (CardPointer card : pHand.getSlot()) {
            a = Math.abs(r - card.x);
            b = Math.abs(c - card.y);
//            System.out.print("Board > Coordinates difference: ");
//            System.out.print("["+a+"]");
//            System.out.println("["+b+"]");

            if (a == 0 && (b > 0 && b < 3)) {

            } else if (b == 0 && (a > 0 && a < 3)) {

            } else {
                check = false;
            }

        }
        return check;

    }

    /**
     * Check if the item is isolated in the board
     * It means that there isn't any item adjacent.
     * @param r
     * @param c
     * @return
     */
    public boolean isAlone(int r, int c) {

        if ((r + 1 < 9 && isOccupied(r + 1, c))
                || (r - 1 >= 0 && isOccupied(r - 1, c))
                || (c + 1 < 9 && isOccupied(r, c + 1))
                || (c - 1 >= 0 && isOccupied(r, c - 1))) {
            return false;
        }
        return true;
    }

    /**
     * Scan each cell of the board and verify if EVERY item is isolated
     *
     * @return true if ALL item are isolated
     * false: if at least one item is not isolated
     */
    public boolean checkBoard() {
        ItemCard tmp;
        for (int i = 0; i < this.gRow; i++) {
            for (int j = 0; j < this.gColumn; j++) {
                tmp = this.getMatrix()[i][j];
                if (tmp != null && !isAlone(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This method verifies if the cell is within the boundaries of this match
     * @param r
     * @param c
     * @return true if the cell is playable
     */
    public boolean isPlayable(int r,int c){
        if(boundaries.get(r)!=null && boundaries.get(r).x<=c && c<=boundaries.get(r).y){
            return true;
        }
        return false;

    }
}



