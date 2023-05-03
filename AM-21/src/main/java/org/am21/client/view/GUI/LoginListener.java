package org.am21.client.view.GUI;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;

public class LoginListener implements MouseListener, MouseMotionListener, ActionListener {
    LoginInterface loginInterface;
    Point p = new Point();

    public LoginListener(LoginInterface loginFrame) {
        this.loginInterface = loginFrame;
        loginFrame.closeLabel.addMouseListener(this);
        loginFrame.minusLabel.addMouseListener(this);
        loginFrame.nicknameField.addActionListener(this);
        loginFrame.loginButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Press Login Button
        if (e.getSource() == loginInterface.loginButton) {
            // Get username
            String username = loginInterface.nicknameField.getText().trim();

            if (username.isEmpty()) {
                loginInterface.nicknameField.setBorder(new CompoundBorder(new MatteBorder(0, 0, 6, 5,
                        new Color(178, 34, 34)), new EmptyBorder(0, 50, 0, 0)));
            } else {
                //TODO:ClientInputHandle...
                //TODO: MainFrameListener...

                // Login successful and close the login frame
                loginInterface.frame.dispose();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == loginInterface.closeLabel) {
            loginInterface.frame.dispose();                         //close window
        }
        if (e.getSource() == loginInterface.minusLabel) {
            loginInterface.frame.setExtendedState(Frame.ICONIFIED); // minimize window
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == loginInterface.frame) {
            p.x = e.getX();
            p.y = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == loginInterface.closeLabel) {
            loginInterface.closeLabel.setBackground(new Color(222, 184, 135));
            loginInterface.closeLabel.setOpaque(true);

        } else {
            loginInterface.closeLabel.setBackground(null);
            loginInterface.closeLabel.setOpaque(false);
        }
        if (e.getSource() == loginInterface.minusLabel) {
            loginInterface.minusLabel.setBackground(new Color(222, 184, 135));
            loginInterface.minusLabel.setOpaque(true);
        } else {
            loginInterface.minusLabel.setBackground(null);
            loginInterface.minusLabel.setOpaque(false);
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == loginInterface.closeLabel) {
            loginInterface.closeLabel.setBackground(null);
            loginInterface.closeLabel.setOpaque(false);
        }
        if (e.getSource() == loginInterface.minusLabel) {
            loginInterface.minusLabel.setBackground(null);
            loginInterface.minusLabel.setOpaque(false);
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
