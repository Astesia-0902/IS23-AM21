package org.am21.client.view.GUI.component;

import javax.swing.*;
import java.awt.*;

public class ScoringTokenLabel extends JPanel {

    private JLabel label;
    private int oriented;
    private int rotateX;
    private int rotateY;


    /**
     * rotation Label for tokens score card
     * @param img image
     * @param rotateDimension image dimension
     * @param oriented grade of orientation
     */
    public ScoringTokenLabel(ImageIcon img, int rotateDimension,int oriented,int rotateX, int rotateY) {
        label = new JLabel(img);
        this.oriented = oriented;
        this.rotateX = rotateX;
        this.rotateY = rotateY;
        setPreferredSize(new Dimension(rotateDimension, rotateDimension));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.rotate(Math.toRadians(this.oriented), this.rotateX, this.rotateY);
        label.setSize(label.getPreferredSize());
        label.paint(g2d);

    }

    public void setItem(int maxSeat){
        switch(maxSeat){

        }
    }

}
