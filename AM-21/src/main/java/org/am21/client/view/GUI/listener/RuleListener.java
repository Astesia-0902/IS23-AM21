package org.am21.client.view.GUI.listener;

import org.am21.client.view.GUI.Gui;

import java.awt.*;
import java.awt.event.*;

public class RuleListener implements MouseListener, MouseMotionListener, ActionListener {
    Gui gui;
    Point p = new Point();

    /**
     * Constructor
     *
     * @param gui is the GUI
     */
    public RuleListener(Gui gui) {
        this.gui = gui;
        gui.ruleDialog.addMouseListener(this);
        gui.ruleDialog.addMouseMotionListener(this);
        gui.ruleDialog.leftButton.addActionListener(this);
        gui.ruleDialog.rightButton.addActionListener(this);
        gui.ruleDialog.closeLabel.addMouseListener(this);
    }

    /**
     * Action performed method
     *
     * @param e is the mouse event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gui.ruleDialog.rightButton) {
            if (gui.ruleDialog.countPage != 5) {
                gui.ruleDialog.rules.next(gui.ruleDialog.rulesPanel);
                gui.ruleDialog.countPage++;
            }
        }
        if (e.getSource() == gui.ruleDialog.leftButton) {
            if (gui.ruleDialog.countPage != 0) {
                gui.ruleDialog.rules.previous(gui.ruleDialog.rulesPanel);
                gui.ruleDialog.countPage--;
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
        if (e.getSource() == gui.ruleDialog.closeLabel) {
            gui.ruleDialog.setVisible(false);
        }
    }

    /**
     * Mouse pressed method
     *
     * @param e is the mouse event
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == gui.ruleDialog) {
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

    }

    /**
     * Mouse exited method
     *
     * @param e is the mouse event
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Mouse dragged method
     *
     * @param e is the mouse event
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() == gui.ruleDialog) {
            Point panelPoint = gui.ruleDialog.getLocation();
            gui.ruleDialog.setLocation(panelPoint.x + e.getX() - p.x, panelPoint.y + e.getY() - p.y);
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
