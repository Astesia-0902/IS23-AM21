package org.am21.client.view.TUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * You can find the virtual view data in this class
 */
public class Storage {
    public static final int SHELF_ROW = 6;
    public static final int SHELF_COLUMN = 5;
    public static final int BOARD_ROW = 9;
    public static final int BOARD_COLUMN = 9;

    //----------------------------------------

    //-----------------------------------------------------------------------------------------------------------------

    public static final String MYSHELFIE4 = Color.YELLOW_BRIGHT + """
                  ___                                ___           ___           ___                         ___                       ___    \s
                 ⁄\\  \\                              /\\__\\         ⁄\\  \\         /\\__\\                       /\\__\\                     /\\__\\   \s
                |::\\  \\         ___                /:/ _/_        \\:\\  \\       /:/ _/_                     /:/ _/_       ___         /:/ _/_  \s
                |:|:\\  \\       /|  |              /:/ ⁄\\  \\        \\:\\  \\     /:/ /\\__\\                   /:/ /\\__\\     /\\__\\       /:/ /\\__\\ \s
              __|:|\\:\\  \\     |:|  |             /:/ /::\\  \\   ___ /::\\  \\   /:/ /:/ _/_   ___     ___   /:/ /:/  /    /:/__/      /:/ /:/ _/_\s
             /::::|_\\:\\__\\    |:|  |            /:/_/:/\\:\\__\\ ⁄\\  /:⁄\\:\\__\\ /:/_/:/ /\\__\\ ⁄\\  \\   /\\__\\ /:/_/:/  /    /::\\  \\     /:/_/:/ /\\__\\
             \\:\\¯¯\\  \\/__/  __|:|__|            \\:\\/:/ /:/  / \\:\\/:/  \\/__/ \\:\\/:/ /:/  / \\:\\  \\ /:/  / \\:\\/:/  /     \\⁄\\:\\  \\__  \\:\\/:/ /:/  /
              \\:\\  \\       /::::\\  \\             \\::/ /:/  /   \\::/__/       \\::/_/:/  /   \\:\\  /:/  /   \\::/__/       ¯¯\\:\\/\\__\\  \\::/_/:/  /\s
               \\:\\  \\      ¯¯¯¯\\:\\  \\             \\/_/:/  /     \\:\\  \\        \\:\\/:/  /     \\:\\/:/  /     \\:\\  \\          \\::/  /   \\:\\/:/  / \s
                \\:\\__\\          \\:\\__\\              /:/  /       \\:\\__\\        \\::/  /       \\::/  /       \\:\\__\\         /:/  /     \\::/  /  \s
                 \\/__/           \\/__/              \\/__/         \\/__/         \\/__/         \\/__/         \\/__/         \\/__/       \\/__/   \s
            """.replace(":", Color.CYAN_BRIGHT + ":" + Color.YELLOW_BRIGHT) + Color.RESET;


    public static final String MYSHELFIE6 = "\n" +
                                            " __       __                   ______   __                  __   ______   __           \n" +
                                            "|  \\     /  \\                 /      \\ |  \\                |  \\ /      \\ |  \\          \n" +
                                            "| $$\\   /  $$ __    __       |  $$$$$$\\| $$____    ______  | $$|  $$$$$$\\ \\$$  ______  \n" +
                                            "| $$$\\ /  $$$|  \\  |  \\      | $$___\\$$| $$    \\  /      \\ | $$| $$_  \\$$|  \\ /      \\ \n" +
                                            "| $$$$\\  $$$$| $$  | $$       \\$$    \\ | $$$$$$$\\|  $$$$$$\\| $$| $$ \\    | $$|  $$$$$$\\\n" +
                                            "| $$\\$$ $$ $$| $$  | $$       _\\$$$$$$\\| $$  | $$| $$    $$| $$| $$$$    | $$| $$    $$\n" +
                                            "| $$ \\$$$| $$| $$__/ $$      |  \\__| $$| $$  | $$| $$$$$$$$| $$| $$      | $$| $$$$$$$$\n" +
                                            "| $$  \\$ | $$ \\$$    $$       \\$$    $$| $$  | $$ \\$$     \\| $$| $$      | $$ \\$$     \\\n" +
                                            " \\$$      \\$$ _\\$$$$$$$        \\$$$$$$  \\$$   \\$$  \\$$$$$$$ \\$$ \\$$       \\$$  \\$$$$$$$\n" +
                                            "             |  \\__| $$                                                                \n" +
                                            "              \\$$    $$                                                                \n" +
                                            "               \\$$$$$$                                                                 \n";


