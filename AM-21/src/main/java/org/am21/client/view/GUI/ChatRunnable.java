package org.am21.client.view.GUI;

import org.am21.client.view.GUI.Interface.ChatDialog;

public class ChatRunnable implements Runnable{
    public Gui gui;
    public ChatRunnable(Gui gui){
        this.gui=gui;

    }

    @Override
    public void run() {
        if(Gui.newChatWindow){
            if(gui.chatDialog!=null && !gui.chatDialog.isVisible()){
                //chatDialog exists already
                gui.chatDialog.setVisible(true);
            }else {
                synchronized (gui.chatLock) {
                    gui.chatDialog = new ChatDialog(gui.frame);
                }
                System.out.println("Chat Dialog created (visible)");
                gui.setNewChatWindow(true);
                gui.chatDialog.setVisible(true);
            }
        }else{
            synchronized (gui.chatLock) {
                gui.chatDialog = new ChatDialog(gui.frame);
            }
            gui.chatDialog.setVisible(false);
            System.out.println("Chat Dialog created (not visible)");
        }
        System.out.println("Closed chat runnable");

    }

}
