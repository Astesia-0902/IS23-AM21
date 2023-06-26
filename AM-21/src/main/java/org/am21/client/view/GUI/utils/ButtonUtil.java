package org.am21.client.view.GUI.utils;

import org.am21.client.view.GUI.component.ButtonColorUI;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * ButtonUtil is a class that is used to create buttons
 */
public class ButtonUtil {
    private final String s = "";
    public static JButton button;

    /**
     * getButton is a method that is used to create a button
     * @param s is the text of the button
     * @return a button
     */
    public static JButton getButton(String s) {
        button = new JButton();
        button.setBackground(new Color(222, 184, 135));
        button.setUI(new ButtonColorUI(new Color(245, 225, 199)));
        button.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 250, 205),
                new Color(255, 250, 205), new Color(139, 69, 19), new Color(139, 69, 19)));
        button.setFont(FontUtil.getFontByName("Leira-Lite-2").deriveFont(Font.PLAIN,ImageUtil.resizeY(22)));
        button.setForeground(new Color(139, 69, 19));
        button.setText(s);
        return button;
    }

    /**
     * getCommandButton is a method that is used to create a button
     * @param s is the text of the button
     * @return a button
     */
    public static JButton getCommandButton(String s) {
        button = new JButton(s);
        button.setContentAreaFilled(false);
        button.setBorder(null);
        button.setFocusPainted(false);
        button.setForeground(new Color(237, 179, 137));
        button.setFont(FontUtil.getFontByName("Leira-Lite-2").deriveFont(Font.PLAIN,ImageUtil.resizeY(22)));
        button.setOpaque(false);
        return button;
    }

    /**
     * getCommandButton is a method that is used to create a button
     * @return a button
     */
    public static JButton getCommandButton(){
        button = new JButton();
        button.setContentAreaFilled(false);
        button.setBorder(new EmptyBorder(0,ImageUtil.resizeX(0),0,0));
        button.setFocusPainted(false);
        return button;
    }
}
