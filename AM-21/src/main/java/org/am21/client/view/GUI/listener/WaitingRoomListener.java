package org.am21.client.view.GUI.listener;

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
    int playerNum=0;


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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gui.waitingRoomInterface.leaveButton) {
            try {
                System.out.println("Try to leave");
                if(gui.askLeaveMatch()) {
                    //TEMP
                    System.out.println("Leave Waiting Room");
                    gui.GO_TO_MENU = true;
                    gui.GAME_ON = false;
                    gui.NEED_NEW_FRAME = true;
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
            //TODO: Change method of askMaxSeats in different one
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
            //TODO: chat
            Gui.myChatMap.put("#All", new JButton("#All"));
            Gui.chatReceiver = "#All";
            if (gui.chatDialog != null) {
                gui.chatDialog.localPrivateChatMap.get(Gui.chatReceiver).setBackground(new Color(83, 46, 91, 230));
                FontMetrics fm = gui.chatDialog.chatMessage.getFontMetrics(gui.chatDialog.chatMessage.getFont());
                gui.chatDialog.chatMessage.setBorder(new EmptyBorder(0, ImageUtil.resizeX(fm.stringWidth(Gui.chatReceiver) + 30), 0, 0));
            }
            gui.askChat();
        }

        if (e.getSource() == gui.waitingRoomInterface.maxSeatsDialog.playerButton_2 ||
                e.getSource() == gui.waitingRoomInterface.maxSeatsDialog.playerButton_3 ||
                e.getSource() == gui.waitingRoomInterface.maxSeatsDialog.playerButton_4) {
            playerNum = 0;

            //TODO: MAX SEATS = 2/3/4...Create a new match with 2/3/4 players...Go to waiting room
            if (e.getSource() == gui.waitingRoomInterface.maxSeatsDialog.playerButton_2) {
                playerNum = 2;
            } else if (e.getSource() == gui.waitingRoomInterface.maxSeatsDialog.playerButton_3) {
                playerNum = 3;
            } else if (e.getSource() == gui.waitingRoomInterface.maxSeatsDialog.playerButton_4) {
                playerNum = 4;
            }

            if (gui.commCtrl.changeMatchSeats(playerNum)) {
                SwingUtilities.invokeLater(() -> {
                    gui.waitingRoomInterface.reloadPlayerNumber(gui.waitingRoomInterface.minNum,String.valueOf(playerNum));
                    gui.waitingRoomInterface.revalidate();
                    gui.waitingRoomInterface.repaint();
                });

                gui.replyDEBUG("Number of Seats available changed" );
                gui.waitingRoomInterface.timer = new Timer(500, e1 -> {
                    //gui.waitingRoomInterface.dispose();
                    //System.out.println("WaitingRoomInterface Disposed");
                    gui.waitingRoomInterface.maxSeatsDialog.setVisible(false);
                    gui.waitingRoomInterface.timer.stop();
                });
                gui.waitingRoomInterface.timer.start();


            } else {
                gui.replyDEBUG("Operation failed: Only the admin are allowed to change settings");
            }

        }
    }

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

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == gui.waitingRoomInterface) {
            p.x = e.getX();
            p.y = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

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

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() == gui.waitingRoomInterface) {
            Point panelPoint = gui.waitingRoomInterface.getLocation();
            gui.waitingRoomInterface.setLocation(panelPoint.x + e.getX() - p.x, panelPoint.y + e.getY() - p.y);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

}
