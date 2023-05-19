package org.am21.networkRMI;

import org.am21.controller.Server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class Welcome extends UnicastRemoteObject implements Lobby {
    public static String serverAddress = "localhost";
    public static String serverPort = "8807";

    public Welcome() throws RemoteException {
    }
    @Override
    public HashMap<String, String> connect() throws RemoteException, MalformedURLException, AlreadyBoundException {
            Server.done();
            HashMap<String,String> infoMap=new HashMap<>();
            infoMap.put("root", String.valueOf(Server.number));
            infoMap.put("address",serverAddress);
            infoMap.put("port",serverPort);
            return infoMap;


    }
}
