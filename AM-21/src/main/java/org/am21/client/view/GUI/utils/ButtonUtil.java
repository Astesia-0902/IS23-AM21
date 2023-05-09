package org.am21.client.view.GUI.utils;

import org.am21.client.view.GUI.component.ButtonColorUI;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class ButtonUtil {
    private final String s = "";
    public static JButton button;

    public static JButton getButton(String s) {
        button = new JButton();
        button.setBackground(new Color(222, 184, 135));
        button.setUI(new ButtonColorUI(new Color(245, 225, 199)));
        button.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 250, 205),
                new Color(255, 250, 205), new Color(139, 69, 19), new Color(139, 69, 19)));
        button.setFont(FontUtil.getFontByName("Leira-Lite-2").deriveFont(Font.PLAIN,ImageUtil.resizeX(20)));
        button.setForeground(new Color(139, 69, 19));
        button.setText(s);
        return button;
    }

    public static JButton getCommandButton(String s) {
        button = new JButton(s);
        button.setContentAreaFilled(false);
        button.setBorder(null);
        button.setFocusPainted(false);
        button.setForeground(new Color(237, 179, 137));
        button.setFont(FontUtil.getFontByName("Leira-Lite-2").deriveFont(Font.PLAIN,ImageUtil.resizeX(20)));
        button.setOpaque(false);
        return button;
    }

    public static JButton getCommandButton(){
        button = new JButton();
        button.setContentAreaFilled(false);
        button.setBorder(null);
        button.setFocusPainted(false);
        return button;
    }
}
