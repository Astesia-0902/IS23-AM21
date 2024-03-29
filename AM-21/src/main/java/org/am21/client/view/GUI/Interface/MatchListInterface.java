package org.am21.client.view.GUI.Interface;

import org.am21.client.view.GUI.component.ScrollBarUI;
import org.am21.client.view.GUI.utils.FontUtil;
import org.am21.client.view.GUI.utils.IconUtil;
import org.am21.client.view.GUI.utils.ImageUtil;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

/**
 * Match list interface
 */
public class MatchListInterface extends JDialog {
    public final ImageIcon closeIcon;
    public final ImageIcon closeIconSelect;
    public final JLabel closeLabel;
    public JList<String> matchList;
    public JPanel topPanel;
    public JScrollPane scrollPane;

    /**
     * Constructor
     *
     * @param frame     is the frame
     * @param matchModel is the match model
     */
    public MatchListInterface(JFrame frame, DefaultListModel<String> matchModel) {
        super(frame);
        setSize(ImageUtil.resizeX(300), ImageUtil.resizeY(400));
        matchList = new JList<>(matchModel);
        matchList.setForeground(new Color(106, 2, 1, 255));
        matchList.setFont(new Font("Serif", Font.BOLD, ImageUtil.resizeY(15)));

        scrollPane = new JScrollPane(matchList);
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

        JLabel match_list = new JLabel("Match List");
        match_list.setBorder(null);
        match_list.setBounds(ImageUtil.resizeX(181), ImageUtil.resizeY(195),
                ImageUtil.resizeX(356), ImageUtil.resizeY(108));
        match_list.setForeground(new Color(245, 238, 252));
        match_list.setFont(FontUtil.getFontByName("Twenty-Regular-2").deriveFont(Font.PLAIN,
                ImageUtil.resizeY(16)));
        match_list.setOpaque(false);

        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(new Color(126, 89, 203, 230));
        topPanel.setBorder(new MatteBorder(ImageUtil.resizeX(5),
                ImageUtil.resizeY(5), ImageUtil.resizeX(5), ImageUtil.resizeY(5),
                new Color(85, 35, 222, 230)));
        topPanel.add(closeLabel, BorderLayout.EAST);
        topPanel.add(match_list, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        setVisible(true);
        setBackground(new Color(178, 173, 204, 230));
        setOpacity(0.9f);
    }

}
