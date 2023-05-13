package org.am21.client.view.GUI.utils;

import java.awt.*;

public class PixelUtil {
    /**
     * screen size util
     */
    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int pcWidth = (int) screenSize.getWidth();
    public static int pcHeight = (int) screenSize.getHeight();

   // public static int pcWidth = 1680;
   // public static int pcHeight = 1050;


    /**
     * dimension util
     */

    public static int gameBoardW = pcWidth*4625/10000;
    public static int gameBoardH = pcHeight*74/100;
    public static int myShelfBoardW = pcWidth*275/1000;
    public static int myShelfBoardH = pcHeight*44/100;

    public static int handBoardW=pcWidth*275/1000;
    public static int handBoardH=pcHeight*75/1000;

    public static int commonGoalCardW = pcWidth*1458/10000;
    public static int commonGoalCardH = pcHeight*157/1000;
    public static int personalGoalCardW = pcWidth*982/10000;
    public static int personalGoalCardH = pcHeight*2324/10000;


    public static int bagW = pcWidth*5/100;
    public static int bagH = pcHeight*8/100;
    public static int livingRoomMenuW = pcWidth*3/100;
    public static int livingRoomMenuH = pcHeight*476/10000;

    public static int insertClearButtonW = pcWidth*230/1000;
    public static int insertClearButtonH = pcHeight*75/1000;

    public static int userMeW = pcWidth*804/10000;
    public static int userMeH = pcHeight*1286/10000;

    public static int enemyW = pcWidth*375/10000;
    public static int enemyH = pcHeight*6/100;

    public static int enemyShelfW = pcWidth*1125/10000;
    public static int enemyShelfH = pcHeight*18/100;

    //
    public static int gameBoardCellW = pcWidth*47/1000;
    public static int gameBoardCellH = pcHeight*75/1000;

    public static int gameBoardItemW = gameBoardCellW *9114/10000;
    public static int gameBoardItemH = gameBoardCellH *9114/10000;


    public static int chairW = pcWidth*375/10000;
    public static int chairH = 64;
    /**
     * position util
     */

    // X position
    public static int commonX_1 = pcWidth*125/10000;
    public static int commonX_2 = pcWidth*5/100;
    public static int commonX_3 = pcWidth*225/1000;
    public static int commonX_4 = pcWidth*7/10;
    public static int commonX_5 = pcWidth*8125/10000;

    public static int personalGoalX = pcWidth*709/1000;

    public static int livingRoomMenuX = pcWidth*964/1000;

    public static int insertButtonX = pcWidth*2238/10000;
    public static int clearButtonX=pcWidth*459/1000;
    public static int userMeX = pcWidth*72/100;

    public static int gameBoardGridX = pcWidth*248/1000;


    public static int chairManMeX = pcWidth*6845/10000;

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
    public static int bagY = pcHeight/100;
    public static int livingRoomMenuY = pcHeight*95/10000;

    public static int gameBoardGridY =  pcHeight*138/1000;

    public static int chairManMeY = pcHeight/100;
    public static int chairManAY = pcHeight*105/10;
    public static int chairManBY = pcHeight*295/1000;
    public static int chairManCY = pcHeight*51/100;

    /**
     * chat util
     */
    public static int cPanelX = pcWidth*6/1000;
    public static int cPanelY = pcHeight*6286/10000;
    public static int cPanelW = pcWidth*2024/10000;
    public static int cPanelH = pcHeight*286/1000;

    public static int cScrollW = pcWidth*2024/10000;
    public static int cScrollH = pcHeight*238/1000;
    public static int cTextFieldY = pcHeight*24/100;
    public static int cTextFieldW = pcWidth*143/1000;
    public static int cTextFieldH = pcHeight*38/1000;
    public static int cButtonX = pcWidth*146/1000;
    public static int cButtonY = pcHeight*24/100;
    public static int cButtonW = pcWidth*56/1000;
    public static int cButtonH = pcHeight*38/1000;

    /**
     * Shelf grid Util
     */

    //position
    public static int myGridX = pcWidth*727/1000;
    public static int myGridY = pcHeight*428/1000;
    public static int enemyGridX = pcWidth*6131/100000;
    public static int enemyAGridY = pcHeight*41/1000;
    public static int enemyBGridY = pcHeight*241/1000;
    public static int enemyCGridY = pcHeight*45143/100000;

    //cell size
    public static int myCellH = pcHeight*61/1000;
    public static int myCellW = pcWidth*4405/100000;
    public static int enemyCellH = pcHeight*25/1000;
    public static int enemyCellW = pcWidth*18/1000;

    //item size
    public static int myItemW = pcWidth*3155/100000;
    public static int myItemH = pcHeight*5143/100000;
    public static int enemyItemH = pcHeight*2/100;
    public static int enemyItemW = pcWidth*131/10000;

    /**
     * score token util
     */

    public static int endGameTokenH = pcHeight*781/10000;
    public static int endGameTokenW = pcWidth*488/10000;

    public static int endGameTokenBoundsW = pcWidth/14;
    public static int endGameTokenBoundsH = pcHeight*1143/10000;
    public static int endGameTokenX = pcWidth*3405/10000;
    public static int endGameTokenY = pcHeight*4724/10000;
    public static int endGameTokenOriented = 9;

    public static int endGameTokenRotateX = pcWidth*95/10000;
    public static int endGameTokenRotateY = pcHeight/7;


    public static int commonGoalTokenH = pcHeight*705/10000;
    public static int commonGoalTokenW = pcWidth*44/1000;

    public static int commonGoalTokenBoundsH = pcHeight*857/10000;
    public static int commonGoalTokenBoundsW = pcWidth*536/10000;
    public static int commonGoalTokenX = pcWidth*82/1000;
    public static int commonGoalTopTokenY = pcHeight*295/10000;
    public static int commonGoalBottomTokenY = pcHeight*19/100;
    public static int commonGoalTokenOriented = -8;

    public static int commonGoalTokenRotateX = pcWidth*536/10000;
    public static int commonGoalTokenRotateY = pcHeight/210;

    /**
     * myHandBoard Interface
     */
    public static int myHandBackGroundX = pcWidth*2262/10000;
    public static int myHandBackGroundY = pcHeight*375/100;

    public static int myHandBackGroundW = pcWidth*4643/10000;
    public static int myHandBackGroundH = pcHeight*6476/10000;

    public static int myHandHandH = pcWidth*275/1000;
    public static int myHandHandW = pcHeight*75/1000;
    public static int myHandHandX= pcWidth*10/336;
    public static int myHandHandY = pcHeight/21;

    public static int myHandShelfX = pcWidth*1072/10000;
    public static int myHandSortX = pcWidth*10/336;
    public static int myHandSortY = pcWidth*3155/10000;
    public static int myHandSortW = pcWidth*100/2154;
    public static int myHandSortH = pcHeight*75/1000;

   public static int myHandOptionX = pcWidth*863/10000;
   public static int myHandOptionXDiff = pcWidth*100/2154;
    public static int myHandOptionY = pcWidth*3155/10000;
    public static int handNumW = pcWidth*10/336;

    public static int handNumH = pcHeight/21;

    public static int myHandConfirmX = pcWidth*357/1000;
    public static int myHandConfirmY = pcHeight*567/1000;
    public static int myHandConfirmW = pcWidth*893/10000;
    public static int myHandConfirmH = pcHeight/21;
}
