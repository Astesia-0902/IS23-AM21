package org.am21.client.view.GUI.Interface;

import org.am21.client.view.GUI.Gui;
import org.am21.client.view.GUI.component.ButtonColorUI;
import org.am21.client.view.GUI.component.ScrollBarUI;
import org.am21.client.view.GUI.utils.FontUtil;
import org.am21.client.view.GUI.utils.IconUtil;
import org.am21.client.view.GUI.utils.ImageUtil;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class ChatDialog extends JDialog {
    public JTextArea chatHistory;
    public JTextField chatMessage;
    public JButton sendButton;
    public JPanel chatPanel;
    public JPanel topPanel;
    public JPanel playerPanel;
    public JLabel closeLabel;
    public JScrollPane scrollPane;
    public ImageIcon closeIconSelect;
    public ImageIcon closeIcon;
    public HashMap<String, JButton> privateChat;

    public ChatDialog(JFrame frame) {
        super(frame);
        //setModal(true);       // If you do not close this window you will not be able to move the following windows
        setSize(ImageUtil.resizeX(500), ImageUtil.resizeY(500));

        closeIcon = IconUtil.getIcon("close_Purple");
        closeIconSelect = IconUtil.getIcon("close_Fuchsia");
        closeLabel = new JLabel(closeIcon);
        closeLabel.setBounds(ImageUtil.resizeX(320), ImageUtil.resizeY(6), ImageUtil.resizeX(25), ImageUtil.resizeY(25));

        JLabel chatRoom = new JLabel("Chat Room");
        chatRoom.setBorder(null);
        chatRoom.setBounds(ImageUtil.resizeX(181), ImageUtil.resizeY(195),
                ImageUtil.resizeX(356), ImageUtil.resizeY(108));
        chatRoom.setForeground(new Color(245, 238, 252));
        chatRoom.setFont(FontUtil.getFontByName("Twenty-Regular-2").deriveFont(Font.PLAIN,
                ImageUtil.resizeY(16)));
        chatRoom.setOpaque(false);

        // [ Chat Room                                   x ]
        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(new Color(126, 89, 203, 230));
        topPanel.setBorder(new MatteBorder(ImageUtil.resizeX(5),
                ImageUtil.resizeY(5), ImageUtil.resizeX(5), ImageUtil.resizeY(5),
                new Color(85, 35, 222, 230)));
        topPanel.add(chatRoom, BorderLayout.CENTER);
        topPanel.add(closeLabel, BorderLayout.EAST);


        playerPanel = new JPanel();
        playerPanel.setLayout(new GridBagLayout());
        playerPanel.setBackground(new Color(126, 89, 203, 230));
        playerPanel.setBorder(new MatteBorder(0, ImageUtil.resizeY(5), 0,
                ImageUtil.resizeY(5), new Color(85, 35, 222, 230)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        privateChat = Gui.chatPlayer;
        for (String user:privateChat.keySet()){
            privateChat.get(user).setForeground(new Color(106, 2, 1));
            privateChat.get(user).setBackground(new Color(178, 173, 204, 230));
            privateChat.get(user).setUI(new ButtonColorUI(new Color(83, 46, 91, 230)));
            privateChat.get(user).setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 250, 205),
                    new Color(255, 250, 205), new Color(139, 69, 19), new Color(139, 69, 19)),
                    new EmptyBorder(0, ImageUtil.resizeX(10), 0, ImageUtil.resizeX(10))));
            privateChat.get(user).setFont(FontUtil.getFontByName("Leira-Lite-2").deriveFont(Font.PLAIN, ImageUtil.resizeY(18)));
            gbc.fill = GridBagConstraints.BOTH;
            playerPanel.add(privateChat.get(user), gbc);
            gbc.gridy++;


            if (!Gui.chatHistory.containsKey(user)) {
                chatHistory = new JTextArea(ImageUtil.resizeX(10), ImageUtil.resizeY(20));
                chatHistory.setEditable(false);
                chatHistory.setForeground(new Color(106, 2, 1));
                chatHistory.setFont(new Font("Serif", Font.BOLD, ImageUtil.resizeY(14)));
                chatHistory.setLineWrap(true);
                chatHistory.setWrapStyleWord(true);
                chatHistory.setCaretPosition(chatHistory.getDocument().getLength());

                Gui.chatHistory.put(user, chatHistory);
            }
        }

        chatHistory = Gui.chatHistory.get(Gui.chatUser);

        scrollPane = new JScrollPane(chatHistory);
        scrollPane.setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, ImageUtil.resizeY(5),
                new Color(85, 35, 222, 230)), new EmptyBorder(ImageUtil.resizeX(5),
                ImageUtil.resizeY(5), ImageUtil.resizeX(5), ImageUtil.resizeY(5))));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUI(new ScrollBarUI());

        chatMessage = new JTextField(30) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(106, 2, 1, 255));
                g.setFont(new Font("Serif", Font.BOLD, ImageUtil.resizeY(14)));
                g.drawString("[" + Gui.chatUser + "]:", ImageUtil.resizeX(5), ImageUtil.resizeY(20));
            }
        };

        FontMetrics fm = chatMessage.getFontMetrics(chatMessage.getFont());
        int nicknameWidth = fm.stringWidth(Gui.chatUser);
        chatMessage.setBorder(new EmptyBorder(0, ImageUtil.resizeX(nicknameWidth + 30), 0, 0));
        //chatMessage.setBorder(new MatteBorder(0, ImageUtil.resizeX(40), 0, 0, new Color(126, 89, 203, 230)));
        chatMessage.setForeground(new Color(106, 2, 1));
        chatMessage.setFont(new Font("Serif", Font.BOLD, ImageUtil.resizeY(14)));

        sendButton = new JButton("Send");
        sendButton.setForeground(new Color(106, 2, 1));
        sendButton.setBackground(new Color(178, 173, 204, 230));
        sendButton.setUI(new ButtonColorUI(new Color(83, 46, 91, 230)));
        sendButton.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 250, 205),
                new Color(255, 250, 205), new Color(139, 69, 19), new Color(139, 69, 19)),
                new EmptyBorder(0, ImageUtil.resizeX(10), 0, ImageUtil.resizeX(10))));
        sendButton.setFont(FontUtil.getFontByName("Leira-Lite-2").deriveFont(Font.PLAIN, ImageUtil.resizeY(18)));


        chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());
        chatPanel.add(chatMessage, BorderLayout.CENTER);
        chatPanel.add(sendButton, BorderLayout.EAST);
        chatPanel.setBorder(new MatteBorder(ImageUtil.resizeX(3), ImageUtil.resizeY(5),
                ImageUtil.resizeX(5), ImageUtil.resizeY(5), new Color(85, 35, 222, 255)));

        add(playerPanel, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(chatPanel, BorderLayout.SOUTH);
        OutputStream outputStream = new OutputStream() {
            @Override
            public void write(int b) {
                chatHistory.append(String.valueOf((char) b));
                chatHistory.setCaretPosition(chatHistory.getDocument().getLength());
            }
        };

        PrintStream printStream = new PrintStream(outputStream, true, StandardCharsets.UTF_8);
        System.setOut(printStream);

        getRootPane().setDefaultButton(sendButton);

        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        setBackground(new Color(247, 253, 252, 128));
        setOpacity(0.75f);
        setVisible(true);

    }
}
