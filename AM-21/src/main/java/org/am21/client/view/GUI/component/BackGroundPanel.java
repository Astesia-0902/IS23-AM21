package org.am21.client.view.GUI.component;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class BackGroundPanel extends JPanel {
    public final HashMap<BufferedImage, int[]> backIcons;

    public BackGroundPanel(HashMap<BufferedImage, int[]> backIcons) {
        this.backIcons = backIcons;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.drawImage(backIcon, 0,0,this.getWidth(), this.getHeight(), null);
        Graphics2D g2d = (Graphics2D) g.create();


        for (BufferedImage backImg : backIcons.keySet()) {
            int x = backIcons.get(backImg)[0];
            int y = backIcons.get(backImg)[1];
            int width = backIcons.get(backImg)[2];
            int height = backIcons.get(backImg)[3];
            g2d.drawImage(backImg, x, y, width, height, null);

            //g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.5f));
        }

        g2d.dispose();

    }

}
