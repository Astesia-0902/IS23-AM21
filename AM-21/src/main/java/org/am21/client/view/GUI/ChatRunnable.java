package org.am21.client.view.GUI;

import org.am21.client.view.GUI.Interface.ChatDialog;

public class ChatRunnable implements Runnable{
    public Gui gui;
    public ChatRunnable(Gui gui){
        this.gui=gui;

    }



    @Override
    public void run() {
        if(Gui.NEW_CHAT_WINDOW){
            if(gui.chatDialog!=null && !gui.chatDialog.isVisible()){
                //chatDialog exists already
                gui.chatDialog.setVisible(true);
            }else {
                synchronized (gui.chatLock) {
                    gui.chatDialog = new ChatDialog(gui.frame, 500, 500);
                }
                System.out.println("Chat Dialog created (visible)");
                Gui.NEW_CHAT_WINDOW = false;
                gui.chatDialog.setVisible(true);
            }
        }else{
            synchronized (gui.chatLock) {
                gui.chatDialog = new ChatDialog(gui.frame, 500, 500);
            }
            gui.chatDialog.setVisible(false);
            System.out.println("Chat Dialog created (not visible)");
        }
        System.out.println("Closed chat runnable");

    }

}
