package org.am21.client.view.GUI.Interface;

import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;
import java.awt.*;


public class LivingRoomMenuInterface extends JFrame {

    public JLayeredPane menuLRPane;
    public JLabel menuLRBack;
    public JButton backGame;
    public JButton backWaitingRoom;
    public JButton quitGame;
    public LivingRoomMenuInterface(){
        int menuLRX = (PixelUtil.pcWidth - PixelUtil.menuLRW) / 2;
        int menuLRY = (PixelUtil.pcHeight - PixelUtil.menuLRH) / 2;
        setBounds(menuLRX, menuLRY, PixelUtil.menuLRW, PixelUtil.menuLRH);
        setUndecorated(true);
        setResizable(false);
        setTitle("Menu");

        menuLRPane = new JLayeredPane();
        menuLRPane.setBounds(0,0,PixelUtil.menuLRW,PixelUtil.menuLRH);

        menuLRPane.setLayout(null);
        menuLRPane.setOpaque(false);
        add(menuLRPane);

        menuLRBack = new JLabel();
        menuLRBack.setBounds(0,0,PixelUtil.menuLRW,PixelUtil.menuLRH);
        menuLRBack.setIcon(ImageUtil.getBoardImage("menuLRBack"));
        menuLRPane.add(menuLRBack,JLayeredPane.DEFAULT_LAYER);

        backGame = new JButton("BACK TO GAME");
        backGame.setFont(new Font("DejaVu Sans",Font.PLAIN,24));
        backGame.setBounds(PixelUtil.buttonLRX,PixelUtil.buttonBackLRY,PixelUtil.buttonLRW,PixelUtil.buttonLRH);
        backGame.setOpaque(true);
        backGame.setBackground(new Color(4, 134, 10, 230));
        backGame.setForeground(new Color(4, 134, 10, 230));
        backGame.addActionListener(e -> dispose());
        menuLRPane.add(backGame,JLayeredPane.PALETTE_LAYER);

        backWaitingRoom = new JButton("GO TO WAITING ROOM");
        backWaitingRoom.setFont(new Font("DejaVu Sans",Font.PLAIN,24));
        backWaitingRoom.setBounds(PixelUtil.buttonLRX,PixelUtil.buttonWaitLRY,PixelUtil.buttonLRW,PixelUtil.buttonLRH);
        backWaitingRoom.setOpaque(true);
        backWaitingRoom.setBackground(new Color(203, 63, 4, 230));
        backWaitingRoom.setForeground(new Color(203, 63, 4, 230));
        menuLRPane.add(backWaitingRoom,JLayeredPane.PALETTE_LAYER);

        quitGame = new JButton("LEAVE THE GAME");
        quitGame.setFont(new Font("DejaVu Sans",Font.PLAIN,24));
        quitGame.setBounds(PixelUtil.buttonLRX,PixelUtil.buttonLeaveLRY,PixelUtil.buttonLRW,PixelUtil.buttonLRH);
        quitGame.setOpaque(true);
        quitGame.setBackground(new Color(172, 19, 5, 230));
        quitGame.setForeground(new Color(172, 19, 5, 230));
        menuLRPane.add(quitGame,JLayeredPane.PALETTE_LAYER);

    }
}
