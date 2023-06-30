package org.am21.model.items;

import org.am21.model.Cards.ItemCard;
import org.am21.model.GameManager;
import org.am21.model.Match;
import org.am21.model.enumer.ServerMessage;
import org.am21.utilities.BoardUtil;
import org.am21.utilities.CardPointer;
import org.am21.utilities.Coordinates;

import java.util.List;

/**
 * This class is the board of the game
 */
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
     *
     * @return true if the refill is successful
     */
    public boolean firstSetup() {

        return bag.refillBoard();
    }

    /**
     * Setting the size of the grid according to the number of player
     *
     * @return number of cell available to play
     **/
    public int getSize() {
        return switch (maxSeats) {
            case 2 -> 29;
            case 3 -> 37;
            case 4 -> 45;
            default -> 0;
        };
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
     * @param r row
     * @param c column
     * @return true if the item has free side, otherwise false
     */
    public boolean hasFreeSide(int r, int c) {

        if (getMatrix()[r][c] == null) {
            GameManager.sendReply(match.currentPlayer.getController(), ServerMessage.Cell_Illegal.value());
            return false;
        }

        if ((r + 1 < BOARD_ROW) && !isOccupied(r + 1, c)) {

            return true;
        } else if (r - 1 >= 0 && !isOccupied(r - 1, c)) {

            return true;
        } else if ((c + 1 < BOARD_COLUMN) && !isOccupied(r, c + 1)) {

            return true;
        } else if (c - 1 >= 0 && !isOccupied(r, c - 1)) {

            return true;
        } else {
            GameManager.sendReply(match.currentPlayer.getController(), ServerMessage.No_Free.value());
            return false;
        }
    }


    /**
     * Condition 2: depends on the others selected card
     * The selected tiles need to be on a Straight Line
     * And the new card needs to be adjacent to one of the old cards
     * <p>
     *
     * @param r        row
     * @param c        column
     * @param selItems selected items list
     * @return true if the conditions are respected, otherwise false
     */
    public boolean isOrthogonal(int r, int c, List<CardPointer> selItems) {
        int a;
        int b;
        boolean one_adjacent = false;
        boolean inline = true;

        for (CardPointer card : selItems) {
            a = Math.abs(r - card.x);
            b = Math.abs(c - card.y);

            //Check if the new item is adjacent at least to one item in the hand
            //Check if the new item is in line
            if (a == 0 && (b > 0 && b <= (Shelf.STD_LIMIT - 1))) {
                if (b == 1) one_adjacent = true;
            } else if (b == 0 && (a > 0 && a <= (Shelf.STD_LIMIT - 1))) {
                if (a == 1) one_adjacent = true;
            } else {
                inline = false;
            }
        }

        return inline && one_adjacent;
    }

    /**
     * Check if the item is isolated in the board
     * It means that there isn't any item adjacent.
     *
     * @param r row
     * @param c column
     * @return true if the item is isolated, otherwise false
     */
    public boolean isAlone(int r, int c) {

        return (r + 1 >= BOARD_ROW || !isOccupied(r + 1, c))
                && (r - 1 < 0 || !isOccupied(r - 1, c))
                && (c + 1 >= BOARD_COLUMN || !isOccupied(r, c + 1))
                && (c - 1 < 0 || !isOccupied(r, c - 1));
    }

    /**
     * Scan each cell of the board and verify if EVERY item is isolated
     *
     * @return true if ALL item are isolated
     * false: if at least one item is not isolated
     */
    public boolean checkBoard() {
        ItemCard tmp;
        for (int i = 0; i < BOARD_ROW; i++) {
            for (int j = 0; j < BOARD_COLUMN; j++) {
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
     *
     * @param r row
     * @param c column
     * @return true if the cell is playable
     */
    public boolean isPlayable(int r, int c) {
        return boundaries.get(r) != null && boundaries.get(r).x <= c && c <= boundaries.get(r).y;

    }

}



