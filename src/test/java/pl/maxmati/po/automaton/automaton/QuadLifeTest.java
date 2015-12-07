package pl.maxmati.po.automaton.automaton;

import org.junit.Test;
import pl.maxmati.po.automaton.coordinates.Cords2D;
import pl.maxmati.po.automaton.state.QuadState;

import java.util.HashMap;
import java.util.Map;

import static pl.maxmati.po.automaton.automaton.AutomatonTestingUtils.compareAutomatonWithStateArray;

/**
 * Created by maxmati on 12/7/15.
 */
public class QuadLifeTest {
    public static final int[][][] BLINKER_DATA = new int[][][]{
            {
                    {0, 0, 0},
                    {1, 2, 3},
                    {0, 0, 0}
            },
            {
                    {0, 4, 0},
                    {0, 2, 0},
                    {0, 4, 0}
            },
            {
                    {0, 0, 0},
                    {4, 2, 4},
                    {0, 0, 0}
            }
    };

    @Test public void testColoredBlinker(){
        Automaton a = new QuadLife(3,3);
        a.insertStructure(generateQuadStateStruct(BLINKER_DATA[0]));

        for (int i = 1; i < BLINKER_DATA.length; i++) {
            a = a.nextState();
            compareAutomatonWithStateArray(a, generateQuadStateArray(BLINKER_DATA[i]), String.format("Cycle %d:", i));
        }

    }

    private QuadState[][] generateQuadStateArray(int[][] data){
        QuadState[][] struct = new QuadState[data.length][data[0].length];
        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x < data[y].length; x++) {
                struct[y][x] = QuadState.valueOf(data[y][x]);
            }
        }
        return struct;
    }

    private Map<Cords2D, QuadState> generateQuadStateStruct(int[][] data){
        Map<Cords2D, QuadState> struct = new HashMap<>();
        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x < data[y].length; x++) {
                struct.put(new Cords2D(x, y), QuadState.valueOf(data[y][x]));
            }
        }
        return struct;
    }
}