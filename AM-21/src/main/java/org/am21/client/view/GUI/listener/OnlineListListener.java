package org.am21.client.view.GUI.listener;

import org.am21.client.view.GUI.Gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

public class OnlineListListener implements MouseListener, MouseMotionListener, ActionListener, ListSelectionListener {
    Gui gui;
    Point p = new Point();

    public OnlineListListener(Gui gui) {
        this.gui = gui;
        gui.onlineListDialog.onlineList.addListSelectionListener(this);
        gui.onlineListDialog.closeLabel.addMouseListener(this);
        gui.onlineListDialog.addMouseListener(this);
        gui.onlineListDialog.addMouseMotionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == gui.onlineListDialog.closeLabel) {
            gui.onlineListDialog.setVisible(false);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == gui.onlineListDialog) {
            p.x = e.getX();
            p.y = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == gui.onlineListDialog.closeLabel) {
            gui.onlineListDialog.closeLabel.setIcon(gui.onlineListDialog.closeIconSelect);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == gui.onlineListDialog.closeLabel) {
            gui.chatDialog.closeLabel.setIcon(gui.chatDialog.closeIcon);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() == gui.onlineListDialog) {
            Point panelPoint = gui.onlineListDialog.getLocation();
            gui.onlineListDialog.setLocation(panelPoint.x + e.getX() - p.x, panelPoint.y + e.getY() - p.y);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        String user = gui.onlineListDialog.onlineList.getSelectedValue();
        gui.privateChat.put(user,new JLabel(user));
        gui.chatDialog.dispose();
        gui.askChat();

    }
}
