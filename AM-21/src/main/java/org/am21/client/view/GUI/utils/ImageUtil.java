package org.am21.client.view.GUI.utils;

import javax.swing.*;
import java.awt.*;

public class ImageUtil {
    public static ImageIcon getShelfImage(int widthShelfImage,int heightShelfImage){

        return new ImageIcon(new ImageIcon(PathUtil.getPath("boards/bookshelf_orth.png")).getImage().getScaledInstance(widthShelfImage,heightShelfImage, Image.SCALE_SMOOTH));
    }

    public static ImageIcon getBoardImage(String name) {
        return switch (name) {
            case "backGround" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("misc/sfondo parquet.jpg")).getImage().getScaledInstance(PixelUtil.pcWidth, PixelUtil.pcHeight, Image.SCALE_SMOOTH));
            case "gameBoard" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("boards/livingroom.png")).getImage().getScaledInstance(PixelUtil.gameBoardW, PixelUtil.gameBoardH, Image.SCALE_SMOOTH));
            case "personalGoalEmpty" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/front_EMPTY.jpg")).getImage().getScaledInstance(PixelUtil.personalGoalCardW, PixelUtil.personalGoalCardH, Image.SCALE_SMOOTH));
            case "commonGoalEmpty" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/back.jpg")).getImage().getScaledInstance(PixelUtil.commonGoalCardW, PixelUtil.commonGoalCardH, Image.SCALE_SMOOTH));
            case "handBoard" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("misc/base_pagina2.jpg")).getImage().getScaledInstance(PixelUtil.handBoardW, PixelUtil.handBoardH, Image.SCALE_SMOOTH));
            case "bagClose" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("misc/Sacchetto Chiuso.png")).getImage().getScaledInstance(PixelUtil.bagW, PixelUtil.bagH, Image.SCALE_SMOOTH));
            case "iconMenu" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("icon tool/menu.png")).getImage().getScaledInstance(PixelUtil.livingRoomMenuW, PixelUtil.livingRoomMenuH, Image.SCALE_SMOOTH));
            case "iconMe" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("icon tool/user.png")).getImage().getScaledInstance(PixelUtil.userMeW, PixelUtil.userMeH, Image.SCALE_SMOOTH));
            case "enemyA" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("icon tool/U1.jpg")).getImage().getScaledInstance(PixelUtil.enemyW, PixelUtil.enemyH, Image.SCALE_SMOOTH));
            case "enemyB" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("icon tool/U2.jpg")).getImage().getScaledInstance(PixelUtil.enemyW, PixelUtil.enemyH, Image.SCALE_SMOOTH));
            case "enemyC" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("icon tool/U3.jpg")).getImage().getScaledInstance(PixelUtil.enemyW, PixelUtil.enemyH, Image.SCALE_SMOOTH));
            case "endGameTokenEmpty" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("scoring tokens/scoring_back_EMPTY.jpg")).getImage().getScaledInstance(PixelUtil.endGameTokenW, PixelUtil.endGameTokenH, Image.SCALE_SMOOTH));
            case "commonGoalTokenEmpty" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("scoring tokens/scoring_back_EMPTY.jpg")).getImage().getScaledInstance(PixelUtil.commonGoalTokenW, PixelUtil.commonGoalTokenH, Image.SCALE_SMOOTH));
            case "myHandBack" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("background/myHandBack.jpg")).getImage().getScaledInstance(PixelUtil.myHandBackGroundW, PixelUtil.myHandBackGroundH, Image.SCALE_SMOOTH));
            case"myHandHand" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("misc/base_pagina2_reverse.jpg")).getImage().getScaledInstance(PixelUtil.myHandHandW, PixelUtil.myHandHandH, Image.SCALE_SMOOTH));
            case "iconSort" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("icon tool/sort.png")).getImage().getScaledInstance(PixelUtil.myHandSortW, PixelUtil.myHandSortH, Image.SCALE_SMOOTH));
            case "menuLRBack" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("background/MenuLRback.jpg")).getImage().getScaledInstance(PixelUtil.menuLRW, PixelUtil.menuLRH, Image.SCALE_SMOOTH));

            default -> null;
        };
    }

    public static ImageIcon getCommonGoalCardImage(String nameCard) {
        return switch (nameCard) {
            case "CommonGoal2Columns" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/2.jpg")).getImage().getScaledInstance(PixelUtil.commonGoalCardW, PixelUtil.commonGoalCardH, Image.SCALE_SMOOTH));
            case "CommonGoal2Lines" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/6.jpg")).getImage().getScaledInstance(PixelUtil.commonGoalCardW, PixelUtil.commonGoalCardH, Image.SCALE_SMOOTH));
            case "CommonGoal3Column" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/5.jpg")).getImage().getScaledInstance(PixelUtil.commonGoalCardW, PixelUtil.commonGoalCardH, Image.SCALE_SMOOTH));
            case "CommonGoal4Lines" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/7.jpg")).getImage().getScaledInstance(PixelUtil.commonGoalCardW, PixelUtil.commonGoalCardH, Image.SCALE_SMOOTH));
            case "CommonGoal8Tiles" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/9.jpg")).getImage().getScaledInstance(PixelUtil.commonGoalCardW, PixelUtil.commonGoalCardH, Image.SCALE_SMOOTH));
            case "CommonGoalCorner" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/8.jpg")).getImage().getScaledInstance(PixelUtil.commonGoalCardW, PixelUtil.commonGoalCardH, Image.SCALE_SMOOTH));
            case "CommonGoalDiagonal" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/11.jpg")).getImage().getScaledInstance(PixelUtil.commonGoalCardW, PixelUtil.commonGoalCardH, Image.SCALE_SMOOTH));
            case "CommonGoal4Group" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/3.jpg")).getImage().getScaledInstance(PixelUtil.commonGoalCardW, PixelUtil.commonGoalCardH, Image.SCALE_SMOOTH));
            case "CommonGoal6Group" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/4.jpg")).getImage().getScaledInstance(PixelUtil.commonGoalCardW, PixelUtil.commonGoalCardH, Image.SCALE_SMOOTH));
            case "CommonGoalSquare" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/1.jpg")).getImage().getScaledInstance(PixelUtil.commonGoalCardW, PixelUtil.commonGoalCardH, Image.SCALE_SMOOTH));
            case "CommonGoalStairs" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/12.jpg")).getImage().getScaledInstance(PixelUtil.commonGoalCardW, PixelUtil.commonGoalCardH, Image.SCALE_SMOOTH));
            case "CommonGoalXShape" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/10.jpg")).getImage().getScaledInstance(PixelUtil.commonGoalCardW, PixelUtil.commonGoalCardH, Image.SCALE_SMOOTH));
            default -> null;
        };
    }

    public static ImageIcon getPersonalGoalCardImage(int numCard) {
        return switch (numCard) {
            case 1 ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals1.png")).getImage().getScaledInstance(PixelUtil.personalGoalCardW, PixelUtil.personalGoalCardH, Image.SCALE_SMOOTH));
            case 2 ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals2.png")).getImage().getScaledInstance(PixelUtil.personalGoalCardW, PixelUtil.personalGoalCardH, Image.SCALE_SMOOTH));
            case 3 ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals3.png")).getImage().getScaledInstance(PixelUtil.personalGoalCardW, PixelUtil.personalGoalCardH, Image.SCALE_SMOOTH));
            case 4 ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals4.png")).getImage().getScaledInstance(PixelUtil.personalGoalCardW, PixelUtil.personalGoalCardH, Image.SCALE_SMOOTH));
            case 5 ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals5.png")).getImage().getScaledInstance(PixelUtil.personalGoalCardW, PixelUtil.personalGoalCardH, Image.SCALE_SMOOTH));
            case 6 ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals6.png")).getImage().getScaledInstance(PixelUtil.personalGoalCardW, PixelUtil.personalGoalCardH, Image.SCALE_SMOOTH));
            case 7 ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals7.png")).getImage().getScaledInstance(PixelUtil.personalGoalCardW, PixelUtil.personalGoalCardH, Image.SCALE_SMOOTH));
            case 8 ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals8.png")).getImage().getScaledInstance(PixelUtil.personalGoalCardW, PixelUtil.personalGoalCardH, Image.SCALE_SMOOTH));
            case 9 ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals9.png")).getImage().getScaledInstance(PixelUtil.personalGoalCardW, PixelUtil.personalGoalCardH, Image.SCALE_SMOOTH));
            case 10 ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals10.png")).getImage().getScaledInstance(PixelUtil.personalGoalCardW, PixelUtil.personalGoalCardH, Image.SCALE_SMOOTH));
            case 11->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals11.png")).getImage().getScaledInstance(PixelUtil.personalGoalCardW, PixelUtil.personalGoalCardH, Image.SCALE_SMOOTH));
            case 12 ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals12.png")).getImage().getScaledInstance(PixelUtil.personalGoalCardW, PixelUtil.personalGoalCardH, Image.SCALE_SMOOTH));
            default -> null;
        };
    }

    public static ImageIcon getItemImage(String image, int widthItem, int heightItem) {
        return switch (image) {
            case "_Books__1.1" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Books1.1.png")).getImage().getScaledInstance(widthItem, heightItem, Image.SCALE_SMOOTH));
            case "_Books__1.2" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Books1.2.png")).getImage().getScaledInstance(widthItem, heightItem, Image.SCALE_SMOOTH));
            case "_Books__1.3" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Books1.3.png")).getImage().getScaledInstance(widthItem, heightItem, Image.SCALE_SMOOTH));
            case "__Cats__1.1" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Cats1.1.png")).getImage().getScaledInstance(widthItem, heightItem, Image.SCALE_SMOOTH));
            case "__Cats__1.2" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Cats1.2.png")).getImage().getScaledInstance(widthItem, heightItem, Image.SCALE_SMOOTH));
            case "__Cats__1.3" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Cats1.3.png")).getImage().getScaledInstance(widthItem, heightItem, Image.SCALE_SMOOTH));
            case "_Frames_1.1" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Frames1.1.png")).getImage().getScaledInstance(widthItem, heightItem, Image.SCALE_SMOOTH));
            case "_Frames_1.2" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Frames1.2.png")).getImage().getScaledInstance(widthItem, heightItem, Image.SCALE_SMOOTH));
            case "_Frames_1.3" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Frames1.3.png")).getImage().getScaledInstance(widthItem, heightItem, Image.SCALE_SMOOTH));
            case "_Games__1.1" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Games1.1.png")).getImage().getScaledInstance(widthItem, heightItem, Image.SCALE_SMOOTH));
            case "_Games__1.2" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Games1.2.png")).getImage().getScaledInstance(widthItem, heightItem, Image.SCALE_SMOOTH));
            case "_Games__1.3" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Games1.3.png")).getImage().getScaledInstance(widthItem, heightItem, Image.SCALE_SMOOTH));
            case "_Plants_1.1" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Plants1.1.png")).getImage().getScaledInstance(widthItem, heightItem, Image.SCALE_SMOOTH));
            case "_Plants_1.2" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Plants1.2.png")).getImage().getScaledInstance(widthItem, heightItem, Image.SCALE_SMOOTH));
            case "_Plants_1.3" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Plants1.3.png")).getImage().getScaledInstance(widthItem, heightItem, Image.SCALE_SMOOTH));
            case "Trophies1.1" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Trophies1.1.png")).getImage().getScaledInstance(widthItem, heightItem, Image.SCALE_SMOOTH));
            case "Trophies1.2" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Trophies1.2.png")).getImage().getScaledInstance(widthItem, heightItem, Image.SCALE_SMOOTH));
            case "Trophies1.3" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Trophies1.3.png")).getImage().getScaledInstance(widthItem, heightItem, Image.SCALE_SMOOTH));
            default -> null;
        };

    }

    public static ImageIcon getScoreTokenImage(int value) {
        return switch (value) {
            case 1 ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("scoring tokens/end game.jpg")).getImage().getScaledInstance(PixelUtil.endGameTokenW, PixelUtil.endGameTokenH, Image.SCALE_SMOOTH));
            case 2 ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("scoring tokens/scoring_2.jpg")).getImage().getScaledInstance(PixelUtil.commonGoalTokenW, PixelUtil.commonGoalTokenH, Image.SCALE_SMOOTH));
            case 4 ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("scoring tokens/scoring_4.jpg")).getImage().getScaledInstance(PixelUtil.commonGoalTokenW, PixelUtil.commonGoalTokenH, Image.SCALE_SMOOTH));
            case 6 ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("scoring tokens/scoring_6.jpg")).getImage().getScaledInstance(PixelUtil.commonGoalTokenW, PixelUtil.commonGoalTokenH, Image.SCALE_SMOOTH));
            case 8 ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("scoring tokens/scoring_8.jpg")).getImage().getScaledInstance(PixelUtil.commonGoalTokenW, PixelUtil.commonGoalTokenH, Image.SCALE_SMOOTH));
            default -> null;
        };
    }

    public static ImageIcon getChairManImage() {
        return new ImageIcon(new ImageIcon(PathUtil.getPath("misc/firstplayertoken.png")).getImage().getScaledInstance(PixelUtil.chairW, PixelUtil.chairH, Image.SCALE_SMOOTH));
    }

    public static ImageIcon getNumberImage(String numColor){
        return switch (numColor) {
            case "1black" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("number/shuzi1-3.png")).getImage().getScaledInstance(PixelUtil.handNumW, PixelUtil.handNumH, Image.SCALE_SMOOTH));
            case "1gray" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("number/shuzi1.png")).getImage().getScaledInstance(PixelUtil.handNumW, PixelUtil.handNumH, Image.SCALE_SMOOTH));
            case "1green" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("number/shuzi1-2.png")).getImage().getScaledInstance(PixelUtil.handNumW, PixelUtil.handNumH, Image.SCALE_SMOOTH));
            case "2black" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("number/shuzi2-3.png")).getImage().getScaledInstance(PixelUtil.handNumW, PixelUtil.handNumH, Image.SCALE_SMOOTH));
            case "2gray" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("number/shuzi2.png")).getImage().getScaledInstance(PixelUtil.handNumW, PixelUtil.handNumH, Image.SCALE_SMOOTH));
            case "2green" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("number/shuzi2-2.png")).getImage().getScaledInstance(PixelUtil.handNumW, PixelUtil.handNumH, Image.SCALE_SMOOTH));
            case "3black" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("number/shuzi3-3.png")).getImage().getScaledInstance(PixelUtil.handNumW, PixelUtil.handNumH, Image.SCALE_SMOOTH));
            case "3gray" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("number/shuzi3-2.png")).getImage().getScaledInstance(PixelUtil.handNumW, PixelUtil.handNumH, Image.SCALE_SMOOTH));
            case "3green" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("number/shuzi3.png")).getImage().getScaledInstance(PixelUtil.handNumW, PixelUtil.handNumH, Image.SCALE_SMOOTH));

            case "4black" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("number/shuzi4-2.png")).getImage().getScaledInstance(PixelUtil.handNumW, PixelUtil.handNumH, Image.SCALE_SMOOTH));
            case "4gray" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("number/shuzi4.png")).getImage().getScaledInstance(PixelUtil.handNumW, PixelUtil.handNumH, Image.SCALE_SMOOTH));
            case "4green" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("number/shuzi4-3.png")).getImage().getScaledInstance(PixelUtil.handNumW, PixelUtil.handNumH, Image.SCALE_SMOOTH));

            case "5black" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("number/shuzi5-3.png")).getImage().getScaledInstance(PixelUtil.handNumW, PixelUtil.handNumH, Image.SCALE_SMOOTH));
            case "5gray" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("number/shuzi5.png")).getImage().getScaledInstance(PixelUtil.handNumW, PixelUtil.handNumH, Image.SCALE_SMOOTH));
            case "5green" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("number/shuzi5-2.png")).getImage().getScaledInstance(PixelUtil.handNumW, PixelUtil.handNumH, Image.SCALE_SMOOTH));

            default -> null;
        };
    }


    public static int resizeX(int element){
        double size = (double) element / 1800;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return (int) (screenSize.width * size);
    }
    public static int resizeY(int element){
        double size = (double) element / 1065;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return (int) (screenSize.height * size);
    }

}
