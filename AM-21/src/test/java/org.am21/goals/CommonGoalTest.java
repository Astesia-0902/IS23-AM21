package org.am21.goals;

import org.am21.controller.PlayerController;
import org.am21.model.Cards.CommonGoal;
import org.am21.model.Cards.CommonGoals.*;
import org.am21.model.Cards.ItemTileCard;
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

        s.insertInColumn(new ItemTileCard(ItemType.__Cats__+"1.1"),0);
        s.insertInColumn(new ItemTileCard(ItemType._Books__+"1.1"),0);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),0);
        s.insertInColumn(new ItemTileCard(ItemType._Frames_+"1.1"),0);
        s.insertInColumn(new ItemTileCard(ItemType._Games__+"1.1"),0);
        s.insertInColumn(new ItemTileCard(ItemType.Trophies+"1.1"),0);

        assertFalse(card.checkGoal(s));

        s.insertInColumn(new ItemTileCard(ItemType.__Cats__+"1.1"),3);
        s.insertInColumn(new ItemTileCard(ItemType._Books__+"1.1"),3);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),3);
        s.insertInColumn(new ItemTileCard(ItemType._Frames_+"1.1"),3);
        s.insertInColumn(new ItemTileCard(ItemType._Games__+"1.1"),3);
        s.insertInColumn(new ItemTileCard(ItemType.Trophies+"1.1"),3);

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
        s.insertInColumn(new ItemTileCard(ItemType.__Cats__+"1.1"),0);
        s.insertInColumn(new ItemTileCard(ItemType._Books__+"1.1"),1);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),2);
        s.insertInColumn(new ItemTileCard(ItemType._Frames_+"1.1"),3);
        s.insertInColumn(new ItemTileCard(ItemType._Games__+"1.1"),4);

        s.insertInColumn(new ItemTileCard(ItemType.__Cats__+"1.1"),0);
        s.insertInColumn(new ItemTileCard(ItemType._Books__+"1.1"),1);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),2);
        s.insertInColumn(new ItemTileCard(ItemType._Frames_+"1.1"),3);
        s.insertInColumn(new ItemTileCard(ItemType._Games__+"1.1"),4);

        assertTrue(card.checkGoal(s));

        s.setCell(5,1,new ItemTileCard(ItemType.__Cats__+"1.1"));
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

        s.insertInColumn(new ItemTileCard(ItemType._Books__+"1.1"),0);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),0);
        s.insertInColumn(new ItemTileCard(ItemType.Trophies+"1.1"),0);
        s.insertInColumn(new ItemTileCard(ItemType.Trophies+"1.1"),0);
        s.insertInColumn(new ItemTileCard(ItemType.Trophies+"1.1"),0);
        s.insertInColumn(new ItemTileCard(ItemType._Books__+"1.1"),0);

        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),3);
        s.insertInColumn(new ItemTileCard(ItemType._Frames_+"1.1"),3);
        s.insertInColumn(new ItemTileCard(ItemType._Games__+"1.1"),3);
        s.insertInColumn(new ItemTileCard(ItemType.Trophies+"1.1"),3);
        s.insertInColumn(new ItemTileCard(ItemType._Books__+"1.1"),3);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),3);

        s.insertInColumn(new ItemTileCard(ItemType._Frames_+"1.1"),2);
        s.insertInColumn(new ItemTileCard(ItemType.__Cats__+"1.1"),2);
        s.insertInColumn(new ItemTileCard(ItemType.Trophies+"1.1"),2);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),2);
        s.insertInColumn(new ItemTileCard(ItemType._Frames_+"1.1"),2);
        s.insertInColumn(new ItemTileCard(ItemType._Games__+"1.1"),2);

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

        s.insertInColumn(new ItemTileCard(ItemType._Books__+"1.1"),0);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),0);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),0);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),0);
        s.insertInColumn(new ItemTileCard(ItemType.Trophies+"1.1"),0);
        s.insertInColumn(new ItemTileCard(ItemType._Books__+"1.1"),0);

        s.insertInColumn(new ItemTileCard(ItemType._Frames_+"1.1"),1);
        s.insertInColumn(new ItemTileCard(ItemType._Frames_+"1.1"),1);
        s.insertInColumn(new ItemTileCard(ItemType._Games__+"1.1"),1);
        s.insertInColumn(new ItemTileCard(ItemType._Games__+"1.1"),1);
        s.insertInColumn(new ItemTileCard(ItemType._Frames_+"1.1"),1);
        s.insertInColumn(new ItemTileCard(ItemType._Frames_+"1.1"),1);

        s.insertInColumn(new ItemTileCard(ItemType.Trophies+"1.1"),4);
        s.insertInColumn(new ItemTileCard(ItemType.Trophies+"1.1"),4);
        s.insertInColumn(new ItemTileCard(ItemType.Trophies+"1.1"),4);
        s.insertInColumn(new ItemTileCard(ItemType.Trophies+"1.1"),4);
        s.insertInColumn(new ItemTileCard(ItemType.Trophies+"1.1"),4);
        s.insertInColumn(new ItemTileCard(ItemType.Trophies+"1.1"),4);

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
        s.insertInColumn(new ItemTileCard(ItemType.__Cats__+"1.1"),0);
        s.insertInColumn(new ItemTileCard(ItemType._Books__+"1.1"),1);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),2);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),3);
        s.insertInColumn(new ItemTileCard(ItemType.__Cats__+"1.1"),4);

        s.insertInColumn(new ItemTileCard(ItemType.__Cats__+"1.1"),0);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),1);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),2);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),3);
        s.insertInColumn(new ItemTileCard(ItemType.__Cats__+"1.1"),4);

        s.insertInColumn(new ItemTileCard(ItemType.__Cats__+"1.1"),0);
        s.insertInColumn(new ItemTileCard(ItemType.Trophies+"1.1"),1);
        s.insertInColumn(new ItemTileCard(ItemType._Games__+"1.1"),2);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),3);
        s.insertInColumn(new ItemTileCard(ItemType.__Cats__+"1.1"),4);

        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),0);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),1);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),2);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),3);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),4);

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
        s.insertInColumn(new ItemTileCard(ItemType.__Cats__+"1.1"),0);
        s.insertInColumn(new ItemTileCard(ItemType._Books__+"1.1"),1);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),2);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),3);
        s.insertInColumn(new ItemTileCard(ItemType.__Cats__+"1.1"),4);

        s.insertInColumn(new ItemTileCard(ItemType.__Cats__+"1.1"),0);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),1);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),2);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),3);
        s.insertInColumn(new ItemTileCard(ItemType.__Cats__+"1.1"),4);

        s.insertInColumn(new ItemTileCard(ItemType.__Cats__+"1.1"),0);
        s.insertInColumn(new ItemTileCard(ItemType.__Cats__+"1.1"),1);
        s.insertInColumn(new ItemTileCard(ItemType.__Cats__+"1.1"),2);
        s.insertInColumn(new ItemTileCard(ItemType.__Cats__+"1.1"),3);
        s.insertInColumn(new ItemTileCard(ItemType.__Cats__+"1.1"),4);

        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),0);
        s.insertInColumn(new ItemTileCard(ItemType.__Cats__+"1.1"),1);
        s.insertInColumn(new ItemTileCard(ItemType._Games__+"1.1"),2);
        s.insertInColumn(new ItemTileCard(ItemType.Trophies+"1.1"),3);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),4);

        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),0);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),1);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),2);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),3);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),4);

        assertTrue(card.checkGoal(s));
    }

    /**
     * 8 item of the same type and more random.
     */
    @Test
    void testCommGoal8Tiles(){
        card = new CommonGoal8Tiles(2);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),0);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),1);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),2);

        assertFalse(card.checkGoal(s));

        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),3);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),4);

        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),0);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),1);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),2);

        s.insertInColumn(new ItemTileCard(ItemType.__Cats__+"1.1"),0);
        s.insertInColumn(new ItemTileCard(ItemType._Books__+"1.1"),1);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),2);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),3);
        s.insertInColumn(new ItemTileCard(ItemType.__Cats__+"1.1"),4);

        s.insertInColumn(new ItemTileCard(ItemType.__Cats__+"1.1"),0);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),1);
        s.insertInColumn(new ItemTileCard(ItemType._Plants_+"1.1"),2);

        assertTrue(card.checkGoal(s));


    }






}