    public static final String PG1 = "    1         2         3         4         5\n" +
                                     "[_" + Color.PLANTS + "_][______._][_" + Color.FRAMES + "_][______._][______._]\n" +
                                     "[______._][______._][______._][______._][__" + Color.CATS + "__]\n" +
                                     "[______._][______._][______._][_" + Color.BOOKS + "__][______._]\n" +
                                     "[______._][_" + Color.GAMES + "__][______._][______._][______._]\n" +
                                     "[______._][______._][______._][______._][______._]\n" +
                                     "[______._][______._][" + Color.TROPHIES + "][______._][______._]";
    public static final String PG2 = "    1         2         3         4         5\n" +
                                     "[______._][______._][______._][______._][______._]\n" +
                                     "[______._][_" + Color.PLANTS + "_][______._][______._][______._]\n" +
                                     "[__" + Color.CATS + "__][______._][_" + Color.GAMES + "__][______._][______._]\n" +
                                     "[______._][______._][______._][______._][_" + Color.BOOKS + "__]\n" +
                                     "[______._][______._][______._][" + Color.TROPHIES + "][______._]\n" +
                                     "[______._][______._][______._][______._][_" + Color.FRAMES + "_]";
    public static final String PG3 = "    1         2         3         4         5\n" +
                                     "[______._][______._][______._][______._][______._]\n" +
                                     "[_" + Color.FRAMES + "_][______._][______._][_" + Color.GAMES + "__][______._]\n" +
                                     "[______._][______._][_" + Color.PLANTS + "_][______._][______._]\n" +
                                     "[______._][__" + Color.CATS + "__][______._][______._][" + Color.TROPHIES + "]\n" +
                                     "[______._][______._][______._][______._][______._]\n" +
                                     "[_" + Color.BOOKS + "__][______._][______._][______._][______._]";
    public static final String PG4 = "    1         2         3         4         5\n" +
                                     "[______._][______._][______._][______._][_" + Color.GAMES + "__]\n" +
                                     "[______._][______._][______._][______._][______._]\n" +
                                     "[" + Color.TROPHIES + "][______._][_" + Color.FRAMES + "_][______._][______._]\n" +
                                     "[______._][______._][______._][_" + Color.PLANTS + "_][______._]\n" +
                                     "[______._][_" + Color.BOOKS + "__][__" + Color.CATS + "__][______._][______._]\n" +
                                     "[______._][______._][______._][______._][______._]";
    public static final String PG5 = "    1         2         3         4         5\n" +
                                     "[______._][______._][______._][______._][______._]\n" +
                                     "[______._][" + Color.TROPHIES + "][______._][______._][______._]\n" +
                                     "[______._][______._][______._][______._][______._]\n" +
                                     "[______._][_" + Color.FRAMES + "_][_" + Color.BOOKS + "__][______._][______._]\n" +
                                     "[______._][______._][______._][______._][_" + Color.PLANTS + "_]\n" +
                                     "[_" + Color.GAMES + "__][______._][______._][__" + Color.CATS + "__][______._]";
    public static final String PG6 = "    1         2         3         4         5\n" +
                                     "[______._][______._][" + Color.TROPHIES + "][______._][__" + Color.CATS + "__]\n" +
                                     "[______._][______._][______._][______._][______._]\n" +
                                     "[______._][______._][______._][_" + Color.BOOKS + "__][______._]\n" +
                                     "[______._][______._][______._][______._][______._]\n" +
                                     "[______._][_" + Color.GAMES + "__][______._][_" + Color.FRAMES + "_][______._]\n" +
                                     "[_" + Color.PLANTS + "_][______._][______._][______._][______._]";
    public static final String PG7 = "    1         2         3         4         5\n" +
                                     "[__" + Color.CATS + "__][______._][______._][______._][______._]\n" +
                                     "[______._][______._][______._][_" + Color.FRAMES + "_][______._]\n" +
                                     "[______._][_" + Color.PLANTS + "_][______._][______._][______._]\n" +
                                     "[" + Color.TROPHIES + "][______._][______._][______._][______._]\n" +
                                     "[______._][______._][______._][______._][_" + Color.GAMES + "__]\n" +
                                     "[______._][______._][_" + Color.BOOKS + "__][______._][______._]";
    public static final String PG8 = "    1         2         3         4         5\n" +
                                     "[______._][______._][______._][______._][_" + Color.FRAMES + "_]\n" +
                                     "[______._][__" + Color.CATS + "__][______._][______._][______._]\n" +
                                     "[______._][______._][" + Color.TROPHIES + "][______._][______._]\n" +
                                     "[_" + Color.PLANTS + "_][______._][______._][______._][______._]\n" +
                                     "[______._][______._][______._][_" + Color.BOOKS + "__][______._]\n" +
                                     "[______._][______._][______._][_" + Color.GAMES + "__][______._]";
    public static final String PG9 = "    1         2         3         4         5\n" +
                                     "[______._][______._][_" + Color.GAMES + "__][______._][______._]\n" +
                                     "[______._][______._][______._][______._][______._]\n" +
                                     "[______._][______._][__" + Color.CATS + "__][______._][______._]\n" +
                                     "[______._][______._][______._][______._][_" + Color.BOOKS + "__]\n" +
                                     "[______._][" + Color.TROPHIES + "][______._][______._][_" + Color.PLANTS + "_]\n" +
                                     "[_" + Color.FRAMES + "_][______._][______._][______._][______._]";
    public static final String PG10 = "    1         2         3         4         5\n" +
                                      "[______._][______._][______._][______._][" + Color.TROPHIES + "]\n" +
                                      "[______._][_" + Color.GAMES + "__][______._][______._][______._]\n" +
                                      "[_" + Color.BOOKS + "__][______._][______._][______._][______._]\n" +
                                      "[______._][______._][______._][__" + Color.CATS + "__][______._]\n" +
                                      "[______._][_" + Color.FRAMES + "_][______._][______._][______._]\n" +
                                      "[______._][______._][______._][_" + Color.PLANTS + "_][______._]";
    public static final String PG11 = "    1         2         3         4         5\n" +
                                      "[______._][______._][_" + Color.PLANTS + "_][______._][______._]\n" +
                                      "[______._][_" + Color.BOOKS + "__][______._][______._][______._]\n" +
                                      "[_" + Color.GAMES + "__][______._][______._][______._][______._]\n" +
                                      "[______._][______._][_" + Color.FRAMES + "_][______._][______._]\n" +
                                      "[______._][______._][______._][______._][__" + Color.CATS + "__]\n" +
                                      "[______._][______._][______._][" + Color.TROPHIES + "][______._]";
    public static final String PG12 = "    1         2         3         4         5\n" +
                                      "[______._][______._][_" + Color.BOOKS + "__][______._][______._]\n" +
                                      "[______._][_" + Color.PLANTS + "_][______._][______._][______._]\n" +
                                      "[______._][______._][_" + Color.FRAMES + "_][______._][______._]\n" +
                                      "[______._][______._][______._][" + Color.TROPHIES + "][______._]\n" +
                                      "[______._][______._][______._][______._][_" + Color.GAMES + "__]\n" +
                                      "[__" + Color.CATS + "__][______._][______._][______._][______._]";

