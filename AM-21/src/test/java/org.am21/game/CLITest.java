package org.am21.game;

import org.am21.client.ClientApp;
import org.am21.client.view.cli.Cli;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.rmi.RemoteException;

public class CLITest {
    ClientApp app;
    String[] args=null;
    Cli c;
    InputStream stdin = null;



    @BeforeEach
    void setUp() throws RemoteException {


    }

    @AfterEach
    void tearDown(){
        app=null;
        c=null;
    }

    @Test
    void test(){

    }
}
