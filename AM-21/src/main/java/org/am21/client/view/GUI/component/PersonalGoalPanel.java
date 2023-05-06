package org.am21.client.view.GUI.component;

import org.am21.client.view.GUI.utils.PathUtil;

import javax.swing.*;

import java.awt.*;

import static org.am21.client.view.GUI.utils.PixelUtil.pcHeight;
import static org.am21.client.view.GUI.utils.PixelUtil.pcWidth;

public class PersonalGoalPanel extends JPanel {
    private final int cardWidth = pcHeight*19/100;

    private final int cardHeight = pcWidth*18/100;

    private final int posX = pcWidth*5/7;

    private final int posYTop = pcHeight/10;
    private JLayeredPane personalGoalPane;
    private JLabel personalGoalLabel;
    private String cardName;

    public PersonalGoalPanel(){
        this.setBounds(posX, posYTop, this.cardWidth, this.cardHeight);
        this.setLayout(null);
        this.setOpaque(false);

        this.personalGoalPane = new JLayeredPane();
        this.personalGoalPane.setBounds(0,0, this.cardWidth, this.cardHeight);
        this.personalGoalPane.setLayout(null);
        this.personalGoalPane.setOpaque(false);
        this.add(this.personalGoalPane);

        this.personalGoalLabel = new JLabel();
        this.personalGoalLabel.setBounds(0,0,this.cardWidth, this.cardHeight);
        this.personalGoalLabel.setIcon(loadImage(this.cardName));
        this.personalGoalPane.add(this.personalGoalLabel,JLayeredPane.DEFAULT_LAYER);
    }

    public ImageIcon loadImage(String cardName){
        switch(cardName){
            case"Goals1": return new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals1.png")).getImage().getScaledInstance(cardWidth,cardHeight, Image.SCALE_SMOOTH));
            case"Goals2": return new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals2.png")).getImage().getScaledInstance(cardWidth,cardHeight, Image.SCALE_SMOOTH));
            case"Goals3": return new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals3.png")).getImage().getScaledInstance(cardWidth,cardHeight, Image.SCALE_SMOOTH));
            case"Goals4": return new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals4.png")).getImage().getScaledInstance(cardWidth,cardHeight, Image.SCALE_SMOOTH));
            case"Goals5": return new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals5.png")).getImage().getScaledInstance(cardWidth,cardHeight, Image.SCALE_SMOOTH));
            case"Goals6": return new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals6.png")).getImage().getScaledInstance(cardWidth,cardHeight, Image.SCALE_SMOOTH));
            case"Goals7": return new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals7.png")).getImage().getScaledInstance(cardWidth,cardHeight, Image.SCALE_SMOOTH));
            case"Goals8": return new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals8.png")).getImage().getScaledInstance(cardWidth,cardHeight, Image.SCALE_SMOOTH));
            case"Goals9": return new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals9.png")).getImage().getScaledInstance(cardWidth,cardHeight, Image.SCALE_SMOOTH));
            case"Goals10": return new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals10.png")).getImage().getScaledInstance(cardWidth,cardHeight, Image.SCALE_SMOOTH));
            case"Goals11": return new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals11.png")).getImage().getScaledInstance(cardWidth,cardHeight, Image.SCALE_SMOOTH));
            case"Goals12": return new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/Personal_Goals12.png")).getImage().getScaledInstance(cardWidth,cardHeight, Image.SCALE_SMOOTH));

            default: return null;
        }
    }

    public void setCard(String cardName){
        this.cardName = cardName;
    }
}
