package org.am21.client.view;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.am21.client.view.TUI.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * You can find the virtual view data in this class
 */
public class Storage {


    public static int matchID;
    public static String[][] virtualBoard;
    public static List<String> players;
    public static String currentPlayer;
    public static List<Integer> scores;
    public static List<Integer> hiddenPoints;
    public static List<String> commonGoal;
    public static List<Integer> commonGoalScore;
    public static int personalGoal;
    public static List<String[][]> shelves;
    public static String gamePhase;
    public static List<String> currentPlayerHand;
    public static boolean endGameToken;


    /**
     * Once the JSON is received, it is parsed and the data is stored in the corresponding variables
     * the key strings of get methods are generated automatically,
     * check them first when you get a wrong answer
     *
     * @param json        the JSON string received from the server
     * @param playerIndex
     */
    public static void setFullViewVariables(String json, int playerIndex) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        virtualBoard = jsonObject.getObject("board", String[][].class);
        players = jsonObject.getJSONArray("players").toJavaList(String.class);
        currentPlayer = jsonObject.getString("currentPlayer");
        scores = jsonObject.getJSONArray("scores").toJavaList(Integer.class);
        hiddenPoints = jsonObject.getJSONArray("hiddenPoints").toJavaList(Integer.class);
        commonGoal = jsonObject.getJSONArray("commonGoals").toJavaList(String.class);
        commonGoalScore = jsonObject.getJSONArray("commonGoalScores").toJavaList(Integer.class);

        personalGoal = jsonObject.getJSONArray("personalGoals").toJavaList(Integer.class).get(playerIndex);

