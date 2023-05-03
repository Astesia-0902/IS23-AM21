package org.am21.client.view.GUI;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;

public class ServerInfoListener implements MouseListener, MouseMotionListener, ActionListener {
    ServerInfoInterface serverInfoInterface;
    Point p = new Point();

    public ServerInfoListener(ServerInfoInterface serverInfoInterface) {
        this.serverInfoInterface = serverInfoInterface;
        serverInfoInterface.closeLabel.addMouseListener(this);
        serverInfoInterface.minusLabel.addMouseListener(this);
        serverInfoInterface.addressField.addActionListener(this);
        serverInfoInterface.portField.addActionListener(this);
        serverInfoInterface.enterButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Press the Login Button
        if (e.getSource() == serverInfoInterface.enterButton) {
            String address = serverInfoInterface.addressField.getText().trim();
            String port = serverInfoInterface.portField.getText().trim();

            if (address.isEmpty() || port.isEmpty()) {
                serverInfoInterface.addressField.setBorder(new CompoundBorder(new MatteBorder(0, 0, 6, 5,
                        new Color(178, 34, 34)), new EmptyBorder(0, 50, 0, 0)));
                serverInfoInterface.portField.setBorder(new CompoundBorder(new MatteBorder(0, 0, 6, 5,
                        new Color(178, 34, 34)), new EmptyBorder(0, 50, 0, 0)));
            } else {


                //TODO:ClientInputHandle...
                //TODO: MainFrameListener...

                // Login successful and close the login frame
                serverInfoInterface.frame.dispose();
            }

        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == serverInfoInterface.closeLabel) {
            serverInfoInterface.frame.dispose();                         //close window
        }
        if (e.getSource() == serverInfoInterface.minusLabel) {
            serverInfoInterface.frame.setExtendedState(Frame.ICONIFIED); // minimize window
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == serverInfoInterface.frame) {
            p.x = e.getX();
            p.y = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == serverInfoInterface.closeLabel) {
            serverInfoInterface.closeLabel.setBackground(new Color(222, 184, 135));
            serverInfoInterface.closeLabel.setOpaque(true);

        } else {
            serverInfoInterface.closeLabel.setBackground(null);
            serverInfoInterface.closeLabel.setOpaque(false);
        }
        if (e.getSource() == serverInfoInterface.minusLabel) {
            serverInfoInterface.minusLabel.setBackground(new Color(222, 184, 135));
            serverInfoInterface.minusLabel.setOpaque(true);
        } else {
            serverInfoInterface.minusLabel.setBackground(null);
            serverInfoInterface.minusLabel.setOpaque(false);
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == serverInfoInterface.closeLabel) {
            serverInfoInterface.closeLabel.setBackground(null);
            serverInfoInterface.closeLabel.setOpaque(false);
        }
        if (e.getSource() == serverInfoInterface.minusLabel) {
            serverInfoInterface.minusLabel.setBackground(null);
            serverInfoInterface.minusLabel.setOpaque(false);
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
