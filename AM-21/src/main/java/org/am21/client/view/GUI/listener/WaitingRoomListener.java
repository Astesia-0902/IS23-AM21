package org.am21.client.view.GUI.listener;

import org.am21.client.view.ClientView;
import org.am21.client.view.GUI.Gui;
import org.am21.client.view.GUI.utils.ImageUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;

public class WaitingRoomListener implements MouseListener, MouseMotionListener, ActionListener {
    Gui gui;
    Point p = new Point();
    int playerNum = 0;

    /**
     * Constructor
     *
     * @param gui is the GUI
     */
    public WaitingRoomListener(Gui gui) {
        this.gui = gui;
        gui.waitingRoomInterface.addMouseListener(this);
        gui.waitingRoomInterface.addMouseMotionListener(this);
        gui.waitingRoomInterface.leaveButton.addActionListener(this);
        gui.waitingRoomInterface.leaveButton.addMouseListener(this);
        gui.waitingRoomInterface.ruleButton.addMouseListener(this);
        gui.waitingRoomInterface.ruleButton.addActionListener(this);
        gui.waitingRoomInterface.settingButton.addMouseListener(this);
        gui.waitingRoomInterface.settingButton.addActionListener(this);
        gui.waitingRoomInterface.chatButton.addActionListener(this);
        gui.waitingRoomInterface.chatButton.addMouseListener(this);
        gui.waitingRoomInterface.onlineButton.addActionListener(this);
        gui.waitingRoomInterface.onlineButton.addMouseListener(this);

        gui.waitingRoomInterface.maxSeatsDialog.playerButton_2.addMouseListener(this);
        gui.waitingRoomInterface.maxSeatsDialog.playerButton_2.addActionListener(this);
        gui.waitingRoomInterface.maxSeatsDialog.playerButton_3.addMouseListener(this);
        gui.waitingRoomInterface.maxSeatsDialog.playerButton_3.addActionListener(this);
        gui.waitingRoomInterface.maxSeatsDialog.playerButton_4.addMouseListener(this);
        gui.waitingRoomInterface.maxSeatsDialog.playerButton_4.addActionListener(this);
        gui.waitingRoomInterface.maxSeatsDialog.closeLabel.addMouseListener(this);
    }

