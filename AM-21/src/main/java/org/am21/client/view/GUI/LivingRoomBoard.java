package org.am21.client.view.GUI;

import javax.swing.*;

public class LivingRoomBoard {

    public static void main(String[] args){
        JFrame frame = new JFrame("Living room");

        frame.add(new BoardPanel());
        frame.setBounds(0,0,1000,1000);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
