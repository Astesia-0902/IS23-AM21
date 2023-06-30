package org.am21.client.view.GUI.Interface;

import org.am21.client.view.GUI.component.BackGroundPanel;
import org.am21.client.view.GUI.utils.ButtonUtil;
import org.am21.client.view.GUI.utils.FontUtil;
import org.am21.client.view.GUI.utils.IconUtil;
import org.am21.client.view.GUI.utils.ImageUtil;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Communication interface
 */
public class CommunicationInterface extends JDialog {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    public JLabel minusLabel;
    public JLabel closeLabel;
    public JButton socketButton;
    public JButton rmiButton;

    /**
     * Constructor
     *
     * @param frame is the frame
     */
    public CommunicationInterface(JFrame frame) {
        super(frame);
        frame.setTitle("MyShelfie - Communication");

        HashMap<BufferedImage, int[]> background = new HashMap<>();
        // Background
        background.put(IconUtil.getBufferedImage("communicationBG"), new int[]
                {0, ImageUtil.resizeY(-90), ImageUtil.resizeX(600), ImageUtil.resizeY(600)});
        BackGroundPanel backGroundPanel = new BackGroundPanel(background);
        JLabel question = new JLabel("Socket or RMI");
        question.setBorder(null);
        question.setBounds(ImageUtil.resizeX(181), ImageUtil.resizeY(195),
                ImageUtil.resizeX(356), ImageUtil.resizeY(108));
        question.setForeground(new Color(139, 69, 19));
        question.setFont(FontUtil.getFontByName("Twenty-Regular-2").deriveFont(Font.PLAIN,
                ImageUtil.resizeY(35)));
        question.setOpaque(false);
        getContentPane().add(question);

        // Icon
        minusLabel = new JLabel(IconUtil.getIcon("minus"));
        minusLabel.setBounds(ImageUtil.resizeX(541), ImageUtil.resizeY(6),
                ImageUtil.resizeX(25), ImageUtil.resizeY(25));
        getContentPane().add(minusLabel);

        closeLabel = new JLabel(IconUtil.getIcon("close"));
        closeLabel.setBounds(ImageUtil.resizeX(571), ImageUtil.resizeY(6),
                ImageUtil.resizeX(25), ImageUtil.resizeY(25));
        getContentPane().add(closeLabel);

        // Socket Button
        socketButton = ButtonUtil.getButton("Socket");
        socketButton.setBounds(ImageUtil.resizeX(30), ImageUtil.resizeY(295),
                ImageUtil.resizeX(250), ImageUtil.resizeY(46));
        getContentPane().add(socketButton);

        // RMI Button
        rmiButton = ButtonUtil.getButton("RMI");
        rmiButton.setBounds(ImageUtil.resizeX(320), ImageUtil.resizeY(295),
                ImageUtil.resizeX(250), ImageUtil.resizeY(46));
        getContentPane().add(rmiButton);


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
