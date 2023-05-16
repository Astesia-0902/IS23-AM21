package org.am21.client.view.GUI.Interface;

import org.am21.client.view.ClientView;
import org.am21.client.view.GUI.component.ScrollBarUI;
import org.am21.client.view.GUI.utils.FontUtil;
import org.am21.client.view.GUI.utils.IconUtil;
import org.am21.client.view.GUI.utils.ImageUtil;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class OnlineList extends JDialog {
    public final JPanel topPanel;
    public JPanel playerPane;
    public final JScrollPane scrollPane;
    public final ImageIcon closeIcon;
    public final ImageIcon closeIconSelect;
    public final JLabel closeLabel;
    Map<JButton, JLabel> playerList = new HashMap<>();

    public OnlineList(JFrame frame) {
        super(frame);
        setSize(ImageUtil.resizeX(200), ImageUtil.resizeY(200));
        playerPane = new JPanel();
        for (int i = 0; i < ClientView.onlinePlayers.length; i++) {
            if (!containsButtonWithText(playerList, ClientView.onlinePlayers[i][0])) {
                if(ClientView.onlinePlayers[i][0] != null) {
                    playerPane.add(new JButton(ClientView.onlinePlayers[i][0]), BorderLayout.WEST);
                    playerPane.add(new JLabel(playerStatusIcon(ClientView.onlinePlayers[i][1])),BorderLayout.EAST);
                }
            }
        }

        scrollPane = new JScrollPane(playerPane);
        scrollPane.setBorder(new CompoundBorder(new MatteBorder(0, ImageUtil.resizeY(5), ImageUtil.resizeX(5), ImageUtil.resizeY(5),
                new Color(85, 35, 222, 230)), new EmptyBorder(ImageUtil.resizeX(5),
                ImageUtil.resizeY(5), ImageUtil.resizeX(5), ImageUtil.resizeY(5))));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUI(new ScrollBarUI());

        closeIcon = IconUtil.getIcon("close_Purple");
        closeIconSelect = IconUtil.getIcon("close_Fuchsia");
        closeLabel = new JLabel(closeIcon);
        closeLabel.setBounds(ImageUtil.resizeX(320), ImageUtil.resizeY(6), ImageUtil.resizeX(25), ImageUtil.resizeY(25));

        JLabel onlineList = new JLabel("Online List");
        onlineList.setBorder(null);
        onlineList.setBounds(ImageUtil.resizeX(181), ImageUtil.resizeY(195),
                ImageUtil.resizeX(356), ImageUtil.resizeY(108));
        onlineList.setForeground(new Color(245, 238, 252));
        onlineList.setFont(FontUtil.getFontByName("Twenty-Regular-2").deriveFont(Font.PLAIN,
                ImageUtil.resizeY(16)));
        onlineList.setOpaque(false);

        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(new Color(126, 89, 203, 230));
        topPanel.setBorder(new MatteBorder(ImageUtil.resizeX(5),
                ImageUtil.resizeY(5), ImageUtil.resizeX(5), ImageUtil.resizeY(5),
                new Color(85, 35, 222, 230)));
        topPanel.add(closeLabel, BorderLayout.EAST);
        topPanel.add(onlineList, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        setBackground(new Color(247, 253, 252, 128));
        setVisible(true);
    }

    private boolean containsButtonWithText(Map<JButton, JLabel> map, String text) {
        for (JButton button : map.keySet()) {
            if (button.getText().equals(text)) {
                return true;
            }
        }
        return false;
    }

    private ImageIcon playerStatusIcon(String status) {
        ImageIcon online = IconUtil.getIcon("OnlineMember");
        ImageIcon game = IconUtil.getIcon("GameMember");

        if (status.equals("Online")) {
            return online;
        } else if (status.equals("GameMember")) {
            return game;
        }
        return null;
    }
}
