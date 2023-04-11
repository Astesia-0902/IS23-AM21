package org.am21.goals;

import org.am21.controller.PlayerController;
import org.am21.model.Cards.PersonalGoalCard;
import org.am21.model.Match;
import org.am21.model.Player;
import org.am21.utilities.CardUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class PersonalGoalTest {

    private Match m;
    private PersonalGoalCard card;
    private List<PersonalGoalCard> listCards;
    private PlayerController c;
    private Player p;

    @BeforeEach
    void setUp(){
        m = new Match(2);
        listCards = CardUtil.buildPersonalGoalCard(2);
        c=new PlayerController("Ade");
        p=c.player;
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

        assertEquals("_Plants_",card.getGoalShelf().getItemName(1,1));
        assertEquals("__Cats__",card.getGoalShelf().getItemName(2,0));
        
    }
    @Test
    void testCheckGoal(){
        card = new PersonalGoalCard("PERSONAL_GOALs"+2);



    }



}