    public static final String PGTable = """
            \t\t__________________________
            \t\t[V] 1 | 2 | 3 | 4 | 5 | 6 
            \t\t v ---+---+---+---+---+---
            \t\t[P] 1 | 2 | 4 | 6 | 9 | 12
            \t\t¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯
            """;
    public static final String CG2Columns = Color.YELLOW_BOLD + """
            * CommonGoal2Columns:
            _____
            ¦ ≠ ¦
            ¦ ≠ ¦
            ¦ ≠ ¦
            ¦ ≠ ¦ x2    Two columns each formed by 6 different types of tiles.
            ¦ ≠ ¦
            ¦ ≠ ¦
            ¦ ≠ ¦
            ¯¯¯¯¯""" + Color.RESET;
    public static final String CG2Lines = Color.YELLOW_BOLD + """
            * CommonGoal2Lines:
            _________________
            ¦ ≠  ≠  ≠  ≠  ≠ ¦       Two lines each formed by 6 different types of tiles.
            ¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯       One line can show the same or a different combination of the other line.
                   x2""" + Color.RESET;
    public static final String CG3Column = Color.YELLOW_BOLD + """
            * CommonGoal3Column:
            ¦¯¯¯¦
            ¦¯¯¯¦
            ¦¯¯¯¦ MAX 3 ≠   Three columns each formed by 6 tiles of maximum three different types.
            ¦¯¯¯¦ x3        One column can show the same or a different combination of another column.
            ¦¯¯¯¦
            ¦¯¯¯¦
            ¦¯¯¯¦
            ¯¯¯¯¯""" + Color.RESET;
    public static final String CG4Lines = Color.YELLOW_BOLD + """
            * CommonGoal4Lines:
            ________________
            ¦  ¦  ¦  ¦  ¦  ¦        Four lines each formed by 5 tiles of maximum three different types.
            ¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯        One line can show the same or a different combination of another line.
                  MAX 3 ≠
                   x2""" + Color.RESET;
    public static final String CG8Tiles = Color.YELLOW_BOLD + """
            * CommonGoal8Tiles:
                _____   _____
                ¦ = ¦   ¦ = ¦
                ¯¯¯¯¯   ¯¯¯¯¯
            _____   _____   _____
            ¦ = ¦   ¦ = ¦   ¦ = ¦       Eight tiles of the same type.
            ¯¯¯¯¯   ¯¯¯¯¯   ¯¯¯¯¯       There’s no restriction about the position of these tiles.
            _____   _____   _____
            ¦ = ¦   ¦ = ¦   ¦ = ¦
            ¯¯¯¯¯   ¯¯¯¯¯   ¯¯¯¯¯""" + Color.RESET;
    public static final String CGCorner = Color.YELLOW_BOLD + """
            * CommonGoalCorner:
            _____ __________ _____
            ¦ = ¦            ¦ = ¦
            ¯¯¯¯¯            ¯¯¯¯¯
            ¦                    ¦
            ¦                    ¦
            ¦                    ¦      Four tiles of the same type in the four corners of the bookshelf.
            ¦                    ¦
            ¦                    ¦
            _____            _____
            ¦ = ¦            ¦ = ¦
            ¯¯¯¯¯ ¯¯¯¯¯¯¯¯¯¯ ¯¯¯¯¯""" + Color.RESET;
    public static final String CGDiagonal = Color.YELLOW_BOLD + """
            * CommonGoalDiagonal:
            _____
            ¦ = ¦
            ¯¯¯¯¯_____
                 ¦ = ¦
                 ¯¯¯¯¯_____
                      ¦ = ¦                 Five tiles of the same type forming a diagonal.
                      ¯¯¯¯¯_____
                           ¦ = ¦
                           ¯¯¯¯¯_____
                                ¦ = ¦
                                ¯¯¯¯¯""" + Color.RESET;
    public static final String CGSquare = Color.YELLOW_BOLD + """
            * CommonGoalSquare:
            ________
            ¦ =  = ¦        Two groups each containing 4 tiles of the same type in a 2x2 square.
            ¦ =  = ¦        The tiles of one square can be different from those of the other square.
            ¯¯¯¯¯¯¯¯
               x2""" + Color.RESET;
    public static final String CGStairs = Color.YELLOW_BOLD + """
            * CommonGoalStairs:
            ¦¯¯¯¦
            ¦¯¯¯¦¯¯¯¦                   Five columns of increasing or decreasing height.
            ¦¯¯¯¦¯¯¯¦¯¯¯¦               Starting from the first column on the left or on the right,
            ¦¯¯¯¦¯¯¯¦¯¯¯¦¯¯¯¦           each next column must be made of exactly one more tile.
            ¦¯¯¯¦¯¯¯¦¯¯¯¦¯¯¯¦¯¯¯¦       Tiles can be of any type.
            ¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯""" + Color.RESET;
    public static final String CG4Group = Color.YELLOW_BOLD + """
            * CommonGoal4Group:
            ◜____◝
             ¦ = ¦
             ¦ = ¦   x4     Four groups each containing at least 4 tiles of the same type
             ¦ = ¦          (not necessarily in the depicted shape).
             ¦ = ¦          The tiles of one group can be different from those of another group.
            ◟¯¯¯¯◞""" + Color.RESET;
    public static final String CG6Group = Color.YELLOW_BOLD + """
            * CommonGoal6Group:
            ◜____◝
             ¦ = ¦   x6     Six groups each containing at least 2 tiles of the same type (not necessarily in the
             ¦ = ¦          depicted shape). The tiles of one group can be different from those of another group.
            ◟¯¯¯¯◞""" + Color.RESET;
    public static final String CGXShape = Color.YELLOW_BOLD + """
            * CommonGoalXShape:
            _____     _____
            ¦ = ¦     ¦ = ¦
            ¯¯¯¯¯_____¯¯¯¯¯
                 ¦ = ¦          Five tiles of the same type forming an X.
            _____¯¯¯¯¯_____
            ¦ = ¦     ¦ = ¦
            ¯¯¯¯¯     ¯¯¯¯¯""" + Color.RESET;

