package org.am21.client.view.GUI.Interface;

import org.am21.client.ClientController;
import org.am21.client.SocketClient;
import org.am21.client.view.GUI.component.BackGroundPanel;
import org.am21.client.view.GUI.utils.ButtonUtil;
import org.am21.client.view.GUI.utils.IconUtil;
import org.am21.client.view.GUI.utils.ImageUtil;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class ServerInfoInterface extends JDialog {
    public static final int WIDTH = 300;
    public static int HEIGHT = 250;

    public JTextField addressField;
    public JTextField portField;
    public JTextField ipField;
    public JButton confirmButton;
    public JLabel minusLabel;
    public JLabel closeLabel;
    public JLabel returnLabel;

    public ImageIcon returnIcon;
    public ImageIcon returnIconColor;

    private int addressY;
    private int portY;
    private int confirmY;

    /**
     * Constructor
     *
     * @param frame
     */
    public ServerInfoInterface(JFrame frame) {
        super(frame);
        String defaultAddress;
        String defaultPort;
        if (ClientController.isRMI) {
            defaultAddress = "localhost";
            defaultPort = "1234";

            HEIGHT = 320;
            addressY = 40;
            portY = 170;
            confirmY = 235;

            // IP Field
            ImageIcon ipIcon = IconUtil.getIcon("ip");
            ipField = new JTextField(15) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    ipIcon.paintIcon(ipField, g, ImageUtil.resizeX(5), ImageUtil.resizeY(5));
                }
            };
            ipField.setText(defaultAddress);
            ipField.setForeground(new Color(255, 255, 240));
            ipField.setFont(new Font("Serif", Font.BOLD, ImageUtil.resizeY(23)));
            ipField.setBackground(new Color(222, 184, 135));
            ipField.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 250, 205),
                    new Color(255, 250, 205), new Color(139, 69, 19), new Color(139, 69, 19)),
                    new EmptyBorder(0, ImageUtil.resizeX(50), 0, 0)));
            ipField.setBounds(ImageUtil.resizeX(25), ImageUtil.resizeY(105),
                    ImageUtil.resizeX(250), ImageUtil.resizeY(46));
            getContentPane().add(ipField);

        } else {
            defaultAddress = SocketClient.defaultServerName;
            defaultPort = String.valueOf(SocketClient.defaultServerPort);

            HEIGHT = 250;
            addressY = 40;
            portY = 105;
            confirmY = 170;
        }

        frame.setTitle("MyShelfie - Server Info");

        HashMap<BufferedImage, int[]> background = new HashMap<>();
        // Background
        background.put(IconUtil.getBufferedImage("serverInfoBG"),
                new int[]{0, 0, ImageUtil.resizeX(WIDTH), ImageUtil.resizeY(HEIGHT)});
        // Icon
        minusLabel = new JLabel(IconUtil.getIcon("minus"));
        minusLabel.setBounds(ImageUtil.resizeX(241), ImageUtil.resizeY(6),
                ImageUtil.resizeX(25), ImageUtil.resizeY(25));
        getContentPane().add(minusLabel);

        closeLabel = new JLabel(IconUtil.getIcon("close"));
        closeLabel.setBounds(ImageUtil.resizeX(271), ImageUtil.resizeY(6),
                ImageUtil.resizeX(25), ImageUtil.resizeY(25));
        getContentPane().add(closeLabel);

        returnIcon = IconUtil.getIcon("return");
        returnIconColor = IconUtil.getIcon("returnSelected");
        returnLabel = new JLabel(returnIconColor);
        returnLabel.setBounds(ImageUtil.resizeX(15), ImageUtil.resizeY(7),
                ImageUtil.resizeX(25), ImageUtil.resizeY(25));
        getContentPane().add(returnLabel);

        BackGroundPanel backGroundPanel = new BackGroundPanel(background);

        // Login Button
        confirmButton = ButtonUtil.getButton("Confirm");
        confirmButton.setBounds(ImageUtil.resizeX(25), ImageUtil.resizeY(confirmY),
                ImageUtil.resizeX(250), ImageUtil.resizeY(46));
        getContentPane().add(confirmButton);

        // Address Field
        ImageIcon addressIcon = IconUtil.getIcon("address");
        addressField = new JTextField(15) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                addressIcon.paintIcon(addressField, g, ImageUtil.resizeX(5), ImageUtil.resizeY(5));
            }
        };
        addressField.setText(defaultAddress);
        addressField.setForeground(new Color(255, 255, 240));
        addressField.setFont(new Font("Serif", Font.BOLD, ImageUtil.resizeY(23)));
        addressField.setBackground(new Color(222, 184, 135));
        addressField.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 250, 205),
                new Color(255, 250, 205), new Color(139, 69, 19), new Color(139, 69, 19)),
                new EmptyBorder(0, ImageUtil.resizeX(50), 0, 0)));
        addressField.setBounds(ImageUtil.resizeX(25), ImageUtil.resizeY(addressY),
                ImageUtil.resizeX(250), ImageUtil.resizeY(46));
        getContentPane().add(addressField);

        // PortField
        ImageIcon portIcon = IconUtil.getIcon("port");
        portField = new JTextField(15) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                portIcon.paintIcon(portField, g, ImageUtil.resizeX(5), ImageUtil.resizeY(5));
            }
        };
        //portField.setText("8807");
        portField.setText(defaultPort);

        portField.setForeground(new Color(255, 255, 240));
        portField.setFont(new Font("Serif", Font.BOLD, ImageUtil.resizeY(23)));
        portField.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 250, 205),
                new Color(255, 250, 205), new Color(139, 69, 19), new Color(139, 69, 19)),
                new EmptyBorder(0, ImageUtil.resizeX(50), 0, 0)));
        portField.setBackground(new Color(222, 184, 135));
        portField.setBounds(ImageUtil.resizeX(25), ImageUtil.resizeY(portY),
                ImageUtil.resizeX(250), ImageUtil.resizeY(46));
        getContentPane().add(portField);

        backGroundPanel.setBorder(new MatteBorder(ImageUtil.resizeY(5), ImageUtil.resizeX(5),
                ImageUtil.resizeY(5), ImageUtil.resizeX(5), new Color(139, 69, 19)));
        add(backGroundPanel);

        setBounds(0, 0, ImageUtil.resizeX(WIDTH), ImageUtil.resizeY(HEIGHT));
        setUndecorated(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
