package org.am21.view.cli;

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
            Thread.sleep(200);
        }
        input = bufferedReader.readLine();
        return input;
    }
}
