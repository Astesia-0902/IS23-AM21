package org.am21.client.view.GUI.Interface;

import javax.swing.*;

public class MatchListInterface extends JDialog {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;

    public MatchListInterface(JFrame frame) throws Exception {
        super(frame);
        frame.setTitle("MyShelfie - Match List");



        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0,0,WIDTH,HEIGHT);
        setUndecorated(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
