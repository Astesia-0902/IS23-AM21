package org.am21.client.view.GUI.utils;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class IconUtil {
    private static Map<String,ImageIcon> iconMap = new HashMap<>();


    static {
        iconMap.put("online",new ImageIcon(PathUtil.getPath("icon tool/online.png")));
        iconMap.put("onlineSelected",new ImageIcon(PathUtil.getPath("icon tool/onlineColor.png")));
        iconMap.put("chat",new ImageIcon(PathUtil.getPath("icon tool/chat (2).png")));
        iconMap.put("chatSelected",new ImageIcon(PathUtil.getPath("icon tool/chatColor.png")));
    }

    public static ImageIcon getIcon(String iconName) {
        return iconMap.get(iconName);
    }
}
