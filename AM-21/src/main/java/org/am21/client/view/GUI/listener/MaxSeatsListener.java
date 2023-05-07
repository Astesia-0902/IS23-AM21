package org.am21.client.view.GUI.listener;

import org.am21.client.view.GUI.Gui;

import java.awt.*;
import java.awt.event.*;

public class MaxSeatsListener  implements MouseListener, MouseMotionListener, ActionListener {
    Gui gui;
    Point p = new Point();

    public MaxSeatsListener(Gui gui) {
        this.gui = gui;
        gui.maxSeatsInterface.playerButton_2.addMouseListener(this);
        gui.maxSeatsInterface.playerButton_2.addActionListener(this);
        gui.maxSeatsInterface.playerButton_3.addMouseListener(this);
        gui.maxSeatsInterface.playerButton_3.addActionListener(this);
        gui.maxSeatsInterface.playerButton_4.addMouseListener(this);
        gui.maxSeatsInterface.playerButton_4.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gui.maxSeatsInterface.playerButton_2){
            //TODO: MAX SEATS = 2...
            gui.maxSeatsInterface.dispose();
            gui.menuActionInterface.dispose();
        }
        if (e.getSource() == gui.maxSeatsInterface.playerButton_3){
            //TODO: MAX SEATS = 3...
            gui.maxSeatsInterface.dispose();
            gui.menuActionInterface.dispose();
        }
        if (e.getSource() == gui.maxSeatsInterface.playerButton_4){
            //TODO: MAX SEATS = 4...
            gui.maxSeatsInterface.dispose();
            gui.menuActionInterface.dispose();
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == gui.maxSeatsInterface.playerButton_2){
            gui.maxSeatsInterface.playerButton_2.setIcon(gui.maxSeatsInterface.buttonSelectedIcon);
            gui.maxSeatsInterface.playerButton_3.setIcon(gui.maxSeatsInterface.buttonIcon);
            gui.maxSeatsInterface.playerButton_4.setIcon(gui.maxSeatsInterface.buttonIcon);
        }
        if (e.getSource() == gui.maxSeatsInterface.playerButton_3){
            gui.maxSeatsInterface.playerButton_2.setIcon(gui.maxSeatsInterface.buttonIcon);
            gui.maxSeatsInterface.playerButton_3.setIcon(gui.maxSeatsInterface.buttonSelectedIcon);
            gui.maxSeatsInterface.playerButton_4.setIcon(gui.maxSeatsInterface.buttonIcon);
        }
        if (e.getSource() == gui.maxSeatsInterface.playerButton_4){
            gui.maxSeatsInterface.playerButton_2.setIcon(gui.maxSeatsInterface.buttonIcon);
            gui.maxSeatsInterface.playerButton_3.setIcon(gui.maxSeatsInterface.buttonIcon);
            gui.maxSeatsInterface.playerButton_4.setIcon(gui.maxSeatsInterface.buttonSelectedIcon);
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

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
