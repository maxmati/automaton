package pl.maxmati.po.automaton.automaton;

import pl.maxmati.po.automaton.Cell;
import pl.maxmati.po.automaton.coordinates.Cords1D;
import pl.maxmati.po.automaton.coordinates.Cords2D;
import pl.maxmati.po.automaton.state.CellState;

import static org.junit.Assert.*;

/**
 * Created by maxmati on 11/22/15
 */
public class AutomatonTestingUtils {

    public static void compareAutomatonWithStateArray(Automaton a, CellState[] state, String message){
        compareAutomatonWithStateArray(a, new CellState[][]{state}, message );
    }

    public static void compareAutomatonWithStateArray(Automaton a, CellState[][] state, String message){
        boolean hitArray[][] = new boolean[state.length][state[0].length];
        for(Cell c: a){
            if(!(c.cords instanceof Cords2D || c.cords instanceof Cords1D))
                fail("RenderableCell cords should be instance of Cords2D or Cords1D " + message);

            final String wrongStateMessage = String.format("Wrong state for %s ", c.cords) + message;
            final String doubleStateMessage = String.format("Double state for %s ", c.cords) + message;

            int x, y;
            if(c.cords instanceof Cords1D){
                y = 0;
                x = ((Cords1D) c.cords).x;
            } else {
                y = ((Cords2D) c.cords).y;
                x = ((Cords2D) c.cords).x;
            }
            assertEquals(wrongStateMessage, state[y][x], c.state);
            assertFalse(doubleStateMessage, hitArray[y][x]);
            hitArray[y][x] = true;
        }

        for (int y = 0; y < hitArray.length; y++) {
            for (int x = 0; x < hitArray[y].length; x++) {
                assertTrue(String.format("Missing state for [%d,%d] ",x,y) + message, hitArray[y][x]);
            }
        }
    }
}
