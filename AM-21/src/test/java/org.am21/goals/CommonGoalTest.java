package org.am21.goals;

import org.am21.controller.PlayerController;
import org.am21.model.Cards.CommonGoal;
import org.am21.model.Cards.CommonGoals.*;
import org.am21.model.Cards.ItemCard;
import org.am21.model.Cards.ItemType;
import org.am21.model.items.Shelf;
import org.am21.utilities.CommonGoalUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommonGoalTest {
    private Shelf s;
    private static int seats=2;
    private PlayerController c;
    private CommonGoal card;


    @BeforeEach
    void setUp(){
        c = new PlayerController("A");
        s = new Shelf(c.player);
    }

    @AfterEach
    void tearDown(){
        c=null;
        s=null;
        card=null;
    }

    /**
     * Test if TokenStack is correctly generated
     */
    @Test
    void testCommGoalGen(){
        List<CommonGoal> listGoal = new ArrayList<>();
        listGoal = CommonGoalUtil.getCommonGoals(2);

        for(CommonGoal x : listGoal){
            assertEquals(2,x.tokenStack.size());
        }

    }

    /**
     * Test CommonGoal2Columns check Goal
     */
    @Test
    void testComm2Col(){
        card = new CommonGoal2Columns(2);

        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType._Books__+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType._Frames_+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType._Games__+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType.Trophies+"1.1"),0);

        assertFalse(card.checkGoal(s));

        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),3);
        s.insertInColumn(new ItemCard(ItemType._Books__+"1.1"),3);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),3);
        s.insertInColumn(new ItemCard(ItemType._Frames_+"1.1"),3);
        s.insertInColumn(new ItemCard(ItemType._Games__+"1.1"),3);
        s.insertInColumn(new ItemCard(ItemType.Trophies+"1.1"),3);

        assertTrue(card.checkGoal(s));


    }

    /**
     * Testing setAchievedPlayers method and his side effects
     */
    @Test
    void testSetAchievedPlayers(){
        card = new CommonGoal2Columns(2);

        card.setAchievedPlayers(c.player);

        assertTrue(card.achievedPlayers.get(0).equals(c.player));

        assertEquals(1,card.tokenStack.size());

    }

    /**
     * First Setup: 2 Lines with all different type -> True
     * Second Setup: 2 Lines with some of the same type -> False
     */
    @Test
    void testComm2Lines(){
        card = new CommonGoal2Lines(2);
        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType._Books__+"1.1"),1);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),2);
        s.insertInColumn(new ItemCard(ItemType._Frames_+"1.1"),3);
        s.insertInColumn(new ItemCard(ItemType._Games__+"1.1"),4);

        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType._Books__+"1.1"),1);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),2);
        s.insertInColumn(new ItemCard(ItemType._Frames_+"1.1"),3);
        s.insertInColumn(new ItemCard(ItemType._Games__+"1.1"),4);

        assertTrue(card.checkGoal(s));

        s.setCell(5,1,new ItemCard(ItemType.__Cats__+"1.1"));
        assertFalse(card.checkGoal(s));


    }

    /**
     * Setup:
     * Column 1: Correct
     * Column 2: Wrong
     * Column 3: Wrong
     * CheckGoal == False
     */
    @Test
    void testComm3ColumnsFalse(){
        card = new CommonGoal3Column(2);

        s.insertInColumn(new ItemCard(ItemType._Books__+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType.Trophies+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType.Trophies+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType.Trophies+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType._Books__+"1.1"),0);

        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),3);
        s.insertInColumn(new ItemCard(ItemType._Frames_+"1.1"),3);
        s.insertInColumn(new ItemCard(ItemType._Games__+"1.1"),3);
        s.insertInColumn(new ItemCard(ItemType.Trophies+"1.1"),3);
        s.insertInColumn(new ItemCard(ItemType._Books__+"1.1"),3);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),3);

        s.insertInColumn(new ItemCard(ItemType._Frames_+"1.1"),2);
        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),2);
        s.insertInColumn(new ItemCard(ItemType.Trophies+"1.1"),2);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),2);
        s.insertInColumn(new ItemCard(ItemType._Frames_+"1.1"),2);
        s.insertInColumn(new ItemCard(ItemType._Games__+"1.1"),2);

        assertFalse(card.checkGoal(s));



    }

    /**
     * Setup:
     * Column 1: 3 type
     * Column 2: 2 type
     * Column 3: 1 type
     */
    @Test
    void testComm3ColumnsTrue(){
        card = new CommonGoal3Column(2);

        s.insertInColumn(new ItemCard(ItemType._Books__+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType.Trophies+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType._Books__+"1.1"),0);

        s.insertInColumn(new ItemCard(ItemType._Frames_+"1.1"),1);
        s.insertInColumn(new ItemCard(ItemType._Frames_+"1.1"),1);
        s.insertInColumn(new ItemCard(ItemType._Games__+"1.1"),1);
        s.insertInColumn(new ItemCard(ItemType._Games__+"1.1"),1);
        s.insertInColumn(new ItemCard(ItemType._Frames_+"1.1"),1);
        s.insertInColumn(new ItemCard(ItemType._Frames_+"1.1"),1);

        s.insertInColumn(new ItemCard(ItemType.Trophies+"1.1"),4);
        s.insertInColumn(new ItemCard(ItemType.Trophies+"1.1"),4);
        s.insertInColumn(new ItemCard(ItemType.Trophies+"1.1"),4);
        s.insertInColumn(new ItemCard(ItemType.Trophies+"1.1"),4);
        s.insertInColumn(new ItemCard(ItemType.Trophies+"1.1"),4);
        s.insertInColumn(new ItemCard(ItemType.Trophies+"1.1"),4);

        assertTrue(card.checkGoal(s));

    }

    /**
     * Setup:
     * Line 0: 3 Types
     * Line 1: 2 Types
     * Line 3: 4 Types
     * Line 5: 1 Type
     */
    @Test
    void testCommGoal4LinesFalse(){
        card = new CommonGoal4Lines(2);
        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType._Books__+"1.1"),1);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),2);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),3);
        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),4);

        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),1);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),2);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),3);
        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),4);

        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType.Trophies+"1.1"),1);
        s.insertInColumn(new ItemCard(ItemType._Games__+"1.1"),2);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),3);
        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),4);

        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),1);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),2);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),3);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),4);

        assertFalse(card.checkGoal(s));


    }

    /**
     * Setup:
     * Line 0: Correct(3 types)
     * Line 1: Correct(2 types)
     * Line 2: Correct(1 type)
     * Line 3: Wrong (4 types)
     * Line 4: Correct(1 type)
     */
    @Test
    void testCommGoal4LinesTrue(){
        card = new CommonGoal4Lines(2);
        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType._Books__+"1.1"),1);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),2);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),3);
        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),4);

        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),1);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),2);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),3);
        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),4);

        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),1);
        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),2);
        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),3);
        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),4);

        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),1);
        s.insertInColumn(new ItemCard(ItemType._Games__+"1.1"),2);
        s.insertInColumn(new ItemCard(ItemType.Trophies+"1.1"),3);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),4);

        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),1);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),2);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),3);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),4);

        assertTrue(card.checkGoal(s));
    }

    /**
     * 8 item of the same type and more random.
     */
    @Test
    void testCommGoal8Tiles(){
        card = new CommonGoal8Tiles(2);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),1);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),2);

        assertFalse(card.checkGoal(s));

        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),3);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),4);

        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),1);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),2);

        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType._Books__+"1.1"),1);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),2);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),3);
        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),4);

        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),0);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),1);
        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),2);

        assertTrue(card.checkGoal(s));

    }

    /**
     * Setup:
     * 1: 2 corner are missing
     * 2: 4 corner, same type
     * 3: 4 corner one different
     */
    @Test
    void testCommGoalCorner(){
        card = new CommonGoalCorner(2);

        s.setCell(0,0,new ItemCard(ItemType._Plants_+"1.1"));
        s.setCell(0,4,new ItemCard(ItemType._Plants_+"1.1"));

        assertFalse(card.checkGoal(s));
        s.setCell(5,0,new ItemCard(ItemType._Plants_+"1.1"));
        s.setCell(5,4,new ItemCard(ItemType._Plants_+"1.1"));

        assertTrue(card.checkGoal(s));

        s.setCell(5,4,new ItemCard(ItemType._Games__+"1.1"));
        assertFalse(card.checkGoal(s));

    }

    /**
     * Test Diagonals of the shelf
     */
    @Test
    void TestCommGoalDiagonalTrue(){
        card = new CommonGoalDiagonal(2);
        //Starting point bottom left
        for(int i=0;i<2;i++){
            for(int r=i,c=0;(r<Shelf.sRow-1+i)&&c<Shelf.sColumn;r++,c++){
                s.setCell(r,c,new ItemCard(ItemType.Trophies+"1.1"));
            }
            assertTrue(card.checkGoal(s));
        }
        for(int i=0;i<2;i++){
            for(int r=i,c=4;(r<Shelf.sRow-1+i)&&c>=0;r++,c--){
                s.setCell(r,c,new ItemCard(ItemType.Trophies+"1.1"));
            }
            assertTrue(card.checkGoal(s));
        }
    }

    /**
     *
     */
    @Test
    void testCommGoalDiagonalFalse(){
        card = new CommonGoalDiagonal(2);
        s.setCell(0,4,new ItemCard(ItemType._Games__+"1.1"));
        assertFalse(card.checkGoal(s));

        for(int r=1,c=0;(r<Shelf.sRow-1)&&c<Shelf.sColumn;r++,c++){
            s.setCell(r,c,new ItemCard(ItemType.Trophies+"1.1"));

        }

        assertFalse(card.checkGoal(s));

    }

    /**
     * Setup:
     * 2 Square: Games
     * 1 Square: Frames
     */
    @Test
    void testCommGoalSquare(){
        card = new CommonGoalSquare(2);

        s.setCell(0,0,new ItemCard(ItemType._Games__+"1.1"));
        s.setCell(1,0,new ItemCard(ItemType._Games__+"1.1"));
        s.setCell(0,1,new ItemCard(ItemType._Games__+"1.1"));
        s.setCell(1,1,new ItemCard(ItemType._Games__+"1.1"));

        assertFalse(card.checkGoal(s));

        s.setCell(1,2,new ItemCard(ItemType._Frames_+"1.1"));
        s.setCell(2,2,new ItemCard(ItemType._Frames_+"1.1"));
        s.setCell(1,3,new ItemCard(ItemType._Frames_+"1.1"));
        s.setCell(2,3,new ItemCard(ItemType._Frames_+"1.1"));

        s.setCell(3,3,new ItemCard(ItemType._Games__+"1.1"));
        s.setCell(4,3,new ItemCard(ItemType._Games__+"1.1"));
        s.setCell(3,4,new ItemCard(ItemType._Games__+"1.1"));

        assertFalse(card.checkGoal(s));
        s.setCell(4,4,new ItemCard(ItemType._Games__+"1.1"));

        assertTrue(card.checkGoal(s));
    }

    /**
     *
     */
    @Test
    void testCommGoal4Group(){
        card = new CommonGoal4Group(2);

        s.setCell(0,0,new ItemCard(ItemType.Trophies+"1.3"));
        s.setCell(1,0,new ItemCard(ItemType.Trophies+"1.3"));
        s.setCell(0,1,new ItemCard(ItemType.Trophies+"1.3"));
        s.setCell(1,1,new ItemCard(ItemType.Trophies+"1.3"));

        assertFalse(card.checkGoal(s));

        s.setCell(2,0,new ItemCard(ItemType._Frames_+"1.3"));
        s.setCell(3,0,new ItemCard(ItemType._Frames_+"1.3"));
        s.setCell(2,1,new ItemCard(ItemType._Frames_+"1.3"));
        s.setCell(3,1,new ItemCard(ItemType._Frames_+"1.3"));

        s.setCell(0,4,new ItemCard(ItemType._Plants_+"1.3"));
        s.setCell(1,4,new ItemCard(ItemType._Plants_+"1.3"));
        s.setCell(2,4,new ItemCard(ItemType._Plants_+"1.3"));
        s.setCell(3,4,new ItemCard(ItemType._Plants_+"1.3"));

        s.setCell(5,2,new ItemCard(ItemType.Trophies+"1.3"));
        s.setCell(5,3,new ItemCard(ItemType.Trophies+"1.3"));
        s.setCell(4,3,new ItemCard(ItemType.Trophies+"1.3"));
        s.setCell(4,4,new ItemCard(ItemType.Trophies+"1.3"));

        assertTrue(card.checkGoal(s));

        s.setCell(1,3,new ItemCard(ItemType._Plants_+"1.3"));
        s.setCell(2,2,new ItemCard(ItemType._Plants_+"1.3"));
        s.setCell(1,2,new ItemCard(ItemType._Plants_+"1.3"));
        s.setCell(0,2,new ItemCard(ItemType._Plants_+"1.3"));

        s.setCell(0,3,new ItemCard(ItemType.__Cats__+"1,1"));
        s.setCell(3,3,new ItemCard(ItemType.__Cats__+"1,1"));
        s.setCell(2,3,new ItemCard(ItemType.__Cats__+"1,1"));

        assertTrue(card.checkGoal(s));



    }

    /**
     *
     */
    @Test
    void testCommGoal6Group(){
        card = new CommonGoal6Group(2);

        s.setCell(0,0,new ItemCard(ItemType.__Cats__+"1.1"));
        s.setCell(1,0,new ItemCard(ItemType.__Cats__+"1.2"));

        s.setCell(0,1,new ItemCard(ItemType._Plants_+"1.1"));
        s.setCell(1,1,new ItemCard(ItemType._Plants_+"1.2"));
        s.setCell(2,1,new ItemCard(ItemType._Plants_+"1.3"));
        s.setCell(2,0,new ItemCard(ItemType._Plants_+"1.1"));

        s.setCell(0,2,new ItemCard(ItemType._Frames_+"1.1"));
        s.setCell(1,2,new ItemCard(ItemType._Frames_+"1.1"));

        s.setCell(2,2,new ItemCard(ItemType.Trophies+"1.1"));
        s.setCell(2,3,new ItemCard(ItemType.Trophies+"1.2"));

        s.setCell(0,3,new ItemCard(ItemType._Games__+"1.1"));
        s.setCell(1,3,new ItemCard(ItemType._Games__+"1.3"));

        assertFalse(card.checkGoal(s));

        s.setCell(0,4,new ItemCard(ItemType.__Cats__+"1.1"));
        s.setCell(1,4,new ItemCard(ItemType.__Cats__+"1.1"));

        assertTrue(card.checkGoal(s));


    }

    @Test
    void testCommGoalXShape(){
        card = new CommonGoalXShape(2);

    }

    @Test
    void testCommGoalStair(){
        card = new CommonGoalStairs(2);


    }






}
