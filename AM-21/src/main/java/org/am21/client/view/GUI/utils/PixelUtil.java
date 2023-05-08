package org.am21.client.view.GUI.utils;

import java.awt.*;

public class PixelUtil {
    /**
     * screen size util
     */
    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int pcWidth = (int) screenSize.getWidth();
    public static int pcHeight = (int) screenSize.getHeight();

    /**
     * dimension util
     */

    public static int gameBoardHW = pcHeight*74/100;
    public static int myShelfBoardHW = pcHeight*44/100;

    public static int goalCardH = 165;
    public static int goalCardW = 245;

    public static int bagHW = pcWidth*5/100;
    public static int livingRoomMenuHW = pcWidth*3/100;
    public static int insertClearButtonW = pcWidth*230/1000;
    public static int bottomButtonH = pcWidth*47/1000;

    public static int userMeHW = 135;
    public static int enemyHW = pcHeight*6/100;
    public static int enemyShelfHW = pcHeight*18/100;

    //
    public static int gameBoardCellHW = pcWidth*47/1000;
    public static int gameBoardItemHW = gameBoardCellHW*9114/10000;

    //public static int handGridHW =
    /**
     * position util
     */

    // X position
    public static int commonX_1 = pcHeight*2/100;
    public static int commonX_2 = pcHeight*8/100;
    public static int commonX_3 = pcHeight*36/100;
    public static int commonX_4 = pcWidth*7/10;
    public static int commonX_5 = pcWidth*8125/10000;

    public static int personalGoalX = pcWidth*709/1000;

    public static int livingRoomMenuX = pcWidth*964/1000;

    public static int insertButtonX = pcHeight*358/1000;
    public static int clearButtonX=pcHeight*734/1000;
    public static int userMeX = pcWidth*72/100;


    public static int gameBoardGridX = pcHeight*397/1000;
   // public static int handGridX = 1200;


    // Y position
    public static int commonY_1 = pcHeight*3/100;
    public static int commonY_2 = pcHeight*23/100;
    public static int commonY_3 = pcHeight*44/100;
    public static int commonY_4 = pcHeight*843/1000;



    public static int gameBoardY = pcHeight/10;
    public static int myShelfBoardY = pcHeight*4/10;
    public static int personalGoalY= pcHeight*16/100;
    public static int commonGoalY_A = pcHeight*762/10000;
    public static int commonGoalY_B = pcHeight*238/1000;
    public static int bagY =  pcHeight/100;
    public static int livingRoomMenuY = pcWidth*6/1000;

    public static int gameBoardGridY =  pcHeight*138/1000;

    //public static int handGridY =

    /**
     * chat util
     */
    public static int cPanelX = pcWidth*6/1000;
    public static int cPanelY = pcWidth*393/1000;
    public static int cPanelW = pcHeight*324/1000;
    public static int cPanelH = pcHeight*286/1000;

    public static int cScrollW = pcHeight*324/1000;
    public static int cScrollH = pcHeight*238/1000;
    public static int cTextFieldY = pcHeight*24/100;
    public static int cTextFieldW = pcHeight*229/1000;
    public static int cTextFieldH = pcHeight*38/1000;
    public static int cButtonX = pcWidth*146/1000;
    public static int cButtonY = pcHeight*24/100;
    public static int cButtonW = pcHeight*9/100;
    public static int cButtonH = pcHeight*38/1000;

    /**
     * Shelf grid Util
     */

    //position
    public static int myGridX =pcHeight*117/100;
    public static int myGridY =pcWidth*2696/10000;
    public static int enemyGridX = pcHeight*101/1000;
    public static int enemyAGridY = pcHeight*419/10000;
    public static int enemyBGridY = pcHeight*2419/10000;
    public static int enemyCGridY = pcWidth*2827/10000;

    //cell size
    public static int myCellH = pcHeight*61/1000;
    public static int myCellW = pcHeight*705/10000;
    public static int enemyCellH = pcHeight*248/10000;
    public static int enemyCellW = pcWidth*179/10000;

    //item size
    public static int myItemHW = pcWidth*315/10000;
    public static int enemyItemHW = pcHeight*21/1000;

    /**
     * score token util
     */

    public static int endGameTokenHW = pcHeight*781/10000;
    public static int endGameTokenBounds = pcWidth/14;
    public static int endGameTokenX = pcWidth*3405/10000;
    public static int endGameTokenY = pcWidth*2952/10000;
    public static int endGameTokenOriented = 9;

    public static int endGameTokenRotateX = pcWidth*95/10000;
    public static int endGameTokenRotateY = pcHeight/7;


    public static int commonGoalTokenHW = pcHeight*705/10000;
    public static int commonGoalTokenBounds = 90;
    public static int commonGoalTokenX = 138;
    public static int commonGoalTopTokenY = 31;
    public static int commonGoalBottomTokenY = 201;
    public static int commonGoalTokenOriented = -8;

    public static int commonGoalTokenRotateX = 90;
    public static int commonGoalTokenRotateY = 5;
}
