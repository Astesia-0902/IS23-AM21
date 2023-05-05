package org.am21.client.view.GUI.utils;

import javax.swing.*;
import java.awt.*;

public class ItemImage {

     public static ImageIcon getItemImage(String image,int width,int height){
         switch (image){
             case "books1": return new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Books1.1.png")).getImage().getScaledInstance(width,height, Image.SCALE_SMOOTH));
             case "books2": return new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Books1.2.png")).getImage().getScaledInstance(width,height, Image.SCALE_SMOOTH));
             case "books3": return new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Books1.3.png")).getImage().getScaledInstance(width,height, Image.SCALE_SMOOTH));
             case "cats1": return new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Cats1.1.png")).getImage().getScaledInstance(width,height, Image.SCALE_SMOOTH));
             case "cats2": return new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Cats1.2.png")).getImage().getScaledInstance(width,height, Image.SCALE_SMOOTH));
             case "cats3": return new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Cats1.3.png")).getImage().getScaledInstance(width,height, Image.SCALE_SMOOTH));
             case "frames1": return new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Frames1.1.png")).getImage().getScaledInstance(width,height, Image.SCALE_SMOOTH));
             case "frames2": return new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Frames1.2.png")).getImage().getScaledInstance(width,height, Image.SCALE_SMOOTH));
             case "frames3": return new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Frames1.3.png")).getImage().getScaledInstance(width,height, Image.SCALE_SMOOTH));
             case "games1": return new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Games1.1.png")).getImage().getScaledInstance(width,height, Image.SCALE_SMOOTH));
             case "games2": return new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Games1.2.png")).getImage().getScaledInstance(width,height, Image.SCALE_SMOOTH));
             case "games3": return new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Games1.3.png")).getImage().getScaledInstance(width,height, Image.SCALE_SMOOTH));
             case "plants1": return new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Plants1.1.png")).getImage().getScaledInstance(width,height, Image.SCALE_SMOOTH));
             case "plants2": return new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Plants1.2.png")).getImage().getScaledInstance(width,height, Image.SCALE_SMOOTH));
             case "plants3": return new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Plants1.3.png")).getImage().getScaledInstance(width,height, Image.SCALE_SMOOTH));
             case "trophies1": return new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Trophies1.1.png")).getImage().getScaledInstance(width,height, Image.SCALE_SMOOTH));
             case "trophies2": return new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Trophies1.2.png")).getImage().getScaledInstance(width,height, Image.SCALE_SMOOTH));
             default:  return new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Trophies1.3.png")).getImage().getScaledInstance(width,height, Image.SCALE_SMOOTH));
         }

     }

}
