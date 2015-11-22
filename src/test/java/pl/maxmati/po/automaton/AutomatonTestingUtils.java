package pl.maxmati.po.automaton;

import pl.maxmati.po.automaton.coordinates.Cords2D;
import pl.maxmati.po.automaton.state.CellState;

import static org.junit.Assert.*;

/**
 * Created by maxmati on 11/22/15
 */
public class AutomatonTestingUtils {

    public static void compareAutomatonWithStateArray(Automaton a, CellState[][] state, String message){
        boolean hitArray[][] = new boolean[state.length][state[0].length];
        for(Cell c: a){
            if(!(c.cords instanceof Cords2D))
                fail("Cell cords should be instance of Cords2D " + message);
            Cords2D cords = (Cords2D) c.cords;
            assertEquals(String.format("Wrong state for [%d,%d] ",cords.x,cords.y) + message,
                    state[cords.y][cords.x], c.state);
            assertFalse(String.format("Double state for [%d,%d] ",cords.x,cords.y) + message,
                    hitArray[cords.y][cords.x]);
            hitArray[cords.y][cords.x] = true;
        }

        for (int y = 0; y < hitArray.length; y++) {
            for (int x = 0; x < hitArray[y].length; x++) {
                assertTrue(String.format("Missing state for [%d,%d] ",x,y) + message, hitArray[y][x]);
            }
        }
    }
}
