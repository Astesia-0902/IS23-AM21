package org.am21.client.view.GUI.component;

import org.am21.client.view.GUI.utils.PathUtil;

import javax.swing.*;
import java.awt.*;

import static org.am21.client.view.GUI.utils.PixelUtil.pcHeight;
import static org.am21.client.view.GUI.utils.PixelUtil.pcWidth;

public class CommonGoalPanel extends JPanel {

    private final int cardWidth = pcWidth * 18 / 100;

    private final int cardHeight = pcHeight * 19 / 100;

    private final int posX = pcHeight * 3 / 100;

    private final int posYTop = pcHeight / 10;

    private final int posYBottom = pcHeight * 3 / 10;
    private JLayeredPane commonGoalPane;
    private JLabel commonGoalTopLabel;
    private JLabel commonGoalBottomLabel;

    private String topCardName;
    private String bottomCardName;

    private ImageIcon tokenBack;

    public CommonGoalPanel() {
        this.setBounds(posX, posYTop, this.cardWidth, this.cardHeight + (posYBottom - posYTop));
        this.setLayout(null);
        this.setOpaque(false);

        this.commonGoalPane = new JLayeredPane();
        this.commonGoalPane.setBounds(0, 0, this.cardWidth, this.cardHeight + (posYBottom - posYTop));
        this.commonGoalPane.setLayout(null);
        this.commonGoalPane.setOpaque(false);
        this.add(this.commonGoalPane);

        setTopCard(this.topCardName);
        setBottomCard(this.bottomCardName);

    }


    public void setTopCard(String topCardName) {
        this.commonGoalTopLabel = new JLabel();
        this.commonGoalTopLabel.setBounds(0, 0, this.cardWidth, this.cardHeight);
        this.commonGoalTopLabel.setIcon(loadCommonGoalImage(topCardName));
        this.commonGoalPane.add(this.commonGoalTopLabel, JLayeredPane.DEFAULT_LAYER);
    }

    public void setBottomCard(String bottomCardName) {
        this.commonGoalBottomLabel = new JLabel();
        this.commonGoalBottomLabel.setBounds(0, posYBottom - posYTop, this.cardWidth, this.cardHeight);
        this.commonGoalBottomLabel.setIcon(loadCommonGoalImage(bottomCardName));
        this.commonGoalPane.add(this.commonGoalBottomLabel, JLayeredPane.DEFAULT_LAYER);
    }
    public ImageIcon loadCommonGoalImage(String nameCard) {
        switch (nameCard) {
            case "2Columns":
                return new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/2.jpg")).getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH));
            case "2Lines":
                return new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/6.jpg")).getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH));
            case "3Column":
                return new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/5.jpg")).getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH));
            case "4Lines":
                return new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/7.jpg")).getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH));
            case "8Tiles":
                return new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/9.jpg")).getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH));
            case "Corner":
                return new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/8.jpg")).getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH));
            case "Diagonal":
                return new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/11.jpg")).getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH));
            case "Group4":
                return new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/3.jpg")).getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH));
            case "Group6":
                return new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/4.jpg")).getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH));
            case "Square":
                return new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/1.jpg")).getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH));
            case "Stairs":
                return new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/12.jpg")).getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH));
            case "Xshape":
                return new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/10.jpg")).getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH));
            default:
                return null;
        }

    }

    public ImageIcon loadTokenImage(int value){
        return null;
    }

    public void setCard(String topCardName,String bottomCardName){
        this.topCardName=topCardName;
        this.bottomCardName=bottomCardName;
    }
}
