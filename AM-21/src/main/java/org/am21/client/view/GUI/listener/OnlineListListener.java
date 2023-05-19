package org.am21.client.view.GUI.listener;

import org.am21.client.view.GUI.Gui;
import org.am21.client.view.GUI.utils.ImageUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

public class OnlineListListener implements MouseListener, MouseMotionListener, ActionListener, ListSelectionListener {
    Gui gui;
    Point p = new Point();

    public OnlineListListener(Gui gui) {
        this.gui = gui;
        gui.onlineListDialog.onlineList.addListSelectionListener(this);
        gui.onlineListDialog.closeLabel.addMouseListener(this);
        gui.onlineListDialog.addMouseListener(this);
        gui.onlineListDialog.addMouseMotionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == gui.onlineListDialog.closeLabel) {
            gui.onlineListDialog.setVisible(false);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == gui.onlineListDialog) {
            p.x = e.getX();
            p.y = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == gui.onlineListDialog.closeLabel) {
            gui.onlineListDialog.closeLabel.setIcon(gui.onlineListDialog.closeIconSelect);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == gui.onlineListDialog.closeLabel) {
            gui.onlineListDialog.closeLabel.setIcon(gui.onlineListDialog.closeIcon);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() == gui.onlineListDialog) {
            Point panelPoint = gui.onlineListDialog.getLocation();
            gui.onlineListDialog.setLocation(panelPoint.x + e.getX() - p.x, panelPoint.y + e.getY() - p.y);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
//        if (gui.chatDialog != null) {
//            //gui.chatDialog.setVisible(false);
//            gui.chatDialog.dispose();
//        }
        String user = gui.onlineListDialog.onlineList.getSelectedValue().split("\\s*\\|\\s*")[0];
        if (!Gui.myChatMap.containsKey(user)) {
            Gui.NEW_PrivateChat = true;
            Gui.myChatMap.put(user, new JButton(user));
            if (gui.chatDialog != null) {
                gui.chatDialog.dispose();
            }
        }

        Gui.chatReceiver = user;
        if (gui.chatDialog!=null) {
            gui.chatDialog.localPrivateChatMap.keySet().forEach(player -> {
                if (player.equals(Gui.chatReceiver)) {
                    gui.chatDialog.localPrivateChatMap.get(player).setBackground(new Color(83, 46, 91, 230));
                    gui.chatDialog.localPrivateChatMap.get(player).setForeground(Color.WHITE);
                } else {
                    gui.chatDialog.localPrivateChatMap.get(player).setBackground(new Color(178, 173, 204, 230));
                    gui.chatDialog.localPrivateChatMap.get(player).setForeground(new Color(106, 2, 1));
                }
            });
            FontMetrics fm = gui.chatDialog.chatMessage.getFontMetrics(gui.chatDialog.chatMessage.getFont());
            gui.chatDialog.chatMessage.setBorder(new EmptyBorder(0, ImageUtil.resizeX(fm.stringWidth(Gui.chatReceiver) + 30), 0, 0));
        }
        gui.askChat();
    }
}
