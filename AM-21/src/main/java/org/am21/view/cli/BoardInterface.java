package org.am21.view.cli;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class BoardInterface extends JFrame {
    /* the empty table */
    public BoardInterface(){
        super("Living Room");
        ImageIcon board = new ImageIcon("./AM-21/imgs/boards/livingroom.png");
        JLabel label = new JLabel(board){
            public void paint(Graphics g){
                Image img=board.getImage();
                g.drawImage(img,0,0,this.getWidth(),this.getHeight(),null);
            }
        };

        this.getLayeredPane().add(label,Integer.valueOf(Integer.MIN_VALUE));
        JPanel showPanel = (JPanel)this.getContentPane();
        showPanel.setOpaque(false);

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e){
                label.setSize(BoardInterface.this.getSize());
                label.repaint();
            }
        });

        this.setSize(board.getIconWidth(),board.getIconHeight());
        label.setSize(this.getSize());
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {

        new BoardInterface();


    }
}
