package org.am21.client.view.GUI.listener;

import org.am21.client.ClientCommunicationController;
import org.am21.client.view.GUI.Gui;

import java.awt.*;
import java.awt.event.*;

public class CommunicationListener implements MouseListener, MouseMotionListener, ActionListener {
    Gui gui;
    Point p = new Point();

    /**
     * Constructor
     *
     * @param gui is the GUI
     */
    public CommunicationListener(Gui gui) {
        this.gui = gui;
        gui.frame.addMouseListener(this);
        gui.communicationInterface.addMouseMotionListener(this);
        gui.communicationInterface.addMouseListener(this);
        gui.communicationInterface.closeLabel.addMouseListener(this);
        gui.communicationInterface.minusLabel.addMouseListener(this);
        gui.communicationInterface.socketButton.addActionListener(this);
        gui.communicationInterface.socketButton.addMouseListener(this);
        gui.communicationInterface.rmiButton.addActionListener(this);
        gui.communicationInterface.rmiButton.addMouseListener(this);
    }

    /**
     * Action performed method
     *
     * @param e is the mouse event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Press the Login Button
        if (e.getSource() == gui.communicationInterface.socketButton) {
            // socket communication
            try {
                ClientCommunicationController.isRMI = false;
                gui.communicationInterface.setVisible(false);
                gui.askServerInfoSocket();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        } else if (e.getSource() == gui.communicationInterface.rmiButton) {
            // RMI
            try {
                ClientCommunicationController.isRMI = true;
                gui.communicationInterface.setVisible(false);
                gui.askServerInfoRMI();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    /**
     * Mouse clicked method
     *
     * @param e is the mouse event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == gui.communicationInterface.closeLabel) {
            gui.frame.dispose();                         //close window
        }
        if (e.getSource() == gui.communicationInterface.minusLabel) {
            gui.frame.setExtendedState(Frame.ICONIFIED); // minimize window
        }
    }

    /**
     * Mouse pressed method
     *
     * @param e is the mouse event
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == gui.communicationInterface) {
            p.x = e.getX();
            p.y = e.getY();
        }

    }

    /**
     * Mouse released method
     *
     * @param e is the mouse event
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Mouse entered method
     *
     * @param e is the mouse event
     */
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

        if (e.getSource() == gui.communicationInterface.socketButton) {
            gui.communicationInterface.socketButton.setBackground(new Color(245, 225, 199));
        }
        if (e.getSource() == gui.communicationInterface.rmiButton) {
            gui.communicationInterface.rmiButton.setBackground(new Color(245, 225, 199));
        }
    }

    /**
     * Mouse exited method
     *
     * @param e is the mouse event
     */
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
        if (e.getSource() == gui.communicationInterface.socketButton) {
            gui.communicationInterface.socketButton.setBackground(new Color(222, 184, 135));
        }
        if (e.getSource() == gui.communicationInterface.rmiButton) {
            gui.communicationInterface.rmiButton.setBackground(new Color(222, 184, 135));
        }
    }

    /**
     * Mouse dragged method
     *
     * @param e is the mouse event
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() == gui.communicationInterface) {
            Point panelPoint = gui.communicationInterface.getLocation();
            gui.communicationInterface.setLocation(panelPoint.x + e.getX() - p.x, panelPoint.y + e.getY() - p.y);
        }
    }

    /**
     * Mouse moved method
     *
     * @param e is the mouse event
     */
    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
