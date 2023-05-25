package org.am21.client.view.GUI;

public class DebugNotification implements Runnable{
    public String debugMessage;

    public DebugNotification(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    @Override
    public void run() {
        System.out.println("GUI > "+debugMessage);

    }
}
