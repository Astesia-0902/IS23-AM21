package org.am21.client.view.GUI.utils;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * FontUtil is a class that is used to load fonts
 */
public class FontUtil {
    private static Map<String, String> fontMap = new HashMap<>();
    private static final Map<String, Font> fonterMap = new HashMap<>();

    static {
        fontMap.put("Twenty-Regular-2", "Twenty-Regular-2.otf");
        fontMap.put("Sunday-Regular-2", "Sunday-Regular-2.otf");
        fontMap.put("PippaHandwriting-Regular-3", "PippaHandwriting-Regular-3.ttf");
        fontMap.put("Leira-Lite-2", "Leira-Lite-2.ttf");
        fontMap.put("Klipan-Black-2", "Klipan-Black-2.ttf");
        fontMap.put("Hamurz-Free-Version-2", "Hamurz-Free-Version-2.ttf");
        fontMap.put("KaushanScript-Regular-1", "KaushanScript-Regular-1.otf");
        fontMap.put("Brizel-2", "Brizel-2.ttf");
        fontMap.put("Boisu-Stroke-2", "Boisu-Stroke-2.otf");
        fontMap.put("Boisu-Full-3", "Boisu-Full-3.otf");
        fontMap.put("Boisu-Fill-4", "Boisu-Fill-4.otf");
        fontMap.put("HongLeiXingShuJianTi-2", "HongLeiXingShuJianTi-2.otf");

        InputStream inputStream = null;
        File file = null;
        Font font = null;
        String path = "AM-21/fonts/";
        try {
            for (String fontName : fontMap.keySet()) {
                file = new File(path + fontMap.get(fontName));
                inputStream = new FileInputStream(file);
                font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
                fonterMap.put(fontName, font);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * This method is used to get the font by name
     *
     * @param fontName
     * @return
     */
    public static Font getFontByName(String fontName) {
        Font font = fonterMap.get(fontName);
        return font;
    }

}