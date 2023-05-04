package org.am21.client.view.GUI.component;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class ButtonColorUI extends BasicButtonUI {
    private Color color;

    public ButtonColorUI(Color color) {
        this.color = color;
    }

    @Override
    protected void paintButtonPressed(Graphics g, AbstractButton b) {
        // Set the selection color to red
        g.setColor(color);
        // Draw a filled rectangle to represent the button being pressed
        g.fillRect(0, 0, b.getWidth(), b.getHeight());
    }
}
