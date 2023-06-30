package org.am21.client.view.GUI.listener;

import org.am21.client.view.GUI.Gui;
import org.am21.client.view.GUI.utils.ImageUtil;

import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;


public class ChatListener implements MouseListener, MouseMotionListener, ActionListener, KeyListener {
    public Gui gui;
    public Point p = new Point();
    public static ChatListener instance;

    /**
     * Constructor
     *
     * @param gui is the GUI
     */
    public ChatListener(Gui gui) {
        instance = this;
        this.gui = gui;
        gui.chatDialog.sendButton.addMouseListener(this);
        gui.chatDialog.sendButton.addMouseMotionListener(this);
        gui.chatDialog.sendButton.addActionListener(this);
        gui.chatDialog.addMouseListener(this);
        gui.chatDialog.addMouseMotionListener(this);
        gui.chatDialog.closeLabel.addMouseListener(this);
        gui.chatDialog.playerPanel.addMouseListener(this);

        gui.chatDialog.localChatMap.keySet().forEach(user -> {
            gui.chatDialog.localChatMap.get(user).addActionListener(this);
            gui.chatDialog.localChatMap.get(user).addMouseListener(this);
        });

    }

    /**
     * Action performed method
     *
     * @param e is the mouse event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gui.chatDialog.sendButton) {
            String message = gui.chatDialog.chatMessageInput.getText();

            if (Gui.chatReceiver.equals("#All") && gui.commCtrl.sendPublicMessage(message, true)) {
                gui.chatDialog.currentChatHistory = Gui.publicChatHistory;
                gui.chatDialog.chatMessageInput.setText("");     //Clear input box
                gui.setAskChat(true);
            } else if (gui.commCtrl.sendPrivateMessage(message, Gui.chatReceiver, true)) {

                gui.chatDialog.currentChatHistory = Gui.privateChatHistoryMap.get(Gui.chatReceiver);
                gui.chatDialog.chatMessageInput.setText("");     //Clear input box

                gui.setAskChat(true);
            } else {
                gui.timeLimitedNotification("Message not sent", 5000);
                gui.chatDialog.chatMessageInput.setText("");     //Clear input box
            }
            gui.chatDialog.chatMessageInput.setText("");     //Clear input box
        }

        gui.chatDialog.localChatMap.keySet().forEach(user -> {
            if (e.getSource() == gui.chatDialog.localChatMap.get(user)) {
                gui.setNewChatWindow(true);
                Gui.chatReceiver = user;
                if (gui.chatDialog != null) {
                    FontMetrics fm = gui.chatDialog.chatMessageInput.getFontMetrics(gui.chatDialog.chatMessageInput.getFont());
                    gui.chatDialog.chatMessageInput.setBorder(new EmptyBorder(0, ImageUtil.resizeX(fm.stringWidth(Gui.chatReceiver) + 30), 0, 0));
                }
                gui.setAskChat(true);
                gui.timeLimitedNotification("Clicked on " + Gui.chatReceiver, 1000);

            }
        });

    }

    /**
     * Key pressed event
     *
     * @param e is the mouse event
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Key pressed event
     *
     * @param e is the mouse event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            gui.chatDialog.sendButton.doClick();
        }
    }

    /**
     * Key released event
     *
     * @param e is the mouse event
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * Mouse clicked event
     *
     * @param e is the mouse event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == gui.chatDialog.closeLabel) {
            gui.chatDialog.setVisible(false);
        }
        // when click on gui.chatDialog, sendButton get focus, so you can press 'Enter' to send a message
        if (e.getSource() == gui.chatDialog) {
            gui.chatDialog.sendButton.requestFocus();
        }
        gui.chatDialog.localChatMap.keySet().forEach(user -> {
            if (e.getSource() == gui.chatDialog.localChatMap.get(user)) {
                gui.chatDialog.localChatMap.get(user).setBackground(new Color(83, 46, 91, 230));
                gui.chatDialog.localChatMap.get(user).setForeground(Color.WHITE);

            } else {
                gui.chatDialog.localChatMap.get(user).setBackground(new Color(178, 173, 204, 230));
                gui.chatDialog.localChatMap.get(user).setForeground(new Color(106, 2, 1));

            }
        });
    }

    /**
     * Mouse pressed event
     *
     * @param e is the mouse event
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == gui.chatDialog) {
            p.x = e.getX();
            p.y = e.getY();
        }
    }

    /**
     * Mouse released event
     *
     * @param e is the mouse event
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Mouse entered event
     *
     * @param e is the mouse event
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == gui.chatDialog.closeLabel) {
            gui.chatDialog.closeLabel.setIcon(gui.chatDialog.closeIconSelect);
        }
        if (e.getSource() == gui.chatDialog.sendButton) {
            gui.chatDialog.sendButton.setBackground(new Color(243, 214, 253));
        }
    }

    /**
     * Mouse exited event
     *
     * @param e is the mouse event
     */
    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == gui.chatDialog.closeLabel) {
            gui.chatDialog.closeLabel.setIcon(gui.chatDialog.closeIcon);
        }
        if (e.getSource() == gui.chatDialog.sendButton) {
            gui.chatDialog.sendButton.setBackground(new Color(178, 173, 204, 230));
        }
    }

    /**
     * Mouse dragged event
     *
     * @param e is the mouse event
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() == gui.chatDialog) {
            Point panelPoint = gui.chatDialog.getLocation();
            gui.chatDialog.setLocation(panelPoint.x + e.getX() - p.x, panelPoint.y + e.getY() - p.y);
        }
    }

    /**
     * Mouse moved event
     *
     * @param e is the mouse event
     */
    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
