package org.am21.client.view.GUI.listener;

import org.am21.client.view.GUI.Gui;

import java.awt.*;
import java.awt.event.*;

public class WaitingRoomListener  implements MouseListener, MouseMotionListener, ActionListener {
    Gui gui;
    Point p = new Point();

    public WaitingRoomListener(Gui gui) {
        this.gui = gui;
        gui.waitingRoomInterface.addMouseListener(this);
        gui.waitingRoomInterface.leaveButton.addActionListener(this);
        gui.waitingRoomInterface.leaveButton.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gui.waitingRoomInterface.leaveButton){
            gui.waitingRoomInterface.dispose();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