    /**
     * Action performed method
     * @param e is the mouse event
     * */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gui.waitingRoomInterface.leaveButton) {
            try {
                System.out.println("Try to leave");
                if (gui.askLeaveMatch()) {
                    //TEMP
                    System.out.println("Leave Waiting Room");
                    ClientView.setGoToMenu(true);
                    ClientView.setGameOn(false);
                    gui.setNeedNewFrame(true);
                    //Remove Match Group Chat from myChatMap
                    Gui.myChatMap.remove("#All");
                    gui.waitingRoomInterface.dispose();
                }
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == gui.waitingRoomInterface.ruleButton) {
            gui.showGameRules();
        }
        if (e.getSource() == gui.waitingRoomInterface.settingButton) {
            gui.askChangeSeats();
        }

        if (e.getSource() == gui.waitingRoomInterface.onlineButton) {
            try {
                gui.showOnlinePlayer();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == gui.waitingRoomInterface.chatButton) {

            if (!Gui.myChatMap.containsKey("#All")) {
                Gui.myChatMap.put("#All", new JButton("#All"));
            }
            gui.setNewChatWindow(true);
            Gui.chatReceiver = "#All";
            if (gui.chatDialog != null) {
                gui.chatDialog.localChatMap.get(Gui.chatReceiver).setBackground(new Color(83, 46, 91, 230));
                FontMetrics fm = gui.chatDialog.chatMessageInput.getFontMetrics(gui.chatDialog.chatMessageInput.getFont());
                gui.chatDialog.chatMessageInput.setBorder(new EmptyBorder(0, ImageUtil.resizeX(fm.stringWidth(Gui.chatReceiver) + 30), 0, 0));
            }
            gui.setAskChat(true);
        }

        if (e.getSource() == gui.waitingRoomInterface.maxSeatsDialog.playerButton_2 ||
            e.getSource() == gui.waitingRoomInterface.maxSeatsDialog.playerButton_3 ||
            e.getSource() == gui.waitingRoomInterface.maxSeatsDialog.playerButton_4) {
            playerNum = 0;

            if (e.getSource() == gui.waitingRoomInterface.maxSeatsDialog.playerButton_2) {
                playerNum = 2;
            } else if (e.getSource() == gui.waitingRoomInterface.maxSeatsDialog.playerButton_3) {
                playerNum = 3;
            } else if (e.getSource() == gui.waitingRoomInterface.maxSeatsDialog.playerButton_4) {
                playerNum = 4;
            }

            if (gui.commCtrl.changeMatchSeats(playerNum)) {
                SwingUtilities.invokeLater(() -> {
                    gui.waitingRoomInterface.reloadPlayerNumber(gui.waitingRoomInterface.minNum,
                            String.valueOf(playerNum), gui.waitingRoomInterface.ID);
                    gui.waitingRoomInterface.revalidate();
                    gui.waitingRoomInterface.repaint();
                });

                gui.replyDEBUG("Number of Seats available changed");
                gui.waitingRoomInterface.timer = new Timer(500, e1 -> {
                    gui.waitingRoomInterface.maxSeatsDialog.setVisible(false);
                    gui.waitingRoomInterface.timer.stop();
                });
                gui.waitingRoomInterface.timer.start();


            } else {
                gui.replyDEBUG("Operation failed: Only the admin are allowed to change settings");
            }

        }
    }

    /**
     * Mouse clicked method
     * @param e is the mouse event
     * */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == gui.waitingRoomInterface.maxSeatsDialog.playerButton_2) {
            gui.waitingRoomInterface.maxSeatsDialog.playerButton_2.setIcon(gui.waitingRoomInterface.maxSeatsDialog.buttonSelectedIcon);
            gui.waitingRoomInterface.maxSeatsDialog.playerButton_3.setIcon(gui.waitingRoomInterface.maxSeatsDialog.buttonIcon);
            gui.waitingRoomInterface.maxSeatsDialog.playerButton_4.setIcon(gui.waitingRoomInterface.maxSeatsDialog.buttonIcon);
        }
        if (e.getSource() == gui.waitingRoomInterface.maxSeatsDialog.playerButton_3) {
            gui.waitingRoomInterface.maxSeatsDialog.playerButton_2.setIcon(gui.waitingRoomInterface.maxSeatsDialog.buttonIcon);
            gui.waitingRoomInterface.maxSeatsDialog.playerButton_3.setIcon(gui.waitingRoomInterface.maxSeatsDialog.buttonSelectedIcon);
            gui.waitingRoomInterface.maxSeatsDialog.playerButton_4.setIcon(gui.waitingRoomInterface.maxSeatsDialog.buttonIcon);
        }
        if (e.getSource() == gui.waitingRoomInterface.maxSeatsDialog.playerButton_4) {
            gui.waitingRoomInterface.maxSeatsDialog.playerButton_2.setIcon(gui.waitingRoomInterface.maxSeatsDialog.buttonIcon);
            gui.waitingRoomInterface.maxSeatsDialog.playerButton_3.setIcon(gui.waitingRoomInterface.maxSeatsDialog.buttonIcon);
            gui.waitingRoomInterface.maxSeatsDialog.playerButton_4.setIcon(gui.waitingRoomInterface.maxSeatsDialog.buttonSelectedIcon);
        }
        if (e.getSource() == gui.waitingRoomInterface.maxSeatsDialog.closeLabel) {
            gui.waitingRoomInterface.maxSeatsDialog.setVisible(false);
        }
    }

    /**
     * Mouse pressed method
     * @param e is the mouse event
     * */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == gui.waitingRoomInterface) {
            p.x = e.getX();
            p.y = e.getY();
        }
    }

    /**
     * Mouse released method
     * @param e is the mouse event
     * */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Mouse entered method
     * @param e is the mouse event
     * */
    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == gui.waitingRoomInterface.leaveButton) {
            gui.waitingRoomInterface.leaveButton.setBackground(new Color(245, 225, 199));
        }
        if (e.getSource() == gui.waitingRoomInterface.ruleButton) {
            gui.waitingRoomInterface.ruleButton.setForeground(new Color(106, 2, 1));
        }
        if (e.getSource() == gui.waitingRoomInterface.settingButton) {
            gui.waitingRoomInterface.settingButton.setForeground(new Color(106, 2, 1));
        }

        if (e.getSource() == gui.waitingRoomInterface.chatButton) {
            gui.waitingRoomInterface.chatButton.setIcon(gui.menuActionInterface.chatIconColor);
        }

        if (e.getSource() == gui.waitingRoomInterface.onlineButton) {
            gui.waitingRoomInterface.onlineButton.setIcon(gui.menuActionInterface.onlineIconColor);
        }
    }

    /**
     * Mouse exited method
     * @param e is the mouse event
     * */
    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == gui.waitingRoomInterface.leaveButton) {
            gui.waitingRoomInterface.leaveButton.setBackground(new Color(222, 184, 135));
        }
        if (e.getSource() == gui.waitingRoomInterface.ruleButton) {
            gui.waitingRoomInterface.ruleButton.setForeground(new Color(237, 179, 137));
        }
        if (e.getSource() == gui.waitingRoomInterface.settingButton) {
            gui.waitingRoomInterface.settingButton.setForeground(new Color(237, 179, 137));
        }

        if (e.getSource() == gui.waitingRoomInterface.chatButton) {
            gui.waitingRoomInterface.chatButton.setIcon(gui.menuActionInterface.chatIcon);
        }
        if (e.getSource() == gui.waitingRoomInterface.onlineButton) {
            gui.waitingRoomInterface.onlineButton.setIcon(gui.menuActionInterface.onlineIcon);
        }
    }

    /**
     * Mouse dragged method
     * @param e is the mouse event
     * */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() == gui.waitingRoomInterface) {
            Point panelPoint = gui.waitingRoomInterface.getLocation();
            gui.waitingRoomInterface.setLocation(panelPoint.x + e.getX() - p.x, panelPoint.y + e.getY() - p.y);
        }
    }

    /**
     * Mouse moved method
     * @param e is the mouse event
     * */
    @Override
    public void mouseMoved(MouseEvent e) {

    }

}
