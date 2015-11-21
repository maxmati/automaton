package pl.maxmati.po.automaton.state;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by maxmati on 11/22/15
 */
public class BinaryStateTest {

    @Test public void testValueOf0_Dead(){
        assertEquals(BinaryState.DEAD, BinaryState.valueOf(0));
    }

    @Test public void testValueOf1_Alive(){
        assertEquals(BinaryState.ALIVE, BinaryState.valueOf(1));
    }

    @Test public void testValueOf2_Null(){
        assertNull(BinaryState.valueOf(2));
    }

}