    public static final List<String> commonGoalList = new ArrayList<>();

    static {
        Collections.addAll(commonGoalList, "CommonGoal2Lines", "CommonGoal2Columns", "CommonGoal3Column",
                "CommonGoal4Lines", "CommonGoal8Tiles", "CommonGoalCorner", "CommonGoalDiagonal", "CommonGoalSquare",
                "CommonGoalStairs", "CommonGoal4Group", "CommonGoal6Group", "CommonGoalXShape");
    }

    public static HashMap<String, String> goalCommonMap = new HashMap<>();

    static {
        goalCommonMap.put("CommonGoal2Columns", CG2Columns);
        goalCommonMap.put("CommonGoal2Lines", CG2Lines);
        goalCommonMap.put("CommonGoal3Column", CG3Column);
        goalCommonMap.put("CommonGoal4Lines", CG4Lines);
        goalCommonMap.put("CommonGoal8Tiles", CG8Tiles);
        goalCommonMap.put("CommonGoalCorner", CGCorner);
        goalCommonMap.put("CommonGoalDiagonal", CGDiagonal);
        goalCommonMap.put("CommonGoalSquare", CGSquare);
        goalCommonMap.put("CommonGoalStairs", CGStairs);
        goalCommonMap.put("CommonGoal4Group", CG4Group);
        goalCommonMap.put("CommonGoal6Group", CG6Group);
        goalCommonMap.put("CommonGoalXShape", CGXShape);
    }

    public static HashMap<Integer, String> goalPersonalMap = new HashMap<>();

    static {
        goalPersonalMap.put(1, PG1);
        goalPersonalMap.put(2, PG2);
        goalPersonalMap.put(3, PG3);
        goalPersonalMap.put(4, PG4);
        goalPersonalMap.put(5, PG5);
        goalPersonalMap.put(6, PG6);
        goalPersonalMap.put(7, PG7);
        goalPersonalMap.put(8, PG8);
        goalPersonalMap.put(9, PG9);
        goalPersonalMap.put(10, PG10);
        goalPersonalMap.put(11, PG11);
        goalPersonalMap.put(12, PG12);
    }


