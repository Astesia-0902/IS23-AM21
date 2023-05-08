package org.am21.client.view.GUI.listener;

import org.am21.client.view.GUI.Gui;

import java.awt.*;
import java.awt.event.*;

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

        gui.waitingRoomInterface.ruleDialog.addMouseListener(this);
        gui.waitingRoomInterface.ruleDialog.addMouseMotionListener(this);
        gui.waitingRoomInterface.ruleDialog.leftButton.addActionListener(this);
        gui.waitingRoomInterface.ruleDialog.rightButton.addActionListener(this);
        gui.waitingRoomInterface.ruleDialog.closeLabel.addMouseListener(this);
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
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == gui.waitingRoomInterface.ruleDialog.closeLabel) {
            gui.waitingRoomInterface.ruleDialog.setVisible(false);
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
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
