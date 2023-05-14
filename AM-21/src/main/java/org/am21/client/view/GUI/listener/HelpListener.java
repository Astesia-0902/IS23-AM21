package org.am21.client.view.GUI.listener;

import org.am21.client.view.GUI.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HelpListener implements MouseListener, ActionListener {
    Gui gui;
    Point p = new Point();

    public HelpListener(Gui gui) {
        this.gui = gui;
        gui.helpDialog.closeLabel.addMouseListener(this);
        gui.helpDialog.onButton.addActionListener(this);
        gui.helpDialog.onButton.addMouseListener(this);
        gui.helpDialog.offButton.addActionListener(this);
        gui.helpDialog.offButton.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gui.helpDialog.onButton) {
            gui.helpDialog.timer = new Timer(500, e1 -> {
                //TODO: assist on...
                gui.helpDialog.setVisible(false);
                gui.helpDialog.timer.stop();
            });
            gui.helpDialog.timer.start();
        }

        if (e.getSource() == gui.helpDialog.offButton) {
            gui.helpDialog.timer = new Timer(500, e1 -> {
                //TODO: assist off...
                gui.helpDialog.setVisible(false);
                gui.helpDialog.timer.stop();
            });
            gui.helpDialog.timer.start();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == gui.helpDialog.onButton) {
            gui.helpDialog.onButton.setIcon(gui.menuActionInterface.maxSeatsDialog.buttonSelectedIcon);
            gui.helpDialog.offButton.setIcon(gui.menuActionInterface.maxSeatsDialog.buttonIcon);
        }
        if (e.getSource() == gui.helpDialog.offButton) {
            gui.helpDialog.onButton.setIcon(gui.menuActionInterface.maxSeatsDialog.buttonIcon);
            gui.helpDialog.offButton.setIcon(gui.menuActionInterface.maxSeatsDialog.buttonSelectedIcon);
        }
        if (e.getSource() == gui.helpDialog.closeLabel) {
            gui.helpDialog.setVisible(false);
        }
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
}
