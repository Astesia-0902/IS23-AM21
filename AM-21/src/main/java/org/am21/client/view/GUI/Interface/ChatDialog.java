package org.am21.client.view.GUI.Interface;

import org.am21.client.view.GUI.component.ButtonColorUI;
import org.am21.client.view.GUI.utils.FontUtil;
import org.am21.client.view.GUI.utils.IconUtil;
import org.am21.client.view.GUI.utils.ImageUtil;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;

public class ChatDialog extends JDialog {
    public JTextArea chatHistory;
    public JTextField chatMessage;
    public JButton sendButton;
    public JPanel chatPanel;
    public JLabel closeLabel;
    public ImageIcon closeIconSelect;
    public ImageIcon closeIcon;

    public ChatDialog() {
        setModal(true);
        setSize(ImageUtil.resizeX(350), ImageUtil.resizeY(400));

        closeIcon = IconUtil.getIcon("close_Purple");
        closeIconSelect = IconUtil.getIcon("close_Fuchsia");
        closeLabel = new JLabel(closeIcon);
        closeLabel.setBounds(ImageUtil.resizeX(320), ImageUtil.resizeY(6), ImageUtil.resizeX(25), ImageUtil.resizeY(25));
        add(closeLabel);


        chatHistory = new JTextArea(ImageUtil.resizeX(350), ImageUtil.resizeY(400));
        chatHistory.setEditable(false);
        chatHistory.setForeground(new Color(106, 2, 1));
        chatHistory.setBorder(new CompoundBorder(new MatteBorder(ImageUtil.resizeX(5),
                ImageUtil.resizeY(5), ImageUtil.resizeX(5), ImageUtil.resizeY(5),
                new Color(85, 35, 222, 230)), new EmptyBorder(ImageUtil.resizeX(5),
                ImageUtil.resizeY(5), ImageUtil.resizeX(5), ImageUtil.resizeY(5))));
        chatHistory.setFont(new Font("Serif", Font.BOLD, ImageUtil.resizeY(14)));

        chatMessage = new JTextField(30);
        chatMessage.setFont(new Font("Serif", Font.BOLD, ImageUtil.resizeY(14)));
        chatMessage.setForeground(new Color(106, 2, 1));

        sendButton = new JButton("Send");
        sendButton.setForeground(new Color(106, 2, 1));
        sendButton.setBackground(new Color(178, 173, 204, 230));
        sendButton.setUI(new ButtonColorUI(new Color(83, 46, 91, 230)));
        sendButton.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 250, 205),
                new Color(255, 250, 205), new Color(139, 69, 19), new Color(139, 69, 19)));
        sendButton.setFont(FontUtil.getFontByName("Leira-Lite-2").deriveFont(Font.PLAIN, ImageUtil.resizeY(18)));


        chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());
        chatPanel.add(chatMessage, BorderLayout.CENTER);
        chatPanel.add(sendButton, BorderLayout.EAST);
        chatPanel.setBorder(new MatteBorder(ImageUtil.resizeX(3), ImageUtil.resizeY(3),
                ImageUtil.resizeX(3), ImageUtil.resizeY(3), new Color(85, 35, 222, 255)));

        add(chatHistory, BorderLayout.CENTER);
        add(chatPanel, BorderLayout.SOUTH);

        OutputStream outputStream = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                chatHistory.append(String.valueOf((char) b));
                chatHistory.setCaretPosition(chatHistory.getDocument().getLength());
            }
        };

        getRootPane().setDefaultButton(sendButton);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        setBackground(new Color(247, 253, 252, 128));
        setOpacity(0.75f);

    }
}
