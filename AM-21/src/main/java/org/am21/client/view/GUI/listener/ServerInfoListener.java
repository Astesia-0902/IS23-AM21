package org.am21.client.view.GUI.listener;

import org.am21.client.view.GUI.Gui;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;

public class ServerInfoListener implements MouseListener, MouseMotionListener, ActionListener, KeyListener {
    Gui gui;
    Point p = new Point();

    public ServerInfoListener(Gui gui) {
        this.gui = gui;
        gui.serverInfoInterface.addMouseMotionListener(this);
        gui.serverInfoInterface.addMouseListener(this);
        gui.serverInfoInterface.closeLabel.addMouseListener(this);
        gui.serverInfoInterface.minusLabel.addMouseListener(this);
        gui.serverInfoInterface.returnLabel.addMouseListener(this);
        gui.serverInfoInterface.portField.addActionListener(this);
        gui.serverInfoInterface.portField.addKeyListener(this);
        gui.serverInfoInterface.addressField.addActionListener(this);
        gui.serverInfoInterface.addressField.addKeyListener(this);
        gui.serverInfoInterface.confirmButton.addActionListener(this);
        gui.serverInfoInterface.confirmButton.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // ASK SERVER INFO
        if (e.getSource() == gui.serverInfoInterface.confirmButton) {
            String address = gui.serverInfoInterface.addressField.getText().trim();
            String port = gui.serverInfoInterface.portField.getText().trim();

            if (address.isEmpty() || port.isEmpty()) {
                gui.serverInfoInterface.addressField.setBorder(new CompoundBorder(new MatteBorder(3, 3, 3, 3,
                        new Color(178, 34, 34)), new EmptyBorder(0, 50, 0, 0)));
                gui.serverInfoInterface.portField.setBorder(new CompoundBorder(new MatteBorder(3, 3, 3, 3,
                        new Color(178, 34, 34)), new EmptyBorder(0, 50, 0, 0)));
            } else {


                //TODO:ClientInputHandle...
                //TODO: MainFrameListener...

                // Login successful and close the login frame
                try {
                    gui.serverInfoInterface.setVisible(false);
                    gui.askLogin();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }

        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == gui.serverInfoInterface.closeLabel) {
            gui.frame.dispose();                         //close window
        }
        if (e.getSource() == gui.serverInfoInterface.minusLabel) {
            gui.frame.setExtendedState(Frame.ICONIFIED); // minimize window
        }
        if (e.getSource() == gui.serverInfoInterface.returnLabel){      // return to select communication
            try {
                gui.serverInfoInterface.setVisible(false);
                gui.init();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == gui.serverInfoInterface){
            gui.serverInfoInterface.confirmButton.requestFocus();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == gui.serverInfoInterface) {
            p.x = e.getX();
            p.y = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == gui.serverInfoInterface.closeLabel) {
            gui.serverInfoInterface.closeLabel.setBackground(new Color(222, 184, 135));
            gui.serverInfoInterface.closeLabel.setOpaque(true);

        }
        if (e.getSource() == gui.serverInfoInterface.minusLabel) {
            gui.serverInfoInterface.minusLabel.setBackground(new Color(222, 184, 135));
            gui.serverInfoInterface.minusLabel.setOpaque(true);
        }
        if (e.getSource() == gui.serverInfoInterface.returnLabel) {
            gui.serverInfoInterface.returnLabel.setIcon(gui.serverInfoInterface.returnIcon);
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == gui.serverInfoInterface.closeLabel) {
            gui.serverInfoInterface.closeLabel.setBackground(null);
            gui.serverInfoInterface.closeLabel.setOpaque(false);
        }
        if (e.getSource() == gui.serverInfoInterface.minusLabel) {
            gui.serverInfoInterface.minusLabel.setBackground(null);
            gui.serverInfoInterface.minusLabel.setOpaque(false);
        }
        if (e.getSource() == gui.serverInfoInterface.returnLabel) {
            gui.serverInfoInterface.returnLabel.setIcon(gui.serverInfoInterface.returnIconColor);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() == gui.serverInfoInterface) {
            Point panelPoint = gui.serverInfoInterface.getLocation();
            gui.serverInfoInterface.setLocation(panelPoint.x + e.getX() - p.x, panelPoint.y + e.getY() - p.y);
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
            gui.serverInfoInterface.confirmButton.doClick();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