    public static final String goalOfTheGame = Color.YELLOW_BOLD + """
            Goal of the game:
            Players take item tiles from the living room and place them in their bookshelves to score points; the
            game ends when a player completely fills their bookshelf. The player with more points at the end will
            win the game. There are 4 ways to score points:
            1. Personal Goal card
                The personal goal card grants points if you match the highlighted spaces with the corresponding item
                tiles.
                Example: In the illustrated situation, at the end of the game the tile disposal shows 3 matches,
                that is worth 4 points.
            2. Common Goal cards
                The common goal cards grant points to the players who achieve the illustrated pattern. See the last
                page for a detailed descriptions of the common goal cards.
                Example: In a 3-player game on both Common Goal cards will be stacked the 4-, 6-, 8- scoring tokens
                (from bottom to top).
            3. Adjacent Item tiles
                Groups of adjacent item tiles of the same type on your bookshelf grant points depending on how many
                tiles are connected (with one side touching).
                Note: Item tiles with the same background color are considered to be of the same type.
                Example: In the situation above, at the end of the game there are 5 groups of adjacent item tiles of
                the same type:
                8 Plant tiles: 8 pt
                4 Trophy tiles: 3 pt
                5 Cat tiles: 5 pt
                4 Frame tiles: 3 pt
                3 Boardgame tiles: 2 pt
                Total:
                21 points
            4. Game-end trigger
                The first player who completely fills their bookshelf scores 1 additional point.
            """ + Color.RESET;
    public static final String gamePlay = Color.YELLOW_BOLD + """
            Gameplay:
            The game is divided in turns that take place in a clockwise order starting from the first player.
            During your turn, you must take 1, 2 or 3 item tiles from the living room board, following these rules:
            The tiles you take must be adjacent to each other and form a straight line.
            All the tiles you take must have at least one side free (not touching directly other tiles) at the
            beginning of your turn (i.e. you cannot take a tile that becomes free after your first pick).
            Then, you must place all the tiles you’ve picked into 1 column of your bookshelf. You can decide
            the order, but you cannot place tiles in more than 1 column in a single turn.
            Note: You cannot take tiles if you don’t have enough available spaces in your bookshelf.
            """ + Color.RESET;
    public static final String refillingLivingRoom = Color.YELLOW_BOLD + """
            Refilling the living room:
            The living room will be refiled when, at the end of your turn, on the board there are only item tiles
            without any other adjacent tile, i.e. the next player can only take single tiles.
            Put the item tiles left on the board back into the bag. Then, draw new item tiles from the bag and
            place them randomly in all the spaces of the board (remember that spaces with dots are only available\s
            in 3- or 4-player games).
            """ + Color.RESET;
    public static final String fulfillingCommonGoal = Color.YELLOW_BOLD + """
            Fulfilling a common goal:
            If at the end of your turn you have achieved the requirements of a common goal card, take the topmost
            available scoring token from that card. You can achieve and take scoring tokens from both common goal
            cards in the same turn. You can only score points from common goal cards once per game, so you can’t
            take more scoring tokens with the same back number. Players who achieve the common goals requirements
            first will score more points than the other players, so try to be faster than your opponents!
            """ + Color.RESET;
    public static final String gameEnd = Color.YELLOW_BOLD + """
            Game end:
            The first player who fills all the spaces of their bookshelf takes the end game token. The game
            continues until the player sitting to the right to the player holding the first player seat (if the end
            of the game is triggered by the player sitting to the right to the first player, the game ends
            immediately). Now you can proceed to the final scoring.
            Each player will score:
                - The points indicated by the tokens they hold (scoring tokens and end game token);
                - 1/2/4/6/9/12 points for 1/2/3/4/5/6 item tiles in the exact position illustrated by their
                  personal goal card;
                - 2/3/5/8 points for groups of 3/4/5/6+ item tiles of the same type adjacent on their bookshelf.
            The player who scored most points wins the game. In case of a tie, the tied player sitting further
            (clockwise) from the first player wins the game.
            Scoring Example:
            Example: Helena scores 12 points from scoring tokens, 6 points from her personal goal card, and
            18 points from the groups of adjacent tiles in her bookshelf:
            6 adjacent Trophy tiles: 8 points
            5 adjacent Cat tiles: 5 points
            5 adjacent Plants tiles: 5 points
            4 matches on the personal goal: 6 points
            Scoring tokens: 12 points
            Total: 36 points
            """ + Color.RESET;

    public static final String listObjects = """
            -----------------------------------------------------------
            [Commands] Try to use of these commands to have a see any of these elements:
              open    --> Show List of Players to Chat with
              hand    --> Show selected items.
              pgoal   --> See your Personal Goal and try to match the items type.
              cgoal   --> See Common Goals and try to achieve more points.
              shelf   --> See your shelves and the insertion limit (Default Limit: 3).
              board   --> See Board in more detail.
              stats   --> See All PLAYERS STATS(Shelf and Scores).
              rules   --> See Game Rules (to clarify any doubt).
              end     --> Show if the Endgame Token is taken (if it is, then it's the last round).
              online  --> Show a list of Online Players, so you can chat with them.
              timer   --> Show timer. (Not installed)
             'Enter'  --> Go Back to Play.
            -----------------------------------------------------------
            Type the commands you wish to use:\040""";
    public static final String menuOption = """
            -----------------------------------------------------------
            [Commands] Menu Option:
              create   --> Create a new match.
               join    --> Join a match.
              online   --> Show Online Players and Chat.
               exit    --> Exit game.
               help    --> Command List (Chat, etc) and Assist Mode 
            Enter 'help' command and learn how to use the CHAT""";

    public static final String waitingAction = """
            The match has not started yet. Please wait more players to join...
            -----------------------------------------------------------
            [Commands] These are the commands available:
              rules    --> Read Game Rules.
              online   --> Show Online Players.
             settings  --> Settings: Change Max Number of Players for this Room, Change Insertion Limit
              more     --> More Options: Leave Match, Exit Game, Help (Command List,Assist Mode).
            Enter 'more' then 'help' commands and learn how to use the Chat""";

