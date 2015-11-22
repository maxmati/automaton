package pl.maxmati.po.automaton;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by maxmati on 11/22/15
 */
public class UtilsTest {

    @Test
    public void testMod() throws Exception {
        assertEquals(5, Utils.mod(5,10));
        assertEquals(2, Utils.mod(5,3));
        assertEquals(1, Utils.mod(-7,2));
    }
}