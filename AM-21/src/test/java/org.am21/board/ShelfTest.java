package org.am21.board;

import org.am21.controller.PlayerController;
import org.am21.model.Cards.ItemCard;
import org.am21.model.Cards.ItemType;
import org.am21.model.Player;
import org.am21.model.items.Shelf;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShelfTest {

    private Shelf s;
    private Player p;
    private PlayerController c;

    @BeforeEach
    void setUp(){
        c=new PlayerController("Ub");
        p=c.player;
        s=new Shelf(p);
    }

    @AfterEach
    void tearDown(){
        c=null;
        p=null;
        s=null;
    }

    /**
     * Testing array which keep count of how many slots are still available
     * in every column of the shelf
     */
    @Test
    void testSlotCol(){
        for(int x: s.slotCol) {
            assertEquals(6, x);
        }
    }

    /**
     * Test how much space is left on the shelf
     */
    @Test
    void testGetTotSlotAvailable(){
        assertEquals(30,s.getTotSlotAvail());
    }

    @Test
    void testInsertInColumn(){
        assertNull(s.getCell(5,0));
        s.insertInColumn(new ItemCard("Item"),0);
        assertNotNull(s.getCell(5,0));
    }


    @Test
    void testColorPoints(){
        s.insertInColumn(new ItemCard(ItemType.Trophies+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType.Trophies+"1.3"),1);
        s.insertInColumn(new ItemCard(ItemType.Trophies+"1.2"),1);

        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.3"),0);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.2"),1);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.2"),1);

        s.insertInColumn(new ItemCard(ItemType._Frames_+"1.1"),2);
        s.insertInColumn(new ItemCard(ItemType._Frames_+"1.3"),3);
        s.insertInColumn(new ItemCard(ItemType._Frames_+"1.2"),3);
        s.insertInColumn(new ItemCard(ItemType._Frames_+"1.2"),3);
        s.insertInColumn(new ItemCard(ItemType._Frames_+"1.1"),4);

        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.2"),0);
        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.3"),1);
        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),2);
        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.2"),2);
        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.3"),2);
        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),2);
        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.2"),3);

        s.insertInColumn(new ItemCard(ItemType._Games__+"1.3"),3);


        assertEquals(18,s.getGroupPoints());

        s.insertInColumn(new ItemCard(ItemType._Frames_+"1.1"),4);
        assertEquals(21,s.getGroupPoints());

    }




    /**
     * Check if the limit changes when the shelf is reaching his full capacity
     */
    @Test
    void testCheckLimit(){
        for(int r=0;r<4;r++){
            for(int i=0;i<Shelf.sColumn;i++)
                s.insertInColumn(new ItemCard(ItemType._Games__+"1.1"),i);
        }

        s.checkLimit();
        assertEquals(2,s.insertLimit);

        for(int i=0;i<Shelf.sColumn;i++)
            s.insertInColumn(new ItemCard(ItemType._Games__+"1.1"),i);

        s.checkLimit();
        assertEquals(1,s.insertLimit);

        for(int i=0;i<Shelf.sColumn;i++)
            s.insertInColumn(new ItemCard(ItemType._Games__+"1.1"),i);

        s.checkLimit();
        assertEquals(0,s.insertLimit);
    }

}
