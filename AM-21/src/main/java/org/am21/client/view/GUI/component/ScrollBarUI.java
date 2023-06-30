package org.am21.client.view.GUI.component;


import org.am21.client.view.GUI.utils.ButtonUtil;
import org.am21.client.view.GUI.utils.IconUtil;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

/**
 * ScrollBarUI is a class that extends BasicScrollBarUI
 * and is used to change the color of the scroll bar
 */
public class ScrollBarUI extends BasicScrollBarUI {
    /**
     * configureScrollBarColors is a method that is used to change the color of the scroll bar
     * */
    @Override
    protected void configureScrollBarColors() {
        trackColor = Color.WHITE;
    }

    /**
     * paintTrack is a method that is used to paint the track of the scroll bar
     * @param g is the graphics
     * @param c is the component
     * @param trackBounds is the bounds of the track
     * */
    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        super.paintTrack(g, c, trackBounds);
    }

    /**
     * paintThumb is a method that is used to paint the thumb of the scroll bar
     * @param g is the graphics
     * @param c is the component
     * @param thumbBounds is the bounds of the thumb
     * */
    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        g.translate(thumbBounds.x, thumbBounds.y);
        g.setColor(new Color(106, 1, 85, 102));
        g.drawRoundRect(5, 0, 9, thumbBounds.height - 1, 5, 5);

        Graphics2D g2 = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.addRenderingHints(rh);

        g2.fillRoundRect(5, 0, 9, thumbBounds.height - 1, 5, 5);

    }

    /**
     * createIncreaseButton is a method that is used to create the increase button of the scroll bar
     * @param orientation is the orientation of the button
     * */
    @Override
    protected JButton createIncreaseButton(int orientation) {
        JButton button = ButtonUtil.getCommandButton();
        button.setIcon(IconUtil.getIcon("scroll-down"));
        return button;
    }

    /**
     * createDecreaseButton is a method that is used to create the decrease button of the scroll bar
     * @param orientation is the orientation of the button
     * */
    @Override
    protected JButton createDecreaseButton(int orientation) {
        JButton button = ButtonUtil.getCommandButton();
        button.setIcon(IconUtil.getIcon("scroll-top"));
        return button;
    }
}
