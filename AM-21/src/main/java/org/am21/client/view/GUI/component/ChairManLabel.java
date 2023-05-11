package org.am21.client.view.GUI.component;

import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;

public class ChairManLabel extends JPanel {
    public JLayeredPane chairPane;
    public JLabel chairLabel;

    //TODO: please completed
    public ChairManLabel(int playerID){
        switch (playerID) {
            case 1 -> setChairMan(PixelUtil.commonX_1, PixelUtil.chairManAY);
            case 2 -> setChairMan(PixelUtil.commonX_1, PixelUtil.chairManBY);
            case 3 -> setChairMan(PixelUtil.commonX_1, PixelUtil.chairManCY);
            default -> //Me
                    setChairMan(PixelUtil.chairManMeX, PixelUtil.chairManMeY);
        }

    }

    public void setChairMan(int posX, int posY){

        setBounds(posX, posY, PixelUtil.chairW, PixelUtil.chairH);
        setLayout(null);
        setOpaque(false);

        chairPane = new JLayeredPane();
        chairPane.setBounds(0,0, PixelUtil.chairW, PixelUtil.chairH);
        chairPane.setLayout(null);
        chairPane.setOpaque(false);
        add(chairPane);

        chairLabel = new JLabel();
        chairLabel.setBounds(0,0,PixelUtil.chairW, PixelUtil.chairH);
        chairLabel.setIcon(ImageUtil.getChairManImage());
        chairPane.add(chairLabel,JLayeredPane.DEFAULT_LAYER);
    }

}
