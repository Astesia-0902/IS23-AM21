package org.am21.client.view.GUI.utils;

import javax.swing.*;
import java.awt.*;

public class ImageUtil {
    public static ImageIcon getShelfImage(int dimensionShelf){

        return new ImageIcon(new ImageIcon(PathUtil.getPath("boards/bookshelf_orth.png")).getImage().getScaledInstance(dimensionShelf,dimensionShelf, Image.SCALE_SMOOTH));
    }

    public static ImageIcon getBoardImage(String name){
        return switch (name) {
            case "backGround" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("misc/sfondo parquet.jpg")).getImage().getScaledInstance(PixelUtil.pcWidth, PixelUtil.pcHeight, Image.SCALE_SMOOTH));
            case "gameBoard" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("boards/livingroom.png")).getImage().getScaledInstance(PixelUtil.gameBoardHW, PixelUtil.gameBoardHW, Image.SCALE_SMOOTH));
            case "personalGoalEmpty" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/front_EMPTY.jpg")).getImage().getScaledInstance(PixelUtil.goalCardH, PixelUtil.goalCardW, Image.SCALE_SMOOTH));
            case "commonGoalEmpty" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/back.jpg")).getImage().getScaledInstance(PixelUtil.goalCardW, PixelUtil.goalCardH, Image.SCALE_SMOOTH));
            case "handBoard" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("misc/base_pagina2.jpg")).getImage().getScaledInstance(PixelUtil.myShelfBoardHW, PixelUtil.bottomButtonH, Image.SCALE_SMOOTH));
            case "bagClose" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("misc/Sacchetto Chiuso.png")).getImage().getScaledInstance(PixelUtil.bagHW, PixelUtil.bagHW, Image.SCALE_SMOOTH));
            case "iconMenu" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("icon tool/menu.png")).getImage().getScaledInstance(PixelUtil.livingRoomMenuHW, PixelUtil.livingRoomMenuHW, Image.SCALE_SMOOTH));
            case "iconMe" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("icon tool/user.png")).getImage().getScaledInstance(PixelUtil.userMeHW, PixelUtil.userMeHW, Image.SCALE_SMOOTH));
            case "enemyA" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("icon tool/U1.jpg")).getImage().getScaledInstance(PixelUtil.enemyHW, PixelUtil.enemyHW, Image.SCALE_SMOOTH));
            case "enemyB" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("icon tool/U2.jpg")).getImage().getScaledInstance(PixelUtil.enemyHW, PixelUtil.enemyHW, Image.SCALE_SMOOTH));
            case "enemyC" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("icon tool/U3.jpg")).getImage().getScaledInstance(PixelUtil.enemyHW, PixelUtil.enemyHW, Image.SCALE_SMOOTH));
            case "endGameTokenEmpty" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("scoring tokens/scoring_back_EMPTY.jpg")).getImage().getScaledInstance(PixelUtil.endGameTokenHW, PixelUtil.endGameTokenHW, Image.SCALE_SMOOTH));
            case "commonGoalTokenEmpty" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("scoring tokens/scoring_back_EMPTY.jpg")).getImage().getScaledInstance(PixelUtil.commonGoalTokenHW, PixelUtil.commonGoalTokenHW, Image.SCALE_SMOOTH));
            default -> null;
        };
    }
    public static ImageIcon getCommonGoalCardImage(String nameCard){
        return switch (nameCard) {
            case "2Columns" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/2.jpg")).getImage().getScaledInstance(PixelUtil.goalCardW, PixelUtil.goalCardH, Image.SCALE_SMOOTH));
            case "2Lines" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/6.jpg")).getImage().getScaledInstance(PixelUtil.goalCardW, PixelUtil.goalCardH, Image.SCALE_SMOOTH));
            case "3Column" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/5.jpg")).getImage().getScaledInstance(PixelUtil.goalCardW, PixelUtil.goalCardH, Image.SCALE_SMOOTH));
            case "4Lines" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/7.jpg")).getImage().getScaledInstance(PixelUtil.goalCardW, PixelUtil.goalCardH, Image.SCALE_SMOOTH));
            case "8Tiles" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/9.jpg")).getImage().getScaledInstance(PixelUtil.goalCardW, PixelUtil.goalCardH, Image.SCALE_SMOOTH));
            case "Corner" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/8.jpg")).getImage().getScaledInstance(PixelUtil.goalCardW, PixelUtil.goalCardH, Image.SCALE_SMOOTH));
            case "Diagonal" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/11.jpg")).getImage().getScaledInstance(PixelUtil.goalCardW, PixelUtil.goalCardH, Image.SCALE_SMOOTH));
            case "Group4" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/3.jpg")).getImage().getScaledInstance(PixelUtil.goalCardW, PixelUtil.goalCardH, Image.SCALE_SMOOTH));
            case "Group6" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/4.jpg")).getImage().getScaledInstance(PixelUtil.goalCardW, PixelUtil.goalCardH, Image.SCALE_SMOOTH));
            case "Square" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/1.jpg")).getImage().getScaledInstance(PixelUtil.goalCardW, PixelUtil.goalCardH, Image.SCALE_SMOOTH));
            case "Stairs" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/12.jpg")).getImage().getScaledInstance(PixelUtil.goalCardW, PixelUtil.goalCardH, Image.SCALE_SMOOTH));
            case "XShape" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/10.jpg")).getImage().getScaledInstance(PixelUtil.goalCardW, PixelUtil.goalCardH, Image.SCALE_SMOOTH));
            default -> null;
        };
    }

    public static ImageIcon getPersonalGoalCardImage(String nameCard){
        return switch (nameCard) {
            case "Goals1" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals1.png")).getImage().getScaledInstance(PixelUtil.goalCardH, PixelUtil.goalCardW, Image.SCALE_SMOOTH));
            case "Goals2" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals2.png")).getImage().getScaledInstance(PixelUtil.goalCardH, PixelUtil.goalCardW, Image.SCALE_SMOOTH));
            case "Goals3" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals3.png")).getImage().getScaledInstance(PixelUtil.goalCardH, PixelUtil.goalCardW, Image.SCALE_SMOOTH));
            case "Goals4" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals4.png")).getImage().getScaledInstance(PixelUtil.goalCardH, PixelUtil.goalCardW, Image.SCALE_SMOOTH));
            case "Goals5" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals5.png")).getImage().getScaledInstance(PixelUtil.goalCardH, PixelUtil.goalCardW, Image.SCALE_SMOOTH));
            case "Goals6" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals6.png")).getImage().getScaledInstance(PixelUtil.goalCardH, PixelUtil.goalCardW, Image.SCALE_SMOOTH));
            case "Goals7" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals7.png")).getImage().getScaledInstance(PixelUtil.goalCardH, PixelUtil.goalCardW, Image.SCALE_SMOOTH));
            case "Goals8" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals8.png")).getImage().getScaledInstance(PixelUtil.goalCardH, PixelUtil.goalCardW, Image.SCALE_SMOOTH));
            case "Goals9" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals9.png")).getImage().getScaledInstance(PixelUtil.goalCardH, PixelUtil.goalCardW, Image.SCALE_SMOOTH));
            case "Goals10" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals10.png")).getImage().getScaledInstance(PixelUtil.goalCardH, PixelUtil.goalCardW, Image.SCALE_SMOOTH));
            case "Goals11" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals11.png")).getImage().getScaledInstance(PixelUtil.goalCardH, PixelUtil.goalCardW, Image.SCALE_SMOOTH));
            case "Goals12" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals12.png")).getImage().getScaledInstance(PixelUtil.goalCardH, PixelUtil.goalCardW, Image.SCALE_SMOOTH));
            default -> null;
        };
    }

    public static ImageIcon getItemImage(String image,int width,int height){
        return switch (image) {
            case "books1" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Books1.1.png")).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            case "books2" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Books1.2.png")).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            case "books3" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Books1.3.png")).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            case "cats1" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Cats1.1.png")).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            case "cats2" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Cats1.2.png")).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            case "cats3" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Cats1.3.png")).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            case "frames1" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Frames1.1.png")).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            case "frames2" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Frames1.2.png")).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            case "frames3" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Frames1.3.png")).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            case "games1" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Games1.1.png")).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            case "games2" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Games1.2.png")).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            case "games3" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Games1.3.png")).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            case "plants1" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Plants1.1.png")).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            case "plants2" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Plants1.2.png")).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            case "plants3" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Plants1.3.png")).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            case "trophies1" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Trophies1.1.png")).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            case "trophies2" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Trophies1.2.png")).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            case "trophies3" ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Trophies1.3.png")).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            default ->
                   null;
        };

    }

    public static ImageIcon getScoreTokenImage(int value){
        return switch (value) {
            case 1 ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("scoring tokens/end game.jpg")).getImage().getScaledInstance(PixelUtil.endGameTokenHW, PixelUtil.endGameTokenHW, Image.SCALE_SMOOTH));
            case 2 ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("scoring tokens/scoring_2.jpg")).getImage().getScaledInstance(PixelUtil.commonGoalTokenHW, PixelUtil.commonGoalTokenHW, Image.SCALE_SMOOTH));
            case 4 ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("scoring tokens/scoring_4.jpg")).getImage().getScaledInstance(PixelUtil.commonGoalTokenHW, PixelUtil.commonGoalTokenHW, Image.SCALE_SMOOTH));
            case 6 ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("scoring tokens/scoring_6.jpg")).getImage().getScaledInstance(PixelUtil.commonGoalTokenHW, PixelUtil.commonGoalTokenHW, Image.SCALE_SMOOTH));
            case 8 ->
                    new ImageIcon(new ImageIcon(PathUtil.getPath("scoring tokens/scoring_8.jpg")).getImage().getScaledInstance(PixelUtil.commonGoalTokenHW, PixelUtil.commonGoalTokenHW, Image.SCALE_SMOOTH));
            default -> null;
        };

    }

    public static ImageIcon getChairManImage(){
        return new ImageIcon(new ImageIcon(PathUtil.getPath("misc/firstplayertoken.png")).getImage().getScaledInstance(PixelUtil.chairHW, PixelUtil.chairHW, Image.SCALE_SMOOTH));
    }


}
