package org.am21.networkRMI;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.RMISocketFactory;

public class MyRMISocketFactory extends RMISocketFactory {
    private String serverAddress;
    private int port;

    public MyRMISocketFactory(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }

    /**
     * Creates a client socket connected to the specified host and port.
     *
     * @param host the host name
     * @param port the port number
     * @return a socket connected to the specified host and port.
     * @throws IOException if an I/O error occurs during socket creation
     * @since JDK1.1
     */
    @Override
    public Socket createSocket(String host, int port) throws IOException {
        InetAddress address = InetAddress.getByName(serverAddress);
        int portToUse = this.port;
        Socket ans = new Socket(address, portToUse);
        System.out.println("RMI Socket Factory > Client connected to " + ans.getRemoteSocketAddress() + ":" + ans.getInetAddress());
        return ans;
    }

    /**
     * Create a server socket on the specified port (port 0 indicates
     * an anonymous port).
     *
     * @param port the port number
     * @return the server socket on the specified port
     * @throws IOException if an I/O error occurs during server socket
     *                     creation
     * @since JDK1.1
     */
    @Override
    public ServerSocket createServerSocket(int port) throws IOException {
        return new ServerSocket(port, 0, InetAddress.getByName(serverAddress));
    }
}
