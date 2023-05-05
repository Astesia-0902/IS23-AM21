package org.am21.client.view.GUI.utils;

import java.awt.*;

public class PixelUtil {
    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int pcWidth = (int) screenSize.getWidth();
    public static int pcHeight = (int) screenSize.getHeight();

    public static int getPcWidth(){
        return pcWidth;
    }
    public static int getPcHeight(){
        return pcHeight;
    }
}
