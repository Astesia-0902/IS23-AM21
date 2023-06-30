package org.am21.client.view.GUI.component;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

/**
 * ButtonColorUI is a class that extends BasicButtonUI
 * and is used to change the color of the button when it is pressed
 * */
public class ButtonColorUI extends BasicButtonUI {
    private Color color;

    /**
     * Constructor
     *
     * @param color is the color of the button
     */
    public ButtonColorUI(Color color) {
        this.color = color;
    }

    /**
     * Paints the button when it is pressed
     *
     * @param g is the graphics
     * @param b is the button
     */
    @Override
    protected void paintButtonPressed(Graphics g, AbstractButton b) {
        // Set the selection color to red
        g.setColor(color);
        // Draw a filled rectangle to represent the button being pressed
        g.fillRect(0, 0, b.getWidth(), b.getHeight());
    }
}

