package org.am21.client.view.GUI.listener;

import org.am21.client.view.GUI.Gui;
import org.am21.client.view.GUI.utils.ImageUtil;

import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class ChatListener implements MouseListener, MouseMotionListener, ActionListener, KeyListener {
    Gui gui;
    Point p = new Point();

    public ChatListener(Gui gui) {
        this.gui = gui;
        gui.chatDialog.sendButton.addMouseListener(this);
        gui.chatDialog.sendButton.addMouseMotionListener(this);
        gui.chatDialog.sendButton.addActionListener(this);
        gui.chatDialog.addMouseListener(this);
        gui.chatDialog.addMouseMotionListener(this);
        gui.chatDialog.closeLabel.addMouseListener(this);
        gui.chatDialog.playerPanel.addMouseListener(this);
//        for (String user : Gui.chatPlayer.keySet()) {
//            Gui.chatPlayer.get(user).addActionListener(this);
//            Gui.chatPlayer.get(user).addMouseListener(this);
//        }
        gui.chatDialog.privateChat.keySet().forEach(user -> {
            gui.chatDialog.privateChat.get(user).addActionListener(this);
            gui.chatDialog.privateChat.get(user).addMouseListener(this);
        });
//        gui.chatDialog.privateChat.forEach((user, button) -> {
//            button.addActionListener(e -> {
//                Gui.chatUser = user;
//                gui.chatDialog.chatHistory = Gui.chatHistory.get(user);
//                gui.chatDialog.scrollPane.setViewportView(gui.chatDialog.chatHistory);
//                gui.askChat();
//            });
//            button.addMouseListener(this);
//        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gui.chatDialog.sendButton) {
            String message = gui.chatDialog.chatMessage.getText();
            gui.handleChatMessage(message,true);
            gui.chatDialog.chatMessage.setText("");     //Clear input box
        }
        gui.chatDialog.privateChat.keySet().forEach(user -> {
            if (e.getSource() == gui.chatDialog.privateChat.get(user)) {
                Gui.chatUser = user;
                gui.chatDialog.chatHistory = Gui.chatHistory.get(user);
                gui.chatDialog.scrollPane.setViewportView(gui.chatDialog.chatHistory);
                gui.askChat();
            }
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            gui.chatDialog.sendButton.doClick();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == gui.chatDialog.closeLabel) {
            gui.chatDialog.setVisible(false);
        }
        // when click on gui.chatDialog, sendButton get focus, so you can press 'Enter' to send a message
        if (e.getSource() == gui.chatDialog) {
            gui.chatDialog.sendButton.requestFocus();
        }
        gui.chatDialog.privateChat.keySet().forEach(user -> {
            if (e.getSource() == gui.chatDialog.privateChat.get(user)) {
                gui.chatDialog.privateChat.get(user).setBackground(new Color(83, 46, 91, 230));
            } else {
                gui.chatDialog.privateChat.get(user).setBackground(new Color(178, 173, 204, 230));
            }
            if (gui.chatDialog != null) {
                FontMetrics fm = gui.chatDialog.chatMessage.getFontMetrics(gui.chatDialog.chatMessage.getFont());
                gui.chatDialog.chatMessage.setBorder(new EmptyBorder
                        (0, ImageUtil.resizeX(fm.stringWidth(Gui.chatUser) + 30), 0, 0));
            }
        });
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == gui.chatDialog) {
            p.x = e.getX();
            p.y = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == gui.chatDialog.closeLabel) {
            gui.chatDialog.closeLabel.setIcon(gui.chatDialog.closeIconSelect);
        }
        if (e.getSource() == gui.chatDialog.sendButton) {
            gui.chatDialog.sendButton.setBackground(new Color(243, 214, 253));
        }
//        gui.chatDialog.privateChat.keySet().forEach(user -> {
//            if (e.getSource() == gui.chatDialog.privateChat.get(user)) {
//                gui.chatDialog.privateChat.get(user).setBackground(new Color(243, 214, 253));
//            }
//        });
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == gui.chatDialog.closeLabel) {
            gui.chatDialog.closeLabel.setIcon(gui.chatDialog.closeIcon);
        }
        if (e.getSource() == gui.chatDialog.sendButton) {
            gui.chatDialog.sendButton.setBackground(new Color(178, 173, 204, 230));
        }
//        gui.chatDialog.privateChat.keySet().forEach(user -> {
//            if (e.getSource() == gui.chatDialog.privateChat.get(user)) {
//                gui.chatDialog.privateChat.get(user).setBackground(new Color(178, 173, 204, 230));
//            }
//        });
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() == gui.chatDialog) {
            Point panelPoint = gui.chatDialog.getLocation();
            gui.chatDialog.setLocation(panelPoint.x + e.getX() - p.x, panelPoint.y + e.getY() - p.y);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
