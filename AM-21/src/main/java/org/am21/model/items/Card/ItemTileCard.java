package org.am21.model.items.Card;

import org.am21.model.items.Cell;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class ItemTileCard extends Card {
    private boolean isNonSelectable;
    private Image selectableImage;
    private Image nonSelectableImage;

    private Cell cell;

    public ItemTileCard(String name, Integer width, Integer height) {
        super(name, width, height);
        this.selectableImage = Toolkit.getDefaultToolkit().getImage("AM-21\\imgs\\"+name+".png");
        this.nonSelectableImage = Toolkit.getDefaultToolkit().getImage("AM-21\\imgs\\"+name+"png");
        this.isNonSelectable = false;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("ItemTileCard.mouseClicked");
                ItemTileCard itemTileCard = (ItemTileCard) e.getSource();               //Get the current component

                if(itemTileCard.getNonSelectable()){
                    // Cannot be selected
                    return;
                }else{
                    // Remove from LivingRoom
                    itemTileCard.getParent().remove(itemTileCard);

                    cell.setState(0);
                    cell.setItemTileCard(null);
                }
            }
        });
    }

    public static Random random = new Random();
//    public static double num = random.nextDouble(1.3) - 1.1;

    public static String[] tileNames = {
            ItemType.Cats.name()+"1.1",ItemType.Cats.name()+"1.2",ItemType.Cats.name()+"1.3",
            ItemType.Books.name()+"1.1",ItemType.Books.name()+"1.2",ItemType.Books.name()+"1.3",
            ItemType.Games.name()+"1.1",ItemType.Games.name()+"1.2",ItemType.Games.name()+"1.3",
            ItemType.Frames.name()+"1.1",ItemType.Frames.name()+"1.2",ItemType.Frames.name()+"1.3",
            ItemType.Trophies.name()+"1.1",ItemType.Trophies.name()+"1.2",ItemType.Trophies.name()+"1.3",
            ItemType.Plants.name()+"1.1",ItemType.Plants.name()+"1.2",ItemType.Plants.name()+"1.3"
    };

    // getTileNames: Get a random card name for each call
    public static String getTileNames() {
        int randomIndex = random.nextInt(tileNames.length);
        return tileNames[randomIndex];
    }

    //
    public static ItemTileCard[] buildCards(){
        ItemTileCard[] itemTileCards = new ItemTileCard[132];

        for (int i = 0; i < itemTileCards.length; i++) {
            String randomTileName = getTileNames();

            ItemTileCard itemTileCard = new ItemTileCard(randomTileName,90,90);
            itemTileCards[i] = itemTileCard;
        }

        return itemTileCards;
    }

    @Override
    public void paint(Graphics g) {
        if(this.isNonSelectable){
            // The tiles that can not be taken
            g.drawImage(this.nonSelectableImage,this.getX(),this.getY(),null);
        }
        else {
            // The tiles that can be taken
            g.drawImage(this.selectableImage,this.getX(),this.getY(),null);
        }
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

enum ItemType {
    Cats,
    Books,
    Games,
    Frames,
    Trophies,
    Plants,
}