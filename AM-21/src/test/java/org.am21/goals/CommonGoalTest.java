package org.am21.goals;

import org.am21.controller.PlayerController;
import org.am21.model.Cards.CommonGoal;
import org.am21.model.Cards.CommonGoals.CommonGoal2Columns;
import org.am21.model.Cards.ItemTileCard;
import org.am21.model.Cards.ItemType;
import org.am21.model.items.Shelf;
import org.am21.utilities.CommonGoalUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class CommonGoalTest {
    private Shelf s;
    private static int seats=2;
    private PlayerController c;



    @BeforeEach
    void setUp(){
        c = new PlayerController("A");
        s = new Shelf(c.player);
    }

    @AfterEach
    void tearDown(){
        c=null;
        s=null;
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
        CommonGoal card = new CommonGoal2Columns(2);

        assertTrue(s.insertInColumn(new ItemTileCard(ItemType.__Cats__+"1.1"),0));
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
        CommonGoal card = new CommonGoal2Columns(2);

        card.setAchievedPlayers(c.player);

        assertTrue(card.achievedPlayers.get(0).equals(c.player));

        assertEquals(1,card.tokenStack.size());


    }



}
