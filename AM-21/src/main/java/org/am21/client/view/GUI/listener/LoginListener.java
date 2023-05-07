package org.am21.client.view.GUI.listener;

import org.am21.client.view.GUI.Gui;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;

public class LoginListener implements MouseListener, MouseMotionListener, ActionListener, KeyListener {
    Gui gui;
    Point p = new Point();

    public LoginListener(Gui gui) {
        this.gui = gui;
        gui.loginInterface.addMouseMotionListener(this);
        gui.loginInterface.addMouseListener(this);
        gui.loginInterface.closeLabel.addMouseListener(this);
        gui.loginInterface.minusLabel.addMouseListener(this);
        gui.loginInterface.nicknameField.addActionListener(this);
        gui.loginInterface.nicknameField.addKeyListener(this);
        gui.loginInterface.loginButton.addKeyListener(this);
        gui.loginInterface.loginButton.addMouseListener(this);
        gui.loginInterface.loginButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Press Login Button
        if (e.getSource() == gui.loginInterface.loginButton) {
            // Get username
            String username = gui.loginInterface.nicknameField.getText().trim();

            if (username.isEmpty()) {
                gui.loginInterface.nicknameField.setBorder(new CompoundBorder(new MatteBorder(3, 3, 5, 5,
                new Color(178, 34, 34)), new EmptyBorder(0, 50, 0, 0)));

            } else {
                //TODO:ClientInputHandle...
                //TODO: MainFrameListener...

                // Login successful and close the login frame

                try {
                    gui.loginInterface.setVisible(false);
                    gui.askMenuAction();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == gui.loginInterface.closeLabel) {
            gui.frame.dispose();                         //close window
        }
        if (e.getSource() == gui.loginInterface.minusLabel) {
            gui.frame.setExtendedState(Frame.ICONIFIED); // minimize window
        }
        if (e.getSource() == gui.loginInterface){
            gui.loginInterface.loginButton.requestFocus();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == gui.loginInterface) {
            p.x = e.getX();
            p.y = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == gui.loginInterface.closeLabel) {
            gui.loginInterface.closeLabel.setBackground(new Color(222, 184, 135));
            gui.loginInterface.closeLabel.setOpaque(true);
        }
        if (e.getSource() == gui.loginInterface.minusLabel) {
            gui.loginInterface.minusLabel.setBackground(new Color(222, 184, 135));
            gui.loginInterface.minusLabel.setOpaque(true);
        }
        if (e.getSource() == gui.loginInterface.loginButton) {
            gui.loginInterface.loginButton.setBackground(new Color(245, 225, 199));
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == gui.loginInterface.closeLabel) {
            gui.loginInterface.closeLabel.setBackground(null);
            gui.loginInterface.closeLabel.setOpaque(false);
        }
        if (e.getSource() == gui.loginInterface.minusLabel) {
            gui.loginInterface.minusLabel.setBackground(null);
            gui.loginInterface.minusLabel.setOpaque(false);
        }
        if (e.getSource() == gui.loginInterface.loginButton) {
            gui.loginInterface.loginButton.setBackground(new Color(222, 184, 135));
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() == gui.loginInterface) {
            Point panelPoint = gui.loginInterface.getLocation();
            gui.loginInterface.setLocation(panelPoint.x + e.getX() - p.x, panelPoint.y + e.getY() - p.y);
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
            gui.loginInterface.loginButton.doClick();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
