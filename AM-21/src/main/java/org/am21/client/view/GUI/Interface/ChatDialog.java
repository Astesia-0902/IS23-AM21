package org.am21.client.view.GUI.Interface;

import org.am21.client.view.ClientView;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatDialog extends JDialog {
    public JTextArea currentChatHistory;
    public JTextField chatMessageInput = new JTextField("");
    public JButton sendButton;
    public JPanel chatPanel;
    public JPanel topPanel;
    public JPanel playerPanel;
    public JLabel closeLabel;
    public JScrollPane scrollPane;
    public ImageIcon closeIconSelect;
    public ImageIcon closeIcon;
    public JLabel chatRoom;
    public GridBagConstraints gbc;
    public HashMap<String, JButton> localChatMap;

    public OutputStream outputStream;

    public PrintStream printStream;


    public Thread inputMinion = new Thread(()->{
        outputStream = new OutputStream() {
            @Override
            public void write(int b) {
                //currentChatHistory.append(String.valueOf((char) b));
                //currentChatHistory.setCaretPosition(currentChatHistory.getDocument().getLength());
            }
        };
        printStream = new PrintStream(outputStream, true, StandardCharsets.UTF_8);
        System.setOut(printStream);



    });
    public ChatDialog(JFrame frame) {
        super(frame);
        convertPrivateChatsForGUI();
        convertPublicChatForGUI();
        //setModal(false);       // If you do not close this window you will not be able to move the following windows
        setSize(ImageUtil.resizeX(500), ImageUtil.resizeY(500));

        closeIcon = IconUtil.getIcon("close_Purple");
        closeIconSelect = IconUtil.getIcon("close_Fuchsia");
        closeLabel = new JLabel(closeIcon);
        closeLabel.setBounds(ImageUtil.resizeX(320), ImageUtil.resizeY(6), ImageUtil.resizeX(25), ImageUtil.resizeY(25));

        chatRoom = new JLabel("Chat Room");
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
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        //Chat displayed at least once (clicked on Online List at least once)
        localChatMap = Gui.myChatMap;
        for (String user : localChatMap.keySet()) {
            localChatMap.get(user).setForeground(new Color(106, 2, 1));
            localChatMap.get(user).setBackground(new Color(178, 173, 204, 230));
            localChatMap.get(user).setUI(new ButtonColorUI(new Color(83, 46, 91, 230)));
            localChatMap.get(user).setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 250, 205),
                    new Color(255, 250, 205), new Color(139, 69, 19), new Color(139, 69, 19)),
                    new EmptyBorder(0, ImageUtil.resizeX(10), 0, ImageUtil.resizeX(10))));
            localChatMap.get(user).setFont(FontUtil.getFontByName("HongLeiXingShuJianTi-2")
                    .deriveFont(Font.PLAIN, ImageUtil.resizeY(18)));
            gbc.fill = GridBagConstraints.BOTH;
            playerPanel.add(localChatMap.get(user), gbc);
            gbc.gridy++;


            if (!Gui.privateChatHistoryMap.containsKey(user)) {
                currentChatHistory = new JTextArea(ImageUtil.resizeX(10), ImageUtil.resizeY(20));
                currentChatHistory.setEditable(false);
                currentChatHistory.setForeground(new Color(106, 2, 1));
                currentChatHistory.setFont(new Font("Serif", Font.BOLD, ImageUtil.resizeY(14)));
                currentChatHistory.setLineWrap(true);
                currentChatHistory.setWrapStyleWord(true);
                currentChatHistory.setCaretPosition(currentChatHistory.getDocument().getLength());

                Gui.privateChatHistoryMap.put(user, currentChatHistory);
            }
        }
        if (Gui.chatReceiver.equals("#All")) {
            System.out.println("Setup Public Chat");
            currentChatHistory = Gui.publicChatHistory;
        } else {
            System.out.println("Setup Private Chat: > "+Gui.chatReceiver);
            currentChatHistory = Gui.privateChatHistoryMap.get(Gui.chatReceiver);
        }
        scrollPane = new JScrollPane(currentChatHistory);
        scrollPane.setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, ImageUtil.resizeY(5),
                new Color(85, 35, 222, 230)), new EmptyBorder(ImageUtil.resizeX(5),
                ImageUtil.resizeY(5), ImageUtil.resizeX(5), ImageUtil.resizeY(5))));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUI(new ScrollBarUI());

        chatMessageInput = new JTextField(30) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(106, 2, 1, 255));
                g.setFont(new Font("Serif", Font.BOLD, ImageUtil.resizeY(14)));
                g.drawString("[" + Gui.chatReceiver + "]:", ImageUtil.resizeX(5), ImageUtil.resizeY(20));
            }
        };
        //chatMessageInput.setFont(new Font("Serif", Font.BOLD, ImageUtil.resizeY(14)));
        FontMetrics fm = chatMessageInput.getFontMetrics(chatMessageInput.getFont());
        int nicknameWidth = fm.stringWidth(Gui.chatReceiver);
        chatMessageInput.setBorder(new EmptyBorder(0, ImageUtil.resizeX(nicknameWidth + 30), 0, 0));
        //chatMessageInput.setBorder(new MatteBorder(0, ImageUtil.resizeX(40), 0, 0, new Color(126, 89, 203, 230)));
        chatMessageInput.setForeground(new Color(106, 2, 1));
        chatMessageInput.setFont(new Font("Serif", Font.BOLD, ImageUtil.resizeY(14)));

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
        chatPanel.add(chatMessageInput, BorderLayout.CENTER);
        getRootPane().setDefaultButton(sendButton);
        chatPanel.add(sendButton, BorderLayout.EAST);
        chatPanel.setBorder(new MatteBorder(ImageUtil.resizeX(3), ImageUtil.resizeY(5),
                ImageUtil.resizeX(5), ImageUtil.resizeY(5), new Color(85, 35, 222, 255)));

        add(playerPanel, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(chatPanel, BorderLayout.SOUTH);
        /*OutputStream outputStream = new OutputStream() {
            @Override
            public void write(int b) {
                //currentChatHistory.append(String.valueOf((char) b));
                //currentChatHistory.setCaretPosition(currentChatHistory.getDocument().getLength());
            }
        };

        PrintStream printStream = new PrintStream(outputStream, true, StandardCharsets.UTF_8);
        System.setOut(printStream);*/

        inputMinion.start();
        //getRootPane().setDefaultButton(sendButton);

        setLocationRelativeTo(frame);
        //setLocationRelativeTo(null);

        setResizable(false);
        setUndecorated(true);
        setBackground(new Color(247, 253, 252, 128));
        setOpacity(0.75f);
        System.out.println("End ChatDialog constructor");
    }


    public void reloadChat() {
        convertPrivateChatsForGUI();
        convertPublicChatForGUI();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        localChatMap = Gui.myChatMap;
        for (String user : localChatMap.keySet()) {
            localChatMap.get(user).setForeground(new Color(106, 2, 1));
            localChatMap.get(user).setBackground(new Color(178, 173, 204, 230));
            localChatMap.get(user).setUI(new ButtonColorUI(new Color(83, 46, 91, 230)));
            localChatMap.get(user).setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 250, 205),
                    new Color(255, 250, 205), new Color(139, 69, 19), new Color(139, 69, 19)),
                    new EmptyBorder(0, ImageUtil.resizeX(10), 0, ImageUtil.resizeX(10))));
            localChatMap.get(user).setFont(FontUtil.getFontByName("HongLeiXingShuJianTi-2")
                    .deriveFont(Font.PLAIN, ImageUtil.resizeY(18)));
            gbc.fill = GridBagConstraints.BOTH;
            playerPanel.add(localChatMap.get(user), gbc);
            gbc.gridy++;


            if (!Gui.privateChatHistoryMap.containsKey(user)) {
                JTextArea tmpChatHistory = new JTextArea(ImageUtil.resizeX(10), ImageUtil.resizeY(20));
                tmpChatHistory.setEditable(false);
                tmpChatHistory.setForeground(new Color(106, 2, 1));
                tmpChatHistory.setFont(new Font("Serif", Font.BOLD, ImageUtil.resizeY(14)));
                tmpChatHistory.setLineWrap(true);
                tmpChatHistory.setWrapStyleWord(true);
                tmpChatHistory.setCaretPosition(tmpChatHistory.getDocument().getLength());

                Gui.privateChatHistoryMap.put(user, tmpChatHistory);
            }
        }
        if (Gui.chatReceiver.equals("#All")) {
            currentChatHistory = Gui.publicChatHistory;
        } else {
            currentChatHistory = Gui.privateChatHistoryMap.get(Gui.chatReceiver);
        }
        scrollPane.setViewportView(currentChatHistory);
        getRootPane().setDefaultButton(sendButton);


    }

    public void convertPrivateChatsForGUI() {
        //HashMap<String, JTextArea> chatMap = new HashMap<>();
        java.util.List<JTextArea> visualChats = new ArrayList<>();
        System.out.println("Convert Private Chats");
        if (ClientView.privateChats != null && !ClientView.privateChats.isEmpty()) {
            java.util.List<java.util.List<String>> privateChatsList = ClientView.privateChats;
            for (java.util.List<String> chat : privateChatsList) {
                JTextArea historyTMP = new JTextArea(ImageUtil.resizeX(10), ImageUtil.resizeY(20));
                historyTMP.setEditable(false);
                historyTMP.setForeground(new Color(106, 2, 1));
                historyTMP.setFont(new Font("Serif", Font.BOLD, ImageUtil.resizeY(14)));
                historyTMP.setLineWrap(true);
                historyTMP.setWrapStyleWord(true);
                historyTMP.setCaretPosition(historyTMP.getDocument().getLength());
                for (String line : chat) {
                    historyTMP.append(line + "\n");
                }
                historyTMP.setCaretPosition(historyTMP.getDocument().getLength());
                //DEBUG print chat
                System.out.println(historyTMP.getText());
                visualChats.add(historyTMP);

            }

            // ChatMap (Keys)
            if (ClientView.chatMap != null && !ClientView.chatMap.isEmpty()) {
                for (Map.Entry<String, Integer> entry : ClientView.chatMap.entrySet()) {
                    String key = entry.getKey();
                    if (key.startsWith(Gui.username) || key.endsWith(Gui.username)) {
                        String[] newKey = key.split("@");
                        String receiver = "";
                        if (newKey[0].equals(Gui.username)) {
                            receiver = newKey[1];
                        } else if (newKey[1].equals(Gui.username)) {
                            receiver = newKey[0];
                        }

                        int value = entry.getValue();
                        //Insert key(receiver) and JTextArea of the Private Chat
                        // Direct Update
                        Gui.privateChatHistoryMap.put(receiver, visualChats.get(value));

                        //chatMap.put(receiver, visualChats.get(value));
                        if (Gui.myChatMap != null && !Gui.myChatMap.containsKey(receiver)) {
                            Gui.myChatMap.put(receiver, new JButton(receiver));
                        }
                    }
                }

            }

        }
        // Finally
        //privateChatHistoryMap = chatMap;

    }

    public void convertPublicChatForGUI() {
        JTextArea historyTMP = new JTextArea(ImageUtil.resizeX(10), ImageUtil.resizeY(20));
        historyTMP.setEditable(false);
        historyTMP.setForeground(new Color(106, 2, 1));
        historyTMP.setFont(new Font("Serif", Font.BOLD, ImageUtil.resizeY(14)));
        historyTMP.setLineWrap(true);
        historyTMP.setWrapStyleWord(true);
        historyTMP.setCaretPosition(historyTMP.getDocument().getLength());
        if (ClientView.publicChat != null && !ClientView.publicChat.isEmpty()) {
            List<String> tmpChat = ClientView.publicChat;
            for (String line : tmpChat) {
                historyTMP.append(line + "\n");
            }
            historyTMP.setCaretPosition(historyTMP.getDocument().getLength());

            //DEBUG print chat
            System.out.println(historyTMP.getText());
        } else {
            System.out.println("No Public chat");
        }
        Gui.publicChatHistory = historyTMP;
    }




}
