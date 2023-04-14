package org.am21.model.component;

import javax.swing.*;
import java.awt.*;

public class BackGroundPanel extends JPanel {

    private Image backIcon;
    public BackGroundPanel(Image backIcon){
        this.backIcon = backIcon;
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backIcon, 0,0,this.getWidth(), this.getHeight(), null);
    }

}
