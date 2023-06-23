package org.am21.client.view.GUI.utils;

import java.awt.*;

public class PixelUtil {

    // screen size util
    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int pcWidth = (int) screenSize.getWidth();
    public static int pcHeight = (int) screenSize.getHeight();

    //other size
    public static int gameBoardW = ImageUtil.resizeX(833);
    public static int gameBoardH = ImageUtil.resizeY(788);
    public static int myShelfBoardW = ImageUtil.resizeX(495);
    public static int myShelfBoardH = ImageUtil.resizeY(468);
    public static int handBoardW = ImageUtil.resizeX(495);
    public static int handBoardH = ImageUtil.resizeY(80);
    public static int commonGoalCardW = ImageUtil.resizeX(262);
    public static int commonGoalCardH = ImageUtil.resizeY(167);
    public static int personalGoalCardW = ImageUtil.resizeX(177);
    public static int personalGoalCardH = ImageUtil.resizeY(248);
    public static int bagW = ImageUtil.resizeX(90);
    public static int bagH = ImageUtil.resizeY(85);
    public static int livingRoomMenuW = ImageUtil.resizeX(54);
    public static int livingRoomMenuH = ImageUtil.resizeY(51);
    public static int insertClearButtonW = ImageUtil.resizeX(414);
    public static int insertClearButtonH = ImageUtil.resizeY(80);
    public static int userMeW = ImageUtil.resizeX(145);
    public static int userMeH = ImageUtil.resizeY(137);
    public static int enemyW = ImageUtil.resizeX(68);
    public static int enemyH = ImageUtil.resizeY(64);
    public static int enemyShelfW = ImageUtil.resizeX(202);
    public static int enemyShelfH = ImageUtil.resizeY(191);
    public static int gameBoardCellW = ImageUtil.resizeX(85);
    public static int gameBoardCellH = ImageUtil.resizeY(80);
    public static int gameBoardItemW = ImageUtil.resizeX(77);
    public static int gameBoardItemH = ImageUtil.resizeY(73);
    public static int chairW = ImageUtil.resizeX(68);
    public static int chairH = ImageUtil.resizeY(64);
    public static int myScoreW = ImageUtil.resizeX(162);
    public static int myScoreH = ImageUtil.resizeY(101);
    public static int myScoreDynamicW = ImageUtil.resizeX(54);
    public static int myScoreDynamicH = ImageUtil.resizeY(51);
    public static int enemyScoreY = ImageUtil.resizeY(36);
    public static int enemyScoreW = ImageUtil.resizeX(107);
    public static int enemyScoreH = ImageUtil.resizeY(101);
    public static int enemyNameW = ImageUtil.resizeX(45);
    public static int commonX_1 = ImageUtil.resizeX(22);
    public static int commonX_2 = ImageUtil.resizeX(90);
    public static int commonX_3 = ImageUtil.resizeX(405);
    public static int commonX_4 = ImageUtil.resizeX(1260);
    public static int commonX_5 = ImageUtil.resizeX(1462);
    public static int personalGoalX = ImageUtil.resizeX(1276);
    public static int livingRoomMenuX = ImageUtil.resizeX(1735);
    public static int insertButtonX = ImageUtil.resizeX(403);
    public static int clearButtonX = ImageUtil.resizeX(826);
    public static int userMeX = ImageUtil.resizeX(1296);
    public static int gameBoardGridX = ImageUtil.resizeX(446);
    public static int myHandPanelX = ImageUtil.resizeX(43);
    public static int chairManMeX = ImageUtil.resizeX(1222);
    public static int commonY_1 = ImageUtil.resizeY(32);
    public static int commonY_2 = ImageUtil.resizeY(245);
    public static int commonY_3 = ImageUtil.resizeY(469);
    public static int commonY_4 = ImageUtil.resizeY(898);
    public static int gameBoardY = ImageUtil.resizeY(106);
    public static int myShelfBoardY = ImageUtil.resizeY(426);
    public static int personalGoalY = ImageUtil.resizeY(170);
    public static int commonGoalY_A = ImageUtil.resizeY(81);
    public static int commonGoalY_B = ImageUtil.resizeY(253);
    public static int bagY = ImageUtil.resizeY(11);
    public static int livingRoomMenuY = ImageUtil.resizeY(10);
    public static int gameBoardGridY = ImageUtil.resizeY(147);
    public static int chairManMeY = ImageUtil.resizeY(11);
    public static int chairManEnemyY = ImageUtil.resizeY(122);
    public static int myScoreDynamicX = ImageUtil.resizeX(1607);
    public static int myHandPanelY = ImageUtil.resizeY(3);

    //chat util
    public static int cButtonY = ImageUtil.resizeY(978);
    public static int cButtonW = ImageUtil.resizeX(68);
    public static int cButtonH = ImageUtil.resizeY(34);
    public static int cWindowW = ImageUtil.resizeX(352);
    public static int cWindowH = ImageUtil.resizeY(300);
    public static int cWindowY = ImageUtil.resizeY(660);
    public static int cPlayerWindowY = ImageUtil.resizeY(450);

