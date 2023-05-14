package org.am21.client.view.GUI.listener;

import org.am21.client.view.GUI.Gui;
import org.am21.client.view.GUI.utils.ImageUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;

public class WaitingRoomListener implements MouseListener, MouseMotionListener, ActionListener {
    Gui gui;
    Point p = new Point();


    public WaitingRoomListener(Gui gui) {
        this.gui = gui;
        gui.waitingRoomInterface.addMouseListener(this);
        gui.waitingRoomInterface.addMouseMotionListener(this);
        gui.waitingRoomInterface.leaveButton.addActionListener(this);
        gui.waitingRoomInterface.leaveButton.addMouseListener(this);
        gui.waitingRoomInterface.ruleButton.addMouseListener(this);
        gui.waitingRoomInterface.ruleButton.addActionListener(this);
        gui.waitingRoomInterface.settingButton.addMouseListener(this);
        gui.waitingRoomInterface.settingButton.addActionListener(this);
        gui.waitingRoomInterface.chatButton.addActionListener(this);
        gui.waitingRoomInterface.chatButton.addMouseListener(this);
        gui.waitingRoomInterface.helpButton.addActionListener(this);
        gui.waitingRoomInterface.helpButton.addMouseListener(this);
        gui.waitingRoomInterface.onlineButton.addActionListener(this);
        gui.waitingRoomInterface.onlineButton.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gui.waitingRoomInterface.leaveButton) {
            //TODO: askLeaveMatch;
            gui.frame.dispose();
        }
        if (e.getSource() == gui.waitingRoomInterface.ruleButton) {
            gui.showGameRules();
        }
        if (e.getSource() == gui.waitingRoomInterface.settingButton) {
            if (gui.commCtrl.changeMatchSeats(gui.askMaxSeats())) {
                gui.replyDEBUG("Number of Seats available changed" );
            } else {
                gui.replyDEBUG("Operation failed: Only the admin are allowed to change settings");
            }
        }
        if (e.getSource() == gui.waitingRoomInterface.helpButton) {
            gui.askAssistMode();
        }
        if (e.getSource() == gui.waitingRoomInterface.onlineButton) {
            try {
                gui.showOnlinePlayer();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == gui.waitingRoomInterface.chatButton) {
            //TODO: chat
            Gui.chatPlayer.put("#All", new JButton("#All"));
            Gui.chatUser = "#All";
            if (gui.chatDialog != null) {
                gui.chatDialog.privateChat.get(Gui.chatUser).setBackground(new Color(83, 46, 91, 230));
                FontMetrics fm = gui.chatDialog.chatMessage.getFontMetrics(gui.chatDialog.chatMessage.getFont());
                gui.chatDialog.chatMessage.setBorder(new EmptyBorder(0, ImageUtil.resizeX(fm.stringWidth(Gui.chatUser) + 30), 0, 0));
            }
            gui.askChat();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == gui.waitingRoomInterface) {
            p.x = e.getX();
            p.y = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == gui.waitingRoomInterface.leaveButton) {
            gui.waitingRoomInterface.leaveButton.setBackground(new Color(245, 225, 199));
        }
        if (e.getSource() == gui.waitingRoomInterface.ruleButton) {
            gui.waitingRoomInterface.ruleButton.setForeground(new Color(106, 2, 1));
        }
        if (e.getSource() == gui.waitingRoomInterface.settingButton) {
            gui.waitingRoomInterface.settingButton.setForeground(new Color(106, 2, 1));
        }
        if (e.getSource() == gui.waitingRoomInterface.helpButton) {
            gui.waitingRoomInterface.helpButton.setIcon(gui.menuActionInterface.helpIconColor);
        }

        if (e.getSource() == gui.waitingRoomInterface.chatButton) {
            gui.waitingRoomInterface.chatButton.setIcon(gui.menuActionInterface.chatIconColor);
        }

        if (e.getSource() == gui.waitingRoomInterface.onlineButton) {
            gui.waitingRoomInterface.onlineButton.setIcon(gui.menuActionInterface.onlineIconColor);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == gui.waitingRoomInterface.leaveButton) {
            gui.waitingRoomInterface.leaveButton.setBackground(new Color(222, 184, 135));
        }
        if (e.getSource() == gui.waitingRoomInterface.ruleButton) {
            gui.waitingRoomInterface.ruleButton.setForeground(new Color(237, 179, 137));
        }
        if (e.getSource() == gui.waitingRoomInterface.settingButton) {
            gui.waitingRoomInterface.settingButton.setForeground(new Color(237, 179, 137));
        }

        if (e.getSource() == gui.waitingRoomInterface.helpButton) {
            gui.waitingRoomInterface.helpButton.setIcon(gui.menuActionInterface.helpIcon);
        }
        if (e.getSource() == gui.waitingRoomInterface.chatButton) {
            gui.waitingRoomInterface.chatButton.setIcon(gui.menuActionInterface.chatIcon);
        }
        if (e.getSource() == gui.waitingRoomInterface.onlineButton) {
            gui.waitingRoomInterface.onlineButton.setIcon(gui.menuActionInterface.onlineIcon);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() == gui.waitingRoomInterface) {
            Point panelPoint = gui.waitingRoomInterface.getLocation();
            gui.waitingRoomInterface.setLocation(panelPoint.x + e.getX() - p.x, panelPoint.y + e.getY() - p.y);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

}
