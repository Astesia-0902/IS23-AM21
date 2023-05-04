package org.am21.client.view.GUI;

import java.awt.*;
import java.awt.event.*;

public class CommunicationListener implements MouseListener, MouseMotionListener, ActionListener {
    Gui gui;
    Point p = new Point();

    public CommunicationListener(Gui gui) {
        this.gui = gui;
        gui.communicationInterface.addMouseMotionListener(this);
        gui.communicationInterface.addMouseListener(this);
        gui.communicationInterface.closeLabel.addMouseListener(this);
        gui.communicationInterface.minusLabel.addMouseListener(this);
        gui.communicationInterface.socketButton.addActionListener(this);
        gui.communicationInterface.rmiButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Press the Login Button
        if (e.getSource() == gui.communicationInterface.socketButton) {
            //TODO: socket communication
            gui.communicationInterface.dispose();
            try {
                gui.communicationInterface.dispose();
                gui.askLogin();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        } else if (e.getSource() == gui.communicationInterface.rmiButton) {
            // RMI
            try {
                gui.communicationInterface.dispose();
                gui.askServerInfo();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == gui.communicationInterface.closeLabel) {
            gui.communicationInterface.dispose();                         //close window
        }
        if (e.getSource() == gui.communicationInterface.minusLabel) {
            gui.communicationInterface.setExtendedState(Frame.ICONIFIED); // minimize window
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == gui.communicationInterface) {
            p.x = e.getX();
            p.y = e.getY();
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == gui.communicationInterface.closeLabel) {
            gui.communicationInterface.closeLabel.setBackground(new Color(222, 184, 135));
            gui.communicationInterface.closeLabel.setOpaque(true);

        }
        if (e.getSource() == gui.communicationInterface.minusLabel) {
            gui.communicationInterface.minusLabel.setBackground(new Color(222, 184, 135));
            gui.communicationInterface.minusLabel.setOpaque(true);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == gui.communicationInterface.closeLabel) {
            gui.communicationInterface.closeLabel.setBackground(null);
            gui.communicationInterface.closeLabel.setOpaque(false);
        }
        if (e.getSource() == gui.communicationInterface.minusLabel) {
            gui.communicationInterface.minusLabel.setBackground(null);
            gui.communicationInterface.minusLabel.setOpaque(false);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() == gui.communicationInterface) {
            Point panelPoint = gui.communicationInterface.getLocation();
            gui.communicationInterface.setLocation(panelPoint.x + e.getX() - p.x, panelPoint.y + e.getY() - p.y);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