        JSONArray temp = jsonObject.getJSONArray("shelves");
        shelves = new java.util.ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            shelves.add(temp.getObject(i, String[][].class));
        }
        gamePhase = jsonObject.getString("gamePhase");
        currentPlayerHand = jsonObject.getJSONArray("currentPlayerHand").toJavaList(String.class);
        matchID = jsonObject.getInteger("matchID");
        endGameToken = jsonObject.getBoolean("endGameToken");
    }

    /**
     * get the index of the player in the list
     * use this index to map other player data in other lists
     *
     * @param player the name of the player
     * @return the index of the player in the list
     */
    public static int getPlayerIndex(String player) {
        if (players != null) {
            return players.indexOf(player);
        }
        return 0;
    }

    public static void convertBackHand(String jsonHand) {
        JSONArray jsonArray = JSONObject.parseArray(jsonHand);
        currentPlayerHand = jsonArray.toJavaList(String.class);

    }

    public static void convertBackBoard(String jsonBoard) {
        JSONArray jsonArray = JSONObject.parseArray(jsonBoard);
        virtualBoard = (String[][]) jsonArray.toArray();
    }

    public static void convertBackShelves(String jsonShelves) {
        JSONArray jsonArray = JSONObject.parseArray(jsonShelves);
        virtualBoard = (String[][]) jsonArray.toArray();
    }

    public static final String MYSHELFIE = "" +
                                           " __  __       ____  _          _  __ _\n" +
                                           "|  \\/  |_   _/ ___|| |__   ___| |/ _(_) ___\n" +
                                           "| |\\/| | | | \\___ \\| '_ \\ / _ \\ | |_| |/ _ \\\n" +
                                           "| |  | | |_| |___) | | | |  __/ |  _| |  __/\n" +
                                           "|_|  |_|\\__, |____/|_| |_|\\___|_|_| |_|\\___|\n" +
                                           "        |___/\n";


    public static final String MYSHELFIE2 = "" +
                                            "                                                                .&%&&&&&                                                                              \n" +
                                            "  (,,*/%       (***/&                               ,&&&..&,&&%                                         #&&%                               \n" +
                                            "  &,&&&&.      &&&&*..                             &&&&. .&&&&&.   ,&&&,.                     &&&..  ,&&....#&&                            \n" +
                                            "   &%&&,       #&&&.                               &&&&.  #&&..     /&(.                      (&#. (&&.. &&&.&&.                           \n" +
                                            "   &%&&&.     /&&&&.                                .&&&.           (&&.                      #&#. .&&. #&&&&&&.                           \n" +
                                            "   &(&#&&.   #&%(&&.      ,&&&&&..   .&&&..          %&&&&&         #&&.                      #&#. /&&.   ....                             \n" +
                                            "   %*%%.%%. %&&.,&&.         .&&&   .&&&.              &&&&&&&      %&&&&&&&&*&               (&#. ,.&.      ,&&&.                         \n" +
                                            "   %,%%. %%%%&. ,%&.          &&&&&&&&..                  %&&&&&    %%%&&...%%%&.   &,&&&&&   /&#.  *&&. #%   ....    &#%%&&&              \n" +
                                            "   #,%%.  %%%. *.%%.           /.%%%%.                      %%%%%.  %%%%.    %%%. %%%.     %& /%#. ,..%%.... ,%%%.  .%..    %%.            \n" +
                                            "   (.%%. #%%%. %,%%.   .%*%%    .%%,.             %#%%%*,    %%%..  %%%.    .%%.  /%%%%%%%%%../%(.    #%%.   %%##  /%%%%%%%%%%.            \n" +
                                            "   /.%%.  .... %*%%.  ,#%%%%%. ,%%.              %%%%%%%%.  %%%%#.  %%#.    %##.  /%#.   #### /#(.     %%,   %%##. #%#.   %%%#             \n" +
                                            "   %.%%#       ###%.  *%#%..#%%%%.                %##%%((#%#####.   ###.    .##.  #%##   (###.*#(.    %%#.   ####.  %##,  (%#%.            \n" +
                                            "  #######.   /######(  /#######..                   .(*#####,#..   #####.  #####.   .#####*...####   %####.  ####.   .###%##..";


    public static final String MYSHELFIE3 = "" +
                                            "\n" +
                                            "                                                                                                                                                                                  \n" +
                                            "                                                                                                                                                                                  \n" +
                                            "MMMMMMMM               MMMMMMMM                                 SSSSSSSSSSSSSSS hhhhhhh                                 lllllll     ffffffffffffffff    iiii                      \n" +
                                            "M:::::::M             M:::::::M                               SS:::::::::::::::Sh:::::h                                 l:::::l    f::::::::::::::::f  i::::i                     \n" +
                                            "M::::::::M           M::::::::M                              S:::::SSSSSS::::::Sh:::::h                                 l:::::l   f::::::::::::::::::f  iiii                      \n" +
                                            "M:::::::::M         M:::::::::M                              S:::::S     SSSSSSSh:::::h                                 l:::::l   f::::::fffffff:::::f                            \n" +
                                            "M::::::::::M       M::::::::::Myyyyyyy           yyyyyyy     S:::::S             h::::h hhhhh           eeeeeeeeeeee     l::::l   f:::::f       ffffffiiiiiii     eeeeeeeeeeee    \n" +
                                            "M:::::::::::M     M:::::::::::M y:::::y         y:::::y      S:::::S             h::::hh:::::hhh      ee::::::::::::ee   l::::l   f:::::f             i:::::i   ee::::::::::::ee  \n" +
                                            "M:::::::M::::M   M::::M:::::::M  y:::::y       y:::::y        S::::SSSS          h::::::::::::::hh   e::::::eeeee:::::ee l::::l  f:::::::ffffff        i::::i  e::::::eeeee:::::ee\n" +
                                            "M::::::M M::::M M::::M M::::::M   y:::::y     y:::::y          SS::::::SSSSS     h:::::::hhh::::::h e::::::e     e:::::e l::::l  f::::::::::::f        i::::i e::::::e     e:::::e\n" +
                                            "M::::::M  M::::M::::M  M::::::M    y:::::y   y:::::y             SSS::::::::SS   h::::::h   h::::::he:::::::eeeee::::::e l::::l  f::::::::::::f        i::::i e:::::::eeeee::::::e\n" +
                                            "M::::::M   M:::::::M   M::::::M     y:::::y y:::::y                 SSSSSS::::S  h:::::h     h:::::he:::::::::::::::::e  l::::l  f:::::::ffffff        i::::i e:::::::::::::::::e \n" +
                                            "M::::::M    M:::::M    M::::::M      y:::::y:::::y                       S:::::S h:::::h     h:::::he::::::eeeeeeeeeee   l::::l   f:::::f              i::::i e::::::eeeeeeeeeee  \n" +
                                            "M::::::M     MMMMM     M::::::M       y:::::::::y                        S:::::S h:::::h     h:::::he:::::::e            l::::l   f:::::f              i::::i e:::::::e           \n" +
                                            "M::::::M               M::::::M        y:::::::y             SSSSSSS     S:::::S h:::::h     h:::::he::::::::e          l::::::l f:::::::f            i::::::ie::::::::e          \n" +
                                            "M::::::M               M::::::M         y:::::y              S::::::SSSSSS:::::S h:::::h     h:::::h e::::::::eeeeeeee  l::::::l f:::::::f            i::::::i e::::::::eeeeeeee  \n" +
                                            "M::::::M               M::::::M        y:::::y               S:::::::::::::::SS  h:::::h     h:::::h  ee:::::::::::::e  l::::::l f:::::::f            i::::::i  ee:::::::::::::e  \n" +
                                            "MMMMMMMM               MMMMMMMM       y:::::y                 SSSSSSSSSSSSSSS    hhhhhhh     hhhhhhh    eeeeeeeeeeeeee  llllllll fffffffff            iiiiiiii    eeeeeeeeeeeeee  \n" +
                                            "                                     y:::::y                                                                                                                                      \n" +
                                            "                                    y:::::y                                                                                                                                       \n" +
                                            "                                   y:::::y                                                                                                                                        \n" +
                                            "                                  y:::::y                                                                                                                                         \n" +
                                            "                                 yyyyyyy                                                                                                                                          \n" +
                                            "                                                                                                                                                                                  \n" +
                                            "                                                                                                                                                                                  \n";
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

    public static final String MYSHELFIE5 = "" +
                                            "\n" +
                                            " .----------------.  .----------------.    .----------------.  .----------------.  .----------------.  .----------------.  .----------------.  .----------------.  .----------------. \n" +
                                            "| .--------------. || .--------------. |  | .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |\n" +
                                            "| | ____    ____ | || |  ____  ____  | |  | |    _______   | || |  ____  ____  | || |  _________   | || |   _____      | || |  _________   | || |     _____    | || |  _________   | |\n" +
                                            "| ||_   \\  /   _|| || | |_  _||_  _| | |  | |   /  ___  |  | || | |_   ||   _| | || | |_   ___  |  | || |  |_   _|     | || | |_   ___  |  | || |    |_   _|   | || | |_   ___  |  | |\n" +
                                            "| |  |   \\/   |  | || |   \\ \\  / /   | |  | |  |  (__ \\_|  | || |   | |__| |   | || |   | |_  \\_|  | || |    | |       | || |   | |_  \\_|  | || |      | |     | || |   | |_  \\_|  | |\n" +
                                            "| |  | |\\  /| |  | || |    \\ \\/ /    | |  | |   '.___`-.   | || |   |  __  |   | || |   |  _|  _   | || |    | |   _   | || |   |  _|      | || |      | |     | || |   |  _|  _   | |\n" +
                                            "| | _| |_\\/_| |_ | || |    _|  |_    | |  | |  |`\\____) |  | || |  _| |  | |_  | || |  _| |___/ |  | || |   _| |__/ |  | || |  _| |_       | || |     _| |_    | || |  _| |___/ |  | |\n" +
                                            "| ||_____||_____|| || |   |______|   | |  | |  |_______.'  | || | |____||____| | || | |_________|  | || |  |________|  | || | |_____|      | || |    |_____|   | || | |_________|  | |\n" +
                                            "| |              | || |              | |  | |              | || |              | || |              | || |              | || |              | || |              | || |              | |\n" +
                                            "| '--------------' || '--------------' |  | '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' |\n" +
                                            " '----------------'  '----------------'    '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------' \n";

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


    public static final String PG1 = "    0         1         2         3         4\n" +
                                     "[_" + Color.PLANTS + "_][______._][_" + Color.FRAMES + "_][______._][______._]\n" +
                                     "[______._][______._][______._][______._][__" + Color.CATS + "__]\n" +
                                     "[______._][______._][______._][_" + Color.BOOKS + "__][______._]\n" +
                                     "[______._][_" + Color.GAMES + "__][______._][______._][______._]\n" +
                                     "[______._][______._][______._][______._][______._]\n" +
                                     "[______._][______._][" + Color.TROPHIES + "][______._][______._]";
    public static final String PG2 = "    0         1         2         3         4\n" +
                                     "[______._][______._][______._][______._][______._]\n" +
                                     "[______._][_" + Color.PLANTS + "_][______._][______._][______._]\n" +
                                     "[__" + Color.CATS + "__][______._][_" + Color.GAMES + "__][______._][______._]\n" +
                                     "[______._][______._][______._][______._][_" + Color.BOOKS + "__]\n" +
                                     "[______._][______._][______._][" + Color.TROPHIES + "][______._]\n" +
                                     "[______._][______._][______._][______._][_" + Color.FRAMES + "_]";
    public static final String PG3 = "    0         1         2         3         4\n" +
                                     "[______._][______._][______._][______._][______._]\n" +
                                     "[_" + Color.FRAMES + "_][______._][______._][_" + Color.GAMES + "__][______._]\n" +
                                     "[______._][______._][_" + Color.PLANTS + "_][______._][______._]\n" +
                                     "[______._][__" + Color.CATS + "__][______._][______._][" + Color.TROPHIES + "]\n" +
                                     "[______._][______._][______._][______._][______._]\n" +
                                     "[_" + Color.BOOKS + "__][______._][______._][______._][______._]";
    public static final String PG4 = "    0         1         2         3         4\n" +
                                     "[______._][______._][______._][______._][_" + Color.GAMES + "__]\n" +
                                     "[______._][______._][______._][______._][______._]\n" +
                                     "[" + Color.TROPHIES + "][______._][_" + Color.FRAMES + "_][______._][______._]\n" +
                                     "[______._][______._][______._][_" + Color.PLANTS + "_][______._]\n" +
                                     "[______._][_" + Color.BOOKS + "__][__" + Color.CATS + "__][______._][______._]\n" +
                                     "[______._][______._][______._][______._][______._]";
    public static final String PG5 = "    0         1         2         3         4\n" +
                                     "[______._][______._][______._][______._][______._]\n" +
                                     "[______._][" + Color.TROPHIES + "][______._][______._][______._]\n" +
                                     "[______._][______._][______._][______._][______._]\n" +
                                     "[______._][_" + Color.FRAMES + "_][_" + Color.BOOKS + "__][______._][______._]\n" +
                                     "[______._][______._][______._][______._][_" + Color.PLANTS + "_]\n" +
                                     "[_" + Color.GAMES + "__][______._][______._][__" + Color.CATS + "__][______._]";
    public static final String PG6 = "    0         1         2         3         4\n" +
                                     "[______._][______._][" + Color.TROPHIES + "][______._][__" + Color.CATS + "__]\n" +
                                     "[______._][______._][______._][______._][______._]\n" +
                                     "[______._][______._][______._][_" + Color.BOOKS + "__][______._]\n" +
                                     "[______._][______._][______._][______._][______._]\n" +
                                     "[______._][_" + Color.GAMES + "__][______._][_" + Color.FRAMES + "_][______._]\n" +
                                     "[_" + Color.PLANTS + "_][______._][______._][______._][______._]";
    public static final String PG7 = "    0         1         2         3         4\n" +
                                     "[__" + Color.CATS + "__][______._][______._][______._][______._]\n" +
                                     "[______._][______._][______._][_" + Color.FRAMES + "_][______._]\n" +
                                     "[______._][_" + Color.PLANTS + "_][______._][______._][______._]\n" +
                                     "[" + Color.TROPHIES + "][______._][______._][______._][______._]\n" +
                                     "[______._][______._][______._][______._][_" + Color.GAMES + "__]\n" +
                                     "[______._][______._][_" + Color.BOOKS + "__][______._][______._]";
    public static final String PG8 = "    0         1         2         3         4\n" +
                                     "[______._][______._][______._][______._][_" + Color.FRAMES + "_]\n" +
                                     "[______._][__" + Color.CATS + "__][______._][______._][______._]\n" +
                                     "[______._][______._][" + Color.TROPHIES + "][______._][______._]\n" +
                                     "[_" + Color.PLANTS + "_][______._][______._][______._][______._]\n" +
                                     "[______._][______._][______._][_" + Color.BOOKS + "__][______._]\n" +
                                     "[______._][______._][______._][_" + Color.GAMES + "__][______._]";
    public static final String PG9 = "    0         1         2         3         4\n" +
                                     "[______._][______._][_" + Color.GAMES + "__][______._][______._]\n" +
                                     "[______._][______._][______._][______._][______._]\n" +
                                     "[______._][______._][__" + Color.CATS + "__][______._][______._]\n" +
                                     "[______._][______._][______._][______._][_" + Color.BOOKS + "__]\n" +
                                     "[______._][" + Color.TROPHIES + "][______._][______._][_" + Color.PLANTS + "_]\n" +
                                     "[_" + Color.FRAMES + "_][______._][______._][______._][______._]";
    public static final String PG10 = "    0         1         2         3         4\n" +
                                      "[______._][______._][______._][______._][" + Color.TROPHIES + "]\n" +
                                      "[______._][_" + Color.GAMES + "__][______._][______._][______._]\n" +
                                      "[_" + Color.BOOKS + "__][______._][______._][______._][______._]\n" +
                                      "[______._][______._][______._][__" + Color.CATS + "__][______._]\n" +
                                      "[______._][_" + Color.FRAMES + "_][______._][______._][______._]\n" +
                                      "[______._][______._][______._][_" + Color.PLANTS + "_][______._]";
    public static final String PG11 = "    0         1         2         3         4\n" +
                                      "[______._][______._][_" + Color.PLANTS + "_][______._][______._]\n" +
                                      "[______._][_" + Color.BOOKS + "__][______._][______._][______._]\n" +
                                      "[_" + Color.GAMES + "__][______._][______._][______._][______._]\n" +
                                      "[______._][______._][_" + Color.FRAMES + "_][______._][______._]\n" +
                                      "[______._][______._][______._][______._][__" + Color.CATS + "__]\n" +
                                      "[______._][______._][______._][" + Color.TROPHIES + "][______._]";
    public static final String PG12 = "    0         1         2         3         4\n" +
                                      "[______._][______._][_" + Color.BOOKS + "__][______._][______._]\n" +
                                      "[______._][_" + Color.PLANTS + "_][______._][______._][______._]\n" +
                                      "[______._][______._][_" + Color.FRAMES + "_][______._][______._]\n" +
                                      "[______._][______._][______._][" + Color.TROPHIES + "][______._]\n" +
                                      "[______._][______._][______._][______._][_" + Color.GAMES + "__]\n" +
                                      "[__" + Color.CATS + "__][______._][______._][______._][______._]";

    //TODO: Personal Goal Table for points(one for all)
    public static final String PGTable="";
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
              hand    --> Show selected items.
              pgoal   --> See your Personal Goal.
              cgoal   --> See Common Goals.
              shelf   --> See your shelves and the insertion limit.
              board   --> See Board.
              stats   --> See All PLAYERS STATS(Shelf and Scores).
              rules   --> See Game Rules (to clarify any doubt).
              end     --> Show if the Endgame Token is taken (if it is, then it's the last round).
              online  --> Show a list of Online Players, so you can chat with them.
              timer   --> Show timer.
             'Enter'  --> Go Back to Play.
            -----------------------------------------------------------
            Type the commands you wish to use:\040""";
    public static final String menuOption = """
            -----------------------------------------------------------
            [Commands] Menu Option:
              create   --> Create a new match.
               join    --> Join a match.
              online   --> Show Online Players.
               exit    --> Exit game.
               help    --> Activate Or Deactivate Assist Mode
            To send a message to a online player type ‘/chat[nickname]’ in the console.""";

    public static final String waitingAction = """
            The match has not started yet. Please wait more players to join...
            -----------------------------------------------------------
            [Commands] These are the commands available:
              leave    --> Leave Match.
              rules    --> Read Game Rules.
              online   --> Show Online Players.
            To send a message in the Match type ‘/chat’ in the console.
            To send a message to a online player type ‘/chat[nickname]’ in the console.""";

    public static final String commandMenu0 = """
            [Commands] Wait for your turn. Meanwhile, you can spectate typing 'show':
              show     --> Show Game Object in more detail(Goals, Board, Shelf, ...).
              more     --> More Options: Leave Match, Exit Game, Help (Assist Mode).
            To send a message in the Match group type ‘/chat’ followed by a 'space' and your message.
            To send a message to an online player type ‘/chat[nickname]’ followed by a 'space' and your message.""";

    public static final String commandMenu1 = """
            [Commands] Use the 'select' command to SELECT item you would like to pick:
              select   --> Select an item on the board.
              show     --> Show Game Object in more detail(Selected items, Goals, Board, Shelf, ...).
              more     --> More Options: Leave Match, Exit Game, Help (Assist Mode)
            To send a message in the Match group type ‘/chat’ followed by a 'space' and your message.
            To send a message to an online player type ‘/chat[nickname]’ followed by a 'space' and your message.""";
    public static final String commandMenu2 = """
            [Commands] Now you can SELECT another item OR INSERT the items in the Shelf:
              select   --> Select another item on the board.
              sort     --> Change selected items order(at least 2 items selected).
              deselect --> Deselect all your selection.
              insert   --> Save your selections and Insert in the shelf.
              show     --> Show Game Object in more detail(Selected items, Goals, Board, Shelf, ...).
              more     --> More Options: Leave Match, Exit Game, Help (Assist Mode)
            To send a message in the Match group type ‘/chat’ followed by a 'space' and your message.
            To send a message to an online player type ‘/chat[nickname]’ followed by a 'space' and your message.""";
    public static final String commandInsert = """
            -----------------------------------------------------------
            [Commands] You can SORT the items you chose before INSERTING in the Shelf:
               sort    --> Change the ORDER of your cards.
               show    --> Show Game OBJECT in more detail(GOALS,SHELF,...).
              insert   --> INSERT directly into the SHELF
                n      --> EXIT Insertion.
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
                y      --> Yes. I want to SELECT AGAIN.
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
                r      --> Retry.
                n      --> DELETE your choice and EXIT Insertion.
            -----------------------------------------------------------
            Enter the option you wish to select:\040""";

    public static final String MoreOptions = """
            [Commands] These are the option available.
              leave    --> LEAVE the Match (if you leave, the match will end for everyone).
              exit     --> EXIT from the Game MyShelfie.
              help     --> Activate or Deactivate Assist Mode.
              'Enter'  --> Go Back to Play.
            -----------------------------------------------------------
            Enter the command you wish to use:\040""";


    /**
     * For DEBUG or Test
     */
    public static final String commandMenuExtra = """
            -----------------------------------------------------------
            [Commands] What do you wish to do? These are the commands available:
              select   --> Select an item on the board.
             deselect  --> Deselect the cards.
               sort    --> Change selected items order(at least 2 items selected).
              insert   --> Insert in the shelves.
               show    --> Show Game Object in more detail(Selected Items, Goals, Board, Shelf, ...).
               more    --> More Options: Leave Match, Exit Game, Help (Assist Mode).
            To send a message in the Match group type ‘/chat’ followed by a 'space' and your message.
            To send a message to an online player type ‘/chat[nickname]’ followed by a 'space' and your message.""";



    public static final int SEL_TIPS=15;

    public static List<String> tips = new ArrayList<>();

    static {
        Collections.addAll(tips,
                //------------------SELECTED-0-ITEM -----------------------------------------------------------------
                Color.YELLOW + "--Tip: Use command abbreviation 'se' to SELECT" + Color.RESET,
                Color.YELLOW + "--Rule: You can only select items with at least one free side" + Color.RESET,
                Color.YELLOW + "--Rule: If there is enough space in your shelf, you can select at most 3 items" + Color.RESET,
                Color.YELLOW + "--Rule: Each turn, you have to insert all of your selections in just ONE column of the shelf" + Color.RESET,
                Color.YELLOW + "--Rule: If there is enough space in your shelf, you can select at most 3 items each turn" + Color.RESET,
                Color.YELLOW + "--Rule: Multiple item with the same color near each other in the shelf, will guarantee you more points at the end of the match" + Color.RESET,
                Color.YELLOW + "--Tip: See the Common Goals and try to achieve more points (Use 'show' then 'cgoal' commands)" + Color.RESET,
                Color.YELLOW + "--Tip: See your Personal Goal and try to achieve more points (Use 'show' the 'pgoal' commands)" + Color.RESET,
                Color.YELLOW + "--Tip: Use command abbreviation 'sh' to SHOW OBJECT" + Color.RESET,
                Color.YELLOW + "--Tip: Use command abbreviation 'mo' to open More OPTIONS" + Color.RESET,
                Color.YELLOW + "--Tip: Open More Options and then use command abbreviation 'le' to LEAVE this Match" + Color.RESET,
                Color.YELLOW + "--Tip: Open More Options and then use command abbreviation 'ex' to EXIT from MyShelfie" + Color.RESET,
                Color.YELLOW + "--Tip: Each match has his own chat group" + Color.RESET,
                Color.YELLOW + "--Tip: You can text any ONLINE player, even the ones who are not in this match" + Color.RESET,

                //------------------SELECTED--------------------------------------------------------------------------------//
                Color.YELLOW + "--Rule: You can decide the insertion order of the items using the command 'sort'" + Color.RESET,
                Color.YELLOW + "--Tip: You can delete all your selections by using 'deselect' command" + Color.RESET,
                Color.YELLOW + "--Tip: Use command abbreviation 'in' to INSERT" + Color.RESET,
                Color.YELLOW + "--Tip: Use command abbreviation 'so' to SORT" + Color.RESET,
                Color.YELLOW + "--Tip: Use command abbreviation 'de' to DESELECT" + Color.RESET
        );
    }

    public static List<String> current_display;

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

    public static final String[] miniCG1={
            "¦ [=][=]  ¦",
            "¦ [=][=]  ¦",
            "¦   x2    ¦"
    };
    public static final String[] miniCG2={
            "¦ :  [≠]  ¦",
            "¦ :  6H   ¦",
            "¦ :  x2   ¦"
    };
    public static final String[] miniCG3={
            "¦ :  [=]  ¦",
            "¦ :  4H   ¦",
            "¦ :  x2   ¦"
    };
    public static final String[] miniCG4={
            "¦   [=]   ¦",
            "¦   [=]   ¦",
            "¦    x6   ¦"
    };
    public static final String[] miniCG5={
            "¦ :   3*≠ ¦",
            "¦ :   6H  ¦",
            "¦ :   x3  ¦"
    };
    public static final String[] miniCG6={
            "¦ 5W  [≠] ¦",
            "¦- - - - -¦",
            "¦   x2    ¦"
    };
    public static final String[] miniCG7={
            "¦ 5W  3*≠ ¦",
            "¦- - - - -¦",
            "¦   x4    ¦"
    };
    public static final String[] miniCG8={
            "¦[=]---[=]¦",
            "¦ ¦     ¦ ¦",
            "¦[=]---[=]¦"
    };
    public static final String[] miniCG9={
            "¦ 8 x [=] ¦",
            "¦   . .   ¦",
            "¦  : : :  ¦"
    };
    public static final String[] miniCG10={
            "¦[=]   [=]¦",
            "¦   [=]   ¦",
            "¦[=]   [=]¦"
    };
    public static final String[] miniCG11={
            "¦[=]    5D¦",
            "¦   [=]   ¦",
            "¦      [=]¦"
    };
    public static final String[] miniCG12={
            "¦¯¯|15/20x¦",
            "¦¯¯|¯¯|   ¦",
            "¦??|¯¯|¯¯|¦"
    };

    public static final String GROUP_POINTS= """
             --------------------------
            ¦ 3 [=]: (2)  ¦ 5 [=]: (5) ¦  
             --------------------------
            ¦ 4 [=]: (3)  ¦ 6+[=]: (8) ¦
            ---------------------------
            """;



}
