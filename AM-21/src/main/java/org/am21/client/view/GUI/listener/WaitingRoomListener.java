package org.am21.client.view.GUI.listener;

import org.am21.client.view.GUI.Gui;

import java.awt.*;
import java.awt.event.*;

public class WaitingRoomListener implements MouseListener, MouseMotionListener, ActionListener,KeyListener {
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

        gui.waitingRoomInterface.ruleDialog.addMouseListener(this);
        gui.waitingRoomInterface.ruleDialog.addMouseMotionListener(this);
        gui.waitingRoomInterface.ruleDialog.leftButton.addActionListener(this);
        gui.waitingRoomInterface.ruleDialog.rightButton.addActionListener(this);
        gui.waitingRoomInterface.ruleDialog.closeLabel.addMouseListener(this);

        gui.waitingRoomInterface.chatDialog.sendButton.addMouseListener(this);
        gui.waitingRoomInterface.chatDialog.sendButton.addKeyListener(this);
        gui.waitingRoomInterface.chatDialog.sendButton.addActionListener(this);
        gui.waitingRoomInterface.chatDialog.addMouseListener(this);
        gui.waitingRoomInterface.chatDialog.addMouseMotionListener(this);
        gui.waitingRoomInterface.chatDialog.closeLabel.addMouseListener(this);
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

        if (e.getSource() == gui.waitingRoomInterface.ruleDialog.rightButton) {
            if (gui.waitingRoomInterface.ruleDialog.countPage != 5) {
                gui.waitingRoomInterface.ruleDialog.rules.next(gui.waitingRoomInterface.ruleDialog.rulesPanel);
                gui.waitingRoomInterface.ruleDialog.countPage++;
            }
        }
        if (e.getSource() == gui.waitingRoomInterface.ruleDialog.leftButton) {
            if (gui.waitingRoomInterface.ruleDialog.countPage != 0) {
                gui.waitingRoomInterface.ruleDialog.rules.previous(gui.waitingRoomInterface.ruleDialog.rulesPanel);
                gui.waitingRoomInterface.ruleDialog.countPage--;
            }
        }
        if (e.getSource() == gui.waitingRoomInterface.chatDialog.sendButton) {
            String message = gui.waitingRoomInterface.chatDialog.chatMessage.getText();
            //TODO: sendMessage(message);
            gui.waitingRoomInterface.chatDialog.chatMessage.setText("");     //Clear input box
        }
        if (e.getSource() == gui.waitingRoomInterface.helpButton) {

            // TODO: askAssistMode()?
        }
        if (e.getSource() == gui.waitingRoomInterface.onlineButton) {
            //gui.showOnlinePlayer();

        }
        if (e.getSource() == gui.waitingRoomInterface.chatButton) {
            //TODO: chat
            gui.waitingRoomInterface.chatDialog.setVisible(true);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == gui.waitingRoomInterface.ruleDialog.closeLabel) {
            gui.waitingRoomInterface.ruleDialog.setVisible(false);
        }
        if (e.getSource() == gui.waitingRoomInterface.chatDialog.closeLabel) {
            gui.waitingRoomInterface.chatDialog.setVisible(false);
        }
        if (e.getSource() == gui.waitingRoomInterface.chatDialog) {
            gui.waitingRoomInterface.chatDialog.sendButton.requestFocus();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == gui.waitingRoomInterface) {
            p.x = e.getX();
            p.y = e.getY();
        }
        if (e.getSource() == gui.waitingRoomInterface.ruleDialog) {
            p.x = e.getX();
            p.y = e.getY();
        }
        if (e.getSource() == gui.waitingRoomInterface.chatDialog) {
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
        if (e.getSource() == gui.waitingRoomInterface.chatDialog.sendButton) {
            gui.waitingRoomInterface.chatDialog.sendButton.setBackground(new Color(243, 214, 253));
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
        if (e.getSource() == gui.waitingRoomInterface.chatDialog.closeLabel) {
            gui.waitingRoomInterface.chatDialog.closeLabel.setIcon(gui.menuActionInterface.chatDialog.closeIconSelect);
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
        if (e.getSource() == gui.waitingRoomInterface.chatDialog.closeLabel) {
            gui.waitingRoomInterface.chatDialog.closeLabel.setIcon(gui.menuActionInterface.chatDialog.closeIcon);
        }
        if (e.getSource() == gui.waitingRoomInterface.chatDialog.sendButton) {
            gui.waitingRoomInterface.chatDialog.sendButton.setBackground(new Color(178, 173, 204, 230));
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() == gui.waitingRoomInterface) {
            Point panelPoint = gui.waitingRoomInterface.getLocation();
            gui.waitingRoomInterface.setLocation(panelPoint.x + e.getX() - p.x, panelPoint.y + e.getY() - p.y);
        }
        if (e.getSource() == gui.waitingRoomInterface.ruleDialog) {
            Point panelPoint = gui.waitingRoomInterface.ruleDialog.getLocation();
            gui.waitingRoomInterface.ruleDialog.setLocation(panelPoint.x + e.getX() - p.x, panelPoint.y + e.getY() - p.y);
        }
        if (e.getSource() == gui.waitingRoomInterface.chatDialog) {
            Point panelPoint = gui.waitingRoomInterface.chatDialog.getLocation();
            gui.waitingRoomInterface.chatDialog.setLocation(panelPoint.x + e.getX() - p.x, panelPoint.y + e.getY() - p.y);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            gui.waitingRoomInterface.chatDialog.sendButton.doClick();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
