package org.am21.client.view.GUI.listener;


import org.am21.client.view.GUI.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;


public class MenuActionListener implements MouseListener, MouseMotionListener, ActionListener {
    Gui gui;
    Point p = new Point();

    public MenuActionListener(Gui gui) {
        this.gui = gui;
        gui.menuActionInterface.addMouseMotionListener(this);
        gui.menuActionInterface.addMouseListener(this);
        gui.menuActionInterface.createButton.addMouseListener(this);
        gui.menuActionInterface.createButton.addActionListener(this);
        gui.menuActionInterface.joinButton.addMouseListener(this);
        gui.menuActionInterface.joinButton.addActionListener(this);
        gui.menuActionInterface.exitButton.addMouseListener(this);
        gui.menuActionInterface.exitButton.addActionListener(this);
        gui.menuActionInterface.chatButton.addActionListener(this);
        gui.menuActionInterface.chatButton.addMouseListener(this);
        gui.menuActionInterface.helpButton.addActionListener(this);
        gui.menuActionInterface.helpButton.addMouseListener(this);
        gui.menuActionInterface.onlineButton.addActionListener(this);
        gui.menuActionInterface.onlineButton.addMouseListener(this);

        gui.menuActionInterface.maxSeatsDialog.playerButton_2.addMouseListener(this);
        gui.menuActionInterface.maxSeatsDialog.playerButton_2.addActionListener(this);
        gui.menuActionInterface.maxSeatsDialog.playerButton_3.addMouseListener(this);
        gui.menuActionInterface.maxSeatsDialog.playerButton_3.addActionListener(this);
        gui.menuActionInterface.maxSeatsDialog.playerButton_4.addMouseListener(this);
        gui.menuActionInterface.maxSeatsDialog.playerButton_4.addActionListener(this);
        gui.menuActionInterface.maxSeatsDialog.closeLabel.addMouseListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gui.menuActionInterface.createButton) {
            //TODO: askMaxSeats()...then create a new match
            try {
                gui.askMaxSeats();

            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == gui.menuActionInterface.joinButton) {
            //TODO: join a Macth...

            try {
                //gui.menuActionInterface.dispose();
                gui.askJoinMatch();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == gui.menuActionInterface.exitButton) {

            try {
                gui.menuActionInterface.setVisible(false);
                gui.askLeaveMatch();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == gui.menuActionInterface.helpButton) {

            // TODO: askAssistMode()?
        }
        if (e.getSource() == gui.menuActionInterface.onlineButton) {
            try {
                gui.showOnlinePlayer();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == gui.menuActionInterface.chatButton) {
            //TODO: chat
            if(!Gui.chatPlayer.isEmpty()){
                gui.askChat();
            }
        }

        if (e.getSource() == gui.menuActionInterface.maxSeatsDialog.playerButton_2 ||
            e.getSource() == gui.menuActionInterface.maxSeatsDialog.playerButton_3 ||
            e.getSource() == gui.menuActionInterface.maxSeatsDialog.playerButton_4) {
            //TODO: MAX SEATS = 2/3/4...Create a new match with 2/3/4 players...Go to waiting room
            gui.menuActionInterface.timer = new Timer(500, e1 -> {
                gui.askWaitingAction();
                gui.menuActionInterface.dispose();
                gui.menuActionInterface.maxSeatsDialog.setVisible(false);
                gui.menuActionInterface.timer.stop();
            });
            gui.menuActionInterface.timer.start();
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == gui.menuActionInterface.maxSeatsDialog.playerButton_2) {
            gui.menuActionInterface.maxSeatsDialog.playerButton_2.setIcon(gui.menuActionInterface.maxSeatsDialog.buttonSelectedIcon);
            gui.menuActionInterface.maxSeatsDialog.playerButton_3.setIcon(gui.menuActionInterface.maxSeatsDialog.buttonIcon);
            gui.menuActionInterface.maxSeatsDialog.playerButton_4.setIcon(gui.menuActionInterface.maxSeatsDialog.buttonIcon);
        }
        if (e.getSource() == gui.menuActionInterface.maxSeatsDialog.playerButton_3) {
            gui.menuActionInterface.maxSeatsDialog.playerButton_2.setIcon(gui.menuActionInterface.maxSeatsDialog.buttonIcon);
            gui.menuActionInterface.maxSeatsDialog.playerButton_3.setIcon(gui.menuActionInterface.maxSeatsDialog.buttonSelectedIcon);
            gui.menuActionInterface.maxSeatsDialog.playerButton_4.setIcon(gui.menuActionInterface.maxSeatsDialog.buttonIcon);
        }
        if (e.getSource() == gui.menuActionInterface.maxSeatsDialog.playerButton_4) {
            gui.menuActionInterface.maxSeatsDialog.playerButton_2.setIcon(gui.menuActionInterface.maxSeatsDialog.buttonIcon);
            gui.menuActionInterface.maxSeatsDialog.playerButton_3.setIcon(gui.menuActionInterface.maxSeatsDialog.buttonIcon);
            gui.menuActionInterface.maxSeatsDialog.playerButton_4.setIcon(gui.menuActionInterface.maxSeatsDialog.buttonSelectedIcon);
        }
        // when click on closeLabel will close the interface
        if (e.getSource() == gui.menuActionInterface.maxSeatsDialog.closeLabel) {
            gui.menuActionInterface.maxSeatsDialog.setVisible(false);
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == gui.menuActionInterface) {
            p.x = e.getX();
            p.y = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == gui.menuActionInterface.createButton) {
            gui.menuActionInterface.createButton.setBackground(new Color(245, 225, 199));
        }
        if (e.getSource() == gui.menuActionInterface.joinButton) {
            gui.menuActionInterface.joinButton.setBackground(new Color(245, 225, 199));
        }
        if (e.getSource() == gui.menuActionInterface.exitButton) {
            gui.menuActionInterface.exitButton.setBackground(new Color(245, 225, 199));
        }

        if (e.getSource() == gui.menuActionInterface.helpButton) {
            gui.menuActionInterface.helpButton.setIcon(gui.menuActionInterface.helpIconColor);
        }

        if (e.getSource() == gui.menuActionInterface.chatButton) {
            gui.menuActionInterface.chatButton.setIcon(gui.menuActionInterface.chatIconColor);
        }

        if (e.getSource() == gui.menuActionInterface.onlineButton) {
            gui.menuActionInterface.onlineButton.setIcon(gui.menuActionInterface.onlineIconColor);
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == gui.menuActionInterface.createButton) {
            gui.menuActionInterface.createButton.setBackground(new Color(222, 184, 135));
        }
        if (e.getSource() == gui.menuActionInterface.joinButton) {
            gui.menuActionInterface.joinButton.setBackground(new Color(222, 184, 135));
        }
        if (e.getSource() == gui.menuActionInterface.exitButton) {
            gui.menuActionInterface.exitButton.setBackground(new Color(222, 184, 135));
        }

        if (e.getSource() == gui.menuActionInterface.helpButton) {
            gui.menuActionInterface.helpButton.setIcon(gui.menuActionInterface.helpIcon);
        }
        if (e.getSource() == gui.menuActionInterface.chatButton) {
            gui.menuActionInterface.chatButton.setIcon(gui.menuActionInterface.chatIcon);
        }
        if (e.getSource() == gui.menuActionInterface.onlineButton) {
            gui.menuActionInterface.onlineButton.setIcon(gui.menuActionInterface.onlineIcon);
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() == gui.menuActionInterface) {
            Point panelPoint = gui.menuActionInterface.getLocation();
            gui.menuActionInterface.setLocation(panelPoint.x + e.getX() - p.x, panelPoint.y + e.getY() - p.y);
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

}
