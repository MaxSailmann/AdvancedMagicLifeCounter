package de.sailmann.max.advancedmagiclifecounter;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void deckIsValid()
    {
        Deck deck = new Deck(false, false, false, false, false, false);
        assertFalse(deck.isValid());
        Deck deck1 = new Deck(true, false, false, false, false, false);
        assertTrue(deck1.isValid());
        Deck deck2 = new Deck(true, true, true, true, true, true);
        assertTrue(deck2.isValid());
    }
}