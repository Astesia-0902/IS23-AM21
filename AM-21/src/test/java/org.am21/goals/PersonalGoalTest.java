package org.am21.goals;

import org.am21.controller.PlayerController;
import org.am21.model.Cards.ItemCard;
import org.am21.model.Cards.ItemType;
import org.am21.model.Cards.PersonalGoalCard;
import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.model.items.Shelf;
import org.am21.utilities.CardUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @version 1.0
 */
public class PersonalGoalTest {
    private Match m;
    private PersonalGoalCard card;
    private List<PersonalGoalCard> listCards;
    private PlayerController c;
    private Player p;
    private Shelf s;

    @BeforeEach
    void setUp(){
        m = new Match(2);
        listCards = CardUtil.buildPersonalGoalCard(2);
        c=new PlayerController("Ade",null);
        p= c.getPlayer();
        s=new Shelf(p);
        p.setShelf(s);
    }

    @AfterEach
    void tearDown(){
        m=null;
        listCards=null;
        c=null;
        p=null;
    }

    @Test
    void testRandomGeneration(){
        assertFalse(listCards.get(0).equals(listCards.get(1)));
    }

    @Test
    void testGoalShelf(){
        card = new PersonalGoalCard("PERSONAL_GOALs"+2);
        card.setupGoalShelf(p);

        assertEquals("_Plants_",card.getPersonalGoalShelf().getItemName(1,1));
        assertEquals("__Cats__",card.getPersonalGoalShelf().getItemName(2,0));

    }
    @Test
    void testCheckGoal(){
        card = new PersonalGoalCard("PERSONAL_GOALs"+2);
        card.player=p;
        p.setMyPersonalGoal(card);
        p.setShelf(s);
        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),1);
        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),1);
        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),1);
        s.insertInColumn(new ItemCard(ItemType.__Cats__+"1.1"),1);

        s.insertInColumn(new ItemCard(ItemType._Plants_+"1.1"),1);
        String type = s.getItemType(1,1);
        assertEquals("_Plants_",type);
        assertEquals(1,p.getMyPersonalGoal().calculatePoints());

    }

    //TODO: Test Points gaining in each stage of the table



}