    // Shelf grid util
    public static int myGridX = ImageUtil.resizeX(1310);
    public static int myGridY = ImageUtil.resizeY(455);
    public static int enemyGridX = ImageUtil.resizeX(111);
    public static int enemyGridY = ImageUtil.resizeY(12);
    public static int myCellH = ImageUtil.resizeY(65);
    public static int myCellW = ImageUtil.resizeX(80);
    public static int enemyCellH = ImageUtil.resizeY(27);
    public static int enemyCellW = ImageUtil.resizeX(33);

    //item size util
    public static int myItemW = ImageUtil.resizeX(59);
    public static int myItemH = ImageUtil.resizeY(57);
    public static int enemyItemH = ImageUtil.resizeY(23);
    public static int enemyItemW = ImageUtil.resizeX(25);

    //score token util
    public static int endGameTokenH = ImageUtil.resizeY(83);
    public static int endGameTokenW = ImageUtil.resizeX(88);
    public static int endGameTokenBoundsW = ImageUtil.resizeX(129);
    public static int endGameTokenBoundsH = ImageUtil.resizeY(122);
    public static int endGameTokenX = ImageUtil.resizeX(613);
    public static int endGameTokenY = ImageUtil.resizeY(503);
    public static int endGameTokenOriented = 9;
    public static int endGameTokenRotateX = ImageUtil.resizeX(17);
    public static int endGameTokenRotateY = ImageUtil.resizeY(152);
    public static int commonGoalTokenH = ImageUtil.resizeY(75);
    public static int commonGoalTokenW = ImageUtil.resizeX(79);
    public static int commonGoalTokenBoundsH = ImageUtil.resizeY(91);
    public static int commonGoalTokenBoundsW = ImageUtil.resizeX(96);
    public static int commonGoalTokenX = ImageUtil.resizeX(148);
    public static int commonGoalTopTokenY = ImageUtil.resizeY(31);
    public static int commonGoalBottomTokenY = ImageUtil.resizeY(202);
    public static int commonGoalTokenOriented = -8;
    public static int commonGoalTokenRotateX = ImageUtil.resizeX(96);
    public static int commonGoalTokenRotateY = ImageUtil.resizeY(5);

    //myHandBoard Interface
    public static int myHandBackGroundX = ImageUtil.resizeX(407);
    public static int myHandBackGroundY = ImageUtil.resizeY(304);
    public static int myHandBackGroundW = ImageUtil.resizeX(836);
    public static int myHandBackGroundH = ImageUtil.resizeY(690);
    public static int myHandHandH = ImageUtil.resizeY(469);
    public static int myHandHandW = ImageUtil.resizeX(85);
    public static int myHandHandX = ImageUtil.resizeX(54);
    public static int myHandHandY = ImageUtil.resizeY(51);
    public static int myHandShelfX = ImageUtil.resizeX(193);
    public static int myHandSortX = ImageUtil.resizeX(54);
    public static int myHandSortY = ImageUtil.resizeY(568);
    public static int myHandSortW = ImageUtil.resizeX(84);
    public static int myHandSortH = ImageUtil.resizeY(80);
    public static int myHandOptionX = ImageUtil.resizeX(155);
    public static int myHandOptionXDiff = ImageUtil.resizeX(84);
    public static int myHandOptionY = ImageUtil.resizeY(568);
    public static int handNumW = ImageUtil.resizeX(54);
    public static int handNumH = ImageUtil.resizeY(51);
    public static int myHandConfirmX = ImageUtil.resizeX(659);
    public static int myHandConfirmY = ImageUtil.resizeY(604);
    public static int myHandConfirmW = ImageUtil.resizeX(161);
    public static int myHandConfirmH = ImageUtil.resizeY(51);
    public static int myHandItemX = ImageUtil.resizeX(4);
    public static int myHandItemY = ImageUtil.resizeY(43);
    public static int myHandShelfGridX = ImageUtil.resizeX(242);
    public static int myHandShelfGridY = ImageUtil.resizeY(78);

    //menu living room interface
    public static int menuLRW = ImageUtil.resizeX(600);
    public static int menuLRH = ImageUtil.resizeY(500);
    public static int buttonLRX = ImageUtil.resizeX(100);
    public static int buttonBackLRY = ImageUtil.resizeY(50);
    public static int buttonWaitLRY = ImageUtil.resizeY(200);
    public static int buttonLeaveLRY = ImageUtil.resizeY(350);
    public static int buttonLRW = ImageUtil.resizeX(400);
    public static int buttonLRH = ImageUtil.resizeY(100);

    //game results interface
    public static int labelRITitleW = ImageUtil.resizeX(1071);
    public static int labelRITitleH = ImageUtil.resizeY(1026);
    public static int labelRITableH = ImageUtil.resizeY(507);
    public static int labelRITitleX = ImageUtil.resizeX(365);
    public static int labelRIY = ImageUtil.resizeY(365);
    public static int labelRITitleY = ImageUtil.resizeY(-304);
    public static int labelRITableRowH = ImageUtil.resizeY(112);
    public static int labelRIButtonX = ImageUtil.resizeX(686);
    public static int labelRIButtonY = ImageUtil.resizeY(916);

}
