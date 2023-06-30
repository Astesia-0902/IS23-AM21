package org.am21.client.view.GUI.listener;

import org.am21.client.view.GUI.Gui;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

/**
 * Match list listener
 * */
public class MatchListListener implements MouseListener, MouseMotionListener, ActionListener, ListSelectionListener {
    Gui gui;
    Point p = new Point();

    /**
     * Constructor
     *
     * @param gui is the GUI
     */
    public MatchListListener(Gui gui) {
        this.gui = gui;
        gui.matchListInterface.matchList.addListSelectionListener(this);
        gui.matchListInterface.closeLabel.addMouseListener(this);
        gui.matchListInterface.addMouseListener(this);
        gui.matchListInterface.addMouseMotionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == gui.matchListInterface.closeLabel) {
            gui.matchListInterface.setVisible(false);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == gui.matchListInterface) {
            p.x = e.getX();
            p.y = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == gui.matchListInterface.closeLabel) {
            gui.matchListInterface.closeLabel.setIcon(gui.matchListInterface.closeIconSelect);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == gui.matchListInterface.closeLabel) {
            gui.matchListInterface.closeLabel.setIcon(gui.matchListInterface.closeIcon);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() == gui.matchListInterface) {
            Point panelPoint = gui.matchListInterface.getLocation();
            gui.matchListInterface.setLocation(panelPoint.x + e.getX() - p.x, panelPoint.y + e.getY() - p.y);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        String match = gui.matchListInterface.matchList.getSelectedValue();
        String id_string = match.substring(4, match.indexOf(" | ") - 1);
        int id = Integer.parseInt(id_string);

        if (gui.commCtrl.joinGame(id)) {
            gui.menuActionInterface.dispose();
            gui.matchListInterface.dispose();
        }

    }
}
