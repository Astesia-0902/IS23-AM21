package org.am21.client.view.GUI.Interface;

import org.am21.client.view.GUI.utils.PathUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;

public class MatchListInterface extends JFrame {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;

    public MatchListInterface() throws Exception {
        setTitle("MyShelfie - Match List");
        setIconImage(ImageIO.read(new File(PathUtil.getPath("Publisher material/Icon 50x50px.png"))));



        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0,0,WIDTH,HEIGHT);
        setUndecorated(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        new MatchListInterface();
    }
}
