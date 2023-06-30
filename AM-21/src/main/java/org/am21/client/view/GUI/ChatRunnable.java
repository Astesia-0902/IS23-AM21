package org.am21.client.view.GUI;

import org.am21.client.view.GUI.Interface.ChatDialog;
import org.am21.client.view.GUI.utils.PixelUtil;


/**
 * ChatRunnable is a class that implements Runnable
 * and is used to create a new chat window
 */
public class ChatRunnable implements Runnable {
    public Gui gui;

    /**
     * Constructor
     *
     * @param gui the gui
     */
    public ChatRunnable(Gui gui) {
        this.gui = gui;

    }

    /**
     * Thread run method
     */
    @Override
    public void run() {
        if (Gui.newChatWindow) {
            if (gui.chatDialog != null && !gui.chatDialog.isVisible()) {
                //chatDialog exists already
                if (gui.livingRoomInterface != null && gui.chatDialog != null && gui.onlineListDialog != null) {
                    gui.chatDialog.setLocation(PixelUtil.commonX_1, PixelUtil.cWindowY);
                    gui.chatDialog.setSize(PixelUtil.cWindowW, PixelUtil.cWindowH);
                    gui.onlineListDialog.setLocation(PixelUtil.commonX_1, PixelUtil.cPlayerWindowY);
                }
                gui.chatDialog.setVisible(true);
            } else {
                synchronized (gui.chatLock) {
                    gui.chatDialog = new ChatDialog(gui.frame);
                }
                if (gui.livingRoomInterface != null && gui.chatDialog != null && gui.onlineListDialog != null) {
                    gui.chatDialog.setLocation(PixelUtil.commonX_1, PixelUtil.cWindowY);
                    gui.chatDialog.setSize(PixelUtil.cWindowW, PixelUtil.cWindowH);
                    gui.onlineListDialog.setLocation(PixelUtil.commonX_1, PixelUtil.cPlayerWindowY);
                }
                System.out.println("Chat Dialog created (visible)");
                gui.setNewChatWindow(true);
                gui.chatDialog.setVisible(true);
            }
        } else {
            synchronized (gui.chatLock) {
                gui.chatDialog = new ChatDialog(gui.frame);
                if (gui.livingRoomInterface != null && gui.chatDialog != null && gui.onlineListDialog != null) {
                    gui.chatDialog.setLocation(PixelUtil.commonX_1, PixelUtil.cWindowY);
                    gui.chatDialog.setSize(PixelUtil.cWindowW, PixelUtil.cWindowH);
                    gui.onlineListDialog.setLocation(PixelUtil.commonX_1, PixelUtil.cPlayerWindowY);
                }
            }
            gui.chatDialog.setVisible(false);
            System.out.println("Chat Dialog created (not visible)");
        }
        System.out.println("Closed chat runnable");

    }

}
