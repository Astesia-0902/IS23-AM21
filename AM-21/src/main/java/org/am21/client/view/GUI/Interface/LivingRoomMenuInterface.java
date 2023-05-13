package org.am21.client.view.GUI.Interface;

import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;

public class LivingRoomMenuInterface extends JFrame {
    public LivingRoomMenuInterface(){
        int width = 750;
        int height = 600;
        int x = (PixelUtil.pcWidth - width) / 2;
        int y = (PixelUtil.pcHeight- height) / 2;
        setBounds(x, y, width, height);


        setTitle("Menu");

        // TODO: add component
    }
}
