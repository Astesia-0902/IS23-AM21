package org.am21.client.view.GUI.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * IconUtil is a class that is used to load the icons
 * and resize them
 */
public class IconUtil {
    private static Map<String, Image> imageMap = new HashMap<>();
    private static Map<String, BufferedImage> bufferedImageMap = new HashMap<>();
    private static Map<String, ImageIcon> iconMap = new HashMap<>();

    static {
        ImageIcon imageIcon = null;
        try {
            imageMap.put("user", ImageIO.read(new FileInputStream(PathUtil.getPath("icon tool/user.png")))
                    .getScaledInstance(ImageUtil.resizeX(35), ImageUtil.resizeY(35), Image.SCALE_SMOOTH));
            imageMap.put("address", ImageIO.read(new FileInputStream(PathUtil.getPath("icon tool/address.png")))
                    .getScaledInstance(ImageUtil.resizeX(35), ImageUtil.resizeY(35), Image.SCALE_SMOOTH));
            imageMap.put("ip", ImageIO.read(new FileInputStream(PathUtil.getPath("icon tool/IP.png")))
                    .getScaledInstance(ImageUtil.resizeX(35), ImageUtil.resizeY(35), Image.SCALE_SMOOTH));
            imageMap.put("port", ImageIO.read(new FileInputStream(PathUtil.getPath("icon tool/server.png")))
                    .getScaledInstance(ImageUtil.resizeX(35), ImageUtil.resizeY(35), Image.SCALE_SMOOTH));
            imageMap.put("return", ImageIO.read(new FileInputStream(PathUtil.getPath("icon tool/return.png")))

                    .getScaledInstance(ImageUtil.resizeX(20), ImageUtil.resizeY(16), Image.SCALE_SMOOTH));
            imageMap.put("returnSelected", ImageIO.read(new FileInputStream(PathUtil.getPath("icon tool/returnColor.png")))
                    .getScaledInstance(ImageUtil.resizeX(20), ImageUtil.resizeY(16), Image.SCALE_SMOOTH));

            imageMap.put("minus", ImageIO.read(new FileInputStream(PathUtil.getPath("icon tool/minus.png")))
                    .getScaledInstance(ImageUtil.resizeX(25), ImageUtil.resizeY(25), Image.SCALE_SMOOTH));
            imageMap.put("close", ImageIO.read(new FileInputStream(PathUtil.getPath("icon tool/close.png")))
                    .getScaledInstance(ImageUtil.resizeX(25), ImageUtil.resizeY(25), Image.SCALE_SMOOTH));
            imageMap.put("close_Fuchsia", ImageIO.read(new FileInputStream(PathUtil.getPath("icon tool/close (1).png")))
                    .getScaledInstance(ImageUtil.resizeX(25), ImageUtil.resizeY(25), Image.SCALE_SMOOTH));
            imageMap.put("close_Purple", ImageIO.read(new FileInputStream(PathUtil.getPath("icon tool/close (2).png")))
                    .getScaledInstance(ImageUtil.resizeX(25), ImageUtil.resizeY(25), Image.SCALE_SMOOTH));
            imageMap.put("close_Brown", ImageIO.read(new FileInputStream(PathUtil.getPath("icon tool/close (1) 20x20.png")))
                    .getScaledInstance(ImageUtil.resizeX(25), ImageUtil.resizeY(25), Image.SCALE_SMOOTH));
            imageMap.put("button", ImageIO.read(new FileInputStream(PathUtil.getPath("icon tool/button1.png")))
                    .getScaledInstance(ImageUtil.resizeX(25), ImageUtil.resizeY(25), Image.SCALE_SMOOTH));
            imageMap.put("buttonSelected", ImageIO.read(new FileInputStream(PathUtil.getPath("icon tool/button2 (2).png")))
                    .getScaledInstance(ImageUtil.resizeX(25), ImageUtil.resizeY(25), Image.SCALE_SMOOTH));
            imageMap.put("online", ImageIO.read(new FileInputStream(PathUtil.getPath("icon tool/online.png")))
                    .getScaledInstance(ImageUtil.resizeX(25), ImageUtil.resizeY(25), Image.SCALE_SMOOTH));
            imageMap.put("onlineSelected", ImageIO.read(new FileInputStream(PathUtil.getPath("icon tool/onlineColor.png")))
                    .getScaledInstance(ImageUtil.resizeX(25), ImageUtil.resizeY(25), Image.SCALE_SMOOTH));
            imageMap.put("chat", ImageIO.read(new FileInputStream(PathUtil.getPath("icon tool/chat (2).png")))
                    .getScaledInstance(ImageUtil.resizeX(25), ImageUtil.resizeY(25), Image.SCALE_SMOOTH));
            imageMap.put("chatSelected", ImageIO.read(new FileInputStream(PathUtil.getPath("icon tool/chatColor.png")))
                    .getScaledInstance(ImageUtil.resizeX(25), ImageUtil.resizeY(25), Image.SCALE_SMOOTH));
            imageMap.put("help", ImageIO.read(new FileInputStream(PathUtil.getPath("icon tool/help.png")))
                    .getScaledInstance(ImageUtil.resizeX(25), ImageUtil.resizeY(25), Image.SCALE_SMOOTH));
            imageMap.put("helpSelected", ImageIO.read(new FileInputStream(PathUtil.getPath("icon tool/helpColor.png")))
                    .getScaledInstance(ImageUtil.resizeX(25), ImageUtil.resizeY(25), Image.SCALE_SMOOTH));

            imageMap.put("scroll-down", ImageIO.read(new FileInputStream(PathUtil.getPath("icon tool/scroll-down2.png")))
                    .getScaledInstance(ImageUtil.resizeX(20), ImageUtil.resizeY(20), Image.SCALE_SMOOTH));
            imageMap.put("scroll-top", ImageIO.read(new FileInputStream(PathUtil.getPath("icon tool/scroll-top2.png")))
                    .getScaledInstance(ImageUtil.resizeX(20), ImageUtil.resizeY(20), Image.SCALE_SMOOTH));

            imageMap.put("left", ImageIO.read(new FileInputStream(PathUtil.getPath("icon tool/left2 (2).png")))
                    .getScaledInstance(ImageUtil.resizeX(100), ImageUtil.resizeY(100), Image.SCALE_SMOOTH));
            imageMap.put("right", ImageIO.read(new FileInputStream(PathUtil.getPath("icon tool/right2 (2).png")))
                    .getScaledInstance(ImageUtil.resizeX(100), ImageUtil.resizeY(100), Image.SCALE_SMOOTH));
            imageMap.put("close_White", ImageIO.read(new FileInputStream(PathUtil.getPath("icon tool/close_white.png")))
                    .getScaledInstance(ImageUtil.resizeX(100), ImageUtil.resizeY(100), Image.SCALE_SMOOTH));
            imageMap.put("rule1", ImageIO.read(new FileInputStream(PathUtil.getPath("background/Rule1 1250x1250.png")))
                    .getScaledInstance(ImageUtil.resizeX(1250), ImageUtil.resizeY(1250), Image.SCALE_SMOOTH));
            imageMap.put("rule2", ImageIO.read(new FileInputStream(PathUtil.getPath("background/Rule2 1250x1250.png")))
                    .getScaledInstance(ImageUtil.resizeX(1250), ImageUtil.resizeY(1250), Image.SCALE_SMOOTH));
            imageMap.put("rule3", ImageIO.read(new FileInputStream(PathUtil.getPath("background/Rule3 1250x1250.png")))
                    .getScaledInstance(ImageUtil.resizeX(1250), ImageUtil.resizeY(1250), Image.SCALE_SMOOTH));
            imageMap.put("rule4", ImageIO.read(new FileInputStream(PathUtil.getPath("background/Rule4 1250x1250.png")))
                    .getScaledInstance(ImageUtil.resizeX(1250), ImageUtil.resizeY(1250), Image.SCALE_SMOOTH));
            imageMap.put("rule5", ImageIO.read(new FileInputStream(PathUtil.getPath("background/Rule5 1250x1250.png")))
                    .getScaledInstance(ImageUtil.resizeX(1250), ImageUtil.resizeY(1250), Image.SCALE_SMOOTH));
            imageMap.put("rule6", ImageIO.read(new FileInputStream(PathUtil.getPath("background/Rule6 1250x1250.png")))
                    .getScaledInstance(ImageUtil.resizeX(1250), ImageUtil.resizeY(1250), Image.SCALE_SMOOTH));

            bufferedImageMap.put("communicationBG", ImageIO.read(new FileInputStream(PathUtil.getPath("background/communicationBG.png"))));
            bufferedImageMap.put("loginBG", ImageIO.read(new FileInputStream(PathUtil.getPath("background/loginBG.png"))));
            bufferedImageMap.put("serverInfoBG", ImageIO.read(new FileInputStream(PathUtil.getPath("misc/base_pagina2.jpg"))));
            bufferedImageMap.put("menuActionBG", ImageIO.read(new FileInputStream(PathUtil.getPath("background/MenuActionBG.png"))));
            bufferedImageMap.put("waitingRoomBG", ImageIO.read(new FileInputStream((PathUtil.getPath("background/Waiting Room BG2.png")))));


            for (String iconName : imageMap.keySet()) {
                imageIcon = new ImageIcon(imageMap.get(iconName));
                iconMap.put(iconName, imageIcon);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This class is used to load all the images and icons used in the application.
     * The images are stored in a HashMap with the name of the image as key and the image as value.
     *
     * @param iconName The name of the icon.
     */
    public static ImageIcon getIcon(String iconName) {
        return iconMap.get(iconName);
    }

    /**
     * This class is used to load all the images and icons used in the application.
     * The images are stored in a HashMap with the name of the image as key and the image as value.
     *
     * @param imageName The name of the image.
     */
    public static BufferedImage getBufferedImage(String imageName) {
        return bufferedImageMap.get(imageName);
    }
}
