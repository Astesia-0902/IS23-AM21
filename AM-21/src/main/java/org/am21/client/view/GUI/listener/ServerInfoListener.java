package org.am21.client.view.GUI.listener;

import org.am21.client.ClientController;
import org.am21.client.SocketClient;
import org.am21.client.view.GUI.Gui;
import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.networkRMI.IClientCallBack;
import org.am21.networkRMI.IClientInput;
import org.am21.networkRMI.Lobby;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class ServerInfoListener implements MouseListener, MouseMotionListener, ActionListener, KeyListener, DocumentListener {
    Gui gui;
    Point p = new Point();

    public ServerInfoListener(Gui gui) {
        this.gui = gui;
        gui.serverInfoInterface.addMouseMotionListener(this);
        gui.serverInfoInterface.addMouseListener(this);
        gui.serverInfoInterface.closeLabel.addMouseListener(this);
        gui.serverInfoInterface.minusLabel.addMouseListener(this);
        gui.serverInfoInterface.returnLabel.addMouseListener(this);
        gui.serverInfoInterface.portField.addActionListener(this);
        gui.serverInfoInterface.portField.addKeyListener(this);
        gui.serverInfoInterface.portField.getDocument().addDocumentListener(this);
        gui.serverInfoInterface.addressField.addActionListener(this);
        gui.serverInfoInterface.addressField.addKeyListener(this);
        gui.serverInfoInterface.addressField.getDocument().addDocumentListener(this);
        if (ClientController.isRMI) {
            gui.serverInfoInterface.ipField.addActionListener(this);
            gui.serverInfoInterface.ipField.addKeyListener(this);
            gui.serverInfoInterface.ipField.getDocument().addDocumentListener(this);
        }
        gui.serverInfoInterface.confirmButton.addActionListener(this);
        gui.serverInfoInterface.confirmButton.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // ASK SERVER INFO
        if (e.getSource() == gui.serverInfoInterface.confirmButton) {
            String address = gui.serverInfoInterface.addressField.getText().trim();
            String port = gui.serverInfoInterface.portField.getText().trim();
            String ip;

            if (address.isEmpty() || port.isEmpty()) {
                gui.serverInfoInterface.addressField.setBorder(new CompoundBorder(new MatteBorder
                        (ImageUtil.resizeY(3), ImageUtil.resizeX(3), ImageUtil.resizeY(3),
                                ImageUtil.resizeX(3), new Color(178, 34, 34)),
                        new EmptyBorder(0, ImageUtil.resizeX(50), 0, 0)));
                gui.serverInfoInterface.portField.setBorder(new CompoundBorder(new MatteBorder
                        (ImageUtil.resizeY(3), ImageUtil.resizeX(3), ImageUtil.resizeY(3),
                                ImageUtil.resizeX(3), new Color(178, 34, 34)),
                        new EmptyBorder(0, ImageUtil.resizeX(50), 0, 0)));

                if (ClientController.isRMI) {
                    ip = gui.serverInfoInterface.ipField.getText().trim();
                    if (ip.isEmpty()) {
                        gui.serverInfoInterface.ipField.setBorder(new CompoundBorder(new MatteBorder
                                (ImageUtil.resizeY(3), ImageUtil.resizeX(3), ImageUtil.resizeY(3),
                                        ImageUtil.resizeX(3), new Color(178, 34, 34)),
                                new EmptyBorder(0, ImageUtil.resizeX(50), 0, 0)));
                    }
                }
            } else {
                gui.serverInfoInterface.dispose();
                if (ClientController.isRMI) {
                    // Determine my address
                    String clientBind = "";
                    ip = gui.serverInfoInterface.ipField.getText().trim();
                    InetAddress localHost = null;
                    try {
                        localHost = InetAddress.getLocalHost();
                    } catch (UnknownHostException e2) {
                        throw new RuntimeException(e2);
                    }
                    int freePort;
                    while (true) {
                        freePort = findFreePort();
                        if (freePort != -1) {
                            System.out.println("Free port found: " + freePort);
                            break;
                        } else {
                            System.out.println("Free port not found");
                        }
                    }
                    //ip = "192.168.20.23";
                    System.setProperty("java.rmi.server.hostname", ip);
                    System.out.println("Your ip address is : " + ip);
                    try {
                        Registry registry = LocateRegistry.createRegistry(freePort);
                        UnicastRemoteObject.unexportObject(gui.clientCallBack, true);
                        IClientCallBack callbackStub = (IClientCallBack) UnicastRemoteObject.exportObject(gui.clientCallBack, freePort);
                        registry.bind("Callback", callbackStub);
                        clientBind = "rmi://" + ip + ":" + freePort + "/Callback";

                    } catch (AlreadyBoundException | RemoteException e3) {
                        throw new RuntimeException(e3);
                    }
                    try {
                        Lobby lobby = (Lobby) Naming.lookup("rmi://" +address + ":" + port + "/Welcome");
                        HashMap<String, String> serverInfo;
                        try {
                            serverInfo = lobby.connect();
                            Gui.root = serverInfo.get("root");
                        } catch (AlreadyBoundException ex) {
                            throw new RuntimeException(ex);
                        }
                        gui.iClientInputHandler = (IClientInput) Naming.lookup("rmi://" + serverInfo.get("address")
                                                                               + ":" + serverInfo.get("port") + "/"
                                                                               + Gui.root);

                        ClientController.iClientInputHandler = gui.iClientInputHandler;
                        gui.commCtrl.registerCallBack(clientBind);
                        gui.askLogin();
                    } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                        gui.timeLimitedNotification("No server found", 1000);
                        gui.askServerInfoRMI();
                    }
                } else {
                    SocketClient socket = new SocketClient();
                    SocketClient.serverName = address;
                    SocketClient.serverPort = Integer.parseInt(port);
                    SocketClient.gui = gui;

                    if (!SocketClient.connectToServer()) {
                        //Server not found
                        gui.timeLimitedNotification("No server found", 1000);
                        gui.askServerInfoSocket();
                    } else {
                        socket.start();
                        try {
                            gui.askLogin();
                        } catch (RemoteException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                }

            }

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == gui.serverInfoInterface.closeLabel) {
            gui.frame.dispose();                         //close window
        }
        if (e.getSource() == gui.serverInfoInterface.minusLabel) {
            gui.frame.setExtendedState(Frame.ICONIFIED); // minimize window
        }
        if (e.getSource() == gui.serverInfoInterface.returnLabel) {      // return to select communication
            try {
                gui.serverInfoInterface.setVisible(false);
                gui.init();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == gui.serverInfoInterface) {
            gui.serverInfoInterface.confirmButton.requestFocus();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == gui.serverInfoInterface) {
            p.x = e.getX();
            p.y = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == gui.serverInfoInterface.closeLabel) {
            gui.serverInfoInterface.closeLabel.setBackground(new Color(222, 184, 135));
            gui.serverInfoInterface.closeLabel.setOpaque(true);

        }
        if (e.getSource() == gui.serverInfoInterface.minusLabel) {
            gui.serverInfoInterface.minusLabel.setBackground(new Color(222, 184, 135));
            gui.serverInfoInterface.minusLabel.setOpaque(true);
        }
        if (e.getSource() == gui.serverInfoInterface.returnLabel) {
            gui.serverInfoInterface.returnLabel.setIcon(gui.serverInfoInterface.returnIcon);
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == gui.serverInfoInterface.closeLabel) {
            gui.serverInfoInterface.closeLabel.setBackground(null);
            gui.serverInfoInterface.closeLabel.setOpaque(false);
        }
        if (e.getSource() == gui.serverInfoInterface.minusLabel) {
            gui.serverInfoInterface.minusLabel.setBackground(null);
            gui.serverInfoInterface.minusLabel.setOpaque(false);
        }
        if (e.getSource() == gui.serverInfoInterface.returnLabel) {
            gui.serverInfoInterface.returnLabel.setIcon(gui.serverInfoInterface.returnIconColor);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() == gui.serverInfoInterface) {
            Point panelPoint = gui.serverInfoInterface.getLocation();
            gui.serverInfoInterface.setLocation(panelPoint.x + e.getX() - p.x, panelPoint.y + e.getY() - p.y);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            gui.serverInfoInterface.confirmButton.doClick();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        updateButtonState();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        updateButtonState();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        updateButtonState();
    }

    private void updateButtonState() {
        gui.serverInfoInterface.confirmButton.setEnabled(
                !gui.serverInfoInterface.addressField.getText().trim().isEmpty() &&
                !gui.serverInfoInterface.portField.getText().trim().isEmpty());
        if (ClientController.isRMI) {
            gui.serverInfoInterface.confirmButton.setEnabled(!gui.serverInfoInterface.ipField.getText().trim().isEmpty());
        }
    }

    public static int findFreePort() {
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(0);
            return socket.getLocalPort();
        } catch (IOException e) {
            return -1;
        } finally {

            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println(org.am21.client.view.TUI.Color.RED + "Socket not closed" + org.am21.client.view.TUI.Color.RESET);
                }
            }
        }
    }
}
