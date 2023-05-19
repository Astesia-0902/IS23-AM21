package org.am21.client.view.TUI;

import org.am21.client.view.ClientView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class InputReadTask implements Callable<String> {
    private final BufferedReader bufferedReader;

    public InputReadTask() {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public String call() throws Exception {
        String input;
        // wait until there is data to complete a readLine()
        while (!bufferedReader.ready()){
            //Thread.sleep(200);
            if(ClientView.NEED_TO_REFRESH){
                ClientView.setNeedToRefresh(false);
                return "";
            }
        }
        input = bufferedReader.readLine();
        return input;
    }
}
