package org.am21.client.view.GUI.component;

import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;

public class ChairManLabel extends JPanel {
    public JLayeredPane chairPane;
    public JLabel chairLabel;

    //TODO: please completed
    public ChairManLabel(int playerID){
        switch(playerID){
            case 1:
                setBounds(0, 0, PixelUtil.chairHW, PixelUtil.chairHW);
                setLayout(null);
                setOpaque(false);

                chairPane = new JLayeredPane();
                chairPane.setBounds(0,0, PixelUtil.chairHW, PixelUtil.chairHW);
                chairPane.setLayout(null);
                chairPane.setOpaque(false);
                add(chairPane);

                chairLabel = new JLabel();
                chairLabel.setBounds(0,0,PixelUtil.chairHW, PixelUtil.chairHW);
                chairLabel.setIcon(ImageUtil.getChairManImage());
                chairPane.add(chairLabel,JLayeredPane.DEFAULT_LAYER);
            default:
        }

    }

}
