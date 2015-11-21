package pl.maxmati.po.automaton.coordinates;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by maxmati on 11/20/15
 */
public class Cords2DTest {

    @Test public void testToString(){
        Cords2D c = new Cords2D(3,6);
        assertEquals("[3,6]", c.toString());
    }

    @Test public void testEqualityWithNullFalse(){
        //noinspection ObjectEqualsNull
        assertFalse(new Cords2D(1,2).equals(null));
    }

    @Test public void testEqualityTransitivity(){
        Cords2D c1 = new Cords2D(2, 4);
        Cords2D c2 = new Cords2D(2, 4);
        Cords2D c3 = new Cords2D(2, 4);

        assertEquals(c1, c2);
        assertEquals(c2, c3);
        assertEquals(c1, c3);
    }

    @Test public void testEqualitySymmetry(){
        Cords2D c1 = new Cords2D(2, 4);
        Cords2D c2 = new Cords2D(4, 2);
        Cords2D c3 = new Cords2D(2, 4);

        assertEquals(c1,c3);
        assertEquals(c3,c1);

        assertNotEquals(c1,c2);
        assertNotEquals(c2,c1);
    }

}