    public static final String commandMenu0 = """
            -----------------------------------------------------------
            [Commands] Wait for your turn. Meanwhile, you can spectate typing 'show':
              show     --> Show Game Object in more detail(Goals, Board, Shelf, ...).
              more     --> More Options: Leave Match, Exit Game, Help (Command List,Assist Mode).
            Enter 'more' then 'help' commands and learn how to use the Chat""";

    public static final String commandMenu1 = """
            -----------------------------------------------------------
            [Commands] Use the 'select' command to SELECT item you would like to pick:
              select   --> Select an item on the board.
              show     --> Show Game Object in more detail(Selected items, Goals, Board, Shelf, ...).
              more     --> More Options: Leave Match, Exit Game, Help (Command List,Assist Mode).
            Enter 'more' then 'help' commands and learn how to use the Chat""";
    public static final String commandMenu2 = """
            -----------------------------------------------------------
            [Commands] Now you can SELECT another item OR INSERT the items in the Shelf:
              select   --> Select another item on the board.
              clear    --> Deselect all your selections.
              insert   --> Save your selections and Insert in the shelf.
              show     --> Show Game Object in more detail(Selected items, Goals, Board, Shelf, ...).
              more     --> More Options: Leave Match, Exit Game, Help (Command List,Assist Mode).
            Enter 'more' then 'help' commands and learn how to use the Chat""";
    public static final String commandInsert = """
            -----------------------------------------------------------
            [Commands] You can SORT the items before INSERTING them in the Shelf:
               sort    --> Change the ORDER of Insertion of your items.
               show    --> Show Game OBJECT in more detail(GOALS,SHELF,...).
              'Enter'  --> INSERT directly into the SHELF
               more    --> More Options: Leave Match, Exit Game, Help (Command List,Assist Mode).
            -----------------------------------------------------------
            Enter the option you wish to select:\040""";
    public static final String selectConfirm = """
            -----------------------------------------------------------
            [Commands] You can CONFIRM, REMAKE or DELETE your Selection. If needed, you could also view some OBJECT... 
                y      --> CONFIRM your Selection.
                r      --> RETRY by selecting again.
               show    --> See a Game OBJECT in more detail(Board, Shelf, Goals, ...).
                n      --> DELETE your choice and EXIT Selection.
            -----------------------------------------------------------
            Enter the option you wish to select:\040""";
    public static final String anotherCard = """
            -----------------------------------------------------------
            [Commands] Do you wish to select another card?
              'Enter'  --> Yes. I want to SELECT AGAIN.
            'Anything' --> No.
            -----------------------------------------------------------
            Enter the option you wish to select:\040""";
    public static final String deselectConfirm = """
            -----------------------------------------------------------
            [Commands] Are you sure to delete all of your selections?
                y      --> Yes. I am sure.
            'Anything' --> No.
            -----------------------------------------------------------
            Enter the option you wish to select:\040""";
    public static final String indexConfirm = """
            -----------------------------------------------------------
            [Commands] Confirm your choice?
                y      --> Yes.
                r      --> Retry.
                n      --> DELETE your choice and EXIT Index Selection.
            -----------------------------------------------------------
            Enter the option you wish to select:\040""";
    public static final String columnConfirm = """
            [Commands] Do you confirm this is the column you wish to insert into?
                y      --> Yes.
                r      --> Retry to choose the column again.
                n      --> DELETE your choice and EXIT (Go back to SORT or SHOW).
            -----------------------------------------------------------
            Enter the option you wish to select:\040""";
    public static final String insertionConfirm = """
            [Commands] Are you sure? If you go on you can't go back to selection!
                y      --> Yes. I am sure.
            'Anything' --> No.
            -----------------------------------------------------------
            Enter the option you wish to select:\040""";
    public static final String MoreOptions = """
            [Commands] These are the option available.
              leave    --> LEAVE the Match (if you leave, the match will end for everyone).
              exit     --> EXIT from the Game MyShelfie.
              help     --> Command List (Chat, etc) and Assist Mode
             'Enter'   --> Go Back.
            -----------------------------------------------------------
            Enter the command you wish to use:\040""";


    /**
     * For DEBUG or Test
     */
    public static final String commandMenuExtra = """
            -----------------------------------------------------------
            [Commands] What do you wish to do? These are the commands available:
              select   --> SELECT an item on the board.
              clear    --> Deselect the cards.
              insert   --> INSERT in the shelves.
               show    --> Show Game OBJECT in more detail(Selected Items, Goals, Board, Shelf, ...).
               more    --> More Options: Leave Match, Exit Game, Help (Command List,Assist Mode).
            Enter 'more' then 'help' commands and learn how to use the Chat""";

    public static final String chatHelp = """
            [Commands to use in the Command Line]     
                  /chat[nickname] 'message'       --> Use this command format to send a message to an online player,
                                                      replace nickname with a Online player's nickname,
                                                      replace 'message' with text.
                                                      (REMEMBER the 'space' between /chat[..] and 'message')
                      /open[nickname]             --> Use this command format to open the Chat with an online player,
                                                      when the chat is opened you can type directly the text you wish to sent.
            """;

    public static final String commandHelp = """
            [Commands] --> [Abbreviation]
              create            c
               join             j
              online            on
               help             he
              select            se
              clear             cl
              insert            in
               sort             so
               show             sh
               more             mo
              leave             le
               exit             ex
              ...               ...
            """;


