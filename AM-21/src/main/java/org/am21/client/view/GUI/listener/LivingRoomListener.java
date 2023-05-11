package org.am21.client.view.GUI.listener;

import org.am21.client.view.GUI.Gui;

import java.awt.*;
import java.awt.event.*;

public class LivingRoomListener implements MouseListener, MouseMotionListener, ActionListener,KeyListener {
    Gui gui;
    Point p = new Point();
    public LivingRoomListener(Gui gui){
        this.gui = gui;
       // gui.livingRoomInterface.addMouseMotionListener(this);
        //gui.livingRoomInterface.addMouseListener(this);
        gui.livingRoomInterface.livingRoomPanel.livingRoomMenuButton.addMouseListener(this);
        gui.livingRoomInterface.livingRoomPanel.livingRoomMenuButton.addActionListener(this);
        gui.livingRoomInterface.livingRoomPanel.insertButton.addMouseListener(this);
        gui.livingRoomInterface.livingRoomPanel.insertButton.addActionListener(this);
        gui.livingRoomInterface.livingRoomPanel.clearButton.addMouseListener(this);
        gui.livingRoomInterface.livingRoomPanel.clearButton.addActionListener(this);
        gui.livingRoomInterface.livingRoomPanel.messageField.addKeyListener(this);
        gui.livingRoomInterface.livingRoomPanel.messageField.addActionListener(this);
        gui.livingRoomInterface.livingRoomPanel.sendButton.addKeyListener(this);
        gui.livingRoomInterface.livingRoomPanel.sendButton.addMouseListener(this);
        gui.livingRoomInterface.livingRoomPanel.sendButton.addActionListener(this);


    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==gui.livingRoomInterface.livingRoomPanel.livingRoomMenuButton){
            //TODO: show menu option interface
        }

        if(e.getSource()==gui.livingRoomInterface.livingRoomPanel.insertButton){
            //TODO: insert action -->open myHandInterface()
            try {
                gui.askInsertion();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        if(e.getSource()==gui.livingRoomInterface.livingRoomPanel.clearButton){
            //TODO: clear action --> all card in the handBoard will removed,t he border of item will change in the color red

            try {
                gui.askDeselection();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    public void mouseClicked(MouseEvent e) {


    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {
    
    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {

    }
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            gui.livingRoomInterface.livingRoomPanel.sendButton.doClick();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
