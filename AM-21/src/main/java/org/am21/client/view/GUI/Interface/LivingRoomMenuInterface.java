package org.am21.client.view.GUI.Interface;

import org.am21.client.view.GUI.Gui;
import org.am21.client.view.GUI.component.ButtonColorUI;
import org.am21.client.view.GUI.utils.FontUtil;
import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.rmi.RemoteException;


public class LivingRoomMenuInterface extends JFrame {

    public JLayeredPane menuLRPane;
    public JLabel menuLRBack;
    public JButton backGame;
    public JButton leaveMatch;
    public JButton quitGame;

    public LivingRoomMenuInterface(Gui gui) {

        setLivingRoomMenuInterfacePane();

        setBackGameButton();

        setLeaveButton(gui);

        setQuitButton(gui);

    }

    /**
     * base set
     */
    public void setLivingRoomMenuInterfacePane() {
        int menuLRX = (PixelUtil.pcWidth - PixelUtil.menuLRW) / 2;
        int menuLRY = (PixelUtil.pcHeight - PixelUtil.menuLRH) / 2;
        setBounds(menuLRX, menuLRY, PixelUtil.menuLRW, PixelUtil.menuLRH);
        setUndecorated(true);
        setResizable(false);
        setTitle("Menu");

        menuLRPane = new JLayeredPane();
        menuLRPane.setBounds(0, 0, PixelUtil.menuLRW, PixelUtil.menuLRH);
        menuLRPane.setLayout(null);
        menuLRPane.setOpaque(false);
        add(menuLRPane);

        menuLRBack = new JLabel();
        menuLRBack.setBounds(0, 0, PixelUtil.menuLRW, PixelUtil.menuLRH);
        menuLRBack.setIcon(ImageUtil.getBoardImage("menuLRBack"));
        menuLRPane.add(menuLRBack, JLayeredPane.DEFAULT_LAYER);
    }

    /**
     * set back game button
     */
    public void setBackGameButton() {

        backGame = new JButton("BACK TO PLAY");
        backGame.setFont(FontUtil.getFontByName("HongLeiXingShuJianTi-2")
                .deriveFont(Font.PLAIN, ImageUtil.resizeY(24)));
        backGame.setBounds(PixelUtil.buttonLRX, PixelUtil.buttonBackLRY, PixelUtil.buttonLRW, PixelUtil.buttonLRH);
        backGame.setBorder(new MatteBorder(ImageUtil.resizeY(2), ImageUtil.resizeX(2), ImageUtil.resizeY(2),
                ImageUtil.resizeX(2), new Color(4, 134, 10, 230)));
        backGame.setUI(new ButtonColorUI(new Color(136, 218, 123, 139)));
        backGame.setBackground(Color.WHITE);
        backGame.setForeground(new Color(4, 134, 10, 230));
        backGame.addActionListener(e -> {
            this.setVisible(false);
            this.dispose();
        });
        menuLRPane.add(backGame, JLayeredPane.PALETTE_LAYER);

    }

    /**
     * set leave button
     *
     * @param gui GUI
     */
    public void setLeaveButton(Gui gui) {

        leaveMatch = new JButton("LEAVE MATCH");
        leaveMatch.setFont(FontUtil.getFontByName("HongLeiXingShuJianTi-2")
                .deriveFont(Font.PLAIN, ImageUtil.resizeY(24)));
        leaveMatch.setBounds(PixelUtil.buttonLRX, PixelUtil.buttonWaitLRY, PixelUtil.buttonLRW, PixelUtil.buttonLRH);
        leaveMatch.setBorder(new MatteBorder(ImageUtil.resizeY(2), ImageUtil.resizeX(2), ImageUtil.resizeY(2),
                ImageUtil.resizeX(2), new Color(203, 63, 4, 230)));
        leaveMatch.setUI(new ButtonColorUI(new Color(231, 163, 135, 230)));
        leaveMatch.setBackground(Color.WHITE);
        leaveMatch.setForeground(new Color(203, 63, 4, 230));
        leaveMatch.addActionListener(e -> {
            try {
                if (gui.askLeaveMatch()) {
                    gui.setNeedNewFrame(true);
                    dispose();
                }
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }

        });
        menuLRPane.add(leaveMatch, JLayeredPane.PALETTE_LAYER);
    }

    /**
     * set exit button
     *
     * @param gui GUI
     */
    public void setQuitButton(Gui gui) {

        quitGame = new JButton("EXIT GAME");
        quitGame.setFont(FontUtil.getFontByName("HongLeiXingShuJianTi-2")
                .deriveFont(Font.PLAIN, ImageUtil.resizeY(24)));
        quitGame.setBounds(PixelUtil.buttonLRX, PixelUtil.buttonLeaveLRY, PixelUtil.buttonLRW, PixelUtil.buttonLRH);
        quitGame.setBorder(new MatteBorder(ImageUtil.resizeY(2), ImageUtil.resizeX(2), ImageUtil.resizeY(2),
                ImageUtil.resizeX(2), new Color(172, 19, 5, 230)));
        quitGame.setBackground(Color.WHITE);
        quitGame.setUI(new ButtonColorUI(new Color(255, 181, 172, 139)));
        quitGame.setForeground(new Color(172, 19, 5, 230));
        quitGame.addActionListener(e -> {
            try {
                gui.askExitGame();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }

        });
        menuLRPane.add(quitGame, JLayeredPane.PALETTE_LAYER);
    }
}
