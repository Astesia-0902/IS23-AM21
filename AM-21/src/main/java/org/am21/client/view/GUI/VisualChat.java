package org.am21.client.view.GUI;

import java.util.concurrent.Callable;

public class VisualChat implements Callable<String> {

    public static boolean CHAT_UPDATE = false;
    @Override
    public String call() throws Exception {
        while(!CHAT_UPDATE){

            Thread.sleep(200);


        }

        return "";
    }
}
