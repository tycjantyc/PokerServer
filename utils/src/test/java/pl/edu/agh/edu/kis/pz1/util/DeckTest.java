package pl.edu.agh.edu.kis.pz1.util;
import org.junit.Test;
import pl.edu.agh.kis.pz1.util.Card;
import pl.edu.agh.kis.pz1.util.Deck;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.*;


public class DeckTest {
    @Test
    public void Deckconstructortest(){
        Deck deck = new Deck();
        assertEquals(52, deck.get_cards().size());
        Set set = new HashSet(deck.get_cards());
        assertEquals(52, set.size());
    }
    @Test
    public void takehand(){
        Deck deck = new Deck();
        assertEquals(5, deck.take_hand().size());
    }

    @Test
    public void shuffle_test(){
        Deck deck = new Deck();
        Deck deck_2 = new Deck();
        assertNotSame(deck.get_cards(),deck_2.get_cards());

    }
}
