package pl.maxmati.po.automaton.state;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by maxmati on 11/22/15
 */
public class WireWorldStateTest {

    @Test
    public void testValueOf() throws Exception {
        assertEquals(WireWorldState.VOID, WireWorldState.valueOf(0));
        assertEquals(WireWorldState.WIRE, WireWorldState.valueOf(1));
        assertEquals(WireWorldState.HEAD, WireWorldState.valueOf(2));
        assertEquals(WireWorldState.TAIL, WireWorldState.valueOf(3));
        assertNull(WireWorldState.valueOf(10));
    }
}