    public static List<String> tips = new ArrayList<>();

    static {
        Collections.addAll(tips,
                //----------------MENU-------------------------------------------------------------------
                Color.YELLOW + "--Tip: You can text any ONLINE player." + Color.RESET,
                Color.YELLOW + "--Rule: A match can be played by at least 2 players and at most 4" + Color.RESET,
                Color.YELLOW + "--Rule: The creator of a match can decide the number of players" + Color.RESET,
                //-----------------WAITING------------------------------------------------------
                Color.YELLOW + "--Tip: Read the Game Rules to solve any doubts." + Color.RESET,
                Color.YELLOW + "--Tip: You can text any ONLINE player, even the ones who are not in this match" + Color.RESET,
                Color.YELLOW + "--Tip: Each match has his own chat group" + Color.RESET,
                //------------------SELECTED-0-ITEM -----------------------------------------------------------------
                Color.YELLOW + "--Rule: You can only select items with at least one free side" + Color.RESET,
                Color.YELLOW + "--Rule: If there is enough space in your shelf, you can select at most 3 items" + Color.RESET,
                Color.YELLOW + "--Rule: Each turn, you have to insert all of your selections in just ONE column of the shelf" + Color.RESET,
                Color.YELLOW + "--Rule: If there is enough space in your shelf, you can select at most 3 items each turn" + Color.RESET,
                Color.YELLOW + "--Rule: MULTIPLE items with the SAME COLOR NEAR each other in the shelf, will guarantee you MORE POINTS at the end of the match" + Color.RESET,
                Color.YELLOW + "--Tip: See the Common Goals and try to achieve more points (Use 'show' then 'cgoal' commands)" + Color.RESET,
                Color.YELLOW + "--Tip: See your Personal Goal and try to achieve more points (Use 'show' the 'pgoal' commands)" + Color.RESET,
                Color.YELLOW + "--Rule: Personal Goal Points are Private, only you can see them" + Color.RESET,
                Color.YELLOW + "--Rule: Personal Goal Points are not cumulative, they will be added, only at the end, to the final Score" + Color.RESET,
                Color.YELLOW + "--Rule: A Player cannot achieve the same Common Goal a second time" + Color.RESET,
                Color.YELLOW + "--Tip: You will get HIGHER Points if you achieve a Common Goal before everybody else" + Color.RESET,
                Color.YELLOW + "--Tip: You can't select more item in Diagonal, just on the same straight line" + Color.RESET,
                Color.YELLOW + "--Tip: Use 'show' command to see Game parts like SHELF, BOARD, GOALS, etc. in more detail" + Color.RESET,
                Color.YELLOW + "--Tip: You can see other players score and Shelf situation by using 'show' the 'stats" + Color.RESET,
                Color.YELLOW + "--Rule: Complete the SHELF FIRST and obtain Endgame Token (+1 point)" + Color.RESET,
                Color.YELLOW + "--Rule: The first to complete his/her shelf will initiate the Last Round" + Color.RESET,
                Color.YELLOW + "--Rule: When the Last Round ends the Match will also and the winner will be announced if there is one" + Color.RESET,
                Color.YELLOW + "--Rule: The top score player is THE winner, no Draws" + Color.RESET,
                Color.YELLOW + "--Tip: See the Group Points System using 'show' then 'shelf' command" + Color.RESET,
                //------------------SELECTED--------------------------------------------------------------------------------//
                Color.YELLOW + "--Rule: You can decide the insertion order of the items using the command 'sort'" + Color.RESET,
                Color.YELLOW + "--Tip: You can delete all your selections by using 'clear' command" + Color.RESET,
                Color.YELLOW + "--Tip: When you select an already selected item, you'll deselect it" + Color.RESET,
                Color.YELLOW + "--Rule: The items selected can be inserted in ONLY one column" + Color.RESET,
                Color.YELLOW + "--Rule: After 'insert' command, if you Confirm your Selections, you can't change your cards anymore " + Color.RESET

        );
    }

    public static final int MENU_TIPS = 3;
    public static final int WAIT_TIPS = 6;
    public static final int SEL_TIPS = tips.size() - 5;

    public static List<String> current_display;

    /**
     * This method is used to reset the display
     */
    public static void reset_display() {
        List<String> empty_display = new ArrayList<>(12);
        Collections.addAll(empty_display,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        );
        current_display = empty_display;
    }

    public static final String C = "\033[1;92m*\033[0m";
    public static final String B = "\033[1;97m*\033[0m";
    public static final String G = "\033[1;93m*\033[0m";
    public static final String F = "\033[1;94m*\033[0m";
    public static final String T = "\033[1;96m*\033[0m";
    public static final String P = "\033[1;95m*\033[0m";

    public static final String[] miniPG1 = {
            "[" + P + "][ ][" + F + "][ ][ ]",
            "[ ][ ][ ][ ][" + C + "]",
            "[ ][ ][ ][" + B + "][ ]",
            "[ ][" + G + "][ ][ ][ ]",
            "[ ][ ][ ][ ][ ]",
            "[ ][ ][" + T + "][ ][ ]"};

