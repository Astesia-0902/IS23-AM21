package org.am21.client.view.GUI;

import javax.swing.*;
import java.awt.*;
public class BoardPanel extends JPanel {


    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setBackground(Color.black);
        g.drawImage(BoardItem.board.getImage(),0,0,this.getWidth(),this.getHeight(),null);
    }
}
