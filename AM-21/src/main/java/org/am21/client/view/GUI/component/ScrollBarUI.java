package org.am21.client.view.GUI.component;


import org.am21.client.view.GUI.utils.ButtonUtil;
import org.am21.client.view.GUI.utils.IconUtil;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class ScrollBarUI extends BasicScrollBarUI {
    @Override
    protected void configureScrollBarColors() {
        trackColor = Color.WHITE;
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        super.paintTrack(g, c, trackBounds);
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        g.translate(thumbBounds.x, thumbBounds.y);
        g.setColor(new Color(106, 1, 85, 102));
        g.drawRoundRect(5,0,9,thumbBounds.height-1,5,5);

        Graphics2D g2 = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.addRenderingHints(rh);

        g2.fillRoundRect(5,0,9,thumbBounds.height-1,5,5);

    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        JButton button = ButtonUtil.getCommandButton();
        button.setIcon(IconUtil.getIcon("scroll-down"));
        return button;
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        JButton button = ButtonUtil.getCommandButton();
        button.setIcon(IconUtil.getIcon("scroll-top"));
        return button;
    }
}
