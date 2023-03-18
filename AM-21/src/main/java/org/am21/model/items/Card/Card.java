package org.am21.model.items.Card;

import org.am21.model.items.Cell;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class Card extends Component {
    private String name;
    private boolean isNonSelectable;
    private Image selectableImage;
    private Image nonSelectableImage;

    private final Integer x;
    private final Integer y;
    private final Integer width;
    private final Integer height;

    private Cell cell;

    public Card(String name, Integer x, Integer y, Integer width, Integer height) {
        this.name = name;

        this.selectableImage = Toolkit.getDefaultToolkit().getImage("AM-21\\imgs\\"+name+".png");
        this.nonSelectableImage = Toolkit.getDefaultToolkit().getImage("AM-21\\imgs\\"+name+"png");
        this.isNonSelectable = false;

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Card.mouseClicked");
                Card card = (Card) e.getSource();               //Get the current component

                if(card.getNonSelectable()){
                    // Cannot be selected
                    return;
                }else{
                    // Remove from LivingRoom
                    cell.setState(0);
                    cell.setCard(null);
                }
            }
        });

    }

    @Override
    public void paint(Graphics g) {
        if(this.isNonSelectable){
            // The tiles that can not be taken
            g.drawImage(this.nonSelectableImage,this.x,this.y,null);
        }
        else {
            // The tiles that can be taken
            g.drawImage(this.selectableImage,this.x,this.y,null);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public boolean getNonSelectable() {
        return isNonSelectable;
    }

    public void setNonSelectable(boolean nonSelectable) {
        isNonSelectable = nonSelectable;
    }

    public Image getSelectableImage() {
        return selectableImage;
    }

    public void setSelectableImage(Image selectableImage) {
        this.selectableImage = selectableImage;
    }

    public Image getNonSelectableImage() {
        return nonSelectableImage;
    }

    public void setNonSelectableImage(Image nonSelectableImage) {
        this.nonSelectableImage = nonSelectableImage;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }
}