    public static final String[] miniPG2 = {
            "[ ][ ][ ][ ][ ]",
            "[ ][" + P + "][ ][ ][ ]",
            "[" + C + "][ ][" + G + "][ ][ ]",
            "[ ][ ][ ][ ][" + B + "]",
            "[ ][ ][ ][" + T + "][ ]",
            "[ ][ ][ ][ ][" + F + "]"};

    public static final String[] miniPG3 = {
            "[ ][ ][ ][ ][ ]",
            "[" + F + "][ ][ ][" + G + "][ ]",
            "[ ][ ][" + P + "][ ][ ]",
            "[ ][" + C + "][ ][ ][" + T + "]",
            "[ ][ ][ ][ ][ ]",
            "[" + B + "][ ][ ][ ][ ]"};

    public static final String[] miniPG4 = {
            "[ ][ ][ ][ ][" + G + "]",
            "[ ][ ][ ][ ][ ]",
            "[" + T + "][ ][" + F + "][ ][ ]",
            "[ ][ ][ ][" + P + "][ ]",
            "[ ][" + B + "][" + C + "][ ][ ]",
            "[ ][ ][ ][ ][ ]"};
    public static final String[] miniPG5 = {
            "[ ][ ][ ][ ][ ]",
            "[ ][" + T + "][ ][ ][ ]",
            "[ ][ ][ ][ ][ ]",
            "[ ][" + F + "][" + B + "][ ][ ]",
            "[ ][ ][ ][ ][" + P + "]",
            "[" + G + "][ ][ ][" + C + "][ ]"};
    public static final String[] miniPG6 = {
            "[ ][ ][" + T + "][ ][" + C + "]",
            "[ ][ ][ ][ ][ ]",
            "[ ][ ][ ][" + B + "][ ]",
            "[ ][ ][ ][ ][ ]",
            "[ ][" + G + "][ ][" + F + "][ ]",
            "[" + P + "][ ][ ][ ][ ]"};
    public static final String[] miniPG7 = {
            "[" + C + "][ ][ ][ ][ ]",
            "[ ][ ][ ][" + F + "][ ]",
            "[ ][" + P + "][ ][ ][ ]",
            "[" + T + "][ ][ ][ ][ ]",
            "[ ][ ][ ][ ][" + G + "]",
            "[ ][ ][" + B + "][ ][ ]"};
    public static final String[] miniPG8 = {
            "[ ][ ][ ][ ][" + F + "]",
            "[ ][" + C + "][ ][" + F + "][ ]",
            "[ ][ ][" + T + "][ ][ ]",
            "[" + P + "][ ][ ][ ][ ]",
            "[ ][ ][ ][" + B + "][ ]",
            "[ ][ ][ ][" + G + "][ ]"};
    public static final String[] miniPG9 = {
            "[ ][ ][" + G + "][ ][ ]",
            "[ ][ ][ ][ ][ ]",
            "[ ][ ][" + C + "][ ][ ]",
            "[ ][ ][ ][ ][" + B + "]",
            "[ ][" + T + "][ ][ ][" + P + "]",
            "[" + F + "][ ][ ][ ][ ]"};
    public static final String[] miniPG10 = {
            "[ ][ ][ ][ ][" + T + "]",
            "[ ][" + G + "][ ][ ][ ]",
            "[" + B + "][ ][ ][ ][ ]",
            "[ ][ ][ ][" + C + "][ ]",
            "[ ][" + F + "][ ][ ][ ]",
            "[ ][ ][ ][" + P + "][ ]"};
    public static final String[] miniPG11 = {
            "[ ][ ][" + P + "][ ][ ]",
            "[ ][" + B + "][ ][ ][ ]",
            "[" + G + "][ ][ ][ ][ ]",
            "[ ][ ][" + F + "][ ][ ]",
            "[ ][ ][ ][ ][" + C + "]",
            "[ ][ ][ ][" + T + "][ ]"};
    public static final String[] miniPG12 = {
            "[ ][ ][" + B + "][ ][ ]",
            "[ ][" + P + "][ ][ ][ ]",
            "[ ][ ][" + F + "][ ][ ]",
            "[ ][ ][ ][" + T + "][ ]",
            "[ ][ ][ ][ ][" + G + "]",
            "[" + C + "][ ][ ][ ][ ]"};


    public static final HashMap<Integer, String[]> PGoals = new HashMap<>();

    static {
        PGoals.put(1, miniPG1);
        PGoals.put(2, miniPG2);
        PGoals.put(3, miniPG3);
        PGoals.put(4, miniPG4);
        PGoals.put(5, miniPG5);
        PGoals.put(6, miniPG6);
        PGoals.put(7, miniPG7);
        PGoals.put(8, miniPG8);
        PGoals.put(9, miniPG9);
        PGoals.put(10, miniPG10);
        PGoals.put(11, miniPG11);
        PGoals.put(12, miniPG12);
    }


    public static final String GROUP_POINTS = """
            Points given based on the numbers of item of the same type near each other 
            \t\t-----------------------------
            \t\t¦ 3 [=] > (2) ¦ 5 [=] > (5) ¦  
            \t\t-----------------------------
            \t\t¦ 4 [=] > (3) ¦ 6+[=] > (8) ¦
            \t\t-----------------------------
            """;
}
