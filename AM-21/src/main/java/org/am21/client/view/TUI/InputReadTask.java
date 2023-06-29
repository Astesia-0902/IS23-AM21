package org.am21.client.view.TUI;

import org.am21.client.view.ClientView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

/**
 * This class is used to read input from the user
 */
public class InputReadTask implements Callable<String> {
    private final BufferedReader bufferedReader;

    /**
     * Constructor
     */
    public InputReadTask() {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public String call() throws Exception {
        String input;
        // wait until there is data to complete a readLine()
        while (!bufferedReader.ready()){
            //Thread.sleep(200);
                if (ClientView.needToRefresh) {
                    ClientView.setNeedToRefresh(false);
                    return "";
                }
        }
        input = bufferedReader.readLine();
        return input;
    }
}
